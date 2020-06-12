package com.dncomponents.client.components.core.entities;

import com.dncomponents.client.components.tree.HasIcon;

/**
 * Used as a entity class in html-binder.
 */
public class ItemId extends ItemBase implements HasIcon {

    private String content;
    private String icon;

    public ItemId() {
    }

    public ItemId(String id, String content) {
        super(id);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return content;
    }
}