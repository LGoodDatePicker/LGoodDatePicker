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

import static com.privatejgoodies.common.base.Preconditions.checkNotBlank;
import static com.privatejgoodies.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Specifies rows in FormLayout by their default orientation, start size and resizing behavior.<p>
 *
 * <strong>Examples:</strong><br>
 * The following examples specify a centered row with a size of 14&nbsp;dlu that won't grow.
 * <pre>
 * new RowSpec(Sizes.dluX(14));
 * new RowSpec(RowSpec.CENTER, Sizes.dluX(14), 0.0);
 * new RowSpec(rowSpec.CENTER, Sizes.dluX(14), RowSpec.NO_GROW);
 * RowSpec.parse("14dlu");
 * RowSpec.parse("14dlu:0");
 * RowSpec.parse("center:14dlu:0");
 * </pre><p>
 *
 * The {@link com.privatejgoodies.forms.layout.FormSpecs} provides predefined frequently used
 * RowSpec instances.
 *
 * @author	Karsten Lentzsch
 * @version $Revision: 1.20 $
 *
 * @see com.privatejgoodies.forms.layout.FormSpecs
 */
public final class RowSpec extends FormSpec {

    // Vertical Orientations ************************************************
    /**
     * By default put the components in the top.
     */
    public static final DefaultAlignment TOP = FormSpec.TOP_ALIGN;

    /**
     * By default put the components in the center.
     */
    public static final DefaultAlignment CENTER = FormSpec.CENTER_ALIGN;

    /**
     * By default put the components in the bottom.
     */
    public static final DefaultAlignment BOTTOM = FormSpec.BOTTOM_ALIGN;

    /**
     * By default fill the component into the row.
     */
    public static final DefaultAlignment FILL = FormSpec.FILL_ALIGN;

    /**
     * Unless overridden the default alignment for a row is CENTER.
     */
    public static final DefaultAlignment DEFAULT = CENTER;

    // Cache ******************************************************************
    /**
     * Maps encoded row specifications to RowSpec instances.
     */
    private static final Map<String, RowSpec> CACHE
            = new HashMap<String, RowSpec>();

    // Instance Creation ****************************************************
    /**
     * Constructs a RowSpec from the given default orientation, size, and resize weight.<p>
     *
     * The resize weight must be a non-negative double; you can use {@code NO_FILL} as a convenience
     * value for no resize.
     *
     * @param defaultAlignment the row's default alignment
     * @param size constant size, component size, or bounded size
     * @param resizeWeight the row's non-negative resize weight
     * @throws IllegalArgumentException if the size is invalid or the resize weight is negative
     */
    public RowSpec(DefaultAlignment defaultAlignment,
            Size size,
            double resizeWeight) {
        super(defaultAlignment, size, resizeWeight);
    }

    /**
     * Constructs a RowSpec for the given size using the default alignment, and no resizing.
     *
     * @param size constant size, component size, or bounded size
     * @throws IllegalArgumentException if the size is invalid
     */
    public RowSpec(Size size) {
        super(DEFAULT, size, NO_GROW);
    }

    /**
     * Constructs a RowSpec from the specified encoded description. The description will be parsed
     * to set initial values.<p>
     *
     * Unlike the factory method {@link #decode(String)}, this constructor does not expand layout
     * variables, and it cannot vend cached objects.
     *
     * @param encodedDescription	the encoded description
     */
    private RowSpec(String encodedDescription) {
        super(DEFAULT, encodedDescription);
    }

    // Factory Methods ********************************************************
    /**
     * Creates and returns a {@link RowSpec} that represents a gap with the specified
     * {@link ConstantSize}.
     *
     * @param gapHeight specifies the gap height
     * @return a RowSpec that describes a vertical gap with the given height
     *
     * @throws NullPointerException if {@code gapHeight} is {@code null}
     *
     * @since 1.2
     */
    public static RowSpec createGap(ConstantSize gapHeight) {
        return new RowSpec(RowSpec.DEFAULT, gapHeight, FormSpec.NO_GROW);
    }

