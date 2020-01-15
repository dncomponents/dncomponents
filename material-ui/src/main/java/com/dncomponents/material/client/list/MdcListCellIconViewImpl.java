package com.dncomponents.material.client.list;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.MaterialUi;
import com.dncomponents.material.client.cell.BaseCellViewImpl;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

public class MdcListCellIconViewImpl extends BaseCellViewImpl {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement iconPanel;
    @UiField
    HTMLElement valuePanel;

    public MdcListCellIconViewImpl() {
        HtmlBinder uiBinder = HtmlBinder.get(MdcListCellIconViewImpl.class, this);
        uiBinder.setTemplateElement(MaterialUi.getUi().listCellIconView);
        uiBinder.bind();

    }

    public MdcListCellIconViewImpl(HTMLTemplateElement templateElement) {
        HtmlBinder uiBinder = HtmlBinder.get(MdcListCellIconViewImpl.class, this);
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void setToValuePanel(Element element) {
        getValuePanel().innerHTML = "";
        getValuePanel().appendChild(element);
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public HTMLElement getValuePanel() {
        return valuePanel;
    }

    public static class Builder {

        private String iconType;
        private HTMLTemplateElement templateElement;

        private Builder() {
        }

        public Builder iconType(String iconType) {
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

        public MdcListCellIconViewImpl build() {
            if (templateElement == null)
                templateElement = MaterialUi.getUi().listCellIconView;
            MdcListCellIconViewImpl listCellIconView = new MdcListCellIconViewImpl(templateElement);
            listCellIconView.iconPanel.innerHTML = iconType;
            return listCellIconView;
        }
    }


}