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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.privatejgoodies.forms.util.LayoutStyle;

/**
 * Provides a hierarchical variable expansion useful to improve layout consistency, style guide
 * compliance, and layout readability.<p>
 *
 * A LayoutMap maps variable names to layout expression Strings. The FormLayout, ColumnSpec, and
 * RowSpec parsers expand variables before an encoded layout specification is parsed and converted
 * into ColumnSpec and RowSpec values. Variables start with the '$' character. The variable name can
 * be wrapped by braces ('{' and '}'). For example, you can write:
 * <code>new FormLayout("pref, $lcg, pref")</code> or
 * <code>new FormLayout("pref, ${lcg}, pref")</code>.<p>
 *
 * LayoutMaps build a chain; each LayoutMap has an optional parent map. The root is defined by
 * {@link LayoutMap#getRoot()}. Application-wide variables should be defined in the root LayoutMap.
 * If you want to override application-wide variables locally, obtain a LayoutMap using {@code
 * new LayoutMap()}, configure it, and provide it as argument to the FormLayout, ColumnSpec, and
 * RowSpec constructors/factory methods.<p>
 *
 * By default the root LayoutMap provides the following associations:
 * <table border="1">
 * <tr><td><b>Variable
 * Name</b><td><b>Abbreviations</b></td><td><b>Orientation</b></td><td><b>Description</b></td></tr>
 * <tr><td>label-component-gap</td><td>lcg, lcgap</td><td>both</td><td>gap between a label and the
 * labeled component</td></tr>
 * <tr><td>related-gap</td><td>rg, rgap</td><td>both</td><td>gap between two related
 * components</td></tr>
 * <tr><td>unrelated-gap</td><td>ug, ugap</td><td>both</td><td>gap between two unrelated
 * components</td></tr>
 * <tr><td>button</td><td>b</td><td>horizontal</td><td>button column with minimum width</td></tr>
 * <tr><td>line-gap</td><td>lg, lgap</td><td>vertical</td><td>gap between two lines</td></tr>
 * <tr><td>narrow-line-gap</td><td>nlg, nlgap</td><td>vertical</td><td>narrow gap between two
 * lines</td></tr>
 * <tr><td>paragraph</td><td>pg, pgap</td><td>vertical</td><td>gap between two
 * paragraphs/sections</td></tr>
 * </table><p>
 *
 * <strong>Examples:</strong>
 * <pre>
 * // Predefined variables
 * new FormLayout(
 *     "pref, $lcgap, pref, $rgap, pref",
 *     "p, $lgap, p, $lgap, p");
 *
 * // Custom variables
 * LayoutMap.getRoot().columnPut("half", "39dlu");
 * LayoutMap.getRoot().columnPut("full", "80dlu");
 * LayoutMap.getRoot().rowPut("table", "fill:0:grow");
 * LayoutMap.getRoot().rowPut("table50", "fill:50dlu:grow");
 * new FormLayout(
 *     "pref, $lcgap, $half, 2dlu, $half",
 *     "p, $lcgap, $table50");
 * new FormLayout(
 *     "pref, $lcgap, $full",
 *     "p, $lcgap, $table50");
 *
 * // Nested variables
 * LayoutMap.getRoot().columnPut("c-gap-c", "$half, 2dlu, $half");
 * new FormLayout(
 *     "pref, $lcgap, ${c-gap-c}", // -&gt; "pref, $lcgap, $half, 2dlu, $half",
 *     "p, $lcgap, $table");
 * </pre>
 *
 * LayoutMap holds two internal Maps that associate key Strings with expression Strings for the
 * columns and rows respectively. Null values are not allowed.<p>
 *
 * <strong>Tips:</strong><ul>
 * <li>You should carefully override predefined variables, because variable users may expect that
 * these don't change.
 * <li>Set custom variables in the root LayoutMap.
 * <li>Avoid aliases for custom variables.
 * </ul>
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.24 $
 *
 * @see FormLayout
 * @see ColumnSpec
 * @see RowSpec
 *
 * @since 1.2
 */
public final class LayoutMap {

