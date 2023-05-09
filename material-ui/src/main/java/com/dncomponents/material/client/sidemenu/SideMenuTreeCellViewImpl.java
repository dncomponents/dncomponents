package com.dncomponents.material.client.sidemenu;

import com.dncomponents.Component;
 import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.material.client.tree.basic.TreeCellViewImpl;
import elemental2.dom.HTMLTemplateElement;
import elemental2.dom.MouseEvent;

/**
 * @author nikolasavic
 */
@Component
public class SideMenuTreeCellViewImpl extends TreeCellViewImpl {


    HtmlBinder uiBinder = HtmlBinder.create(SideMenuTreeCellViewImpl.class, this);

    public SideMenuTreeCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        DomUtil.addHandler(valuePanel, new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                mouseEvent.preventDefault();
            }
        });
    }

    @Override
    public void addClickHandler(ClickHandler clickHandler) {
        clickHandler.addTo(valuePanel);
    }

}
