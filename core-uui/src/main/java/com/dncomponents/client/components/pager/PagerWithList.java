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

package com.dncomponents.client.components.pager;

import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.pager.PagerWithListView;

import java.util.ArrayList;
import java.util.List;


public class PagerWithList extends Pager<PagerWithListView> {

    public static final int OFFSET = 2;
    public static final int RANGE_SIZE = 2 * OFFSET + 1;
    List<Integer> itemsList = new ArrayList<>();
    List<PagerItem> items = new ArrayList<>();


    public PagerWithList() {
        super(Ui.get().getPagerListUi());
    }

    ArrayList<Integer> getPages(int index) {
        ArrayList<Integer> listPages = new ArrayList<>();
        //1. index less than RANGE_SIZE + 3 items from left (1,2,3)
        if (numberOfPages < 3 + 3 + OFFSET + OFFSET) {
            for (int i = 0; i < numberOfPages; i++) {
                listPages.add(i);
            }
        } else if (index <= OFFSET + 3) {
            int i;
            for (i = 0; i <= index + OFFSET || i < 3 + OFFSET; i++) {
                listPages.add(i);
            }
            //add two last numbers
            if (i + 3 < numberOfPages) {
                listPages.addAll(addLastNumbers(numberOfPages));
            } else {
                for (int j = i; j < numberOfPages; j++) {
                    listPages.add(j);
                }

            }
        } else if (index - OFFSET > 3) {
            listPages.addAll(addFirstNumbers());
            int minus = (index - (numberOfPages - 3));
            minus = minus > 0 ? minus : 0;
            for (int i = index - OFFSET - minus; i < numberOfPages || i < numberOfPages - 3 - OFFSET; i++) {
                listPages.add(i);
                if (i >= index + OFFSET && index < numberOfPages - 1 - 3 - OFFSET) {
                    listPages.addAll(addLastNumbers(numberOfPages));
                    break;
                }
            }
        }
        return listPages;
    }

    private ArrayList<Integer> addFirstNumbers() {
        ArrayList<Integer> listPages = new ArrayList<>();
        listPages.add(0);
        listPages.add(1);
        listPages.add(-1);
        return listPages;
    }

    private ArrayList<Integer> addLastNumbers(int numberOfPages) {
        ArrayList<Integer> listPages = new ArrayList<>();
        listPages.add(-1);
        listPages.add(numberOfPages - 2);
        listPages.add(numberOfPages - 1);
        return listPages;
    }

    @Override
    protected void calculate() {
        super.calculate();
        itemsList = getPages(currentPage);
    }


    @Override
    protected void updateView() {
        view.getRootView().clear();
        items.clear();
        if (itemsList.size() >= 2)
            view.getRootView().addItem(new PagerItem(this, "Previous", mouseEvent -> previous()));
        itemsList.forEach(e -> items.add(new PagerItem(this, e)));
        items.forEach(e -> view.getRootView().addItem(e));
        if (itemsList.size() >= 2)
            view.getRootView().addItem(new PagerItem(this, "Next", mouseEvent -> next()));
        setCurrentPageView();
        super.updateView();
    }

    void setCurrentPageView() {
        items.forEach(e -> {
            if (e.getUserObject() == currentPage) e.setActive(true);
        });
    }


}
