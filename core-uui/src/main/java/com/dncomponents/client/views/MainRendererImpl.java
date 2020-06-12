package com.dncomponents.client.views;

public class MainRendererImpl<T> implements MainRenderer<T> {

    @Override
    public void render(T t, MainViewSlots slots) {
        slots.getMainSlot().innerHTML = t + "";
    }
}
