/*
 * Copyright 2023 dncomponents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
