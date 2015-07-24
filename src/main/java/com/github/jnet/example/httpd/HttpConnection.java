package com.github.jnet.example.httpd;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jnet.SourceConnection;
import com.github.jnet.utils.IoUtils;

/**
 * @author bjxieb
 * @date 7/24/15
 */
public class HttpConnection extends SourceConnection {
  private static final Logger log = LoggerFactory.getLogger(HttpConnection.class);
  private int bodyLength = 0;
  private int bodyStartPosition = 0;
  private Request request;
  private Response response;

  public HttpConnection(SocketChannel channel) throws IOException {
    super(channel);
    bodyLength = 0;
    bodyStartPosition = 0;
    request = new Request();
    response = new Response();
  }

  @Override
  public void write(ByteBuffer buffer) throws IOException {

  }

  @Override
  public void write() {

  }

  @Override
  public void read() throws IOException {
    this.readBuffer.limit(readBuffer.position() + bufferMaxSize);
    try {
      IoUtils.read(this.channel, this.readBuffer);
    } catch (Exception e) {
      e.printStackTrace();
      this.close();
      return;
    }
    String headerBufferString = readBuffer.getString("ASCII");
    int position = headerBufferString.indexOf("\r\n\r\n");
    String header = headerBufferString.substring(0, position);
    bodyStartPosition = position + 4;
    try {
      this.parseHeader(header);
      String bodyBufferString = readBuffer.getString(bodyStartPosition, bodyLength, "ASCII");
      this.parseBody(bodyBufferString);

    } catch (Exception e) {
      e.printStackTrace();
    }
    int len = readBuffer.position();
    byte lastByte = readBuffer.getByte(len - 1);
    byte[] exit = readBuffer.getBytes(0, len - 1);
    log.debug(len + "");
    // this.processor.write(this);

  }

  private void parseHeader(String header) throws Exception {
    log.debug(this.toString() + " Parse HTTP Header");
    String[] lines = header.split("\r\n");
    if (lines.length == 0) {
      throw new Exception("invalid headers");
    }
    String hline = lines[0];
    String[] row = hline.split(" ");
    if (row.length != 3) {
      throw new Exception("invalid headers");
    }
    if (!row[2].toUpperCase().equals(Http.VERSION)) {
      throw new Exception("Only supported " + Http.VERSION);
    }
    if (!row[0].equals("GET") && !row[0].equals("POST")) {
      throw new Exception("invalid headers");
    }
    request.headers.put(Headers.HEAD_METHOD, row[0].trim());
    String url = row[1].trim();
    if (url.indexOf("?") != -1) {
      url = url.substring(0, url.indexOf("?"));
    }
    request.headers.put(Headers.HEAD_URI, url);
    request.headers.put(Headers.HEAD_VERSION, row[2].trim());

    for (String line : lines) {
      row = line.split(": ");
      if (row.length != 2) {
        continue;
      }
      request.headers.put(row[0].trim(), row[1].trim());
    }
    if (!request.headers.containsKey(Headers.CONTENT_LENGTH)) {
      request.headers.put(Headers.CONTENT_LENGTH, "0");
    }
    bodyLength = Integer.parseInt(request.headers.get(Headers.CONTENT_LENGTH));
    if (bodyLength < 0) {
      throw new Exception("invalid headers");
    }
    log.debug(request.toString());
  }

  private void parseBody(String body) {
    log.debug(this.toString() + "parse HTTP Body.");
    String paramStr = body;
    String url = request.headers.get(Headers.HEAD_URI);
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
    if (request.headers.containsKey(Headers.HEAD_COOKIE)) {
      String cookieStr = request.headers.get(Headers.HEAD_COOKIE);
      String[] cookies = cookieStr.split(";");
      for (String line : cookies) {
        row = line.split("=");
        if (row.length != 2) {
          continue;
        }
        request.cookies.put(row[0].trim(), row[1].trim());
      }
    }
  }

  @Override
  public boolean close() throws IOException {
    return false;
  }
}
