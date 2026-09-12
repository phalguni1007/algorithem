package lru;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private final Map<K, Node<K, V>> cache;
    private final int capacity;
    private Node<K, V> head;
    private Node<K, V> tail;

    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be greater than zero");
        }
        this.capacity = capacity;
        this.cache = new HashMap<>();
    }

    public synchronized V get(K key) {
        Node<K, V> node = cache.get(key);
        if (node == null) {
            return null;
        }

        moveToHead(node);
        return node.value;
    }

    public synchronized void put(K key, V value) {
        Node<K, V> existing = cache.get(key);
        if (existing != null) {
            existing.value = value;
            moveToHead(existing);
            return;
        }

        Node<K, V> newNode = new Node<>(key, value);
        cache.put(key, newNode);
        addToHead(newNode);

        if (cache.size() > capacity) {
            Node<K, V> lru = tail;
            removeNode(lru);
            cache.remove(lru.key);
        }
    }

    public synchronized V remove(K key) {
        Node<K, V> node = cache.remove(key);
        if (node == null) {
            return null;
        }

        removeNode(node);
        return node.value;
    }

    public synchronized int size() {
        return cache.size();
    }

    public synchronized boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    public synchronized void clear() {
        cache.clear();
        head = null;
        tail = null;
    }

    private void addToHead(Node<K, V> node) {
        node.prev = null;
        node.next = head;

        if (head != null) {
            head.prev = node;
        }

        head = node;

        if (tail == null) {
            tail = node;
        }
    }

    pr(Node<K, V> node) {
        if (node == head) {
            return;
        }

        removeNode(node);
        addToHead(node);
    }

    private void removeNode(Node<K, V> node) {
        if (node == null) {
            return;
        }

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        node.prev = null;
        node.next = null;
    }

    private static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> prev;
        private Node<K, V> next;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
