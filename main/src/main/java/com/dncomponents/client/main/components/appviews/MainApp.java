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

package com.dncomponents.client.main.components.appviews;

import com.dncomponents.UiField;
import com.dncomponents.client.components.AbstractCellComponent;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.sidemenu.SideMenu;
import com.dncomponents.client.main.components.appviews.accordion.AccordionPlace;
import com.dncomponents.client.main.components.appviews.autocomplete.AutocompletePlace;
import com.dncomponents.client.main.components.appviews.button.ButtonPlace;
import com.dncomponents.client.main.components.appviews.checkbox.CheckBoxPlace;
import com.dncomponents.client.main.components.appviews.checkbox.RadioPlace;
import com.dncomponents.client.main.components.appviews.dropdown.DropdownPlace;
import com.dncomponents.client.main.components.appviews.forms.FormsPlace;
import com.dncomponents.client.main.components.appviews.list.ListPlace;
import com.dncomponents.client.main.components.appviews.modal.ModalPlace;
import com.dncomponents.client.main.components.appviews.popover.PopoverPlace;
import com.dncomponents.client.main.components.appviews.progress.ProgressPlace;
import com.dncomponents.client.main.components.appviews.sidemenu.SidemenuPlace;
import com.dncomponents.client.main.components.appviews.tab.TabPlace;
import com.dncomponents.client.main.components.appviews.table.TablePlace;
import com.dncomponents.client.main.components.appviews.tabletree.TableTreePlace;
import com.dncomponents.client.main.components.appviews.textbox.TextBoxPlace;
import com.dncomponents.client.main.components.appviews.tooltip.TooltipPlace;
import com.dncomponents.client.main.components.appviews.tree.TreePlace;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.appview.AcceptsOneElement;
import com.dncomponents.client.views.appview.PlaceManager;
import elemental2.dom.HTMLElement;
import elemental2.dom.Node;


//@UiTemplate("#main-app")
public class MainApp implements AcceptsOneElement {

    @UiField
    static HTMLElement contentWrapper;

    PlaceManager placeManager = new PlaceManager(this);
    @UiField
    public SideMenu<ItemId> side;

    {
        placeManager.register(AccordionPlace.AccordionPlaceRegister.instance);
        placeManager.register(AutocompletePlace.AutocompletePlaceRegister.instance);
        placeManager.register(AutocompletePlace.AutocompletePlaceRegister.instance);
        placeManager.register(ButtonPlace.ButtonPlaceRegister.instance);
        placeManager.register(CheckBoxPlace.CheckBoxPlaceRegister.instance);
        placeManager.register(RadioPlace.RadioPlaceRegister.instance);
        placeManager.register(DropdownPlace.DropDownPlaceRegister.instance);
        placeManager.register(ListPlace.ListPlaceRegister.instance);
        placeManager.register(TabPlace.TabPlaceRegister.instance);
        placeManager.register(TablePlace.TablePlaceRegister.instance);
        placeManager.register(TableTreePlace.TableTreePlaceRegister.instance);
        placeManager.register(TextBoxPlace.TextBoxPlaceRegister.instance);
        placeManager.register(TreePlace.TreePlaceRegister.instance);
        placeManager.register(ProgressPlace.ProgressPlaceRegister.instance);
        placeManager.register(TooltipPlace.TooltipPlaceRegister.instance);
        placeManager.register(PopoverPlace.PopoverPlaceRegister.instance);
        placeManager.register(ModalPlace.ModalPlaceRegister.instance);
        placeManager.register(SidemenuPlace.SidemenuPlaceRegister.instance);
        placeManager.register(TableTreePlace.TableTreePlaceRegister.instance);
        placeManager.register(FormsPlace.FormsPlaceRegister.instance);
        placeManager.setHomePlace(AccordionPlace.class);
    }

    HtmlBinder binder = HtmlBinder.create(MainApp.class, this);

    public MainApp() {
        binder.bind();
        side.setPlaceManager(placeManager);
        side.expandAll(false);
    }

    @Override
    public void setElement(IsElement element) {
        contentWrapper.innerHTML = "";
        contentWrapper.appendChild(element.asElement());
        AbstractCellComponent.resetScrollOnPage(contentWrapper);
        PR.prettyPrint();
    }

    public Node asNode() {
        return binder.asNode();
    }

}
