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

import static com.privatejgoodies.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses encoded column and row specifications. Returns ColumnSpec or RowSpec arrays if successful,
 * and aims to provide useful information in case of a syntax error.
 *
 * @author	Karsten Lentzsch
 * @version $Revision: 1.12 $
 *
 * @see ColumnSpec
 * @see RowSpec
 */
public final class FormSpecParser {

    // Parser Patterns ******************************************************
    private static final Pattern MULTIPLIER_PREFIX_PATTERN
            = Pattern.compile("\\d+\\s*\\*\\s*\\(");

    private static final Pattern DIGIT_PATTERN
            = Pattern.compile("\\d+");

    // Instance Fields ********************************************************
    private final String source;
    private final LayoutMap layoutMap;

    // Instance Creation ******************************************************
    /**
     * Constructs a parser for the given encoded column/row specification, the given LayoutMap, and
     * orientation.
     *
     * @param source the raw encoded column or row specification as provided by the user
     * @param description describes the source, e.g. "column specification"
     * @param layoutMap maps layout variable names to ColumnSpec and RowSpec objects
     * @param horizontal {@code true} for columns, {@code false} for rows
     *
     * @throws NullPointerException if {@code source} or {@code layoutMap} is {@code null}
     */
    private FormSpecParser(
            String source,
            String description,
            LayoutMap layoutMap,
            boolean horizontal) {
        checkNotNull(source, "The %S must not be null.", description);
        checkNotNull(layoutMap, "The LayoutMap must not be null.");
        this.layoutMap = layoutMap;
        this.source = this.layoutMap.expand(source, horizontal);
    }

    // Parser API *************************************************************
    static ColumnSpec[] parseColumnSpecs(
            String encodedColumnSpecs,
            LayoutMap layoutMap) {
        FormSpecParser parser = new FormSpecParser(
                encodedColumnSpecs,
                "encoded column specifications",
                layoutMap,
                true);
        return parser.parseColumnSpecs();
    }

    static RowSpec[] parseRowSpecs(
            String encodedRowSpecs,
            LayoutMap layoutMap) {
        FormSpecParser parser = new FormSpecParser(
                encodedRowSpecs,
                "encoded row specifications",
                layoutMap,
                false);
        return parser.parseRowSpecs();
    }

    // Parser Implementation **************************************************
    private ColumnSpec[] parseColumnSpecs() {
        List encodedColumnSpecs = split(source, 0);
        int columnCount = encodedColumnSpecs.size();
        ColumnSpec[] columnSpecs = new ColumnSpec[columnCount];
        for (int i = 0; i < columnCount; i++) {
            String encodedSpec = (String) encodedColumnSpecs.get(i);
            columnSpecs[i] = ColumnSpec.decodeExpanded(encodedSpec);
        }
        return columnSpecs;
    }

    private RowSpec[] parseRowSpecs() {
        List encodedRowSpecs = split(source, 0);
        int rowCount = encodedRowSpecs.size();
        RowSpec[] rowSpecs = new RowSpec[rowCount];
        for (int i = 0; i < rowCount; i++) {
            String encodedSpec = (String) encodedRowSpecs.get(i);
            rowSpecs[i] = RowSpec.decodeExpanded(encodedSpec);
        }
        return rowSpecs;
    }

