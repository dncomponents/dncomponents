package com.dncomponents.bootstrap.client.autocomplete.tree;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.autocomplete.BaseAutocompleteViewImpl;
import com.dncomponents.client.components.HasTreeData;
import com.dncomponents.client.components.Tree;
import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.close.HasCloseHandlers;
import com.dncomponents.client.components.core.events.filters.Filter;
import com.dncomponents.client.components.core.events.open.HasOpenHandlers;
import com.dncomponents.client.components.core.events.selection.SelectionHandler;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.core.ui.autocomplete.AutocompleteTreeView;
import elemental2.dom.HTMLTemplateElement;

import java.util.List;
import java.util.function.Function;

/**
 * @author nikolasavic
 */
@UiTemplate
public class AutocompleteTreeViewImpl<T> extends BaseAutocompleteViewImpl<TreeNode<T>> implements AutocompleteTreeView<T> {

    @UiField
    Tree tree;

    HtmlBinder uiBinder = HtmlBinder.get(AutocompleteTreeViewImpl.class, this);

    public AutocompleteTreeViewImpl() {
        uiBinder.bind();
        init();
    }

    public AutocompleteTreeViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        init();
    }

    private void init() {
        tree.showRoot(false);
        tree.getRowCellConfig().setFieldGetter((Function<TreeNode<T>, String>) node -> node.getUserObject() + "");
        tree.getSelectionModel().setSelectionMode(DefaultMultiSelectionModel.SelectionMode.SINGLE);
        tree.showRoot(false);
    }

    @Override
    public void focusList() {
        if (!tree.getCells().isEmpty())
            tree.getSelectionModel().focusCell(tree.getRowCell(0));
    }

    @Override
    public DefaultMultiSelectionModel getSelectionModel() {
        return tree.getSelectionModel();
    }

    @Override
    public HasTreeData<T> getHasRowsData() {
        return tree;
    }

    @Override
    public HasOpenHandlers<TreeNode<T>> getHasOpenHandlers() {
        return tree;
    }

    @Override
    public HasCloseHandlers<TreeNode<T>> getHasCloseHandlers() {
        return tree;
    }

    @Override
    public void setFieldGetter(Function<TreeNode<T>, String> fieldGetter) {
        tree.getRowCellConfig().setFieldGetter(fieldGetter);
    }

    @Override
    public CellConfig<TreeNode<T>, String> getRowCellConfig() {
        return tree.getRowCellConfig();
    }

    @Override
    public void setFilter(Filter<TreeNode<T>> filter) {
        tree.addFilter(filter);
    }

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<List<TreeNode<T>>> handler) {
        return tree.getSelectionModel().addSelectionHandler(handler);
    }

}