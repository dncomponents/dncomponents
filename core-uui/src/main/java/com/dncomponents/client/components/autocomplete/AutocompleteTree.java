package com.dncomponents.client.components.autocomplete;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.autocomplete.AutocompleteTreeView;
import elemental2.dom.Element;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class AutocompleteTree<T> extends AbstractAutocomplete<TreeNode<T>, AutocompleteTreeView<T>> {

    public AutocompleteTree(CellConfig cellConfig) {
        super(Ui.get().getAutocompleteTreeView(), cellConfig);
    }

    public void setRoot(TreeNode<T> root) {
        view.setRoot(root);
    }

    public AutocompleteTree() {
        super(Ui.get().getAutocompleteTreeView());
    }

    public AutocompleteTree(AutocompleteTreeView view) {
        super(view);
    }

    public void setCellConfig(CellConfig config) {
        super.cellConfig = config;
        bind();
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
//                autocomplete.setItemRenderer((idItemTreeNode, slots) ->
//                        slots.getMainSlot().innerHTML = idItemTreeNode.getUserObject().getHtml());
//                TreeNode<ItemId> root = new TreeNode<>(new ItemId("root", "root"));
//                parseItem((HTMLElement) htmlElement, root, this);
//                dropDown.setRoot(root);
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

        @Override
        public boolean isPremium() {
            return true;
        }
    }

}
