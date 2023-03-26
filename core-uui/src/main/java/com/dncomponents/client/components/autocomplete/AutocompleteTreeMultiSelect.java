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

package com.dncomponents.client.components.autocomplete;

import com.dncomponents.client.components.Tree;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectView;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.dncomponents.client.components.Tree.TreeHtmlParser.parseItem;

/**
 * @author nikolasavic
 */
public class AutocompleteTreeMultiSelect<T> extends AbstractAutocompleteMultiSelect<TreeNode<T>> {

    public AutocompleteTreeMultiSelect() {
        super(Ui.get().getAutocompleteMultiSelectView(true));
        bind();
    }

    public AutocompleteTreeMultiSelect(AutocompleteMultiSelectView<TreeNode<T>> view) {
        super(view);
        bind();
    }

    public AutocompleteTreeMultiSelect(Function<TreeNode<T>, String> fieldGetter) {
        super(Ui.get().getAutocompleteMultiSelectView(true), fieldGetter);
        bind();
    }

    public AutocompleteTreeMultiSelect(AutocompleteMultiSelectView<TreeNode<T>> view, Function<TreeNode<T>, String> fieldGetter) {
        super(view, fieldGetter);
        bind();
    }

    private void bind() {
//        view.getRowCellConfig().setFieldGetter(node -> node.getUserObject() + "");
        view.getRowCellConfig().setFieldGetter(new Function<TreeNode<T>, String>() {
            @Override
            public String apply(TreeNode<T> node) {
                return node.getUserObject() + "";
            }
        });
        setItemRenderer((node, slots) -> slots.getMainSlot().textContent = node.getUserObject() + "");
        AutocompleteTree.TreeAcLogic treeAcLogic = new AutocompleteTree.TreeAcLogic(this);
        treeAcLogic.bind();
    }

    public void setRoot(TreeNode<T> root) {
        ((Tree<T>) view.getHasRowsData()).setRoot(root);
        view.getHasRowsData().drawData();
    }

    public static class AutocompleteTreeMultiSelectHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static AutocompleteTreeMultiSelectHtmlParser instance;

        private AutocompleteTreeMultiSelectHtmlParser() {
        }

        public static AutocompleteTreeMultiSelectHtmlParser getInstance() {
            if (instance == null)
                return instance = new AutocompleteTreeMultiSelectHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            AutocompleteTreeMultiSelect<ItemId> autocomplete;
            AutocompleteMultiSelectView<TreeNode<?>> view = getView(AutocompleteTreeMultiSelect.class, htmlElement, elements);
            if (view != null)
                autocomplete = new AutocompleteTreeMultiSelect(view);
            else
                autocomplete = new AutocompleteTreeMultiSelect();

            if (htmlElement.hasChildNodes()) {
                TreeNode<ItemId> root = new TreeNode<>(new ItemId("root", "root"));
                parseItem((HTMLElement) htmlElement, root, this);
                autocomplete.getRowCellConfig().setCellRenderer(r -> r.valuePanel.innerHTML =
                        r.cell.getModel().getUserObject().getContent());
                autocomplete.setRoot(root);
            }

            DomUtil.copyAllAttributes(htmlElement, autocomplete.asElement());
            replaceAndCopy(htmlElement, autocomplete);
            return autocomplete;
        }

        @Override
        public String getId() {
            return "dn-autocomplete-tree-multi-select";
        }

        @Override
        public Class getClazz() {
            return AutocompleteTreeMultiSelect.class;
        }

    }

}
