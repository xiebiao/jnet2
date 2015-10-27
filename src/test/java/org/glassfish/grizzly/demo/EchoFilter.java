package org.glassfish.grizzly.demo;

import java.io.IOException;

import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;

/**
 * Created by xiebiao on 2015/10/27.
 */
public class EchoFilter extends BaseFilter {
  /**
   * Handle just read operation, when some message has come and ready to be processed.
   *
   * @param ctx Context of {@link FilterChainContext} processing
   * @return the next action
   * @throws java.io.IOException
   */
  @Override
  public NextAction handleRead(FilterChainContext ctx) throws IOException {
    // Peer address is used for non-connected UDP Connection :)
    final Object peerAddress = ctx.getAddress();

    final Object message = ctx.getMessage();

    ctx.write(peerAddress, message, null);

    return ctx.getStopAction();
  }
}


