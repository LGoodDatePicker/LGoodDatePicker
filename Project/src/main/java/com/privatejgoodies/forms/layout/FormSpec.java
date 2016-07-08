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

import static com.privatejgoodies.common.base.Preconditions.checkArgument;
import static com.privatejgoodies.common.base.Preconditions.checkNotBlank;
import static com.privatejgoodies.common.base.Preconditions.checkNotNull;

import java.awt.Container;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * An abstract class that specifies columns and rows in FormLayout by their default alignment, start
 * size and resizing behavior. API users will use the subclasses {@link ColumnSpec} and
 * {@link RowSpec}
 * .<p>
 *
 * Also implements the parser for encoded column and row specifications and provides parser
 * convenience behavior for its subclasses ColumnSpec and RowSpec.<p>
 *
 * TODO: Consider extracting the parser role to a separate class.
 *
 * @author	Karsten Lentzsch
 * @version $Revision: 1.25 $
 *
 * @see ColumnSpec
 * @see RowSpec
 * @see FormLayout
 * @see CellConstraints
 */
public abstract class FormSpec implements Serializable {

    // Horizontal and Vertical Default Alignments ***************************
    /**
     * By default put components in the left.
     */
    static final DefaultAlignment LEFT_ALIGN = new DefaultAlignment("left");

    /**
     * By default put components in the right.
     */
    static final DefaultAlignment RIGHT_ALIGN = new DefaultAlignment("right");

    /**
     * By default put the components in the top.
     */
    static final DefaultAlignment TOP_ALIGN = new DefaultAlignment("top");

    /**
     * By default put the components in the bottom.
     */
    static final DefaultAlignment BOTTOM_ALIGN = new DefaultAlignment("bottom");

    /**
     * By default put the components in the center.
     */
    static final DefaultAlignment CENTER_ALIGN = new DefaultAlignment("center");

    /**
     * By default fill the column or row.
     */
    static final DefaultAlignment FILL_ALIGN = new DefaultAlignment("fill");

    /**
     * An array of all enumeration values used to canonicalize deserialized default alignments.
     */
    private static final DefaultAlignment[] VALUES
            = {LEFT_ALIGN, RIGHT_ALIGN, TOP_ALIGN, BOTTOM_ALIGN, CENTER_ALIGN, FILL_ALIGN};

    // Resizing Weights *****************************************************
    /**
     * Gives a column or row a fixed size.
     */
    public static final double NO_GROW = 0.0d;

    /**
     * The default resize weight.
     */
    public static final double DEFAULT_GROW = 1.0d;

    // Parser Patterns ******************************************************
    private static final Pattern TOKEN_SEPARATOR_PATTERN
            = Pattern.compile(":");

    private static final Pattern BOUNDS_SEPARATOR_PATTERN
            = Pattern.compile("\\s*,\\s*");

    // Fields ***************************************************************
    /**
     * Holds the default alignment that will be used if a cell does not override this default.
     */
    private DefaultAlignment defaultAlignment;

    /**
     * Holds the size that describes how to size this column or row.
     */
    private Size size;

    /**
     * Holds the resize weight; is 0 if not used.
     */
    private double resizeWeight;

    // Instance Creation ****************************************************
    /**
     * Constructs a {@code FormSpec} for the given default alignment, size, and resize weight. The
     * resize weight must be a non-negative double; you can use {@code NONE} as a convenience value
     * for no resize.
     *
     * @param defaultAlignment the spec's default alignment
     * @param size a constant, component or bounded size
     * @param resizeWeight the spec resize weight
     *
     * @throws NullPointerException if the {@code size} is {@code null}
     * @throws IllegalArgumentException if the {@code resizeWeight} is negative
     */
    protected FormSpec(DefaultAlignment defaultAlignment,
            Size size,
            double resizeWeight) {
        checkNotNull(size, "The size must not be null.");
        checkArgument(resizeWeight >= 0, "The resize weight must be non-negative.");
        this.defaultAlignment = defaultAlignment;
        this.size = size;
        this.resizeWeight = resizeWeight;
    }

    /**
     * Constructs a FormSpec from the specified encoded description. The description will be parsed
     * to set initial values.
     *
     * @param defaultAlignment the default alignment
     * @param encodedDescription	the encoded description
     */
    protected FormSpec(DefaultAlignment defaultAlignment, String encodedDescription) {
        this(defaultAlignment, Sizes.DEFAULT, NO_GROW);
        parseAndInitValues(encodedDescription.toLowerCase(Locale.ENGLISH));
    }

