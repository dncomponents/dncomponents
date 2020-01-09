package com.dncomponents.client.components.core.entities;

import java.util.List;

public class RowItemId extends ItemBase {

    List<ItemId> cells;

    public RowItemId(String id, List<ItemId> cells) {
        super(id);
        this.cells = cells;
    }

    public RowItemId() {
    }

    public List<ItemId> getCells() {
        return cells;
    }

    public void setCells(List<ItemId> cells) {
        this.cells = cells;
    }

}