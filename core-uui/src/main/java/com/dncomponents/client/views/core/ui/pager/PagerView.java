package com.dncomponents.client.views.core.ui.pager;


import com.dncomponents.client.views.core.pcg.View;

/**
 * @author nikolasavic
 */
public interface PagerView extends View {
    void setText(String s);

    void setPresenter(Presenter presenter);

    void setPageNumber(int pageNumber);

    void enablePrevious(boolean b);

    void enableNext(boolean b);

    interface Presenter {
        void previous();

        void next();

        void first();

        void last();

        void setCurrentPage(int page);
    }

}
