package com.github.jnet.bio.example.httpd;


public interface Servlet {

    void doRequest(Request request, Response response) throws Exception;
}
