/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.sun.javafx.scene.control.behavior;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.scene.control.Control;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableFocusModel;
import javafx.scene.control.TablePositionBase;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import java.util.ArrayList;
import java.util.List;
import com.sun.javafx.PlatformUtil;
import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.END;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.F2;
import static javafx.scene.input.KeyCode.HOME;
import static javafx.scene.input.KeyCode.KP_DOWN;
import static javafx.scene.input.KeyCode.KP_LEFT;
import static javafx.scene.input.KeyCode.KP_RIGHT;
import static javafx.scene.input.KeyCode.KP_UP;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.PAGE_DOWN;
import static javafx.scene.input.KeyCode.PAGE_UP;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.SPACE;
import static javafx.scene.input.KeyCode.TAB;
import static javafx.scene.input.KeyCode.UP;

public abstract class TableViewBehaviorBase<C extends Control, T, TC extends TableColumnBase<T,?>> extends BehaviorBase<C> {

    /**************************************************************************
     *                                                                        *
     * Setup key bindings                                                     *
     *                                                                        *  
     *************************************************************************/
    protected static final List<KeyBinding> TABLE_VIEW_BINDINGS = new ArrayList<KeyBinding>();

    static {
        TABLE_VIEW_BINDINGS.add(new KeyBinding(TAB, "TraverseNext"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(TAB, "TraversePrevious").shift());

        TABLE_VIEW_BINDINGS.add(new KeyBinding(HOME, "SelectFirstRow"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(END, "SelectLastRow"));
        
        TABLE_VIEW_BINDINGS.add(new KeyBinding(PAGE_UP, "ScrollUp"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(PAGE_DOWN, "ScrollDown"));

        TABLE_VIEW_BINDINGS.add(new KeyBinding(LEFT, "SelectLeftCell"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(KP_LEFT, "SelectLeftCell"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(RIGHT, "SelectRightCell"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(KP_RIGHT, "SelectRightCell"));

        TABLE_VIEW_BINDINGS.add(new KeyBinding(UP, "SelectPreviousRow"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(KP_UP, "SelectPreviousRow"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(DOWN, "SelectNextRow"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(KP_DOWN, "SelectNextRow"));

        TABLE_VIEW_BINDINGS.add(new KeyBinding(LEFT, "TraverseLeft"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(KP_LEFT, "TraverseLeft"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(RIGHT, "SelectNextRow"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(KP_RIGHT, "SelectNextRow"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(UP, "TraverseUp"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(KP_UP, "TraverseUp"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(DOWN, "TraverseDown"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(KP_DOWN, "TraverseDown"));

        TABLE_VIEW_BINDINGS.add(new KeyBinding(HOME, "SelectAllToFirstRow").shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(END, "SelectAllToLastRow").shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(PAGE_UP, "SelectAllPageUp").shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(PAGE_DOWN, "SelectAllPageDown").shift());

        TABLE_VIEW_BINDINGS.add(new KeyBinding(UP, "AlsoSelectPrevious").shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(KP_UP, "AlsoSelectPrevious").shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(DOWN, "AlsoSelectNext").shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(KP_DOWN, "AlsoSelectNext").shift());
        
        TABLE_VIEW_BINDINGS.add(new KeyBinding(SPACE, "SelectAllToFocus").shift());

//        TABLE_VIEW_BINDINGS.add(new KeyBinding(UP, "AlsoSelectPreviousCell").shift());
//        TABLE_VIEW_BINDINGS.add(new KeyBinding(KP_UP, "AlsoSelectPreviousCell").shift());
//        TABLE_VIEW_BINDINGS.add(new KeyBinding(DOWN, "AlsoSelectNextCell").shift());
//        TABLE_VIEW_BINDINGS.add(new KeyBinding(KP_DOWN, "AlsoSelectNextCell").shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(LEFT, "AlsoSelectLeftCell").shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(KP_LEFT, "AlsoSelectLeftCell").shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(RIGHT, "AlsoSelectRightCell").shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(KP_RIGHT, "AlsoSelectRightCell").shift());

        TABLE_VIEW_BINDINGS.add(new KeyBinding(UP, "FocusPreviousRow").shortcut());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(DOWN, "FocusNextRow").shortcut());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(RIGHT, "FocusRightCell").shortcut());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(KP_RIGHT, "FocusRightCell").shortcut());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(LEFT, "FocusLeftCell").shortcut());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(KP_LEFT, "FocusLeftCell").shortcut());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(A, "SelectAll").shortcut());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(HOME, "FocusFirstRow").shortcut());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(END, "FocusLastRow").shortcut());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(PAGE_UP, "FocusPageUp").shortcut());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(PAGE_DOWN, "FocusPageDown").shortcut());

        TABLE_VIEW_BINDINGS.add(new KeyBinding(UP, "DiscontinuousSelectPreviousRow").shortcut().shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(DOWN, "DiscontinuousSelectNextRow").shortcut().shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(LEFT, "DiscontinuousSelectPreviousColumn").shortcut().shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(RIGHT, "DiscontinuousSelectNextColumn").shortcut().shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(PAGE_UP, "DiscontinuousSelectPageUp").shortcut().shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(PAGE_DOWN, "DiscontinuousSelectPageDown").shortcut().shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(HOME, "DiscontinuousSelectAllToFirstRow").shortcut().shift());
        TABLE_VIEW_BINDINGS.add(new KeyBinding(END, "DiscontinuousSelectAllToLastRow").shortcut().shift());
        
        if (PlatformUtil.isMac()) {
            TABLE_VIEW_BINDINGS.add(new KeyBinding(SPACE, "toggleFocusOwnerSelection").ctrl().shortcut());
        } else {
            TABLE_VIEW_BINDINGS.add(new KeyBinding(SPACE, "toggleFocusOwnerSelection").ctrl());
        }

        TABLE_VIEW_BINDINGS.add(new KeyBinding(ENTER, "Activate"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(SPACE, "Activate"));
        TABLE_VIEW_BINDINGS.add(new KeyBinding(F2, "Activate"));
//        TABLE_VIEW_BINDINGS.add(new KeyBinding(SPACE, "Activate").ctrl());
        
        TABLE_VIEW_BINDINGS.add(new KeyBinding(ESCAPE, "CancelEdit"));
    }

    @Override protected void callAction(String name) {
        if ("SelectPreviousRow".equals(name)) selectPreviousRow();
        else if ("SelectNextRow".equals(name)) selectNextRow();
        else if ("SelectLeftCell".equals(name)) selectLeftCell();
        else if ("SelectRightCell".equals(name)) selectRightCell();
        else if ("SelectFirstRow".equals(name)) selectFirstRow();
        else if ("SelectLastRow".equals(name)) selectLastRow();
        else if ("SelectAll".equals(name)) selectAll();
        else if ("SelectAllPageUp".equals(name)) selectAllPageUp();
        else if ("SelectAllPageDown".equals(name)) selectAllPageDown();
        else if ("SelectAllToFirstRow".equals(name)) selectAllToFirstRow();
        else if ("SelectAllToLastRow".equals(name)) selectAllToLastRow();
        else if ("AlsoSelectNext".equals(name)) alsoSelectNext();
        else if ("AlsoSelectPrevious".equals(name)) alsoSelectPrevious();
        else if ("AlsoSelectLeftCell".equals(name)) alsoSelectLeftCell();
        else if ("AlsoSelectRightCell".equals(name)) alsoSelectRightCell();
        else if ("ClearSelection".equals(name)) clearSelection();
        else if ("ScrollUp".equals(name)) scrollUp();
        else if ("ScrollDown".equals(name)) scrollDown();
        else if ("FocusPreviousRow".equals(name)) focusPreviousRow();
        else if ("FocusNextRow".equals(name)) focusNextRow();
        else if ("FocusLeftCell".equals(name)) focusLeftCell();
        else if ("FocusRightCell".equals(name)) focusRightCell();
        else if ("Activate".equals(name)) activate();
        else if ("CancelEdit".equals(name)) cancelEdit();
        else if ("FocusFirstRow".equals(name)) focusFirstRow();
        else if ("FocusLastRow".equals(name)) focusLastRow();
        else if ("toggleFocusOwnerSelection".equals(name)) toggleFocusOwnerSelection();
        else if ("SelectAllToFocus".equals(name)) selectAllToFocus();
        else if ("FocusPageUp".equals(name)) focusPageUp();
        else if ("FocusPageDown".equals(name)) focusPageDown();
        else if ("DiscontinuousSelectNextRow".equals(name)) discontinuousSelectNextRow();
        else if ("DiscontinuousSelectPreviousRow".equals(name)) discontinuousSelectPreviousRow();
        else if ("DiscontinuousSelectNextColumn".equals(name)) discontinuousSelectNextColumn();
        else if ("DiscontinuousSelectPreviousColumn".equals(name)) discontinuousSelectPreviousColumn();
        else if ("DiscontinuousSelectPageUp".equals(name)) discontinuousSelectPageUp();
        else if ("DiscontinuousSelectPageDown".equals(name)) discontinuousSelectPageDown();
        else if ("DiscontinuousSelectAllToLastRow".equals(name)) discontinuousSelectAllToLastRow();
        else if ("DiscontinuousSelectAllToFirstRow".equals(name)) discontinuousSelectAllToFirstRow();
        else super.callAction(name);
    }

    @Override protected void callActionForEvent(KeyEvent e) {
        // RT-12751: we want to keep an eye on the user holding down the shift key, 
        // so that we know when they enter/leave multiple selection mode. This
        // changes what happens when certain key combinations are pressed.
        isShiftDown = e.getEventType() == KeyEvent.KEY_PRESSED && e.isShiftDown();
        isShortcutDown = e.getEventType() == KeyEvent.KEY_PRESSED && e.isShortcutDown();
        
        super.callActionForEvent(e);
    }
    
    
    
    /**************************************************************************
     *                                                                        *
     * Internal fields                                                        *
     *                                                                        *  
     *************************************************************************/
    
    protected boolean isShortcutDown = false;
    protected boolean isShiftDown = false;
    protected boolean selectionPathDeviated = false;
    protected boolean selectionChanging = false;

    protected final ListChangeListener<TablePositionBase> selectedCellsListener = new ListChangeListener<TablePositionBase>() {
        @Override public void onChanged(ListChangeListener.Change c) {
            while (c.next()) {
                TableSelectionModel sm = getSelectionModel();
                if (sm == null) return;
                
                TablePositionBase anchor = getAnchor();
                boolean cellSelectionEnabled = sm.isCellSelectionEnabled();
                
                int addedSize = c.getAddedSize();
                List<TablePositionBase> addedSubList = (List<TablePositionBase>) c.getAddedSubList();
                
                // newest selection
                if (addedSize > 0 && ! hasAnchor()) {
                    TablePositionBase tp = addedSubList.get(addedSize - 1);
                    setAnchor(tp);
                }
                
                if (!hasAnchor() && cellSelectionEnabled && ! selectionPathDeviated) {
                    // check if the selection is on the same row or column, 
                    // otherwise set selectionPathDeviated to true
                    for (int i = 0; i < addedSize; i++) {
                        TablePositionBase tp = addedSubList.get(i);
                        if (anchor.getRow() != -1 && tp.getRow() != anchor.getRow() && tp.getColumn() != anchor.getColumn()) {
                            selectionPathDeviated = true;
                            break;
                        }
                    }
                }
            }
        }
    };
    
    protected final WeakListChangeListener<TablePositionBase> weakSelectedCellsListener = 
            new WeakListChangeListener<TablePositionBase>(selectedCellsListener);
    
    

    /**************************************************************************
     *                                                                        *
     * Constructors                                                           *
     *                                                                        *  
     *************************************************************************/
    
    public TableViewBehaviorBase(C control) {
        super(control, TABLE_VIEW_BINDINGS);
    }

    
    
    /**************************************************************************
     *                                                                        *
     * Abstract API                                                           *
     *                                                                        *  
     *************************************************************************/    
    
    /**
     * Call to record the current anchor position
     */
    protected void setAnchor(TablePositionBase tp) {
        TableCellBehaviorBase.setAnchor(getControl(), tp);
        selectionPathDeviated = false;
    }
    
    /**
     * Will return the current anchor position.
     */
    protected TablePositionBase getAnchor() {
        return TableCellBehaviorBase.getAnchor(getControl(), getFocusedCell());
    }
    
    /**
     * Returns true if there is an anchor set, and false if not anchor is set.
     */
    protected boolean hasAnchor() {
        return TableCellBehaviorBase.hasAnchor(getControl());
    }
    
    /**
     * Returns the number of items in the underlying data model.
     */
    protected abstract int getItemCount();

    /**
     * Returns the focus model for the underlying UI control (which must extend
     * from TableFocusModel).
     */
    protected abstract TableFocusModel getFocusModel();
    
    /**
     * Returns the selection model for the underlying UI control (which must extend
     * from TableSelectionModel).
     */
    protected abstract TableSelectionModel<T> getSelectionModel();
    
    /**
     * Returns an observable list of all cells that are currently selected in
     * the selection model of the underlying control.
     */
    protected abstract ObservableList<? extends TablePositionBase/*<C,TC>*/> getSelectedCells();
    
    /**
     * Returns the focused cell from the focus model of the underlying control.
     */
    protected abstract TablePositionBase getFocusedCell();

    /**
     * Returns the position of the given table column in the visible leaf columns
     * list of the underlying control.
     */
    protected abstract int getVisibleLeafIndex(TableColumnBase tc);
    
    /**
     * Returns the column at the given index in the visible leaf columns list of
     * the underlying control.
     */
    protected abstract TableColumnBase getVisibleLeafColumn(int index);
    
    /**
     * Begins the edit process in the underlying control for the given row/column
     * position.
     */
    protected abstract void editCell(int row, TableColumnBase tc);
    
    /**
     * Returns an observable list of all visible leaf columns in the underlying
     * control.
     */
    protected abstract ObservableList<? extends TableColumnBase> getVisibleLeafColumns();

    /**
     * Creates a TablePositionBase instance using the underlying controls
     * concrete implementation for the given row/column intersection.
     */
    protected abstract TablePositionBase<TC> getTablePosition(int row, TableColumnBase<T,?> tc);
    
    
    
    /**************************************************************************
     *                                                                        *
     * Public API                                                             *
     *                                                                        *  
     *************************************************************************/     
    
    /*
     * Anchor is created upon
     * - initial selection of an item (by mouse or keyboard)
     * 
     * Anchor is changed when you
     * - move the selection to an item by UP/DOWN/LEFT/RIGHT arrow keys
     * - select an item by mouse click
     * - add/remove an item to/from an existing selection by CTRL+SPACE shortcut
     * - add/remove an items to/from an existing selection by CTRL+mouse click
     * 
     * Note that if an item is removed from an existing selection by 
     * CTRL+SPACE/CTRL+mouse click, anchor still remains on this item even 
     * though it is not selected.
     * 
     * Anchor is NOT changed when you
     * - create linear multi-selection by SHIFT+UP/DOWN/LEFT/RIGHT arrow keys
     * - create linear multi-selection by SHIFT+SPACE arrow keys
     * - create linear multi-selection by SHIFT+mouse click
     * 
     * In case there is a discontinuous selection in the list, creating linear 
     * multi-selection between anchor and focused item will cancel the 
     * discontinuous selection. It means that only items that are located between
     * anchor and focused item will be selected. 
     */
    protected void setAnchor(int row, TableColumnBase col) {
        setAnchor(row == -1 && col == null ? null : getTablePosition(row, col));
    }
    
    private Callback<Void, Integer> onScrollPageUp;
    public void setOnScrollPageUp(Callback<Void, Integer> c) { onScrollPageUp = c; }

    private Callback<Void, Integer> onScrollPageDown;
    public void setOnScrollPageDown(Callback<Void, Integer> c) { onScrollPageDown = c; }

    private Runnable onFocusPreviousRow;
    public void setOnFocusPreviousRow(Runnable r) { onFocusPreviousRow = r; }

    private Runnable onFocusNextRow;
    public void setOnFocusNextRow(Runnable r) { onFocusNextRow = r; }

    private Runnable onSelectPreviousRow;
    public void setOnSelectPreviousRow(Runnable r) { onSelectPreviousRow = r; }

    private Runnable onSelectNextRow;
    public void setOnSelectNextRow(Runnable r) { onSelectNextRow = r; }

    private Runnable onMoveToFirstCell;
    public void setOnMoveToFirstCell(Runnable r) { onMoveToFirstCell = r; }

    private Runnable onMoveToLastCell;
    public void setOnMoveToLastCell(Runnable r) { onMoveToLastCell = r; }

    private Runnable onSelectRightCell;
    public void setOnSelectRightCell(Runnable r) { onSelectRightCell = r; }

    private Runnable onSelectLeftCell;
    public void setOnSelectLeftCell(Runnable r) { onSelectLeftCell = r; }
    
    @Override public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        
//        // FIXME can't assume (yet) cells.get(0) is necessarily the lead cell
//        ObservableList<? extends TablePositionBase> cells = getSelectedCells();
//        setAnchor(cells.isEmpty() ? null : cells.get(0));
        
        if (!getControl().isFocused() && getControl().isFocusTraversable()) {
            getControl().requestFocus();
        }
    }
    
    
    
    /**************************************************************************
     *                                                                        *
     * Private implementation                                                 *
     *                                                                        *  
     *************************************************************************/ 
    
    protected void scrollUp() {
        TableSelectionModel<T> sm = getSelectionModel();
        if (sm == null || getSelectedCells().isEmpty()) return;
        
        TablePositionBase<TC> selectedCell = getSelectedCells().get(0);
        
        int newSelectedIndex = -1;
        if (onScrollPageUp != null) {
            newSelectedIndex = onScrollPageUp.call(null);
        }
        if (newSelectedIndex == -1) return;
        
        sm.clearAndSelect(newSelectedIndex, selectedCell.getTableColumn());
    }

    protected void scrollDown() {
        TableSelectionModel<T> sm = getSelectionModel();
        if (sm == null || getSelectedCells().isEmpty()) return;
        
        TablePositionBase<TC> selectedCell = getSelectedCells().get(0);
        
        int newSelectedIndex = -1;
        if (onScrollPageDown != null) {
            newSelectedIndex = onScrollPageDown.call(null);
        }
        if (newSelectedIndex == -1) return;
        
        sm.clearAndSelect(newSelectedIndex, selectedCell.getTableColumn());
    }
    
    protected void focusFirstRow() {
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;
        
        TableColumnBase tc = getFocusedCell() == null ? null : getFocusedCell().getTableColumn();
        fm.focus(0, tc);
        
        if (onMoveToFirstCell != null) onMoveToFirstCell.run();
    }
    
    protected void focusLastRow() {
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;
        
        TableColumnBase tc = getFocusedCell() == null ? null : getFocusedCell().getTableColumn();
        fm.focus(getItemCount() - 1, tc);
        
        if (onMoveToLastCell != null) onMoveToLastCell.run();
    }

    protected void focusPreviousRow() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;

        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        if (sm.isCellSelectionEnabled()) {
            fm.focusAboveCell();
        } else {
            fm.focusPrevious();
        }
        
        if (! isShortcutDown || getAnchor() == null) {
            setAnchor(fm.getFocusedIndex(), null);
        }

        if (onFocusPreviousRow != null) onFocusPreviousRow.run();
    }

    protected void focusNextRow() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;

        TableFocusModel fm = getFocusModel();
        if (fm == null) return;
        
        if (sm.isCellSelectionEnabled()) {
            fm.focusBelowCell();
        } else {
            fm.focusNext();
        }
        
        if (! isShortcutDown || getAnchor() == null) {
            setAnchor(fm.getFocusedIndex(), null);
        }
        
        if (onFocusNextRow != null) onFocusNextRow.run();
    }

    protected void focusLeftCell() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;

        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        fm.focusLeftCell();
        if (onFocusPreviousRow != null) onFocusPreviousRow.run();
    }

    protected void focusRightCell() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;

        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        fm.focusRightCell();
        if (onFocusNextRow != null) onFocusNextRow.run();
    }
    
    protected void focusPageUp() {
        int newFocusIndex = onScrollPageUp.call(null);
        
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;
        TableColumnBase tc = getFocusedCell() == null ? null : getFocusedCell().getTableColumn();
        fm.focus(newFocusIndex, tc);
    }
    
    protected void focusPageDown() {
        int newFocusIndex = onScrollPageDown.call(null);
        
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;
        TableColumnBase tc = getFocusedCell() == null ? null : getFocusedCell().getTableColumn();
        fm.focus(newFocusIndex, tc);
    }

    protected void clearSelection() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;

        sm.clearSelection();
    }
    
    protected void clearSelectionOutsideRange(int start, int end) {
        TableSelectionModel<T> sm = getSelectionModel();
        if (sm == null) return;
        
        int min = Math.min(start, end);
        int max = Math.max(start, end);
        
        List<Integer> indices = new ArrayList<Integer>(sm.getSelectedIndices());
        
        selectionChanging = true;
        for (int i = 0; i < indices.size(); i++) {
            int index = indices.get(i);
            if (index < min || index >= max) {
                sm.clearSelection(index);
            }
        }
        selectionChanging = false;
    }

    protected void alsoSelectPrevious() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null || sm.getSelectionMode() == SelectionMode.SINGLE) return;
        
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;
        
