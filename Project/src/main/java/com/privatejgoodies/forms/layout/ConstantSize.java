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

import java.awt.Component;
import java.awt.Container;
import java.io.Serializable;
import java.util.List;

/**
 * An implementation of the {@link Size} interface that represents constant sizes described by a
 * value and unit, for example: 10&nbsp;pixel, 15&nbsp;point or 4&nbsp;dialog units. You can get
 * instances of {@code ConstantSize} using the factory methods and constants in the {@link Sizes}
 * class. Logical constant sizes that vary with the current layout style are delivered by the
 * {@link com.privatejgoodies.forms.util.LayoutStyle} class.<p>
 *
 * This class supports different size units:
 * <table>
 * <tr><td><b>Unit</b>&nbsp;
 * </td><td>&nbsp;<b>Abbreviation</b>&nbsp;</td><td>&nbsp;
 * <b>Size</b></td></tr>
 * <tr><td>Millimeter</td><td>mm</td><td>0.1 cm</td></tr>
 * <tr><td>Centimeter</td><td>cm</td><td>10.0 mm</td></tr>
 * <tr><td>Inch</td><td>in</td><td>25.4 mm</td></tr>
 * <tr><td>DTP Point</td><td>pt</td><td>1/72 in</td></tr>
 * <tr><td>Pixel</td><td>px</td><td>1/(resolution in dpi) in</td></tr>
 * <tr><td>Dialog Unit</td><td>dlu</td><td>honors l&amp;f, resolution, and dialog font
 * size</td></tr>
 * </table><p>
 *
 * <strong>Examples:</strong><pre>
 * Sizes.ZERO;
 * Sizes.DLUX9;
 * Sizes.dluX(42);
 * Sizes.pixel(99);
 * </pre>
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.21 $
 *
 * @see	Size
 * @see	Sizes
 */
public final class ConstantSize implements Size, Serializable {

    // Public Units *********************************************************
    public static final Unit PIXEL = new Unit("Pixel", "px", null, true);
    public static final Unit POINT = new Unit("Point", "pt", null, true);
    public static final Unit DIALOG_UNITS_X = new Unit("Dialog units X", "dluX", "dlu", true);
    public static final Unit DIALOG_UNITS_Y = new Unit("Dialog units Y", "dluY", "dlu", true);
    public static final Unit MILLIMETER = new Unit("Millimeter", "mm", null, false);
    public static final Unit CENTIMETER = new Unit("Centimeter", "cm", null, false);
    public static final Unit INCH = new Unit("Inch", "in", null, false);

    public static final Unit PX = PIXEL;
    public static final Unit PT = POINT;
    public static final Unit DLUX = DIALOG_UNITS_X;
    public static final Unit DLUY = DIALOG_UNITS_Y;
    public static final Unit MM = MILLIMETER;
    public static final Unit CM = CENTIMETER;
    public static final Unit IN = INCH;

    /**
     * An array of all enumeration values used to canonicalize deserialized units.
     */
    private static final Unit[] VALUES
            = {PIXEL, POINT, DIALOG_UNITS_X, DIALOG_UNITS_Y, MILLIMETER, CENTIMETER, INCH};

    // Fields ***************************************************************
    private final double value;
    private final Unit unit;

