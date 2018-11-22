package com.github.jnet.bio.example.httpd;

public class ServletFactory {

    public static  Filter         filter = null;
    private static ServletFactory servletFactory;

    private ServletFactory() {
    }

    public static ServletFactory getInstance() {
        if (servletFactory == null) {
            servletFactory = new ServletFactory();
        }
        return servletFactory;
    }

    public static Servlet get(Request url) {
        if (filter == null) {
            return null;
        }
        return filter.getServlet(url);
    }
}
