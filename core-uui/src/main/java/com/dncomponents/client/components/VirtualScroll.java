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

package com.dncomponents.client.components;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.core.ui.list.ScrollView;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Support for virtual scrolling.
 * Its purpose is to show large number of rows smoothly
 * without affecting browser performance.
 * It is turned on by default on all components that are using this class.
 * It is highly recommended to keep it turned on.
 *
 * @author nikolasavic
 */
public class VirtualScroll {

    private static final int BLOCK_SIZE = 20;
    static final int ROW_SIZE_SCROLL_STARTS = 200;
    AbstractCellComponent owner;
    /**
     * defines number of rows after virtual scroll is turned on
     */
    int scrollStarts = ROW_SIZE_SCROLL_STARTS;
    int VISIBLE_BLOCK = 4;//visible blocks in scrolling component
    int blockRowsSize = BLOCK_SIZE;//number of rows in block
    int rowHeight;
    int rows; //number of rows
    //    int topScroll = 12111; //last
    int topScroll = 11; //first
    //calculated values
    int currentBlock;
    int blockHeight; //height in pixels
    int lastBlockHeight;
    int totalHeight; //height of all scrollable area
    // height of block number usually less than totalHeight
    private int evenHeight;
    int totalNumberOfBlocks;
    private double blockEvenPercent; // block height in percent
    private ScrollView view;

    private boolean enabled = true;
    HTMLElement topElement;
    HTMLElement bottomElement;
    private List<Integer> oldBlocks = new ArrayList<>();
    final List<Integer> blocks = new ArrayList<>();
    int topHeight;
    int bottomHeight;
    private HandlerRegistration scrollHandlerRegistration;

    public VirtualScroll(AbstractCellComponent owner, ScrollView scrollView) {
        this.owner = owner;
        this.view = scrollView;
        this.rowHeight = view.getRowHeight();
        init();
    }

    public VirtualScroll() {
    }

    void init() {
        setRowsSize();
        calculateAll();
        addScrollHandler();
    }

    private void addScrollHandler() {
        scrollHandlerRegistration = view.addScrollHandler(event -> {
            if (enabled && isRowsStart())
                setTopScroll(new Double(view.getScrollTop()).intValue());
        });
    }

    private void calculateTotalHeight() {
        totalHeight = rows * rowHeight;
    }

    private void calculateBlockHeight() {
        blockHeight = blockRowsSize * rowHeight;
    }

    private void calculateNumberOfBlocks() {
        //number of blocks as a whole numbers.
        totalNumberOfBlocks = new Double(rows / blockRowsSize).intValue();
        // height of block number usually less than totalHeight
        evenHeight = totalNumberOfBlocks * blockHeight;
        if (evenHeight != 0)
            blockEvenPercent = ((double) blockHeight / evenHeight);
    }

    private void calculateLastBlockHeight() {
        int rest = rows - (totalNumberOfBlocks * blockRowsSize);
        lastBlockHeight = rest * rowHeight;
    }

    private void calculateCurrentBlock() {
        if (evenHeight != 0 && blockEvenPercent != 0)
            currentBlock = (int) (((double) topScroll / evenHeight) / blockEvenPercent);
    }

    protected void initTopBottomElements() {
        topElement = view.createEmptyRow();
        bottomElement = view.createEmptyRow();
    }

    void setHeight(HTMLElement el, int height) {
        DomUtil.setHeight(el, height + "px");
    }

    private void updateView() {
        view.clear();
        initTopBottomElements();
        view.addItem(topElement);
        calculateTopBottomHeights();
        setHeight(topElement, topHeight);
        setHeight(bottomElement, bottomHeight);

        for (Integer block : blocks) {
            Range range = getRangeFromBlock(block);
            for (int i = range.from; i < range.to; i++)
                owner.createAndInitModelRowCell(owner.rowsFiltered.get(i));
        }
        owner.newBlock();
        view.addItem(bottomElement);
        oldBlocks = copy(blocks);
    }