    // Instance Creation ****************************************************
    /**
     * Constructs a ConstantSize for the given size and unit.
     *
     * @param value the size value interpreted in the given units
     * @param unit	the size's unit
     *
     * @since 1.1
     */
    public ConstantSize(int value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    /**
     * Constructs a ConstantSize for the given size and unit.
     *
     * @param value the size value interpreted in the given units
     * @param unit the size's unit
     *
     * @since 1.1
     */
    public ConstantSize(double value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    /**
     * Creates and returns a ConstantSize from the given encoded size and unit description.
     *
     * @param encodedValueAndUnit the size's value and unit as string, trimmed and in lower case
     * @param horizontal	true for horizontal, false for vertical
     * @return a constant size for the given encoding and unit description
     *
     * @throws IllegalArgumentException if the unit requires integer but the value is not an integer
     */
    static ConstantSize valueOf(String encodedValueAndUnit, boolean horizontal) {
        String[] split = ConstantSize.splitValueAndUnit(encodedValueAndUnit);
        String encodedValue = split[0];
        String encodedUnit = split[1];
        Unit unit = Unit.valueOf(encodedUnit, horizontal);
        double value = Double.parseDouble(encodedValue);
        if (unit.requiresIntegers) {
            checkArgument(value == (int) value,
                    "%s value %s must be an integer.", unit, encodedValue);
        }
        return new ConstantSize(value, unit);
    }

    /**
     * Creates and returns a ConstantSize for the specified size value in horizontal dialog units.
     *
     * @param value	size value in horizontal dialog units
     * @return the associated Size instance
     */
    static ConstantSize dluX(int value) {
        return new ConstantSize(value, DLUX);
    }

    /**
     * Creates and returns a ConstantSize for the specified size value in vertical dialog units.
     *
     * @param value size value in vertical dialog units
     * @return the associated Size instance
     */
    static ConstantSize dluY(int value) {
        return new ConstantSize(value, DLUY);
    }

    // Accessors ************************************************************
    /**
     * Returns this size's value.
     *
     * @return the size value
     *
     * @since 1.1
     */
    public double getValue() {
        return value;
    }

    /**
     * Returns this size's unit.
     *
     * @return the size unit
     *
     * @since 1.1
     */
    public Unit getUnit() {
        return unit;
    }

    // Accessing the Value **************************************************
    /**
     * Converts the size if necessary and returns the value in pixels.
     *
     * @param component the associated component
     * @return the size in pixels
     */
    public int getPixelSize(Component component) {
        if (unit == PIXEL) {
            return intValue();
        } else if (unit == POINT) {
            return Sizes.pointAsPixel(intValue(), component);
        } else if (unit == INCH) {
            return Sizes.inchAsPixel(value, component);
        } else if (unit == MILLIMETER) {
            return Sizes.millimeterAsPixel(value, component);
        } else if (unit == CENTIMETER) {
            return Sizes.centimeterAsPixel(value, component);
        } else if (unit == DIALOG_UNITS_X) {
            return Sizes.dialogUnitXAsPixel(intValue(), component);
        } else if (unit == DIALOG_UNITS_Y) {
            return Sizes.dialogUnitYAsPixel(intValue(), component);
        } else {
            throw new IllegalStateException("Invalid unit " + unit);
        }
    }

    // Implementing the Size Interface **************************************
    /**
     * Returns this size as pixel size. Neither requires the component list nor the specified
     * measures.<p>
     *
     * Invoked by {@link com.privatejgoodies.forms.layout.FormSpec} to determine the size of a
     * column or row.
     *
     * @param container the layout container
     * @param components the list of components used to compute the size
     * @param minMeasure the measure that determines the minimum sizes
     * @param prefMeasure the measure that determines the preferred sizes
     * @param defaultMeasure the measure that determines the default sizes
     * @return the computed maximum size in pixel
     */
    @Override
    public int maximumSize(Container container,
            List components,
            FormLayout.Measure minMeasure,
            FormLayout.Measure prefMeasure,
            FormLayout.Measure defaultMeasure) {
        return getPixelSize(container);
    }

    /**
     * Describes if this Size can be compressed, if container space gets scarce. Used by the
     * FormLayout size computations in {@code #compressedSizes} to check whether a column or row can
     * be compressed or not.<p>
     *
     * ConstantSizes are incompressible.
     *
     * @return {@code false}
     *
     * @since 1.1
     */
    @Override
    public boolean compressible() {
        return false;
    }

    // Overriding Object Behavior *******************************************
    /**
     * Indicates whether some other ConstantSize is "equal to" this one.
     *
     * @param o the Object with which to compare
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     *
     * @see java.lang.Object#hashCode()
     * @see java.util.Hashtable
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConstantSize)) {
            return false;
        }
        ConstantSize size = (ConstantSize) o;
        return this.value == size.value
                && this.unit == size.unit;
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of
     * hashtables such as those provided by {@code java.util.Hashtable}.
     *
     * @return a hash code value for this object.
     *
     * @see java.lang.Object#equals(java.lang.Object)
     * @see java.util.Hashtable
     */
    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode() + 37 * unit.hashCode();
    }

    /**
     * Returns a string representation of this size object.
     *
     * <strong>Note:</strong> This string representation may change at any time. It is intended for
     * debugging purposes. For parsing, use {@link #encode()} instead.
     *
     * @return a string representation of the constant size
     */
    @Override
    public String toString() {
        return value == intValue()
                ? Integer.toString(intValue()) + unit.abbreviation()
                : Double.toString(value) + unit.abbreviation();
    }

    /**
     * Returns a parseable string representation of this constant size.
     *
     * @return a String that can be parsed by the Forms parser
     *
     * @since 1.2
     */
    @Override
    public String encode() {
        return value == intValue()
                ? Integer.toString(intValue()) + unit.encode()
                : Double.toString(value) + unit.encode();
    }

    // Helper Code **********************************************************
    private int intValue() {
        return (int) Math.round(value);
    }

    /**
     * Splits a string that encodes size with unit into the size and unit substrings. Returns an
     * array of two strings.
     *
     * @param encodedValueAndUnit a strings that represents a size with unit, trimmed and in lower
     * case
     * @return the first element is size, the second is unit
     */
    private static String[] splitValueAndUnit(String encodedValueAndUnit) {
        String[] result = new String[2];
        int len = encodedValueAndUnit.length();
        int firstLetterIndex = len;
        while (firstLetterIndex > 0
                && Character.isLetter(encodedValueAndUnit.charAt(firstLetterIndex - 1))) {
            firstLetterIndex--;
        }
        result[0] = encodedValueAndUnit.substring(0, firstLetterIndex);
        result[1] = encodedValueAndUnit.substring(firstLetterIndex);
        return result;
    }

    // Helper Class *********************************************************
    /**
     * An ordinal-based serializable typesafe enumeration for units as used in instances of
     * {@link ConstantSize}.
     */
    public static final class Unit implements Serializable {

        private final transient String name;
        private final transient String abbreviation;
        private final transient String parseAbbreviation;
        final transient boolean requiresIntegers;

        private Unit(String name, String abbreviation, String parseAbbreviation, boolean requiresIntegers) {
            this.name = name;
            this.abbreviation = abbreviation;
            this.parseAbbreviation = parseAbbreviation;
            this.requiresIntegers = requiresIntegers;
        }

        /**
         * Returns a Unit that corresponds to the specified string.
         *
         * @param name the encoded unit, trimmed and in lower case
         * @param horizontal true for a horizontal unit, false for vertical
         * @return the corresponding Unit
         * @throws IllegalArgumentException if no Unit exists for the string
         */
        static Unit valueOf(String name, boolean horizontal) {
            if (name.length() == 0) {
                Unit defaultUnit = Sizes.getDefaultUnit();
                if (defaultUnit != null) {
                    return defaultUnit;
                }
                return horizontal ? DIALOG_UNITS_X : DIALOG_UNITS_Y;
            } else if (name.equals("px")) {
                return PIXEL;
            } else if (name.equals("dlu")) {
                return horizontal ? DIALOG_UNITS_X : DIALOG_UNITS_Y;
            } else if (name.equals("pt")) {
                return POINT;
            } else if (name.equals("in")) {
                return INCH;
            } else if (name.equals("mm")) {
                return MILLIMETER;
            } else if (name.equals("cm")) {
                return CENTIMETER;
            } else {
                throw new IllegalArgumentException(
                        "Invalid unit name '" + name + "'. Must be one of: "
                        + "px, dlu, pt, mm, cm, in");
            }
        }

        /**
         * Returns a string representation of this unit object.
         *
         * <strong>Note:</strong> This implementation may change at any time. It is intended for
         * debugging purposes. For parsing, use {@link #encode()} instead.
         *
         * @return a string representation of the constant size
         */
        @Override
        public String toString() {
            return name;
        }

        /**
         * Returns a parseable string representation of this unit.
         *
         * @return a String that can be parsed by the Forms parser
         *
         * @since 1.2
         */
        public String encode() {
            return parseAbbreviation != null
                    ? parseAbbreviation
                    : abbreviation;
        }

        /**
         * Returns the first character of this Unit's name. Used to identify it in short format
         * strings.
         *
         * @return the first character of this Unit's name.
         */
        public String abbreviation() {
            return abbreviation;
        }

        // Serialization *****************************************************
        private static int nextOrdinal = 0;

        private final int ordinal = nextOrdinal++;

        private Object readResolve() {
            return VALUES[ordinal];  // Canonicalize
        }

    }

}
