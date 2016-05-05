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

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

/**
 * A library-internal class that consists only of static utility methods.
 *
 * <strong>Note: This class is not part of the public Forms API. It's intended for library
 * implementation purposes only. The class's API may change at any time.</strong>
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.14 $
 *
 * @since 1.2
 */
public final class FormUtils {

    // Instance *************************************************************
    private FormUtils() {
        // Suppresses default constructor, prevents instantiation.
    }

    // API ********************************************************************
    /**
     * Lazily checks and answers whether the Aqua look&amp;feel is active.
     *
     * @return {@code true} if the current look&amp;feel is Aqua
     */
    public static boolean isLafAqua() {
        ensureValidCache();
        if (cachedIsLafAqua == null) {
            cachedIsLafAqua = Boolean.valueOf(computeIsLafAqua());
        }
        return cachedIsLafAqua.booleanValue();
    }

    // Caching and Lazily Computing the Laf State *****************************
    /**
     * Clears cached internal Forms state that is based on the Look&amp;Feel, for example dialog
     * base units.<p>
     *
     * There's typically no need to call this method directly. It'll be invoked automatically, if
     * the L&amp;F has been changed via {@link UIManager#setLookAndFeel} and cached data is
     * requested. It's been made public to allow cache invalidation for cases where the L&amp;F is
     * changed temporarily by replacing the UIDefaults, for example in a visual editor.
     *
     * @since 1.2.1
     */
    public static void clearLookAndFeelBasedCaches() {
        cachedIsLafAqua = null;
        DefaultUnitConverter.getInstance().clearCache();
    }

    /**
     * Holds the LookAndFeel that has been used to computed cached values. If the current L&amp;F
     * differs from this cached value, the caches must be cleared.
     */
    private static LookAndFeel cachedLookAndFeel;

    /**
     * Holds the cached result of the Aqua l&amp;f check. Is invalidated if a look&amp;feel change
     * has been detected in {@code #ensureValidCache}.
     */
    private static Boolean cachedIsLafAqua;

    /**
     * Computes and answers whether an Aqua look&amp;feel is active. This may be Apple's Aqua
     * L&amp;f, or a sub-L&amp;f that uses the same ID, because it doesn't substantially change the
     * look.
     *
     * @return true if the current look&amp;feel is Aqua
     */
    private static boolean computeIsLafAqua() {
        return UIManager.getLookAndFeel().getID().equals("Aqua");
    }

    static void ensureValidCache() {
        LookAndFeel currentLookAndFeel = UIManager.getLookAndFeel();
        if (currentLookAndFeel != cachedLookAndFeel) {
            clearLookAndFeelBasedCaches();
            cachedLookAndFeel = currentLookAndFeel;
        }
    }

}
