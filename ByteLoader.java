package com.cj.loader;

import java.util.HashMap;
import java.util.Map;

public class ByteLoader extends ClassLoader {
    public Map<String, byte[]> classes = (Map)new HashMap<>();

    protected Class<?> findClass(String s) throws ClassNotFoundException {
        Class<?> clazz = null;
        try {
            super.findClass(s);
            } catch (Exception exception) {}
        try {
            if (this.classes.containsKey(s)) {
                clazz = defineClass(s, this.classes.get(s), 0, ((byte[])this.classes.get(s)).length);
                }
            } catch (Throwable err) {
            err.printStackTrace();
            }
        return clazz;
    }
}
