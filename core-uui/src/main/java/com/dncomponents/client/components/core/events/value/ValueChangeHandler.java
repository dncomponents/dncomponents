package com.dncomponents.client.components.core.events.value;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface ValueChangeHandler<T> extends BaseEventListener {


  void onValueChange(ValueChangeEvent<T> event);

  String TYPE = "valueChangeEvent";

  @Override
  default void handleEvent(Event evt) {
    onValueChange(Js.cast(((CustomEvent) evt).detail));
  }

  @Override
  default String getType() {
    return TYPE;
  }

}