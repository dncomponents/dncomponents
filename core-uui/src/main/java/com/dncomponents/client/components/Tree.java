/*
 * Copyright 2023 dncomponents
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

package com.dncomponents.client.components;

import com.dncomponents.client.components.core.*;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.close.CloseHandler;
import com.dncomponents.client.components.core.events.close.HasCloseHandlers;
import com.dncomponents.client.components.core.events.filters.Filter;
import com.dncomponents.client.components.core.events.open.HasOpenHandlers;
import com.dncomponents.client.components.core.events.open.OpenHandler;
import com.dncomponents.client.components.list.ListTreeMultiSelectionModel;
import com.dncomponents.client.components.tree.AbstractTreeCell;
import com.dncomponents.client.components.tree.TreeCellFactory;
import com.dncomponents.client.components.tree.TreeMultiSelectionModel;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.tree.TreeUi;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.NodeList;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.dncomponents.client.dom.DomUtil.isChildOf;

/**
 * @author nikolasavic
 */
public class Tree<M> extends AbstractCellComponent<TreeNode<M>, Object, TreeUi> implements HasOpenHandlers<TreeNode>, HasCloseHandlers<TreeNode>, HasTreeData<M> {

    static class TreeLogic<M> {
        AbstractCellComponent<TreeNode<M>, Object, ?> owner;

        public TreeLogic(AbstractCellComponent<TreeNode<M>, Object, ?> owner) {
            this.owner = owner;
        }

        //when filtering
        boolean showParents = true;
        boolean showChildren = false;
        boolean expandWhenFiltering = true;
        //end when filtering
        boolean showRoot = true;
        /**
         * Default {@code CellConfig} for all cells.
         */
        private boolean checkable = false;
        TreeNode root;

        protected final Filter<TreeNode<M>> FILTER_EXPAND = new Filter<TreeNode<M>>() {
            @Override
            public boolean test(TreeNode<M> treeNode) {
                return !treeNode.getAllParents()
                        .parallelStream()
                        .anyMatch(e -> !e.isExpanded());
            }
        };

        public boolean isShowRoot() {
            return showRoot;
        }

        public void setShowRoot(boolean showRoot) {
            if (this.showRoot && !showRoot && root != null)
                root.setExpanded(true);
            this.showRoot = showRoot;
        }

        void filterChildParentNodes() {
            if (!owner.filters.isEmpty())
                filterWithParentNodes();

        }

        protected void filterAndSort() {
//            filterChildParentNodes();
            owner.rowsFiltered = owner.rowsFiltered.stream().filter(FILTER_EXPAND).collect(Collectors.toList());
            if (!showRoot)
                owner.rowsFiltered.remove(root);
            owner.ensureVirtualScroll().calculateAll();
        }

        public void setRoot(TreeNode<M> root) {
            this.root = root;
            List rows = new ArrayList<>();
            rows.add(root);
            rows.addAll(root.getAllChildNodesInOrder());
            owner.setRowsData(rows);
        }

        public void setExpandAll(boolean expandAll) {
            owner.rows.stream().forEach((TreeNode e) -> {
                if (showRoot || e.getLevel() != 0)
                    e.setExpanded(expandAll);
            });
        }

        private void filterWithParentNodes() {
            if (!showParents && !showChildren)
                return;
            Set<TreeNode> ancestors = new HashSet<>();
            Set<TreeNode> descendants = new HashSet<>();
            for (TreeNode treeNode : owner.rowsFiltered) {
                if (showParents) {
                    ancestors.add(treeNode);
                    ancestors.addAll(treeNode.getAllParents());
                }
                if (showChildren) {
                    descendants.add(treeNode);
                    descendants.addAll(treeNode.getAllChildNodesInOrder());
                }
            }
            owner.rowsFiltered = owner.rows.stream().filter(new Filter<TreeNode>() {
                @Override
                public boolean test(TreeNode treeNode) {
                    return ancestors.contains(treeNode) || descendants.contains(treeNode);
                }
            }).collect(Collectors.toList());
        }

        private boolean isAllExpandedOrCollapsed() {
            return allNodesCollapsed() || allNodesExpanded();
        }

