package com.dncomponents.bootstrap.client.multilevel;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.dropdown.DropDownView;
import com.google.gwt.user.client.Command;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;
import elemental2.dom.Node;

/**
 * @author nikolasavic
 */
@UiTemplate
public class DropDownMultilevelViewImpl implements DropDownView {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement button;
    @UiField("dropdown-menu")
    HTMLElement dropDownMenu;
    @UiStyle
    String dropDownMenuShowStyle;

    HtmlBinder uiBinder = HtmlBinder.get(DropDownMultilevelViewImpl.class, this);

    public DropDownMultilevelViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public DropDownMultilevelViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void addClickOnButton(BaseEventListener handler) {
        handler.addTo(button);
    }

    @Override
    public void showList(boolean b) {
        if (b)
            dropDownMenu.classList.add(dropDownMenuShowStyle);
        else
            dropDownMenu.classList.remove(dropDownMenuShowStyle);
    }

    @Override
    public void addItem(IsElement item) {
        dropDownMenu.appendChild(item.asElement());
    }

    @Override
    public void removeItem(IsElement item) {
        item.asElement().remove();
    }

    @Override
    public void setButtonHtmlContent(HTMLElement content) {
        button.innerHTML = "";
        button.appendChild(content);
    }

    @Override
    public IsElement getRelativeElement() {
        return () -> button;
    }

    @Override
    public void setButtonContent(String content) {
        button.innerHTML = content;
    }

    @Override
    public void addClickOutOfButton(Command command) {
        ((ClickHandler) mouseEvent -> {
            if (!root.contains((Node) mouseEvent.target)) {
                command.execute();
            }
        }).addTo(DomGlobal.document.body);
    }

    @Override
    public void addDropDownPanel(IsElement dropDownPanel) {
        root.appendChild(dropDownPanel.asElement());
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
