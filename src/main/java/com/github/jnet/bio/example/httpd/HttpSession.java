package com.github.jnet.bio.example.httpd;

import com.github.jnet.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jnet.bio.Session;



public class HttpSession extends Session {

  private static final Logger logger = LoggerFactory.getLogger(HttpSession.class);

  /** Session每次最多读取数据大小(KB) */
  private static final int BUF_SIZE = 512;
  private static final int STATE_READ_HEAD = 0;
  private static final int STATE_READ_BODY = 1;

  private Request request = new Request();
  private Response response = new Response();
  private int currentState = STATE_READ_HEAD;
  private int bodyLen = 0;
  private int bodyStartPos = 0;

  public HttpSession() {
    request = new Request();
    response = new Response();
    currentState = STATE_READ_HEAD;
    bodyLen = 0;
    bodyStartPos = 0;

    ServletFactory.filter = (Filter) new DefaultFilter();
  }

  private void parseHeader(String header) throws Exception {
    logger.info(this.toString() + " Parse HTTP Header");
    String[] lines = header.split("\r\n");
    if (lines.length == 0) {
      throw new Exception("invalid header");
    }

    String hline = lines[0];
    String[] row = hline.split(" ");
    if (row.length != 3) {
      throw new Exception("invalid header");
    }
    if (!row[2].toUpperCase().equals(HttpVersion.VERSION)) {
      throw new Exception("Only supported " + HttpVersion.VERSION);
    }
    if (!row[0].equals("GET") && !row[0].equals("POST")) {
      throw new Exception("invalid header");
    }
    request.header.put(HttpHeader.HEAD_METHOD, row[0].trim());
    request.header.put(HttpHeader.HEAD_URL, row[1].trim());
    request.header.put(HttpHeader.HEAD_VERSION, row[2].trim());
    for (String line : lines) {
      row = line.split(": ");
      if (row.length != 2) {
        continue;
      }
      request.header.put(row[0].trim(), row[1].trim());
    }
    if (!request.header.containsKey(HttpHeader.CONTENT_LENGTH)) {
      request.header.put(HttpHeader.CONTENT_LENGTH, "0");
    }
    bodyLen = Integer.parseInt(request.header.get(HttpHeader.CONTENT_LENGTH));
    if (bodyLen < 0) {
      throw new Exception("invalid header");
    }
    logger.debug(request.toString());
  }

  private void parseBody(String body) {
    logger.info(this.toString() + "parse HTTP Body.");
    String paramStr = body;
    String url = request.header.get(HttpHeader.HEAD_URL);
    int paramPos = url.indexOf("?");
    if (paramPos >= 0) {
      paramStr = url.substring(paramPos + 1) + paramStr;
    }
    String[] params = paramStr.split("&");
    String[] row;
    for (String line : params) {
      row = line.split("=");
      if (row.length != 2) {
        continue;
      }
      request.params.put(row[0].trim(), row[1].trim());
    }
    if (request.header.containsKey(HttpHeader.HEAD_COOKIE)) {
      String cookieStr = request.header.get(HttpHeader.HEAD_COOKIE);
      String[] cookies = cookieStr.split(";");
      for (String line : cookies) {
        row = line.split("=");
        if (row.length != 2) {
          continue;
        }
        request.cookie.put(row[0].trim(), row[1].trim());
      }
    }
  }



  @Override
  public void read(IoBuffer readBuf, IoBuffer writeBuf) throws Exception {
    logger.info("Poccess the Session[" + this.getId() + "] ");
    logger.debug(readBuf.toString());
    if (currentState == STATE_READ_HEAD) {
      String buf = readBuf.getString("ASCII");
      int endPos = buf.indexOf("\r\n\r\n");
      if (endPos == -1) {
        remain(BUF_SIZE, IoState.READ);
      }
      currentState = STATE_READ_BODY;
      String header = buf.substring(0, endPos);
      parseHeader(header);
      bodyStartPos = endPos + 4;
    }
    if (currentState == STATE_READ_BODY) {
      if (bodyStartPos + bodyLen > readBuf.position()) {
        remain(bodyStartPos + bodyLen - readBuf.position(), IoState.READ);
        return;
      }
      currentState = STATE_READ_HEAD;
      String body = readBuf.getString(bodyStartPos, bodyLen, "ASCII");
      parseBody(body);
      handle(readBuf, writeBuf);
      setNextState(IoState.WRITE);
      return;
    }
    /** 读取剩下buffer */
    remain(BUF_SIZE, IoState.READ);
  }

  private void handle(IoBuffer readBuf, IoBuffer writeBuf) throws Exception {
    Servlet action = ServletFactory.get(request);
    if (action == null) {
      throw new Exception("action not found");
    }
    action.doRequest(request, response);
    writeBuf.position(0);
    writeBuf.writeBytes(response.toBytes());
    writeBuf.limit(writeBuf.position());
    writeBuf.position(0);
    request.reset();
    response.reset();
    logger.info("Write buffer to Session[" + this.getId() + "].");
  }

  @Override
  public void open(IoBuffer readBuf, IoBuffer writeBuf) throws Exception {
    remain(BUF_SIZE, IoState.READ);
  }

  public String toString() {
    return "Session[" + this.getId() + "] ";
  }

  @Override
  public void write(IoBuffer readBuf, IoBuffer writeBuf) throws Exception {
    logger.info(this.toString() + " writing...");

  }

  @Override
  public void close() {
    this.request.reset();
    this.response.reset();
    this.currentState = STATE_READ_HEAD;
    this.bodyLen = 0;
    this.bodyStartPos = 0;
  }
}
