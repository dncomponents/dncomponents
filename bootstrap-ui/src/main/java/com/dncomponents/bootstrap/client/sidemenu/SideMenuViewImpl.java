package com.dncomponents.bootstrap.client.sidemenu;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.bootstrap.client.tree.TreeUiImpl;
import com.dncomponents.bootstrap.client.tree.basic.TreeCellParentViewImpl;
import com.dncomponents.bootstrap.client.tree.basic.TreeCellViewImpl;
import com.dncomponents.client.components.Tree;
import com.dncomponents.client.components.core.CellRenderer;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.TreeCellConfig;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.components.filters.Filter;
import com.dncomponents.client.components.textbox.TextBox;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.views.core.ui.sidemenu.SideMenuView;
import com.dncomponents.client.views.core.ui.tree.BaseTreeCellView;
import com.dncomponents.client.views.core.ui.tree.ParentTreeCellView;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import elemental2.dom.*;

import java.util.List;

/**
 * @author nikolasavic
 */
public class SideMenuViewImpl implements SideMenuView {

    private static final String VIEW_ID = "default";
    @UiField
    HTMLElement root;
    @UiField
    HTMLElement treePanel;
    @UiField
    TextBox searchBox;
    @UiField
    HTMLTemplateElement treeSideTemplate;
    Tree tree;
    @UiStyle
    String collapsedStyle;
    private boolean collapsedToggle = true;

    HtmlBinder uiBinder = HtmlBinder.get(SideMenuViewImpl.class, this);
    @UiField
    public HTMLButtonElement navBtn;
    @UiField
    public HTMLDivElement navbarPanel;
    @UiField
    public String showNavBarPanelStyle;
    @UiField
    public HTMLDivElement treeHolder;


    public SideMenuViewImpl() {
        uiBinder.bind();
        initTree();
        init();
    }

    public SideMenuViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
        initTree();
        init();
    }

    public SideMenuViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        initTree();
        init();
    }

    private void initTree() {
        tree = new Tree(new TreeUiImpl(treeSideTemplate) {
            @Override
            public ParentTreeCellView getParentTreeCellView() {
                return new TreeCellParentViewImpl(treeItemSimpleParent) {

                    @Override
                    public void addOpenCloseHandler(BaseEventListener handler) {
                        handler.addTo(root);
                    }

                    @Override
                    public void setOpened(boolean b) {
                        if (!b)
                            valuePanel.classList.add(closeStyle);
                        else
                            valuePanel.classList.remove(closeStyle);
                    }
                };
            }

            @Override
            public BaseTreeCellView getTreeCellView() {
                return new TreeCellViewImpl(treeItemSimple) {
                    {
                        DomUtil.addHandler(valuePanel, new ClickHandler() {
                            @Override
                            public void onClick(MouseEvent mouseEvent) {
                                mouseEvent.preventDefault();
                            }
                        });
                    }

                    @Override
                    public void addClickHandler(ClickHandler clickHandler) {
                        clickHandler.addTo(valuePanel);
                    }

                };
            }
        });
        DomUtil.replaceRaw(tree, treeHolder);
    }

    private void init() {
        tree.getSelectionModel().setSelectionMode(DefaultMultiSelectionModel.SelectionMode.SINGLE);
        tree.setScrollHeight("100%");
        tree.showRoot(false);
        tree.showChildren(true);
        tree.setEditable(false);
        searchBox.addHandler((KeyUpHandler) event -> {
            tree.expandAll(searchBox.getValueFromView() != null);
            tree.drawData();
        });
        DomUtil.addHandler(navBtn, (ClickHandler) mouseEvent -> {
            if (collapsedToggle) {
                navBtn.classList.remove(collapsedStyle);
                navbarPanel.classList.add(showNavBarPanelStyle);
            } else {
                navBtn.classList.add(collapsedStyle);
                navbarPanel.classList.remove(showNavBarPanelStyle);
            }
            collapsedToggle = !collapsedToggle;
        });
        tree.asElement().style.setProperty("padding-bottom", "80px");
    }

    @Override
    public void showFilter(boolean b) {
        DomUtil.setVisible(searchBox.asElement(), b);
    }

    @Override
    public void setRoot(TreeNode item) {
        tree.setRoot(item);
    }

    @Override
    public void drawData() {
        tree.drawData();
    }

    @Override
    public void expandAll(boolean b) {
        tree.expandAll(b);
    }

    @Override
    public <T> HandlerRegistration addSelectionHandler(SelectionHandler<List<TreeNode<T>>> handler) {
        return tree.getSelectionModel().addSelectionHandler(handler);
    }


    @Override
    public List<TreeNode> getAll() {
        return tree.getRowsData();
    }

    @Override
    public String getValueFromView() {
        return searchBox.getValueFromView();
    }

    @Override
    public void registerFilter(Filter<TreeNode> filter) {
        tree.addFilter(filter);
        filter.addFilterHandler(event -> tree.expandAll(true));
    }

    @Override
    public void showNode(TreeNode treeNode) {
        tree.showTreeNode(treeNode);
    }

    @Override
    public void setCellConfig(TreeNode menuItem, TreeCellConfig<Object, Object> cellConfig) {
        tree.setCellConfig(menuItem, cellConfig);
    }

    @Override
    public <T> void setRenderer(CellRenderer<TreeNode<T>, Object> renderer) {
        tree.getRowCellConfig().getCellBuilder().setCellRenderer(renderer);
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    public void setHeight(String height) {
        treePanel.style.maxHeight = CSSProperties.MaxHeightUnionType.of(height);
    }

    @Override
    public void setWidth(String width) {
        root.style.minWidth = CSSProperties.MinWidthUnionType.of(width);
    }

    @Override
    public <T> void setSelected(TreeNode<T> item, boolean selected, boolean fireEvent) {
        tree.getSelectionModel().setSelected(item, selected, fireEvent);
    }

    @Override
    public void show(Boolean b) {

    }

    @Override
    public boolean isShown() {
        return false;
    }

}