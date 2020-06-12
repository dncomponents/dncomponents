package com.dncomponents.client.components;

import com.dncomponents.client.components.core.*;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.core.entities.RowItemId;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.list.ListTreeMultiSelectionModel;
import com.dncomponents.client.components.pager.Pager;
import com.dncomponents.client.components.table.AbstractHeaderCell;
import com.dncomponents.client.components.table.RowTableCellFactory;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.table.columnclasses.FooterCellFactory;
import com.dncomponents.client.components.table.header.HeaderCellHolder;
import com.dncomponents.client.components.table.header.HeaderTableFilterCell;
import com.dncomponents.client.components.table.header.HeaderTableTextCell;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.table.TableUi;
import elemental2.dom.EventListener;
import elemental2.dom.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author nikolasavic
 */
public class Table<T> extends AbstractCellComponent<T, List, TableUi> implements HasRowsDataList<T> {

    public TreeGroupBy groupBy;

    public TreeGroupBy getGroupBy() {
        if (groupBy == null)
            groupBy = new TreeGroupBy(this);
        return groupBy;
    }

    Pager pager;
    protected List<ColumnConfig> columnConfigs = new ArrayList<>();
    //    public boolean expandAllGroupBy = false;
    public HeaderCellHolder headerCellHolder = new HeaderCellHolder(this, groupBy);

    TableBar tableBar;
    protected final CellFactory<T, ?, ? extends AbstractCellComponent<T, ?, ?>> valueCellFactory;

    {
        ensureRowCellConfig().setFieldGetter(t -> columnConfigs.stream().map(c -> c.getFieldGetter().apply(t)).collect(Collectors.toList()));
        setSelectionModel(new ListTreeMultiSelectionModel<>(this, this.getView().getRootView()));
        defaultCellFactory = (RowTableCellFactory<T>) context -> new RowTable<>();
        valueCellFactory = new CellFactory<T, Object, AbstractCellComponent<T, ?, ?>>() {
            @Override
            public BaseCell<T, Object> getCell(CellContext<T, Object, AbstractCellComponent<T, ?, ?>> c) {
                return new TableCell<>();
            }
        };
        ensureRowCellConfig().setCellFactory(new CellFactory<T, List, AbstractCellComponent<T, ?, ?>>() {
            @Override
            public BaseCell<T, List> getCell(CellContext<T, List, AbstractCellComponent<T, ?, ?>> c) {
                return new RowTable<>();
            }
        });
    }

    List<T> rowExpanderList = new ArrayList<>();

    // popup editing
    private boolean popupEditing;


    public Table(TableUi ui, List<T> rows) {
        super(ui, rows);
    }

    public Table(TableUi ui) {
        super(ui);
    }

    public Table() {
        super(Ui.get().getTableUi());
    }

    @Override
    public void drawData() {
        if (pager != null)
            pager.recalculate();
        addHeader();
        addFooter();
        setColumnWidths();
        if (headerCellHolder.filterData()) {
            return;
        }
        if (groupBy != null)
            groupBy.ensureVirtualScroll().setEnabled(false);
        ensureVirtualScroll().setEnabled(true);
        super.drawData();
    }

    /**
     * Next time {drawData} is called, header will be drawn too but only once.
     * Every sequent call will not redraw header.
     */
    public void drawHeader() {
        addHeader = false;
    }

    //header
    List<AbstractHeaderCell> headerItems = new ArrayList<>();

    //header is added only once. For situation where we need to update header call it explicitly.
    boolean addHeader;

    protected void addHeader() {
        if (addHeader)
            return;
        addHeader = true;
        headerItems.clear();
        view.getRootView().clearHeaders();
        for (ColumnConfig config : columnConfigs) {
            if (!config.isVisible()) continue;
            initHeader(config);
        }
        if (headerRenderer != null) {
            view.getRootView().getHeaderRow().innerHTML = "";
            headerRenderer.render(view.getRootView().getHeaderRow(), headerItems, this);
        }
    }

    private void initHeader(ColumnConfig config) {
        AbstractHeaderCell headerCell = config.getHeaderCellFactory().getCell();
        headerCellHolder.addCell(headerCell);
        initCell(headerCell, new Object(), config, this);
        headerItems.add(headerCell);
        view.getRootView().addHeaderItem(headerCell);
        if (headerCell instanceof HeaderTableFilterCell) {
            view.getRootView().initFilteringHeader();
        }
        headerCell.draw();
    }

    //footer
    protected void addFooter() {
        List<AbstractFooterCell> footerCells = new ArrayList<>();
        view.getRootView().clearFooter();
        if (columnConfigs.stream().anyMatch(cc -> cc.getFooterCellFactory() != null))
            for (ColumnConfig config : columnConfigs) {
                if (!config.isVisible()) continue;
                FooterCellFactory footerCellFactory = config.getFooterCellFactory();
                if (footerCellFactory == null)
                    footerCellFactory = config.getDefaultFooterCellFactory();
                AbstractFooterCell tableFooterCell = footerCellFactory.getCell();
                footerCells.add(tableFooterCell);
                initCell(tableFooterCell, new Object(), config, this);
                view.getRootView().addFooterItem(tableFooterCell);
                tableFooterCell.draw();
            }
        if (footerRenderer != null) {
            view.getRootView().getFooterRow().innerHTML = "";
            footerRenderer.render(view.getRootView().getFooterRow(), footerCells, this);
        }

    }

