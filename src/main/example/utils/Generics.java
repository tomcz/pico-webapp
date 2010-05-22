package example.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Generics {

    public static <T> T first(List<T> list) {
        return isEmpty(list) ? null : list.get(0);
    }

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null) || collection.isEmpty();
    }

    public static <T> Set<T> newHashSet() {
        return new HashSet<T>();
    }

    public static <T> Set<T> newHashSet(T... items) {
        Set<T> set = newHashSet();
        Collections.addAll(set, items);
        return set;
    }

    public static <T> List<T> newArrayList() {
        return new ArrayList<T>();
    }

    public static <T> List<T> newArrayList(T... items) {
        List<T> list = newArrayList();
        Collections.addAll(list, items);
        return list;
    }

    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    public static <K, V> Map<K, V> newHashMap(K key, V value) {
        Map<K, V> map = newHashMap();
        map.put(key, value);
        return map;
    }
}
