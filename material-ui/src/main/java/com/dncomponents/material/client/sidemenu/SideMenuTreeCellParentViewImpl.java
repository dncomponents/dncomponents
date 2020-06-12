package com.dncomponents.material.client.sidemenu;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.material.client.tree.basic.TreeCellParentViewImpl;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class SideMenuTreeCellParentViewImpl extends TreeCellParentViewImpl {

    @UiField
    HTMLElement valuePanelParent;

    HtmlBinder uiBinder = HtmlBinder.get(SideMenuTreeCellParentViewImpl.class, this);

    public SideMenuTreeCellParentViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void addOpenCloseHandler(BaseEventListener handler) {
        handler.addTo(root);
    }

}
