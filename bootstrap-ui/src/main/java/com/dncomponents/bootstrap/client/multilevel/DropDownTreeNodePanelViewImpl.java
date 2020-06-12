package com.dncomponents.bootstrap.client.multilevel;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.popover.Popper;
import com.dncomponents.client.dom.handlers.MouseEnterHandler;
import com.dncomponents.client.dom.handlers.MouseLeaveHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.dropdown.DropDownTreeNodePanelView;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

public class DropDownTreeNodePanelViewImpl implements DropDownTreeNodePanelView {


    private HtmlBinder uiBinder = HtmlBinder.get(DropDownTreeNodePanelViewImpl.class, this);

    @UiField
    public HTMLElement root;

    Popper popper;


    public DropDownTreeNodePanelViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        DomGlobal.setTimeout(e -> root.style.left = "-30px !important", 200);
    }


    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public void add(IsElement item) {
        root.appendChild(item.asElement());
    }

    @Override
    public void clear() {
        root.innerHTML = "";
    }

    @Override
    public void show(IsElement relativeTo, boolean b, String orientation) {
        if (b)
            root.classList.add("show");
        else
            root.classList.remove("show");
        if (popper == null) {
            Popper.Defaults def = Popper.Defaults.create();
            def.setPlacement(orientation);
            popper = new Popper(relativeTo.asElement(), this.asElement(), def);
        }
        popper.scheduleUpdate();
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