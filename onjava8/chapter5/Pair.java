package com.example.onjava8.chapter5;

/**
 * Pair 是一个只读的 数据传输对象
 * 适合 Map 初始化。添加了静态的 make() 方法，以便为创建 Pair 对象提供一个更简洁的名字。
 */
public class Pair<K, V> {
    public final K key;
    public final V value;
    public Pair(K k, V v) {
        key = k;
        value = v;
    }
    public K key() { return key; }
    public V value() { return value; }
    public static <K,V> Pair<K, V> make(K k, V v) {
        return new Pair<K,V>(k, v);
    }
}
