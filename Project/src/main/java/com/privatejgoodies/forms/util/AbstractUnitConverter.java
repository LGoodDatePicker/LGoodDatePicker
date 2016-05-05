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
package com.privatejgoodies.forms.util;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Toolkit;

import com.privatejgoodies.common.bean.Bean;

/**
 * An abstract implementation of the {@link UnitConverter} interface that minimizes the effort
 * required to convert font-dependent sizes to pixels.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.8 $
 *
 * @see DefaultUnitConverter
 * @see com.privatejgoodies.forms.layout.Size
 * @see com.privatejgoodies.forms.layout.Sizes
 */
public abstract class AbstractUnitConverter extends Bean implements UnitConverter {

    private static final int DTP_RESOLUTION = 72;

    // Unit Converter Implementation *********************************************
    /**
     * Converts Inches and returns pixels using the specified resolution.
     *
     * @param in the Inches
     * @param component the component that provides the graphics object
     * @return the given Inches as pixels
     */
    @Override
    public int inchAsPixel(double in, Component component) {
        return inchAsPixel(in, getScreenResolution(component));
    }

    /**
     * Converts Millimeters and returns pixels using the resolution of the given component's
     * graphics object.
     *
     * @param mm Millimeters
     * @param component the component that provides the graphics object
     * @return the given Millimeters as pixels
     */
    @Override
    public int millimeterAsPixel(double mm, Component component) {
        return millimeterAsPixel(mm, getScreenResolution(component));
    }

    /**
     * Converts Centimeters and returns pixels using the resolution of the given component's
     * graphics object.
     *
     * @param cm Centimeters
     * @param component the component that provides the graphics object
     * @return the given Centimeters as pixels
     */
    @Override
    public int centimeterAsPixel(double cm, Component component) {
        return centimeterAsPixel(cm, getScreenResolution(component));
    }

    /**
     * Converts DTP Points and returns pixels using the resolution of the given component's graphics
     * object.
     *
     * @param pt DTP Points
     * @param component the component that provides the graphics object
     * @return the given Points as pixels
     */
    @Override
    public int pointAsPixel(int pt, Component component) {
        return pointAsPixel(pt, getScreenResolution(component));
    }

    /**
     * Converts horizontal dialog units and returns pixels. Honors the resolution, dialog font size,
     * platform, and l&amp;f.
     *
     * @param dluX the horizontal dialog units
     * @param c a Component that provides the font and graphics
     * @return the given horizontal dialog units as pixels
     */
    @Override
    public int dialogUnitXAsPixel(int dluX, Component c) {
        return dialogUnitXAsPixel(dluX, getDialogBaseUnitsX(c));
    }

    /**
     * Converts vertical dialog units and returns pixels. Honors the resolution, dialog font size,
     * platform, and l&amp;f.
     *
     * @param dluY the vertical dialog units
     * @param c a Component that provides the font and graphics
     * @return the given vertical dialog units as pixels
     */
    @Override
    public int dialogUnitYAsPixel(int dluY, Component c) {
        return dialogUnitYAsPixel(dluY, getDialogBaseUnitsY(c));
    }

    // Abstract Behavior *****************************************************
    /**
     * Gets and returns the horizontal dialog base units. Implementations are encouraged to cache
     * previously computed dialog base units.
     *
     * @param component a Component that provides the font and graphics
     * @return the horizontal dialog base units
     */
    protected abstract double getDialogBaseUnitsX(Component component);

    /**
     * Gets and returns the vertical dialog base units. Implementations are encouraged to cache
     * previously computed dialog base units.
     *
     * @param component a Component that provides the font and graphics
     * @return the vertical dialog base units
     */
    protected abstract double getDialogBaseUnitsY(Component component);

    // Convenience Methods ***************************************************
    /**
     * Converts Inches and returns pixels using the specified resolution.
     *
     * @param in the Inches
     * @param dpi the resolution
     * @return the given Inches as pixels
     */
    protected static final int inchAsPixel(double in, int dpi) {
        return (int) Math.round(dpi * in);
    }

    /**
     * Converts Millimeters and returns pixels using the specified resolution.
     *
     * @param mm Millimeters
     * @param dpi the resolution
     * @return the given Millimeters as pixels
     */
    protected static final int millimeterAsPixel(double mm, int dpi) {
        return (int) Math.round(dpi * mm * 10 / 254);
    }

    /**
     * Converts Centimeters and returns pixels using the specified resolution.
     *
     * @param cm Centimeters
     * @param dpi the resolution
     * @return the given Centimeters as pixels
     */
    protected static final int centimeterAsPixel(double cm, int dpi) {
        return (int) Math.round(dpi * cm * 100 / 254);
    }

    /**
     * Converts DTP Points and returns pixels using the specified resolution.
     *
     * @param pt DTP Points
     * @param dpi the resolution in dpi
     * @return the given Points as pixels
     */
    protected static final int pointAsPixel(int pt, int dpi) {
        return Math.round(dpi * pt / DTP_RESOLUTION);
    }

    /**
     * Converts horizontal dialog units and returns pixels.
     *
     * @param dluX the horizontal dialog units
     * @param dialogBaseUnitsX the horizontal dialog base units
     * @return the given dialog base units as pixels
     */
    protected int dialogUnitXAsPixel(int dluX, double dialogBaseUnitsX) {
        return (int) Math.round(dluX * dialogBaseUnitsX / 4);
    }

    /**
     * Converts vertical dialog units and returns pixels.
     *
     * @param dluY the vertical dialog units
     * @param dialogBaseUnitsY the vertical dialog base units
     * @return the given dialog base units as pixels
     */
    protected int dialogUnitYAsPixel(int dluY, double dialogBaseUnitsY) {
        return (int) Math.round(dluY * dialogBaseUnitsY / 8);
    }

    // Helper Code ************************************************************
    /**
     * Computes and returns the average character width of the specified test string using the given
     * FontMetrics. The test string shall represent an "average" text.
     *
     * @param metrics used to compute the test string's width
     * @param testString the string that shall represent an "average" text
     * @return the test string's average character width.
     */
    protected double computeAverageCharWidth(
            FontMetrics metrics,
            String testString) {
        int width = metrics.stringWidth(testString);
        double average = (double) width / testString.length();
        //System.out.println("Average width of '" + testString + "'=" + average);
        return average;
    }

    /**
     * Returns the components screen resolution or the default screen resolution if the component is
     * null or has no toolkit assigned yet.
     *
     * @param c the component to ask for a toolkit
     * @return the component's screen resolution
     */
    protected int getScreenResolution(Component c) {
        if (c == null) {
            return getDefaultScreenResolution();
        }

        Toolkit toolkit = c.getToolkit();
        return toolkit != null
                ? toolkit.getScreenResolution()
                : getDefaultScreenResolution();
    }

    private static int defaultScreenResolution = -1;

    /**
     * Computes and returns the default resolution.
     *
     * @return the default screen resolution
     */
    protected int getDefaultScreenResolution() {
        if (defaultScreenResolution == -1) {
            defaultScreenResolution
                    = Toolkit.getDefaultToolkit().getScreenResolution();
        }
        return defaultScreenResolution;
    }

}
