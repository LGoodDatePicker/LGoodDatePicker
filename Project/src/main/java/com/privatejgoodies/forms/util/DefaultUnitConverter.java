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

import static com.privatejgoodies.common.base.Preconditions.checkNotBlank;
import static com.privatejgoodies.common.internal.Messages.MUST_NOT_BE_BLANK;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * This is the default implementation of the {@link UnitConverter} interface. It converts horizontal
 * and vertical dialog base units to pixels.<p>
 *
 * The horizontal base unit is equal to the average width, in pixels, of the characters in the
 * system font; the vertical base unit is equal to the height, in pixels, of the font. Each
 * horizontal base unit is equal to 4 horizontal dialog units; each vertical base unit is equal to 8
 * vertical dialog units.<p>
 *
 * The DefaultUnitConverter computes dialog base units using a default font and a test string for
 * the average character width. You can configure the font and the test string via the bound Bean
 * properties
 * <em>defaultDialogFont</em> and <em>averageCharacterWidthTestString</em>. See also Microsoft's
 * suggestion for a custom computation
 * <a href="http://support.microsoft.com/default.aspx?scid=kb;EN-US;125681">custom computation</a>.
 * More information how to use dialog units in screen design can be found in Microsoft's
 * <a href="http://msdn2.microsoft.com/en-us/library/ms997619">Design Specifications and
 * Guidelines</a>.<p>
 *
 * Since the Forms 1.1 this converter logs font information at the {@code CONFIG} level.
 *
 * @version $Revision: 1.23 $
 * @author Karsten Lentzsch
 * @see UnitConverter
 * @see com.privatejgoodies.forms.layout.Size
 * @see com.privatejgoodies.forms.layout.Sizes
 */
public final class DefaultUnitConverter extends AbstractUnitConverter {

    public static final String PROPERTY_AVERAGE_CHARACTER_WIDTH_TEST_STRING
            = "averageCharacterWidthTestString";

    public static final String PROPERTY_DEFAULT_DIALOG_FONT
            = "defaultDialogFont";

    /**
     * @since 1.6
     */
    public static final String OLD_AVERAGE_CHARACTER_TEST_STRING
            = "X";

    /**
     * @since 1.4
     */
    public static final String MODERN_AVERAGE_CHARACTER_TEST_STRING
            = "abcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * @since 1.4
     */
    public static final String BALANCED_AVERAGE_CHARACTER_TEST_STRING
            = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final Logger LOGGER
            = Logger.getLogger(DefaultUnitConverter.class.getName());

    /**
     * Holds the sole instance that will be lazily instantiated.
     */
    private static DefaultUnitConverter instance;

    /**
     * Holds the string that is used to compute the average character width. Since 1.6 the default
     * value is the balanced average character test string, where it was just &quot;X&quot; before.
     */
    private String averageCharWidthTestString
            = BALANCED_AVERAGE_CHARACTER_TEST_STRING;

    /**
     * Holds a custom font that is used to compute the global dialog base units. If not set, a
     * fallback font is is lazily created in method #getCachedDefaultDialogFont, which in turn looks
     * up a font in method #lookupDefaultDialogFont.
     */
    private Font defaultDialogFont;

    // Cached *****************************************************************
    /**
     * Holds the lazily created cached global dialog base units that are used if a component is not
     * (yet) available - for example in a Border.
     */
    private DialogBaseUnits cachedGlobalDialogBaseUnits = null;

    /**
     * Holds the horizontal dialog base units that are valid for the FontMetrics stored in
     * {@code cachedFontMetrics}.
     */
    private DialogBaseUnits cachedDialogBaseUnits = null;

    /**
     * Holds the FontMetrics used to compute the per-component dialog units. The latter are valid,
     * if a FontMetrics equals this stored metrics.
     */
    private FontMetrics cachedFontMetrics = null;

    /**
     * Holds a cached default dialog font that is used as fallback, if no default dialog font has
     * been set.
     *
     * @see #getDefaultDialogFont()
     * @see #setDefaultDialogFont(Font)
     */
    private Font cachedDefaultDialogFont = null;

    // Instance Creation and Access *******************************************
    /**
     * Constructs a DefaultUnitConverter and registers a listener that handles changes in the
     * look&amp;feel.
     */
    private DefaultUnitConverter() {
    }

    /**
     * Lazily instantiates and returns the sole instance.
     *
     * @return the lazily instantiated sole instance
     */
    public static DefaultUnitConverter getInstance() {
        if (instance == null) {
            instance = new DefaultUnitConverter();
        }
        return instance;
    }

