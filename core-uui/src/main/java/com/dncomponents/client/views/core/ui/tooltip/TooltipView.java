package com.dncomponents.client.views.core.ui.tooltip;

 import com.dncomponents.client.views.HasViewSlots;
 import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface TooltipView<V extends TooltipViewSlots> extends View, HasViewSlots<V> {

    void setBottomOrientation();

    void setTopOrientation();

    void setLeftOrientation();

    void setRightOrientation();

    void calculatePositionBottom(HTMLElement owner);

    void calculatePositionTop(HTMLElement owner);

    void calculatePositionLeft(HTMLElement owner);

    void calculatePositionRight(HTMLElement owner);

    void setContent(String text);

    void setContent(HTMLElement element);


}
