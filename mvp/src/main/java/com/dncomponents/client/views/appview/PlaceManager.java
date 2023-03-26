/*
 * Copyright 2023 dncomponents
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

package com.dncomponents.client.views.appview;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.core.events.value.ValueChangeHandler;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.History;
import elemental2.dom.CustomEvent;
import elemental2.dom.HTMLElement;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class PlaceManager implements HasPlaceChangeHandler {

    private Map<String, Place.PlaceRegister> placeRegisterMap = new HashMap<>();

    private Map<Class<? extends Place>, Place.PlaceRegister> tokenRegisterMap = new HashMap<>();

    private Class<? extends Place> homePlace;

    private final AcceptsOneElement mainApp;

    private AbstractActivity currentActivity;

    private Place currentPlace;

    private HTMLElement bus;

    public PlaceManager(AcceptsOneElement mainApp) {
        this.mainApp = mainApp;
        History.addValueChangeHandler(new Consumer<String>() {
            @Override
            public void accept(String s) {
                String token = s;
                if (token == null)
                    return;
                String filteredToken = token;
                if (token.contains(Place.PlaceRegister.DIVIDER))
                    filteredToken = token.substring(0, token.indexOf(Place.PlaceRegister.DIVIDER));
                Place.PlaceRegister placeRegister;
                if (token.isEmpty() && homePlace != null) {
                    placeRegister = tokenRegisterMap.get(homePlace);
                    History.newItem(placeRegister.getHistoryToken(), false);
                } else
                    placeRegister = placeRegisterMap.get(filteredToken);
                if (placeRegister == null)
                    return;
                Place place = placeRegister.getPlaceFromToken(token);
                setPlace(place, true);
//                getMainApp().showMenu(place);
                if (currentActivity != null && !currentActivity.onStop())
                    return;
                AbstractActivity activity = placeRegister.getActivity(place);
                activity.setPlaceManager(PlaceManager.this);
                activity.setMainApp(getMainApp());
                activity.onStart();
                currentActivity = activity;
            }
        });
    }

    public void register(Place.PlaceRegister placeRegister) {
        placeRegisterMap.put(placeRegister.getHistoryToken(), placeRegister);
        tokenRegisterMap.put(placeRegister.forPlace(), placeRegister);
    }

    public AcceptsOneElement getMainApp() {
        return mainApp;
    }

    public void setHomePlace(Class<? extends Place> homePlace) {
        this.homePlace = homePlace;
    }

    public Place getCurrentPlace() {
        return currentPlace;
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Place> handler) {
        return handler.addTo(ensureHandlers());
    }

    @Override
    public void goTo(Place place, boolean fireEvent) {
        Place.PlaceRegister placeRegister = tokenRegisterMap.get(place.getClass());
        if (currentActivity != null && !currentActivity.onStop())
            return;
        AbstractActivity activity = placeRegister.getActivity(place);
        activity.setPlaceManager(this);
        activity.setMainApp(getMainApp());
        activity.onStart();
        currentActivity = activity;
        History.newItem(placeRegister.getTokenFromPlace(place), false);
        setPlace(place, fireEvent);
    }

    private void setPlace(Place place, boolean fireEvent) {
        Place oldValue = getCurrentPlace();
        this.currentPlace = place;
        if (fireEvent) {
            Place newValue = getCurrentPlace();
            ValueChangeEvent.fireIfNotEqual(this, oldValue, newValue);
        }
    }

    protected HTMLElement ensureHandlers() {
        if (bus == null)
            bus = new DomUtil().createDiv();
        return bus;
    }

    public String getHistoryToken(Place place) {
        Place.PlaceRegister placeRegister = tokenRegisterMap.get(place.getClass());
        String historyToken = placeRegister.getTokenFromPlace(place);
        return historyToken;
    }

    @Override
    public void fireEvent(CustomEvent event) {
        ensureHandlers().dispatchEvent(event);
    }
}
