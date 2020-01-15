package com.dncomponents.bootstrap.client.tabletree;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class TableTreeTemplates implements IsElement {
    @UiField
    public HTMLElement root;
    @UiField
    public HTMLTemplateElement treeItemSimple;
    @UiField
    public HTMLTemplateElement treeItemSimpleCheckbox;
    @UiField
    public HTMLTemplateElement treeItemSimpleParentCheckbox;

    {
        HtmlBinder.get(TableTreeTemplates.class, this).bind();
    }

    public TableTreeTemplates() {
        init();
    }

    private void init() {
        //init code here
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

}
