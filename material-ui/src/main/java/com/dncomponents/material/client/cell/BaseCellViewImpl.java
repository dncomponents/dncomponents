package com.dncomponents.material.client.cell;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.DoubleClickHandler;
import com.dncomponents.client.dom.handlers.KeyDownHandler;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public abstract class BaseCellViewImpl implements BaseCellView {

    @UiStyle
    protected String modelSelected;
    @UiStyle
    protected String cellHighlighted;
    @UiStyle
    protected String errorCell;
    @UiStyle
    protected String valueChanged;
    @UiField
    public HTMLElement valuePanel;
    @UiField
    public HTMLElement root;

    @Override
    public void addClickHandler(ClickHandler clickHandler) {
        asElement().addEventListener(clickHandler.getType(), clickHandler);
    }

    @Override
    public void addDoubleClickHandler(DoubleClickHandler doubleClickHandler) {
        doubleClickHandler.addTo(asElement());
    }

    @Override
    public void addKeyDownHandler(KeyDownHandler keyDownHandler) {
        keyDownHandler.addTo(asElement());
    }

    @Override
    public void setErrorStyle(boolean b) {
        if (b)
            asElement().classList.add(errorCell);
        else
            asElement().classList.remove(errorCell);
    }

    @Override
    public void setValueChangedStyle(boolean b) {
        if (b)
            asElement().classList.add(valueChanged);
        else
            asElement().classList.remove(valueChanged);
    }

    @Override
    public void setSelected(boolean b) {
        if (b)
            asElement().classList.add(modelSelected);
        else
            asElement().classList.remove(modelSelected);
    }

    @Override
    public void setFocus(boolean b) {
        if (b) {
            asElement().setAttribute("tabindex", 0);
            asElement().focus();
        } else {
            asElement().removeAttribute("tabindex");
            asElement().blur();
        }
    }

    public void setToValuePanel(Element element) {
        getValuePanel().innerHTML = ""; //clear
        getValuePanel().appendChild(element);
    }

    @Override
    public HTMLElement getValuePanel() {
        return valuePanel;
    }


    @Override
    public HTMLElement asElement() {
        return root;
    }
}