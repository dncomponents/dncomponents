/*
 * Copyright 2024 dncomponents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dncomponents.client.views;

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
import com.dncomponents.client.components.core.HtmlParserService;
import com.dncomponents.client.components.core.StyleHtmlParser;
import com.dncomponents.client.components.core.ValueHtmlParser;
import com.dncomponents.client.components.dropdown.DropDown;
import com.dncomponents.client.components.form.Form;
import com.dncomponents.client.components.modal.Dialog;
import com.dncomponents.client.components.multi.DropDownMultiLevel;
import com.dncomponents.client.components.popover.Popover;
import com.dncomponents.client.components.progress.Progress;
import com.dncomponents.client.components.sidemenu.SideMenu;
import com.dncomponents.client.components.tab.Tab;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.components.textbox.*;
import com.dncomponents.client.components.tooltip.Tooltip;


public class Ui {

    static {
        HtmlParserService.registerComponent(Accordion.AccordionHtmlParser.getInstance());
        HtmlParserService.registerComponent(TextBox.TextBoxHtmlParser.getInstance());
        HtmlParserService.registerComponent(TextArea.TextAreaHtmlParser.getInstance());
        HtmlParserService.registerComponent(IntegerBox.IntegerBoxHtmlParser.getInstance());
        HtmlParserService.registerComponent(DoubleBox.DoubleBoxHtmlParser.getInstance());
        HtmlParserService.registerComponent(LongBox.LongBoxHtmlParser.getInstance());
        HtmlParserService.registerComponent(DateBox.DateBoxHtmlParser.getInstance());
        HtmlParserService.registerComponent(Button.ButtonHtmlParser.getInstance());
        HtmlParserService.registerComponent(CheckBox.CheckBoxHtmlParser.getInstance());
        HtmlParserService.registerComponent(Radio.RadioHtmlParser.getInstance());
        HtmlParserService.registerComponent(Tab.TabHtmlParser.getInstance());
        HtmlParserService.registerComponent(Progress.ProgressHtmlParser.getInstance());
        HtmlParserService.registerComponent(DropDown.DropDownHtmlParser.getInstance());
        HtmlParserService.registerComponent(Popover.PopoverHtmlParser.getInstance());
        HtmlParserService.registerComponent(Tooltip.TooltipHtmlParser.getInstance());
        HtmlParserService.registerComponent(Dialog.ModalDialogHtmlParser.getInstance());
        HtmlParserService.registerComponent(SideMenu.SideMenuHtmlParser.getInstance());
        HtmlParserService.registerComponent(ListData.ListHtmlParser.getInstance());
        HtmlParserService.registerComponent(Tree.TreeHtmlParser.getInstance());
        HtmlParserService.registerComponent(Table.TableHtmlParser.getInstance());
        HtmlParserService.registerComponent(DropDownMultiLevel.DropDownMultiLevelHtmlParser.getInstance());
        HtmlParserService.registerComponent(Autocomplete.AutocompleteHtmlParser.getInstance());
        HtmlParserService.registerComponent(AutocompleteMultiSelect.AutocompleteMultiSelectHtmlParser.getInstance());
        HtmlParserService.registerComponent(AutocompleteTree.AutocompleteTreeHtmlParser.getInstance());
        HtmlParserService.registerComponent(AutocompleteTreeMultiSelect.AutocompleteTreeMultiSelectHtmlParser.getInstance());
        HtmlParserService.registerComponent(Form.FormHtmlParser.getInstance());

        HtmlParserService.register(Form.FormHtmlParserAfter.getInstance());
        HtmlParserService.register(RadioSelectionGroup.RadioSelectionGroupHtmlParser.getInstance());
        HtmlParserService.register(CheckBoxSelectionGroup.CheckBoxSelectionGroupHtmlParser.getInstance());
        HtmlParserService.register(StyleHtmlParser.getInstance());
        HtmlParserService.register(ValueHtmlParser.getInstance());
        HtmlParserService.register(Popover.PopoverAfterHtmlParser.getInstance());
        HtmlParserService.register(Tooltip.TooltipAfterHtmlParser.getInstance());
    }

    private static boolean debug = true;

    protected static ComponentsViews implementation;

    public static void set(ComponentsViews implementation) {
        Ui.implementation = implementation;
    }

    public static ComponentsViews get() {
        return implementation;
    }

    public static void setDebug(boolean debug) {
        Ui.debug = debug;
    }

    public static boolean isDebug() {
        return debug;
    }

}
