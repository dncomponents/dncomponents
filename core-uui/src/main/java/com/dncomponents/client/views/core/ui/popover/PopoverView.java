package com.dncomponents.client.views.core.ui.popover;

import com.dncomponents.client.views.core.ui.tooltip.TooltipView;

/**
 * @author nikolasavic
 */
public interface PopoverView extends TooltipView<PopoverViewSlots> {
    void setPopoverTitle(String title);
}
