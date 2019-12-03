package com.dncomponents.client.views;

import com.dncomponents.client.views.core.MultiMap;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.accordion.AccordionUi;
import com.dncomponents.client.views.core.ui.button.ButtonView;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxView;
import com.dncomponents.client.views.core.ui.dropdown.DropDownUi;
import com.dncomponents.client.views.core.ui.modal.ModalDialogView;
import com.dncomponents.client.views.core.ui.pager.PagerListUi;
import com.dncomponents.client.views.core.ui.pager.PagerUi;
import com.dncomponents.client.views.core.ui.popover.PopoverView;
import com.dncomponents.client.views.core.ui.progress.ProgressView;
import com.dncomponents.client.views.core.ui.radio.RadioView;
import com.dncomponents.client.views.core.ui.tab.TabUi;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import com.dncomponents.client.views.core.ui.tooltip.TooltipView;

import java.util.Map;


/**
 * @author nikolasavic
 */
public interface ComponentsViews {

    MultiMap<Class, ViewFactory> getRegisteredViewFactoriesList();

    Map<String, ViewFactory> getRegisteredViewFactoriesMap();

    void registerViewFactory(Class clazz, ViewFactory... viewFactories);

    AccordionUi getAccordionUi();

    ButtonView getButtonView();

    TextBoxView getTextBoxView();

    CheckBoxView getCheckBoxView();

    RadioView getRadioView();

    DropDownUi getDropDownUi();

    TextBoxView getTextAreaView();

    TooltipView getTooltipView();

    PopoverView getPopoverView();

    ModalDialogView getModalDialogView();

    TabUi getTabUi();

    ProgressView getProgressView();

}
