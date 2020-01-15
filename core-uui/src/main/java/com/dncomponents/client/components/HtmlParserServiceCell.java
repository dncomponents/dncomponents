package com.dncomponents.client.components;

import com.dncomponents.client.components.autocomplete.Autocomplete;
import com.dncomponents.client.components.autocomplete.AutocompleteMultiSelect;
import com.dncomponents.client.components.autocomplete.AutocompleteTree;
import com.dncomponents.client.components.multi.DropDownMultiLevel;
import com.dncomponents.client.components.sidemenu.SideMenu;
import com.dncomponents.client.components.core.HtmlParserService;

/**
 * @author nikolasavic
 */
public class HtmlParserServiceCell {
//    static {
//        register();
//    }

    public static void register() {
        HtmlParserService.COMPONENT_HTML_PARSERS.add(SideMenu.SideMenuHtmlParser.getInstance());
        HtmlParserService.COMPONENT_HTML_PARSERS.add(ListData.ListHtmlParser.getInstance());
        HtmlParserService.COMPONENT_HTML_PARSERS.add(Tree.TreeHtmlParser.getInstance());
        HtmlParserService.COMPONENT_HTML_PARSERS.add(Table.TableHtmlParser.getInstance());
        HtmlParserService.COMPONENT_HTML_PARSERS.add(DropDownMultiLevel.DropDownMultiLevelHtmlParser.getInstance());
        HtmlParserService.COMPONENT_HTML_PARSERS.add(Autocomplete.AutocompleteHtmlParser.getInstance());
        HtmlParserService.COMPONENT_HTML_PARSERS.add(AutocompleteMultiSelect.AutocompleteMultiSelectHtmlParser.getInstance());
        HtmlParserService.COMPONENT_HTML_PARSERS.add(AutocompleteTree.AutocompleteTreeHtmlParser.getInstance());
    }
}
