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

package com.dncomponents.client.components.table.header;


import com.dncomponents.client.components.table.header.filter.Comparator;
import com.dncomponents.client.views.core.ui.table.headers.HeaderTableMenuCellView;

import java.util.ArrayList;


public class HeaderTableMenuCell extends HeaderTableSortCell implements HeaderTableMenuCellView.Presenter {

    HeaderFiltering headerFiltering;
    HeaderGrouping headerGrouping;

    public HeaderTableMenuCell() {
    }

    public HeaderTableMenuCell(HeaderTableMenuCellView headerCellView) {
        super(headerCellView);
    }


    @Override
    protected void bind() {
        super.bind();
        getCellView().setPresenter(this);
        getCellView().setColumn(getCellConfig());
        headerCellHolder.addGroupByHandler(event -> {
                    updateGroupByView(event.getByColumn(getCellConfig()));
                    int index = new ArrayList<>(event.getModifiers()).indexOf(headerGrouping);
                    //Updates grouping orders for multiple sort
                    if (event.getModifiers().size() > 1)
                        getCellView().setGroupOrder(index);
                }
        );
        headerCellHolder.addFilterHandler(event -> updateFilterView(event.getByColumn(getCellConfig())));
    }

    //Group
    @Override
    public void groupBy(SortingDirection direction) {
        if (headerGrouping == null)
            headerGrouping = new HeaderGrouping(getCellConfig(), direction);
        else
            headerGrouping.setSortingDirection(direction);

        headerCellHolder.group(headerGrouping);
    }

    private void updateGroupByView(HeaderGrouping header) {
        this.headerGrouping = header;
        getCellView().setGroupedBy(header == null ? null : header.getSortingDirection());
    }
    //end Group

    //Filter
    private void updateFilterView(HeaderFiltering header) {
        this.headerFiltering = header;
        getCellView().setFiltered(header);
    }

    @Override
    public void selected(Object userEnteredValue, Comparator comparator) {
        headerFiltering.setUserEnteredValue(userEnteredValue);
        headerFiltering.setComparator(comparator);
        headerCellHolder.filtered(headerFiltering);

    }
    //end Filter


    @Override
    public HeaderTableMenuCellView getCellView() {
        return (HeaderTableMenuCellView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getHeaderTableMenuCellView();
    }
}