        /**
         * check if all nodes are expanded
         *
         * @return true if all node's properties of expanded are true
         */
        public boolean allNodesExpanded() {
            return owner.rows.stream().allMatch(treeNode -> treeNode.isExpanded() == true);
        }

        public boolean allNodesCollapsed() {
            return owner.rows.stream().allMatch(treeNode -> treeNode.isExpanded() == false);
        }

    }

    protected Map<TreeNode<M>, CellConfig<TreeNode<M>, Object>> mapCfg = new HashMap<>();

    private TreeLogic treeLogic = new TreeLogic(this);

    {
        defaultCellFactory = (TreeCellFactory<M, Object>) c -> AbstractTreeCell.getCell(c.model, isCheckable());
        setSelectionModel(new ListTreeMultiSelectionModel<>(this, view.getRootView()));
//        setSelectionModel(new TreeMultiSelectionModel(this, view.getRootView()));
    }

    public Tree(TreeUi ui) {
        super(ui);
    }

    public Tree() {
        super(Ui.get().getTreeUi());
    }

    public Tree(TreeNode root) {
        this();
        this.treeLogic.root = root;
    }

    public Tree(TreeNode root, TreeUi ui) {
        this(ui);
        this.treeLogic.root = root;
    }

    @Override
    public AbstractTreeCell getRowCell(int row) {
        return (AbstractTreeCell) super.getRowCell(row);
    }

    @Override
    public AbstractTreeCell getRowCell(TreeNode o) {
        return (AbstractTreeCell) super.getRowCell(o);
    }

    @Override
    public List<AbstractTreeCell> getRowCells(List<TreeNode<M>> list) {
        return (List<AbstractTreeCell>) super.getRowCells(list);
    }

    @Override
    public List<AbstractTreeCell<M, Object>> getCells() {
        return (List<AbstractTreeCell<M, Object>>) super.getCells();
    }

    @Override
    protected void filterAndSort() {
        super.filterAndSort();
        treeLogic.filterChildParentNodes();
        treeLogic.filterAndSort();
    }

    private boolean isAllExpandedOrCollapsed() {
        return treeLogic.isAllExpandedOrCollapsed();
    }

    protected CellConfig getDefaultCellConfig(TreeNode node) {
        return ensureRowCellConfig();
    }

    public boolean isCheckable() {
        return treeLogic.checkable;
    }

    public void setCheckable(boolean checkable) {
        treeLogic.checkable = checkable;
        this.setSelectionModel(new TreeMultiSelectionModel(this, view.getRootView()));
        getSelectionModel().setNavigator(false);
    }

    public TreeNode<M> getRoot() {
        return treeLogic.root;
    }

    public void setRoot(TreeNode<M> root) {
        this.treeLogic.root = root;
        List rows = new ArrayList<>();
        rows.add(root);
        rows.addAll(root.getAllChildNodesInOrder());
        setRowsData(rows);
    }

    public void rootToList() {
        rows.clear();
        rowsFiltered.clear();
        List rows = new ArrayList<>();
        rows.add(treeLogic.root);
        rows.addAll(treeLogic.root.getAllChildNodesInOrder());
        setRowsData(rows);
    }

    public boolean isAllCollapsed() {
        return treeLogic.allNodesCollapsed();
    }

    public boolean isAllExpanded() {
        return treeLogic.allNodesExpanded();
    }


    /**
     * sets expandAll property and all nodes to expandAll
     *
     * @param expandAll
     */
    public void expandAll(boolean expandAll) {
        treeLogic.setExpandAll(expandAll);
    }

    public void showTreeNode(TreeNode treeNode) {
        TreeNode parent = treeNode.getParent();
        while (parent != null) {
            parent.setExpanded(true);
            parent = parent.getParent();
        }
//        AbstractTreeCell treeCell = getRowCell(treeNode);
        getSelectionModel().setSelected(treeNode, true);
        drawData();
//        getRowCell(treeNode).scrollInView();
    }

    @Override
    public ListTreeMultiSelectionModel<TreeNode<M>> getSelectionModel() {
        return (ListTreeMultiSelectionModel<TreeNode<M>>) selectionModel;
    }

