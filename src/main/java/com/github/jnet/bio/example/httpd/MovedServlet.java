package com.github.jnet.bio.example.httpd;


public class MovedServlet implements Servlet {

    @Override
    public void doRequest(Request request, Response response) throws Exception {
        response.setStatusCode(302);
        response.getHeader().put("location",
            "http://localhost:8082/hello_world");
        response.write("跳转");
    }
}
