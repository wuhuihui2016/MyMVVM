package com.whh.others.collection;

public class ArrayEx {

    /**
     * 合并数组
     * @param a
     * @param b
     * @return
     */
    static String[] concat(String[] a, String[] b) {
        String[] c = new String[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static void main(String[] args) {
        String[] a = {"1", "2", "3"};
        String[] b = {"4", "5", "6"};
        String[] concat = concat(a, b);
        for (int i = 0; i < concat.length; i++) {
            System.out.println(concat[i]);
        }
    }
}
