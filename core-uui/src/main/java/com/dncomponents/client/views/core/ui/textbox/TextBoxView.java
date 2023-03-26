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

package com.dncomponents.client.views.core.ui.textbox;

import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.dom.handlers.OnBlurHandler;
import com.dncomponents.client.views.FocusComponentView;
import elemental2.dom.EventListener;

/**
 * @author nikolasavic
 */
public interface TextBoxView extends FocusComponentView {

    String getValue();

    void setValue(String value);

    void addOnInputChangeHandler(EventListener listener);

    void addOnBlurHandler(OnBlurHandler handler);

    void addOnKeyUpHandler(KeyUpHandler handler);

    void setPlaceHolder(String placeHolder);

    void setError(boolean b);

    void setErrorMessage(String errorMessage);

    default void setValid(boolean b) {

    }

    default void setHelperText(String helperText) {

    }

    default void setLabel(String labelText) {

    }
}
