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
