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
package com.privatejgoodies.common.internal;

/**
 * Provides standardized messages for use with the Preconditions class and in exception messages.<p>
 *
 * <strong>Note:</strong> This class is not part of the public JGoodies Common API. It is intended
 * for implementation purposes only. The class's API may change at any time.
 *
 * @author Karsten Lentzsch
 *
 * @since 1.4
 */
public final class Messages {

    private Messages() {
        // Overrides default constructor; prevents instantiation.
    }

    public static final String MUST_NOT_BE_NULL = "The %1$s must not be null.";

    public static final String MUST_NOT_BE_BLANK = "The %1$s must not be null, empty, or whitespace.";

    public static final String MUST_BE_TRIMMED = "The %1$s must not contain leading or trailing whitespace.";

    public static final String USE_ELLIPSIS_NOT_THREE_DOTS
            = "The @Action annotation text ends with three dots \"...\"; use the single ellipsis character '\u2026' (\\u2026) instead.";

}
