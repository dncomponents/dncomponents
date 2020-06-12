package com.dncomponents.bootstrap.client;

import com.dncomponents.UiField;
import com.dncomponents.bootstrap.client.accordion.AccordionUiImpl;
import com.dncomponents.bootstrap.client.accordion.AccordionViewFactory;
import com.dncomponents.bootstrap.client.autocomplete.list.AutocompleteViewFactory;
import com.dncomponents.bootstrap.client.autocomplete.list.AutocompleteViewImpl;
import com.dncomponents.bootstrap.client.autocomplete.multiselect.AutocompleteListOrTreeMultiSelectViewImpl;
import com.dncomponents.bootstrap.client.autocomplete.tree.AutocompleteTreeViewImpl;
import com.dncomponents.bootstrap.client.button.ButtonViewFactory;
import com.dncomponents.bootstrap.client.button.ButtonViewImpl;
import com.dncomponents.bootstrap.client.checkbox.CheckBoxViewFactory;
import com.dncomponents.bootstrap.client.checkbox.CheckBoxViewImpl;
import com.dncomponents.bootstrap.client.dropdown.DropDownUiImpl;
import com.dncomponents.bootstrap.client.dropdown.DropdownViewFactory;
import com.dncomponents.bootstrap.client.list.ListDataViewFactory;
import com.dncomponents.bootstrap.client.list.ListUiImpl;
import com.dncomponents.bootstrap.client.dialog.DialogViewFactory;
import com.dncomponents.bootstrap.client.dialog.DialogViewImpl;
import com.dncomponents.bootstrap.client.multilevel.DropDownMultiLevelUiImpl;
import com.dncomponents.bootstrap.client.multilevel.DropDownMultiLevelViewFactory;
import com.dncomponents.bootstrap.client.pager.PagerListUiImpl;
import com.dncomponents.bootstrap.client.pager.PagerUiImpl;
import com.dncomponents.bootstrap.client.popover.PopoverViewFactory;
import com.dncomponents.bootstrap.client.popover.PopoverViewImpl;
import com.dncomponents.bootstrap.client.progress.ProgressViewFactory;
import com.dncomponents.bootstrap.client.progress.ProgressViewImpl;
import com.dncomponents.bootstrap.client.radio.RadioViewFactory;
import com.dncomponents.bootstrap.client.radio.RadioViewImpl;
import com.dncomponents.bootstrap.client.sidemenu.SideMenuViewFactory;
import com.dncomponents.bootstrap.client.sidemenu.SideMenuViewImpl;
import com.dncomponents.bootstrap.client.tab.TabUiImpl;
import com.dncomponents.bootstrap.client.tab.TabViewFactory;
import com.dncomponents.bootstrap.client.table.TableUiImpl;
import com.dncomponents.bootstrap.client.table.TableViewFactory;
import com.dncomponents.bootstrap.client.table.header.filter.FilterPanelViewImplList;
import com.dncomponents.bootstrap.client.tabletree.TableTreeUiImpl;
import com.dncomponents.bootstrap.client.tabletree.TableTreeViewFactory;
import com.dncomponents.bootstrap.client.textarea.TextAreaViewFactory;
import com.dncomponents.bootstrap.client.textarea.TextAreaViewImpl;
import com.dncomponents.bootstrap.client.textbox.TextBoxViewFactory;
import com.dncomponents.bootstrap.client.textbox.TextBoxViewImpl;
import com.dncomponents.bootstrap.client.tooltip.ToolTipViewFactory;
import com.dncomponents.bootstrap.client.tooltip.TooltipViewImpl;
import com.dncomponents.bootstrap.client.tree.TreeUiImpl;
import com.dncomponents.bootstrap.client.tree.TreeViewFactory;
import com.dncomponents.client.components.ListData;
import com.dncomponents.client.components.Table;
import com.dncomponents.client.components.TableTree;
import com.dncomponents.client.components.Tree;
import com.dncomponents.client.components.accordion.Accordion;
import com.dncomponents.client.components.autocomplete.Autocomplete;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.checkbox.Radio;
import com.dncomponents.client.components.core.BootstrapTemplates;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.dropdown.DropDown;
import com.dncomponents.client.components.modal.Dialog;
import com.dncomponents.client.components.multi.DropDownMultiLevel;
import com.dncomponents.client.components.popover.Popover;
import com.dncomponents.client.components.progress.Progress;
import com.dncomponents.client.components.sidemenu.SideMenu;
import com.dncomponents.client.components.tab.Tab;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.components.textbox.TextBox;
import com.dncomponents.client.components.tooltip.Tooltip;
import com.dncomponents.client.views.ComponentsViews;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.MultiMap;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.IconRenderer;
import com.dncomponents.client.views.core.ui.accordion.AccordionUi;
import com.dncomponents.client.views.core.ui.autocomplete.AutocompleteTreeView;
import com.dncomponents.client.views.core.ui.autocomplete.AutocompleteView;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectView;
import com.dncomponents.client.views.core.ui.button.ButtonView;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxView;
import com.dncomponents.client.views.core.ui.dropdown.DropDownMultiLevelUi;
import com.dncomponents.client.views.core.ui.dropdown.DropDownUi;
import com.dncomponents.client.views.core.ui.list.ListUi;
import com.dncomponents.client.views.core.ui.modal.DialogView;
import com.dncomponents.client.views.core.ui.pager.PagerListUi;
import com.dncomponents.client.views.core.ui.pager.PagerUi;
import com.dncomponents.client.views.core.ui.popover.PopoverView;
import com.dncomponents.client.views.core.ui.progress.ProgressView;
import com.dncomponents.client.views.core.ui.radio.RadioView;
import com.dncomponents.client.views.core.ui.sidemenu.SideMenuView;
import com.dncomponents.client.views.core.ui.tab.TabUi;
import com.dncomponents.client.views.core.ui.table.TableUi;
import com.dncomponents.client.views.core.ui.table.TableView;
import com.dncomponents.client.views.core.ui.table.headers.FilterPanelListView;
import com.dncomponents.client.views.core.ui.tabletree.TableTreeUi;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import com.dncomponents.client.views.core.ui.tooltip.TooltipView;
import com.dncomponents.client.views.core.ui.tree.TreeUi;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
//@UiTemplate("#bootstrap-4-default-theme")
public class BootstrapUi implements ComponentsViews {
    {
        BootstrapTemplates.register();
    }

