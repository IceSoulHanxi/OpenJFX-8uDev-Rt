/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.javafx.scene.control.infrastructure;

import static org.junit.Assert.assertNotNull;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.util.List;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import com.sun.javafx.scene.control.skin.LabeledText;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.sun.javafx.scene.control.skin.VirtualScrollBar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VirtualFlowTestUtils {

    public static void assertListContainsItemsInOrder(final List items, final Object... expected) {
        assertEquals(expected.length, items.size());
        for (int i = 0; i < expected.length; i++) {
            Object item = items.get(i);
            assertEquals(expected[i], item);
        }
    }

    public static void clickOnRow(final Control control, int row, KeyModifier... modifiers) {
        IndexedCell cell = VirtualFlowTestUtils.getCell(control, row);

        if ((cell instanceof TableRow) || (cell instanceof TreeTableRow)) {
            for (Node n : cell.getChildrenUnmodifiable()) {
                if (! (n instanceof IndexedCell)) {
                    continue;
                }
                IndexedCell<?> childCell = (IndexedCell<?>)n;
                new MouseEventFirer(childCell).fireMousePressAndRelease(modifiers);
                break;
            }
        } else {
            new MouseEventFirer(cell).fireMousePressAndRelease(modifiers);
        }
    }

    public static void assertRowsEmpty(final Control control, final int startRow, final int endRow) {
        assertRows(control, startRow, endRow, true);
    }

    public static void assertRowsNotEmpty(final Control control, final int startRow, final int endRow) {
        assertRows(control, startRow, endRow, false);
    }

    public static void assertCellEmpty(IndexedCell cell) {
        if (cell instanceof TableRow || cell instanceof TreeTableRow) {
            for (Node n : cell.getChildrenUnmodifiable()) {
                if (! (n instanceof IndexedCell)) {
                    continue;
                }
                IndexedCell<?> childCell = (IndexedCell<?>)n;
                assertCellEmpty(childCell);
            }
        } else {
            final String text = cell.getText();
            assertTrue("Expected null, found '" + text + "'", text == null || text.isEmpty());

            final Node graphic = cell.getGraphic();
            assertTrue("Expected null graphic, found " + graphic, graphic == null);
        }
    }

    public static void assertCellNotEmpty(IndexedCell cell) {
        if (cell instanceof TableRow || cell instanceof TreeTableRow) {
            for (Node n : cell.getChildrenUnmodifiable()) {
                if (! (n instanceof IndexedCell)) {
                    continue;
                }
                IndexedCell<?> childCell = (IndexedCell<?>)n;
                assertCellNotEmpty(childCell);
            }
        } else {
            final String text = cell.getText();
            final Node graphic = cell.getGraphic();
            assertTrue("Expected a non-null text or graphic property",
                       (text != null && ! text.isEmpty()) || graphic != null);
        }
    }

    private static void assertRows(final Control control, final int startRow, final int endRow, final boolean expectEmpty) {
        Callback<IndexedCell<?>, Void> callback = new Callback<IndexedCell<?>, Void>() {
            @Override public Void call(IndexedCell<?> indexedCell) {
                boolean hasChildrenCell = false;
                for (Node n : indexedCell.getChildrenUnmodifiable()) {
                    if (! (n instanceof IndexedCell)) {
                        continue;
                    }
                    hasChildrenCell = true;
                    IndexedCell<?> childCell = (IndexedCell<?>)n;

                    if (expectEmpty) {
                        assertCellEmpty(childCell);
                    } else {
                        assertCellNotEmpty(childCell);
                    }
                }

                if (! hasChildrenCell) {
                    if (expectEmpty) {
                        assertCellEmpty(indexedCell);
                    } else {
                        assertCellNotEmpty(indexedCell);
                    }
                }
                return null;
            }
        };

        assertCallback(control, startRow, endRow, callback);
    }

    public static void assertCellTextEquals(final Control control, final int index, final String... expected) {
        if (expected == null || expected.length == 0) return;

        Callback<IndexedCell<?>, Void> callback = new Callback<IndexedCell<?>, Void>() {
            @Override public Void call(IndexedCell<?> indexedCell) {
                if (indexedCell.getIndex() != index) return null;

                if (expected.length == 1) {
                    assertEquals(expected[0], indexedCell.getText());
                } else {
                    int jump = 0;
                    for (int i = 0; i < expected.length; i++) {
                        Node childNode = indexedCell.getChildrenUnmodifiable().get(i + jump);
                        String text = null;
                        if (! (childNode instanceof IndexedCell)) {
                            jump++;
                            continue;
                        }

                        text = ((IndexedCell) childNode).getText();
                        assertEquals(expected[i], text);
                    }
                }
                return null;
            }
        };

        assertCallback(control, index, index + 1, callback);
    }

    public static void assertTableCellTextEquals(final Control control, final int row, final int column, final String expected) {
        Callback<IndexedCell<?>, Void> callback = new Callback<IndexedCell<?>, Void>() {
            @Override public Void call(IndexedCell<?> indexedCell) {
                if (indexedCell.getIndex() != row) return null;

                IndexedCell cell = (IndexedCell) indexedCell.getChildrenUnmodifiable().get(column);
                assertEquals(expected, cell.getText());
                return null;
            }
        };

        assertCallback(control, row, row + 1, callback);
    }

    // used by TreeView / TreeTableView to ensure the correct indentation
    // (although note that it has only been developed so far for TreeView)
    public static void assertLayoutX(final Control control, final int startRow, final int endRow, final double expectedLayoutX) {
        Callback<IndexedCell<?>, Void> callback = new Callback<IndexedCell<?>, Void>() {
            @Override public Void call(IndexedCell<?> indexedCell) {
                List<Node> childrenOfCell = indexedCell.getChildrenUnmodifiable();
                LabeledText labeledText = null;
                for (int j = 0; j < childrenOfCell.size(); j++) {
                    Node child = childrenOfCell.get(j);
                    if (child instanceof LabeledText) {
                        labeledText = (LabeledText) child;
                    }
                }

                String error = "Element in row " + indexedCell.getIndex() + " has incorrect indentation. "
                        + "Expected " + expectedLayoutX + ", but found " + labeledText.getLayoutX();
                assertEquals(error, expectedLayoutX, labeledText.getLayoutX(), 0.0);
                return null;
            }
        };

        assertCallback(control, startRow, endRow, callback);
    }

    public static int getCellCount(final Control control) {
        return getVirtualFlow(control).getCellCount();
    }

    public static IndexedCell getCell(final Control control, final int index) {
        return getVirtualFlow(control).getCell(index);
    }

    public static IndexedCell getCell(final Control control, final int row, final int column) {
        IndexedCell rowCell = getVirtualFlow(control).getCell(row);
        if (column == -1) {
            return rowCell;
        }

        int count = 0;
        for (Node n : rowCell.getChildrenUnmodifiable()) {
            if (! (n instanceof IndexedCell)) {
                continue;
            }
            count++;
            if (count < column) continue;
            return (IndexedCell) n;
        }
        return null;
    }

    public static void assertCallback(final Control control, final int startRow, final int endRow, final Callback<IndexedCell<?>, Void> callback) {
        final VirtualFlow<?> flow = getVirtualFlow(control);
        final int sheetSize = flow.getCellCount();

        // NOTE: we used to only go to the end of the sheet, but now we go past that if
        // endRow desires. This is to allow for us to test cells that are visually
        // shown, but empty.
        final int end = endRow == -1 ? sheetSize : endRow; //Math.min(endRow, sheetSize);

        for (int row = startRow; row < end; row++) {
            // old approach:
            // callback.call((IndexedCell<?>)sheet.getChildren().get(row));

            // new approach:
            callback.call(flow.getCell(row));
        }
    }

    public static void assertGraphicIsVisible(final Control control, int row) {
        assertGraphicIsVisible(control, row, -1);
    }

    public static void assertGraphicIsVisible(final Control control, int row, int column) {
        Cell cell = getCell(control, row, column);
        Node graphic = cell.getGraphic();
        assertNotNull(graphic);
        assertTrue(graphic.isVisible() && graphic.getOpacity() == 1.0);
    }

    public static void assertGraphicIsNotVisible(final Control control, int row) {
        assertGraphicIsNotVisible(control, row, -1);
    }

    public static void assertGraphicIsNotVisible(final Control control, int row, int column) {
        Cell cell = getCell(control, row, column);
        Node graphic = cell.getGraphic();
        if (graphic == null) {
            return;
        }

        assertNotNull(graphic);
        assertTrue(!graphic.isVisible() || graphic.getOpacity() == 0.0);
    }

    public static void assertCellCount(final Control control, final int expected) {
        assertEquals(getVirtualFlow(control).getCellCount(), expected);
    }

    public static VirtualFlow<?> getVirtualFlow(Control control) {
        Group group = new Group();
        Scene scene = new Scene(group);

        Stage stage = new Stage();
        stage.setScene(scene);

        group.getChildren().setAll(control);
        stage.show();

        VirtualFlow<?> flow;
        if (control instanceof ComboBox) {
            final ComboBox cb = (ComboBox) control;
            final ComboBoxListViewSkin skin = (ComboBoxListViewSkin) cb.getSkin();
            control = skin.getListView();
        }

        flow = (VirtualFlow<?>)control.lookup("#virtual-flow");
        stage.close();

        return flow;
    }

    public static VirtualScrollBar getVirtualFlowVerticalScrollbar(final Control control) {
        VirtualFlow<?> flow = getVirtualFlow(control);
        VirtualScrollBar scrollBar = null;
        for (Node child : flow.getChildrenUnmodifiable()) {
            if (child instanceof VirtualScrollBar) {
                if (((VirtualScrollBar)child).getOrientation() == Orientation.VERTICAL) {
                    scrollBar = (VirtualScrollBar) child;
                }
            }
        }

        //        Toolkit.getToolkit().firePulse();
        return scrollBar;
    }
}
