package com.github.jnet.bio.example.httpd;

/**
 * 返回HTTP 500
 * 
 * @author xiebiao
 *
 */
public class InternalServerErrorServlet implements Servlet {

  @Override
  public void doRequest(Request request, Response response) throws Exception {
    response.setStatusCode(500);
    response.write("系统内部错误");
  }

}
