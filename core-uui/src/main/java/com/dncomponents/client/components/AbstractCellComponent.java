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

package com.dncomponents.client.components;

import com.dncomponents.client.components.core.*;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.cell.CellValueChangedHandler;
import com.dncomponents.client.components.core.events.cell.HasCellValueChangedHandlers;
import com.dncomponents.client.components.core.events.filters.Filter;
import com.dncomponents.client.components.core.events.form.HasModelChangedHandlers;
import com.dncomponents.client.components.core.events.form.ModelChangedHandler;
import com.dncomponents.client.components.core.events.row.*;
import com.dncomponents.client.components.core.events.rowdata.RowDataChangedEvent;
import com.dncomponents.client.components.core.events.rowdata.RowDataChangedHandler;
import com.dncomponents.client.components.core.events.table.SortEvent;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.core.events.value.HasValueSelection;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.views.core.pcg.ComponentUi;
import com.dncomponents.client.views.core.ui.list.ScrollView;
import elemental2.dom.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author nikolasavic
 */
public abstract class AbstractCellComponent<T, M, W extends ComponentUi<? extends ScrollView>> extends BaseComponent<Object, W> implements
        HasRowsData<T>, HasSelectionModel, HasModelChangedHandlers<T>, SortEvent.HasSortHandler,
        HasCellValueChangedHandlers<T>, HasValueSelection<List<T>>, HasRowRemovedHandlers<T>,
        HasRowAddedHandlers<T> {

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

    protected VirtualScroll virtualScroll;

    protected CellFactory<T, M, ? extends AbstractCellComponent<T, ?, ?>> defaultCellFactory;

    private boolean editable = true;

    private boolean cellEditMode = true;

    private boolean dragAndDropEnabled = false;

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


    //#region Handlers registrations
    public HandlerRegistration addRowDataChangedHandler(RowDataChangedHandler handler) {
        return handler.addTo(asElement());
    }

    @Override
    public HandlerRegistration addSortHandler(SortEvent.SortHandler handler) {
        return addHandler(handler);
    }

    @Override
    public HandlerRegistration addCellValueChangedHandler(CellValueChangedHandler<T> handler) {
        return addHandler(handler);
    }

    @Override
    public HandlerRegistration addModelChangedHandler(ModelChangedHandler<T> handler) {
        return handler.addTo(this.asElement());
    }

    @Override
    public HandlerRegistration addRowRemovedHandler(RowRemovedHandler<T> handler) {
        return handler.addTo(this.asElement());
    }

    @Override
    public HandlerRegistration addRowAddedHandler(RowAddedHandler<T> handler) {
        return handler.addTo(this.asElement());
    }

    /**
     * For performance reasons instead of adding handlers to each cell
     * only one is added to a root component and intercept events from the cells.
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
        asElement().addEventListener(handler.getType(), listener);
        return new HandlerRegistration() {
            @Override
            public void removeHandler() {
                asElement().removeEventListener(handler.getType(), listener);
            }
        };
    }
    //#endregion Handlers registrations

    public void refreshSelections() {
        for (BaseCell<T, M> visibleCell : visibleCells) {
            visibleCell.setSelection();
        }
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

    @Override
    public HasValue<List<T>> getHasValue() {
        return getSelectionModel().getHasValue();
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

    protected BaseCell createAndInitTempModelRowCell(T model) {
        CellConfig<T, M> cc = ensureRowCellConfig(model);
        BaseCell cell = cc.getCellFactory().getCell(new CellContext(cc, defaultCellFactory, model, this));
        initCell(cell, model, cc, this);
        cell.draw();
        return cell;
    }


    /**
     * @return unmodifiableList model list
     */
    @Override
    public List<T> getRowsData() {
        return rows;
//        return Collections.unmodifiableList(rows);
    }

    /**
     * Sets list of model values to display.
     */
    public void setRowsData(List<T> rows) {
        this.rows = new ArrayList<>(rows);
        this.rowsFiltered = new ArrayList<>(this.rows);
        RowDataChangedEvent.fire(this, rows.size());
    }

    @Override
    public void drawData() {
        view.getRootView().clear();
        visibleCells.clear();
        filterAndSort();
        displayFilteredData();
    }

    protected void addRow(T t) {
        insertRowP(t, rows.size(), true);
    }

    protected void insertRow(T t, int index) {
        insertRowP(t, index, true);
    }

    private BaseCell insertRowP(T t, int index, boolean fireEvent) {
        rows.add(index, t);
        rowsFiltered.add(index, t);
        final BaseCell cell = createAndInitModelRowCell(t);
        if (fireEvent)
            RowAddedEvent.fire(this, cell, (Consumer<T>) t1 -> {
                removeRowFromComp(cell);
                insertRowP(t1, 0, false).draw();
                drawData();
            }, () -> {
                removeRowFromComp(cell);
                drawData();
            });
        return cell;
    }

    private void removeRowFromComp(BaseCell<T, ?> cell) {
        T t = cell.getModel();
        cell.removeFromParent();
        rowsFiltered.remove(t);
        visibleCells.remove(cell);
        rows.remove(t);
    }

    protected void removeRow(T t) {
        BaseCell cell = getRowCell(t);
        if (cell != null) {
            final int index = rows.indexOf(t);
            RowRemovedEvent.fire(this, cell, () -> {
                insertRowP(t, index, false);
                drawData();
            });
            removeRowFromComp(cell);
        }
    }

    public void scrollIntoView(T t) {
        BaseCell baseCell = getRowCell(t);
        if (baseCell != null)
            baseCell.scrollInView();
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
     * note: To enable cell editing, field {@link CellConfig} must be defined.
     * see: {@link FieldSetter}
     * Also it is possible to turn on/of editing for individual columns see {@link CellConfig}
     * <p>
     *
     * @param editable <code>true</code> to enable editing, <code>false</code>
     *                 to disable it
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setDragAndDropEnabled(boolean dragAndDropEnabled) {
        this.dragAndDropEnabled = dragAndDropEnabled;
    }

    public boolean isDragAndDropEnabled() {
        return dragAndDropEnabled;
    }

    public void setCellEditMode(boolean cellEditMode) {
        this.cellEditMode = cellEditMode;
    }

    public boolean isCellEditMode() {
        return cellEditMode;
    }

    //#region filter and sorting

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
//        return filters
//                .stream()
//                .reduce(Predicate::and)
//                .orElse(o -> true);
        return filters
                .stream()
                .reduce(new BinaryOperator<Predicate>() {
                    @Override
                    public Predicate apply(Predicate predicate, Predicate predicate2) {
                        return predicate.and(predicate2);
                    }
                })
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

    //#endregion
    void newBlock() {
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


    public void setScrollHeight(String height) {
        view.getRootView().setScrollHeight(height);
    }

    public static void resetScrollOnPage(HTMLElement el) {
        NodeList<Element> childNodes = el.querySelectorAll("*");
        for (int i = 0; i < childNodes.length; i++) {
            final Object o = BaseComponent.allComponents.get(childNodes.getAt(i));
            if (o instanceof AbstractCellComponent) {
                ((AbstractCellComponent<?, ?, ?>) o).resetScrollPosition();
            }
        }
    }
}
