package com.example.demo.nio.netty;

import io.netty.channel.DefaultEventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class EventLoopTest {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(2); // io 事件，普通任务 ，定时任务
        EventLoopGroup dfGroup = new DefaultEventLoop();  // 普通任务 ， 定时任务
        System.out.println(NettyRuntime.availableProcessors());

        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());

        // 执行普通任务
        group.next().submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("thread poll ok");
        });

        log.debug("main");

        group.next().scheduleAtFixedRate(() -> {
            log.debug("schedule ok");
        }, 1, 1, TimeUnit.SECONDS);

    }
}
