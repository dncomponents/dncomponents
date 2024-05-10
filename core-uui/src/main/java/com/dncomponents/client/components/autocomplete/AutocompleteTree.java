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

package com.dncomponents.client.components.autocomplete;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.TreeCellConfig;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.core.events.close.HasCloseHandlers;
import com.dncomponents.client.components.core.events.open.HasOpenHandlers;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.autocomplete.AutocompleteTreeView;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;

import java.util.Map;
import java.util.function.Function;

import static com.dncomponents.client.components.Tree.TreeHtmlParser.parseItem;


public class AutocompleteTree<T> extends AbstractAutocomplete<TreeNode<T>, AutocompleteTreeView<T>, TreeNode<T>> {
    TreeAcLogic treeAcLogic;

    public AutocompleteTree() {
        super(Ui.get().getAutocompleteTreeView());
        bind();
    }

    public AutocompleteTree(AutocompleteTreeView<T> view, Function<TreeNode<T>, String> fieldGetter) {
        super(view, fieldGetter);
        bind();
    }

    public AutocompleteTree(AutocompleteTreeView view) {
        super(view);
        bind();
    }

    public void setRoot(TreeNode<T> root) {
        view.getHasRowsData().setRoot(root);
        view.getHasRowsData().drawData();
    }

    private void bind() {
        treeAcLogic = new TreeAcLogic(this);
        treeAcLogic.bind();
    }

    static class TreeAcLogic {
        boolean treeOp = false;
        boolean onlyLeaf;
        AbstractAutocomplete ac;

        public TreeAcLogic(AbstractAutocomplete ac) {
            this.ac = ac;
        }

        public void bind() {
            ac.blurRegistration.removeHandler();
            ((HasCloseHandlers<Object>) ac.getView().getHasRowsData()).addCloseHandler(e -> treeOp = true);
            ((HasOpenHandlers<Object>) ac.getView().getHasRowsData()).addOpenHandler(e -> treeOp = true);
            ac.addBlurHandler(e -> DomGlobal.setTimeout(p0 -> {
                if (treeOp) {
                    treeOp = false;
                    ac.getView().setTextBoxFocused(true);
                    return;
                }
                ac.showList(false);
            }, 200));
        }

        public boolean isNotLeaf(TreeNode value) {
            return (!value.isLeaf() && onlyLeaf);
        }

        public void setOnlyLeaf(boolean onlyLeaf) {
            this.onlyLeaf = onlyLeaf;
        }

        public boolean isOnlyLeaf() {
            return onlyLeaf;
        }
    }

    public void selectOnlyLeaf(boolean b) {
        this.treeAcLogic.setOnlyLeaf(b);
    }

//    @Override
//    public void setValue(TreeNode<T> value, boolean fireEvents) {
////        if (!value.isLeaf() && treeAcLogic.isOnlyLeaf())
////            return;
//        super.setValue(value, fireEvents);
//    }

    public TreeCellConfig<T, String> getRowCellConfig() {
        return (TreeCellConfig<T, String>) view.getRowCellConfig();
    }

    public static class AutocompleteTreeHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static AutocompleteTreeHtmlParser instance;

        private AutocompleteTreeHtmlParser() {
        }

        public static AutocompleteTreeHtmlParser getInstance() {
            if (instance == null)
                return instance = new AutocompleteTreeHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            AutocompleteTree<ItemId> autocomplete;
            AutocompleteTreeView view = getView(AutocompleteTree.class, htmlElement, elements);
            if (view != null)
                autocomplete = new AutocompleteTree(view);
            else
                autocomplete = new AutocompleteTree();

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
            return "dn-autocomplete-tree";
        }

        @Override
        public Class getClazz() {
            return AutocompleteTree.class;
        }

    }

}