    // Public API ***********************************************************
    /**
     * Returns the default alignment.
     *
     * @return the default alignment
     */
    public final DefaultAlignment getDefaultAlignment() {
        return defaultAlignment;
    }

    /**
     * Returns the size.
     *
     * @return the size
     */
    public final Size getSize() {
        return size;
    }

    /**
     * Returns the current resize weight.
     *
     * @return the resize weight.
     */
    public final double getResizeWeight() {
        return resizeWeight;
    }

    /**
     * Checks and answers whether this spec can grow or not. That is the case if and only if the
     * resize weight is != {@code NO_GROW}.
     *
     * @return true if it can grow, false if it can't grow
     */
    final boolean canGrow() {
        return getResizeWeight() != NO_GROW;
    }

    // Abstract Behavior ****************************************************
    /**
     * Returns if this is a horizontal specification (vs. vertical). Used to distinct between
     * horizontal and vertical dialog units, which have different conversion factors.
     *
     * @return true for horizontal, false for vertical
     */
    abstract boolean isHorizontal();

    // Setting Values *********************************************************
    void setDefaultAlignment(DefaultAlignment defaultAlignment) {
        this.defaultAlignment = defaultAlignment;
    }

    void setSize(Size size) {
        this.size = size;
    }

    void setResizeWeight(double resizeWeight) {
        this.resizeWeight = resizeWeight;
    }

    // Parsing **************************************************************
    /**
     * Parses an encoded form specification and initializes all required fields. The encoded
     * description must be in lower case.
     *
     * @param encodedDescription the FormSpec in an encoded format
     *
     * @throws NullPointerException if {@code encodedDescription} is {@code null}
     * @throws IllegalArgumentException if {@code encodedDescription} is empty, whitespace, has no
     * size, or is otherwise invalid
     */
    private void parseAndInitValues(String encodedDescription) {
        checkNotBlank(encodedDescription,
                "The encoded form specification must not be null, empty or whitespace.");
        String[] token = TOKEN_SEPARATOR_PATTERN.split(encodedDescription);
        checkArgument(token.length > 0, "The form spec must not be empty.");
        int nextIndex = 0;
        String next = token[nextIndex++];

        // Check if the first token is an orientation.
        DefaultAlignment alignment = DefaultAlignment.valueOf(next, isHorizontal());
        if (alignment != null) {
            setDefaultAlignment(alignment);
            checkArgument(token.length > 1, "The form spec must provide a size.");
            next = token[nextIndex++];
        }
        setSize(parseSize(next));
        if (nextIndex < token.length) {
            setResizeWeight(parseResizeWeight(token[nextIndex]));
        }
    }

    /**
     * Parses an encoded size spec and returns the size.
     *
     * @param token a token that represents a size, either bounded or plain
     * @return the decoded Size
     */
    private Size parseSize(String token) {
        if (token.startsWith("[") && token.endsWith("]")) {
            return parseBoundedSize(token);
        }
        if (token.startsWith("max(") && token.endsWith(")")) {
            return parseOldBoundedSize(token, false);
        }
        if (token.startsWith("min(") && token.endsWith(")")) {
            return parseOldBoundedSize(token, true);
        }
        return parseAtomicSize(token);
    }

    private Size parseBoundedSize(String token) {
        String content = token.substring(1, token.length() - 1);
        String[] subtoken = BOUNDS_SEPARATOR_PATTERN.split(content);
        Size basis = null;
        Size lower = null;
        Size upper = null;
        if (subtoken.length == 2) {
            Size size1 = parseAtomicSize(subtoken[0]);
            Size size2 = parseAtomicSize(subtoken[1]);
            if (isConstant(size1)) {
                if (isConstant(size2)) {
                    lower = size1;
                    basis = size2;
                    upper = size2;
                } else {
                    lower = size1;
                    basis = size2;
                }
            } else {
                basis = size1;
                upper = size2;
            }
        } else if (subtoken.length == 3) {
            lower = parseAtomicSize(subtoken[0]);
            basis = parseAtomicSize(subtoken[1]);
            upper = parseAtomicSize(subtoken[2]);
        }
        if ((lower == null || isConstant(lower))
                && (upper == null || isConstant(upper))) {
            return new BoundedSize(basis, lower, upper);
        }
        throw new IllegalArgumentException(
                "Illegal bounded size '" + token + "'. Must be one of:"
                + "\n[<constant size>,<logical size>]                 // lower bound"
                + "\n[<logical size>,<constant size>]                 // upper bound"
                + "\n[<constant size>,<logical size>,<constant size>] // lower and upper bound."
                + "\nExamples:"
                + "\n[50dlu,pref]                                     // lower bound"
                + "\n[pref,200dlu]                                    // upper bound"
                + "\n[50dlu,pref,200dlu]                              // lower and upper bound."
        );
    }

