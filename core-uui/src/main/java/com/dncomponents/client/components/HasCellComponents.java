package com.dncomponents.client.components;

/**
 * @author nikolasavic
 */
public interface HasCellComponents {
    void resetScrollPosition();

    static void resetAll(AbstractCellComponent... all) {
        for (AbstractCellComponent h : all) {
            h.resetScrollPosition();
        }
    }
}
