package com.dncomponents.client.components.table.header;


import com.dncomponents.client.components.table.AbstractHeaderCell;
import com.dncomponents.client.views.core.ui.table.headers.HeaderTableTextCellView;

/**
 * Header cell that displays text.
 *
 * @author nikolasavic
 */
public class HeaderTableTextCell extends AbstractHeaderCell {

    private String text;

    public HeaderTableTextCell() {
    }

    public HeaderTableTextCell(String text) {
        setText(text);
    }

    public HeaderTableTextCell(HeaderTableTextCellView headerCellView) {
        super(headerCellView);
    }

    @Override
    public void draw() {
        getCellView().setText(text == null ? getCellConfig().getColumnName() : text);
    }

    public HeaderTableTextCell setText(String columnName) {
        this.text = columnName;
        return this;
    }


    public String getText() {
        return text;
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getHeaderTableTextCellView();
    }

    @Override
    protected HeaderTableTextCellView getCellView() {
        return (HeaderTableTextCellView) super.getCellView();
    }

}