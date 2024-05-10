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

package com.dncomponents.client.components.table.columnclasses.editcolumn;

import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;


public interface TableCellEditView extends BaseCellView {
    void addOnEditHandler(ClickHandler c);

    void addOnCancelHandler(ClickHandler c);

    void addOnSaveHandler(ClickHandler c);

    void addOnDeleteHandler(ClickHandler c);

    void setEditMode(boolean b);
}
