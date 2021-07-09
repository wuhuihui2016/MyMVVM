package com.whh.others.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * HashMap 练习
 * <p>
 * author:wuhuihui 2021.07.09
 */
public class HashMapEx {

    static HashMap hashMap;

    public static void main(String[] args) {
        initMap();
        getMap();

        hashMap.putIfAbsent("bb", 4); //k-v已有，则不会覆盖已有key 的 value //替换已有元素
        System.out.println("新增bb，如果bb已在，不新增");
        getMap();

        hashMap.replace("aa", 5); //替换已有元素
        System.out.println("替换了 aa 上的 value");
        getMap();
    }

    private static void initMap() {
        hashMap = new HashMap();
        hashMap.put(1, "1111");
        hashMap.put(2, "2222");
        hashMap.put("3", "3333");
        hashMap.put("aa", "aaaa");
    }

    private static void getMap() {
        Iterator iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + " = " + value);
        }

        /**
         keySet():迭代后只能通过 get() 取 key
         entrySet()：迭代后可以 e.getKey()，e.getValue() 取 key 和 value。返回的是 Entry 接口
         entrySet 遍历 map 的性能更好，速度更快，多用 entrySet() 方式遍历。**/
    }


}
