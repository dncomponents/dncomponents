package com.dncomponents.material.client.tabletree;

import com.dncomponents.Component;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.tree.TreeUiImpl;

@Component
public class TreeTUi extends TreeUiImpl {


    HtmlBinder binder = HtmlBinder.create(TreeTUi.class, this);

    public TreeTUi() {
        binder.bind();
    }

    public TreeTUi(String template) {
        super(template);
    }
}