    @UiField
    public HTMLTemplateElement dropDownUi;
     @UiField
    public HTMLTemplateElement accordionUi;
    @UiField
    public HTMLTemplateElement button;
    @UiField
    public HTMLTemplateElement buttonIcon;
    @UiField
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
    @UiField
    public HTMLTemplateElement progressBuilder;
    //cell components
    @UiField
    public HTMLTemplateElement tree;
    @UiField
    public HTMLTemplateElement tableUi;
    @UiField
    public HTMLTemplateElement list;
    @UiField
    public HTMLTemplateElement dropDownMultiLevelUi;
    @UiField
    public HTMLTemplateElement autocomplete;
    @UiField
    public HTMLTemplateElement autocompleteTree;
    @UiField
    public HTMLTemplateElement autocompleteMultiSelect;
    @UiField
    public HTMLTemplateElement treeCellIconView;
    @UiField
    public HTMLTemplateElement treeCellParentIconView;
    @UiField
    public HTMLTemplateElement filterPanelList;
    @UiField
    public HTMLTemplateElement sidemenu;
    @UiField
    public HTMLTemplateElement pagerUi;
    @UiField
    public HTMLTemplateElement pagerListUi;


    protected MultiMap<Class, ViewFactory> registeredViewFactoriesList = new MultiMap<>();


    public MultiMap<Class, ViewFactory> getRegisteredViewFactoriesList() {
        return registeredViewFactoriesList;
    }

    public void registerViewFactory(Class clazz, ViewFactory... viewFactories) {
        for (ViewFactory viewFactory : viewFactories)
            registeredViewFactoriesList.put(clazz, viewFactory);
    }

    public static class RegViews { //used for plugin
        public MultiMap<Class, ViewFactory> registeredViewFactoriesList = new MultiMap<>();

        public void registerViewFactory(Class clazz, ViewFactory... viewFactories) {
            for (ViewFactory viewFactory : viewFactories)
                registeredViewFactoriesList.put(clazz, viewFactory);
        }

