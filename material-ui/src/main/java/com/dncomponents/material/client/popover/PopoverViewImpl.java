package com.dncomponents.material.client.popover;

import com.dncomponents.UiField;
import com.dncomponents.Component;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.popover.PopoverView;
import com.dncomponents.client.views.core.ui.popover.PopoverViewSlots;
import com.dncomponents.material.client.tooltip.TooltipViewImpl;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@Component
public class PopoverViewImpl extends TooltipViewImpl<PopoverViewSlots> implements PopoverView {

    public static final String VIEW_ID = "default";
    @UiField
    HTMLElement popoverTitle;

    HtmlBinder uiBinder = HtmlBinder.create(PopoverViewImpl.class, this);

    public PopoverViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void setPopoverTitle(String title) {
        popoverTitle.innerHTML = title;
    }

    PopoverViewSlots viewSlots = new PopoverViewSlots() {
        @Override
        public HTMLElement getTitle() {
            return popoverTitle;
        }

        @Override
        public HTMLElement getContent() {
            return contentPanel;
        }
    };

    @Override
    public PopoverViewSlots getViewSlots() {
        return viewSlots;
    }
}
