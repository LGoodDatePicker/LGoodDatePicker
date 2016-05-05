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
package com.privatejgoodies.forms.layout;

import com.privatejgoodies.forms.util.LayoutStyle;

/**
 * Provides frequently used column and row specifications.
 *
 * @author	Karsten Lentzsch
 * @version $Revision: 1.17 $
 *
 * @see	com.privatejgoodies.forms.layout.FormLayout
 * @see	ColumnSpec
 *
 * @since 1.6 This class was the FormFactory before.
 */
public final class FormSpecs {

    private FormSpecs() {
        // Suppresses default constructor, prevents instantiation.
    }

    // Frequently used Column Specifications ********************************
    /**
     * An unmodifiable {@code ColumnSpec} that determines its width by computing the maximum of all
     * column component minimum widths.
     *
     * @see #PREF_COLSPEC
     * @see #DEFAULT_COLSPEC
     */
    public static final ColumnSpec MIN_COLSPEC
            = new ColumnSpec(Sizes.MINIMUM);

    /**
     * An unmodifiable {@code ColumnSpec} that determines its width by computing the maximum of all
     * column component preferred widths.
     *
     * @see #MIN_COLSPEC
     * @see #DEFAULT_COLSPEC
     */
    public static final ColumnSpec PREF_COLSPEC
            = new ColumnSpec(Sizes.PREFERRED);

    /**
     * An unmodifiable {@code ColumnSpec} that determines its preferred width by computing the
     * maximum of all column component preferred widths and its minimum width by computing all
     * column component minimum widths.<p>
     *
     * Useful to let a column shrink from preferred width to minimum width if the container space
     * gets scarce.
     *
     * @see #MIN_COLSPEC
     * @see #PREF_COLSPEC
     */
    public static final ColumnSpec DEFAULT_COLSPEC
            = new ColumnSpec(Sizes.DEFAULT);

    /**
     * An unmodifiable {@code ColumnSpec} that has an initial width of 0 pixels and that grows.
     * Useful to describe <em>glue</em> columns that fill the space between other columns.
     *
     * @see #GLUE_ROWSPEC
     */
    public static final ColumnSpec GLUE_COLSPEC
            = new ColumnSpec(ColumnSpec.DEFAULT, Sizes.ZERO, ColumnSpec.DEFAULT_GROW);

    // Layout Style Dependent Column Specs ***********************************
    /**
     * Describes a logical horizontal gap between a label and an associated component. Useful for
     * builders that automatically fill a grid with labels and components.<p>
     *
     * <strong>Note:</strong> In a future version this constant will likely be moved to a class
     * {@code LogicalSize} or {@code StyledSize}.
     *
     * @since 1.0.3
     */
    public static final ColumnSpec LABEL_COMPONENT_GAP_COLSPEC
            = ColumnSpec.createGap(LayoutStyle.getCurrent().getLabelComponentPadX());

    /**
     * Describes a logical horizontal gap between two related components. For example the
     * <em>OK</em> and <em>Cancel</em> buttons are considered related.<p>
     *
     * <strong>Note:</strong> In a future version this constant will likely be moved to a class
     * {@code LogicalSize} or {@code StyledSize}.
     *
     * @see #UNRELATED_GAP_COLSPEC
     */
    public static final ColumnSpec RELATED_GAP_COLSPEC
            = ColumnSpec.createGap(LayoutStyle.getCurrent().getRelatedComponentsPadX());

    /**
     * Describes a logical horizontal gap between two unrelated components.<p>
     *
     * <strong>Note:</strong> In a future version this constant will likely be moved to a class
     * {@code LogicalSize} or {@code StyledSize}.
     *
     * @see #RELATED_GAP_COLSPEC
     */
    public static final ColumnSpec UNRELATED_GAP_COLSPEC
            = ColumnSpec.createGap(LayoutStyle.getCurrent().getUnrelatedComponentsPadX());

    /**
     * Describes a logical horizontal column for a fixed size button. This spec honors the current
     * layout style's default button minimum width.<p>
     *
     * <strong>Note:</strong> In a future version this constant will likely be moved to a class
     * {@code LogicalSize} or {@code StyledSize}.
     *
     * @see #GROWING_BUTTON_COLSPEC
     */
    public static final ColumnSpec BUTTON_COLSPEC
            = new ColumnSpec(Sizes.bounded(Sizes.PREFERRED,
                    LayoutStyle.getCurrent().getDefaultButtonWidth(),
                    null));

    /**
     * Describes a logical horizontal column for a growing button. This spec does <em>not</em> use
     * the layout style's default button minimum width.<p>
     *
     * <strong>Note:</strong> In a future version this constant will likely be moved to a class
     * {@code LogicalSize} or {@code StyledSize}.
     *
     * @see #BUTTON_COLSPEC
     */
    public static final ColumnSpec GROWING_BUTTON_COLSPEC
            = new ColumnSpec(ColumnSpec.DEFAULT,
                    BUTTON_COLSPEC.getSize(),
                    ColumnSpec.DEFAULT_GROW);

    // Frequently used Row Specifications ***********************************
    /**
     * An unmodifiable {@code RowSpec} that determines its height by computing the maximum of all
     * column component minimum heights.
     *
     * @see #PREF_ROWSPEC
     * @see #DEFAULT_ROWSPEC
     */
    public static final RowSpec MIN_ROWSPEC
            = new RowSpec(Sizes.MINIMUM);

