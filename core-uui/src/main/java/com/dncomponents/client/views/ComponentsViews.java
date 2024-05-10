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
import com.dncomponents.client.views.core.ui.form.FormUi;
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


public interface ComponentsViews {

    MultiMap<Class, ViewFactory> getRegisteredViewFactoriesList();

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

    DialogView getModalDialogView();

    TabUi getTabUi();

    ProgressView getProgressView();

    IconRenderer getIconRenderer();

    //cell components
    ListUi getListUi();

    <T> AutocompleteView<T> getAutocompleteView();

    AutocompleteTreeView getAutocompleteTreeView();

    <T> AutocompleteMultiSelectView<T> getAutocompleteMultiSelectView(boolean tree);

    TreeUi getTreeUi();

    TableUi getTableUi();

    SideMenuView getSideMenuView();

    FilterPanelListView getFilterPanelListView();

    TableTreeUi getTableTreeUi();

    TableTreeUi getTreeGroupBy(TableView rootView);

    DropDownMultiLevelUi getDropDownMultiLevelUi();

    PagerUi getPagerUi();

    PagerListUi getPagerListUi();

    FormUi getFormUi();

}
