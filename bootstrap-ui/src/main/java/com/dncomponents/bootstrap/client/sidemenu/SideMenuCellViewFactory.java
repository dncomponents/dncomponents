package com.dncomponents.bootstrap.client.sidemenu;

import com.dncomponents.bootstrap.client.button.FontAwesome;
import com.dncomponents.bootstrap.client.button.FontAwesomeSize;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.tree.BaseTreeCellView;
import com.dncomponents.client.views.core.ui.tree.ParentTreeCellView;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class SideMenuCellViewFactory {


    public static class DefaultSideMenuCellViewFactory extends AbstractPluginHelper implements ViewFactory<BaseTreeCellView> {


        private static DefaultSideMenuCellViewFactory instance;

        private DefaultSideMenuCellViewFactory() {
            arguments.put(TreeCellIconViewImpl.Builder.iconTypeId, FontAwesome.lookUp.toStringList());
            arguments.put(TreeCellIconViewImpl.Builder.iconSizeId, FontAwesomeSize.lookUp.toStringList());
        }

        public static DefaultSideMenuCellViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultSideMenuCellViewFactory();
            return instance;
        }

        @Override
        public BaseTreeCellView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            FontAwesome iconType = FontAwesome.lookUp
                    .getValue(attributes.get(TreeCellIconViewImpl.Builder.iconTypeId));
            FontAwesomeSize iconSize = FontAwesomeSize.lookUp
                    .getValue(attributes.get(TreeCellIconViewImpl.Builder.iconSizeId));

            return TreeCellIconViewImpl.Builder.get()
                    .iconType(iconType)
                    .iconSize(iconSize)
                    .build();
        }

        @Override
        public String getId() {
            return TreeCellIconViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return TreeCellIconViewImpl.class;
        }
    }

    public static class DefaultSideMenuCellParentViewFactory extends AbstractPluginHelper implements ViewFactory<ParentTreeCellView> {


        private static DefaultSideMenuCellParentViewFactory instance;

        private DefaultSideMenuCellParentViewFactory() {
            arguments.put(TreeCellIconViewImpl.Builder.iconTypeId, FontAwesome.lookUp.toStringList());
            arguments.put(TreeCellIconViewImpl.Builder.iconSizeId, FontAwesomeSize.lookUp.toStringList());
        }

        public static DefaultSideMenuCellParentViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultSideMenuCellParentViewFactory();
            return instance;
        }

        @Override
        public ParentTreeCellView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            FontAwesome iconType = FontAwesome.lookUp
                    .getValue(attributes.get(TreeCellIconViewImpl.Builder.iconTypeId));
            FontAwesomeSize iconSize = FontAwesomeSize.lookUp
                    .getValue(attributes.get(TreeCellIconViewImpl.Builder.iconSizeId));

            return TreeCellParentIconViewImpl.Builder.get()
                    .iconType(iconType)
                    .iconSize(iconSize)
                    .setRootClickExpand(true)
                    .build();
        }

        @Override
        public String getId() {
            return TreeCellParentIconViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return TreeCellParentIconViewImpl.class;
        }
    }

}