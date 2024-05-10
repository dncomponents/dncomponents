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

package com.dncomponents.client.components.table.columnclasses;

import com.dncomponents.client.components.core.DefaultCellEditor;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.textbox.DateBox;
import elemental2.core.JsDate;

import java.util.Date;


public class TableCellDate<T> extends TableCell<T, Date> {

    //todo set format

    public TableCellDate() {
        init();
    }

    private void init() {
        //default renderer
        setRenderer(r -> {
            if (r.value == null)
                r.valuePanel.innerHTML = "N/D";
            else {
                final String s = new JsDate(((double) value.getTime())).toLocaleDateString();
                r.valuePanel.innerHTML = new JsDate(((double) value.getTime())).toLocaleDateString();
            }
        });
        //default cell editor
        DateBox dateBox = new DateBox();
        setCellEditor(new DefaultCellEditor(dateBox));
    }
}
