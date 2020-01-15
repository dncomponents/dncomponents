package com.dncomponents.client.components;

import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.ComponentUi;
import com.dncomponents.client.views.core.pcg.cell.CellView;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public abstract class AbstractCell<P, M, CW extends CellView> implements IsElement {

    protected P model;
    protected M value;

    protected AbstractCellComponent owner;

    protected CellConfig<P, M> cellConfig;

    protected CW cellView;

    public AbstractCell() {
    }

    public AbstractCell(CW cellView) {
        this.cellView = cellView;
    }

    public P getModel() {
        return model;
    }

    protected void setModel(P model) {
        this.model = model;
    }

    public M getValue() {
        return value;
    }

    public AbstractCellComponent getOwner() {
        return owner;
    }

    protected void setOwner(AbstractCellComponent owner) {
        this.owner = owner;
        if (cellView == null)
            initViewFromOwner();
    }

    protected void initViewFromOwner() {
    }

    protected CW getCellView() {
        return cellView;
    }



    public abstract void draw();

    /**
     * this method is called after all cell fields are initialised by the owner widget
     */
    protected void bind() {
    }

    void removeFromParent() {
        cellView.asElement().remove();
    }

    @Override
    public HTMLElement asElement() {
        return cellView.asElement();
    }

    protected ComponentUi getUi() {
        return getOwner().getView();
    }

    protected static ComponentUi getUi(AbstractCellComponent component) {
        return component.getView();
    }

    public void setCellConfig(CellConfig<P, M> cellConfig) {
        this.cellConfig = cellConfig;
    }

    public CellConfig<P, M> getCellConfig() {
        return cellConfig;
    }
}