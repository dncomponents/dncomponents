package com.dncomponents.client.views.core.pcg;

import elemental2.dom.HTMLElement;

/**
 * Factory interface produces all needed views for component.
 * {@link ComponentUi#getRootView} produce root view for owner component.
 * All other interface methods should create new view instances. (not reference local or static views)
 * <p>
 * Used when component creates sub-elements.
 *
 * @author nikolasavic
 */
public interface ComponentUi<T extends View> extends View {
    T getRootView();

    @Override
    default HTMLElement asElement() {
        return getRootView().asElement();
    }
}
