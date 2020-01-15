package com.dncomponents.bootstrap.client.autocomplete.tree;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.autocomplete.BaseAutocompleteViewImpl;
import com.dncomponents.client.components.HasRows;
import com.dncomponents.client.components.Tree;
import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.components.filters.Filter;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.core.ui.autocomplete.AutocompleteTreeView;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
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
        tree.getSelectionModel().setSelectionMode(DefaultMultiSelectionModel.SelectionMode.SINGLE);
    }

    @Override
    public void setRoot(TreeNode root) {
        tree.setRoot(root);
        tree.drawData();
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
    public HasRows<TreeNode<T>> getHasRowsData() {
        return tree;
    }

    @Override
    public void setFieldGetter(Function<TreeNode<T>, String> fieldGetter) {
//        tree.setFieldGetter(fieldGetter);
    }

    @Override
    public void setFilter(Filter<TreeNode<T>> filter) {
        tree.addFilter(filter);
    }

    @Override
    public void setCellConfig(CellConfig cellConfig) {
//        tree.setCellConfig((TreeCellConfig) cellConfig);
    }

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<List<TreeNode<T>>> handler) {
        return tree.getSelectionModel().addSelectionHandler(handler);
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        tree.fireEvent(event);
    }
}
