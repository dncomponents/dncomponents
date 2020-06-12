package com.dncomponents.client.components.core.events.close;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasCloseHandlers<T> extends HasHandlers {
  /**
   * Adds a {@link CloseEvent} handler.
   * 
   * @param handler the handler
   * @return the registration for the event
   */
  HandlerRegistration addCloseHandler(CloseHandler<T> handler);
}
