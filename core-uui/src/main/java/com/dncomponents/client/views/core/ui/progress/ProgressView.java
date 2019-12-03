package com.dncomponents.client.views.core.ui.progress;


import com.dncomponents.client.views.core.pcg.View;

/**
 * @author nikolasavic
 */
public interface ProgressView extends View {

    void setBarWidth(int percent);

    void setBarText(String text);

    void setMinimumWidth(int minimumWidth);

}