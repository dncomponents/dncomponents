package com.dncomponents.client.components.pager;


import com.dncomponents.client.components.AbstractCellComponent;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.pager.PagerUi;
import com.dncomponents.client.views.core.ui.pager.PagerView;

/**
 * @author nikolasavic
 */
public class Pager<V extends PagerView> extends BaseComponent<Object, PagerUi<V>> implements PagerView.Presenter {

    private static final int DEFAULT_VISIBLE_NUMBER = 20;
    protected int rowSize, numberOfPages, currentPage, from, to;
    protected AbstractCellComponent owner;
    int visibleRowNumber = DEFAULT_VISIBLE_NUMBER;

    public Pager() {
        this(Ui.get().getPagerUi());
        view.getRootView().setPresenter(this);
    }

    public Pager(PagerUi ui) {
        super(ui);
        view.getRootView().setPresenter(this);
    }


    protected void calculate() {
        numberOfPages = (int) Math.ceil((double) rowSize / visibleRowNumber);
        if (currentPage >= numberOfPages || currentPage < 0) currentPage = 0;
        from = currentPage * visibleRowNumber;
        if (currentPage == numberOfPages - 1 || numberOfPages == 0) {
            to = from + (rowSize - from);
        } else {
            to = from + visibleRowNumber;
        }
    }

    public void setCurrentPage(int page) {
        if (page < 0 || page >= numberOfPages) return;
        boolean update = page != currentPage;
        this.currentPage = page;
        if (update) {
            calculate();
            updateView();
            owner.drawData();
        }
    }

    public void setCurrentPageNoUpdate(int page) {
        if (page < 0 || page >= numberOfPages) return;
        this.currentPage = page;
        calculate();
    }

    protected void updateView() {
        view.getRootView().setPageNumber(currentPage);
        view.getRootView().enableNext(hasNext());
        view.getRootView().enablePrevious(hasPrevious());
        view.getRootView().setText(from + " - " + to + " of " + rowSize + " items");
    }

    public int getPageFromIndex(int index) {
        if (index > rowSize)
            return new Double(Math.floor(rowSize / visibleRowNumber)).intValue();
        else if (index < 0) return 0;
        else
            return new Double(Math.floor(index / visibleRowNumber)).intValue();
    }

    public void setPageFromIndex(int index) {
        int page = getPageFromIndex(index);
        if (page != currentPage)
            setCurrentPage(page);
    }

    public boolean isPageBound(int index) {
        return (index % visibleRowNumber == 0 && index != 0);
    }

    public int getVisibleRowNumber() {
        return visibleRowNumber;
    }

    public void setVisibleRowNumber(int visibleRowNumber) {
        this.visibleRowNumber = visibleRowNumber;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public boolean hasNext() {
        return currentPage + 1 < numberOfPages;
    }

    public boolean hasPrevious() {
        return currentPage > 0;
    }

    public void next() {
        if (hasNext())
            setCurrentPage(currentPage + 1);
    }

    public void nextNoUpdate() {
        if (hasNext())
            setCurrentPageNoUpdate(currentPage + 1);
    }

    public void previous() {
        if (hasPrevious())
            setCurrentPage(currentPage - 1);
    }

    public void first() {
        setCurrentPage(0);
    }

    public void last() {
        setCurrentPage(numberOfPages - 1);
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public void recalculate() {
        rowSize = owner.getRowsFiltered().size();
        calculate();
        updateView();
    }

    public void setOwner(AbstractCellComponent owner) {
        this.owner = owner;
    }


    @Override
    protected PagerUi getView() {
        return super.getView();
    }
}