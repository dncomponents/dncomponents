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

package com.dncomponents.bootstrap.client.tree;

import com.dncomponents.UiField;
import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.bootstrap.client.tree.basic.TreeCellParentViewImpl;
import com.dncomponents.bootstrap.client.tree.basic.TreeCellViewImpl;
import com.dncomponents.bootstrap.client.tree.checkbox.TreeCellCheckboxParentViewImpl;
import com.dncomponents.bootstrap.client.tree.checkbox.TreeCellCheckboxSimpleViewImpl;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.tree.*;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class TreeUiImpl implements TreeUi {

    public static final String VIEW_ID = "default";

    @UiField
    HTMLTemplateElement treeMain;
    @UiField
    public HTMLTemplateElement treeItemSimple;
    @UiField
    protected HTMLTemplateElement treeItemSimpleParent;
    @UiField
    HTMLTemplateElement treeItemSimpleCheckbox;
    @UiField
    HTMLTemplateElement treeItemSimpleParentCheckbox;
    @UiField
    protected HTMLTemplateElement treeItemSimpleParentIcon;
    @UiField
    HTMLTemplateElement treeItemSimpleCheckboxIcon;
    @UiField
    HTMLTemplateElement treeItemSimpleParentCheckboxIcon;

    TreeView treeView;

    HtmlBinder uiBinder = HtmlBinder.create(TreeUiImpl.class, this);

    public TreeUiImpl() {
        uiBinder.setTemplateElement((BootstrapUi.getUi()).tree);
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
    public BaseTreeCellView getTreeCellView(String icon) {
        return new TreeCellViewImpl(treeItemSimple).setIcon(icon);
    }

    @Override
    public ParentTreeCellView getParentTreeCellView(String icon) {
        return new TreeCellParentViewImpl(treeItemSimpleParent).setIcon(icon);
    }

    @Override
    public TreeCellCheckboxSimpleView getTreeCellCheckBoxView(String icon) {
        return new TreeCellCheckboxSimpleViewImpl(treeItemSimpleCheckbox).setIcon(icon);
    }

    @Override
    public TreeCellCheckboxParentView getParentTreeCellCheckboxView(String icon) {
        return new TreeCellCheckboxParentViewImpl(treeItemSimpleParentCheckbox).setIcon(icon);
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
                templateElement = (BootstrapUi.getUi()).tree;
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
