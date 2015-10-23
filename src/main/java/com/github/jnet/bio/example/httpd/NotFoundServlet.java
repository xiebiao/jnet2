package com.github.jnet.bio.example.httpd;

public class NotFoundServlet implements Servlet {

  public void doRequest(Request request, Response response) throws Exception {
    response.setStatusCode(404);
    response.write("Not found.");
  }

}
