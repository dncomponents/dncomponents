package com.dncomponents.client.components.core.entities;

/**
 * @author nikolasavic
 */
public class ItemBase {
    private String id;

    public ItemBase() {
    }

    public ItemBase(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
