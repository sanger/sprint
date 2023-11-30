package uk.ac.sanger.sprint.utils;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

/**
 * A map with upper case strings for keys.
 * Keys are converted to upper case when they are put into the map.
 * Keys are converted to upper case when they are being looked up in the map.
 * @author dr6
 */
public class UCMap<V> implements Map<String, V> {
    private final Map<String, V> inner;

    public UCMap(int initialCapacity) {
        inner = new HashMap<>(initialCapacity);
    }

    public UCMap() {
        inner = new HashMap<>();
    }

    public UCMap(Map<String, V> contents) {
        this(contents.size());
        this.putAll(contents);
    }

    @Override
    public int size() {
        return inner.size();
    }

    @Override
    public boolean isEmpty() {
        return inner.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return inner.containsKey(upcase(key));
    }

    @Override
    public boolean containsValue(Object value) {
        return inner.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return inner.get(upcase(key));
    }

    @Override
    public V put(String key, V value) {
        return inner.put(upcase(key), value);
    }

    @Override
    public V remove(Object key) {
        return inner.remove(upcase(key));
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> m) {
        for (Entry<? extends String, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        inner.clear();
    }

    
    @Override
    public Set<String> keySet() {
        return inner.keySet();
    }

    
    @Override
    public Collection<V> values() {
        return inner.values();
    }

    
    @Override
    public Set<Entry<String, V>> entrySet() {
        return inner.entrySet();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return inner.getOrDefault(upcase(key), defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super V> action) {
        inner.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super String, ? super V, ? extends V> function) {
        inner.replaceAll(function);
    }

    
    @Override
    public V putIfAbsent(String key, V value) {
        return inner.putIfAbsent(upcase(key), value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return inner.remove(upcase(key), value);
    }

    @Override
    public boolean replace(String key, V oldValue, V newValue) {
        return inner.replace(upcase(key), oldValue, newValue);
    }

    
    @Override
    public V replace(String key, V value) {
        return inner.replace(upcase(key), value);
    }

    @Override
    public V computeIfAbsent(String key,  Function<? super String, ? extends V> mappingFunction) {
        return inner.computeIfAbsent(upcase(key), mappingFunction);
    }

    @Override
    public V computeIfPresent(String key,  BiFunction<? super String, ? super V, ? extends V> remappingFunction) {
        return inner.computeIfPresent(upcase(key), remappingFunction);
    }

    @Override
    public V compute(String key,  BiFunction<? super String, ? super V, ? extends V> remappingFunction) {
        return inner.compute(upcase(key), remappingFunction);
    }

    @Override
    public V merge(String key,  V value,  BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return inner.merge(upcase(key), value, remappingFunction);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj==this || obj instanceof Map && this.inner.equals(obj));
    }

    @Override
    public int hashCode() {
        return inner.hashCode();
    }

    private static <K> K upcase(K key) {
        if (key instanceof String) {
            //noinspection unchecked
            key = (K) ((String) key).toUpperCase();
        }
        return key;
    }

    @Override
    public String toString() {
        return "UCMap("+inner+")";
    }

    /**
     * Collector to collect to a {@code UCMap} from strings to the input objects.
     * @param keyMapper mapper from input objects to strings
     * @param <T> the type of input object
     * @return a collector to collect to a {@code UCMap}
     */
    public static <T> Collector<T, ?, UCMap<T>> toUCMap(Function<? super T, String> keyMapper) {
        return BasicUtils.inMap(keyMapper, UCMap::new);
    }

    public static <T> UCMap<T> from(Function<T, String> keyMapper, T value) {
        UCMap<T> map = new UCMap<>(1);
        map.put(keyMapper.apply(value), value);
        return map;
    }

    @SafeVarargs
    public static <T> UCMap<T> from(Function<T, String> keyMapper, T... values) {
        UCMap<T> map = new UCMap<>(values.length);
        for (T value : values) {
            map.put(keyMapper.apply(value), value);
        }
        return map;
    }

    public static <T> UCMap<T> from(Collection<? extends T> items, Function<T, String> keyMapper) {
        UCMap<T> map = new UCMap<>(items.size());
        for (T item : items) {
            map.put(keyMapper.apply(item), item);
        }
        return map;
    }
}
