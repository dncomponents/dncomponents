package com.dncomponents.client.views.appview;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;

import java.util.HashMap;
import java.util.Map;

public class PlaceManager implements HasPlaceChangeHandler {

    private Map<String, Place.PlaceRegister> placeRegisterMap = new HashMap<>();

    private Map<Class<? extends Place>, Place.PlaceRegister> tokenRegisterMap = new HashMap<>();

    private Class<? extends Place> homePlace;

    private final AcceptsOneElement mainApp;

    private AbstractActivity currentActivity;

    private Place currentPlace;

    public PlaceManager(AcceptsOneElement mainApp) {
        this.mainApp = mainApp;
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                String token = event.getValue();
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
        return ensureHandlers().addHandler(ValueChangeEvent.getType(), handler);
    }

    @Override
    public void goTo(Place place, boolean fireEvent) {
        Place.PlaceRegister placeRegister = tokenRegisterMap.get(place.getClass());
        if (currentActivity != null && !currentActivity.onStop())
            return;
        AbstractActivity activity = placeRegister.getActivity(place);
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

    @Override
    public void fireEvent(GwtEvent<?> event) {
        ensureHandlers().fireEvent(event);
    }

    private HandlerManager handlerManager;

    protected HandlerManager ensureHandlers() {
        if (handlerManager == null) {
            handlerManager = new HandlerManager(this);
        }
        return handlerManager;
    }

    public String getHistoryToken(Place place) {
        Place.PlaceRegister placeRegister = tokenRegisterMap.get(place.getClass());
        String historyToken = placeRegister.getTokenFromPlace(place);
        return historyToken;
    }
}