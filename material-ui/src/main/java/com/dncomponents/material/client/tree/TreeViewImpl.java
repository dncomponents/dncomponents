package com.dncomponents.material.client.tree;

import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.tree.TreeView;
import com.dncomponents.material.client.list.ListViewImpl;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TreeViewImpl extends ListViewImpl implements TreeView {

    HtmlBinder uiBinder = HtmlBinder.get(TreeViewImpl.class, this);

    public TreeViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public TreeViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }
}