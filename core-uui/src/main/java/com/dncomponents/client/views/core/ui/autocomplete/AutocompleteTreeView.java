package com.dncomponents.client.views.core.ui.autocomplete;

import com.dncomponents.client.components.tree.TreeNode;

/**
 * @author nikolasavic
 */
public interface AutocompleteTreeView<T> extends BaseAutocompleteView<TreeNode<T>> {
    void setRoot(TreeNode root);
}
