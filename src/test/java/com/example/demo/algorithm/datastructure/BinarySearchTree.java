package com.example.demo.algorithm.datastructure;


public class BinarySearchTree<Key extends Comparable<Key>, Value> {

    private Node<Key, Value> root;  // 根节点
    private int N;

    private static class Node<Key, Value> {
        public Key key;
        public Value value;
        public Node<Key, Value> left;
        public Node<Key, Value> right;

        public Node(Key key, Value value, Node<Key, Value> left, Node<Key, Value> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public int size() {
        return N;
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    public Node put(Node<Key, Value> x, Key key, Value value) {
        if (x == null) {
            return new Node(key, value, null, null);
        }
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            x.right = put(x.right, key, value);
        } else if (cmp < 0) {
            x.left = put(x.left, key, value);
        } else {
            x.value = value;
        }
        N++;
        return x;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    public Value get(Node<Key, Value> x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            return get(x.right, key);
        } else if (cmp < 0) {
            return get(x.left.key);
        } else {

            return x.value;
        }
    }

    public void delete(Key key) {
        delete(root, key);
    }

    public Node<Key, Value> delete(Node<Key, Value> x, Key key) {
        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);

        if (cmp > 0) {
            x.right = delete(x.right, key);
        } else if (cmp < 0) {
            x.left = delete(x.left, key);
        } else {
            // 如果key等于x节点的键，完成真正的删除节点动作，要删除的节点就是x

            // 让元素个数-1
            N--;
            // 找到右子树中最小的节点
            if (x.right == null) {
                return x.left;
            }
            if (x.left == null) {
                return x.right;
            }

            Node<Key, Value> minNode = x.right;
            while (minNode.left != null) {
                minNode = minNode.left;
            }

            // 删除右子树中最小的节点
            Node<Key, Value> n = x.right;
            while (n.left != null) {
                if (n.left.left != null) {
                    n.left = null;
                } else {
                    // 变换n节点
                    n = n.left;
                }
            }
            // 让x节点的左子树成为minNode的左子树
            minNode.left = x.left;
            // 让x节点的父节点指向minNode
            minNode.right = x.right;
            // 让x节点的父节点指向minNode
            x = minNode;

        }

        return x;
    }
}

class BinaryTreeTest {
    public static void main(String[] args) {
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>();
        tree.put(2, "lisi");
        tree.put(1, "zhangsan");
        tree.put(3, "wangwu");

        System.out.println(tree.size());

        System.out.println(tree.get(2));

        tree.delete(3);
        System.out.println(tree.size());

        System.out.println(tree.get(3));


    }
}