package com.dncomponents.client.views.core.ui.button;


import com.dncomponents.client.views.MainViewSlots;
import com.dncomponents.client.views.core.pcg.View;

/**
 * @author nikolasavic
 */
public interface ButtonView extends View, MainViewSlots.HasMainViewSlots {
    void setText(String text);

    void setHTML(String html);

    String getHTML();

    String getText();

    void setEnabled(boolean enabled);
}