    //end footer

    public boolean isPopupEditing() {
        return popupEditing;
    }

    /**
     * Toggles popup editing
     *
     * @param popupEditing
     */
    public void setPopupEditing(boolean popupEditing) {
        this.popupEditing = popupEditing;
    }

    //filterBar is added only once.
    // For situation where we need to update header call it explicitly.
    boolean filterBar;

    public void setControlBar() {
        if (filterBar)
            return;
        filterBar = true;
        if (tableBar == null)
            tableBar = new TableBar(headerCellHolder, getView().getTableBarUi(), getView());
        view.getRootView().setHeaderBar(tableBar, getColumnConfigs().size());
    }

    private int getVisibleColumnsSize() {
        return (int) columnConfigs.stream().filter(e -> e.isVisible()).count();
    }

    private void setColumnWidths() {
        for (int j = 0; j < getVisibleColumnsSize(); j++) {
            view.getRootView().setColumnWidth(j, columnConfigs.get(j).getColumnWidth());
            view.getRootView().setHeaderColumnWidth(j, columnConfigs.get(j).getColumnWidth());
            view.getRootView().setFooterColumnWidth(j, columnConfigs.get(j).getColumnWidth());
        }
    }


    @Override
    protected void displayFilteredData() {
        if (pager != null)//if pager is on
            IntStream.range(pager.getFrom(), pager.getTo())
                    .limit(rowsFiltered.size())
                    .forEach(i -> createAndInitModelRowCell(rowsFiltered.get(i)));
        else
            displayFilteredDataInVirtualScroll();
    }

    /**
     * @param columns Columns definitions
     */
    public void addColumn(ColumnConfig<T, ?>... columns) {
        Collections.addAll(columnConfigs, columns);
    }

    public List<ColumnConfig> getColumnConfigs() {
        return columnConfigs;
    }


    public void setMultiSorting(Boolean multiSorting) {
        headerCellHolder.setMultiSorting(multiSorting);
    }

    public boolean isMultiSorting() {
        return headerCellHolder.isMultiSorting();
    }

    @Override
    public List<RowTable<T>> getRowCells(List<T> list) {
        return (List<RowTable<T>>) super.getRowCells(list);
    }

    @Override
    public RowTable<T> getRowCell(T t) {
        return (RowTable<T>) super.getRowCell(t);
    }

    @Override
    public RowTable<T> getRowCell(int row) {
        return (RowTable<T>) super.getRowCell(row);
    }

    public TableCell<T, ?> getCell(int row, int column) {
        return getRowCell(row).getCells().get(column);
    }

    @Override
    public List<Comparator> getComparators() {
        return super.getComparators();
    }

    @Override
    public void addComparator(Comparator<T> comparator) {
        super.addComparator(comparator);
    }


    @Override
    public List<RowTable<T>> getCells() {
        return (List<RowTable<T>>) visibleCells;
    }


