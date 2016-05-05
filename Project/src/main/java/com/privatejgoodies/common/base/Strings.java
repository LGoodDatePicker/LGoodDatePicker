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

/**
 * Provides frequently used static null-safe String testing methods .
 *
 * @author Karsten Lentzsch
 */
public class Strings {

    /**
     * A string with three dots that should is often meant to be the ellipsis string "\u2026" or
     * character '\u2026'.
     *
     * @see #ELLIPSIS_STRING
     * @since 1.7
     */
    public static final String NO_ELLIPSIS_STRING = "...";

    /**
     * The correct ellipsis string.
     *
     * @see #NO_ELLIPSIS_STRING
     */
    public static final String ELLIPSIS_STRING = "\u2026";

    protected Strings() {
        // Override default constructor; prevents direct instantiation.
    }

    // String Validations ***************************************************
    /**
     * Checks if the given string is whitespace, empty ("") or {@code null}.
     *
     * <pre>
     * Strings.isBlank(null)    == true
     * Strings.isBlank("")      == true
     * Strings.isBlank(" ")     == true
     * Strings.isBlank(" abc")  == false
     * Strings.isBlank("abc ")  == false
     * Strings.isBlank(" abc ") == false
     * </pre>
     *
     * @param str the string to check, may be {@code null}
     * @return {@code true} if the string is whitespace, empty or {@code null}
     *
     * @see #isEmpty(String)
     */
    public static boolean isBlank(String str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return true;
        }
        for (int i = length - 1; i >= 0; i--) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given string is not empty (""), not {@code null} and not whitespace only.
     *
     * <pre>
     * Strings.isNotBlank(null)    == false
     * Strings.isNotBlank("")      == false
     * Strings.isNotBlank(" ")     == false
     * Strings.isNotBlank(" abc")  == true
     * Strings.isNotBlank("abc ")  == true
     * Strings.isNotBlank(" abc ") == true
     * </pre>
     *
     * @param str the string to check, may be {@code null}
     * @return {@code true} if the string is not empty and not {@code null} and not whitespace only
     *
     * @see #isEmpty(String)
     */
    public static boolean isNotBlank(String str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return false;
        }
        for (int i = length - 1; i >= 0; i--) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given string is empty ("") or {@code null}.
     *
     * <pre>
     * Strings.isEmpty(null)  == true
     * Strings.isEmpty("")    == true
     * Strings.isEmpty(" ")   == false
     * Strings.isEmpty("Hi ") == false
     * </pre>
     *
     * @param str the string to check, may be {@code null}
     * @return {@code true} if the string is empty or {@code null}
     *
     * @see #isBlank(String)
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * Checks if the given string is not empty ("") and not {@code null}.
     *
     * <pre>
     * Strings.isNotEmpty(null)  == false
     * Strings.isNotEmpty("")    == false
     * Strings.isNotEmpty(" ")   == true
     * Strings.isNotEmpty("Hi")  == true
     * Strings.isNotEmpty("Hi ") == true
     * </pre>
     *
     * @param str the string to check, may be {@code null}
     * @return {@code true} if the string is not empty and not {@code null}
     *
     * @see #isBlank(String)
     */
    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    /**
     * Checks if the given string is {@code null}, empty (""), or the first and last characters are
     * not whitespace.
     *
     * <pre>
     * Strings.isTrimmed(null)  == true
     * Strings.isTrimmed("")    == true
     * Strings.isTrimmed(" ")   == false
     * Strings.isTrimmed("Hi")  == true
     * Strings.isTrimmed("Hi ") == false
     * Strings.isTrimmed(" Hi") == false
     * </pre>
     *
     * @param str the string to check, may be {@code null}
     * @return {@code true} if the string is {@code null}, empty, or the first and last characters
     * are not whitespace.
     *
     * @since 1.3
     */
    public static boolean isTrimmed(String str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return true;
        }
        return !Character.isWhitespace(str.charAt(0))
                && !Character.isWhitespace(str.charAt(length - 1));
    }

    /**
     * Checks if {@code str} starts with the given prefix ignoring cases. {@code null} is handled
     * safely; if both arguments are null, true is returned, false otherwise.
     *
     * <pre>
     * Strings.startsWithIgnoreCase(null, null)      == true
     * Strings.startsWithIgnoreCase("a", null)       == false
     * Strings.startsWithIgnoreCase(null, "a")       == false
     * Strings.startsWithIgnoreCase("",  "")         == true
     * Strings.startsWithIgnoreCase(" ", "")         == true
     * Strings.startsWithIgnoreCase("John", "J")     == true
     * Strings.startsWithIgnoreCase("John", "Jo")    == true
     * Strings.startsWithIgnoreCase("John", "Joh")   == true
     * Strings.startsWithIgnoreCase("John", "joh")   == true
     * Strings.startsWithIgnoreCase("john", "Joh")   == true
     * Strings.startsWithIgnoreCase("john", "joh")   == true
     * Strings.startsWithIgnoreCase("John", "John")  == true
     * Strings.startsWithIgnoreCase("John", "john")  == true
     * Strings.startsWithIgnoreCase("John", "Jonny") == false
     * </pre>
     *
     * @param str the test string to check, may be null
     * @param prefix the prefix to check for, may be null
     * @return {@code true}, if the string starts with the prefix, ignoring cases, {@code false}
     * otherwise
     *
     * @see String#startsWith(java.lang.String)
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if (str == null) {
            return prefix == null;
        }
        if (prefix == null) {  // str is not null
            return false;
        }
        return str.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    /**
     * Abbreviates the given string if it exceeds the given maximum length by replacing its center
     * part with an ellipsis ('&hellip;'). If the string is {@code null} or shorter than the limit,
     * it is returned as is.<p>
     *
     * <pre>
     * Strings.abbreviateCenter(null,      3) == null
     * Strings.abbreviateCenter("",        3) == ""
     * Strings.abbreviateCenter(" ",       3) == " "
     * Strings.abbreviateCenter("a",       3) == "a"
     * Strings.abbreviateCenter("ab",      3) == "ab"
     * Strings.abbreviateCenter("abc",     3) == "abc"
     * Strings.abbreviateCenter("abcd",    3) == "a&hellip;d"
     * Strings.abbreviateCenter("abcde",   3) == "a&hellip;e"
     * Strings.abbreviateCenter("abcde",   4) == "ab&hellip;e"
     * Strings.abbreviateCenter("abcdef",  4) == "ab&hellip;f"
     * Strings.abbreviateCenter("abcdefg", 5) == "ab&hellip;fg"
     * </pre>
     *
     * @param str the source string
     * @param maxLength the maximum length of the result string
     * @return {@code str} if its length is less than or equal to {@code maxLength}, an abbreviated
     * string with length {@code maxLength} where the center is replaced by an ellipsis
     */
    public static String abbreviateCenter(String str, int maxLength) {
        if (str == null) {
            return null;
        }
        final int length = str.length();
        if (length <= maxLength) {
            return str;
        }
        int headLength = maxLength / 2;
        int tailLength = maxLength - headLength - 1;
        String head = str.substring(0, headLength);
        String tail = str.substring(length - tailLength, length);
        return head + "\u2026" + tail;
    }

}
