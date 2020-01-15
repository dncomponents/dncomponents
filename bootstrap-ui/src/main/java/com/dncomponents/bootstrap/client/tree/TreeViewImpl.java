package com.dncomponents.bootstrap.client.tree;

import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.list.ListViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.tree.TreeView;
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
        init();
    }
}