    public HandlerRegistration addRowHandler(BaseEventListener handler) {
        elemental2.dom.EventListener listener = new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                DomGlobal.console.log(evt.target);
                for (RowTable<T> rowTable : getCells()) {
                    if (rowTable.asElement() == evt.target || rowTable.asElement().contains((Node) evt.target)) {
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

    @Override
    public HandlerRegistration addCellHandler(BaseEventListener handler) {
        elemental2.dom.EventListener listener = evt -> {
            if (getCell(evt) != null)
                handler.handleEvent(evt);
        };
        asElement().addEventListener(handler.getType(), listener);
        return () -> asElement().removeEventListener(handler.getType(), listener);
    }

    public void setSingleExpandRow(boolean b) {
        TableCellRowExpander.singleExpand = b;
        T last = null;
        if (!rowExpanderList.isEmpty())
            last = rowExpanderList.get(rowExpanderList.size() - 1);
        if (b) {
            rowExpanderList.clear();
            rowExpanderList.add(last);
        }
        drawData();
    }

    public RowTable<T> getRowCell(Event event) {
        for (RowTable<T> cell : getCells()) {
            if (cell.asElement() == event.target || cell.asElement().contains((Node) event.target)) {
                return cell;
            }
        }
        return null;
    }

    public TableCell<T, ?> getCell(Event evt) {
        for (RowTable<T> tRowTable : getCells()) {
            for (TableCell tableCell : tRowTable.getCells()) {
                if (tableCell.asElement().contains((Node) evt.target)) {
                    return tableCell;
                }
            }
        }
        return null;
    }

    public static class TableHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static TableHtmlParser instance;

        private static String ROW_TAG = "row";
        private static String CELL_TAG = "cell";

        private static String HEADER_TAG = "header";
        private static String FOOTER_TAG = "footer";


        private TableHtmlParser() {
        }

        public static TableHtmlParser getInstance() {
            if (instance == null)
                return instance = new TableHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> templateElement) {
            Table<RowItemId> table = new Table<>();

            if (htmlElement.hasChildNodes()) {
                List<ItemId> headerItems = new ArrayList<>();
                List<RowItemId> rowItems = new ArrayList<>();

                NodeList<Element> header_tags = htmlElement.getElementsByTagName(HEADER_TAG);
                for (int i = 0; i < header_tags.length; i++) {
                    parseHeader((HTMLElement) header_tags.getAt(i), headerItems);
                    break; //we expect only one row.
                }

                NodeList<Element> tags = htmlElement.getElementsByTagName(ROW_TAG);
                for (int i = 0; i < tags.length; i++)
                    parseRow((HTMLElement) tags.getAt(i), rowItems);

                for (int i = 0; i < headerItems.size(); i++) {
                    int finalI = i;
                    ColumnConfig<RowItemId, ItemId> cellConfig = new ColumnConfig<>(new Function<RowItemId, ItemId>() {
                        @Override
                        public ItemId apply(RowItemId item) {
                            return item.getCells().get(finalI);
                        }
                    });
                    cellConfig.setCellFactory(c -> new TableCell<RowItemId, ItemId>().setRenderer(r ->
                            r.valuePanel.innerHTML = r.value.getContent())
                    );
                    cellConfig.setHeaderCellFactory(() ->
                            new HeaderTableTextCell().setText(headerItems.get(finalI).getContent()));
                    table.addColumn(cellConfig);
                }
                table.setRowsData(rowItems);
                table.drawData();
            }
            replaceAndCopy(htmlElement, table);
            return table;
        }

        private void parseHeader(HTMLElement html, List<ItemId> headerItems) {
            NodeList<Element> tags = html.getElementsByTagName(CELL_TAG);
            for (int i = 0; i < tags.length; i++)
                headerItems.add(getIdItem(tags.getAt(i)));
        }


        private void parseRow(HTMLElement html, List<RowItemId> rowIdItems) {
            RowItemId rowItem = new RowItemId();
            rowItem.setId(getElementId(html));
            NodeList<Element> tags = html.getElementsByTagName(CELL_TAG);
            List<ItemId> list = new ArrayList<>();
            for (int i = 0; i < tags.length; i++)
                list.add(getIdItem(tags.getAt(i)));
            rowItem.setCells(list);
            rowIdItems.add(rowItem);
        }

        @Override
        public String getId() {
            return "dn-table";
        }

        @Override
        public Class getClazz() {
            return Table.class;
        }

    }

    private HeaderRenderer headerRenderer;
    private FooterRenderer footerRenderer;

    public void setHeaderRenderer(HeaderRenderer<T> headerRenderer) {
        this.headerRenderer = headerRenderer;
    }

    public void setFooterRenderer(FooterRenderer<T> footerRenderer) {
        this.footerRenderer = footerRenderer;
    }

    public interface HeaderRenderer<T> {
        void render(HTMLElement headerRow, List<AbstractHeaderCell> headerCells, Table<T> table);
    }

    public interface FooterRenderer<T> {
        void render(HTMLElement headerRow, List<AbstractFooterCell> footerCells, Table<T> table);
    }

    public void setPager(Pager pager) {
        this.pager = pager;
        pager.setOwner(this);
        view.getRootView().setPager(pager);
    }

    @Override
    protected TableUi getView() {
        return super.getView();
    }


    @Override
    public TableRowCellConfig<T, List> getRowCellConfig() {
        return (TableRowCellConfig<T, List>) super.getRowCellConfig();
    }

    @Override
    protected CellConfig<T, List> ensureRowCellConfig() {
        if (rowCellConfig == null)
            rowCellConfig = new TableRowCellConfig();
        return rowCellConfig;
    }

    public void addRow(T t) {
        super.addRow(t);
    }

    public void insertRow(T t, int index) {
        super.insertRow(t, index);
    }

    public void removeRow(T t) {
        super.removeRow(t);
    }

    public void setRowsData(List<T> rows) {
        super.setRowsData(rows);
    }

    public class TableRowCellConfig<T, M> extends CellConfig<T, M> {

        public TableRowCellConfig() {
            super(t -> null);
//            builder = new BaseCell.BaseCellBuilder<T, M, BaseCell.BaseCellBuilder>() {
//                @Override
//                public <H extends BaseCell<T, M>> H build() {
//                    return null;
//                }
//            };
            builder = new RowTable.Builder<>();
            this.setCellFactory(new RowTableCellFactory<T>() {
                @Override
                public RowTable<T> getCell(CellContext<T, List, Table<T>> c) {
                    return new RowTable<T>().initWithBuilder(builder);
                }
            });
        }

        public TableRowCellConfig<T, M> setCellFactory(RowTableCellFactory<T> cellFactory) {
            this.cellFactory = (CellFactory<T, M, ? extends AbstractCellComponent<T, ?, ?>>) cellFactory;
            return this;
        }
    }
}