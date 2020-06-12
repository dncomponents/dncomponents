package com.dncomponents.client.components;

import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.core.CellEditor;
import com.dncomponents.client.components.core.CellRenderer;
import com.dncomponents.client.components.core.RendererContext;
import com.dncomponents.client.components.table.header.filter.FilterUtil;
import com.dncomponents.client.views.core.pcg.ComponentUi;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import elemental2.dom.HTMLElement;
import elemental2.dom.MouseEvent;

/**
 * @author nikolasavic
 */
public abstract class BaseCell<P, M> extends AbstractCell<P, M, BaseCellView> {

    protected CellEditing<P, M> cellEditing;

    protected CellRenderer<P, M> cellRenderer;

    private CellEditor<M> cellEditor;

    private boolean editable = true;

    private boolean selectable = true;

    private boolean focusable;

    private boolean enabled = true;

    private boolean selected;

    public BaseCell() {
    }

    public BaseCell(BaseCellView baseCellView) {
        super(baseCellView);
    }

    @Override
    public void draw() {
        initCellValue();
        renderView();
        setSelection();
    }

    protected void renderView() {
        cellView.getValuePanel().innerHTML = "";
        getCellRenderer().setValue(new RendererContext<>(getValue(), cellView.getValuePanel(), this));
    }

    @Override
    public void setCellConfig(CellConfig<P, M> cellConfig) {
        super.setCellConfig(cellConfig);
    }

    public void initCellValue() {
        value = cellConfig.getFieldGetter().apply(model);
    }

    public boolean isSelected() {
        return selected;
    }

    protected void setSelected(boolean b) {
        if (isSelectable()) {
            cellView.setSelected(selected = b);
        }
    }

    protected void setSelection() {
        if (getOwner().getSelectionModel() != null)
            setSelected(getOwner().getSelectionModel().isSelected(getModel()));
    }

    @Override
    protected void bind() {
        cellView.addClickHandler(this::onClick1);
        cellView.addDoubleClickHandler(mouseEvent -> {
            if (isEditable()) {
                if (!ensureEditor().isEditing())
                    ensureEditor().editCell();
            }
        });
        cellView.addKeyDownHandler(event -> {
            if (event.code.equals("Enter")) {
                if (isEditable()) {
                    if (!ensureEditor().isEditing())
                        ensureEditor().editCell();
                }
            }
        });
    }

    protected void onClick1(MouseEvent event) {

    }

    public <C extends BaseCell<P, M>> C setSelectable(boolean selectable) {
        this.selectable = selectable;
        return (C) this;
    }

    public <C extends BaseCell<P, M>> C setFocusable(boolean focusable) {
        this.focusable = focusable;
        return (C) this;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public boolean isFocusable() {
        return focusable;
    }


    //todo can we have protected ?
    public void setFocus(boolean b) {
        if (isFocusable()) {
            cellView.setFocus(b);
        }
    }

    public boolean isEditable() {
        return editable && getOwner().isEditable();
    }


    public boolean isEnabled() {
        return enabled;
    }

    public <C extends BaseCell<P, M>> C setEnabled(boolean b) {
        this.enabled = b;
        return (C) this;
    }

    public void scrollInView() {
        getCellView().asElement().scrollIntoView();
    }


    @Override
    protected BaseCellView getCellView() {
        return super.getCellView();
    }

    @Override
    public HTMLElement asElement() {
        return cellView.asElement();
    }

    /**
     * programmatically starts editing cell
     */
    public void startEditing() {
        if (isEditable())
            ensureEditor().editCell();
    }

    private CellEditing ensureEditor() {
        if (cellEditing == null)
            cellEditing = new CellEditing<>(this);
        return cellEditing;
    }

    protected CellRenderer<P, M> getCellRenderer() {
        if (cellRenderer == null) {
            cellRenderer = getDefaultCellRenderer();
        }
        return cellRenderer;
    }


    public <C extends BaseCell<P, M>> C setCellEditor(CellEditor<M> cellEditor) {
        this.cellEditor = cellEditor;
        return (C) this;
    }

    protected CellEditor getCellEditor() {
        if (cellEditor == null)
            cellEditor = FilterUtil.getComponent(getCellConfig().getClazz());
        return cellEditor;
    }

    public <C extends BaseCell<P, M>> C setEditable(boolean editable) {
        this.editable = editable;
        return (C) this;
    }

    public <C extends BaseCell<P, M>> C setRenderer(CellRenderer<P, M> cellRenderer) {
        this.cellRenderer = cellRenderer;
        return (C) this;
    }

    public <C extends BaseCell<P, M>> C setCellView(BaseCellView cellView) {
        this.cellView = cellView;
        return (C) this;
    }

    protected CellRenderer<P, M> getDefaultCellRenderer() {
        return CellRendererDefault.getInstance();
    }

    protected ComponentUi getUi() {
        return getOwner().getView();
    }

    private static class CellRendererDefault<P, M> implements CellRenderer<P, M> {
        @Override
        public void setValue(RendererContext<P, M> r) {
            r.valuePanel.innerHTML = r.value == null ? "" : r.value + "";
        }

        private static CellRendererDefault instance;

        public static CellRendererDefault getInstance() {
            if (instance == null)
                instance = new CellRendererDefault();
            return instance;
        }
    }

    protected BaseCell(BaseCellBuilder<P, M, ?> builder) {
        initWithBuilder(builder);
    }

    public <C extends BaseCell<P, M>> C initWithBuilder(BaseCellBuilder builder) {
        if (builder.editable != null)
            this.editable = builder.editable;
        if (builder.cellRenderer != null)
            this.cellRenderer = builder.cellRenderer;
        if (builder.cellEditor != null)
            this.cellEditor = builder.cellEditor;
        if (builder.selected != null)
            this.selected = builder.selected;//todo selectable instead?
        if (builder.baseCellView != null)
            this.cellView = builder.baseCellView;
        return (C) this;
    }

    public abstract static class BaseCellBuilder<P, M, C extends BaseCellBuilder<P, M, C>> {

        private CellRenderer<P, M> cellRenderer;
        private CellEditor<M> cellEditor;
        private Boolean editable;
        private Boolean selected;
        private BaseCellView baseCellView;

        protected void initWithBuilder(BaseCellBuilder builder) {
            this.editable = builder.editable;
            this.cellRenderer = builder.cellRenderer;
            this.cellEditor = builder.cellEditor;
            this.selected = builder.selected;
            this.baseCellView = builder.baseCellView;
        }


        public C setCellRenderer(CellRenderer<P, M> cellRenderer) {
            this.cellRenderer = cellRenderer;
            return (C) this;
        }

        public C setCellEditor(CellEditor<M> cellEditor) {
            this.cellEditor = cellEditor;
            return (C) this;
        }

        public C setEditable(boolean editable) {
            this.editable = editable;
            return (C) this;
        }

        public C setSelected(boolean selected) {
            this.selected = selected;
            return (C) this;
        }

        public C setBaseCellView(BaseCellView baseCellView) {
            this.baseCellView = baseCellView;
            return (C) this;
        }


        public abstract <H extends BaseCell<P, M>> H build();

        public static BaseCellBuilder create() {
            return null;
        }
    }
}