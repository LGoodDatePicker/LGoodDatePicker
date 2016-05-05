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

import com.privatejgoodies.forms.layout.ConstantSize;
import com.privatejgoodies.forms.layout.Size;
import com.privatejgoodies.forms.layout.Sizes;

/**
 * A {@link LayoutStyle} that aims to provide layout constants as defined by Microsoft's <em>User
 * Experience Guidelines</em>.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.12 $
 */
public final class MacLayoutStyle extends LayoutStyle {

    static final MacLayoutStyle INSTANCE = new MacLayoutStyle();

    private MacLayoutStyle() {
        // Suppresses default constructor, prevents instantiation.
    }

    // Component Sizes ******************************************************
    private static final Size BUTTON_WIDTH = Sizes.dluX(54);
    private static final Size BUTTON_HEIGHT = Sizes.dluY(14);

    // Gaps ******************************************************************
    private static final ConstantSize DIALOG_MARGIN_X = Sizes.DLUX9;
    private static final ConstantSize DIALOG_MARGIN_Y = Sizes.DLUY9;

    private static final ConstantSize TABBED_DIALOG_MARGIN_X = Sizes.DLUX4;
    private static final ConstantSize TABBED_DIALOG_MARGIN_Y = Sizes.DLUY4;

    private static final ConstantSize LABEL_COMPONENT_PADX = Sizes.DLUX3;
    private static final ConstantSize RELATED_COMPONENTS_PADX = Sizes.DLUX4;
    private static final ConstantSize UNRELATED_COMPONENTS_PADX = Sizes.DLUX8;

    private static final ConstantSize LABEL_COMPONENT_PADY = Sizes.DLUY2;
    private static final ConstantSize RELATED_COMPONENTS_PADY = Sizes.DLUY3;
    private static final ConstantSize UNRELATED_COMPONENTS_PADY = Sizes.DLUY6;
    private static final ConstantSize NARROW_LINE_PAD = Sizes.DLUY2;
    private static final ConstantSize LINE_PAD = Sizes.DLUY3;
    private static final ConstantSize PARAGRAPH_PAD = Sizes.DLUY9;
    private static final ConstantSize BUTTON_BAR_PAD = Sizes.DLUY4;

    // Layout Sizes *********************************************************
    @Override
    public Size getDefaultButtonWidth() {
        return BUTTON_WIDTH;
    }

    @Override
    public Size getDefaultButtonHeight() {
        return BUTTON_HEIGHT;
    }

    @Override
    public ConstantSize getDialogMarginX() {
        return DIALOG_MARGIN_X;
    }

    @Override
    public ConstantSize getDialogMarginY() {
        return DIALOG_MARGIN_Y;
    }

    @Override
    public ConstantSize getTabbedDialogMarginX() {
        return TABBED_DIALOG_MARGIN_X;
    }

    @Override
    public ConstantSize getTabbedDialogMarginY() {
        return TABBED_DIALOG_MARGIN_Y;
    }

    @Override
    public ConstantSize getLabelComponentPadX() {
        return LABEL_COMPONENT_PADX;
    }

    @Override
    public ConstantSize getLabelComponentPadY() {
        return LABEL_COMPONENT_PADY;
    }

    @Override
    public ConstantSize getRelatedComponentsPadX() {
        return RELATED_COMPONENTS_PADX;
    }

    @Override
    public ConstantSize getRelatedComponentsPadY() {
        return RELATED_COMPONENTS_PADY;
    }

    @Override
    public ConstantSize getUnrelatedComponentsPadX() {
        return UNRELATED_COMPONENTS_PADX;
    }

    @Override
    public ConstantSize getUnrelatedComponentsPadY() {
        return UNRELATED_COMPONENTS_PADY;
    }

    @Override
    public ConstantSize getNarrowLinePad() {
        return NARROW_LINE_PAD;
    }

    @Override
    public ConstantSize getLinePad() {
        return LINE_PAD;
    }

    @Override
    public ConstantSize getParagraphPad() {
        return PARAGRAPH_PAD;
    }

    @Override
    public ConstantSize getButtonBarPad() {
        return BUTTON_BAR_PAD;
    }

}
