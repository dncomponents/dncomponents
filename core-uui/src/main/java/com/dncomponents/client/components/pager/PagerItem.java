package com.dncomponents.client.components.pager;

import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.pager.PagerItemView;
import elemental2.dom.MouseEvent;

/**
 * @author nikolasavic
 */
public class PagerItem extends BaseComponent<Integer, PagerItemView> {

    public PagerItem(Pager pager, Integer number) {
        super(pager.getView().getPagerItemView());
        this.setUserObject(number);
        setText(((userObject == -1) ? "..." : userObject + 1) + "");
        new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                pager.setCurrentPage(getUserObject());
            }
        }.addTo(asElement());
    }

    public PagerItem(Pager pager, String text, ClickHandler clickHandler) {
        super(pager.getView().getPagerItemView());
        setText(text);
        clickHandler.addTo(asElement());
    }

    public void setText(String str) {
        view.setText(str);
    }

    public void setActive(boolean b) {
        view.setActive(b);
    }

}