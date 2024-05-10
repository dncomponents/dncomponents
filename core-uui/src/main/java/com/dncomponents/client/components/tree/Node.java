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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public interface Node<N extends Node> {

    static List<Node<?>> collect(Node<?> node) {
        List<Node<?>> list = new ArrayList<>();
        for (Node child : node.getChildren()) {
            list.add(child);
            if (!child.isLeaf())
                list.addAll(collect(child));
        }
        return list;
    }

    /**
     * @return list of nodes
     */
    static List<Node<?>> getAllChildNodesInOrder(Node<?> node) {
        List<Node<?>> allNodes = new ArrayList<>();
        if (!node.isLeaf())
            for (Node rg : node.getChildren()) {
                allNodes.add(rg);
                if (!rg.isLeaf()) {
                    allNodes.addAll(getAllChildNodesInOrder(rg));
                }
            }
        return allNodes;
    }

    /**
     * @return list of nodes
     */
    static List<Node<?>> getAllChildNodesInOrderSorted(Node<?> node, Comparator<Node<?>> comparator, List<Node<?>> filteredList) {
        List<Node<?>> allNodes = new ArrayList<>();
        if (!node.isLeaf()) {
            List<Node<?>> leafNodes = new ArrayList<>();
            List<Node<?>> treeNodes = new ArrayList<>();
            for (Node rg : node.getChildren()) {
                if (!rg.isLeaf()) {
                    treeNodes.add(rg);
                    allNodes.add(rg);
                    allNodes.addAll(getAllChildNodesInOrderSorted(rg, comparator, filteredList));
                } else {
                    if (filteredList == null || filteredList.contains(rg)) leafNodes.add(rg);
                }
            }
            leafNodes.sort(comparator);
            allNodes.addAll(leafNodes);
        }
        return allNodes;
    }

    static List<Node<?>> getAllNodesAtLevel(int level, Node<?> node) {
        final List<Node<?>> all = collect(node.getRoot());
        return all.stream()
                .filter(n -> n.getLevel() == level)
                .collect(Collectors.toList());
    }

    <T> T getUserObject();

    N getParent();

    void setParent(N node);

    List<N> getChildren();

    void createChildrenIfNull();

    default void insert(N node, int index) {
        createChildrenIfNull();
        node.setParent(this);
        getChildren().add(index, node);
    }

    default void insertAfter(N node) {
        node.setParent(getParent());
        getParent().getChildren().add(getParent().getChildren().indexOf(this) + 1, node);
    }

    default void add(N node) {
        createChildrenIfNull();
        node.setParent(this);
        getChildren().add(node);
    }

    default void addAll(List<N> nodeList) {
        nodeList.forEach(node -> node.setParent(this));
        getChildren().addAll(nodeList);
    }

    default boolean isLeaf() {
        return getChildren() == null || getChildren().isEmpty();
    }

    default boolean isRoot() {
        return getParent() == null;
    }

    default Node getRoot() {
        Node ancestor = this;
        Node previous;
        do {
            previous = ancestor;
            ancestor = ancestor.getParent();
        } while (ancestor != null);
        return previous;
    }

    default int getLevel() {
        int level = -1;
        Node ancestor = this;
        do {
            level++;
            ancestor = ancestor.getParent();
        } while (ancestor != null);
        return level;
    }

    default List<N> getSiblingsAndChildNodes() {
        return (List<N>) collect(this.getParent());
    }

    default List<N> getAllNodesFromRoot() {
        return (List<N>) collect(this.getRoot());
    }


    default List<N> getAllChildNodes() {
        return (List<N>) collect(this);
    }

    /**
     * get first leaf of this node
     *
     * @return
     */
    default N getFirstLeaf() {
        Node node = this;
        if (!this.isLeaf())
            do {
                node = (Node) node.getChildren().get(0);
            } while (!node.isLeaf());
        return (N) node;
    }

    default List<N> getSiblings() {
        if (getParent() != null) {
            return getParent().getChildren();
        }
        return Collections.emptyList();
    }

    default N nextSibling() {
        if (getParent() != null) {
            List<N> siblings = getParent().getChildren();
            int index = siblings.indexOf(this);
            if (index < siblings.size() - 1) {
                return siblings.get(index + 1);
            }
        }
        return null;
    }

    default N previousSibling() {
        if (getParent() != null) {
            List<N> siblings = getParent().getChildren();
            int index = siblings.indexOf(this);
            if (index > 0) {
                return siblings.get(index - 1);
            }
        }
        return null;
    }

    default List<N> getAllLeafs() {
        return getAllChildNodes().stream()
                .filter(node -> node.isLeaf())
                .collect(Collectors.toList());
    }

    /**
     * Collects all parents of node.
     *
     * @return list of parent nodes or empty list if no parents found.
     */
    default List<N> getAllParents() {
        List<N> parents = new ArrayList<>();
        N parent = this.getParent();
        while (parent != null) {
            parents.add(parent);
            parent = (N) parent.getParent();
        }
        return parents;
    }

    default List<N> getAllChildNodesInOrder() {
        return (List<N>) getAllChildNodesInOrder(this);
    }

    default List<N> getAllChildNodesInOrderSorted(Comparator comparator, List<Node<?>> filteredList) {
        return (List<N>) getAllChildNodesInOrderSorted(this, comparator, filteredList);
    }

    default void remove(N nodeToRemove) {
        if (nodeToRemove == null) {
            throw new IllegalArgumentException("argument is null");
        }
        getChildren().remove(nodeToRemove);
        nodeToRemove.setParent(null);
    }

    default void removeFromParent() {
        N parent = getParent();
        if (parent != null) {
            parent.remove(this);
        }
    }

    default boolean isParentOf(TreeNode node) {
        return this.getAllParents().contains(node);
    }
}
