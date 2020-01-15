package com.dncomponents.client.components.sidemenu;

import com.dncomponents.client.components.core.*;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.core.events.selection.HasSelectionHandlers;
import com.dncomponents.client.components.core.events.selection.SelectionEvent;
import com.dncomponents.client.components.core.events.selection.SelectionHandler;
import com.dncomponents.client.components.filters.Filter;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.appview.PlaceManager;
import com.dncomponents.client.views.core.ui.sidemenu.SideMenuView;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.NodeList;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.dncomponents.client.dom.DomUtil.isChildOf;

/**
 * @author nikolasavic
 */
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
        view.addSelectionHandler(new com.google.gwt.event.logical.shared.SelectionHandler<List<TreeNode<T>>>() {
            @Override
            public void onSelection(com.google.gwt.event.logical.shared.SelectionEvent<List<TreeNode<T>>> event) {
                SelectionEvent.fire(SideMenu.this, event.getSelectedItem().get(0).getUserObject());
            }
        });
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
            addSelectionHandler(event ->
                    History.newItem(((ItemId) event.getItem()).getId(), true));
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
                parseItem((HTMLElement) htmlElement, root, sideMenu, elements);
                sideMenu.setRootMenuItem(root);
                sideMenu.draw();
            }
            replaceAndCopy(htmlElement, sideMenu);
            return sideMenu;
        }


        public static String SIDE_MENU_TREE_CELL_VIEW = "SIDE_MENU_TREE_CELL_VIEW";
        public static String SIDE_MENU_TREE_CELL_PARENT_VIEW = "SIDE_MENU_TREE_CELL_PARENT_VIEW";

        public void parseItem(HTMLElement node, TreeNode<ItemId> treeNode, SideMenu<ItemId> sideMenu, Map<String, ?> el) {
            NodeList<Element> elements = node.getElementsByTagName(ITEM);
            for (int i = 0; i < elements.length; i++) {
                Element at = elements.getAt(i);
                if (!isChildOf(node, at)) {
                    continue;
                }
                TreeNode<ItemId> item;
                ItemId idItem = getIdItem(at);
                if (at.hasAttribute(CONTENT)) {
                    item = new TreeNode<>(idItem);
//                    sideMenu.setCellConfig(item, new TreeCellConfig<>().setCellFactory(c ->
//                            new TreeCellParent(getViewFor(SIDE_MENU_TREE_CELL_PARENT_VIEW, at, el))));
//                    can we just give view from main template sidemenu
                    parseItem((HTMLElement) at, item, sideMenu, el);
                } else {
                    item = new TreeNode<>(idItem);
//                    sideMenu.setCellConfig(item, new TreeCellConfig<>().setCellFactory(c ->
//                            new TreeCellSimple(getViewFor(SIDE_MENU_TREE_CELL_VIEW, at, el))));
                }
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

        @Override
        public boolean isPremium() {
            return true;
        }
    }


    private void setCellRenderer(CellRenderer<TreeNode<T>, String> cellRenderer) {
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