package com.dncomponents.material.client.tree;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.tree.*;
import com.dncomponents.material.client.MaterialUi;
import com.dncomponents.material.client.tree.basic.TreeCellParentViewImpl;
import com.dncomponents.material.client.tree.basic.TreeCellViewImpl;
import com.dncomponents.material.client.tree.checkbox.TreeCellCheckboxParentViewImpl;
import com.dncomponents.material.client.tree.checkbox.TreeCellCheckboxSimpleViewImpl;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class TreeUiImpl implements TreeUi {

    private static final String VIEW_ID = "default";

    @UiField("tree-main")
    HTMLTemplateElement treeMain;
    @UiField("tree-item-simple")
    protected HTMLTemplateElement treeItemSimple;
    @UiField("tree-item-simple-parent")
    protected HTMLTemplateElement treeItemSimpleParent;
    @UiField("tree-item-simple-checkbox")
    HTMLTemplateElement treeItemSimpleCheckbox;
    @UiField("tree-item-simple-parent-checkbox")
    HTMLTemplateElement treeItemSimpleParentCheckbox;

    TreeView treeView;

    HtmlBinder uiBinder = HtmlBinder.get(TreeUiImpl.class, this);

    public TreeUiImpl() {
        uiBinder.setTemplateElement((MaterialUi.getUi()).tree);
        uiBinder.bind();
    }

    public TreeUiImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public TreeUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public BaseTreeCellView getTreeCellView() {
        return new TreeCellViewImpl(treeItemSimple);
    }

    @Override
    public ParentTreeCellView getParentTreeCellView() {
        return new TreeCellParentViewImpl(treeItemSimpleParent);
    }

    @Override
    public TreeCellCheckboxSimpleView getTreeCellCheckBoxView() {
        return new TreeCellCheckboxSimpleViewImpl(treeItemSimpleCheckbox);
    }

    @Override
    public TreeCellCheckboxParentView getParentTreeCellCheckboxView() {
        return new TreeCellCheckboxParentViewImpl(treeItemSimpleParentCheckbox);
    }

    @Override
    public TreeView getRootView() {
        if (treeView == null)
            treeView = new TreeViewImpl(treeMain);
        return treeView;
    }

    public static class TreeUiViewFactory extends AbstractPluginHelper implements ViewFactory<TreeUi> {

        private static TreeUiViewFactory instance;

        private TreeUiViewFactory() {
        }

        public static TreeUiViewFactory getInstance() {
            if (instance == null)
                return instance = new TreeUiViewFactory();
            return instance;
        }

        @Override
        public TreeUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (MaterialUi.getUi()).tree;
            return new TreeUiImpl(templateElement);
        }

        @Override
        public String getId() {
            return TreeUiImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return TreeUiImpl.class;
        }
    }
}