/*
 * Copyright (c) 2002-2013 JGoodies Software GmbH. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of JGoodies Software GmbH nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.privatejgoodies.forms.factories;

import java.io.Serializable;

import com.privatejgoodies.forms.layout.CellConstraints;
import com.privatejgoodies.forms.layout.CellConstraints.Alignment;
import com.privatejgoodies.forms.layout.FormLayout;

/**
 * A factory for CellConstraints objects.
 *
 * <strong>Examples</strong>:<br>
 * The following cell constraints locate a component in the third column of the fifth row; column
 * and row span are 1; the component will be aligned with the column's right-hand side and the row's
 * bottom.
 * <pre>
 * CC.xy  (3, 5);
 * CC.xy  (3, 5, CC.RIGHT, CC.BOTTOM);
 * CC.xy  (3, 5, "right, bottom");
 *
 * CC.xyw (3, 5, 1);
 * CC.xyw (3, 5, 1, CC.RIGHT, CC.BOTTOM);
 * CC.xyw (3, 5, 1, "right, bottom");
 *
 * CC.xywh(3, 5, 1, 1);
 * CC.xywh(3, 5, 1, 1, CC.RIGHT, CC.BOTTOM);
 * CC.xywh(3, 5, 1, 1, "right, bottom");
 * </pre> See also the examples in the {@link FormLayout} class comment.
 *
 * @author	Karsten Lentzsch
 * @version $Revision: 1.4 $
 *
 * @since 1.3
 */
public final class CC implements Cloneable, Serializable {

    // Constants ************************************************************
    public static final Alignment DEFAULT = CellConstraints.DEFAULT;
    public static final Alignment FILL = CellConstraints.FILL;
    public static final Alignment LEFT = CellConstraints.LEFT;
    public static final Alignment RIGHT = CellConstraints.RIGHT;
    public static final Alignment CENTER = CellConstraints.CENTER;
    public static final Alignment TOP = CellConstraints.TOP;
    public static final Alignment BOTTOM = CellConstraints.BOTTOM;

    // Setters with Column-Row Order ******************************************
    /**
     * Sets column and row origins; sets width and height to 1; uses the default alignments.<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.xy(1, 1);
     * CC.xy(1, 3);
     * </pre>
     *
     * @param col the new column index
     * @param row the new row index
     * @return this
     */
    public static CellConstraints xy(int col, int row) {
        return xywh(col, row, 1, 1);
    }

    /**
     * Sets column and row origins; sets width and height to 1; decodes horizontal and vertical
     * alignments from the given string.<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.xy(1, 3, "left, bottom");
     * CC.xy(1, 3, "l, b");
     * CC.xy(1, 3, "center, fill");
     * CC.xy(1, 3, "c, f");
     * </pre>
     *
     * @param col the new column index
     * @param row the new row index
     * @param encodedAlignments describes the horizontal and vertical alignments
     * @return this
     *
     * @throws IllegalArgumentException if an alignment orientation is invalid
     */
    public static CellConstraints xy(int col, int row, String encodedAlignments) {
        return xywh(col, row, 1, 1, encodedAlignments);
    }

    /**
     * Sets the column and row origins; sets width and height to 1; set horizontal and vertical
     * alignment using the specified objects.<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.xy(1, 3, CellConstraints.LEFT,   CellConstraints.BOTTOM);
     * CC.xy(1, 3, CellConstraints.CENTER, CellConstraints.FILL);
     * </pre>
     *
     * @param col the new column index
     * @param row the new row index
     * @param colAlign horizontal component alignment
     * @param rowAlign vertical component alignment
     * @return this
     */
    public static CellConstraints xy(int col, int row,
            Alignment colAlign, Alignment rowAlign) {
        return xywh(col, row, 1, 1, colAlign, rowAlign);
    }

    /**
     * Sets the column, row, width, and height; uses a height (row span) of 1 and the horizontal and
     * vertical default alignments.<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.xyw(1, 3, 7);
     * CC.xyw(1, 3, 2);
     * </pre>
     *
     * @param col the new column index
     * @param row the new row index
     * @param colSpan the column span or grid width
     * @return this
     */
    public static CellConstraints xyw(int col, int row, int colSpan) {
        return xywh(col, row, colSpan, 1, CellConstraints.DEFAULT, CellConstraints.DEFAULT);
    }

    /**
     * Sets the column, row, width, and height; decodes the horizontal and vertical alignments from
     * the given string. The row span (height) is set to 1
     * .<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.xyw(1, 3, 7, "left, bottom");
     * CC.xyw(1, 3, 7, "l, b");
     * CC.xyw(1, 3, 2, "center, fill");
     * CC.xyw(1, 3, 2, "c, f");
     * </pre>
     *
     * @param col the new column index
     * @param row the new row index
     * @param colSpan the column span or grid width
     * @param encodedAlignments describes the horizontal and vertical alignments
     * @return this
     * @throws IllegalArgumentException if an alignment orientation is invalid
     */
    public static CellConstraints xyw(int col, int row, int colSpan,
            String encodedAlignments) {
        return xywh(col, row, colSpan, 1, encodedAlignments);
    }

