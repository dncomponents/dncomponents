package com.dncomponents.client.views.core.ui.autocomplete;

import com.dncomponents.client.components.HasTreeData;
import com.dncomponents.client.components.core.events.close.HasCloseHandlers;
import com.dncomponents.client.components.core.events.open.HasOpenHandlers;
import com.dncomponents.client.components.tree.TreeNode;

/**
 * @author nikolasavic
 */
public interface AutocompleteTreeView<T> extends BaseAutocompleteView<TreeNode<T>> {
    @Override
    HasTreeData<T> getHasRowsData();

    HasOpenHandlers<TreeNode<T>> getHasOpenHandlers();

    HasCloseHandlers<TreeNode<T>> getHasCloseHandlers();
}
