package com.dncomponents.client.views.core.ui.tree;


import com.dncomponents.client.views.core.pcg.cell.BaseCellView;

/**
 * @author nikolasavic
 */
public interface BaseTreeCellView extends BaseCellView {
    void setActive(boolean b);

    void setPadding(double padding);
}