    /**
     * An unmodifiable {@code RowSpec} that determines its height by computing the maximum of all
     * column component preferred heights.
     *
     * @see #MIN_ROWSPEC
     * @see #DEFAULT_ROWSPEC
     */
    public static final RowSpec PREF_ROWSPEC
            = new RowSpec(Sizes.PREFERRED);

    /**
     * An unmodifiable {@code RowSpec} that determines its preferred height by computing the maximum
     * of all column component preferred heights and its minimum height by computing all column
     * component minimum heights.<p>
     *
     * Useful to let a column shrink from preferred height to minimum height if the container space
     * gets scarce.
     *
     * @see #MIN_COLSPEC
     * @see #PREF_COLSPEC
     */
    public static final RowSpec DEFAULT_ROWSPEC
            = new RowSpec(Sizes.DEFAULT);

    /**
     * An unmodifiable {@code RowSpec} that has an initial height of 0 pixels and that grows. Useful
     * to describe <em>glue</em> rows that fill the space between other rows.
     *
     * @see #GLUE_COLSPEC
     */
    public static final RowSpec GLUE_ROWSPEC
            = new RowSpec(RowSpec.DEFAULT, Sizes.ZERO, RowSpec.DEFAULT_GROW);

    // Layout Style Dependent Row Specs *************************************
    /**
     * Describes a logical horizontal gap between a label and an associated component. Useful for
     * builders that automatically fill a grid with labels and components.<p>
     *
     * <strong>Note:</strong> In a future version this constant will likely be moved to a class
     * {@code LogicalSize} or {@code StyledSize}.
     *
     * @since 1.4
     */
    public static final RowSpec LABEL_COMPONENT_GAP_ROWSPEC
            = RowSpec.createGap(LayoutStyle.getCurrent().getLabelComponentPadY());

    /**
     * Describes a logical vertical gap between two related components. For example the <em>OK</em>
     * and <em>Cancel</em> buttons are considered related.<p>
     *
     * <strong>Note:</strong> In a future version this constant will likely be moved to a class
     * {@code LogicalSize} or {@code StyledSize}.
     *
     * @see #UNRELATED_GAP_ROWSPEC
     */
    public static final RowSpec RELATED_GAP_ROWSPEC
            = RowSpec.createGap(LayoutStyle.getCurrent().getRelatedComponentsPadY());

    /**
     * Describes a logical vertical gap between two unrelated components.<p>
     *
     * <strong>Note:</strong> In a future version this constant will likely be moved to a class
     * {@code LogicalSize} or {@code StyledSize}.
     *
     * @see #RELATED_GAP_ROWSPEC
     */
    public static final RowSpec UNRELATED_GAP_ROWSPEC
            = RowSpec.createGap(LayoutStyle.getCurrent().getUnrelatedComponentsPadY());

    /**
     * Describes a logical vertical narrow gap between two rows in the grid. Useful if the vertical
     * space is scarce or if an individual vertical gap shall be small than the default line gap.<p>
     *
     * <strong>Note:</strong> In a future version this constant will likely be moved to a class
     * {@code LogicalSize} or {@code StyledSize}.
     *
     * @see #LINE_GAP_ROWSPEC
     * @see #PARAGRAPH_GAP_ROWSPEC
     */
    public static final RowSpec NARROW_LINE_GAP_ROWSPEC
            = RowSpec.createGap(LayoutStyle.getCurrent().getNarrowLinePad());

    /**
     * Describes the logical vertical default gap between two rows in the grid. A little bit larger
     * than the narrow line gap.<p>
     *
     * <strong>Note:</strong> In a future version this constant will likely be moved to a class
     * {@code LogicalSize} or {@code StyledSize}.
     *
     * @see #NARROW_LINE_GAP_ROWSPEC
     * @see #PARAGRAPH_GAP_ROWSPEC
     */
    public static final RowSpec LINE_GAP_ROWSPEC
            = RowSpec.createGap(LayoutStyle.getCurrent().getLinePad());

    /**
     * Describes the logical vertical default gap between two paragraphs in the layout grid. This
     * gap is larger than the default line gap.<p>
     *
     * <strong>Note:</strong> In a future version this constant will likely be moved to a class
     * {@code LogicalSize} or {@code StyledSize}.
     *
     * @see #NARROW_LINE_GAP_ROWSPEC
     * @see #LINE_GAP_ROWSPEC
     */
    public static final RowSpec PARAGRAPH_GAP_ROWSPEC
            = RowSpec.createGap(LayoutStyle.getCurrent().getParagraphPad());

    /**
     * Describes a logical row for a fixed size button. This spec honors the current layout style's
     * default button minimum height.<p>
     *
     * <strong>Note:</strong> In a future version this constant will likely be moved to a class
     * {@code LogicalSize} or {@code StyledSize}.
     *
     * @since 1.2
     */
    public static final RowSpec BUTTON_ROWSPEC
            = new RowSpec(Sizes.bounded(Sizes.PREFERRED,
                    LayoutStyle.getCurrent().getDefaultButtonHeight(),
                    null));

}
