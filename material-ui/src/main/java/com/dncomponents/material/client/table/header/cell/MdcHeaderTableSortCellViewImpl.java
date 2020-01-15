package com.dncomponents.material.client.table.header.cell;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.table.header.SortingDirection;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.table.headers.HeaderTableSortCellView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;
import elemental2.dom.MouseEvent;

import static com.dncomponents.client.components.table.header.SortingDirection.ASCENDING;
import static com.dncomponents.client.components.table.header.SortingDirection.DESCENDING;

/**
 * @author nikolasavic
 */
@UiTemplate
public class MdcHeaderTableSortCellViewImpl implements HeaderTableSortCellView {

    @UiField
    HTMLElement root;
    @UiField("text-panel")
    HTMLElement textPanel;
    @UiField("icon-panel")
    HTMLElement sortIcon;
    @UiField("icon-panel-order")
    HTMLElement sortIconOrder;
    @UiStyle
    String active;

    SortingDirection currentDirection;
    SortPresenter presenter;

    HtmlBinder uiBinder = HtmlBinder.get(MdcHeaderTableSortCellViewImpl.class, this);

    public MdcHeaderTableSortCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
        init();
    }

    public MdcHeaderTableSortCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        init();
    }

    private void init() {
        asElement().addEventListener(ClickHandler.TYPE, new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                if (currentDirection == null) {
                    presenter.sort(ASCENDING);
                } else if (currentDirection == ASCENDING) {
                    presenter.sort(DESCENDING);
                } else if (currentDirection == DESCENDING) {
                    presenter.sort(null);
                }

            }
        });
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
    public void setSorted(SortingDirection direction) {
        if (direction == null) {
            sortIcon.innerHTML = "sort";
        } else if (direction == ASCENDING) {
            sortIcon.innerHTML = "arrow_downward";
        } else if (direction == DESCENDING) {
            sortIcon.innerHTML = "arrow_upward";
        }

        currentDirection = direction;
        setActive(currentDirection != null);
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setActive(boolean b) {
        if (b) asElement().classList.add(active);
        else asElement().classList.remove(active);
    }

    @Override
    public void setSortPresenter(SortPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setSortIconText(String iconText) {
        sortIconOrder.innerHTML = iconText;
    }

    @Override
    public void setGroupOrder(int order) {

    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
