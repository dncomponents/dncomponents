package com.dncomponents.client.views.core.ui.modal;


import com.dncomponents.client.views.ViewSlots;
import elemental2.dom.HTMLElement;

public interface ModalDialogViewSlots extends ViewSlots {

    HTMLElement getHeaderPanel();

    HTMLElement getContentPanel();

    HTMLElement getFooterPanel();
}
