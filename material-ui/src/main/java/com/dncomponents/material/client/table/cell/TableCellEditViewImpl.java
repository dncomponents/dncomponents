package com.dncomponents.material.client.table.cell;


import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.table.columnclasses.editcolumn.TableCellEditView;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class TableCellEditViewImpl extends TableCellViewImpl implements TableCellEditView {

    @UiField
    HTMLButtonElement btnEdit;
    @UiField
    HTMLButtonElement btnSave;
    @UiField
    HTMLButtonElement btnCancel;
    @UiField
    HTMLButtonElement btnDelete;

    HtmlBinder uiBinder = HtmlBinder.get(TableCellEditViewImpl.class, this);

    public TableCellEditViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public TableCellEditViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void addOnEditHandler(ClickHandler c) {
        DomUtil.addHandler(btnEdit, c);
    }

    @Override
    public void addOnCancelHandler(ClickHandler c) {
        DomUtil.addHandler(btnCancel, c);
    }

    @Override
    public void addOnSaveHandler(ClickHandler c) {
        DomUtil.addHandler(btnSave, c);
    }

    @Override
    public void addOnDeleteHandler(ClickHandler c) {
        DomUtil.addHandler(btnDelete, c);
    }

    @Override
    public void setEditMode(boolean b) {
        DomUtil.setVisible(btnSave, b);
        DomUtil.setVisible(btnCancel, b);
        DomUtil.setVisible(btnEdit, !b);
        DomUtil.setVisible(btnDelete, !b);
    }
}
