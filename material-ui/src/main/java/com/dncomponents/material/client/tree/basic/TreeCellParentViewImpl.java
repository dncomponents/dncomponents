package com.dncomponents.material.client.tree.basic;

import com.dncomponents.UiField;
import com.dncomponents.Component;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.views.core.ui.tree.ParentTreeCellView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@Component
public class TreeCellParentViewImpl extends TreeCellViewImpl implements ParentTreeCellView {

    @UiField
    protected HTMLElement openCloseElement;

    @UiField
    protected String openStyle;
    @UiField
    protected String closeStyle;

    HtmlBinder uiBinder = HtmlBinder.create(TreeCellParentViewImpl.class, this);

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
            openCloseElement.innerHTML = openStyle;
        else
            openCloseElement.innerHTML = closeStyle;
    }

    @Override
    public void addOpenCloseHandler(BaseEventListener handler) {
        handler.addTo(openCloseElement);
    }

}
