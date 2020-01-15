package com.dncomponents.client.components.multi;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.core.selectionmodel.DefaultSingleSelectionModel;
import com.dncomponents.client.components.core.selectionmodel.SingleSelectionModel;
import com.dncomponents.client.components.core.selectionmodel.helper.HasItemSelectionHandlers;
import com.dncomponents.client.components.core.selectionmodel.helper.ItemSelectionEvent;
import com.dncomponents.client.components.core.selectionmodel.helper.ItemSelectionHandler;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemViewSlots;
import com.dncomponents.client.views.core.ui.dropdown.DropDownMultiLevelUi;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.event.shared.HandlerRegistration;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.dncomponents.client.components.Tree.TreeHtmlParser.parseItem;

/**
 * @author nikolasavic
 */
public class DropDownMultiLevel<T> extends BaseComponent<TreeNode<T>, DropDownMultiLevelUi> implements
        HasOpenHandlers, HasCloseHandlers, HasItemSelectionHandlers<DropDownItemMultiLevel<T>> {

    SingleSelectionModel<TreeNode<T>> singleSelectionModel = new DefaultSingleSelectionModel<TreeNode<T>>() {
        @Override
        public List<TreeNode<T>> getItems() {
            return rows;
        }
    };
    DropDownItemMultiLevel.DropDownItemRenderer<TreeNode<T>> renderer = new DropDownItemMultiLevel.DropDownItemRenderer<TreeNode<T>>() {
        @Override
        public void render(TreeNode<T> tTreeNode, DropDownItemViewSlots slots) {
            slots.getMainSlot().textContent = tTreeNode.getUserObject() + "";
        }
    };
    List<TreeNode<T>> rows;
    boolean menuVisible;
    private TriggerType triggerType = TriggerType.HOVER;

    DropDownTreeNodePanel<T> dropDownPanel;

    public enum TriggerType {
        CLICK, HOVER
    }


    public DropDownMultiLevel(DropDownMultiLevelUi ui) {
        super(ui);
    }

    public DropDownMultiLevel() {
        super(Ui.get().getDropDownMultiLevelUi());
        view.getRootView().addClickOnButton((ClickHandler) mouseEvent -> {
            showMenu(!menuVisible);
            fireOpenCloseEvent();
        });
        view.getRootView().addClickOutOfButton(() -> {
            if (menuVisible) {
                showMenu(false);
                fireOpenCloseEvent();
            }
        });
    }

    private void fireOpenCloseEvent() {
        if (menuVisible)
            OpenEvent.fire(this, this);
        else
            CloseEvent.fire(this, this);
    }

    public void setRoot(TreeNode<T> root) {
        setUserObject(root);
        rows = root.getAllChildNodesInOrder();
    }


    public void setSelected(DropDownItemMultiLevel<T> item, boolean b, boolean fireEvent) {
        ItemSelectionEvent.fire(this, item);
        singleSelectionModel.setSelected(item.getValue(), b, fireEvent);
    }

    public void setButtonContent(String content) {
        view.getRootView().setButtonContent(content);
    }

    public void setButtonContent(HTMLElement content) {
        view.getRootView().setButtonHtmlContent(content);
    }

    public void showMenu(boolean b) {
        if (dropDownPanel == null) {
            dropDownPanel = new DropDownTreeNodePanel<T>(this, userObject);
            view.getRootView().addDropDownPanel(dropDownPanel);
        }
        menuVisible = b;
        dropDownPanel.show(view.getRootView().getRelativeElement(), b, "bottom");
    }


    @Override
    public HandlerRegistration addOpenHandler(OpenHandler handler) {
        return ensureHandlers().addHandler(OpenEvent.getType(), handler);
    }

    @Override
    public HandlerRegistration addCloseHandler(CloseHandler handler) {
        return ensureHandlers().addHandler(CloseEvent.getType(), handler);
    }

    public void setItemRenderer(DropDownItemMultiLevel.DropDownItemRenderer<TreeNode<T>> renderer) {
        this.renderer = renderer;
    }

    @Override
    protected DropDownMultiLevelUi getView() {
        return super.getView();
    }

//    @Override
//    public HandlerRegistration addItemSelectionHandler(ItemSelectionHandler<DropDownItem<TreeNode<T>>> handler) {
//        return ensureHandlers().addHandler(ItemSelectionEvent.getType(), handler);
//    }

    @Override
    public HandlerRegistration addItemSelectionHandler(ItemSelectionHandler<DropDownItemMultiLevel<T>> handler) {
        return ensureHandlers().addHandler(ItemSelectionEvent.getType(), handler);
    }


    public void setTriggerType(TriggerType triggerType) {
        this.triggerType = triggerType;
    }

    public TriggerType getTriggerType() {
        return triggerType;
    }

    protected IsElement getRelativeElement() {
        return view.getRootView().getRelativeElement();
    }

    public SingleSelectionModel<TreeNode<T>> getSingleSelectionModel() {
        return singleSelectionModel;
    }

    public static class DropDownMultiLevelHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static DropDownMultiLevelHtmlParser instance;

        private DropDownMultiLevelHtmlParser() {
            tags.put(CONTENT, Collections.emptyList());
        }

        public static DropDownMultiLevelHtmlParser getInstance() {
            if (instance == null)
                return instance = new DropDownMultiLevelHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            DropDownMultiLevel<ItemId> dropDown;
            DropDownMultiLevelUi ui = getView(DropDownMultiLevel.class, htmlElement, elements);
            if (ui != null)
                dropDown = new DropDownMultiLevel(ui);
            else
                dropDown = new DropDownMultiLevel();

            if (htmlElement.hasChildNodes()) { //todo apply this everywhere - if has children it is IdItem type
                dropDown.setItemRenderer((idItemTreeNode, slots) ->
                        slots.getMainSlot().innerHTML = idItemTreeNode.getUserObject().getContent());
                TreeNode<ItemId> root = new TreeNode<>(new ItemId("root", "root"));
                parseItem((HTMLElement) htmlElement, root, this);
                dropDown.setRoot(root);
            }

            if (htmlElement.hasAttribute(CONTENT)) {
                dropDown.setButtonContent(htmlElement.getAttribute(CONTENT));
            }

            DomUtil.copyAllAttributes(htmlElement, dropDown.asElement());
            DomUtil.replace(dropDown.asElement(), htmlElement);
            return dropDown;
        }

        @Override
        public String getId() {
            return "dn-dropdown-multilevel";
        }

        @Override
        public Class getClazz() {
            return DropDownMultiLevel.class;
        }

        @Override
        public boolean isPremium() {
            return true;
        }
    }

}