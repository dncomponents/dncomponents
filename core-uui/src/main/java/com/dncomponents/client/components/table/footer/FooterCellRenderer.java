package com.dncomponents.client.components.table.footer;

import com.dncomponents.client.components.AbstractFooterCell;
import elemental2.dom.HTMLElement;
/**
 * @author nikolasavic
 */
public interface FooterCellRenderer<T, M> {
    void setValue(HTMLElement valuePanel, AbstractFooterCell<T, M> cell);
}