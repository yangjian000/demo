package com.example.demo.juc;

import com.sun.jmx.remote.internal.ArrayQueue;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.DefineThreadPool")
public class DefineThreadPool {
    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool(1, 1000, TimeUnit.MILLISECONDS, 1, (queue, task) -> {
            // 1 等待
//            queue.put(task);
            // 2 超时等待
//            queue.offer(task, 500, TimeUnit.MILLISECONDS);
            // 3 放弃任务
//            log.debug("放弃：{}",task);
            // 4 抛出异常
//            throw new RuntimeException("任务执行失败:" + task);
            // 5 让调用者自己执行任务
//            task.run();
        });
        for (int i = 0; i < 3; i++) {
            int j = i;
            pool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("{}", j);
            });
        }
    }
}

@FunctionalInterface
interface RejectPolicy<T> {
    void reject(BlockingQueue<T> taskQueue, T task);
}

@Slf4j(topic = "c.ThreadPool")
class ThreadPool {

    BlockingQueue<Runnable> taskQueue;

    private HashSet<Worker> workers = new HashSet<>();

    private int coreSize;

    private long timeout;

    private TimeUnit timeUnit;

    private RejectPolicy<Runnable> rejectPolicy;

    public void execute(Runnable task) {
        synchronized (workers) {
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                log.debug("新增worker：{}", worker);
                workers.add(worker);
                worker.start();
            } else {
                taskQueue.tryPut(rejectPolicy, task);
            }
        }
    }

    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int queueCapacity, RejectPolicy rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.rejectPolicy = rejectPolicy;
        this.taskQueue = new BlockingQueue<>(queueCapacity);
    }

    class Worker extends Thread {

        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
//            while (task != null || (task = taskQueue.take()) != null) {
            while (task != null || (task = taskQueue.poll(timeout, timeUnit)) != null) {
                try {
                    log.debug("正在执行：{}", task);
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }
            synchronized (workers) {
                log.debug("worker 被移除：{}", this);
                workers.remove(this);
            }
        }
    }


}

@Slf4j(topic = "c.BlockingQueue")
class BlockingQueue<T> {

    private Deque<T> deque = new ArrayDeque<>();

    private ReentrantLock lock = new ReentrantLock();

    private Condition fullWaitSet = lock.newCondition();

    private Condition emptyWaitSet = lock.newCondition();

    private int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }


    public T poll(long timeout, TimeUnit unit) {
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while (deque.isEmpty()) {
                try {
                    if (nanos <= 0) {
                        return null;
                    }
                    nanos = emptyWaitSet.awaitNanos(nanos); //返回剩余时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = deque.removeFirst();
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    public T take() {
        lock.lock();
        try {
            while (deque.isEmpty()) {
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = deque.removeFirst();
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    public void put(T element) {
        lock.lock();
        try {
            while (deque.size() == capacity) {
                try {
                    log.debug("等待加入任务队列：{}", element);
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列：{}", element);
            deque.addLast(element);
            emptyWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }

    public boolean offer(T element, long timeout, TimeUnit timeUnit) {
        lock.lock();
        long nanos = timeUnit.toNanos(timeout);
        try {
            while (deque.size() == capacity) {
                log.debug("等待加入任务队列：{}", element);
                try {
                    if (nanos <= 0) {
                        return false;
                    }
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列：{}", element);
            deque.addLast(element);
            emptyWaitSet.signal();
            return true;
        } finally {
            lock.unlock();
        }

    }

    public void tryPut(RejectPolicy<T> rejectPolicy, T element) {
        lock.lock();
        try {
            if (deque.size() == capacity) {
                rejectPolicy.reject(this, element);
            } else {
                deque.addLast(element);
                emptyWaitSet.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}