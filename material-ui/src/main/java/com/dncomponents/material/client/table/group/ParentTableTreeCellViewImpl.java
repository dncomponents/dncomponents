package com.dncomponents.material.client.table.group;

import com.dncomponents.UiField;
import com.dncomponents.Component;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.core.ui.table.ParentTableTreeCellView;
import com.dncomponents.material.client.tree.basic.TreeCellParentViewImpl;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@Component
public class ParentTableTreeCellViewImpl extends TreeCellParentViewImpl implements ParentTableTreeCellView {

    @UiField
    HTMLElement openCloseCell;

    HtmlBinder uiBinder = HtmlBinder.create(ParentTableTreeCellViewImpl.class, this);

    public ParentTableTreeCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public ParentTableTreeCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public void setSpan(int colNumb) {
        openCloseCell.setAttribute("colspan", colNumb + "");
    }

    @Override
    public void setPadding(double padding) {
        DomUtil.setPaddingLeft(openCloseCell, padding + "px");
    }
}
