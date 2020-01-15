package com.dncomponents.client.components.table.footer;
/**
 * @author nikolasavic
 */
public class FooterTextCell extends FooterCell {

    String text;

    public FooterTextCell(String text) {
        this.text = text;
        setCellRenderer((valuePanel, cell) ->
                valuePanel.innerHTML = text);
    }

    public FooterTextCell setText(String text) {
        this.text = text;
        return this;
    }
}