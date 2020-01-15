package com.dncomponents.bootstrap.client.tabletree;

import com.dncomponents.bootstrap.client.table.TableUiImpl;
import com.dncomponents.client.views.core.ui.tabletree.TableTreeUi;
import com.dncomponents.client.views.core.ui.tree.TreeUi;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
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
