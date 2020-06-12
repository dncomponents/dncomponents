package com.dncomponents.client.components.multi;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.close.CloseEvent;
import com.dncomponents.client.components.core.events.close.CloseHandler;
import com.dncomponents.client.components.core.events.close.HasCloseHandlers;
import com.dncomponents.client.components.core.events.open.HasOpenHandlers;
import com.dncomponents.client.components.core.events.open.OpenEvent;
import com.dncomponents.client.components.core.events.open.OpenHandler;
import com.dncomponents.client.components.core.selectionmodel.DefaultSingleSelectionModel;
import com.dncomponents.client.components.core.selectionmodel.SingleSelectionModel;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.MainRenderer;
import com.dncomponents.client.views.MainRendererImpl;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.dropdown.DropDownMultiLevelUi;
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
        HasOpenHandlers, HasCloseHandlers {

    SingleSelectionModel<TreeNode<T>> singleSelectionModel = new DefaultSingleSelectionModel<TreeNode<T>>() {
        @Override
        public List<TreeNode<T>> getItems() {
            return rows;
        }
    };
    MainRenderer<TreeNode<T>> itemRenderer = new MainRendererImpl<>();
    List<TreeNode<T>> rows;
    boolean menuVisible;
    private TriggerType triggerType = TriggerType.HOVER;
    DropDownTreeNodePanel<T> dropDownPanel;
    HandlerRegistration clickOutHandlerRegistration;

    public enum TriggerType {
        CLICK, HOVER
    }

    public DropDownMultiLevel(DropDownMultiLevelUi ui) {
        super(ui);
        view.getRootView().addClickOnButton((ClickHandler) mouseEvent -> {
            showMenu(!menuVisible);
            if (menuVisible) {
                clickOutHandlerRegistration = view.getRootView().addClickOutOfButton(evt -> {
                    if (!DomUtil.isDescendant(this.asElement(), ((Element) evt.target))) {
                        if (clickOutHandlerRegistration != null)
                            clickOutHandlerRegistration.removeHandler();
                        clickOutHandlerRegistration = null;
                        showMenu(false);
                    }
                });
            }
        });
    }

    public DropDownMultiLevel() {
        this(Ui.get().getDropDownMultiLevelUi());
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
//        ItemSelectionEvent.fire(this, item);
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
        fireOpenCloseEvent();
    }


    @Override
    public HandlerRegistration addOpenHandler(OpenHandler handler) {
        return handler.addTo(asElement());
    }

    @Override
    public HandlerRegistration addCloseHandler(CloseHandler handler) {
        return handler.addTo(asElement());
    }

    public void setItemRenderer(MainRenderer<TreeNode<T>> renderer) {
        this.itemRenderer = renderer;
    }

    @Override
    protected DropDownMultiLevelUi getView() {
        return super.getView();
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

            if (htmlElement.hasChildNodes()) {
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

    }

}