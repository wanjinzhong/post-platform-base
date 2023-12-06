
package com.neilw.postplatform.base.util;

import lombok.NoArgsConstructor;
import org.apache.commons.collections4.MapUtils;

import java.util.*;

@NoArgsConstructor
public final class MapUtil extends cn.hutool.core.map.MapUtil {

    public static <T, K> void putAll(Map<T, List<K>> map, T key, List<K> data) {
        List<K> items = map.getOrDefault(key, new ArrayList<>());
        items.addAll(data);
        map.put(key, items);
    }

    public static <T, K> void put(Map<T, List<K>> map, T key, K data) {
        List<K> items = map.getOrDefault(key, new ArrayList<>());
        items.add(data);
        map.put(key, items);
    }

    public static <T, K, V> void put(Map<T, Map<K, List<V>>> map, T key1, K key2, V data) {
        Map<K, List<V>> m = map.getOrDefault(key1, new HashMap<>());
        put(m, key2, data);
        map.put(key1, m);
    }

    public static <T, K> void combine(Map<T, K> map, Map<T, K> another) {
        if (MapUtils.isNotEmpty(another)) {
            map.putAll(another);
        }
    }
    public static <T, K> Map<T, K> buildMap(Object... dataPair) {
        Map<T, K> map = new HashMap<>();
        if (dataPair == null || dataPair.length == 0) {
            return map;
        }
        if (dataPair.length % 2 == 1) {
            throw new RuntimeException("Wrong number of data");
        }
        for (int i = 0; i < dataPair.length / 2; i ++) {
            map.put((T) dataPair[i*2], (K) dataPair[i*2+1]);
        }
        return map;
    }

    public static <K, V> Optional<V> get(Map<K, V> map, K key) {
        return Optional.ofNullable(map.get(key));
    }
}
