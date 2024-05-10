/*
 * Copyright 2024 dncomponents
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

package com.dncomponents.client.components.core;

import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.components.core.events.focus.Focusable;
import com.dncomponents.client.components.core.events.focus.HasBlurHandlers;
import com.dncomponents.client.components.core.events.validator.CanShowError;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.views.IsElement;


public interface CellEditor<M> {

    HasValue<M> getHasValue();

    Focusable getFocusable();

    IsElement getIsElement();

    void startEditing();

    HasBlurHandlers getHasBlurHandler();

    void setEndEditingHandler(Command endEditing);

//    void validate();

    default void showErrorMessage(String msg) {
        if (getHasValue() instanceof CanShowError) {
            ((CanShowError) getHasValue()).showErrorMessage(msg);
        }
    }

    abstract class CellEditorSimple<M> implements CellEditor<M> {


        @Override
        public Focusable getFocusable() {
            return null;
        }

        @Override
        public void startEditing() {

        }

        @Override
        public HasBlurHandlers getHasBlurHandler() {
            return null;
        }

        @Override
        public void setEndEditingHandler(Command endEditing) {

        }
    }
}
