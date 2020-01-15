package com.dncomponents.client.components.events;

import com.dncomponents.client.components.AbstractCellComponent;
import com.dncomponents.client.components.BaseCell;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import elemental2.dom.MouseEvent;

/**
 * @author nikolasavic
 */
public abstract class AbstractCellEvent<T, H extends EventHandler> extends GwtEvent<H> {

    private BaseCell<T, ?> cell;
    private MouseEvent mouseEvent;


    public AbstractCellEvent(BaseCell<T, ?> cell) {
        this.cell = cell;
    }

    public AbstractCellEvent(BaseCell<T, ?> cell, MouseEvent mouseEvent) {
        this.cell = cell;
        this.mouseEvent = mouseEvent;
    }

    @Override
    public AbstractCellComponent<T, ?, ?> getSource() {
        return (AbstractCellComponent<T, ?, ?>) super.getSource();
    }

    public BaseCell<T, ?> getCell() {
        return cell;
    }

    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }
}