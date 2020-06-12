package com.dncomponents.client.components.tree;

import com.dncomponents.client.components.AbstractCellComponent;
import com.dncomponents.client.components.list.ListTreeMultiSelectionModel;
import com.dncomponents.client.views.core.ui.list.ListView;

import java.util.stream.Collectors;

/**
 * @author nikolasavic
 */
public class TreeMultiSelectionModel extends ListTreeMultiSelectionModel<TreeNode<Object>> {

    public TreeMultiSelectionModel(AbstractCellComponent owner, ListView view) {
        super(owner, view);
    }

    @Override
    public boolean setSelected(TreeNode<Object> model, boolean b, boolean fireEvent) {
        boolean changed = false;
        if (b) {
            if (!isSelected(model)) {
                selection.add(model);
                if (!model.isLeaf()) {
                    selection.addAll(model.getAllChildNodesInOrder().stream()
                            .filter(e -> !isSelected(e)).collect(Collectors.toList()));
                }
                for (TreeNode treeNode : model.getAllParents()) {
                    if (isAllSelected(treeNode)) {
                        selection.add(treeNode);
                    }
                }
                changed = true;
            }
        } else {
            if (isSelected(model)) {
                selection.remove(model);
                if (!model.isLeaf()) {
                    selection.removeAll(model.getAllChildNodesInOrder());
                }
                selection.removeAll(model.getAllParents());
                changed = true;
            }
        }
        if (changed) {
            fireSelectionChange();
        }
        return changed;
    }

    private boolean isAllSelected(TreeNode node) {
        return node.getAllChildNodesInOrder().stream().allMatch(selection::contains);
    }

    public SelectionState getNodeState(TreeNode parent) {
        boolean allSelected = parent.getAllChildNodesInOrder().stream().allMatch(selection::contains);
        boolean allUnSelected = parent.getAllChildNodesInOrder().stream().allMatch(treeNode -> !selection.contains(treeNode));
        if (allSelected) {
            return SelectionState.CHECKED;
        } else if (allUnSelected) {
            return SelectionState.UNCHECKED;
        } else if (!allSelected && !allUnSelected) {
            return SelectionState.INDETERMINATE;
        } else {
            return null;
        }
    }

    public enum SelectionState {
        CHECKED, UNCHECKED, INDETERMINATE
    }
}