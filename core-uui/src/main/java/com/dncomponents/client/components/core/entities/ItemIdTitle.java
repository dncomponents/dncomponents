package com.dncomponents.client.components.core.entities;

public class ItemIdTitle extends ItemId {

    private String title;

    public ItemIdTitle() {
    }

    public ItemIdTitle(String id, String content, String title) {
        super(id, content);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return getId() + "";
    }

}