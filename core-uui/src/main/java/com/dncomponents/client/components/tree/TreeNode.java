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

package com.dncomponents.client.components.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TreeNode)) return false;
        TreeNode<?> treeNode = (TreeNode<?>) o;
        return Objects.equals(getUserObject(), treeNode.getUserObject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserObject());
    }
}
