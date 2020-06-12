package com.dncomponents.bootstrap.client.tabletree;

import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.tree.TreeUiImpl;
import com.dncomponents.client.components.core.HtmlBinder;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TreeTUi extends TreeUiImpl {


     HtmlBinder binder = HtmlBinder.get(TreeTUi.class, this);

    public TreeTUi() {
        binder.bind();
    }

    public TreeTUi(String template) {
        super(template);
    }
}
