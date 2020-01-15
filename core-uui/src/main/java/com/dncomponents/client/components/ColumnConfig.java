package com.dncomponents.client.components;

import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.table.columnclasses.FooterCellFactory;
import com.dncomponents.client.components.table.columnclasses.GroupRowRenderer;
import com.dncomponents.client.components.table.columnclasses.TableHeaderCellFactory;
import com.dncomponents.client.components.table.footer.FooterCell;
import com.dncomponents.client.components.table.header.HeaderTableSortCell;
import com.dncomponents.client.components.table.header.filter.FilterPanelFactory;
import elemental2.dom.HTMLElement;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Created by nikolasavic
 */
public class ColumnConfig<T, M> extends CellConfig<T, M> {

    private String columnName = "";
    protected Comparator<T> comparator;
    private String columnWidth = "100px";
    private boolean visible = true;
    private boolean editable;
    private FilterPanelFactory<T> filterPanelFactory;

    {
        setCellFactory(c -> new TableCell<T, M>().initWithBuilder(builder));
        builder = new TableCell.Builder<>();
    }

    private GroupRowRenderer<T, M> groupRowRenderer = new GroupRowRenderer<T, M>() {

        @Override
        public void render(M value, List<T> groupedValues, HTMLElement htmlElement) {
            htmlElement.innerHTML = value + "";
        }
    };

    private boolean sortable = true;

    private TableHeaderCellFactory headerCellFactory = () -> new HeaderTableSortCell().setText(columnName);

    private FooterCellFactory<T, M> footerCellFactory;

    private FooterCellFactory<T, M> defaultFooterCellFactory = FooterCell::new;

    public FooterCellFactory<T, M> getDefaultFooterCellFactory() {
        return defaultFooterCellFactory;
    }


    public ColumnConfig(Function<T, M> fieldGetter, String columnName) {
        super(fieldGetter);
        this.columnName = columnName;
    }

    public ColumnConfig(Function<T, M> fieldGetter) {
        super(fieldGetter);
    }

    public ColumnConfig(Function<T, M> fieldGetter, BiConsumer<T, M> fieldSetter) {
        super(fieldGetter, fieldSetter);
    }

    public ColumnConfig(Function<T, M> fieldGetter, BiConsumer<T, M> fieldSetter, String columnName) {
        super(fieldGetter, fieldSetter);
        setColumnName(columnName);
    }

    public FooterCellFactory<T, M> getFooterCellFactory() {
        return footerCellFactory;
    }

    public ColumnConfig<T, M> setFooterCellFactory(FooterCellFactory<T, M> footerCellFactory) {
        this.footerCellFactory = footerCellFactory;
        return this;
    }

    public ColumnConfig<T, M> setHeaderCellFactory(TableHeaderCellFactory headerCellFactory) {
        this.headerCellFactory = headerCellFactory;
        return this;
    }

    public TableHeaderCellFactory getHeaderCellFactory() {
        return headerCellFactory;
    }


    public String getColumnName() {
        return columnName;
    }

    public ColumnConfig<T, M> setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public Comparator<T> getComparator() {
        if (comparator != null) {
            return comparator;
        } else {
            return Comparator.comparing(fieldGetter,
                    Comparator.nullsFirst((o1, o2) -> {
                        if (!(o1 instanceof Comparable) || !(o2 instanceof Comparable)) //this happens for entities without comparable defined;
                            throw new IllegalArgumentException("Entity must be of Comparable type! Please define valid comparator for this column!");
                        Comparable<M> comparable1 = (Comparable<M>) o1;
                        Comparable<M> comparable2 = (Comparable<M>) o2;
                        return comparable1.compareTo((M) comparable2);
                    }));
        }
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public String getColumnWidth() {
        return columnWidth;
    }

    public ColumnConfig<T, M> setColumnWidth(String width) {
        this.columnWidth = width;
        return this;
    }

    public boolean isSortable() {
        return sortable;
    }

    public GroupRowRenderer<T, M> getGroupRowRenderer() {
        return groupRowRenderer;
    }

    public ColumnConfig<T, M> setGroupRowRenderer(GroupRowRenderer<T, M> groupRowRenderer) {
        this.groupRowRenderer = groupRowRenderer;
        return this;
    }

    @Override
    public TableCellFactory<T, M> getCellFactory() {
        return (TableCellFactory) cellFactory;
    }


    public ColumnConfig<T, M> setCellFactory(TableCellFactory<T, M> cellFactory) {
        super.setCellFactory(cellFactory);
        return this;
    }

    @Override
    public String toString() {
        return columnName + "";
    }

    public boolean isVisible() {
        return visible;
    }

    public ColumnConfig<T, M> setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public boolean isEditable() {
        return editable;
    }

    public ColumnConfig<T, M> setEditable(boolean editable) {
        this.editable = editable;
        return this;
    }

    public ColumnConfig<T, M> setClazz(Class clazz) {
        return super.setClazz(clazz);
    }

    @Override
    public TableCell.Builder<T, M> getCellBuilder() {
        return (TableCell.Builder<T, M>) super.getCellBuilder();
    }

    public ColumnConfig<T, M> setCellBuilder(BuilderSet<T, M> b) {
        b.setBuilder(getCellBuilder());
        return this;
    }

    public FilterPanelFactory<T> getFilterPanelFactory() {
        return filterPanelFactory;
    }

    public ColumnConfig<T, M> setFilterPanelFactory(FilterPanelFactory<T> filterPanelFactory) {
        this.filterPanelFactory = filterPanelFactory;
        return this;
    }

    public interface BuilderSet<T, M> {
        void setBuilder(TableCell.Builder<T, M> b);
    }

}