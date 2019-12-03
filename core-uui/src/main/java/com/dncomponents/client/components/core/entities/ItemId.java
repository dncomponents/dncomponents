package com.dncomponents.client.components.core.entities;

/**
 * Used as a entity class in ui-binder.
 */
public class ItemId {
    private String id;
    private String html;

    public ItemId() {
    }

    public ItemId(String id, String html) {
        this.id = id;
        this.html = html;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Override
    public String toString() {
        return html;
    }
}
