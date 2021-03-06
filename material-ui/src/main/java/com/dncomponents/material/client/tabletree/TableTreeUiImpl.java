package com.dncomponents.material.client.tabletree;

import com.dncomponents.client.views.core.ui.tabletree.TableTreeUi;
import com.dncomponents.client.views.core.ui.tree.TreeUi;
import com.dncomponents.material.client.table.TableUiImpl;
import elemental2.dom.HTMLTemplateElement;

public class TableTreeUiImpl extends TableUiImpl implements TableTreeUi {

    TreeTUi treeUi = new TreeTUi();

    public TableTreeUiImpl() {
        super();
    }

    public TableTreeUiImpl(HTMLTemplateElement templateElement) {
        super(templateElement);
    }


    @Override
    public TreeUi getTreeUi() {
        return treeUi;
    }
}