        if (sm.isCellSelectionEnabled()) {
            updateCellVerticalSelection(-1, new Runnable() {
                @Override public void run() {
                    getSelectionModel().selectAboveCell();
                }
            });
        } else {
            if (isShiftDown && hasAnchor()) {
                updateRowSelection(-1);
            } else {
                sm.selectPrevious();
            }
        }
        onSelectPreviousRow.run();
    }
    
    protected void alsoSelectNext() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null || sm.getSelectionMode() == SelectionMode.SINGLE) return;
        
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        if (sm.isCellSelectionEnabled()) {
            updateCellVerticalSelection(1, new Runnable() {
                @Override public void run() {
                    getSelectionModel().selectBelowCell();
                }
            });
        } else {
            if (isShiftDown && hasAnchor()) {
                updateRowSelection(1);
            } else {
                sm.selectNext();
            }
        }
        onSelectNextRow.run();
    }
    
    protected void alsoSelectLeftCell() {
        updateCellHorizontalSelection(-1, new Runnable() {
            @Override public void run() { 
                getSelectionModel().selectLeftCell();
            }
        });
    }

    protected void alsoSelectRightCell() {
        updateCellHorizontalSelection(1, new Runnable() {
            @Override public void run() { 
                getSelectionModel().selectRightCell();
            }
        });
    }
    
    protected void updateRowSelection(int delta) {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null || sm.getSelectionMode() == SelectionMode.SINGLE) return;
        
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;
        
        int newRow = fm.getFocusedIndex() + delta;
        TablePositionBase anchor = getAnchor();
        
        if (! hasAnchor()) {
            setAnchor(getFocusedCell());
        } 

        clearSelectionOutsideRange(anchor.getRow(), newRow);

        if (anchor.getRow() > newRow) {
            sm.selectRange(anchor.getRow(), newRow - 1);
        } else {
            sm.selectRange(anchor.getRow(), newRow + 1);
        }
    }
    
    protected void updateCellVerticalSelection(int delta, Runnable defaultAction) {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null || sm.getSelectionMode() == SelectionMode.SINGLE) return;
        
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;
        
        TablePositionBase focusedCell = getFocusedCell();
        if (isShiftDown && sm.isSelected(focusedCell.getRow() + delta, focusedCell.getTableColumn())) {
            int newFocusOwner = focusedCell.getRow() + delta;
            sm.clearSelection(selectionPathDeviated ? newFocusOwner : focusedCell.getRow(), focusedCell.getTableColumn());
            fm.focus(newFocusOwner, focusedCell.getTableColumn());
        } else if (isShiftDown && getAnchor() != null && ! selectionPathDeviated) {
            int newRow = fm.getFocusedIndex() + delta;
            
            // we don't let the newRow go outside the bounds of the data
            newRow = Math.max(Math.min(getItemCount() - 1, newRow), 0);

            int start = Math.min(getAnchor().getRow(), newRow);
            int end = Math.max(getAnchor().getRow(), newRow);
            for (int _row = start; _row <= end; _row++) {
                sm.select(_row, focusedCell.getTableColumn());
            }
            fm.focus(newRow, focusedCell.getTableColumn());
        } else {
            final int focusIndex = fm.getFocusedIndex();
            if (! sm.isSelected(focusIndex, focusedCell.getTableColumn())) {
                sm.select(focusIndex, focusedCell.getTableColumn());
            }
            defaultAction.run();
        }
    }
    
    protected void updateCellHorizontalSelection(int delta, Runnable defaultAction) {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null || sm.getSelectionMode() == SelectionMode.SINGLE) return;

        TableFocusModel fm = getFocusModel();
        if (fm == null) return;
        
        TablePositionBase focusedCell = getFocusedCell();
        if (focusedCell == null || focusedCell.getTableColumn() == null) return;
        
        TableColumnBase adjacentColumn = getColumn(focusedCell.getTableColumn(), delta);
        if (adjacentColumn == null) return;
        
        if (isShiftDown && getAnchor() != null && 
            sm.isSelected(focusedCell.getRow(), adjacentColumn) &&
            ! (focusedCell.getRow() == getAnchor().getRow() && focusedCell.getTableColumn().equals(adjacentColumn))) {
                sm.clearSelection(focusedCell.getRow(),selectionPathDeviated ? adjacentColumn : focusedCell.getTableColumn());
                fm.focus(focusedCell.getRow(), adjacentColumn);
        } else if (isShiftDown && getAnchor() != null && ! selectionPathDeviated) {
            final int columnPos = getVisibleLeafIndex(focusedCell.getTableColumn());
            final int newColumn = columnPos + delta;

            int start = Math.min(columnPos, newColumn);
            int end = Math.max(columnPos, newColumn);
            for (int _col = start; _col <= end; _col++) {
                sm.select(focusedCell.getRow(), getColumn(_col));
            }
            fm.focus(focusedCell.getRow(), getColumn(newColumn));
        } else {
            defaultAction.run();
        }
    }
    
    protected TableColumnBase getColumn(int index) {
        return getVisibleLeafColumn(index);
    }
    
    protected TableColumnBase getColumn(TableColumnBase tc, int delta) {
        return getVisibleLeafColumn(getVisibleLeafIndex(tc) + delta);
    }

    protected void selectFirstRow() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;

        ObservableList<? extends TablePositionBase> selection = getSelectedCells();
        TableColumnBase<?,?> selectedColumn = selection.size() == 0 ? null : selection.get(0).getTableColumn();
        sm.clearAndSelect(0, selectedColumn);

        if (onMoveToFirstCell != null) onMoveToFirstCell.run();
    }

    protected void selectLastRow() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;

        ObservableList<? extends TablePositionBase> selection = getSelectedCells();
        TableColumnBase<?,?> selectedColumn = selection.size() == 0 ? null : selection.get(0).getTableColumn();
        sm.clearAndSelect(getItemCount() - 1, selectedColumn);

        if (onMoveToLastCell != null) onMoveToLastCell.run();
    }

    protected void selectPreviousRow() {
        selectCell(-1, 0);
        if (onSelectPreviousRow != null) onSelectPreviousRow.run();
    }

    protected void selectNextRow() {
        selectCell(1, 0);
        if (onSelectNextRow != null) onSelectNextRow.run();
    }

    protected void selectLeftCell() {
        selectCell(0, -1);
        if (onSelectLeftCell != null) onSelectLeftCell.run();
    }

    protected void selectRightCell() {
        selectCell(0, 1);
        if (onSelectRightCell != null) onSelectRightCell.run();
    }

    protected void selectCell(int rowDiff, int columnDiff) {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;

        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        TablePositionBase<TC> focusedCell = getFocusedCell();
        int currentRow = focusedCell.getRow();
        int currentColumn = getVisibleLeafIndex(focusedCell.getTableColumn());
        if (rowDiff < 0 && currentRow == 0) return;
        else if (rowDiff > 0 && currentRow == getItemCount() - 1) return;
        else if (columnDiff < 0 && currentColumn == 0) return;
        else if (columnDiff > 0 && currentColumn == getVisibleLeafColumns().size() - 1) return;

        TableColumnBase tc = focusedCell.getTableColumn();
        tc = getColumn(tc, columnDiff);
        
        int row = focusedCell.getRow() + rowDiff;
        sm.clearAndSelect(row, tc);
        setAnchor(row, tc);
    }
    
    protected void cancelEdit() {
        editCell(-1, null);
    }

    protected void activate() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;

        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        TablePositionBase<TC> cell = getFocusedCell();
        sm.select(cell.getRow(), cell.getTableColumn());

        // edit this row also
        if (cell.getRow() >= 0) {
            editCell(cell.getRow(), cell.getTableColumn());
        }
    }
    
    protected void selectAllToFocus() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;

        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        TablePositionBase<TC> focusedCell = getFocusedCell();
        int focusRow = focusedCell.getRow();
        
        TablePositionBase<TC> anchor = getAnchor();
        int anchorRow = anchor.getRow();
        
        sm.clearSelection();
        if (! sm.isCellSelectionEnabled()) {
            int startPos = anchorRow;
            int endPos = anchorRow > focusRow ? focusRow - 1 : focusRow + 1;
            sm.selectRange(startPos, endPos);
        } else {
            // we add all cells/rows between the current selection focus and
            // the acnhor (inclusive) to the current selection.

            // and then determine all row and columns which must be selected
            int minRow = Math.min(focusedCell.getRow(), anchorRow);
            int maxRow = Math.max(focusedCell.getRow(), anchorRow);
            final int focusedCellColumnPos = getVisibleLeafIndex(focusedCell.getTableColumn());
            final int anchorColumnPos = getVisibleLeafIndex(anchor.getTableColumn());
            int minColumn = Math.min(focusedCellColumnPos, anchorColumnPos);
            int maxColumn = Math.max(focusedCellColumnPos, anchorColumnPos);

            // clear selection
            sm.clearSelection();

            // and then perform the selection
            for (int _row = minRow; _row <= maxRow; _row++) {
                for (int _col = minColumn; _col <= maxColumn; _col++) {
                    sm.select(_row, getVisibleLeafColumn(_col));
                }
            }
        }
        
        setAnchor(anchor);
    }
    
    protected void selectAll() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;
        sm.selectAll();
    }

    protected void selectAllToFirstRow() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;

        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        TablePositionBase focusedCell = getFocusedCell();
        
        int leadIndex = focusedCell.getRow();
        
        if (isShiftDown) {
            leadIndex = getAnchor() == null ? leadIndex : getAnchor().getRow();
        }

        sm.clearSelection();
        if (! sm.isCellSelectionEnabled()) {
            // we are going from 0 to one before the focused cell as that is
            // the requirement of selectRange, so we call focus on the 0th row
            sm.selectRange(0, leadIndex + 1);
            getFocusModel().focus(0);
//            setAnchor(leadIndex, null);
        } else {
            // TODO
            
//            setAnchor(leadIndex, );
        }
        
        if (isShiftDown) {
            setAnchor(leadIndex, null);
        }

        if (onMoveToFirstCell != null) onMoveToFirstCell.run();
    }

    protected void selectAllToLastRow() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;

        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        TablePositionBase focusedCell = getFocusedCell();
        
        int leadIndex = focusedCell.getRow();
        
        if (isShiftDown) {
            leadIndex = getAnchor() == null ? leadIndex : getAnchor().getRow();
        }
        
        sm.clearSelection();
        if (! sm.isCellSelectionEnabled()) {
            sm.selectRange(leadIndex, getItemCount());
        } else {
            // TODO
        }
        
        if (isShiftDown) {
            setAnchor(leadIndex, null);
        }

        if (onMoveToLastCell != null) onMoveToLastCell.run();
    }
    
    protected void selectAllPageUp() {
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        int leadIndex = fm.getFocusedIndex();
        if (isShiftDown) {
            leadIndex = getAnchor() == null ? leadIndex : getAnchor().getRow();
            setAnchor(leadIndex, null);
        }
        
        int leadSelectedIndex = onScrollPageUp.call(null);
        
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;
        
        selectionChanging = true;
        sm.clearSelection();
        sm.selectRange(leadSelectedIndex, leadIndex + 1);
        selectionChanging = false;
    }
    
    protected void selectAllPageDown() {
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;
        
        int leadIndex = fm.getFocusedIndex();
        if (isShiftDown) {
            leadIndex = getAnchor() == null ? leadIndex : getAnchor().getRow();
            setAnchor(leadIndex, null);
        }
        
        int leadSelectedIndex = onScrollPageDown.call(null);
        
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;
        
        selectionChanging = true;
        sm.clearSelection();
        sm.selectRange(leadIndex, leadSelectedIndex + 1);
        selectionChanging = false;
    }
    
    protected void toggleFocusOwnerSelection() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;

        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        TablePositionBase focusedCell = getFocusedCell();
        
        if (sm.isSelected(focusedCell.getRow(), focusedCell.getTableColumn())) {
            sm.clearSelection(focusedCell.getRow(), focusedCell.getTableColumn());
            fm.focus(focusedCell.getRow(), focusedCell.getTableColumn());
        } else {
            sm.select(focusedCell.getRow(), focusedCell.getTableColumn());
        }
        
        setAnchor(focusedCell.getRow(), focusedCell.getTableColumn());
    }
    
    // This functionality was added, but then removed when it was realised by 
    // UX that TableView should not include 'spreadsheet-like' functionality.
    // When / if we ever introduce this kind of control, this functionality can
    // be re-enabled then.
    /*
    protected void moveToLeftMostColumn() {
        // Functionality as described in RT-12752
        if (onMoveToLeftMostColumn != null) onMoveToLeftMostColumn.run();
        
        TableSelectionModel sm = getSelectionModel();
        if (sm == null || ! sm.isCellSelectionEnabled()) return;
        
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        TablePosition focusedCell = fm.getFocusedCell();
        
        TableColumn endColumn = getControl().getVisibleLeafColumn(0);
        sm.clearAndSelect(focusedCell.getRow(), endColumn);
    }
    
    protected void moveToRightMostColumn() {
        // Functionality as described in RT-12752
        if (onMoveToRightMostColumn != null) onMoveToRightMostColumn.run();
        
        TableSelectionModel sm = getSelectionModel();
        if (sm == null || ! sm.isCellSelectionEnabled()) return;
        
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        TablePosition focusedCell = fm.getFocusedCell();
        
        TableColumn endColumn = getControl().getVisibleLeafColumn(getControl().getVisibleLeafColumns().size() - 1);
        sm.clearAndSelect(focusedCell.getRow(), endColumn);
    }
     */
    
    
    /**************************************************************************
     * Discontinuous Selection                                                *
     *************************************************************************/
    
    protected void discontinuousSelectPreviousRow() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;
        
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;
        
        int index = fm.getFocusedIndex() - 1;
        if (index < 0) return;
        
        if (! sm.isCellSelectionEnabled()) {
            sm.select(index);
        } else {
            sm.select(index, getFocusedCell().getTableColumn());
        }
    }
    
    protected void discontinuousSelectNextRow() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;
        
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        int index = fm.getFocusedIndex() + 1;
        
        if (! sm.isCellSelectionEnabled()) {
            sm.select(index);
        } else {
            sm.select(index, getFocusedCell().getTableColumn());
        }
    }
    
    protected void discontinuousSelectPreviousColumn() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null || ! sm.isCellSelectionEnabled()) return;
        
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        TableColumnBase tc = getColumn(getFocusedCell().getTableColumn(), -1);
        sm.select(fm.getFocusedIndex(), tc);
    }
    
    protected void discontinuousSelectNextColumn() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null || ! sm.isCellSelectionEnabled()) return;
        
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        TableColumnBase tc = getColumn(getFocusedCell().getTableColumn(), 1);
        sm.select(fm.getFocusedIndex(), tc);
    }
    
    protected void discontinuousSelectPageUp() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;
        
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        int leadIndex = fm.getFocusedIndex();
        int leadSelectedIndex = onScrollPageUp.call(null);
        
        if (! sm.isCellSelectionEnabled()) {
            sm.selectRange(leadSelectedIndex, leadIndex + 1);
        }
    }
    
    protected void discontinuousSelectPageDown() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;
        
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;
        
        int leadIndex = fm.getFocusedIndex();
        int leadSelectedIndex = onScrollPageDown.call(null);
        
        if (! sm.isCellSelectionEnabled()) {
            sm.selectRange(leadIndex, leadSelectedIndex + 1);
        }
    }
    
    protected void discontinuousSelectAllToFirstRow() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;
        
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        int index = fm.getFocusedIndex();
        
        if (! sm.isCellSelectionEnabled()) {
            sm.selectRange(0, index);
            fm.focus(0);
        } else {
            for (int i = 0; i < index; i++) {
                sm.select(i, getFocusedCell().getTableColumn());
            }
            fm.focus(0, getFocusedCell().getTableColumn());
        }
        
        if (onMoveToFirstCell != null) onMoveToFirstCell.run();
    }
    
    protected void discontinuousSelectAllToLastRow() {
        TableSelectionModel sm = getSelectionModel();
        if (sm == null) return;
        
        TableFocusModel fm = getFocusModel();
        if (fm == null) return;

        int index = fm.getFocusedIndex() + 1;
        
        if (! sm.isCellSelectionEnabled()) {
            sm.selectRange(index, getItemCount());
        } else {
            for (int i = index; i < getItemCount(); i++) {
                sm.select(i, getFocusedCell().getTableColumn());
            }
        }

        if (onMoveToLastCell != null) onMoveToLastCell.run();
    }   
}
