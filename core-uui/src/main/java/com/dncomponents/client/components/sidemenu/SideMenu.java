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

package com.dncomponents.client.components.sidemenu;

import com.dncomponents.client.components.core.*;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.filters.Filter;
import com.dncomponents.client.components.core.events.selection.HasSelectionHandlers;
import com.dncomponents.client.components.core.events.selection.SelectionEvent;
import com.dncomponents.client.components.core.events.selection.SelectionHandler;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.dom.History;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.appview.PlaceManager;
import com.dncomponents.client.views.core.ui.sidemenu.SideMenuView;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.NodeList;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.dncomponents.client.dom.DomUtil.isChildOf;


public class SideMenu<T> extends BaseComponent<Object, SideMenuView> implements HasSelectionHandlers<T> {

    Filter<TreeNode> filter = new Filter<TreeNode>() {
        @Override
        public boolean test(TreeNode treeNode) {
            if (treeNode == null || treeNode.getUserObject() == null || view.getValueFromView() == null)
                return true;
            String str = "";
            if (filterField == null)
                str = treeNode.getUserObject() + "";
            else
                str = filterField.apply(treeNode);
            if (str == null)
                return false;
            return str.toLowerCase().contains(view.getValueFromView().toLowerCase());
        }
    };

    private CellRenderer<TreeNode<T>, Object> itemRenderer;

    protected Function<TreeNode<T>, String> filterField;


    public SideMenu(SideMenuView view) {
        super(view);
        init();
    }

    public SideMenu() {
        super(Ui.get().getSideMenuView());
        init();
    }

    private void init() {
        view.registerFilter(filter);
        view.addSelectionHandler(new SelectionHandler<List<TreeNode<T>>>() {
            @Override
            public void onSelection(SelectionEvent<List<TreeNode<T>>> event) {
                SelectionEvent.fire((HasSelectionHandlers<T>) SideMenu.this, event.getSelectedItem().get(0).getUserObject());
            }
        });
//        view.addSelectionHandler((SelectionHandler<List<TreeNode<T>>>) event ->
//                SelectionEvent.fire((HasSelectionHandlers<T>) SideMenu.this, event.getSelectedItem().get(0).getUserObject()));
    }

    public void showFilter(boolean b) {
        view.showFilter(b);
    }

    public void setRootMenuItem(TreeNode item) {
        view.setRoot(item);
    }

    public void expandAll(boolean b) {
        view.expandAll(b);
    }

    public List<TreeNode<T>> getAllItems() {
        return view.getAll();
    }


    public void showNode(TreeNode treeNode) {
        view.showNode(treeNode);
    }

    public void setCellConfig(TreeNode menuItem, TreeCellConfig<Object, Object> cellConfig) {
        view.setCellConfig(menuItem, cellConfig);
    }

    public void draw() {
        view.drawData();
    }


    public void setItemRenderer(CellRenderer<TreeNode<T>, Object> itemRenderer) {
        this.itemRenderer = itemRenderer;
        view.setRenderer(this.itemRenderer);
    }

    public void setFilterField(Function<TreeNode<T>, String> filterField) {
        this.filterField = filterField;
    }

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<T> handler) {
        return handler.addTo(asElement());
    }

    public void setSelected(TreeNode<T> item, boolean selected, boolean fireEvent) {
        view.setSelected(item, selected, fireEvent);
    }

    PlaceManager placeManager;

    public void setPlaceManager(PlaceManager placeManager) {
        if (this.placeManager == null) {
            this.placeManager = placeManager;
            addSelectionHandler(new SelectionHandler<T>() {
                @Override
                public void onSelection(SelectionEvent<T> event) {
                    if (event.getSelectedItem() instanceof ItemId)
                        History.newItem(((ItemId) event.getSelectedItem()).getId(), true);
                    else
                        History.newItem(event.getSelectedItem() + "", true);
                }
            });
            placeManager.addValueChangeHandler(event -> {
                final String historyToken = placeManager.getHistoryToken(event.getValue());
                for (TreeNode item : getAllItems()) {
                    if (((ItemId) item.getUserObject()).getId() != null &&
                        ((ItemId) item.getUserObject()).getId().equalsIgnoreCase(historyToken)) {
                        setSelected(item, true, false);
                        draw();
                    }
                }
            });
        }
    }

    public static class SideMenuHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static SideMenu.SideMenuHtmlParser instance;

        private SideMenuHtmlParser() {
            tags.put(CONTENT, Collections.emptyList());
            tags.put(ITEM, Collections.emptyList());
        }

        public static SideMenu.SideMenuHtmlParser getInstance() {
            if (instance == null)
                return instance = new SideMenu.SideMenuHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            SideMenu<ItemId> sideMenu;
            SideMenuView ui = getView(SideMenuView.class, htmlElement, elements);
            if (ui != null)
                sideMenu = new SideMenu(ui);
            else
                sideMenu = new SideMenu();

            if (htmlElement.hasChildNodes()) {
                TreeNode<ItemId> root = new TreeNode<>(new ItemId("root", "root"));
                sideMenu.setItemRenderer(r -> r.valuePanel.innerHTML =
                        r.cell.getModel().getUserObject().getContent() + ""
                );
                sideMenu.setFilterField(itemIdTreeNode -> itemIdTreeNode.getUserObject().getContent());
                parseItem((HTMLElement) htmlElement, root);
                sideMenu.setRootMenuItem(root);
                sideMenu.draw();
            }
            replaceAndCopy(htmlElement, sideMenu);
            return sideMenu;
        }


        public void parseItem(HTMLElement node, TreeNode<ItemId> treeNode) {
            NodeList<Element> elements = node.getElementsByTagName(ITEM);
            for (int i = 0; i < elements.length; i++) {
                Element at = elements.getAt(i);
                if (!isChildOf(node, at)) {
                    continue;
                }
                TreeNode<ItemId> item;
                ItemId idItem = getIdItem(at);
                item = new TreeNode<>(idItem);
                if (at.hasAttribute(CONTENT))
                    parseItem((HTMLElement) at, item);
                treeNode.add(item);
            }
        }

        @Override
        public String getId() {
            return "dn-side-menu";
        }

        @Override
        public Class getClazz() {
            return SideMenu.class;
        }

    }

    public void setHeight(String height) {
        view.setHeight(height);
    }

    public void setWidth(String width) {
        view.setWidth(width);
    }

    public void show(boolean b) {
        view.show(b);
    }

    public boolean isShown() {
        return view.isShown();
    }
}
