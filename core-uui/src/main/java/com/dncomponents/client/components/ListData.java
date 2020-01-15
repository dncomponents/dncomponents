package com.dncomponents.client.components;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.list.ListCell;
import com.dncomponents.client.components.list.ListCellFactory;
import com.dncomponents.client.components.list.ListTreeCellNavigator;
import com.dncomponents.client.components.list.ListTreeMultiSelectionModel;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.list.ListUi;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.NodeList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author nikolasavic
 */
public class ListData<T, M> extends AbstractCellComponent<T, M, ListUi> {


    {
        defaultCellFactory = (ListCellFactory<T, M>) context -> new ListCell<>();
        setSelectionModel(new ListTreeMultiSelectionModel(this, this.getView().getRootView()));
    }

    private Comparator comparator;

    private boolean sortable = true;

    private Comparator getComparator() {
        if (comparator == null)
            comparator = Comparator.comparing(ensureRowCellConfig().getFieldGetter(), Comparator.nullsFirst((o1, o2) -> {
                if (!(o1 instanceof Comparable) || !(o2 instanceof Comparable)) //this happens for entities without comparable defined;
                    throw new IllegalArgumentException("Entity must be of Comparable type! Please define valid comparator for this column!");
                Comparable comparable1 = (Comparable) o1;
                Comparable comparable2 = (Comparable) o2;
                return comparable1.compareTo(comparable2);
            }));
        return comparator;
    }

    private boolean scrollable;

    public ListData() {
        this(new Function<T, M>() {
            @Override
            public M apply(T t) {
                return (M) (t + "");
            }
        });
    }

    public ListData(ListUi ui) {
        super(ui);
    }

    public ListData(Function<T, M> fieldGetter) {
        super(Ui.get().getListUi());
        ensureRowCellConfig().setFieldGetter(fieldGetter);
    }

    public ListData(Function<T, M> fieldGetter, BiConsumer<T, M> fieldSetter) {
        super(Ui.get().getListUi());
        ensureRowCellConfig().setFieldGetter(fieldGetter);
        ensureRowCellConfig().setFieldSetter(fieldSetter);
    }

    public ListData(ListUi ui, Function<T, M> fieldGetter, BiConsumer<T, M> fieldSetter) {
        super(ui);
        ensureRowCellConfig().setFieldGetter(fieldGetter);
        ensureRowCellConfig().setFieldSetter(fieldSetter);
    }

    public ListData(ListUi instance, Function<T, M> fieldGetter) {
        super(instance);
        ensureRowCellConfig().setFieldGetter(fieldGetter);
    }

    @Override
    public List<ListCell<T, M>> getCells() {
        return (List<ListCell<T, M>>) super.getCells();
    }

    @Override
    public ListCell<T, M> getRowCell(T model) {
        return (ListCell<T, M>) super.getRowCell(model);
    }

    public ListCell<T, M> getRowCell(int row) {
        return getCells().get(row);
    }

    @Override
    public ListTreeMultiSelectionModel<T> getSelectionModel() {
        return (ListTreeMultiSelectionModel) super.getSelectionModel();
    }

    /**
     * Sorts with default sorter for cell
     *
     * @param b <code>true</code> ascending <code>false</code> descending <code>null</code> for original
     */
    public void sort(Boolean b) {
        if (!isSortable())
            return;
        clearComparators();
        Comparator comparator = getComparator();
        if (b != null)
            if (b)
                addComparator(comparator);
            else
                addComparator(comparator.reversed());
        drawData();
    }

    public boolean isScrollable() {
        return scrollable;
    }

    public void setScrollable(boolean b) {
        view.getRootView().setScrollable(b);
        scrollable = b;
    }

    /**
     * Sets the comparator used to compare the items in this column when sorting.
     * <code>null</code> to remove custom comparator.
     * If not set default sorter is used based on FieldGetter
     *
     * @param comparator the Comparator to use for sorting
     */

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void removeComparator() {
        this.comparator = null;
    }

    /**
     * Sets if the column can be sorted (defaults to true).
     *
     * @param sortable the sortable state
     */
    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    /**
     * Returns <code>true</code> if the column is sortable.
     *
     * @return the sortable state
     */
    public boolean isSortable() {
        return sortable;
    }


//    public void setRowRenderer(CellRenderer<T, M> rowRenderer) {
//         ensureRowCellConfig().setCellRenderer(rowRenderer);
//    }


    public static class ListHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static ListHtmlParser instance;

        private ListHtmlParser() {
            tags.put(ITEM, Collections.emptyList());
        }

        public static ListHtmlParser getInstance() {
            if (instance == null)
                return instance = new ListHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> templateElement) {
            ListData<ItemId, String> list;
            ListUi ui = getView(ListUi.class, htmlElement, templateElement);
            if (ui != null)
                list = new ListData(ui);
            else
                list = new ListData();
            list.getRowCellConfig().setFieldGetter(o -> o + "");
            if (htmlElement.hasChildNodes()) {
                getChildren(list, htmlElement, this);
            }
            replaceAndCopy(htmlElement, list);
            return list;
        }


        public static void getChildren(HasRowsData<ItemId> hasRowsData, Element htmlElement, ComponentHtmlParser cp) {
            NodeList<Element> elementsByTagName = htmlElement.getElementsByTagName(ITEM);
            for (int i = 0; i < elementsByTagName.length; i++) {
                final HTMLElement at = (HTMLElement) elementsByTagName.getAt(i);
                hasRowsData.addRow(cp.getIdItem(at));
            }
            hasRowsData.drawData();
        }

        @Override
        public String getId() {
            return "dn-list";
        }

        @Override
        public Class getClazz() {
            return ListData.class;
        }

        @Override
        public boolean isPremium() {
            return true;
        }
    }

    @Override
    void newBlock() {
        final ListTreeCellNavigator cellNavigator = getSelectionModel().getCellNavigator();
        //todo we should just focus cell with .focus({preventScroll:true}); as described:
        //todo https://developer.mozilla.org/en-US/docs/Web/API/HTMLElement/focus
        //todo but there is no such a method in elemental2
        if (cellNavigator != null && cellNavigator.working) {
            cellNavigator.focusCurrentCell();
        }
    }

    @Override
    protected ListCellConfig<T, M> ensureRowCellConfig() {
        if (rowCellConfig == null)
            rowCellConfig = new ListCellConfig<>();
        return (ListCellConfig<T, M>) rowCellConfig;
    }

    @Override
    public ListCellConfig<T, M> getRowCellConfig() {
        return ensureRowCellConfig();
    }


}