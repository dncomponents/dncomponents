package com.dncomponents.client.views.appview;

public interface HasPresenter<P extends Presenter> {
    void setPresenter(P presenter);
}
