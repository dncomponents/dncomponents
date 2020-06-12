package com.dncomponents.client.components.core;

import com.dncomponents.client.components.ListData;
import com.dncomponents.client.components.Table;
import com.dncomponents.client.components.Tree;
import com.dncomponents.client.components.accordion.Accordion;
import com.dncomponents.client.components.autocomplete.Autocomplete;
import com.dncomponents.client.components.autocomplete.AutocompleteMultiSelect;
import com.dncomponents.client.components.autocomplete.AutocompleteTree;
import com.dncomponents.client.components.autocomplete.AutocompleteTreeMultiSelect;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.checkbox.CheckBoxSelectionGroup;
import com.dncomponents.client.components.checkbox.Radio;
import com.dncomponents.client.components.checkbox.RadioSelectionGroup;
import com.dncomponents.client.components.dropdown.DropDown;
import com.dncomponents.client.components.modal.Dialog;
import com.dncomponents.client.components.multi.DropDownMultiLevel;
import com.dncomponents.client.components.popover.Popover;
import com.dncomponents.client.components.progress.Progress;
import com.dncomponents.client.components.sidemenu.SideMenu;
import com.dncomponents.client.components.tab.Tab;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.components.textbox.*;
import com.dncomponents.client.components.tooltip.Tooltip;
import elemental2.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class HtmlParserService {
    public static final List<ComponentHtmlParser> COMPONENT_HTML_PARSERS = new ArrayList<>();
    public static final List<HtmlParser> HTML_PARSERS = new ArrayList<>();
    public static final List<HtmlParser> HTML_PARSERS_BEFORE = new ArrayList<>();

    static {
        register();
    }

    public static void register() {
        COMPONENT_HTML_PARSERS.add(Accordion.AccordionHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(TextBox.TextBoxHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(TextArea.TextAreaHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(IntegerBox.IntegerBoxHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(DoubleBox.DoubleBoxHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(LongBox.LongBoxHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(DateBox.DateBoxHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(Button.ButtonHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(CheckBox.CheckBoxHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(Radio.RadioHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(Tab.TabHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(Progress.ProgressHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(DropDown.DropDownHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(Popover.PopoverHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(Tooltip.TooltipHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(Dialog.ModalDialogHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(SideMenu.SideMenuHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(ListData.ListHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(Tree.TreeHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(Table.TableHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(DropDownMultiLevel.DropDownMultiLevelHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(Autocomplete.AutocompleteHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(AutocompleteMultiSelect.AutocompleteMultiSelectHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(AutocompleteTree.AutocompleteTreeHtmlParser.getInstance());
        COMPONENT_HTML_PARSERS.add(AutocompleteTreeMultiSelect.AutocompleteTreeMultiSelectHtmlParser.getInstance());

        //
        HTML_PARSERS.add(RadioSelectionGroup.RadioSelectionGroupHtmlParser.getInstance());
        HTML_PARSERS.add(CheckBoxSelectionGroup.CheckBoxSelectionGroupHtmlParser.getInstance());
        HTML_PARSERS.add(StyleHtmlParser.getInstance());
        HTML_PARSERS.add(ValueHtmlParser.getInstance());
        HTML_PARSERS.add(Popover.PopoverAfterHtmlParser.getInstance());
        HTML_PARSERS.add(Tooltip.TooltipAfterHtmlParser.getInstance());
    }

    public static ComponentHtmlParser getComponentParser(String tag) {
        return getParser(tag, COMPONENT_HTML_PARSERS);
    }

    public static HtmlParser getParser(String tag) {
        return getParser(tag, HTML_PARSERS);
    }

    public static boolean isComponentParserTag(Element element) {
        return isParserTag(element, COMPONENT_HTML_PARSERS);
    }

    public static boolean isParserTag(Element element) {
        return isParserTag(element, HTML_PARSERS);
    }

    private static <T extends HtmlParser> T getParser(String tag, List<T> parsers) {
        for (T componentHtmlParser : parsers) {
            if (componentHtmlParser.getId().equalsIgnoreCase(tag)) {
                return componentHtmlParser;
            }
        }
        return null;
    }

    private static boolean isParserTag(Element element, List<? extends HtmlParser> parsers) {
        for (HtmlParser componentHtmlParser : parsers) {
            if (componentHtmlParser.getId().equalsIgnoreCase(element.tagName)) {
                return true;
            }
        }
        return false;
    }

}