    /**
     * Parses an encoded compound size and sets the size fields. The compound size has format:
     * max(&lt;atomic size&gt;;&lt;atomic size2&gt;) | min(&lt;atomic size1&gt;;&lt;atomic
     * size2&gt;) One of the two atomic sizes must be a logical size, the other must be a size
     * constant.
     *
     * @param token a token for a bounded size, e.g. "max(50dlu; pref)"
     * @param setMax if true we set a maximum size, otherwise a minimum size
     * @return a Size that represents the parse result
     */
    private Size parseOldBoundedSize(String token, boolean setMax) {
        int semicolonIndex = token.indexOf(';');
        String sizeToken1 = token.substring(4, semicolonIndex);
        String sizeToken2 = token.substring(semicolonIndex + 1, token.length() - 1);

        Size size1 = parseAtomicSize(sizeToken1);
        Size size2 = parseAtomicSize(sizeToken2);

        // Check valid combinations and set min or max.
        if (isConstant(size1)) {
            if (size2 instanceof Sizes.ComponentSize) {
                return new BoundedSize(size2, setMax ? null : size1,
                        setMax ? size1 : null);
            }
            throw new IllegalArgumentException(
                    "Bounded sizes must not be both constants.");
        }
        if (isConstant(size2)) {
            return new BoundedSize(size1, setMax ? null : size2,
                    setMax ? size2 : null);
        }
        throw new IllegalArgumentException(
                "Bounded sizes must not be both logical.");
    }

    /**
     * Decodes and returns an atomic size that is either a constant size or a component size.
     *
     * @param token	the encoded size
     * @return the decoded size either a constant or component size
     */
    private Size parseAtomicSize(String token) {
        String trimmedToken = token.trim();
        if (trimmedToken.startsWith("'") && trimmedToken.endsWith("'")) {
            int length = trimmedToken.length();
            if (length < 2) {
                throw new IllegalArgumentException("Missing closing \"'\" for prototype.");
            }
            return new PrototypeSize(trimmedToken.substring(1, length - 1));
        }
        Sizes.ComponentSize componentSize = Sizes.ComponentSize.valueOf(trimmedToken);
        if (componentSize != null) {
            return componentSize;
        }
        return ConstantSize.valueOf(trimmedToken, isHorizontal());
    }

    /**
     * Decodes an encoded resize mode and resize weight and answers the resize weight.
     *
     * @param token	the encoded resize weight
     * @return the decoded resize weight
     * @throws IllegalArgumentException if the string description is an invalid string
     * representation
     */
    private static double parseResizeWeight(String token) {
        if (token.equals("g") || token.equals("grow")) {
            return DEFAULT_GROW;
        }
        if (token.equals("n") || token.equals("nogrow") || token.equals("none")) {
            return NO_GROW;
        }
        // Must have format: grow(<double>)
        if ((token.startsWith("grow(") || token.startsWith("g("))
                && token.endsWith(")")) {
            int leftParen = token.indexOf('(');
            int rightParen = token.indexOf(')');
            String substring = token.substring(leftParen + 1, rightParen);
            return Double.parseDouble(substring);
        }
        throw new IllegalArgumentException(
                "The resize argument '" + token + "' is invalid. "
                + " Must be one of: grow, g, none, n, grow(<double>), g(<double>)");
    }

    private static boolean isConstant(Size aSize) {
        return aSize instanceof ConstantSize
                || aSize instanceof PrototypeSize;
    }

    // Misc *****************************************************************
    /**
     * Returns a string representation of this form specification. The string representation
     * consists of three elements separated by a colon (<tt>":"</tt>), first the alignment, second
     * the size, and third the resize spec.<p>
     *
     * This method does <em>not</em> return an encoded version of this object; the contrary is the
     * case. Many instances will return a string that cannot be parsed.<p>
     *
     * <strong>Note:</strong> The string representation may change at any time. For parsing use
     * {@link #encode()} instead.
     *
     * @return	a string representation of the form specification.
     */
    @Override
    public final String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(defaultAlignment);

