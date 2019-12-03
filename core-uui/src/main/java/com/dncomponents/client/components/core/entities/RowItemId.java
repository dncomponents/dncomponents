package com.dncomponents.client.components.core.entities;

import java.util.List;

public class RowItemId {
    String id;
    List<ItemId> cells;

    public RowItemId(String id, List<ItemId> cells) {
        this.id = id;
        this.cells = cells;
    }

    public RowItemId() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ItemId> getCells() {
        return cells;
    }

    public void setCells(List<ItemId> cells) {
        this.cells = cells;
    }

}
