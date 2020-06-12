package com.dncomponents.bootstrap.client.tree.basic;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.views.core.ui.tree.ParentTreeCellView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TreeCellParentViewImpl extends TreeCellViewImpl implements ParentTreeCellView {

    @UiField
    public HTMLElement openCloseElement;

    @UiStyle
    public String openStyle;
    @UiStyle
    public String closeStyle;

    HtmlBinder uiBinder = HtmlBinder.get(TreeCellParentViewImpl.class, this);

    public TreeCellParentViewImpl() {
    }

    public TreeCellParentViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public TreeCellParentViewImpl(HTMLTemplateElement templateElement) {
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
        handler.addTo(openCloseElement);
    }
}