    /**
     * Marks a layout variable; used by the Forms parsers.
     */
    private static final char VARIABLE_PREFIX_CHAR = '$';

    /**
     * Maps column aliases to their default name, for example
     * {@code "rgap"} -> {@code "related-gap"}.
     */
    private static final Map<String, String> COLUMN_ALIASES
            = new HashMap<String, String>();

    /**
     * Maps row aliases to their default name, for example {@code "rgap"} -> {@code "related-gap"}.
     */
    private static final Map<String, String> ROW_ALIASES
            = new HashMap<String, String>();

    /**
     * Holds the lazily initialized root map.
     */
    private static LayoutMap root = null;

    // Instance Fields ********************************************************
    /**
     * Refers to the parent map that is used to look up values if this map contains no association
     * for a given key. The parent maps can build chains.
     */
    private final LayoutMap parent;

    /**
     * Holds the raw associations from variable names to expressions. The expression may contain
     * variables that are not expanded.
     */
    private final Map<String, String> columnMap;

    /**
     * Holds the cached associations from variable names to expressions. The expression are fully
     * expanded and contain no variables.
     */
    private final Map<String, String> columnMapCache;

    /**
     * Holds the raw associations from variable names to expressions. The expression may contain
     * variables that are not expanded.
     */
    private final Map<String, String> rowMap;

    /**
     * Holds the cached associations from variable names to expressions. The expression are fully
     * expanded and contain no variables.
     */
    private final Map<String, String> rowMapCache;

    // Instance Creation ******************************************************
    /**
     * Constructs a LayoutMap that has the root LayoutMap as parent.
     */
    public LayoutMap() {
        this(getRoot());
    }

    /**
     * Constructs a LayoutMap with the given optional parent.
     *
     * @param parent the parent LayoutMap, may be {@code null}
     */
    public LayoutMap(LayoutMap parent) {
        this.parent = parent;
        columnMap = new HashMap<String, String>();
        rowMap = new HashMap<String, String>();
        columnMapCache = new HashMap<String, String>();
        rowMapCache = new HashMap<String, String>();
    }

    // Default ****************************************************************
    /**
     * Lazily initializes and returns the LayoutMap that is used for variable expansion, if no
     * custom LayoutMap is provided.
     *
     * @return the LayoutMap that is used, if no custom LayoutMap is provided
     */
    public static synchronized LayoutMap getRoot() {
        if (root == null) {
            root = createRoot();
        }
        return root;
    }

    // Column Mapping *********************************************************
    /**
     * Returns {@code true} if this map or a parent map - if any - contains a mapping for the
     * specified key.
     *
     * @param key key whose presence in this LayoutMap chain is to be tested.
     * @return {@code true} if this map contains a column mapping for the specified key.
     *
     * @throws NullPointerException if the key is {@code null}.
     *
     * @see Map#containsKey(Object)
     */
    public boolean columnContainsKey(String key) {
        String resolvedKey = resolveColumnKey(key);
        return columnMap.containsKey(resolvedKey)
                || parent != null && parent.columnContainsKey(resolvedKey);
    }

    /**
     * Looks up and returns the String associated with the given key. First looks for an association
     * in this LayoutMap. If there's no association, the lookup continues with the parent map - if
     * any.
     *
     * @param key key whose associated value is to be returned.
     * @return the column String associated with the {@code key}, or {@code null} if no LayoutMap in
     * the parent chain contains an association.
     *
     * @throws NullPointerException if {@code key} is {@code null}
     *
     * @see Map#get(Object)
     */
    public String columnGet(String key) {
        String resolvedKey = resolveColumnKey(key);
        String cachedValue = columnMapCache.get(resolvedKey);
        if (cachedValue != null) {
            return cachedValue;
        }
        String value = columnMap.get(resolvedKey);
        if (value == null && parent != null) {
            value = parent.columnGet(resolvedKey);
        }
        if (value == null) {
            return null;
        }
        String expandedString = expand(value, true);
        columnMapCache.put(resolvedKey, expandedString);
        return expandedString;
    }

