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

import com.privatejgoodies.common.base.SystemUtils;
import com.privatejgoodies.forms.layout.ConstantSize;
import com.privatejgoodies.forms.layout.Size;

/**
 * An abstract class that describes a layout and design style guide. It provides constants used to
 * lay out panels consistently.<p>
 *
 * <strong>Note:</strong> This class is work in progress and the API may change without notice.
 * Therefore it is recommended to not write custom subclasses for production code. A future version
 * of this class may collaborate with a class {@code LogicalSize} or {@code StyledSize}.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.15 $
 *
 * @see com.privatejgoodies.forms.util.MacLayoutStyle
 * @see com.privatejgoodies.forms.util.WindowsLayoutStyle
 * @see com.privatejgoodies.forms.layout.FormSpecs
 */
public abstract class LayoutStyle {

    /**
     * Holds the current layout style.
     */
    private static LayoutStyle current = initialLayoutStyle();

    // Computing the initial layout style *************************************
    /**
     * Computes and returns the initial {@code LayoutStyle}. Checks the OS name and returns
     * {@code MacLayoutStyle} on Mac OS X and {@code WindowLayoutStyle} on all other platforms.
     *
     * @return MacLayoutStyle on Mac, WindowsLayoutStyle on all other platforms
     */
    private static LayoutStyle initialLayoutStyle() {
        return SystemUtils.IS_OS_MAC
                ? MacLayoutStyle.INSTANCE
                : WindowsLayoutStyle.INSTANCE;
    }

    // Accessing the current style ******************************************
    /**
     * Returns the current {@code LayoutStyle}.
     *
     * @return the current {@code LayoutStyle}
     */
    public static LayoutStyle getCurrent() {
        return current;
    }

    /**
     * Set a new {@code LayoutStyle}.
     *
     * @param newLayoutStyle the style to be set
     */
    public static void setCurrent(LayoutStyle newLayoutStyle) {
        current = newLayoutStyle;
    }

    // Layout Sizes *********************************************************
    /**
     * Returns this style's default button width.
     *
     * @return the default button width
     *
     * @see #getDefaultButtonHeight()
     */
    public abstract Size getDefaultButtonWidth();

    /**
     * Returns this style's default button height.
     *
     * @return the default button height
     *
     * @see #getDefaultButtonWidth()
     */
    public abstract Size getDefaultButtonHeight();

    /**
     * Returns this style's horizontal margin for general dialogs.
     *
     * @return the horizontal margin for general dialogs
     *
     * @see #getDialogMarginY()
     * @see #getTabbedDialogMarginX()
     */
    public abstract ConstantSize getDialogMarginX();

    /**
     * Returns this style's vertical margin for general dialogs.
     *
     * @return the vertical margin for general dialogs
     *
     * @see #getDialogMarginX()
     * @see #getTabbedDialogMarginY()
     */
    public abstract ConstantSize getDialogMarginY();

    /**
     * Returns this style's horizontal margin for dialogs that consist of a tabbed pane.
     *
     * @return the horizontal margin for dialogs that consist of a tabbed pane
     *
     * @see #getTabbedDialogMarginY()
     * @see #getDialogMarginX()
     *
     * @since 1.0.3
     */
    public abstract ConstantSize getTabbedDialogMarginX();

    /**
     * Returns this style's vertical margin for dialogs that consist of a tabbed pane.
     *
     * @return the vertical margin for dialogs that consist of a tabbed pane
     *
     * @see #getTabbedDialogMarginX()
     * @see #getDialogMarginY()
     *
     * @since 1.0.3
     */
    public abstract ConstantSize getTabbedDialogMarginY();

    /**
     * Returns a gap used to separate a label and associated control.
     *
     * @return a gap between label and associated control
     *
     * @see #getRelatedComponentsPadX()
     * @see #getUnrelatedComponentsPadX()
     */
    public abstract ConstantSize getLabelComponentPadX();

    /**
     * Returns a gap used to separate a label and associated control.
     *
     * @return a gap between label and associated control
     *
     * @see #getRelatedComponentsPadY()
     * @see #getUnrelatedComponentsPadY()
     *
     * @since 1.4
     */
    public abstract ConstantSize getLabelComponentPadY();

    /**
     * Returns a horizontal gap used to separate related controls.
     *
     * @return a horizontal gap between related controls
     *
     * @see #getLabelComponentPadX()
     * @see #getRelatedComponentsPadY()
     * @see #getUnrelatedComponentsPadX()
     */
    public abstract ConstantSize getRelatedComponentsPadX();

    /**
     * Returns a vertical gap used to separate related controls.
     *
     * @return a vertical gap between related controls
     *
     * @see #getRelatedComponentsPadX()
     * @see #getUnrelatedComponentsPadY()
     */
    public abstract ConstantSize getRelatedComponentsPadY();

    /**
     * Returns a horizontal gap used to separate unrelated controls.
     *
     * @return a horizontal gap between unrelated controls
     *
     * @see #getLabelComponentPadX()
     * @see #getUnrelatedComponentsPadY()
     * @see #getRelatedComponentsPadX()
     */
    public abstract ConstantSize getUnrelatedComponentsPadX();

    /**
     * Returns a vertical gap used to separate unrelated controls.
     *
     * @return a vertical gap between unrelated controls
     *
     * @see #getUnrelatedComponentsPadX()
     * @see #getRelatedComponentsPadY()
     */
    public abstract ConstantSize getUnrelatedComponentsPadY();

    /**
     * Returns a narrow vertical pad used to separate lines.
     *
     * @return a narrow vertical pad used to separate lines
     *
     * @see #getLinePad()
     * @see #getParagraphPad()
     */
    public abstract ConstantSize getNarrowLinePad();

    /**
     * Returns a narrow vertical pad used to separate lines.
     *
     * @return a vertical pad used to separate lines
     *
     * @see #getNarrowLinePad()
     * @see #getParagraphPad()
     */
    public abstract ConstantSize getLinePad();

    /**
     * Returns a pad used to separate paragraphs.
     *
     * @return a vertical pad used to separate paragraphs
     *
     * @see #getNarrowLinePad()
     * @see #getLinePad()
     */
    public abstract ConstantSize getParagraphPad();

    /**
     * Returns a pad used to separate a button bar from a component.
     *
     * @return a vertical pad used to separate paragraphs
     *
     * @see #getRelatedComponentsPadY()
     * @see #getUnrelatedComponentsPadY()
     *
     * @since 1.0.3
     */
    public abstract ConstantSize getButtonBarPad();

}
