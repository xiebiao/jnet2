package com.github.jnet.bio.example.httpd;


public class DefaultFilter implements Filter {

    @Override
    public Servlet getServlet(Request request) {
        String url = request.header.get(HttpHeader.HEAD_URL);
        int pos = url.indexOf("?");
        if (pos >= 0) {
            url = url.substring(0, pos);
        }
        if (url.indexOf("echo") >= 0) {
            return new EchoServlet();
        }
        if (url.indexOf("time") >= 0) {
            return new TimeServlet();
        }
        if (url.indexOf("error") >= 0) {
            return new InternalServerErrorServlet();
        }
        if (url.indexOf("moved") >= 0) {
            return new MovedServlet();
        }
        return new NotFoundServlet();
    }

}
