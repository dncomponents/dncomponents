package com.dncomponents.material.client.multilevel;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.popover.Popper;
import com.dncomponents.client.dom.handlers.MouseEnterHandler;
import com.dncomponents.client.dom.handlers.MouseLeaveHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.dropdown.DropDownTreeNodePanelView;
import com.google.gwt.user.client.Timer;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class DropDownTreeNodePanelViewImpl implements DropDownTreeNodePanelView {


    private HtmlBinder uiBinder = HtmlBinder.get(DropDownTreeNodePanelViewImpl.class, this);

    @UiField
    public HTMLElement root;
    @UiField
    public HTMLElement listRoot;
    @UiStyle
    String showStyle;
    Popper popper;


    public DropDownTreeNodePanelViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        Timer timer = new Timer() {
            @Override
            public void run() {
                root.style.left = "-30px !important";
            }
        };
        timer.schedule(200);

//        root.style.left = "-1px !important";

    }

    private void init() {
        //init code here
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public void add(IsElement item) {
        listRoot.appendChild(item.asElement());
    }

    @Override
    public void clear() {
        listRoot.innerHTML = "";
    }

    @Override
    public void show(IsElement relativeTo, boolean b, String orientation) {
        if (b)
            root.classList.add(showStyle);
        else
            root.classList.remove(showStyle);
        if (popper == null) {
            Popper.Defaults def = Popper.Defaults.create();
            def.setPlacement(orientation);
            popper = new Popper(relativeTo.asElement(), this.asElement(), def);
        }
        popper.scheduleUpdate();

        Timer timer = new Timer() {
            @Override
            public void run() {
                if (orientation.equalsIgnoreCase("right-start")) {
                    DropDownTreeNodePanelViewImpl.this.asElement().style.left = "";
                    DropDownTreeNodePanelViewImpl.this.asElement().style.top = "20px";
                }
            }
        };
        timer.schedule(200);
    }

    @Override
    public void addMouseEnterHandler(MouseEnterHandler mouseEnterHandler) {
        mouseEnterHandler.addTo(asElement());
    }

    @Override
    public void addMouseLeaveHandler(MouseLeaveHandler mouseLeaveHandler) {
        mouseLeaveHandler.addTo(asElement());
    }

}