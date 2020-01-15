package com.dncomponents.material.client.table.header.menu;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.selectionmodel.helper.ItemSelectionEvent;
import com.dncomponents.client.components.core.selectionmodel.helper.ItemSelectionHandler;
import com.dncomponents.client.components.dropdown.DropDown;
import com.dncomponents.client.components.dropdown.DropDownItem;
import com.dncomponents.client.components.table.header.HeaderFiltering;
import com.dncomponents.client.components.table.header.SortingDirection;
import com.dncomponents.client.components.table.header.filter.HasFilterValue;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.table.headers.HeaderTableMenuCellView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

import static com.dncomponents.client.components.table.header.SortingDirection.ASCENDING;
import static com.dncomponents.client.components.table.header.SortingDirection.DESCENDING;

/**
 * @author nikolasavic
 */
public class HeaderTableMenuCellViewImpl implements HeaderTableMenuCellView {

    @UiField
    HTMLElement root;
    @UiField("text-panel")
    HTMLElement textPanel;
    @UiField("filter-icon-panel")
    HTMLElement filterIconPanel;
    @UiField("group-by-icon-panel")
    HTMLElement groupByIconPanel;
    @UiField("icon-panel")
    HTMLElement sortIconPanel;
    @UiField("menu-holder")
    HTMLElement menuHolder;
    @UiStyle
    String sortStyle;
    @UiStyle
    String sortUpStyle;
    @UiStyle
    String sortDownStyle;

    Presenter presenter;

    DropDown<MenuItem> menu = new DropDown<>();

    HasFilterValue<Object> filterPanel;

    HtmlBinder uiBinder = HtmlBinder.get(HeaderTableMenuCellViewImpl.class, this);

    public HeaderTableMenuCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
        init();
    }

    public HeaderTableMenuCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        init();
    }

    private void init() {
        DomUtil.setVisible(filterIconPanel, true);
        DomUtil.replace(menu.asElement(), menuHolder);
    }

    @Override
    public void setColumn(ColumnConfig column) {
//        filterPanel = column.getFilterFactory().getHasFilterValue();
        bind();
    }

    private void bind() {
        DomUtil.setVisible(filterIconPanel, false);
        DomUtil.setVisible(groupByIconPanel, false);
        DomUtil.setVisible(sortIconPanel, false);
        menu.setButtonContent("|||");
        menu.addItem(new DropDownItem<>(menu, new MenuItem(Type.SORTING, "Sort (asc)", ASCENDING, active -> presenter.sort(active ? null : ASCENDING))));
        menu.addItem(new DropDownItem<>(menu, new MenuItem(Type.SORTING, "Sort (desc)", DESCENDING, active -> presenter.sort(active ? null : DESCENDING))));
        menu.addItem(new DropDownItem<>(menu, new MenuItem(Type.GROUPING, "Group by (asc)", ASCENDING, active -> presenter.groupBy(active ? null : ASCENDING))));
        menu.addItem(new DropDownItem<>(menu, new MenuItem(Type.GROUPING, "Group by (desc)", DESCENDING, active -> presenter.groupBy(active ? null : DESCENDING))));

        DropDownItem di = new DropDownItem<>(menu, new MenuItem(Type.FILTERING, "Filter", null, null));

        if (filterPanel != null) {
            ((ClickHandler) mouseEvent -> {
                mouseEvent.stopPropagation();

            }).addTo(filterPanel.asElement());
            filterPanel.setFilterValueHandler(presenter);
            di.setContent(filterPanel.asElement());
        }

        menu.addItem(di);
        menu.addItemSelectionHandler(new ItemSelectionHandler<DropDownItem<MenuItem>>() {
            @Override
            public void onSelection(ItemSelectionEvent<DropDownItem<MenuItem>> event) {
                event.getSelectedItem().getUserObject().execute(event.getSelectedItem().isSelected());
            }
        });
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public String getText() {
        return textPanel.innerHTML;
    }

    @Override
    public void setText(String text) {
        textPanel.innerHTML = text;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setActive(boolean b) {
//        if (b) markup.getRoot().addStyleName("active");
//        else getMarkup().getRoot().removeStyleName("active");
    }

    @Override
    public void setSortPresenter(SortPresenter presenter) {
    }

    @Override
    public void setSortIconText(String iconText) {
        sortIconPanel.innerHTML = iconText;
    }

    @Override
    public void setGroupOrder(int order) {
        if (order != -1)
            groupByIconPanel.innerHTML = (order + 1) + "";
    }

    @Override
    public void setSorted(SortingDirection direction) {
        if (direction == null) {
            sortIconPanel.className = sortStyle;
        } else if (direction == ASCENDING) {
            sortIconPanel.className = sortDownStyle;
        } else if (direction == DESCENDING) {
            sortIconPanel.className = sortUpStyle;
        }
//        currentDirection = direction;
//        setActive(currentDirection != null);


        menu.getItems().forEach(item -> {
            if (item.getUserObject().type == Type.SORTING)
                item.setActive(item.getUserObject().direction == direction);
        });
        setActive(direction != null);
        DomUtil.setVisible(sortIconPanel, direction != null);
    }

    @Override
    public void setFiltered(HeaderFiltering b) {
        if (b != null)
            filterPanel.setValue(b.getUserEnteredValue(), b.getComparator());
        DomUtil.setVisible(filterIconPanel, b != null);
    }

    @Override
    public void setGroupedBy(SortingDirection direction) {
        menu.getItems().forEach(item -> {
            if (item.getUserObject().type == Type.GROUPING)
                item.setActive(item.getUserObject().direction == direction);
        });
        setActive(direction != null);
        DomUtil.setVisible(groupByIconPanel, direction != null);
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    enum Type {
        SORTING, GROUPING, FILTERING
    }

    interface Command {
        void execute(boolean active);
    }

    class MenuItem {

        SortingDirection direction;
        String name;
        Type type;

        Command command;

        public MenuItem(Type type, String name, SortingDirection direction, Command command) {
            this.direction = direction;
            this.name = name;
            this.type = type;
            this.command = command;
        }

        @Override
        public String toString() {
            return name;
        }

        public void execute(boolean b) {
            if (command != null)
                command.execute(b);
        }

    }
}