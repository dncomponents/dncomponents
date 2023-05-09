/*
 * Copyright 2023 dncomponents
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

package com.dncomponents.bootstrap.client.autocomplete.multiselect;

import com.dncomponents.UiField;
import com.dncomponents.bootstrap.client.autocomplete.BaseAutocompleteViewImpl;
import com.dncomponents.client.components.AbstractCellComponent;
import com.dncomponents.client.components.HasRowsData;
import com.dncomponents.client.components.ListData;
import com.dncomponents.client.components.Tree;
import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.TemplateParser;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.filters.Filter;
import com.dncomponents.client.components.core.events.selection.SelectionHandler;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.components.list.ListTreeMultiSelectionModel;
import com.dncomponents.client.components.textbox.TextBox;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectItemView;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;
import elemental2.dom.HTMLUListElement;
import elemental2.dom.MouseEvent;

import java.util.function.Function;

public class AutocompleteListOrTreeMultiSelectViewImpl extends BaseAutocompleteViewImpl implements AutocompleteMultiSelectView {

    @UiField
    HTMLElement listOrTreePanel;
    @UiField
    HTMLUListElement selectionPanel;
    @UiField
    TextBox textBox;
    HTMLTemplateElement itemView;
    AbstractCellComponent listOrTree;

    HtmlBinder uiBinder = HtmlBinder.create(AutocompleteListOrTreeMultiSelectViewImpl.class, this);

    public static AutocompleteListOrTreeMultiSelectViewImpl getInstance(HTMLTemplateElement templateElement, boolean tree) {
        if (tree) {
            Tree tree1 = new Tree();
            tree1.showRoot(false);
            return new AutocompleteListOrTreeMultiSelectViewImpl(templateElement, tree1);
        } else
            return new AutocompleteListOrTreeMultiSelectViewImpl(templateElement, new ListData());
    }

    private AutocompleteListOrTreeMultiSelectViewImpl(HTMLTemplateElement templateElement, AbstractCellComponent listOrTree) {
        TemplateParser templateParser = new TemplateParser(templateElement, true);
        HTMLTemplateElement mainTemplate = templateParser.getElement("rootView");
        itemView = templateParser.getElement("itemView");
        uiBinder.setTemplateElement(mainTemplate);
        uiBinder.bind();
        this.listOrTree = listOrTree;
        init();
    }

    @Override
    public HandlerRegistration addButtonClickHandler(ClickHandler clickHandler) {
        return clickHandler.addTo(selectionPanel);
    }

    @Override
    public void setTextBoxCurrentValue(String value) {

    }

    private void init() {
        listOrTreePanel.innerHTML = "";
        listOrTreePanel.appendChild(listOrTree.asElement());
        listOrTree.getSelectionModel().setSelectionMode(DefaultMultiSelectionModel.SelectionMode.MULTI);
        listOrTree.getRowCellConfig().setFieldGetter(t -> t + "");
        ((ListTreeMultiSelectionModel) listOrTree.getSelectionModel()).useMetaKeyForSelection(false);
        new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                textBox.setFocus(true);
            }
        }.addTo(root);
    }

    @Override
    public void focusList() {
        if (!listOrTree.getCells().isEmpty())
            ((ListTreeMultiSelectionModel) listOrTree.getSelectionModel()).focusCell(listOrTree.getRowCell(0));
    }

    @Override
    public DefaultMultiSelectionModel getSelectionModel() {
        return listOrTree.getSelectionModel();
    }

    @Override
    public HasRowsData getHasRowsData() {
        return listOrTree;
    }

    @Override
    public void setFieldGetter(Function fieldGetter) {
        listOrTree.getRowCellConfig().setFieldGetter(fieldGetter);
    }

    @Override
    public CellConfig<TreeNode, String> getRowCellConfig() {
        return listOrTree.getRowCellConfig();
    }

    @Override
    public void clearItems() {
        selectionPanel.innerHTML = "";
    }

    @Override
    public void addItem(IsElement item) {
        selectionPanel.appendChild(item.asElement());
    }

    @Override
    public AutocompleteMultiSelectItemView getAutocompleteMultiSelectItemView() {
        return new AutocompleteMultiSelectItemViewImpl(itemView);
    }

    @Override
    public void setPlaceHolder(String text) {

    }

    @Override
    public void setFilter(Filter filter) {
        listOrTree.addFilter(filter);
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler handler) {
        return listOrTree.getSelectionModel().addSelectionHandler(handler);
    }
}