    // Access to Bound Properties *********************************************
    /**
     * Returns the string used to compute the average character width. By default it is initialized
     * to {@link #BALANCED_AVERAGE_CHARACTER_TEST_STRING}.
     *
     * @return the test string used to compute the average character width
     */
    public String getAverageCharacterWidthTestString() {
        return averageCharWidthTestString;
    }

    /**
     * Sets a string that will be used to compute the average character width. By default it is
     * initialized to {@link #BALANCED_AVERAGE_CHARACTER_TEST_STRING}. You can provide other test
     * strings, for example:
     * <ul>
     * <li>&quot;Xximeee&quot;</li>
     * <li>&quot;ABCEDEFHIJKLMNOPQRSTUVWXYZ&quot;</li>
     * <li>&quot;abcdefghijklmnopqrstuvwxyz&quot;</li>
     * </ul>
     *
     * @param newTestString the test string to be used
     * @throws NullPointerException if {@code newTestString} is {@code null}
     * @throws IllegalArgumentException if {@code newTestString} is empty or whitespace
     */
    public void setAverageCharacterWidthTestString(String newTestString) {
        checkNotBlank(newTestString, MUST_NOT_BE_BLANK, "test string");
        String oldTestString = averageCharWidthTestString;
        averageCharWidthTestString = newTestString;
        firePropertyChange(
                PROPERTY_AVERAGE_CHARACTER_WIDTH_TEST_STRING,
                oldTestString,
                newTestString);
    }

    /**
     * Returns the dialog font that is used to compute the dialog base units. If a default dialog
     * font has been set using {@link #setDefaultDialogFont(Font)}, this font will be returned.
     * Otherwise a cached fallback will be lazily created.
     *
     * @return the font used to compute the dialog base units
     */
    public Font getDefaultDialogFont() {
        return defaultDialogFont != null
                ? defaultDialogFont
                : getCachedDefaultDialogFont();
    }

    /**
     * Sets a dialog font that will be used to compute the dialog base units.
     *
     * @param newFont the default dialog font to be set
     */
    public void setDefaultDialogFont(Font newFont) {
        Font oldFont = defaultDialogFont; // Don't use the getter
        defaultDialogFont = newFont;
        clearCache();
        firePropertyChange(PROPERTY_DEFAULT_DIALOG_FONT, oldFont, newFont);
    }

    // Implementing Abstract Superclass Behavior ******************************
    /**
     * Returns the cached or computed horizontal dialog base units.
     *
     * @param component a Component that provides the font and graphics
     * @return the horizontal dialog base units
     */
    @Override
    protected double getDialogBaseUnitsX(Component component) {
        return getDialogBaseUnits(component).x;
    }

    /**
     * Returns the cached or computed vertical dialog base units for the given component.
     *
     * @param component a Component that provides the font and graphics
     * @return the vertical dialog base units
     */
    @Override
    protected double getDialogBaseUnitsY(Component component) {
        return getDialogBaseUnits(component).y;
    }

    // Compute and Cache Global and Components Dialog Base Units **************
    /**
     * Lazily computes and answer the global dialog base units. Should be re-computed if the
     * l&amp;f, platform, or screen changes.
     *
     * @return a cached DialogBaseUnits object used globally if no container is available
     */
    private DialogBaseUnits getGlobalDialogBaseUnits() {
        if (cachedGlobalDialogBaseUnits == null) {
            cachedGlobalDialogBaseUnits = computeGlobalDialogBaseUnits();
        }
        return cachedGlobalDialogBaseUnits;
    }

    /**
     * Looks up and returns the dialog base units for the given component. In case the component is
     * {@code null} the global dialog base units are answered.<p>
     *
     * Before we compute the dialog base units we check whether they have been computed and cached
     * before - for the same component {@code FontMetrics}.
     *
     * @param c the component that provides the graphics object
     * @return the DialogBaseUnits object for the given component
     */
    private DialogBaseUnits getDialogBaseUnits(Component c) {
        FormUtils.ensureValidCache();
        if (c == null) { // || (font = c.getFont()) == null) {
            // logInfo("Missing font metrics: " + c);
            return getGlobalDialogBaseUnits();
        }
        FontMetrics fm = c.getFontMetrics(getDefaultDialogFont());
        if (fm.equals(cachedFontMetrics)) {
            return cachedDialogBaseUnits;
        }
        DialogBaseUnits dialogBaseUnits = computeDialogBaseUnits(fm);
        cachedFontMetrics = fm;
        cachedDialogBaseUnits = dialogBaseUnits;
        return dialogBaseUnits;
    }

