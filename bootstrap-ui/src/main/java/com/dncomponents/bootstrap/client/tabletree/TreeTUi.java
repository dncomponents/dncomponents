package com.dncomponents.bootstrap.client.tabletree;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.table.group.ParentTableTreeCellViewImpl;
import com.dncomponents.bootstrap.client.tree.TreeUiImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.tree.ParentTreeCellView;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TreeTUi extends TreeUiImpl {


    HtmlBinder binder = HtmlBinder.get(TreeTUi.class, this);
    @UiField
    HTMLTemplateElement tableTreeItemSimpleParent;

    public TreeTUi() {
        binder.bind();
    }

    public TreeTUi(String template) {
        super(template);
    }

    @Override
    public ParentTreeCellView getParentTreeCellView(String icon) {
        return new ParentTableTreeCellViewImpl(tableTreeItemSimpleParent).setIcon(icon);
    }
}
