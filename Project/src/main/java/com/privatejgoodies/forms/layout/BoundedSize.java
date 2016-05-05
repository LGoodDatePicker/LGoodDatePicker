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

import static com.privatejgoodies.common.base.Preconditions.checkNotNull;

import java.awt.Container;
import java.io.Serializable;
import java.util.List;

/**
 * Describes sizes that provide lower and upper bounds as used by the JGoodies FormLayout.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.21 $
 *
 * @see	Sizes
 * @see	ConstantSize
 */
public final class BoundedSize implements Size, Serializable {

    /**
     * Holds the base size.
     */
    private final Size basis;

    /**
     * Holds an optional lower bound.
     */
    private final Size lowerBound;

    /**
     * Holds an optional upper bound.
     */
    private final Size upperBound;

    // Instance Creation ****************************************************
    /**
     * Constructs a BoundedSize for the given basis using the specified lower and upper bounds.
     *
     * @param basis the base size
     * @param lowerBound the lower bound size
     * @param upperBound the upper bound size
     *
     * @throws NullPointerException if {@code basis}, {@code lowerBound}, or {@code upperBound} is
     * {@code null}
     *
     * @since 1.1
     */
    public BoundedSize(Size basis, Size lowerBound, Size upperBound) {
        this.basis = checkNotNull(basis, "The basis must not be null.");
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        if (lowerBound == null && upperBound == null) {
            throw new IllegalArgumentException(
                    "A bounded size must have a non-null lower or upper bound.");
        }
    }

    // Accessors ************************************************************
    /**
     * Returns the base size, which is not-{@code null}.
     *
     * @return the base size
     *
     * @since 1.1
     */
    public Size getBasis() {
        return basis;
    }

    /**
     * Returns the optional lower bound.
     *
     * @return the optional lower bound
     *
     * @since 1.1
     */
    public Size getLowerBound() {
        return lowerBound;
    }

    /**
     * Returns the optional upper bound.
     *
     * @return the optional upper bound
     *
     * @since 1.1
     */
    public Size getUpperBound() {
        return upperBound;
    }

    // Implementation of the Size Interface *********************************
    /**
     * Returns this size as pixel size. Neither requires the component list nor the specified
     * measures. Honors the lower and upper bound.<p>
     *
     * Invoked by {@code FormSpec} to determine the size of a column or row.
     *
     * @param container the layout container
     * @param components the list of components to measure
     * @param minMeasure the measure used to determine the minimum size
     * @param prefMeasure the measure used to determine the preferred size
     * @param defaultMeasure the measure used to determine the default size
     * @return the maximum size in pixels
     * @see FormSpec#maximumSize(Container, List, FormLayout.Measure, FormLayout.Measure,
     * FormLayout.Measure)
     */
    @Override
    public int maximumSize(Container container,
            List components,
            FormLayout.Measure minMeasure,
            FormLayout.Measure prefMeasure,
            FormLayout.Measure defaultMeasure) {
        int size = basis.maximumSize(container,
                components,
                minMeasure,
                prefMeasure,
                defaultMeasure);
        if (lowerBound != null) {
            size = Math.max(size, lowerBound.maximumSize(
                    container,
                    components,
                    minMeasure,
                    prefMeasure,
                    defaultMeasure));
        }
        if (upperBound != null) {
            size = Math.min(size, upperBound.maximumSize(
                    container,
                    components,
                    minMeasure,
                    prefMeasure,
                    defaultMeasure));
        }
        return size;
    }

    /**
     * Describes if this Size can be compressed, if container space gets scarce. Used by the
     * FormLayout size computations in {@code #compressedSizes} to check whether a column or row can
     * be compressed or not.<p>
     *
     * BoundedSizes are compressible if the base Size is compressible.
     *
     * @return {@code true} if and only if the basis is compressible
     *
     * @since 1.1
     */
    @Override
    public boolean compressible() {
        return getBasis().compressible();
    }

    // Overriding Object Behavior *******************************************
    /**
     * Indicates whether some other BoundedSize is "equal to" this one.
     *
     * @param object the object with which to compare
     * @return {@code true} if this object is the same as the object argument, {@code false}
     * otherwise.
     * @see Object#hashCode()
     * @see java.util.Hashtable
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BoundedSize)) {
            return false;
        }
        BoundedSize size = (BoundedSize) object;
        return basis.equals(size.basis)
                && (lowerBound == null && size.lowerBound == null
                || lowerBound != null && lowerBound.equals(size.lowerBound))
                && (upperBound == null && size.upperBound == null
                || upperBound != null && upperBound.equals(size.upperBound));
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of
     * hashtables such as those provided by {@code java.util.Hashtable}.
     *
     * @return a hash code value for this object.
     * @see Object#equals(Object)
     * @see java.util.Hashtable
     */
    @Override
    public int hashCode() {
        int hashValue = basis.hashCode();
        if (lowerBound != null) {
            hashValue = hashValue * 37 + lowerBound.hashCode();
        }
        if (upperBound != null) {
            hashValue = hashValue * 37 + upperBound.hashCode();
        }
        return hashValue;
    }

    /**
     * Returns a string representation of this size object.<p>
     *
     * <strong>Note:</strong> This string representation may change at any time. It is intended for
     * debugging purposes. For parsing, use {@link #encode()} instead.
     *
     * @return a string representation of this bounded size
     */
    @Override
    public String toString() {
        return encode();
    }

    /**
     * Returns a parseable string representation of this bounded size.
     *
     * @return a String that can be parsed by the Forms parser
     *
     * @since 1.2
     */
    @Override
    public String encode() {
        StringBuffer buffer = new StringBuffer("[");
        if (lowerBound != null) {
            buffer.append(lowerBound.encode());
            buffer.append(',');
        }
        buffer.append(basis.encode());
        if (upperBound != null) {
            buffer.append(',');
            buffer.append(upperBound.encode());
        }
        buffer.append(']');
        return buffer.toString();
    }

}
