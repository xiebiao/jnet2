package com.github.jnet.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public final class Platform {

    public enum OS {
        Linux, Windows
    }

    public static void getEnv() {
        Map<String, String> env = System.getenv();
        Iterator<String> it = env.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            //  System.out.println(key + " " + env.get(key));
        }
    }

    public static void getProperties() {
        Properties props = System.getProperties();
        Object[] it = props.keySet().toArray();
        for (int i = 0; i < it.length; i++) {
            String key = String.valueOf(it[i]);
            //System.out.println(key + " " + props.getProperty(key));
        }

    }

    public static String getOsName() {
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name");
        return osName;
    }

    public static String getOsArch() {
        Properties props = System.getProperties();
        String osArch = props.getProperty("os.arch");
        return osArch;
    }

    public static String getOsVersion() {
        Properties props = System.getProperties();
        String osArch = props.getProperty("os.version");
        return osArch;
    }

    public static final void main(String args[]) {
        Platform.getEnv();
        System.out.println(Platform.getOsName());
        System.out.println(Platform.getOsArch());
        System.out.println(Platform.getOsVersion());

    }
}
