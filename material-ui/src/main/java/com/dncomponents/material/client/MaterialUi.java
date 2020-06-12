package com.dncomponents.material.client;

import com.dncomponents.UiField;
import com.dncomponents.client.components.ListData;
import com.dncomponents.client.components.Table;
import com.dncomponents.client.components.TableTree;
import com.dncomponents.client.components.Tree;
import com.dncomponents.client.components.accordion.Accordion;
import com.dncomponents.client.components.autocomplete.Autocomplete;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.checkbox.Radio;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.MaterialTemplates;
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
import com.dncomponents.material.client.accordion.AccordionUiImpl;
import com.dncomponents.material.client.accordion.AccordionViewFactory;
import com.dncomponents.material.client.autocomplete.list.AutocompleteViewFactory;
import com.dncomponents.material.client.autocomplete.list.AutocompleteViewImpl;
import com.dncomponents.material.client.autocomplete.multiselect.AutocompleteListOrTreeMultiSelectViewImpl;
import com.dncomponents.material.client.autocomplete.tree.AutocompleteTreeViewImpl;
import com.dncomponents.material.client.button.ButtonViewFactory;
import com.dncomponents.material.client.button.ButtonViewImpl;
import com.dncomponents.material.client.checkbox.CheckBoxViewFactory;
import com.dncomponents.material.client.checkbox.CheckBoxViewImpl;
import com.dncomponents.material.client.dialog.DialogViewFactory;
import com.dncomponents.material.client.dialog.DialogViewImpl;
import com.dncomponents.material.client.dropdown.DropDownUiImpl;
import com.dncomponents.material.client.dropdown.DropdownViewFactory;
import com.dncomponents.material.client.list.ListDataViewFactory;
import com.dncomponents.material.client.list.ListUiImpl;
import com.dncomponents.material.client.multilevel.DropDownMultiLevelUiImpl;
import com.dncomponents.material.client.multilevel.DropDownMultiLevelViewFactory;
import com.dncomponents.material.client.pager.PagerListUiImpl;
import com.dncomponents.material.client.pager.PagerUiImpl;
import com.dncomponents.material.client.popover.PopoverViewFactory;
import com.dncomponents.material.client.popover.PopoverViewImpl;
import com.dncomponents.material.client.progress.ProgressViewFactory;
import com.dncomponents.material.client.progress.ProgressViewImpl;
import com.dncomponents.material.client.radio.RadioViewFactory;
import com.dncomponents.material.client.radio.RadioViewImpl;
import com.dncomponents.material.client.sidemenu.SideMenuViewFactory;
import com.dncomponents.material.client.sidemenu.SideMenuViewImpl;
import com.dncomponents.material.client.tab.TabUiFactory;
import com.dncomponents.material.client.tab.TabUiImpl;
import com.dncomponents.material.client.table.TableUiImpl;
import com.dncomponents.material.client.table.TableViewFactory;
import com.dncomponents.material.client.table.header.filter.FilterPanelViewImplList;
import com.dncomponents.material.client.tabletree.TableTreeUiImpl;
import com.dncomponents.material.client.tabletree.TableTreeViewFactory;
import com.dncomponents.material.client.textarea.TextAreaViewFactory;
import com.dncomponents.material.client.textarea.TextAreaViewImpl;
import com.dncomponents.material.client.textbox.TextBoxViewFactory;
import com.dncomponents.material.client.textbox.TextBoxViewImpl;
import com.dncomponents.material.client.tooltip.ToolTipViewFactory;
import com.dncomponents.material.client.tooltip.TooltipViewImpl;
import com.dncomponents.material.client.tree.TreeUiImpl;
import com.dncomponents.material.client.tree.TreeViewFactory;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

public class MaterialUi implements ComponentsViews {

    {
        MaterialTemplates.register();
    }

    @UiField
    String rowHeight;
    @UiField
    public HTMLTemplateElement dropDownUi;
    @UiField
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
    @UiField
    public HTMLTemplateElement treeCellIconView;
    @UiField
    public HTMLTemplateElement treeCellParentIconView;
    @UiField
    public HTMLTemplateElement buttonBuilder;
    @UiField
    public HTMLTemplateElement textBoxBuilder;
    @UiField
    public HTMLTemplateElement helperText;
    @UiField
    public HTMLTemplateElement textboxOutLined;
    @UiField
    public HTMLTemplateElement listCellIconView;
    @UiField
    public HTMLTemplateElement filterPanelList;
    @UiField
    public HTMLTemplateElement list;
    @UiField
    public HTMLTemplateElement tree;
    @UiField
    public HTMLTemplateElement tableUi;
    @UiField
    public HTMLTemplateElement dropDownMultiLevelUi;
    @UiField
    public HTMLTemplateElement autocomplete;
    @UiField
    public HTMLTemplateElement autocompleteTree;
    @UiField
    public HTMLTemplateElement autocompleteMultiSelect;
    @UiField
    public HTMLTemplateElement pagerUi;
    @UiField
    public HTMLTemplateElement pagerListUi;
    @UiField
    public HTMLTemplateElement sidemenu;

