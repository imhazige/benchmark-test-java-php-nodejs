package com.kazge.example.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CollectionUtils {
    @SuppressWarnings("rawtypes")
    public static Map buildMap(Object... objects) {
        if (null == objects){
            return null;
        }

        if (null != objects && 0 != objects.length % 2) {
            throw new RuntimeException(
                    "to create map, must be key value matched.");
        }

        Map<Object, Object> map = new HashMap<Object, Object>();
        for (int i = 0; null != objects && i < objects.length; i += 2) {
            Object key = objects[i];
            Object value = objects[i + 1];
            if (null == value) {
                continue;
            }
            map.put(key, value);
        }

        return map;
    }

    public static List<String> flatMap(Map<String,Object> params){
        if (null == params){
            return null;
        }
        List<String> ps = new ArrayList<String>();
        Iterator<Entry<String, Object>> it = params.entrySet().iterator();

        while(it.hasNext()){
            Entry<String,Object> en = it.next();
            if (null == en.getValue()){
                continue;
            }
            ps.add(en.getKey());
            ps.add(en.getValue().toString());
        }

        return ps;
    }

    public static <T> int find(T[] array, T value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }

        return -1;
    }

    public static <K, V> Map<K, V> filter(Map<K, V> all, K[] keys) {
        if (null == keys) {
            return null;
        }
        Iterator<Entry<K, V>> it = all.entrySet().iterator();
        Map<K, V> map = null;

        while (it.hasNext()) {
            Entry<K, V> en = it.next();
            K k = en.getKey();
            V v = en.getValue();
            if (0 > find(keys, k)) {
                continue;
            }
            if (null == map) {
                map = new HashMap<K, V>();
            }
            map.put(k, v);
        }

        return map;
    }



}