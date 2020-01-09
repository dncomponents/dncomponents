package com.dncomponents.material.client;

import com.dncomponents.UiField;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.MaterialTemplates;
import com.dncomponents.client.components.progress.Progress;
import com.dncomponents.client.components.tab.Tab;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.components.textbox.TextBox;
import com.dncomponents.client.views.ComponentsViews;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.MultiMap;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.IconRenderer;
import com.dncomponents.client.views.core.ui.accordion.AccordionUi;
import com.dncomponents.client.views.core.ui.button.ButtonView;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxView;
import com.dncomponents.client.views.core.ui.dropdown.DropDownUi;
import com.dncomponents.client.views.core.ui.modal.ModalDialogView;
import com.dncomponents.client.views.core.ui.popover.PopoverView;
import com.dncomponents.client.views.core.ui.progress.ProgressView;
import com.dncomponents.client.views.core.ui.radio.RadioView;
import com.dncomponents.client.views.core.ui.tab.TabUi;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import com.dncomponents.client.views.core.ui.tooltip.TooltipView;
import com.dncomponents.material.client.accordion.AccordionUiImpl;
import com.dncomponents.material.client.button.ButtonViewFactory;
import com.dncomponents.material.client.button.MdcButtonViewImpl;
import com.dncomponents.material.client.checkbox.CheckBoxViewFactory;
import com.dncomponents.material.client.checkbox.MdcCheckBoxViewImpl;
import com.dncomponents.material.client.dropdown.DropDownUiImpl;
import com.dncomponents.material.client.modal.ModalDialogViewImpl;
import com.dncomponents.material.client.popover.PopoverViewImpl;
import com.dncomponents.material.client.progress.ProgressViewImpl;
import com.dncomponents.material.client.radio.MdcRadioViewImpl;
import com.dncomponents.material.client.tab.MdcTabUiImpl;
import com.dncomponents.material.client.tab.TabUiFactory;
import com.dncomponents.material.client.textarea.MdcTextAreaViewImpl;
import com.dncomponents.material.client.textarea.TextAreaViewFactory;
import com.dncomponents.material.client.textbox.MdcTextBoxViewImpl;
import com.dncomponents.material.client.textbox.TextBoxViewFactory;
import com.dncomponents.material.client.tooltip.MdcTooltipViewImpl;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

import java.util.HashMap;
import java.util.Map;


public class MaterialUi implements ComponentsViews {

    {
        MaterialTemplates.register();
    }

    @UiField("ROW_HEIGHT")
    String rowHeight;
    @UiField("dropdown-ui")
    public HTMLTemplateElement dropDownUi;

    @UiField("accordion-ui")
    public HTMLTemplateElement accordionUi;
    @UiField
    public HTMLTemplateElement button;
    @UiField
    public HTMLTemplateElement textbox;
    @UiField
    public HTMLTemplateElement textarea;
    @UiField
    public HTMLTemplateElement radio;
    @UiField
    public HTMLTemplateElement checkbox;
    @UiField
    public HTMLTemplateElement simplecheckbox;
    @UiField
    public HTMLTemplateElement sidemenu;
    @UiField
    public HTMLTemplateElement popover;
    @UiField
    public HTMLTemplateElement tooltip;
    @UiField
    public HTMLTemplateElement modalDialog;
    @UiField
    public HTMLTemplateElement tabUi;
    @UiField
    public HTMLTemplateElement progress;
    @UiField("progress-builder")
    public HTMLTemplateElement progressBuilder;
    @UiField
    public HTMLTemplateElement treeCellIconView;
    @UiField
    public HTMLTemplateElement treeCellParentIconView;

    /// material cust
    @UiField("button-builder")
    public HTMLTemplateElement buttonBuilder;
    @UiField("textbox-builder")
    public HTMLTemplateElement textBoxBuilder;
    @UiField
    public HTMLTemplateElement helperText;
    @UiField
    public HTMLTemplateElement textboxOutLined;
    @UiField
    public HTMLTemplateElement listCellIconView;
    /// end material cust

    @UiField
    public HTMLTemplateElement filterPanelList;


