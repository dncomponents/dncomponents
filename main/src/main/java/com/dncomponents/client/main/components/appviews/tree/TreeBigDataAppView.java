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

package com.dncomponents.client.main.components.appviews.tree;

import com.dncomponents.UiField;
import com.dncomponents.client.components.Tree;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.open.OpenHandler;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


public class TreeBigDataAppView implements IsElement {

    @UiField
    HTMLElement root;
    @UiField
    Tree tree;
    @UiField
    public TextArea ta;

    public TreeBigDataAppView() {
        HtmlBinder.create(TreeBigDataAppView.class, this).bind();
        init();
    }

    static int t = 0;

    private void init() {
        tree.expandAll(false);
        TreeNode root = new TreeNode("root");
        root.add(new TreeNode("temp"));
        root.setExpanded(false);
        tree.setRoot(root);

        OpenHandler<TreeNode<Object>> openHandler = event -> {
            TreeNode<Object> n = event.getOwner();
            if (n.getChildren().size() > 998) {
                return;
            }
            n.remove(n.getChildren().get(0));
            for (int i = 0; i < 1000; i++) {
                TreeNode nn = new TreeNode(t++ + "");
                nn.setExpanded(false);
                nn.add(new TreeNode("temp"));
                n.add(nn);
            }
            tree.rootToList();
            tree.drawData();
            ta.setValue(tree.getRowsData().size() + " items.");
        };
        tree.addOpenHandler(openHandler);
        tree.drawData();
    }

    private static TreeBigDataAppView instance;

    public static TreeBigDataAppView getInstance() {
        if (instance == null)
            instance = new TreeBigDataAppView();
        return instance;
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

}
