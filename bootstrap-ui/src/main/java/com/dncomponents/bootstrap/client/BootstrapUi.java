package com.dncomponents.bootstrap.client;

import com.dncomponents.UiField;
import com.dncomponents.bootstrap.client.accordion.AccordionUiImpl;
import com.dncomponents.bootstrap.client.button.ButtonViewFactory;
import com.dncomponents.bootstrap.client.button.ButtonViewImpl;
import com.dncomponents.bootstrap.client.checkbox.CheckBoxViewFactory;
import com.dncomponents.bootstrap.client.checkbox.CheckBoxViewImpl;
import com.dncomponents.bootstrap.client.dropdown.DropDownUiImpl;
import com.dncomponents.bootstrap.client.modal.ModalDialogViewImpl;
import com.dncomponents.bootstrap.client.popover.PopoverViewImpl;
import com.dncomponents.bootstrap.client.progress.ProgressViewImpl;
import com.dncomponents.bootstrap.client.radio.RadioViewImpl;
import com.dncomponents.bootstrap.client.tab.TabUiFactory;
import com.dncomponents.bootstrap.client.tab.TabUiImpl;
import com.dncomponents.bootstrap.client.textarea.TextAreaViewImpl;
import com.dncomponents.bootstrap.client.textbox.TextBoxViewImpl;
import com.dncomponents.bootstrap.client.tooltip.TooltipViewImpl;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.BootstrapTemplates;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.progress.Progress;
import com.dncomponents.client.components.tab.Tab;
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
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nikolasavic
 */
//@UiTemplate("#bootstrap-4-default-theme")
public class BootstrapUi implements ComponentsViews {
    {
        BootstrapTemplates.register();
    }

    @UiField("dropdown-ui")
    public HTMLTemplateElement dropDownUi;
    @UiField("accordion-ui")
    public HTMLTemplateElement accordionUi;
    @UiField
    public HTMLTemplateElement button;
    @UiField
    public HTMLTemplateElement buttonIcon;
    @UiField("button-builder")
    public HTMLTemplateElement buttonBuilder;
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

    protected MultiMap<Class, ViewFactory> registeredViewFactoriesList = new MultiMap<>();

    protected Map<String, ViewFactory> registeredViewFactoriesMap = new HashMap<>();

    public MultiMap<Class, ViewFactory> getRegisteredViewFactoriesList() {
        return registeredViewFactoriesList;
    }

    public void registerViewFactory(Class clazz, ViewFactory... viewFactories) {
        for (ViewFactory viewFactory : viewFactories)
            registeredViewFactoriesList.put(clazz, viewFactory);
    }

    public void registerStaticViewFactory(Class clazz, ViewFactory... viewFactories) {
        for (ViewFactory viewFactory : viewFactories)
            registeredViewFactoriesList.put(clazz, viewFactory);
    }

    public Map<String, ViewFactory> getRegisteredViewFactoriesMap() {
        return registeredViewFactoriesMap;
    }

    private void registerViews() {
        registerStaticViewFactory(Button.class, ButtonViewFactory.DefaultButtonIconViewFactory.getInstance());
        registerStaticViewFactory(Button.class, ButtonViewFactory.DefaultButtonViewFactory.getInstance());
        registerStaticViewFactory(Tab.class, TabUiFactory.DefaultUiFactory.getInstance());
        registerStaticViewFactory(CheckBox.class, CheckBoxViewFactory.DefaultCheckBoxViewFactory.getInstance());
        registerStaticViewFactory(CheckBox.class, CheckBoxViewFactory.DefaultSimpleCheckBoxViewFactory.getInstance());
        registerStaticViewFactory(Progress.class, ProgressViewImpl.ProgressViewFactory.getInstance());
    }

    public BootstrapUi(HTMLTemplateElement templateElement) {
        HtmlBinder uiBinder = HtmlBinder.get(BootstrapUi.class, this);
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        registerViews();
    }

    public BootstrapUi() {
        HtmlBinder uiBinder = HtmlBinder.get(BootstrapUi.class, this);
        uiBinder.bind();
        registerViews();
    }

    @Override
    public AccordionUi getAccordionUi() {
        return new AccordionUiImpl(accordionUi);
    }

    @Override
    public ButtonView getButtonView() {
        return new ButtonViewImpl(button);
    }

    @Override
    public TextBoxView getTextBoxView() {
        return new TextBoxViewImpl(textbox);
    }

    @Override
    public TextBoxView getTextAreaView() {
        return new TextAreaViewImpl(textarea);
    }

    @Override
    public CheckBoxView getCheckBoxView() {
        return new CheckBoxViewImpl(checkbox);
    }

    @Override
    public RadioView getRadioView() {
        return new RadioViewImpl(radio);
    }

    @Override
    public DropDownUi getDropDownUi() {
        return new DropDownUiImpl(dropDownUi);
    }


    @Override
    public TooltipView getTooltipView() {
        return new TooltipViewImpl(tooltip);
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
        return new TabUiImpl(tabUi);
    }


    @Override
    public ProgressView getProgressView() {
        return new ProgressViewImpl(progress);
    }

    @Override
    public IconRenderer getIconRenderer() {
        return fontAwesomeIconRenderer;
    }

    IconRenderer fontAwesomeIconRenderer = new IconRenderer() {
        @Override
        public void render(HTMLElement element, String icon) {
            if (element != null)
                element.className = icon;
        }
    };

    public static <S extends BootstrapUi> S getUi() {
        return (S) Ui.get();
    }

    //for plugins

    protected static MultiMap<Class, ViewFactory> registeredViewFactoriesListS = new MultiMap<>();

    protected static Map<String, ViewFactory> registeredViewFactoriesMapS = new HashMap<>();

    public static void registerStaticViewFactoryS(Class clazz, ViewFactory... viewFactories) {
        for (ViewFactory viewFactory : viewFactories)
            registeredViewFactoriesListS.put(clazz, viewFactory);
    }

    static {
        registerStaticViewFactoryS(Button.class, ButtonViewFactory.DefaultButtonIconViewFactory.getInstance());
        registerStaticViewFactoryS(Button.class, ButtonViewFactory.DefaultButtonViewFactory.getInstance());
        registerStaticViewFactoryS(Tab.class, TabUiFactory.DefaultUiFactory.getInstance());
        registerStaticViewFactoryS(CheckBox.class, CheckBoxViewFactory.DefaultCheckBoxViewFactory.getInstance());
        registerStaticViewFactoryS(CheckBox.class, CheckBoxViewFactory.DefaultSimpleCheckBoxViewFactory.getInstance());
        registerStaticViewFactoryS(Progress.class, ProgressViewImpl.ProgressViewFactory.getInstance());
    }

    public static MultiMap<Class, ViewFactory> getRegisteredViewFactoriesListS() {
        return registeredViewFactoriesListS;
    }

    //end for plugins

}