    /**
     * Sets the column, row, width, and height; sets the horizontal and vertical alignment using the
     * specified alignment objects. The row span (height) is set to 1
     * .<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.xyw(1, 3, 2, CellConstraints.LEFT,   CellConstraints.BOTTOM);
     * CC.xyw(1, 3, 7, CellConstraints.CENTER, CellConstraints.FILL);
     * </pre>
     *
     * @param col the new column index
     * @param row the new row index
     * @param colSpan the column span or grid width
     * @param colAlign horizontal component alignment
     * @param rowAlign vertical component alignment
     * @return this
     * @throws IllegalArgumentException if an alignment orientation is invalid
     */
    public static CellConstraints xyw(int col, int row, int colSpan,
            Alignment colAlign, Alignment rowAlign) {
        return xywh(col, row, colSpan, 1, colAlign, rowAlign);
    }

    /**
     * Sets the column, row, width, and height; uses default alignments.<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.xywh(1, 3, 2, 1);
     * CC.xywh(1, 3, 7, 3);
     * </pre>
     *
     * @param col the new column index
     * @param row the new row index
     * @param colSpan the column span or grid width
     * @param rowSpan the row span or grid height
     * @return this
     */
    public static CellConstraints xywh(int col, int row, int colSpan, int rowSpan) {
        return xywh(col, row, colSpan, rowSpan, CellConstraints.DEFAULT, CellConstraints.DEFAULT);
    }

    /**
     * Sets the column, row, width, and height; decodes the horizontal and vertical alignments from
     * the given string.<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.xywh(1, 3, 2, 1, "left, bottom");
     * CC.xywh(1, 3, 2, 1, "l, b");
     * CC.xywh(1, 3, 7, 3, "center, fill");
     * CC.xywh(1, 3, 7, 3, "c, f");
     * </pre>
     *
     * @param col the new column index
     * @param row the new row index
     * @param colSpan the column span or grid width
     * @param rowSpan the row span or grid height
     * @param encodedAlignments describes the horizontal and vertical alignments
     * @return this
     * @throws IllegalArgumentException if an alignment orientation is invalid
     */
    public static CellConstraints xywh(int col, int row, int colSpan, int rowSpan,
            String encodedAlignments) {
        return new CellConstraints().xywh(col, row, colSpan, rowSpan, encodedAlignments);
    }

    /**
     * Sets the column, row, width, and height; sets the horizontal and vertical alignment using the
     * specified alignment objects.<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.xywh(1, 3, 2, 1, CellConstraints.LEFT,   CellConstraints.BOTTOM);
     * CC.xywh(1, 3, 7, 3, CellConstraints.CENTER, CellConstraints.FILL);
     * </pre>
     *
     * @param col the new column index
     * @param row the new row index
     * @param colSpan the column span or grid width
     * @param rowSpan the row span or grid height
     * @param colAlign horizontal component alignment
     * @param rowAlign vertical component alignment
     * @return this
     * @throws IllegalArgumentException if an alignment orientation is invalid
     */
    public static CellConstraints xywh(int col, int row, int colSpan, int rowSpan,
            Alignment colAlign, Alignment rowAlign) {
        return new CellConstraints(col, row, colSpan, rowSpan, colAlign, rowAlign);
    }

    // Setters with Row-Column Order ******************************************
    /**
     * Sets row and column origins; sets height and width to 1; uses the default alignments.<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.rc(1, 1);
     * CC.rc(3, 1);
     * </pre>
     *
     * @param row the new row index
     * @param col the new column index
     * @return this
     */
    public static CellConstraints rc(int row, int col) {
        return rchw(row, col, 1, 1);
    }

    /**
     * Sets row and column origins; sets height and width to 1; decodes vertical and horizontal
     * alignments from the given string.<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.rc(3, 1, "bottom, left");
     * CC.rc(3, 1, "b, l");
     * CC.rc(3, 1, "fill, center");
     * CC.rc(3, 1, "f, c");
     * </pre>
     *
     * @param row the new row index
     * @param col the new column index
     * @param encodedAlignments describes the vertical and horizontal alignments
     * @return this
     *
     * @throws IllegalArgumentException if an alignment orientation is invalid
     */
    public static CellConstraints rc(int row, int col, String encodedAlignments) {
        return rchw(row, col, 1, 1, encodedAlignments);
    }

    /**
     * Sets the row and column origins; sets width and height to 1; set horizontal and vertical
     * alignment using the specified objects.<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.rc(3, 1, CellConstraints.BOTTOM, CellConstraints.LEFT);
     * CC.rc(3, 1, CellConstraints.FILL,   CellConstraints.CENTER);
     * </pre>
     *
     * @param row the new row index
     * @param col the new column index
     * @param rowAlign vertical component alignment
     * @param colAlign horizontal component alignment
     * @return this
     */
    public static CellConstraints rc(int row, int col,
            Alignment rowAlign, Alignment colAlign) {
        return rchw(row, col, 1, 1, rowAlign, colAlign);
    }