    public static MultiMap<Class, ViewFactory> registeredViewFactoriesList = new MultiMap<>();

    public MultiMap<Class, ViewFactory> getRegisteredViewFactoriesList() {
        return registeredViewFactoriesList;
    }

    public void registerViewFactory(Class clazz, ViewFactory... viewFactories) {
        for (ViewFactory viewFactory : viewFactories)
            registeredViewFactoriesList.put(clazz, viewFactory);
    }

    void reg() {
        registerViewFactory(Accordion.class, AccordionViewFactory.DefaultAccordionViewFactory.getInstance());
        registerViewFactory(Autocomplete.class, AutocompleteViewFactory.DefaultAutocompleteViewFactory.getInstance());
        registerViewFactory(Button.class, ButtonViewFactory.DefaultButtonViewFactory.getInstance());
        registerViewFactory(CheckBox.class, CheckBoxViewFactory.DefaultCheckBoxViewFactory.getInstance());
        registerViewFactory(CheckBox.class, CheckBoxViewFactory.DefaultSimpleCheckBoxViewFactory.getInstance());
        registerViewFactory(Dialog.class, DialogViewFactory.DefaultDialogViewFactory.getInstance());
        registerViewFactory(DropDown.class, DropdownViewFactory.DefaultDropdownViewFactory.getInstance());
        registerViewFactory(DropDownMultiLevel.class, DropDownMultiLevelViewFactory.DefaultDropDownMultiLevelViewFactory.getInstance());
        registerViewFactory(ListData.class, ListDataViewFactory.DefaultListDataViewFactory.getInstance());
        registerViewFactory(Popover.class, PopoverViewFactory.DefaultPopoverViewFactory.getInstance());
        registerViewFactory(Progress.class, ProgressViewFactory.getInstance());
        registerViewFactory(SideMenu.class, SideMenuViewFactory.DefaultSideMenuViewFactory.getInstance());
        registerViewFactory(Radio.class, RadioViewFactory.DefaultRadioViewFactory.getInstance());
        registerViewFactory(Tab.class, TabUiFactory.DefaultTabFactory.getInstance());
        registerViewFactory(Table.class, TableViewFactory.DefaultTableViewFactory.getInstance());
        registerViewFactory(TableTree.class, TableTreeViewFactory.DefaultTableTreeViewFactory.getInstance());
        registerViewFactory(TextArea.class, TextAreaViewFactory.getInstance());
        registerViewFactory(TextBox.class, TextBoxViewFactory.getInstance());
        registerViewFactory(Tooltip.class, ToolTipViewFactory.DefaultToolTipViewFactory.getInstance());
        registerViewFactory(Tree.class, TreeViewFactory.DefaultTreeViewFactory.getInstance());
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

    @Override
    public AccordionUi getAccordionUi() {
        return new AccordionUiImpl(accordionUi);
    }

    @Override
    public ButtonView getButtonView() {
        return ButtonViewImpl.ButtonBuilder.get().build();
    }

    @Override
    public TextBoxView getTextBoxView() {
        return TextBoxViewImpl.TextBoxViewBuilder.get().build();
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
    public TextBoxView getTextAreaView() {
        return TextAreaViewImpl.TextAreaViewBuilder.get().build();
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

    @Override
    public ListUi getListUi() {
        return new ListUiImpl(list);
    }

    @Override
    public <T> AutocompleteView<T> getAutocompleteView() {
        return new AutocompleteViewImpl(autocomplete);
    }


    @Override
    public AutocompleteTreeView getAutocompleteTreeView() {
        return new AutocompleteTreeViewImpl(autocompleteTree);
    }

    @Override
    public <T> AutocompleteMultiSelectView<T> getAutocompleteMultiSelectView(boolean tree) {
        return AutocompleteListOrTreeMultiSelectViewImpl.getInstance(autocompleteMultiSelect, tree);
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
    public PagerUi getPagerUi() {
        return new PagerUiImpl(pagerUi);
    }

    @Override
    public PagerListUi getPagerListUi() {
        return new PagerListUiImpl(pagerListUi);
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


    public static MaterialUi getUi() {
        return (MaterialUi) Ui.get();
    }

}