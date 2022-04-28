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
        put(root, key, value);
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
        delete(root,key);
    }

    public void delete(Node<Key, Value> x, Key key) {

    }
}
