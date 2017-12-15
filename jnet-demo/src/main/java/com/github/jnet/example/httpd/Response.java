package com.github.jnet.example.httpd;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Response {

  private Map<String, String> header = new HashMap<String, String>();
  private Map<String, String> cookie = new HashMap<String, String>();
  private StringBuilder res = new StringBuilder();
  private String charset = "UTF-8";
  private int statusCode = 200;

  public void write(String str) {
    res.append(str);
  }

  public int getStatusCode() {
    return statusCode;
  }

  public Map<String, String> getHeader() {
    return header;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public void reset() {
    header.clear();
    cookie.clear();
    res = new StringBuilder();
  }

  public byte[] toBytes() throws UnsupportedEncodingException {
    byte[] body = res.toString().getBytes(charset);
    header.put(Headers.CONTENT_LENGTH, ((Integer) body.length).toString());

    if (!header.containsKey(Headers.HEAD_CONTENT_TYPE)) {
      header.put(Headers.HEAD_CONTENT_TYPE, "text/html");
    }

    StringBuilder sb = new StringBuilder();
    switch (this.statusCode) {
      case 200:
        sb.append(Http.VERSION + " 200 OK\r\n");
        break;
      case 302:
        sb.append(Http.VERSION + " 302 Moved \r\n");
        break;
      case 500:
        sb.append(Http.VERSION + " 500 INTERNAL SERVER ERROR\r\n");
        break;
    }

    Iterator<Map.Entry<String, String>> it = header.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, String> entry = it.next();
      sb.append(entry.getKey() + ": " + entry.getValue() + "\r\n");
    }
    sb.append("\r\n");

    byte[] head = sb.toString().getBytes(charset);
    byte[] pacaket = new byte[head.length + body.length];
    System.arraycopy(head, 0, pacaket, 0, head.length);
    System.arraycopy(body, 0, pacaket, head.length, body.length);

    return pacaket;
  }

  public String toString() {
    return "\n" + header.toString() + "\n" + cookie.toString() + "\n" + res.toString();
  }
}
