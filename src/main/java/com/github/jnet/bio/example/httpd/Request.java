package com.github.jnet.bio.example.httpd;

import java.util.HashMap;
import java.util.Map;

public class Request {

	public Map<String, String> header = new HashMap<String, String>();
	public Map<String, String> cookie = new HashMap<String, String>();
	public Map<String, String> params = new HashMap<String, String>();

	public void reset() {
		header.clear();
		cookie.clear();
		params.clear();
	}

	public String toString() {
		return "\n" + header.toString() + "\n" + cookie.toString() + "\n"
				+ params.toString();
	}
}
