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

package com.dncomponents.client.views.core.ui.checkbox;


import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.handlers.OnChangeHandler;
import com.dncomponents.client.views.FocusComponentView;
import com.dncomponents.client.views.MainViewSlots;

/**
 * @author nikolasavic
 */
public interface CheckBoxView extends FocusComponentView, MainViewSlots.HasMainViewSlots {
    boolean isChecked();

    void setChecked(Boolean b);

    void setIndeterminate(boolean b);

    void setLabel(String txt);

    HandlerRegistration addOnChangeHandler(OnChangeHandler changeHandler);

    void setName(String nameOfGroup);

    String getLabel();

//    void setHelperText(String helperText);
}
