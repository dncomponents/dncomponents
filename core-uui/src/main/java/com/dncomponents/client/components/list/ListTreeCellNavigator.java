package com.dncomponents.client.components.list;


import com.dncomponents.client.components.AbstractCellComponent;
import com.google.gwt.user.client.Timer;
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

    public boolean working=false;
    Timer timer = new Timer() {
        @Override
        public void run() {
            working = false;
        }
    };

    private void selectCell(Object obj, KeyboardEvent keyboardEvent) {
        timer.cancel();
        working = true;
        setVal(obj, true, keyboardEvent);
        timer.schedule(500);

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