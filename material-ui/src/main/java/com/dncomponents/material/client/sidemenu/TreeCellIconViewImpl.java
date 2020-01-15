package com.dncomponents.material.client.sidemenu;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.core.HasStyle;
import com.dncomponents.client.views.core.ui.tree.BaseTreeCellView;
import com.dncomponents.material.client.MaterialUi;
import com.dncomponents.material.client.button.FontAwesome;
import com.dncomponents.material.client.button.FontAwesomeSize;
import com.dncomponents.material.client.cell.BaseCellViewImpl;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
//tree-item-simple
@UiTemplate
public class TreeCellIconViewImpl extends BaseCellViewImpl implements BaseTreeCellView {

    @UiStyle
    String activeStyle;

    @UiField
    HTMLElement iconPanel;

    private FontAwesome iconType;
    private FontAwesomeSize iconSize;
    private HTMLTemplateElement templateElement;

    public TreeCellIconViewImpl() {
    }

    HtmlBinder uiBinder = HtmlBinder.get(TreeCellIconViewImpl.class, this);

    public TreeCellIconViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public TreeCellIconViewImpl(HTMLTemplateElement templateElement) {
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

    public TreeCellIconViewImpl setTemplateElement(HTMLTemplateElement templateElement) {
        this.templateElement = templateElement;
        return this;
    }

    public TreeCellIconViewImpl setIconSize(FontAwesomeSize iconSize) {
        this.iconSize = iconSize;
        return this;
    }

    public TreeCellIconViewImpl setIconType(FontAwesome iconType) {
        this.iconType = iconType;
        return this;
    }

    public static class Builder {


        private FontAwesome iconType;
        private FontAwesomeSize iconSize;
        private HTMLTemplateElement templateElement;

        private Builder() {
        }


        public Builder iconSize(FontAwesomeSize iconSize) {
            this.iconSize = iconSize;
            return this;
        }

        public Builder iconType(FontAwesome iconType) {
            this.iconType = iconType;
            return this;
        }

        public Builder template(HTMLTemplateElement templateElement) {
            this.templateElement = templateElement;
            return this;
        }

        public static Builder get() {
            return new Builder();
        }

        public TreeCellIconViewImpl build() {
            if (templateElement == null)
                templateElement = MaterialUi.getUi().treeCellIconView;
            TreeCellIconViewImpl treeCellIconView = new TreeCellIconViewImpl(templateElement);
            treeCellIconView.iconPanel.className = HasStyle.appendString(iconType) + HasStyle.appendString(iconSize);
            return treeCellIconView;
        }
    }


}
