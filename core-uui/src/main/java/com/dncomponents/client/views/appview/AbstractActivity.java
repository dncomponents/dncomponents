package com.dncomponents.client.views.appview;

import com.dncomponents.client.views.IsElement;

public abstract class AbstractActivity<V extends IsElement, P extends Place> implements Presenter {

    protected V view;
    protected P place;
    protected PlaceManager placeManager;
    protected AcceptsOneElement mainApp;

    public AbstractActivity(V view, P place) {
        this.view = view;
        this.place = place;
        if (view instanceof HasPresenter) {
            ((HasPresenter) view).setPresenter(this);
        }
    }

    protected void onStart() {
        mainApp.setElement(view);
    }

    /**
     * @return true to continue or false to stop
     */
    protected boolean onStop() {
        return true;
    }

    protected AcceptsOneElement getMainApp() {
        return mainApp;
    }

    void setMainApp(AcceptsOneElement mainApp) {
        this.mainApp = mainApp;
    }

    void setPlaceManager(PlaceManager placeManager) {
        this.placeManager = placeManager;
    }
}
