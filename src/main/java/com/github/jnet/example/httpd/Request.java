package com.github.jnet.example.httpd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Request {

    public Map<String, String> headers = new ConcurrentHashMap<String, String>();
    public Map<String, String> cookies = new ConcurrentHashMap<String, String>();
    public Map<String, String> params  = new ConcurrentHashMap<String, String>();

    public void reset() {
        headers.clear();
        cookies.clear();
        params.clear();
    }

    public String toString() {
        return "\n" + headers.toString() + "\n" + cookies.toString() + "\n" + params.toString();
    }
}
