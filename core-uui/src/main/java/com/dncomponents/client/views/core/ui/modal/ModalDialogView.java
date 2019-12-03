package com.dncomponents.client.views.core.ui.modal;

import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.HasViewSlots;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.View;
import com.google.gwt.user.client.Command;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface ModalDialogView extends View, HasViewSlots<ModalDialogViewSlots> {

    void setHeader(IsElement element);

    void setContent(HTMLElement element);

    void setFooter(IsElement element);

    void setTitle(String title);

    void addCloseHandler(Command onCloseCmd);

    void show();

    void hide();

    void addFooterElement(HTMLElement element);

    void clearFooter();

    void setWidth(String width);

    void setHeight(String height);

    void setBackDrop(boolean backdrop);

    void setCloseOnEscape(boolean closeOnEscape);

    void setDraggable(boolean draggable);

    void setPosition(int top, int left);

    int getTopPosition();

    int getLeftPosition();
}
