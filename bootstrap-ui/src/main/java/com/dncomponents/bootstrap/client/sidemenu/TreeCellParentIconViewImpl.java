package com.dncomponents.bootstrap.client.sidemenu;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.bootstrap.client.button.FontAwesome;
import com.dncomponents.bootstrap.client.button.FontAwesomeSize;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.views.core.HasStyle;
import com.dncomponents.client.views.core.ui.tree.ParentTreeCellView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TreeCellParentIconViewImpl extends TreeCellIconViewImpl implements ParentTreeCellView {

    @UiField
    HTMLElement openCloseElement;
    @UiStyle
    String openStyle;
    @UiStyle
    String closeStyle;
    boolean rootClick;

    HtmlBinder uiBinder = HtmlBinder.get(TreeCellParentIconViewImpl.class, this);

    public TreeCellParentIconViewImpl(String template) {
        super(template);
    }

    public TreeCellParentIconViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void setOpened(boolean b) {
        if (!b)
            openCloseElement.className = openStyle;
        else
            openCloseElement.className = closeStyle;
    }

    @Override
    public void addOpenCloseHandler(BaseEventListener handler) {
        handler.addTo(rootClick ? asElement() : openCloseElement);
    }

    public static class Builder {


        private FontAwesome iconType;
        private FontAwesomeSize iconSize;
        private HTMLTemplateElement templateElement;
        private boolean rootClickExpand;

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

        public Builder setRootClickExpand(boolean rootClickExpand) {
            this.rootClickExpand = rootClickExpand;
            return this;
        }

        public static Builder get() {
            return new Builder();
        }

        public ParentTreeCellView build() {
            if (templateElement == null)
                templateElement = (BootstrapUi.getUi()).treeCellParentIconView;
            TreeCellParentIconViewImpl treeCellIconView = new TreeCellParentIconViewImpl(templateElement);
            treeCellIconView.rootClick = rootClickExpand;
            treeCellIconView.iconPanel.className = HasStyle.appendString(iconType) + HasStyle.appendString(iconSize);
            return treeCellIconView;
        }
    }


}
