package com.dncomponents.client.components;

import com.dncomponents.client.components.tree.TreeNode;

/**
 * @author nikolasavic
 */
public interface HasTreeData<T> extends HasRowsData<TreeNode<T>> {
    void setRoot(TreeNode<T> root);

    TreeNode<T> getRoot();
}
