package com.dncomponents.client.components.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nikolasavic
 */
public class TreeNode<T> implements Node<TreeNode<T>> {

    private TreeNode parent;
    private List<TreeNode<T>> children;

    private T userObject;
    private boolean expanded = true;
    private boolean checked;


    public TreeNode(T userObject) {
        this.userObject = userObject;
    }

    @Override
    public T getUserObject() {
        return userObject;
    }

    public void setUserObject(T userObject) {
        this.userObject = userObject;
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public void setParent(TreeNode node) {
        this.parent = node;
    }

    @Override
    public List<TreeNode<T>> getChildren() {
        return children;
    }

    @Override
    public void createChildrenIfNull() {
        if (children == null)
            children = new ArrayList<>();
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public String toString() {
        return userObject + "";
    }
}