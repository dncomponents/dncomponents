package com.dncomponents.client.views.core.pcg.cell;

import com.dncomponents.client.dom.handlers.DoubleClickHandler;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.KeyDownHandler;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface BaseCellView extends CellView {

    void addClickHandler(ClickHandler clickHandler);

    void addDoubleClickHandler(DoubleClickHandler doubleClickHandler);

    void addKeyDownHandler(KeyDownHandler keyDownHandler);

    void setErrorStyle(boolean b);

    void setValueChangedStyle(boolean b);

    void setSelected(boolean b);

    void setFocus(boolean b);

    void setToValuePanel(Element element);

    HTMLElement getValuePanel();

    void showSuccessMessage(String successText);

    void showErrorMessage(String message);
}
