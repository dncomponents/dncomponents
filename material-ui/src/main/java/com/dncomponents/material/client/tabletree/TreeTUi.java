package com.dncomponents.material.client.tabletree;

import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.tree.TreeUiImpl;

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
