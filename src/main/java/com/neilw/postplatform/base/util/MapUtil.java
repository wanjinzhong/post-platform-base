
package com.neilw.postplatform.base.util;

import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUtil {

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
}
