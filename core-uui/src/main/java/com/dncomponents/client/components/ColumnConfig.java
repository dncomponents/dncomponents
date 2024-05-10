/*
 * Copyright 2024 dncomponents
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

import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.core.CellEditorFactory;
import com.dncomponents.client.components.core.CellFactory;
import com.dncomponents.client.components.core.CellRenderer;
import com.dncomponents.client.components.core.validation.Validator;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.table.columnclasses.FooterCellFactory;
import com.dncomponents.client.components.table.columnclasses.GroupRowRenderer;
import com.dncomponents.client.components.table.columnclasses.TableHeaderCellFactory;
import com.dncomponents.client.components.table.header.HeaderTableSortCell;
import com.dncomponents.client.components.table.header.filter.FilterPanelFactory;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.Function;


public class ColumnConfig<T, M> extends CellConfig<T, M> {

    protected Comparator<T> comparator;
    private String columnWidth = "100px";
    private boolean visible = true;
    private boolean editable;
    private FilterPanelFactory<T> filterPanelFactory;
    private boolean sortable = true;
    private TableHeaderCellFactory headerCellFactory = () -> new HeaderTableSortCell().setText(getName());
    private FooterCellFactory<T, M> footerCellFactory = null;
    private GroupRowRenderer<T, M> groupRowRenderer = (value, groupedValues, htmlElement) -> htmlElement.innerHTML = value + "";

    {
        setCellFactory(c -> new TableCell<>());
    }

    public ColumnConfig(Function<T, M> fieldGetter, String columnName) {
        super(fieldGetter);
        this.setName(columnName);
    }

    public ColumnConfig(Function<T, M> fieldGetter) {
        super(fieldGetter);
    }

    public ColumnConfig(Function<T, M> fieldGetter, BiConsumer<T, M> fieldSetter) {
        super(fieldGetter, fieldSetter);
    }

    public ColumnConfig(Function<T, M> fieldGetter, BiConsumer<T, M> fieldSetter, String columnName) {
        super(fieldGetter, fieldSetter);
        setName(columnName);
    }

    public FooterCellFactory<T, M> getFooterCellFactory() {
        return footerCellFactory;
    }

    public void setFooterCellFactory(FooterCellFactory<T, M> footerCellFactory) {
        this.footerCellFactory = footerCellFactory;
    }

    public void setHeaderCellFactory(TableHeaderCellFactory headerCellFactory) {
        this.headerCellFactory = headerCellFactory;
    }

    public TableHeaderCellFactory getHeaderCellFactory() {
        return headerCellFactory;
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

    public void setColumnWidth(String width) {
        this.columnWidth = width;
    }

    public boolean isSortable() {
        return sortable;
    }

    public GroupRowRenderer<T, M> getGroupRowRenderer() {
        return groupRowRenderer;
    }

    public void setGroupRowRenderer(GroupRowRenderer<T, M> groupRowRenderer) {
        this.groupRowRenderer = groupRowRenderer;
    }

    @Override
    public TableCellFactory<T, M> getCellFactory() {
        return (TableCellFactory) cellFactory;
    }

    public void setCellFactory(TableCellFactory<T, M> cellFactory) {
        super.setCellFactory(cellFactory);
    }

    @Override
    public String toString() {
        return getName() + "";
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public FilterPanelFactory<T> getFilterPanelFactory() {
        return filterPanelFactory;
    }

    public void setFilterPanelFactory(FilterPanelFactory<T> filterPanelFactory) {
        this.filterPanelFactory = filterPanelFactory;
    }

    public static class Builder<T, M> {
        private BiConsumer<T, M> fieldSetter;
        private Function<T, M> fieldGetter;
        private Validator<M> validator;
        private CellEditorFactory<T, M> cellEditorFactory;
        private Class clazz;
        private CellFactory<T, M> cellFactory;
        private CellRenderer<T, M> cellRenderer;
        private String name;
        private String helperText;
        private String validText;
        private Comparator<T> comparator;
        private String columnWidth;
        private Boolean visible;
        private Boolean editable;
        private FilterPanelFactory<T> filterPanelFactory;
        private TableHeaderCellFactory headerCellFactory;
        private FooterCellFactory<T, M> footerCellFactory;
        private GroupRowRenderer<T, M> groupRowRenderer;

        public Builder() {
        }

        public Builder(Function<T, M> fieldGetter, BiConsumer<T, M> fieldSetter) {
            this.fieldSetter = fieldSetter;
            this.fieldGetter = fieldGetter;
        }

        public Builder(Function<T, M> fieldGetter) {
            this.fieldGetter = fieldGetter;
        }

        public Builder<T, M> setFieldSetter(BiConsumer<T, M> fieldSetter) {
            this.fieldSetter = fieldSetter;
            return this;
        }

        public Builder<T, M> setFieldGetter(Function<T, M> fieldGetter) {
            this.fieldGetter = fieldGetter;
            return this;
        }

        public Builder<T, M> setValidator(Validator<M> validator) {
            this.validator = validator;
            return this;
        }

        public Builder<T, M> setCellEditorFactory(CellEditorFactory<T, M> cellEditorFactory) {
            this.cellEditorFactory = cellEditorFactory;
            return this;
        }

        public Builder<T, M> setClazz(Class clazz) {
            this.clazz = clazz;
            return this;
        }

        public Builder<T, M> setCellFactory(TableCellFactory<T, M> cellFactory) {
            this.cellFactory = cellFactory;
            return this;
        }

        public Builder<T, M> setCellRenderer(CellRenderer<T, M> cellRenderer) {
            this.cellRenderer = cellRenderer;
            return this;
        }

        public Builder<T, M> setName(String columnName) {
            this.name = columnName;
            return this;
        }

        public Builder<T, M> setHelperText(String helperText) {
            this.helperText = helperText;
            return this;
        }

        public Builder<T, M> setValidText(String validText) {
            this.validText = validText;
            return this;
        }

        public Builder<T, M> setVisible(Boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder<T, M> setComparator(Comparator<T> comparator) {
            this.comparator = comparator;
            return this;
        }

        public Builder<T, M> setColumnWidth(String columnWidth) {
            this.columnWidth = columnWidth;
            return this;
        }

        public Builder<T, M> setVisible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder<T, M> setEditable(boolean editable) {
            this.editable = editable;
            return this;
        }

        public Builder<T, M> setFilterPanelFactory(FilterPanelFactory<T> filterPanelFactory) {
            this.filterPanelFactory = filterPanelFactory;
            return this;
        }

        public Builder<T, M> setFooterCellFactory(FooterCellFactory<T, M> footerCellFactory) {
            this.footerCellFactory = footerCellFactory;
            return this;
        }

        public Builder<T, M> setGroupRowRenderer(GroupRowRenderer<T, M> groupRowRenderer) {
            this.groupRowRenderer = groupRowRenderer;
            return this;
        }

        public Builder<T, M> setHeaderCellFactory(TableHeaderCellFactory headerCellFactory) {
            this.headerCellFactory = headerCellFactory;
            return this;
        }

        public ColumnConfig<T, M> build() {
            final ColumnConfig<T, M> columnConfig = new ColumnConfig<>(this.fieldGetter, this.fieldSetter);
            if (this.cellEditorFactory != null)
                columnConfig.setCellEditorFactory(this.cellEditorFactory);
            if (this.editable != null)
                columnConfig.setEditable(this.editable);
            if (this.name != null)
                columnConfig.setName(this.name);
            if (this.helperText != null)
                columnConfig.setHelperText(this.helperText);
            if (this.validText != null)
                columnConfig.setSuccessText(this.validText);
            if (this.cellFactory != null)
                columnConfig.setCellFactory(this.cellFactory);
            if (this.validator != null)
                columnConfig.setValidator(this.validator);
            if (this.visible != null)
                columnConfig.setVisible(this.visible);
            if (this.comparator != null)
                columnConfig.setComparator(this.comparator);
            if (this.clazz != null)
                columnConfig.setClazz(this.clazz);
            if (this.columnWidth != null)
                columnConfig.setColumnWidth(this.columnWidth);
            if (this.filterPanelFactory != null)
                columnConfig.setFilterPanelFactory(this.filterPanelFactory);
            if (this.headerCellFactory != null)
                columnConfig.setHeaderCellFactory(this.headerCellFactory);
            if (this.footerCellFactory != null)
                columnConfig.setFooterCellFactory(this.footerCellFactory);
            if (this.groupRowRenderer != null)
                columnConfig.setGroupRowRenderer(this.groupRowRenderer);
            if (this.cellRenderer != null)
                columnConfig.setCellRenderer(this.cellRenderer);
            return columnConfig;
        }
    }
}
