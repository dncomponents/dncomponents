package com.dncomponents.client.views.appview;

import com.dncomponents.client.views.IsElement;

public class DefaultActivity extends AbstractActivity {

    public DefaultActivity(IsElement view, Place place) {
        super(view, place);
    }

    @Override
    protected void onStart() {
        mainApp.setElement(view);
    }

}