    /**
     * Associates the specified column String with the specified key in this map. If the map
     * previously contained a mapping for this key, the old value is replaced by the specified
     * value. The value set in this map overrides an association - if any - in the chain of parent
     * LayoutMaps.<p>
     *
     * The {@code value} must not be {@code null}. To remove an association from this map use
     * {@link #columnRemove(String)}.
     *
     * @param key key with which the specified value is to be associated.
     * @param value column expression value to be associated with the specified key.
     * @return previous String associated with specified key, or {@code null} if there was no
     * mapping for key.
     *
     * @throws NullPointerException if the {@code key} or {@code value} is {@code null}.
     *
     * @see Map#put(Object, Object)
     */
    public String columnPut(String key, String value) {
        checkNotNull(value, "The column expression value must not be null.");
        String resolvedKey = resolveColumnKey(key);
        columnMapCache.clear();
        return columnMap.put(
                resolvedKey,
                value.toLowerCase(Locale.ENGLISH));
    }

    public String columnPut(String key, ColumnSpec value) {
        return columnPut(key, value.encode());
    }

    public String columnPut(String key, Size value) {
        return columnPut(key, value.encode());
    }

    /**
     * Removes the column value mapping for this key from this map if it is present.<p>
     *
     * Returns the value to which the map previously associated the key, or {@code null} if the map
     * contained no mapping for this key. The map will not contain a String mapping for the
     * specified key once the call returns.
     *
     * @param key key whose mapping is to be removed from the map.
     * @return previous value associated with specified key, or {@code null} if there was no mapping
     * for key.
     *
     * @throws NullPointerException if {@code key} is {@code null}.
     *
     * @see Map#remove(Object)
     */
    public String columnRemove(String key) {
        String resolvedKey = resolveColumnKey(key);
        columnMapCache.clear();
        return columnMap.remove(resolvedKey);
    }

    // Row Mapping ************************************************************
    /**
     * Returns {@code true} if this map or a parent map - if any - contains a RowSpec mapping for
     * the specified key.
     *
     * @param key key whose presence in this LayoutMap chain is to be tested.
     * @return {@code true} if this map contains a row mapping for the specified key.
     *
     * @throws NullPointerException if the key is {@code null}.
     *
     * @see Map#containsKey(Object)
     */
    public boolean rowContainsKey(String key) {
        String resolvedKey = resolveRowKey(key);
        return rowMap.containsKey(resolvedKey)
                || parent != null && parent.rowContainsKey(resolvedKey);
    }

    /**
     * Looks up and returns the RowSpec associated with the given key. First looks for an
     * association in this LayoutMap. If there's no association, the lookup continues with the
     * parent map - if any.
     *
     * @param key key whose associated value is to be returned.
     * @return the row specification associated with the {@code key}, or {@code null} if no
     * LayoutMap in the parent chain contains an association.
     *
     * @throws NullPointerException if {@code key} is {@code null}
     *
     * @see Map#get(Object)
     */
    public String rowGet(String key) {
        String resolvedKey = resolveRowKey(key);
        String cachedValue = rowMapCache.get(resolvedKey);
        if (cachedValue != null) {
            return cachedValue;
        }
        String value = rowMap.get(resolvedKey);
        if (value == null && parent != null) {
            value = parent.rowGet(resolvedKey);
        }
        if (value == null) {
            return null;
        }
        String expandedString = expand(value, false);
        rowMapCache.put(resolvedKey, expandedString);
        return expandedString;
    }

    public String rowPut(String key, String value) {
        checkNotNull(value, "The row expression value must not be null.");
        String resolvedKey = resolveRowKey(key);
        rowMapCache.clear();
        return rowMap.put(
                resolvedKey,
                value.toLowerCase(Locale.ENGLISH));
    }

    /**
     * Associates the specified ColumnSpec with the specified key in this map. If the map previously
     * contained a mapping for this key, the old value is replaced by the specified value. The
     * RowSpec set in this map override an association - if any - in the chain of parent
     * LayoutMaps.<p>
     *
     * The RowSpec must not be {@code null}. To remove an association from this map use
     * {@link #rowRemove(String)}.
     *
     * @param key key with which the specified value is to be associated.
     * @param value ColumnSpec to be associated with the specified key.
     * @return previous ColumnSpec associated with specified key, or {@code null} if there was no
     * mapping for key.
     *
     * @throws NullPointerException if the {@code key} or {@code value} is {@code null}.
     *
     * @see Map#put(Object, Object)
     */
    public String rowPut(String key, RowSpec value) {
        return rowPut(key, value.encode());
    }