    public void setTreeSelectionMode() {
        setSelectionModel(new TreeMultiSelectionModel(this, view.getRootView()));
    }

    public void setScrollHeight(String height) {
        view.getRootView().setScrollHeight(height);
    }

    public void showChildren(boolean showChildren) {
        treeLogic.showChildren = showChildren;
    }

    public void showParents(boolean showParents) {
        treeLogic.showParents = showParents;
    }

    public boolean isShowRoot() {
        return treeLogic.isShowRoot();
    }

    public void showRoot(boolean b) {
        treeLogic.setShowRoot(b);
    }

    public boolean isShowChildren() {
        return treeLogic.showChildren;
    }

    public boolean isShowParents() {
        return treeLogic.showParents;
    }

    @Override
    public HandlerRegistration addCloseHandler(CloseHandler<TreeNode> handler) {
        return addHandler(handler);
    }

    @Override
    public HandlerRegistration addOpenHandler(OpenHandler<TreeNode> handler) {
        return addHandler(handler);
    }

    public static class TreeHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static TreeHtmlParser instance;

        private TreeHtmlParser() {
            tags.put(ITEM, Collections.emptyList());
        }

        public static TreeHtmlParser getInstance() {
            if (instance == null)
                return instance = new TreeHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            Tree<ItemId> tree;
            TreeUi ui = getView(Tree.class, htmlElement, elements);
            if (ui != null)
                tree = new Tree<>(ui);
            else
                tree = new Tree<>();

            if (htmlElement.hasChildNodes()) {
                TreeNode<ItemId> root = new TreeNode<>(new ItemId("root", "root"));
                parseItem((HTMLElement) htmlElement, root, this);
                tree.setRoot(root);
                tree.getRowCellConfig().setCellRenderer(r -> r.valuePanel.innerHTML =
                        r.cell.getModel().getUserObject().getContent());
                tree.drawData();
            }
            replaceAndCopy(htmlElement, tree);
            return tree;
        }

        public static void parseItem(HTMLElement node, TreeNode<ItemId> treeNode, ComponentHtmlParser cp) {
            NodeList<Element> elements = node.getElementsByTagName(ITEM);
            for (int i = 0; i < elements.length; i++) {
                Element at = elements.getAt(i);
                if (!isChildOf(node, at)) {
                    continue;
                }
                TreeNode<ItemId> item;
                ItemId idItem = cp.getIdItem(at);
                if (at.hasAttribute(CONTENT)) {
                    item = new TreeNode<>(idItem);
                    parseItem((HTMLElement) at, item, cp);

                } else
                    item = new TreeNode<>(idItem);
                treeNode.add(item);
            }
        }

        @Override
        public String getId() {
            return "dn-tree";
        }

        @Override
        public Class getClazz() {
            return Tree.class;
        }

    }

    class DefaultTreeRowConfig extends TreeCellConfig<M, Object> {
        public DefaultTreeRowConfig() {
            this(o -> o.getUserObject(), (node, o) -> node.setUserObject((M) o));
        }

        public DefaultTreeRowConfig(Function<TreeNode<M>, Object> fieldGetter) {
            super(fieldGetter);
        }

        public DefaultTreeRowConfig(Function<TreeNode<M>, Object> fieldGetter, BiConsumer<TreeNode<M>, Object> fieldSetter) {
            super(fieldGetter, fieldSetter);
        }
    }

    @Override
    public TreeCellConfig ensureRowCellConfig() {
        if (rowCellConfig == null)
            rowCellConfig = new DefaultTreeRowConfig();
        return (TreeCellConfig) rowCellConfig;
    }

    @Override
    protected CellConfig<TreeNode<M>, Object> ensureRowCellConfig(TreeNode<M> model) {
        if (mapCfg.get(model) != null)
            return mapCfg.get(model);
        else
            return ensureRowCellConfig();
    }

    @Override
    public TreeCellConfig<M, Object> getRowCellConfig() {
        return (TreeCellConfig<M, Object>) super.getRowCellConfig();
    }

    public void setCellConfig(TreeNode m, CellConfig cellConfig) {
        mapCfg.put(m, cellConfig);
    }

}
