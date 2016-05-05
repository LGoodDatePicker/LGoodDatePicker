/*
 * Copyright (c) 2009-2013 JGoodies Software GmbH. All Rights Reserved.
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
package com.privatejgoodies.common.internal;

import static com.privatejgoodies.common.base.Preconditions.checkNotNull;
import static com.privatejgoodies.common.internal.Messages.MUST_NOT_BE_NULL;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Turns a ResourceBundle into a {@link StringLocalizer}.
 *
 * <strong>Note:</strong> This class is not part of the public JGoodies Common API. It's intended
 * for implementation purposes only. The class's API may change at any time.
 *
 * @author Karsten Lentzsch
 *
 * @since 1.5.1
 */
public final class ResourceBundleLocalizer implements StringLocalizer {

    private final ResourceBundle bundle;

    // Instance Creation ******************************************************
    public ResourceBundleLocalizer(ResourceBundle bundle) {
        this.bundle = checkNotNull(bundle, MUST_NOT_BE_NULL, "resource bundle");
    }

    // StringLocalizer Implementation *****************************************
    /**
     * Looks up and returns the internationalized (i15d) string for the given resource key from the
     * ResourceBundle that has been provided during the builder construction.
     *
     * @param resourceKey the key to look for in the resource bundle
     * @return the associated internationalized string, or the resource key itself in case of a
     * missing resource
     * @throws IllegalStateException if no ResourceBundle has been set
     */
    @Override
    public String getString(String resourceKey) {
        try {
            return bundle.getString(resourceKey);
        } catch (MissingResourceException mre) {
            return resourceKey;
        }
    }

}