    public String rowPut(String key, Size value) {
        return rowPut(key, value.encode());
    }

    /**
     * Removes the row value mapping for this key from this map if it is present.<p>
     *
     * Returns the value to which the map previously associated the key, or {@code null} if the map
     * contained no mapping for this key. The map will not contain a String mapping for the
     * specified key once the call returns.
     *
     * @param key key whose mapping is to be removed from the map.
     * @return previous value associated with specified key, or {@code null} if there was no mapping
     * for key.
     *
     * @throws NullPointerException if {@code key} is {@code null}.
     *
     * @see Map#remove(Object)
     */
    public String rowRemove(String key) {
        String resolvedKey = resolveRowKey(key);
        rowMapCache.clear();
        return rowMap.remove(resolvedKey);
    }

    // Overriding Object Behavior *********************************************
    /**
     * Returns a string representation of this LayoutMap that lists the column and row associations.
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer(super.toString());
        buffer.append("\n  Column associations:");
        for (Entry entry : columnMap.entrySet()) {
            buffer.append("\n    ");
            buffer.append(entry.getKey());
            buffer.append("->");
            buffer.append(entry.getValue());
        }
        buffer.append("\n  Row associations:");
        for (Entry entry : rowMap.entrySet()) {
            buffer.append("\n    ");
            buffer.append(entry.getKey());
            buffer.append("->");
            buffer.append(entry.getValue());
        }
        return buffer.toString();
    }

    // String Expansion *******************************************************
    String expand(String expression, boolean horizontal) {
        int cursor = 0;
        int start = expression.indexOf(LayoutMap.VARIABLE_PREFIX_CHAR, cursor);
        if (start == -1) { // No variables
            return expression;
        }
        StringBuffer buffer = new StringBuffer();
        do {
            buffer.append(expression.substring(cursor, start));
            String variableName = nextVariableName(expression, start);
            buffer.append(expansion(variableName, horizontal));
            cursor = start + variableName.length() + 1;
            start = expression.indexOf(LayoutMap.VARIABLE_PREFIX_CHAR, cursor);
        } while (start != -1);
        buffer.append(expression.substring(cursor));
        return buffer.toString();
    }

    private static String nextVariableName(String expression, int start) {
        int length = expression.length();
        if (length <= start) {
            FormSpecParser.fail(expression, start, "Missing variable name after variable char '$'.");
        }
        if (expression.charAt(start + 1) == '{') {
            int end = expression.indexOf('}', start + 1);
            if (end == -1) {
                FormSpecParser.fail(expression, start, "Missing closing brace '}' for variable.");
            }
            return expression.substring(start + 1, end + 1);
        }
        int end = start + 1;
        while (end < length
                && Character.isUnicodeIdentifierPart(expression.charAt(end))) {
            end++;
        }
        return expression.substring(start + 1, end);
    }

    private String expansion(String variableName, boolean horizontal) {
        String key = stripBraces(variableName);
        String expansion = horizontal ? columnGet(key) : rowGet(key);
        if (expansion == null) {
            String orientation = horizontal ? "column" : "row";
            throw new IllegalArgumentException("Unknown " + orientation + " layout variable \"" + key + "\"");
        }
        return expansion;
    }

    private static String stripBraces(String variableName) {
        return variableName.charAt(0) == '{'
                ? variableName.substring(1, variableName.length() - 1)
                : variableName;
    }

    // Helper Code ************************************************************
    private static String resolveColumnKey(String key) {
        checkNotNull(key, "The column key must not be null.");
        String lowercaseKey = key.toLowerCase(Locale.ENGLISH);
        String defaultKey = COLUMN_ALIASES.get(lowercaseKey);
        return defaultKey == null ? lowercaseKey : defaultKey;
    }

    private static String resolveRowKey(String key) {
        checkNotNull(key, "The row key must not be null.");
        String lowercaseKey = key.toLowerCase(Locale.ENGLISH);
        String defaultKey = ROW_ALIASES.get(lowercaseKey);
        return defaultKey == null ? lowercaseKey : defaultKey;
    }

    private static LayoutMap createRoot() {
        LayoutMap map = new LayoutMap(null);

        // Column variables
        map.columnPut(
                "label-component-gap",
                new String[]{"lcg", "lcgap"},
                FormSpecs.LABEL_COMPONENT_GAP_COLSPEC);
        map.columnPut(
                "related-gap",
                new String[]{"rg", "rgap"},
                FormSpecs.RELATED_GAP_COLSPEC);
        map.columnPut(
                "unrelated-gap",
                new String[]{"ug", "ugap"},
                FormSpecs.UNRELATED_GAP_COLSPEC);
        map.columnPut(
                "button",
                new String[]{"b"},
                FormSpecs.BUTTON_COLSPEC);
        map.columnPut(
                "growing-button",
                new String[]{"gb"},
                FormSpecs.GROWING_BUTTON_COLSPEC);
        map.columnPut(
                "dialog-margin",
                new String[]{"dm", "dmargin"},
                ColumnSpec.createGap(LayoutStyle.getCurrent().getDialogMarginX()));
        map.columnPut(
                "tabbed-dialog-margin",
                new String[]{"tdm", "tdmargin"},
                ColumnSpec.createGap(LayoutStyle.getCurrent().getTabbedDialogMarginX()));
        map.columnPut(
                "glue",
                FormSpecs.GLUE_COLSPEC.toShortString());

        // Row variables
        map.rowPut(
                "label-component-gap",
                new String[]{"lcg", "lcgap"},
                FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC);
        map.rowPut(
                "related-gap",
                new String[]{"rg", "rgap"},
                FormSpecs.RELATED_GAP_ROWSPEC);
        map.rowPut(
                "unrelated-gap",
                new String[]{"ug", "ugap"},
                FormSpecs.UNRELATED_GAP_ROWSPEC);
        map.rowPut(
                "narrow-line-gap",
                new String[]{"nlg", "nlgap"},
                FormSpecs.NARROW_LINE_GAP_ROWSPEC);
        map.rowPut(
                "line-gap",
                new String[]{"lg", "lgap"},
                FormSpecs.LINE_GAP_ROWSPEC);
        map.rowPut(
                "paragraph-gap",
                new String[]{"pg", "pgap"},
                FormSpecs.PARAGRAPH_GAP_ROWSPEC);
        map.rowPut(
                "dialog-margin",
                new String[]{"dm", "dmargin"},
                RowSpec.createGap(LayoutStyle.getCurrent().getDialogMarginY()));
        map.rowPut(
                "tabbed-dialog-margin",
                new String[]{"tdm", "tdmargin"},
                RowSpec.createGap(LayoutStyle.getCurrent().getTabbedDialogMarginY()));
        map.rowPut(
                "button",
                new String[]{"b"},
                FormSpecs.BUTTON_ROWSPEC);
        map.rowPut(
                "glue",
                FormSpecs.GLUE_ROWSPEC);

        return map;
    }

    private void columnPut(String key, String[] aliases, ColumnSpec value) {
        ensureLowerCase(key);
        columnPut(key, value);
        for (String aliase : aliases) {
            ensureLowerCase(aliase);
            COLUMN_ALIASES.put(aliase, key);
        }
    }

    private void rowPut(String key, String[] aliases, RowSpec value) {
        ensureLowerCase(key);
        rowPut(key, value);
        for (String aliase : aliases) {
            ensureLowerCase(aliase);
            ROW_ALIASES.put(aliase, key);
        }
    }

    private static void ensureLowerCase(String str) {
        String lowerCase = str.toLowerCase(Locale.ENGLISH);
        if (!lowerCase.equals(str)) {
            throw new IllegalArgumentException(
                    "The string \"" + str + "\" should be lower case.");
        }
    }

}