    void calculateAll() {
        calculateTotalHeight();
        calculateBlockHeight();
        calculateNumberOfBlocks();
        calculateCurrentBlock();
        calculateVisibleBlocks();
        calculateLastBlockHeight();
        calculateTopBottomHeights();
    }

    private void setRowsSize() {
        rows = owner.rowsFiltered.size();
    }

    private List<Integer> copy(List<Integer> list) {
        List<Integer> copy = new ArrayList<>();
        for (Integer i : list) {
            copy.add(i.intValue());
        }
        return copy;
    }

    private boolean isRowsStart() {
        return rows > scrollStarts;
    }

    boolean isSame(List<Integer> list1, List<Integer> list2) {
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i).intValue() != list2.get(i).intValue())
                return false;
        }
        return true;
    }

    public Range getRangeFromBlock(int block) {
        int from = block * blockRowsSize;
        int sum = from + blockRowsSize;
        int to = sum > rows ? rows : sum;
        return new Range(from, to);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setTopScroll(int topScroll) {
        if (topScroll < 0 || topScroll > totalHeight)
            DomGlobal.console.log("error: Top scroll must be in range: " + 0 + " - " + totalHeight);
//            throw new IllegalArgumentException("Top scroll must be in range: " + 0 + " - " + totalHeight);
        this.rows = owner.rowsFiltered.size();
        this.topScroll = topScroll;
        int oldBlock = currentBlock;
        calculateCurrentBlock();
        calculateVisibleBlocks();
        if (currentBlock != oldBlock) {
            owner.getCells().clear();
            updateView();
        }
    }

    private int getRowHeight() {
        return view.getRowHeight();
    }

    void calculateVisibleBlocks() {
        blocks.clear();
        assert currentBlock >= 0;
        assert currentBlock <= totalNumberOfBlocks;

        int half = VISIBLE_BLOCK / 2;  //2
        int from = currentBlock - half;
        Integer toAdd = null;
        if (from < 0) {
            toAdd = Math.abs(from);
            from = 0;
        }
        for (int i = from; i < currentBlock; i++) {
            blocks.add(i);
        }
        Integer toSubtracts = null;
        int to = currentBlock + half + (toAdd != null ? toAdd : 0);
        // total number of blocks 25
        if (to > totalNumberOfBlocks) {
            toSubtracts = to - totalNumberOfBlocks - 1;
            to = totalNumberOfBlocks + 1;
        }
        for (int i = currentBlock; i < to; i++) {
            blocks.add(i);
        }
        if (toSubtracts != null)
            for (int i = 0; i < toSubtracts; i++) {
                blocks.add(0, blocks.get(0) - 1);
            }
    }

    int getHalf() {
        return VISIBLE_BLOCK / 2;
    }

    void calculateTopBottomHeights() {
        topHeight = blocks.get(0) * blockHeight;
        int n = totalNumberOfBlocks - blocks.get(VISIBLE_BLOCK - 1);
        bottomHeight = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                bottomHeight += lastBlockHeight;
                continue;
            }
            bottomHeight += blockHeight;
        }
    }

    /**
     * Draws data if number of rows is above {@link #scrollStarts}
     * otherwise draws data without virtual scroll.
     */
    void drawData() {
        setRowsSize();
        calculateAll();
        updateView();
    }

    /**
     * Defines number of rows after VirtualScroll is turned on.
     * default is {@link #scrollStarts}
     *
     * @param rowNumber row number to start virtual scroll
     */
    public void setScrollStarts(int rowNumber) {
        this.scrollStarts = rowNumber;
    }

    public void removeScrollHandler() {
        if (scrollHandlerRegistration != null) {
            scrollHandlerRegistration.removeHandler();
            scrollHandlerRegistration = null;
        }
    }

    public void scrollingStarts() {
        if (scrollHandlerRegistration == null)
            addScrollHandler();
    }

    public class Range {
        public final int from;
        public final int to;

        Range(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }
}
