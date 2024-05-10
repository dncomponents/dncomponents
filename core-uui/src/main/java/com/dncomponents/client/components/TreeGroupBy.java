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

package com.dncomponents.client.components;

import com.dncomponents.client.components.table.columnclasses.treetablecell.AbstractTableTreeCell;
import com.dncomponents.client.components.table.header.HeaderGrouping;
import com.dncomponents.client.components.table.header.SortingDirection;
import com.dncomponents.client.components.tree.Node;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.Ui;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;


public class TreeGroupBy<T> extends TableTree<T> {

    Table<T> table;

    @Override
    protected void addHeader() {

    }

    @Override
    protected void addFooter() {

    }

    public TreeGroupBy(Table<T> table) {
        super(Ui.get().getTreeGroupBy(table.getView().getRootView()));
        this.table = table;
        for (ColumnConfig columnConfig : table.getColumnConfigs())
            this.addColumn(new TreeTableColumnConfig(columnConfig));
        getColumnConfigs().get(0).setCellFactory(c -> AbstractTableTreeCell.getCell((TreeNode) c.model, checkable));
        showRoot(false);
    }

    public static Map groupBy(HeaderGrouping headerGrouping, List rows) {
        Comparator<Object> comparator = headerGrouping.getSortingDirection() ==
                                        SortingDirection.DESCENDING ? Collections.reverseOrder() : null;
        final Object collect = rows.stream()
                .filter(o -> headerGrouping.getColumn().getFieldGetter().apply(o) != null)
                .collect(Collectors.groupingBy(t -> headerGrouping.getColumn().getFieldGetter().apply(t), () -> new TreeMap<>(comparator), Collectors.toList()));
        LinkedHashMap resultMap = new LinkedHashMap();
        if (headerGrouping.getSortingDirection() != SortingDirection.DESCENDING) {
//            putNulls(resultMap, headerGrouping, rows);
            resultMap.putAll((Map) collect);
        } else {
            resultMap.putAll((Map) collect);
//            putNulls(resultMap, headerGrouping, rows);
        }
        return resultMap;
    }

    private static void putNulls(LinkedHashMap resultMap, HeaderGrouping headerGrouping, List rows) {
        resultMap.put(null, rows.stream().filter(o -> headerGrouping.getColumn().getFieldGetter().apply(o) == null).collect(Collectors.toList()));
    }

    static void group(TreeNode node, Collection<HeaderGrouping> columnsForGrouping, List rows, boolean expandAll) {
        List<HeaderGrouping> listGrouping = new ArrayList<>(columnsForGrouping);
        HeaderGrouping headerGrouping = listGrouping.remove(0);
        groupBy(headerGrouping, rows).forEach((key, list1) -> {
            List list = (List) list1;
            TreeNode groupNode = new TreeNode(key);
            groupNode.setExpanded(expandAll);
//            groupNode.setCellConfig(headerGrouping.getColumn());
            node.add(groupNode);
            if (listGrouping.isEmpty()) {
                list.forEach(o -> groupNode.add(new TreeNode(o)));
            } else
                group(groupNode, listGrouping, list, expandAll);
        });
    }

    LinkedHashSet<HeaderGrouping> groupByInOrder = new LinkedHashSet<>();

    Collection<HeaderGrouping> getRetain(Collection<HeaderGrouping> headerGroupings) {
        //retain - common elements (without diff)
        List<HeaderGrouping> retain = new ArrayList<>(headerGroupings);
        retain.retainAll(groupByInOrder);

        //if both are equals
        boolean commonsAreEqual = retain.equals(new ArrayList<>(groupByInOrder).subList(0, retain.size()));

        //get difference
        if (!retain.isEmpty() && commonsAreEqual)
            return retain;
        else
            return new ArrayList<>();
    }

    List<HeaderGrouping> groupings;

    public void groupBy(Collection<HeaderGrouping> columnsForGrouping) {
        this.groupings = new ArrayList<>(columnsForGrouping);
        table.getView().getRootView().clear();
        table.visibleCells.clear();
        table.filterAndSort();
        TreeNode root = new TreeNode("root ");
        group(root, columnsForGrouping, table.rowsFiltered, isAllExpanded());
        final Collection<HeaderGrouping> retain = getRetain(columnsForGrouping);
        if (!retain.isEmpty()) {
            copyExpended(retain.size(), root);
        }
        setRoot(root);
        table.ensureVirtualScroll().setEnabled(false);
        ensureVirtualScroll().setEnabled(true);
        drawData();
        groupByInOrder.removeIf(e -> e.getActiveModifier() == null);
        groupByInOrder.addAll(columnsForGrouping);
    }

    private void copyExpended(int level, TreeNode root) {
        getRoot().getAllLeafs().forEach(new Consumer<TreeNode>() {
            @Override
            public void accept(TreeNode node) {
                if (!table.rowsFiltered.contains(node.getUserObject())) {
                    TreeNode parent = node.getParent();
                    node.removeFromParent();
                    if (parent != null) {
                        if (parent.getChildren().isEmpty()) {
                            parent.removeFromParent();
                        }
                    }
                }
            }
        });
        for (int i = 1; i <= level; i++) {
            List nodes = Node.getAllNodesAtLevel(i, getRoot())
                    .stream()
                    .filter(node -> ((TreeNode) node).isExpanded() == !isAllExpanded())
                    .collect(Collectors.toList());
            List nodesToCopy = Node.getAllNodesAtLevel(i, root);
            for (Object oo : nodes) {
                for (Object o : nodesToCopy) {
                    TreeNode node = (TreeNode) o;
                    TreeNode node1 = (TreeNode) oo;
                    if (isEqual(node, node1)) {
                        node.setExpanded(node1.isExpanded());
                        break;
                    }
                }
            }
        }
    }

    public static boolean isEqual(TreeNode node1, TreeNode node2) {
//        List<Object> list1 = node1.getAllLeafs().stream().map(e -> e.getUserObject()).collect(Collectors.toList());
        List<Object> list1 = (List<Object>) node1.getAllLeafs().stream().map(new Function() {
            @Override
            public Object apply(Object o) {
                return ((TreeNode) o).getUserObject();
            }
        }).collect(Collectors.toList());
        List<Object> list2 = (List<Object>) node2.getAllLeafs().stream().map(new Function() {
            @Override
            public Object apply(Object o) {
                return ((TreeNode) o).getUserObject();
            }
        }).collect(Collectors.toList());

//        List<Object> list1 = node1.getAllLeafs().stream().map(new Function<TreeNode<?>, Object>() {
//            @Override
//            public Object apply(TreeNode<?> treeNode) {
//                return treeNode.getUserObject();
//            }
//        }).collect(Collectors.toList());
//        List<Object> list2 = node2.getAllLeafs().stream().map(e -> e.getUserObject()).collect(Collectors.toList());

        boolean leafsEqual = list1.containsAll(list2) && list1.size() == list2.size();
//        boolean leafsEqual = new HashSet(list1).equals(new HashSet(list2));
        boolean levelsEqual = node1.getLevel() == node2.getLevel();
        return leafsEqual && levelsEqual;
    }

    public void clear() {
        groupByInOrder.clear();
        table.getView().getRootView().clear();
        table.visibleCells.clear();
//        setRoot(null);
        rows.clear();
        rowsFiltered.clear();
    }

    @Override
    public void drawData() {
        super.drawData();
    }

    public void setExpandAll(boolean expandAll) {
        treeLogic.setExpandAll(expandAll);
    }

}
