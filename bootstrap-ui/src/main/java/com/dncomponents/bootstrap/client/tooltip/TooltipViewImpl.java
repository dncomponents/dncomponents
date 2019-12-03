package com.dncomponents.bootstrap.client.tooltip;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.tooltip.TooltipView;
import com.dncomponents.client.views.core.ui.tooltip.TooltipViewSlots;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class TooltipViewImpl<V extends TooltipViewSlots> implements TooltipView<V> {

    @UiField
    HTMLElement root;
    @UiField
    protected HTMLElement contentPanel;
    @UiField
    HTMLElement arrowPanel;
    @UiStyle
    String topStyle;
    @UiStyle
    String bottomStyle;
    @UiStyle
    String leftStyle;
    @UiStyle
    String rightStyle;
    @UiStyle
    String baseStyle;
    @UiStyle
    String fadeStyle;
    @UiStyle
    String showStyle;

    HtmlBinder uiBinder = HtmlBinder.get(TooltipViewImpl.class, this);

    public TooltipViewImpl() {
    }

    public TooltipViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    public void setBottomOrientation() {
        asElement().className = (baseStyle + " " + fadeStyle + " " + bottomStyle + " " + showStyle);
    }

    @Override
    public void setTopOrientation() {
        asElement().className = (baseStyle + " " + fadeStyle + " " + topStyle + " " + showStyle);
    }

    @Override
    public void setLeftOrientation() {
        asElement().className = (baseStyle + " " + fadeStyle + " " + leftStyle + " " + showStyle);
    }

    @Override
    public void setRightOrientation() {
        asElement().className = (baseStyle + " " + fadeStyle + " " + rightStyle + " " + showStyle);
    }

    @Override
    public void calculatePositionBottom(HTMLElement owner) {
        setLeftArrowPosition(asElement().offsetWidth / 2.0);
    }

    @Override
    public void calculatePositionTop(HTMLElement owner) {
        setLeftArrowPosition(asElement().offsetWidth / 2.0);
    }

    @Override
    public void calculatePositionLeft(HTMLElement owner) {
        setTopArrowPosition((asElement().offsetHeight / 2.0));
    }

    @Override
    public void calculatePositionRight(HTMLElement owner) {
        setTopArrowPosition(asElement().offsetHeight / 2.0);
    }

    private double scrollY() {
        return DomGlobal.window.scrollY;
    }

    @Override
    public void setContent(String text) {
        contentPanel.textContent = text;
    }

    @Override
    public void setContent(HTMLElement element) {
        contentPanel.appendChild(element);
    }

    private void setLeftArrowPosition(double n) {
        n -= arrowPanel.offsetWidth / 2;
        arrowPanel.style.left = n + "px";
    }

    private void setTopArrowPosition(double n) {
        n -= arrowPanel.offsetHeight / 2.0;
        arrowPanel.style.top = n + "px";
    }


    @Override
    public HTMLElement asElement() {
        return root;
    }

    TooltipViewSlots slots = new TooltipViewSlots() {
        @Override
        public HTMLElement getContent() {
            return contentPanel;
        }
    };

    @Override
    public V getViewSlots() {
        return (V) slots;
    }
}
