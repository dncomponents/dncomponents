package com.dncomponents.client.components.core.events.open;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;

public interface HasOpenHandlers<T> extends HasHandlers {
  /**
   * Adds an {@link OpenEvent} handler.
   *
   * @param handler the handler
   * @return the registration for the event
   */
  HandlerRegistration addOpenHandler(OpenHandler<T> handler);
}
