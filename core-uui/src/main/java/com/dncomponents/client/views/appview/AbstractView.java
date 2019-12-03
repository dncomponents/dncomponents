package com.dncomponents.client.views.appview;

import com.dncomponents.client.views.IsElement;

public abstract class AbstractView<P extends Presenter> implements IsElement, HasPresenter<P> {

    protected P presenter;

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }
}
