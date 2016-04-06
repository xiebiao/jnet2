package com.github.logging.internal;

import java.util.logging.Level;

import com.github.logging.Logger;

/**
 * @author <a href="mailto:joyrap@qq.com">joyrap@qq.com</a>
 * @date 7/26/15
 */
public class DefaultLoggerImpl implements Logger {
  private Logger logger;
  private java.util.logging.Logger javaLogger;

  public DefaultLoggerImpl(String name) {
    javaLogger = java.util.logging.Logger.getLogger(name);
    // java.util.logging.Logger logger = new java.util.logging.Logger();
  }

  @Override
  public void debug(String info) {
    javaLogger.log(Level.FINEST, info);
  }

  @Override
  public void debug(Throwable throwable) {

  }

  @Override
  public void info(String info) {

  }

  @Override
  public void info(Throwable throwable) {

  }

  @Override
  public void error(String info) {

  }

  @Override
  public void error(Throwable throwable) {

  }
}
