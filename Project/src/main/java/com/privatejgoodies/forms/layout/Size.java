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

import java.awt.Container;
import java.util.List;

/**
 * An interface that describes sizes as used by the {@link FormLayout}: component measuring sizes,
 * constant sizes with value and unit, and bounded sizes that provide lower and upper bounds for a
 * size.<p>
 *
 * You can find a motivation for the different {@code Size} types in the Forms whitepaper that is
 * part of the product documentation and that is available online too, see
 * <a href="http://www.jgoodies.com/articles/forms.pdf" >
 * http://www.jgoodies.com/articles/forms.pdf</a>.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.12 $
 *
 * @see	Sizes
 * @see	ConstantSize
 */
public interface Size {

    /**
     * Computes and returns this Size's maximum pixel size applied to the given list of components
     * using the specified measures.<p>
     *
     * Invoked by {@link com.privatejgoodies.forms.layout.FormSpec} to determine the size of a
     * column or row. This method is not intended to be called by API users, and it uses API
     * invisible parameter types.
     *
     * @param container the layout container
     * @param components the list of components used to compute the size
     * @param minMeasure the measure that determines the minimum sizes
     * @param prefMeasure the measure that determines the preferred sizes
     * @param defaultMeasure the measure that determines the default sizes
     * @return the maximum size in pixels for the given list of components
     */
    int maximumSize(Container container,
            List components,
            FormLayout.Measure minMeasure,
            FormLayout.Measure prefMeasure,
            FormLayout.Measure defaultMeasure);

    /**
     * Describes if this Size can be compressed, if container space gets scarce. Used by the
     * FormLayout size computations in {@code #compressedSizes} to check whether a column or row can
     * be compressed or not.<p>
     *
     * The ComponentSize <em>default</em> is compressible, as well as BoundedSizes that are based on
     * the <em>default</em> size.
     *
     * @return {@code true} for compressible Sizes
     *
     * @since 1.1
     */
    boolean compressible();

    /**
     * Returns a String respresentation of this Size object that can be parsed by the Forms
     * parser.<p>
     *
     * Implementors should return a non-verbose string.
     *
     * @return a parseable String representation of this object.
     *
     * @since 1.2
     */
    String encode();

}
