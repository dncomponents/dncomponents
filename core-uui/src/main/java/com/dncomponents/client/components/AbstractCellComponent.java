package com.dncomponents.client.components;

import com.dncomponents.client.components.core.*;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.components.events.CellEditEvent;
import com.dncomponents.client.components.events.RowCountChangedEvent;
import com.dncomponents.client.components.filters.Filter;
import com.dncomponents.client.components.table.header.filterevents.SortEvent;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.views.core.pcg.ComponentUi;
import com.dncomponents.client.views.core.ui.list.ScrollView;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;
import elemental2.dom.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author nikolasavic
 */
public abstract class AbstractCellComponent<T, M, W extends ComponentUi<? extends ScrollView>> extends BaseComponent<Object, W> implements
        HasRows<T>,
        HasSelectionModel,
        SortEvent.HasSortHandler,
        CellEditEvent.HasCellEditHandler<T> {

    protected List<T> rows = new ArrayList<>();

    protected List<? extends BaseCell<T, M>> visibleCells = new ArrayList<>();

    //selection
    protected DefaultMultiSelectionModel<T> selectionModel;


    protected CellConfig<T, M> rowCellConfig;

    //filters
    protected List<Predicate> filters = new ArrayList<>();
    //comparators
    protected List<Comparator> comparators = new ArrayList<>();

    protected List<T> rowsFiltered = new ArrayList<>();
//    protected boolean filterOn = false;

    protected VirtualScroll virtualScroll;

    protected CellFactory<T, M, ? extends AbstractCellComponent<T, ?, ?>> defaultCellFactory;

    private boolean editable = true;

    public AbstractCellComponent(W ui) {
        super(ui);
    }

    public AbstractCellComponent(W ui, List<T> rows) {
        super(ui);
        this.rows = rows;
    }

    static AbstractCell initCell(AbstractCell cell, Object model, CellConfig config, AbstractCellComponent owner) {
        cell.setCellConfig(config);
        cell.setModel(model);
        cell.setOwner(owner);
        cell.bind();
        return cell;
    }

    static AbstractCell initCellList(AbstractCell cell, Object model, CellConfig config, List visibleCells, AbstractCellComponent owner) {
        initCell(cell, model, config, owner);
        visibleCells.add(cell);
        return cell;
    }

    static AbstractCell initCellListDraw(AbstractCell cell, Object model, CellConfig config, List visibleCells, AbstractCellComponent owner) {
        AbstractCell cell1 = initCellList(cell, model, config, visibleCells, owner);
        cell1.draw();
        return cell1;
    }


    //region Handlers registrations


    @Override
    public HandlerRegistration addRowCountChangedHandler(RowCountChangedEvent.Handler handler) {
        return ensureHandlers().addHandler(RowCountChangedEvent.getType(), handler);
    }

    @Override
    public HandlerRegistration addSortHandler(SortEvent.SortHandler handler) {
        return ensureHandlers().addHandler(SortEvent.getType(), handler);
    }

    @Override
    public HandlerRegistration addCellEditHandler(CellEditEvent.CellEditHandler<T> handler) {
        return ensureHandlers().addHandler(CellEditEvent.getType(), handler);
    }

    /**
     * @return unmodifiableList model list
     */
    @Override
    public List<T> getRowsData() {
        return rows; //TODO not sure if to keep unmodifiableList
//        return Collections.unmodifiableList(rows);
    }

    /**
     * Sets list of model values to display.
     *
     * @param rows
     */
    public void setRowsData(List<T> rows) {
        this.rows = new ArrayList<>(rows);
        this.rowsFiltered = new ArrayList<>(this.rows);
        RowCountChangedEvent.fire(this, rows.size(), true);
    }

    @Override
    public void drawData() {
        view.getRootView().clear();
        visibleCells.clear();
        filterAndSort();
        displayFilteredData();
    }

    /**
     * Displays data after all filters and sorting are applied.
     */
    protected void displayFilteredData() {
        displayFilteredDataInVirtualScroll();
    }

    /**
     * If rows number reaches {@link VirtualScroll#scrollStarts} displays rows data in virtual scroll
     */
    protected void displayFilteredDataInVirtualScroll() {
        if (rowsFiltered.size() > ensureVirtualScroll().scrollStarts)
            ensureVirtualScroll().drawData();
        else {
            removeScrollHandler();
            rowsFiltered.forEach(t -> createAndInitModelRowCell(t));
        }
    }

    @Override
    public int getRowSize() {
        return rows.size();
    }

    public BaseCell<T, M> getRowCell(int row) {
        return visibleCells.get(row);
    }


    public BaseCell<T, M> getRowCell(T t) {
        return visibleCells.stream()
                .filter(cell -> cell.getModel() == t)
                .findFirst().orElse(null);
    }

    public List<? extends BaseCell> getRowCells(List<T> list) {
        return list.stream()
                .map(model -> getRowCell(model))
                .collect(Collectors.toList());
    }

    @Override
    public DefaultMultiSelectionModel<T> getSelectionModel() {
        return selectionModel;
    }

    protected void setSelectionModel(DefaultMultiSelectionModel selectionModel) {
        //On selection change updates cells selection view
        this.selectionModel = selectionModel;
        getSelectionModel().addSelectionHandler(event -> getCells().forEach(BaseCell::setSelection));
    }

    public List<? extends BaseCell<T, M>> getCells() {
        return visibleCells;
    }

    public int getCellsSize() {
        return visibleCells.size();
    }


    protected BaseCell createAndInitModelRowCell(T model) {
        CellConfig<T, M> cc = ensureRowCellConfig(model);
        BaseCell cell = cc.getCellFactory().getCell(new CellContext(cc, defaultCellFactory, model, this));
        initCellListDraw(cell, model, cc, visibleCells, this);
        view.getRootView().addItem(cell);
        return cell;
    }


//    protected abstract CellConfig getDefaultCellConfig(T model);

    @Override
    public void addRow(T t) {
        rows.add(t);
        rowsFiltered.add(t);
        createAndInitModelRowCell(t);
    }

    @Override
    public void insertRow(T t, int index) {
        rows.add(index, t);
        rowsFiltered.add(index, t);
        createAndInitModelRowCell(t);
    }

    public void scrollIntoView(T t) {
        BaseCell baseCell = getRowCell(t);
        if (baseCell != null)
            baseCell.scrollInView();
    }

    //TODO add insert row fn
    @Override
    public void removeRow(T t) {
        BaseCell cell = getRowCell(t);
        if (cell != null) {
            cell.removeFromParent();
            visibleCells.remove(cell);
            rows.remove(t);
            rowsFiltered.remove(t);
        }
//        RowCountChangedEvent.fire(this, rows.size(), false);
    }

    protected VirtualScroll ensureVirtualScroll() {
        if (virtualScroll == null)
            virtualScroll = new VirtualScroll(this, view.getRootView());
        virtualScroll.scrollingStarts();
        return virtualScroll;
    }

    private void removeScrollHandler() {
        if (virtualScroll != null)
            virtualScroll.removeScrollHandler();
    }

    public void removeRows(List<T> list) {
        getSelectionModel().setSelected(list, false, true);
        List listt = new ArrayList<>(list);
        Iterator<T> iter = listt.iterator();
        while (iter.hasNext()) {
            T t = iter.next();
            removeRow(t);
        }
    }

    @Override
    protected W getView() {
        return super.getView();
    }


    /**
     * For performance reasons instead of adding handlers to each cell
     * only one is added to a root component and intercept events from the cells.
     *
     * @param handler Cell event handler
     */
    public HandlerRegistration addCellHandler(BaseEventListener handler) {
        EventListener listener = new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                for (BaseCell baseCell : getCells()) {
                    if (baseCell.asElement().contains(((Node) evt.target))) {
                        handler.handleEvent(evt);
                    }
                }
            }
        };
        if (!DomUtil.Junit)
            asElement().addEventListener(handler.getType(), listener);
        return new HandlerRegistration() {
            @Override
            public void removeHandler() {
                asElement().removeEventListener(handler.getType(), listener);
            }
        };
    }

    public BaseCell<T, ?> getCell(HTMLElement element) {
        for (BaseCell baseCell : getCells()) {
            if (baseCell.asElement().contains(element)) {
                return baseCell;
            }
        }
        return null;
    }

    public BaseCell<T, ?> getCell(Event evt) {
        for (BaseCell baseCell : getCells()) {
            if (baseCell.asElement().contains(((Node) evt.target))) {
                return baseCell;
            }
        }
        return null;
    }

    /**
     * Gets whether cells for this component can be editable.
     *
     * @return <code>true</code> if the component is editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Sets whether this component can have editable cells {@link BaseCell}
     * <p>
     * After setting this value, call {@link #drawData()} to change takes an effect.
     * <p>
     * note: To enable cell editing, field {@link CellConfig#} must be defined.
     * see: {@link FieldSetter}
     * Also it is possible to turn on/of editing for individual columns see {@link CellConfig#}
     * <p>
     *
     * @param editable <code>true</code> to enable editing, <code>false</code>
     *                 to disable it
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    //region filter and sorting

    /**
     * Applies filters and sort data.
     * Filtered and sorted results are stored to {@link AbstractCellComponent#rowsFiltered}
     * while {@link AbstractCellComponent#rows} remains the same.
     */
    protected void filterAndSort() {
        rowsFiltered.clear();
        rowsFiltered.addAll((ArrayList) (rows.stream()
                .filter(onePredicate())
                .sorted(oneComparator())
                .collect(Collectors.toList())));
    }

    /**
     * Creates unique comparator from comparators list {@link AbstractCellComponent#comparators}
     *
     * @return {@link Comparator}
     */
    protected Comparator oneComparator() {
        return comparators
                .stream()
                .reduce((o1, o2) -> o1.thenComparing(o2))
                .orElse((o11, o21) -> 0);
    }

    /**
     * Creates unique predicate from predicate list {@link AbstractCellComponent#filters}
     *
     * @return {@link Predicate}
     */
    protected Predicate onePredicate() {
        return filters
                .stream()
                .reduce(Predicate::and)
                .orElse(o -> true);
    }


    /**
     * Registers filter. To apply registered filter either run {@link #drawData()}
     *
     * @param predicate to be registered
     */
    public void addFilter(Predicate<T> predicate) {
        filters.add(predicate);
        if (predicate instanceof Filter) {
            ((Filter) predicate).addFilterHandler(event -> drawData());
        }
    }

    protected boolean hasFilters() {
        return !filters.isEmpty();
    }


    /**
     * Gets all model items after applying filtering and sorting to {@link #rows}.
     * This list is sub-set of {@link #rows} which holds original model values.
     *
     * @return list of filtered model items.
     */
    public List<T> getRowsFiltered() {
        return rowsFiltered;
    }

    /**
     * Gets all filters registered to this component
     *
     * @return filters
     */
    public List<Predicate> getFilters() {
        return filters;
    }

    /**
     * Removes filter from list of registered filters.
     * Call {@link #drawData()} to see changes.
     *
     * @param rowFilter
     */
    public void removeFilter(Filter rowFilter) {
        filters.remove(rowFilter);
    }

    /**
     * Clears all registered filters of this component.
     * Call {@link #drawData()} to see changes.
     */
    public void clearAllFilters() {
        filters.clear();
    }

    /**
     * Ads {@link Comparator} to comparator lists.
     *
     * @param comparator
     * @see AbstractCellComponent#oneComparator() ()
     */
    protected void addComparator(Comparator<T> comparator) {
        comparators.add(comparator);
    }

    /**
     * Removes comparator from list.
     *
     * @param comparator to remove
     */
    protected void removeComparator(Comparator<T> comparator) {
        comparators.remove(comparator);
    }

    /**
     * Clears all comparators.
     */
    protected void clearComparators() {
        comparators.clear();
    }

    protected List<Comparator> getComparators() {
        return comparators;
    }

    //endregion

    private HandlerManager handlerManager;

    protected HandlerManager ensureHandlers() {
        if (handlerManager == null) {
            handlerManager = new HandlerManager(this);
        }
        return handlerManager;
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        if (handlerManager != null) {
            handlerManager.fireEvent(event);
        }
    }

    void newBlock() {
//        ListTreeCellNavigator selectionModel = getSelectionModel();
//        GWT.log(selectionModel () + "");
    }

    public CellConfig<T, M> getRowCellConfig() {
        return ensureRowCellConfig();
    }

    protected CellConfig<T, M> ensureRowCellConfig() {
        if (rowCellConfig == null)
            rowCellConfig = new CellConfig<>(t -> null);
        return rowCellConfig;
    }

    protected CellConfig<T, M> ensureRowCellConfig(T model) {
        if (rowCellConfig == null)
            rowCellConfig = new CellConfig<>(t -> null);
        return rowCellConfig;
    }

    public void resetScrollPosition() {
        getView().getRootView().resetScrollTop(null);
    }

    public void resetScrollTo(Double value) {
        getView().getRootView().resetScrollTop(value);
    }

}