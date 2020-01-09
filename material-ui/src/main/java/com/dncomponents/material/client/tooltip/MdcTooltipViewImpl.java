package com.dncomponents.material.client.tooltip;

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
public class MdcTooltipViewImpl<V extends TooltipViewSlots> implements TooltipView<V> {

    @UiField
    HTMLElement root;
    @UiField
    protected HTMLElement contentPanel;
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

    HtmlBinder uiBinder = HtmlBinder.get(MdcTooltipViewImpl.class, this);

    public MdcTooltipViewImpl() {
    }

    public MdcTooltipViewImpl(HTMLTemplateElement templateElement) {
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
        double top = owner.getBoundingClientRect().bottom + scrollY();
        double left = (owner.getBoundingClientRect().left - asElement().offsetWidth / 2 + owner.offsetWidth / 2);
        setPosition(top, left);
    }

    @Override
    public void calculatePositionTop(HTMLElement owner) {
        double top = owner.getBoundingClientRect().top + scrollY() - asElement().offsetHeight;
        double left = owner.getBoundingClientRect().left - asElement().offsetWidth / 2 + owner.offsetWidth / 2;
        setPosition(top, left);
    }

    @Override
    public void calculatePositionLeft(HTMLElement owner) {
        double top = owner.getBoundingClientRect().top + scrollY() - asElement().offsetHeight / 2 + owner.offsetHeight / 2;
        double left = (int) owner.getBoundingClientRect().left - asElement().offsetWidth;
        setPosition(top, left);
    }

    @Override
    public void calculatePositionRight(HTMLElement owner) {
        double top = owner.getBoundingClientRect().top + scrollY() - asElement().offsetHeight / 2 + owner.offsetHeight / 2;
        double left = owner.getBoundingClientRect().right;
        setPosition(top, left);
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


    private void setPosition(double top, double left) {
        asElement().style.top = top + "px";
        asElement().style.left = left + "px";
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
