package com.dncomponents.client.components.core;

import com.dncomponents.client.components.BaseCell;
import elemental2.dom.HTMLElement;
/**
 * @author nikolasavic
 */
public class RendererContext<T, M> {
    public RendererContext(M value, HTMLElement valuePanel, BaseCell<T, M> cell) {
        this.value = value;
        this.valuePanel = valuePanel;
        this.cell = cell;
    }

    public M value;
    public HTMLElement valuePanel;
    public BaseCell<T, M> cell;
}