    /**
     * Computes and returns the horizontal dialog base units. Honors the font, font size and
     * resolution.<p>
     *
     * Implementation Note: 14dluY map to 22 pixel for 8pt Tahoma on 96 dpi. I could not yet manage
     * to compute the Microsoft compliant font height. Therefore this method adds a correction value
     * that seems to work well with the vast majority of desktops.<p>
     *
     * TODO: Revise the computation of vertical base units as soon as there are more information
     * about the original computation in Microsoft environments.
     *
     * @param metrics the FontMetrics used to measure the dialog font
     * @return the horizontal and vertical dialog base units
     */
    private DialogBaseUnits computeDialogBaseUnits(FontMetrics metrics) {
        double averageCharWidth
                = computeAverageCharWidth(metrics, averageCharWidthTestString);
        int ascent = metrics.getAscent();
        double height = ascent > 14 ? ascent : ascent + (15 - ascent) / 3;
        DialogBaseUnits dialogBaseUnits
                = new DialogBaseUnits(averageCharWidth, height);
        if (LOGGER.isLoggable(Level.CONFIG)) {
            LOGGER.config(
                    "Computed dialog base units "
                    + dialogBaseUnits
                    + " for: "
                    + metrics.getFont());
        }
        return dialogBaseUnits;
    }

    /**
     * Computes the global dialog base units. The current implementation assumes a fixed 8pt font
     * and on 96 or 120 dpi. A better implementation should ask for the main dialog font and should
     * honor the current screen resolution.<p>
     *
     * Should be re-computed if the l&amp;f, platform, or screen changes.
     *
     * @return a DialogBaseUnits object used globally if no container is available
     */
    private DialogBaseUnits computeGlobalDialogBaseUnits() {
        LOGGER.config("Computing global dialog base units...");
        Font dialogFont = getDefaultDialogFont();
        FontMetrics metrics = createDefaultGlobalComponent().getFontMetrics(dialogFont);
        DialogBaseUnits globalDialogBaseUnits = computeDialogBaseUnits(metrics);
        return globalDialogBaseUnits;
    }

    /**
     * Lazily creates and returns a fallback for the dialog font that is used to compute the dialog
     * base units. This fallback font is cached and will be reset if the L&amp;F changes.
     *
     * @return the cached fallback font used to compute the dialog base units
     */
    private Font getCachedDefaultDialogFont() {
        FormUtils.ensureValidCache();
        if (cachedDefaultDialogFont == null) {
            cachedDefaultDialogFont = lookupDefaultDialogFont();
        }
        return cachedDefaultDialogFont;
    }

    /**
     * Looks up and returns the font used by buttons. First, tries to request the button font from
     * the UIManager; if this fails a JButton is created and asked for its font.
     *
     * @return the font used for a standard button
     */
    private static Font lookupDefaultDialogFont() {
        Font buttonFont = UIManager.getFont("Button.font");
        return buttonFont != null
                ? buttonFont
                : new JButton().getFont();
    }

    /**
     * Creates and returns a component that is used to lookup the default font metrics. The current
     * implementation creates a {@code JPanel}. Since this panel has no parent, it has no toolkit
     * assigned. And so, requesting the font metrics will end up using the default toolkit and its
     * deprecated method {@code ToolKit#getFontMetrics()}
     * .<p>
     *
     * TODO: Consider publishing this method and providing a setter, so that an API user can set a
     * realized component that has a toolkit assigned.
     *
     * @return a component used to compute the default font metrics
     */
    private static Component createDefaultGlobalComponent() {
        return new JPanel(null);
    }

    /**
     * Invalidates the caches. Resets the global dialog base units, clears the Map from
     * {@code FontMetrics} to dialog base units, and resets the fallback for the default dialog
     * font. This is invoked after a change of the look&amp;feel.
     */
    void clearCache() {
        cachedGlobalDialogBaseUnits = null;
        cachedFontMetrics = null;
        cachedDefaultDialogFont = null;
    }

    // Helper Code ************************************************************
    /**
     * Describes horizontal and vertical dialog base units.
     */
    private static final class DialogBaseUnits {

        final double x;
        final double y;

        DialogBaseUnits(double dialogBaseUnitsX, double dialogBaseUnitsY) {
            this.x = dialogBaseUnitsX;
            this.y = dialogBaseUnitsY;
        }

        @Override
        public String toString() {
            return "DBU(x=" + x + "; y=" + y + ")";
        }
    }

}
