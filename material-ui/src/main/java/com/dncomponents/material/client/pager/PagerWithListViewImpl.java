package com.dncomponents.material.client.pager;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.pager.PagerWithListView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

import java.util.ArrayList;

/**
 * @author nikolasavic
 */
public class PagerWithListViewImpl implements PagerWithListView {

    @UiField
    HTMLElement root;

    Presenter presenter;


    HtmlBinder uiBinder = HtmlBinder.create(PagerWithListViewImpl.class, this);


    public PagerWithListViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void setText(String s) {

    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setPageNumber(int pageNumber) {

    }

    @Override
    public void enablePrevious(boolean b) {

    }

    @Override
    public void enableNext(boolean b) {

    }

    @Override
    public void setNumberOfPages(int numberOfPages) {

    }

    @Override
    public void update(ArrayList<Integer> itemsList) {

    }

    @Override
    public void addItem(IsElement element) {
        asElement().appendChild(element.asElement());
    }

    @Override
    public void clear() {
        asElement().innerHTML = "";
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