    /**
     * Parses the encoded row specification and returns a RowSpec object that represents the string.
     * Variables are expanded using the default LayoutMap.
     *
     * @param encodedRowSpec the encoded row specification
     *
     * @return a RowSpec instance for the given specification
     * @throws NullPointerException if {@code encodedRowSpec} is {@code null}
     *
     * @see #decode(String, LayoutMap)
     * @see LayoutMap#getRoot()
     *
     * @since 1.2
     */
    public static RowSpec decode(String encodedRowSpec) {
        return decode(encodedRowSpec, LayoutMap.getRoot());
    }

    /**
     * Parses the encoded row specifications and returns a RowSpec object that represents the
     * string. Variables are expanded using the given LayoutMap.
     *
     * @param encodedRowSpec the encoded column specification
     * @param layoutMap expands layout row variables
     *
     * @return a RowSpec instance for the given specification
     * @throws NullPointerException if {@code encodedRowSpec} or {@code layoutMap} is {@code null}
     *
     * @see #decodeSpecs(String, LayoutMap)
     *
     * @since 1.2
     */
    public static RowSpec decode(String encodedRowSpec, LayoutMap layoutMap) {
        checkNotBlank(encodedRowSpec,
                "The encoded row specification must not be null, empty or whitespace.");
        checkNotNull(layoutMap,
                "The LayoutMap must not be null.");
        String trimmed = encodedRowSpec.trim();
        String lower = trimmed.toLowerCase(Locale.ENGLISH);
        return decodeExpanded(layoutMap.expand(lower, false));
    }

    /**
     * Decodes an expanded, trimmed, lower case row spec. Called by the public RowSpec factory
     * methods. Looks up and returns the RowSpec object from the cache - if any, or constructs and
     * returns a new RowSpec instance.
     *
     * @param expandedTrimmedLowerCaseSpec the encoded column specification
     * @return a RowSpec for the given encoded row spec
     */
    static RowSpec decodeExpanded(String expandedTrimmedLowerCaseSpec) {
        RowSpec spec = CACHE.get(expandedTrimmedLowerCaseSpec);
        if (spec == null) {
            spec = new RowSpec(expandedTrimmedLowerCaseSpec);
            CACHE.put(expandedTrimmedLowerCaseSpec, spec);
        }
        return spec;
    }

    /**
     * Parses and splits encoded row specifications using the default {@link LayoutMap} and returns
     * an array of RowSpec objects.
     *
     * @param encodedRowSpecs comma separated encoded row specifications
     * @return an array of decoded row specifications
     * @throws NullPointerException if {@code encodedRowSpecs} is {@code null}
     *
     * @see #decodeSpecs(String, LayoutMap)
     * @see #decode(String)
     * @see LayoutMap#getRoot()
     */
    public static RowSpec[] decodeSpecs(String encodedRowSpecs) {
        return decodeSpecs(encodedRowSpecs, LayoutMap.getRoot());
    }

    /**
     * Parses and splits encoded row specifications using the given {@link LayoutMap} and returns an
     * array of RowSpec objects.
     *
     * @param encodedRowSpecs comma separated encoded row specifications
     * @param layoutMap expands layout row variables
     * @return an array of decoded row specifications
     *
     * @throws NullPointerException {@code encodedRowSpecs} or {@code layoutMap} is {@code null}
     *
     * @see RowSpec#RowSpec(String)
     *
     * @since 1.2
     */
    public static RowSpec[] decodeSpecs(String encodedRowSpecs, LayoutMap layoutMap) {
        return FormSpecParser.parseRowSpecs(encodedRowSpecs, layoutMap);
    }

    // Implementing Abstract Behavior ***************************************
    /**
     * Returns if this is a horizontal specification (vs. vertical). Used to distinct between
     * horizontal and vertical dialog units, which have different conversion factors.
     *
     * @return always {@code false} (for vertical)
     */
    @Override
    protected boolean isHorizontal() {
        return false;
    }

}
