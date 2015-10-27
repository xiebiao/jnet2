package org.glassfish.grizzly.demo;

import java.io.IOException;

import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;

/**
 * Created by xiebiao on 2015/10/27.
 */
public class ClientFilter extends BaseFilter {
  /**
   * Handle just read operation, when some message has come and ready to be processed.
   *
   * @param ctx Context of {@link FilterChainContext} processing
   * @return the next action
   * @throws java.io.IOException
   */
  @Override
  public NextAction handleRead(final FilterChainContext ctx) throws IOException {
    // We get String message from the context, because we rely prev. Filter in chain is StringFilter
    final String serverResponse = ctx.getMessage();
    System.out.println("Server echo: " + serverResponse);

    return ctx.getStopAction();
  }
}