        public RegViews() {
            registerViewFactory(Accordion.class, AccordionViewFactory.DefaultAccordionViewFactory.getInstance());
            registerViewFactory(Autocomplete.class, AutocompleteViewFactory.DefaultAutocompleteViewFactory.getInstance());
            registerViewFactory(Button.class, ButtonViewFactory.DefaultButtonIconViewFactory.getInstance());
            registerViewFactory(Button.class, ButtonViewFactory.DefaultButtonViewFactory.getInstance());
            registerViewFactory(CheckBox.class, CheckBoxViewFactory.DefaultCheckBoxViewFactory.getInstance());
            registerViewFactory(CheckBox.class, CheckBoxViewFactory.DefaultSimpleCheckBoxViewFactory.getInstance());
            registerViewFactory(Dialog.class, DialogViewFactory.DefaultDialogViewFactory.getInstance());
            registerViewFactory(DropDown.class, DropdownViewFactory.DefaultDropdownViewFactory.getInstance());
            registerViewFactory(DropDownMultiLevel.class, DropDownMultiLevelViewFactory.DefaultDropDownMultiLevelViewFactory.getInstance());
            registerViewFactory(ListData.class, ListDataViewFactory.DefaultListDataViewFactory.getInstance());
            registerViewFactory(Popover.class, PopoverViewFactory.DefaultPopoverViewFactory.getInstance());
            registerViewFactory(Progress.class, ProgressViewFactory.DefaultProgressViewFactory.getInstance());
            registerViewFactory(Radio.class, RadioViewFactory.DefaultRadioViewFactory.getInstance());
            registerViewFactory(SideMenu.class, SideMenuViewFactory.DefaultSideMenuViewFactory.getInstance());
            registerViewFactory(Tab.class, TabViewFactory.DefaultTabViewFactory.getInstance());
            registerViewFactory(Table.class, TableViewFactory.DefaultTableViewFactory.getInstance());
            registerViewFactory(TableTree.class, TableTreeViewFactory.DefaultTableTreeViewFactory.getInstance());
            registerViewFactory(TextArea.class, TextAreaViewFactory.DefaultTextAreaViewFactory.getInstance());
            registerViewFactory(TextBox.class, TextBoxViewFactory.DefaultTextBoxViewFactory.getInstance());
            registerViewFactory(Tooltip.class, ToolTipViewFactory.DefaultToolTipViewFactory.getInstance());
            registerViewFactory(Tree.class, TreeViewFactory.DefaultTreeViewFactory.getInstance());
        }
    }


    public BootstrapUi(HTMLTemplateElement templateElement) {
        HtmlBinder uiBinder = HtmlBinder.get(BootstrapUi.class, this);
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        registeredViewFactoriesList = new RegViews().registeredViewFactoriesList;
    }

    public BootstrapUi() {
        HtmlBinder uiBinder = HtmlBinder.get(BootstrapUi.class, this);
        uiBinder.bind();
        registeredViewFactoriesList = new RegViews().registeredViewFactoriesList;
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
    public DialogView getModalDialogView() {
        return new DialogViewImpl(modalDialog);
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

    @Override
    public ListUi getListUi() {
        return new ListUiImpl(list);
    }

    @Override
    public <T> AutocompleteView<T> getAutocompleteView() {
        return new AutocompleteViewImpl(autocomplete);
    }

    @Override
    public <T> AutocompleteMultiSelectView<T> getAutocompleteMultiSelectView(boolean tree) {
        return AutocompleteListOrTreeMultiSelectViewImpl.getInstance(autocompleteMultiSelect, tree);
    }

     @Override
    public AutocompleteTreeView getAutocompleteTreeView() {
        return new AutocompleteTreeViewImpl(autocompleteTree);
    }

    @Override
    public TreeUi getTreeUi() {
        return new TreeUiImpl(tree);
    }


    @Override
    public TableUi getTableUi() {
        return new TableUiImpl(tableUi);
    }

    @Override
    public SideMenuView getSideMenuView() {
        return new SideMenuViewImpl(sidemenu);
    }

    @Override
    public FilterPanelListView getFilterPanelListView() {
        return new FilterPanelViewImplList(filterPanelList);
    }

    @Override
    public TableTreeUi getTableTreeUi() {
        return new TableTreeUiImpl();
    }

    @Override
    public TableTreeUi getTreeGroupBy(TableView rootView) {
        return new TableTreeUiImpl() {
            @Override
            public TableView getRootView() {
                return rootView;
            }
        };
    }

    @Override
    public DropDownMultiLevelUi getDropDownMultiLevelUi() {
        return new DropDownMultiLevelUiImpl(dropDownMultiLevelUi);
    }

    @Override
    public PagerUi getPagerUi() {
        return new PagerUiImpl(pagerUi);
    }

    @Override
    public PagerListUi getPagerListUi() {
        return new PagerListUiImpl(pagerListUi);
    }

}