package com.dncomponents.material.client.tree.basic;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.tree.BaseTreeCellView;
import com.dncomponents.material.client.cell.BaseCellViewImpl;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
//tree-item-simple
@UiTemplate
public class TreeCellViewImpl extends BaseCellViewImpl implements BaseTreeCellView {

    @UiStyle
    String activeStyle;
    @UiField
    HTMLElement iconPanel;
    @UiField
    HTMLElement iconParent;
    String icon;


    HtmlBinder uiBinder = HtmlBinder.get(TreeCellViewImpl.class, this);

    public TreeCellViewImpl() {
    }

    public TreeCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public TreeCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    public void setActive(boolean b) {
        if (b) asElement().classList.add(activeStyle);
        else asElement().classList.remove(activeStyle);
    }

    @Override
    public void setPadding(double padding) {
        DomUtil.setPaddingLeft(asElement(), padding + "px");
    }

    public <C extends BaseTreeCellView> C setIcon(String icon) {
        this.icon = icon;
        if (icon != null && iconParent != null && iconPanel != null) {
            DomUtil.setVisible(iconParent, true);
            Ui.get().getIconRenderer().render(iconPanel, icon);
        }
        return (C) this;
    }

}