    // Parser Implementation **************************************************
    private List<String> split(String expression, int offset) {
        List<String> encodedSpecs = new ArrayList<String>();
        int parenthesisLevel = 0;  // number of open '('
        int bracketLevel = 0;      // number of open '['
        int quoteLevel = 0;        // number of open '\''
        int length = expression.length();
        int specStart = 0;
        char c;
        boolean lead = true;
        for (int i = 0; i < length; i++) {
            c = expression.charAt(i);
            if (lead && Character.isWhitespace(c)) {
                specStart++;
                continue;
            }
            lead = false;
            if (c == ',' && parenthesisLevel == 0 && bracketLevel == 0 && quoteLevel == 0) {
                String token = expression.substring(specStart, i);
                addSpec(encodedSpecs, token, offset + specStart);
                specStart = i + 1;
                lead = true;
            } else if (c == '(') {
                if (bracketLevel > 0) {
                    fail(offset + i, "illegal '(' in [...]");
                }
                parenthesisLevel++;
            } else if (c == ')') {
                if (bracketLevel > 0) {
                    fail(offset + i, "illegal ')' in [...]");
                }
                parenthesisLevel--;
                if (parenthesisLevel < 0) {
                    fail(offset + i, "missing '('");
                }
            } else if (c == '[') {
                if (bracketLevel > 0) {
                    fail(offset + i, "too many '['");
                }
                bracketLevel++;
            } else if (c == ']') {
                bracketLevel--;
                if (bracketLevel < 0) {
                    fail(offset + i, "missing '['");
                }
            } else if (c == '\'') {
                if (quoteLevel == 0) {
                    quoteLevel++;
                } else if (quoteLevel == 1) {
                    quoteLevel--;
                }
            }
        }
        if (parenthesisLevel > 0) {
            fail(offset + length, "missing ')'");
        }
        if (bracketLevel > 0) {
            fail(offset + length, "missing ']");
        }
        if (specStart < length) {
            String token = expression.substring(specStart);
            addSpec(encodedSpecs, token, offset + specStart);
        }
        return encodedSpecs;
    }

    private void addSpec(List<String> encodedSpecs, String expression, int offset) {
        String trimmedExpression = expression.trim();
        Multiplier multiplier = multiplier(trimmedExpression, offset);
        if (multiplier == null) {
            encodedSpecs.add(trimmedExpression);
            return;
        }
        List<String> subTokenList = split(multiplier.expression, offset + multiplier.offset);
        for (int i = 0; i < multiplier.multiplier; i++) {
            encodedSpecs.addAll(subTokenList);
        }
    }

    private Multiplier multiplier(String expression, int offset) {
        Matcher matcher = MULTIPLIER_PREFIX_PATTERN.matcher(expression);
        if (!matcher.find()) {
            return null;
        }
        if (matcher.start() > 0) {
            fail(offset + matcher.start(), "illegal multiplier position");
        }
        Matcher digitMatcher = DIGIT_PATTERN.matcher(expression);
        if (!digitMatcher.find()) {
            return null;
        }
        String digitStr = expression.substring(0, digitMatcher.end());
        int number = 0;
        try {
            number = Integer.parseInt(digitStr);
        } catch (NumberFormatException e) {
            fail(offset, e);
        }
        if (number <= 0) {
            fail(offset, "illegal 0 multiplier");
        }
        String subexpression = expression.substring(matcher.end(), expression.length() - 1);
        return new Multiplier(number, subexpression, matcher.end());
    }

    // Exceptions *************************************************************
    public static void fail(String source, int index, String description) {
        throw new FormLayoutParseException(message(source, index, description));
    }

    private void fail(int index, String description) {
        throw new FormLayoutParseException(
                message(source, index, description));
    }

    private void fail(int index, NumberFormatException cause) {
        throw new FormLayoutParseException(
                message(source, index, "Invalid multiplier"),
                cause);
    }

    private static String message(String source, int index, String description) {
        StringBuffer buffer = new StringBuffer('\n');
        buffer.append('\n');
        buffer.append(source);
        buffer.append('\n');
        for (int i = 0; i < index; i++) {
            buffer.append(' ');
        }
        buffer.append('^');
        buffer.append(description);
        String message = buffer.toString();
        throw new FormLayoutParseException(message);
    }

    /**
     * Used by the parser for encoded column and row specifications.
     */
    public static final class FormLayoutParseException extends RuntimeException {

        FormLayoutParseException(String message) {
            super(message);
        }

        FormLayoutParseException(String message, Throwable cause) {
            super(message, cause);
        }

    }

    // Helper Class ***********************************************************
    /**
     * Internal helper class that is returned by {@link FormSpecParser#multiplier(String, int)}.
     */
    static final class Multiplier {

        final int multiplier;
        final String expression;
        final int offset;

        Multiplier(int multiplier, String expression, int offset) {
            this.multiplier = multiplier;
            this.expression = expression;
            this.offset = offset;
        }

    }

}
