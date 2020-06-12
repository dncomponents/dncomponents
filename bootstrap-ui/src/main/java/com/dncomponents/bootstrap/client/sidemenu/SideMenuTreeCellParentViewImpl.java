package com.dncomponents.bootstrap.client.sidemenu;

import com.dncomponents.UiField;
import com.dncomponents.bootstrap.client.tree.basic.TreeCellParentViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.BaseEventListener;
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

    @Override
    public void setOpened(boolean b) {
        if (!b)
            valuePanelParent.classList.add(closeStyle);
        else
            valuePanelParent.classList.remove(closeStyle);
    }

}
