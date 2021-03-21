package com.dncomponents.client.components;

import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.core.CellEditor;
import com.dncomponents.client.components.core.CellRenderer;
import com.dncomponents.client.components.core.RendererContext;
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
            if (isEditable() && getOwner().isCellEditMode()) {
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

    public void stopEditing() {
        if (isEditable())
            ensureEditor().stopEditing();
    }

    protected CellEditing<P, M> ensureEditor() {
        if (cellEditing == null)
            cellEditing = new CellEditing<>(this);
        return cellEditing;
    }

    protected CellRenderer<P, M> getCellRenderer() {
        if (cellRenderer == null)
            cellRenderer = cellConfig.getCellRenderer();
        return cellRenderer;
    }

    public <C extends BaseCell<P, M>> C setCellEditor(CellEditor<M> cellEditor) {
        this.cellEditor = cellEditor;
        return (C) this;
    }

    protected CellEditor<M> getCellEditor() {
        if (cellEditor == null)
            cellEditor = cellConfig.getCellEditorFactory().getCellEditor(getModel());
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

    public void setValueChangedStyle(boolean b) {
        ensureEditor().setValueChangedStyle(b);
    }

    public void revertValueBeforeEdit() {
        ensureEditor().revertValueBeforeEdit();
    }

    public M oldValue() {
        return ensureEditor().originalValue;
    }

    public M newValue() {
        return ensureEditor().newValue;
    }

    public void setErrorStyle(boolean b) {
        ensureEditor().setErrorStyle(b);
    }

    protected ComponentUi getUi() {
        return getOwner().getView();
    }

}
