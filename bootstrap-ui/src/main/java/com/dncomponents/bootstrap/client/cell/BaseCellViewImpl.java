package com.dncomponents.bootstrap.client.cell;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.DoubleClickHandler;
import com.dncomponents.client.dom.handlers.KeyDownHandler;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import elemental2.dom.Element;
import elemental2.dom.HTMLDivElement;
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


    private HTMLDivElement successDiv = DomUtil.createDiv();
    private HTMLDivElement errorDiv = DomUtil.createDiv();

    @Override
    public void showSuccessMessage(String successText) {
        errorDiv.remove();
        if (successText == null)
            successDiv.remove();
        else {
            successDiv.style.position = "absolute";
            successDiv.style.set("width","100%");
            successDiv.innerHTML = "<div class=\"valid-tooltip d-block\">" + successText + "</div>";
            getValuePanel().appendChild(successDiv);
        }
    }

    @Override
    public void showErrorMessage(String message) {
        successDiv.remove();
        if (message == null)
            errorDiv.remove();
        else {
            errorDiv.style.position = "absolute";
            errorDiv.style.set("width","100%");
            errorDiv.innerHTML = "<div class=\"invalid-tooltip  d-block\">" + message + "</div>";
            getValuePanel().appendChild(errorDiv);
        }
    }


    @Override
    public HTMLElement asElement() {
        return root;
    }


}
