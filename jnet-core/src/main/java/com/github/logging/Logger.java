package com.github.logging;

/**
 * @author xiebiao
 * @date 7/26/15
 */
public interface Logger {
  void debug(String info);

  void debug(Throwable throwable);

  void info(String info);

  void info(Throwable throwable);

  void error(String info);

  void error(Throwable throwable);
}
