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

package com.dncomponents.client.components.list;


import com.dncomponents.client.components.AbstractCellComponent;
import elemental2.dom.DomGlobal;
import elemental2.dom.DomGlobal.SetTimeoutCallbackFn;
import elemental2.dom.KeyboardEvent;

/**
 * @author nikolasavic
 */
public class ListTreeCellNavigator extends BaseCellNavigator {

    public ListTreeCellNavigator(AbstractCellComponent owner, HasNavigationHandler view) {
        super(owner, view);
    }

    @Override
    protected void moveCellUp(KeyboardEvent keyboardEvent) {
        Object obj = owner.getRowsFiltered().get(owner.getRowsFiltered().indexOf(currentFocusedModel) + 1);
        selectCell(obj, keyboardEvent);
    }

    @Override
    protected void moveCellDown(KeyboardEvent keyboardEvent) {
        Object obj = owner.getRowsFiltered().get(owner.getRowsFiltered().indexOf(currentFocusedModel) - 1);
        selectCell(obj, keyboardEvent);
    }

    public boolean working = false;
    SetTimeoutCallbackFn timerFn = p -> working = false;

    private void selectCell(Object obj, KeyboardEvent keyboardEvent) {
        working = true;
        setVal(obj, true, keyboardEvent);
        DomGlobal.setTimeout(timerFn, 500);
    }

    public void focusCurrentCell() {
        selectCell(currentFocusedModel, null);
    }

    @Override
    protected void moveCellLeft(KeyboardEvent keyboardEvent) {
        moveCellDown(keyboardEvent);
    }

    @Override
    protected void moveCellRight(KeyboardEvent keyboardEvent) {
        moveCellUp(keyboardEvent);
    }

}
