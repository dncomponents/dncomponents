/*
 * Copyright 2024 dncomponents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dncomponents.client.main.components.appviews.accordion;

import com.dncomponents.UiField;
import com.dncomponents.client.components.accordion.Accordion;
import com.dncomponents.client.components.accordion.AccordionItem;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.entities.ItemIdTitle;
import com.dncomponents.client.components.core.events.selection.SelectionEvent;
import com.dncomponents.client.components.core.events.selection.SelectionHandler;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.core.events.value.ValueChangeHandler;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.main.testing.Person;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.MouseEvent;

import java.util.List;


public class AccordionAppView implements IsElement {
    private static AccordionAppView instance;

    @UiField
    HTMLElement root;
    @UiField
    TextArea ta;
    @UiField(provided = true)
    Accordion<Person> eventsAccordion = new Accordion<>();
    @UiField
    Accordion<ItemIdTitle> accordion;
    @UiField
    Accordion<Fruit> accordionRenderer;
    @UiField
    CheckBox allClosed;
    @UiField
    CheckBox multiExpand;
    @UiField
    public Button bbb;
    @UiField
    public Button ha;
    @UiField
    public TextArea ta2;
    @UiField
    Button brn;
    @UiField
    HTMLElement span;


    public AccordionAppView() {
        HtmlBinder.create(AccordionAppView.class, this).bind();
        initDemo();
        initEvents();
        initRenderer();
    }


    private void initEvents() {
        eventsAccordion.setItemRenderer((people, slots) -> {
            slots.getTitle().innerHTML = people.getName() + "";
            slots.getContent().innerHTML = people.getAge() + "";
        });
        brn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                span.innerHTML = "sdlkfjlksdjf";
                span.remove();
            }
        });
        {
            final Person person = new Person("Joe Paul");
            person.setAge(22);
            eventsAccordion.addItem(new AccordionItem<>(eventsAccordion, person));
        }
        {
            final Person person = new Person("Mike Smith");
            person.setAge(32);
            eventsAccordion.addItem(new AccordionItem<>(eventsAccordion, person));
        }
        {
            final Person person = new Person("Marry Johnson");
            person.setAge(42);
            eventsAccordion.addItem(new AccordionItem<>(eventsAccordion, person));
        }
        eventsAccordion.addSelectionHandler(event -> {
            ta.setValue("");
            event.getSelectedItem().forEach(e -> ta.append(e.getUserObject().getName() +
                                                           " " + e.getUserObject().getAge() + "\n"));
        });
        eventsAccordion.getEntitySelectionModel().addSelectionHandler(event -> {
            for (Person people : event.getSelectedItem())
                ta.append("entity selection: " + people + " \n");
        });
    }


    private void initDemo() {
        accordion.addSelectionHandler(new SelectionHandler<List<AccordionItem<ItemIdTitle>>>() {
            @Override
            public void onSelection(SelectionEvent<List<AccordionItem<ItemIdTitle>>> event) {
                ta2.setValue("");
                final List<AccordionItem<ItemIdTitle>> selectedItem = event.getSelectedItem();
                final AccordionItem<ItemIdTitle> itemIdTitleAccordionItem = selectedItem.get(0);
                for (AccordionItem<ItemIdTitle> item : selectedItem) {
                    ItemIdTitle userObject = item.getUserObject();
                    if (userObject != null)
                        ta2.append(userObject.getId() + "\n");
                }
            }
        });
        allClosed.setValue(accordion.isAllClosed(), false);
        allClosed.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                accordion.setAllClosed(event.getValue());
            }
        });
        multiExpand.setValue(accordion.isMultiExpand(), false);
        multiExpand.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                accordion.setMultiExpand(event.getValue());
            }
        });
    }

    private void initRenderer() {
        accordionRenderer.setItemRenderer((userObject, slots) -> {
            slots.getTitle().innerHTML = "<h4>" + userObject.getName() + "</h4>";
            slots.getContent().innerHTML = userObject.getDescription();
        });
        accordionRenderer.addItem(new AccordionItem<>(accordionRenderer, new Fruit("Apple", "apple fruit")));
        accordionRenderer.addItem(new AccordionItem<>(accordionRenderer, new Fruit("Orange", "Orange fruit")));
        accordionRenderer.addItem(new AccordionItem<>(accordionRenderer, new Fruit("Banana", "Banana fruit")));
        AccordionItem<Fruit> accordionItem = new AccordionItem<>(accordionRenderer);
        accordionItem.setRenderer((fruit, slots) -> {
            slots.getTitle().innerHTML = "<b>" + fruit.getName() + "</b>";
            slots.getTitle().style.color = "red";
            slots.getContent().appendChild(new Button(fruit.getDescription()).asElement());
        });
        accordionItem.setUserObject(new Fruit("Pineapple", "sweet fruit"));
        accordionRenderer.addItem(accordionItem);
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }


    public static AccordionAppView getInstance() {
        if (instance == null) instance = new AccordionAppView();
        return instance;
    }
}