    /**
     * Sets the row, column, height, and width; uses a height (row span) of 1 and the vertical and
     * horizontal default alignments.<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.rcw(3, 1, 7);
     * CC.rcw(3, 1, 2);
     * </pre>
     *
     * @param row the new row index
     * @param col the new column index
     * @param colSpan the column span or grid width
     * @return this
     */
    public static CellConstraints rcw(int row, int col, int colSpan) {
        return rchw(row, col, 1, colSpan, CellConstraints.DEFAULT, CellConstraints.DEFAULT);
    }

    /**
     * Sets the row, column, height, and width; decodes the vertical and horizontal alignments from
     * the given string. The row span (height) is set to 1
     * .<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.rcw(3, 1, 7, "bottom, left");
     * CC.rcw(3, 1, 7, "b, l");
     * CC.rcw(3, 1, 2, "fill, center");
     * CC.rcw(3, 1, 2, "f, c");
     * </pre>
     *
     * @param row the new row index
     * @param col the new column index
     * @param colSpan the column span or grid width
     * @param encodedAlignments describes the vertical and horizontal alignments
     * @return this
     *
     * @throws IllegalArgumentException if an alignment orientation is invalid
     */
    public static CellConstraints rcw(int row, int col, int colSpan,
            String encodedAlignments) {
        return rchw(row, col, 1, colSpan, encodedAlignments);
    }

    /**
     * Sets the row, column, height, and width; sets the vertical and horizontal alignment using the
     * specified alignment objects. The row span (height) is set to 1
     * .<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.rcw(3, 1, 2, CellConstraints.BOTTOM, CellConstraints.LEFT);
     * CC.rcw(3, 1, 7, CellConstraints.FILL,   CellConstraints.CENTER);
     * </pre>
     *
     * @param row the new row index
     * @param col the new column index
     * @param colSpan the column span or grid width
     * @param rowAlign vertical component alignment
     * @param colAlign horizontal component alignment
     * @return this
     *
     * @throws IllegalArgumentException if an alignment orientation is invalid
     */
    public static CellConstraints rcw(int row, int col, int colSpan,
            Alignment rowAlign, Alignment colAlign) {
        return rchw(row, col, 1, colSpan, rowAlign, colAlign);
    }

    /**
     * Sets the row, column, height, and width; uses default alignments.<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.rchw(1, 3, 2, 1);
     * CC.rchw(1, 3, 7, 3);
     * </pre>
     *
     * @param row the new row index
     * @param col the new column index
     * @param rowSpan the row span or grid height
     * @param colSpan the column span or grid width
     * @return this
     */
    public static CellConstraints rchw(int row, int col, int rowSpan, int colSpan) {
        return rchw(row, col, rowSpan, colSpan, CellConstraints.DEFAULT, CellConstraints.DEFAULT);
    }

    /**
     * Sets the row, column, height, and width; decodes the vertical and horizontal alignments from
     * the given string.<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.rchw(3, 1, 1, 2, "bottom, left");
     * CC.rchw(3, 1, 1, 2, "b, l");
     * CC.rchw(3, 1, 3, 7, "fill, center");
     * CC.rchw(3, 1, 3, 7, "f, c");
     * </pre>
     *
     * @param row the new row index
     * @param col the new column index
     * @param rowSpan the row span or grid height
     * @param colSpan the column span or grid width
     * @param encodedAlignments describes the vertical and horizontal alignments
     * @return this
     * @throws IllegalArgumentException if an alignment orientation is invalid
     */
    public static CellConstraints rchw(int row, int col, int rowSpan, int colSpan,
            String encodedAlignments) {
        return new CellConstraints().rchw(row, col, rowSpan, colSpan, encodedAlignments);
    }

    /**
     * Sets the row, column, height, and width; sets the vertical and horizontal alignment using the
     * specified alignment objects.<p>
     *
     * <strong>Examples:</strong><pre>
     * CC.rchw(3, 1, 1, 2, CellConstraints.BOTTOM, CellConstraints.LEFT);
     * CC.rchw(3, 1, 3, 7, CellConstraints.FILL,   CellConstraints.CENTER);
     * </pre>
     *
     * @param row the new row index
     * @param col the new column index
     * @param rowSpan the row span or grid height
     * @param colSpan the column span or grid width
     * @param rowAlign vertical component alignment
     * @param colAlign horizontal component alignment
     * @return this
     *
     * @throws IllegalArgumentException if an alignment orientation is invalid
     */
    public static CellConstraints rchw(int row, int col, int rowSpan, int colSpan,
            Alignment rowAlign, Alignment colAlign) {
        return xywh(col, row, colSpan, rowSpan, colAlign, rowAlign);
    }

}
