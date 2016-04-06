package com.github.logging;

import org.junit.Test;

import com.github.logging.internal.DefaultLoggerImpl;

/**
 * @author <a href="mailto:joyrap@qq.com">joyrap@qq.com</a>
 * @date 7/26/15
 */
public class DefaultLoggerImplTest {
  @Test
  public void debug() {
    DefaultLoggerImpl defaultLogger = new DefaultLoggerImpl("test");
    defaultLogger.debug("xx");
  }
}
