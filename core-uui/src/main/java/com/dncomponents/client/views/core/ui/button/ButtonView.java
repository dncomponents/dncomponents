package com.dncomponents.client.views.core.ui.button;


import com.dncomponents.client.views.core.pcg.View;

/**
 * @author nikolasavic
 */
public interface ButtonView extends View, ButtonViewSlots.HasButtonViewSlots {
    void setText(String text);

    void setHTML(String html);

    String getHTML();

    String getText();

    void setEnabled(boolean enabled);
}