        buffer.append(":");
        buffer.append(size.toString());
        buffer.append(':');
        if (resizeWeight == NO_GROW) {
            buffer.append("noGrow");
        } else if (resizeWeight == DEFAULT_GROW) {
            buffer.append("grow");
        } else {
            buffer.append("grow(");
            buffer.append(resizeWeight);
            buffer.append(')');
        }
        return buffer.toString();
    }

    /**
     * Returns a string representation of this form specification. The string representation
     * consists of three elements separated by a colon (<tt>":"</tt>), first the alignment, second
     * the size, and third the resize spec.<p>
     *
     * This method does <em>not</em> return an encoded version of this object; the contrary is the
     * case. Many instances will return a string that cannot be parsed.<p>
     *
     * <strong>Note:</strong> The string representation may change at any time. For parsing use
     * {@link #encode()} instead.
     *
     * @return a string representation of the form specification.
     */
    public final String toShortString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(defaultAlignment.abbreviation());

        buffer.append(":");
        buffer.append(size.toString());
        buffer.append(':');
        if (resizeWeight == NO_GROW) {
            buffer.append("n");
        } else if (resizeWeight == DEFAULT_GROW) {
            buffer.append("g");
        } else {
            buffer.append("g(");
            buffer.append(resizeWeight);
            buffer.append(')');
        }
        return buffer.toString();
    }

    /**
     * Returns a short and parseable string representation of this form specification. The string
     * will omit the alignment and resize specifications if these are the default values.<p>
     *
     * @return a string representation of the form specification.
     *
     * @see #toShortString() for a more verbose string representation
     *
     * @since 1.2
     */
    public final String encode() {
        StringBuffer buffer = new StringBuffer();
        DefaultAlignment alignmentDefault = isHorizontal()
                ? ColumnSpec.DEFAULT
                : RowSpec.DEFAULT;
        if (!alignmentDefault.equals(defaultAlignment)) {
            buffer.append(defaultAlignment.abbreviation());
            buffer.append(":");
        }
        buffer.append(size.encode());
        if (resizeWeight == NO_GROW) {
            // Omit the resize part
        } else if (resizeWeight == DEFAULT_GROW) {
            buffer.append(':');
            buffer.append("g");
        } else {
            buffer.append(':');
            buffer.append("g(");
            buffer.append(resizeWeight);
            buffer.append(')');
        }
        return buffer.toString();
    }

    // Helper Code **********************************************************
    /**
     * Computes the maximum size for the given list of components, using this form spec and the
     * specified measure.<p>
     *
     * Invoked by FormLayout to determine the size of one of my elements
     *
     * @param container the layout container
     * @param components the list of components to measure
     * @param minMeasure the measure used to determine the minimum size
     * @param prefMeasure the measure used to determine the preferred size
     * @param defaultMeasure the measure used to determine the default size
     * @return the maximum size in pixels
     */
    final int maximumSize(Container container,
            List components,
            FormLayout.Measure minMeasure,
            FormLayout.Measure prefMeasure,
            FormLayout.Measure defaultMeasure) {
        return size.maximumSize(container,
                components,
                minMeasure,
                prefMeasure,
                defaultMeasure);
    }

    /**
     * An ordinal-based serializable typesafe enumeration for the column and row default alignment
     * types.
     */
    public static final class DefaultAlignment implements Serializable {

        private final transient String name;

        private DefaultAlignment(String name) {
            this.name = name;
        }

        /**
         * Returns a DefaultAlignment that corresponds to the specified string, null if no such
         * alignment exists.
         *
         * @param str	the encoded alignment
         * @param isHorizontal indicates the values orientation
         * @return the corresponding DefaultAlignment or null
         */
        private static DefaultAlignment valueOf(String str, boolean isHorizontal) {
            if (str.equals("f") || str.equals("fill")) {
                return FILL_ALIGN;
            } else if (str.equals("c") || str.equals("center")) {
                return CENTER_ALIGN;
            } else if (isHorizontal) {
                if (str.equals("r") || str.equals("right")) {
                    return RIGHT_ALIGN;
                } else if (str.equals("l") || str.equals("left")) {
                    return LEFT_ALIGN;
                } else {
                    return null;
                }
            } else if (str.equals("t") || str.equals("top")) {
                return TOP_ALIGN;
            } else if (str.equals("b") || str.equals("bottom")) {
                return BOTTOM_ALIGN;
            } else {
                return null;
            }
        }

        /**
         * Returns this Alignment's name.
         *
         * @return this alignment's name.
         */
        @Override
        public String toString() {
            return name;
        }

        /**
         * Returns the first character of this Alignment's name. Used to identify it in short format
         * strings.
         *
         * @return the name's first character.
         */
        public char abbreviation() {
            return name.charAt(0);
        }

        // Serialization *****************************************************
        private static int nextOrdinal = 0;

        private final int ordinal = nextOrdinal++;

        private Object readResolve() {
            return VALUES[ordinal];  // Canonicalize
        }

    }

}
