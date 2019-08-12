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
package com.privatejgoodies.common.base;

import java.util.Formatter;

/**
 * Reduces the code necessary to check preconditions on method state and parameters.
 *
 * @author Karsten Lentzsch
 */
public final class Preconditions {

    private Preconditions() {
        // Override default constructor; prevents instantiation.
    }

    // Tests that throw IllegalArgumentExceptions *****************************
    /**
     * Checks the truth of the given expression and throws a customized
     * {@link IllegalArgumentException} if it is false. Intended for doing parameter validation in
     * methods and constructors, e.g.:
     * <blockquote><pre>
     * public void foo(int count) {
     *    Preconditions.checkArgument(count &gt; 0, "count must be positive.");
     * }
     * </pre></blockquote>
     *
     * @param expression the precondition to check involving one ore more parameters to the calling
     * method or constructor
     * @param message the detail message to be used in the event that an exception is thrown
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks the truth of the given expression and throws a customized
     * {@link IllegalArgumentException} if it is false. Intended for doing parameter validation in
     * methods and constructors, e.g.:
     * <blockquote><pre>
     * public void foo(int count) {
     *    Preconditions.checkArgument(count &gt; 0, "count must be positive: %s.", count);
     * }
     * </pre></blockquote>
     *
     * @param expression the precondition to check involving one ore more parameters to the calling
     * method or constructor
     * @param messageFormat a {@link Formatter format} string for the detail message to be used in
     * the event that an exception is thrown.
     * @param messageArgs the arguments referenced by the format specifiers in the
     * {@code messageFormat}
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(
            boolean expression, String messageFormat, Object... messageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(format(messageFormat, messageArgs));
        }
    }

    // Tests Throwing NullPointerExceptions ***********************************
    /**
     * Checks that the given object reference is not {@code null} and throws a customized
     * {@link NullPointerException} if it is. Intended for doing parameter validation in methods and
     * constructors, e.g.:
     * <blockquote><pre>
     * public void foo(Bar bar, Baz baz) {
     *      this.bar = Preconditions.checkNotNull(bar, "bar must not be null.");
     *      Preconditions.checkNotBull(baz, "baz must not be null.");
     * }
     * </pre></blockquote>
     *
     * @param reference the object reference to check for being {@code null}
     * @param message the detail message to be used in the event that an exception is thrown
     * @param <T> the type of the reference
     * @return {@code reference} if not {@code null}
     * @throws NullPointerException if {@code reference} is {@code null}
     */
    public static <T> T checkNotNull(T reference, String message) {
        if (reference == null) {
            throw new NullPointerException(message);
        }
        return reference;
    }

    /**
     * Checks that the given object reference is not {@code null} and throws a customized
     * {@link NullPointerException} if it is. Intended for doing parameter validation in methods and
     * constructors, e.g.:
     * <blockquote><pre>
     * public void foo(Bar bar, Baz baz) {
     *      this.bar = Preconditions.checkNotNull(bar, "bar must not be null.");
     *      Preconditions.checkNotBull(baz, "The %s must not be null.", "baz");
     * }
     * </pre></blockquote>
     *
     * @param reference the object reference to check for being {@code null}
     * @param messageFormat a {@link Formatter format} string for the detail message to be used in
     * the event that an exception is thrown.
     * @param messageArgs the arguments referenced by the format specifiers in the
     * {@code messageFormat}
     * @param <T> the type of the reference
     * @return {@code reference} if not {@code null}
     * @throws NullPointerException if {@code reference} is {@code null}
     */
    public static <T> T checkNotNull(
            T reference, String messageFormat, Object... messageArgs) {
        if (reference == null) {
            throw new NullPointerException(format(messageFormat, messageArgs));
        }
        return reference;
    }

    // Tests Throwing IllegalStateExceptions **********************************
    /**
     * Checks the truth of the given expression and throws a customized
     * {@link IllegalStateException} if it is false. Intended for doing validation in methods
     * involving the state of the calling instance, but not involving parameters of the calling
     * method, e.g.:
     * <blockquote><pre>
     * public void unlock() {
     *    Preconditions.checkState(locked, "Must be locked to be unlocked.");
     * }
     * </pre></blockquote>
     *
     * @param expression the precondition to check involving the state of the calling instance
     * @param message the detail message to be used in the event that an exception is thrown
     * @throws IllegalStateException if {@code expression} is false
     */
    public static void checkState(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Checks the truth of the given expression and throws a customized
     * {@link IllegalStateException} if it is false. Intended for doing validation in methods
     * involving the state of the calling instance, but not involving parameters of the calling
     * method, e.g.:
     * <blockquote><pre>
     * public void unlock() {
     *    Preconditions.checkState(locked,
     *        "Must be locked to be unlocked. Most recent lock: %s",
     *        mostRecentLock);
     * }
     * </pre></blockquote>
     *
     * @param expression the precondition to check involving the state of the calling instance
     * @param messageFormat a {@link Formatter format} string for the detail message to be used in
     * the event that an exception is thrown.
     * @param messageArgs the arguments referenced by the format specifiers in the
     * {@code messageFormat}
     * @throws IllegalStateException if {@code expression} is false
     */
    public static void checkState(
            boolean expression, String messageFormat, Object... messageArgs) {
        if (!expression) {
            throw new IllegalStateException(format(messageFormat, messageArgs));
        }
    }

    // Combined Tests Throwing Multiple Exceptions ****************************
    /**
     * Checks that the given string is not blank and throws a customized
     * {@link NullPointerException} if it is {@code null}, and a customized
     * {@link IllegalArgumentException} if it is empty or whitespace. Intended for doing parameter
     * validation in methods and constructors, e.g.:
     * <blockquote><pre>
     * public void foo(String text) {
     *      checkNotBlank(text, "The text must not be null, empty or whitespace.");
     * }
     * </pre></blockquote>
     *
     * @param str the string to check for being blank
     * @param message the detail message to be used in the event that an exception is thrown
     * @return {@code str} if not {@code null}
     * @throws NullPointerException if {@code str} is {@code null}
     * @throws IllegalArgumentException if {@code str} is empty or whitespace
     */
    public static String checkNotBlank(String str, String message) {
        checkNotNull(str, message);
        checkArgument(Strings.isNotBlank(str), message);
        return str;
    }

    /**
     * Checks that the given string is not blank and throws a customized
     * {@link NullPointerException} if it is {@code null}, and a customized
     * {@link IllegalArgumentException} if it is empty or whitespace. Intended for doing parameter
     * validation in methods and constructors, e.g.:
     * <blockquote><pre>
     * public void foo(String text, String id) {
     *      checkNotBlank(
     *          text,
     *          "The text for %s must not be null, empty or whitespace.",
     *          id);
     * }
     * </pre></blockquote>
     *
     * @param str the string to check for being blank
     * @param messageFormat a {@link Formatter format} string for the detail message to be used in
     * the event that an exception is thrown.
     * @param messageArgs the arguments referenced by the format specifiers in the
     * {@code messageFormat}
     * @return {@code str} if not {@code null}
     * @throws NullPointerException if {@code str} is {@code null}
     * @throws IllegalArgumentException if {@code str} is empty or whitespace
     */
    public static String checkNotBlank(
            String str, String messageFormat, Object... messageArgs) {
        checkNotNull(str, messageFormat, messageArgs);
        checkArgument(Strings.isNotBlank(str), messageFormat, messageArgs);
        return str;
    }

    // Helper Code ************************************************************
    static String format(String messageFormat, Object... messageArgs) {
        return String.format(messageFormat, messageArgs);
    }

}
