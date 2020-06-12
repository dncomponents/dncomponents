package com.dncomponents.client.views;

/**
 * @author nikolasavic
 */
public interface Renderer<T, R extends ViewSlots> {
    void render(T t, R slots);
}