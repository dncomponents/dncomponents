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

package com.dncomponents.client.views.core.ui.list;

import com.dncomponents.client.views.core.pcg.ComponentUi;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;

/**
 * @author nikolasavic
 */
public interface ListUi extends ComponentUi<ListView> {

    BaseCellView getListCellView();

    ListCellCheckBoxView getListCheckBoxView();
}