    public static MultiMap<Class, ViewFactory> registeredViewFactoriesList = new MultiMap<>();
    public static Map<String, ViewFactory> registeredViewFactoriesMap = new HashMap<>();

    public static void registerStaticViewFactory(Class clazz, ViewFactory... viewFactories) {
        for (ViewFactory viewFactory : viewFactories)
            registeredViewFactoriesList.put(clazz, viewFactory);
    }

    public MultiMap<Class, ViewFactory> getRegisteredViewFactoriesList() {
        return registeredViewFactoriesList;
    }

    @Override
    public Map<String, ViewFactory> getRegisteredViewFactoriesMap() {
        return registeredViewFactoriesMap;
    }


    public void registerViewFactory(Class clazz, ViewFactory... viewFactories) {
        for (ViewFactory viewFactory : viewFactories)
            registeredViewFactoriesList.put(clazz, viewFactory);
    }

    void reg() {
        registerStaticViewFactory(Button.class, ButtonViewFactory.DefaultButtonViewFactory.getInstance());
        registerStaticViewFactory(Tab.class, TabUiFactory.DefaultUiFactory.getInstance());
        registerStaticViewFactory(CheckBox.class, CheckBoxViewFactory.DefaultCheckBoxViewFactory.getInstance());
        registerStaticViewFactory(CheckBox.class, CheckBoxViewFactory.DefaultSimpleCheckBoxViewFactory.getInstance());
        registerStaticViewFactory(Progress.class, ProgressViewImpl.ProgressViewFactory.getInstance());
        registerStaticViewFactory(TextBox.class, TextBoxViewFactory.getInstance());
        registerStaticViewFactory(TextArea.class, TextAreaViewFactory.getInstance());

    }

    public MaterialUi() {
        HtmlBinder uiBinder = HtmlBinder.get(MaterialUi.class, this);
        uiBinder.bind();
        reg();
    }

    public MaterialUi(String template) {
        HtmlBinder uiBinder = HtmlBinder.get(MaterialUi.class, this);
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }


    private static MaterialUi instance;

    public static MaterialUi getInstance() {
        if (instance == null)
            instance = new MaterialUi();
        return instance;
    }


    @Override
    public AccordionUi getAccordionUi() {
        return new AccordionUiImpl(accordionUi);
    }

    @Override
    public ButtonView getButtonView() {
        return MdcButtonViewImpl.ButtonBuilder.get().build();
    }

    @Override
    public TextBoxView getTextBoxView() {
        return MdcTextBoxViewImpl.MdcTextBoxViewBuilder.get().build();
    }

    @Override
    public CheckBoxView getCheckBoxView() {
        return new MdcCheckBoxViewImpl(checkbox);
    }

    @Override
    public RadioView getRadioView() {
        return new MdcRadioViewImpl(radio);
    }


    @Override
    public DropDownUi getDropDownUi() {
        return new DropDownUiImpl(dropDownUi);
    }


    @Override
    public TextBoxView getTextAreaView() {
        return MdcTextAreaViewImpl.MdcTextAreaViewBuilder.get().build();
    }

    @Override
    public TooltipView getTooltipView() {
        return new MdcTooltipViewImpl(tooltip);
    }

    @Override
    public PopoverView getPopoverView() {
        return new PopoverViewImpl(popover);
    }

    @Override
    public ModalDialogView getModalDialogView() {
        return new ModalDialogViewImpl(modalDialog);
    }


    @Override
    public TabUi getTabUi() {
        return new MdcTabUiImpl(tabUi);
    }

    @Override
    public ProgressView getProgressView() {
        return new ProgressViewImpl(progress);
    }

    @Override
    public IconRenderer getIconRenderer() {
        return materialIconRenderer;
    }

    IconRenderer materialIconRenderer = new IconRenderer() {
        @Override
        public void render(HTMLElement element, String icon) {
            if (icon == null)
                element.remove();
            else
                element.innerHTML = icon;
        }
    };


    public static MaterialUi getUi() {
        return (MaterialUi) Ui.get();
    }

}