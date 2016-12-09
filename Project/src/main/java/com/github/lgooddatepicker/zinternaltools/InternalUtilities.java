package com.github.lgooddatepicker.zinternaltools;

import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.DataInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.UIDefaults;
import javax.swing.table.TableCellEditor;

/**
 * InternalUtilities, This class contains static functions that are used by the date picker or the
 * calendar panel. Some of these functions are large, and were separated out of the date picker
 * class or calendar panel class in order to improve the readability of those classes.
 */
public class InternalUtilities {

    /**
     * areObjectsEqual, This function exists as a workaround for the fact that Objects.equals() did
     * not exist in Java 1.6.
     *
     * Returns {@code true} if the arguments are equal to each other and {@code false} otherwise.
     * Consequently, if both arguments are {@code null}, {@code true} is returned and if exactly one
     * argument is {@code null}, {@code
     * false} is returned. Otherwise, equality is determined by using the
     * {@link Object#equals equals} method of the first argument.
     *
     * @param a an object
     * @param b an object to be compared with {@code a} for equality
     * @return {@code true} if the arguments are equal to each other and {@code false} otherwise
     * @see Object#equals(Object)
     */
    public static boolean areObjectsEqual(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    /**
     * doesParsedDateMatchText, This compares the numbers in a parsed date, to the original text
     * from which the date was parsed. Specifically this compares the day of the month and the year
     * of the parsed date to the text. On a technical note, this function is not aware of which
     * field is which, but that ambiguity does not prevent it from performing a successful
     * comparison on all tested cases. This will return true if the dates are a match, or otherwise
     * return false.
     *
     * Testing note: This function has been thoroughly tested and gives the proper result with all
     * valid and invalid dates in the years between -10000 and 10000 inclusive. Valid dates are
     * defined as those dates that are returned from the LocalDate class, when using the
     * localDate.plusDays(1) function. Invalid dates are defined as any of the following: The 31st
     * day of February, April, June, September, or November. The 30th day of February. Or the 29th
     * day of February on any year that is not a leap year.
     */
    static public boolean doesParsedDateMatchText(LocalDate parsedDate, String text,
            Locale formatLocale) {
        if (parsedDate == null || text == null) {
            return false;
        }
        text = text.toLowerCase();
        // This only matches numbers, and it does not include any hyphen "-".
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);
        ArrayList<String> unsignedNumbersFound = new ArrayList<String>();
        while (matcher.find()) {
            String foundString = matcher.group();
            foundString = forceNumberStringToTwoDigits(foundString);
            unsignedNumbersFound.add(foundString);
        }
        String parsedDayOfMonth = "" + parsedDate.getDayOfMonth();
        parsedDayOfMonth = forceNumberStringToTwoDigits(parsedDayOfMonth);
        boolean dayOfMonthFound = unsignedNumbersFound.remove(parsedDayOfMonth);

        DateTimeFormatter formatBC = DateTimeFormatter.ofPattern("G", formatLocale);
        String eraBCString = LocalDate.of(-100, 1, 1).format(formatBC).toLowerCase();
        if (parsedDate.getYear() < 1 && text.contains(eraBCString)) {
            String parsedYearForBC = "" + (parsedDate.getYear() - 1);
            parsedYearForBC = parsedYearForBC.replace("-", "");
            parsedYearForBC = forceNumberStringToTwoDigits(parsedYearForBC);
            boolean yearFoundForBC = unsignedNumbersFound.remove(parsedYearForBC);
            return yearFoundForBC && dayOfMonthFound;
        } else {
            String parsedYear = "" + parsedDate.getYear();
            parsedYear = parsedYear.replace("-", "");
            parsedYear = forceNumberStringToTwoDigits(parsedYear);
            boolean yearFound = unsignedNumbersFound.remove(parsedYear);
            return yearFound && dayOfMonthFound;
        }
    }

    /**
     * getJavaRunningVersionAsDouble, Returns a double with the currently running java version.
     */
    public static double getJavaRunningVersionAsDouble() {
        String version = System.getProperty("java.version");
        int pos = version.indexOf('.');
        pos = version.indexOf('.', pos + 1);
        return Double.parseDouble(version.substring(0, pos));
    }

    /**
     * getJavaRunningVersionAsString, Returns a string with the currently running java version.
     */
    public static String getJavaRunningVersionAsString() {
        String version = System.getProperty("java.version");
        return version;
    }

    /**
     * getJavaTargetVersionFromPom, Returns a string with the java "target" version, as it was
     * specified in the pom file at compile time.
     */
    public static String getJavaTargetVersionFromPom() {
        try {
            Properties properties = new Properties();
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            properties.load(classLoader.getResourceAsStream("project.properties"));
            return "" + properties.getProperty("targetJavaVersion");
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * getMostCommonElementInList, This returns the most common element in the supplied list. In the
     * event of a tie, any element that is tied as the "largest element" may be returned. If the
     * list has no elements, or if the list is null, then this will return null. This can also
     * return null if null happens to be the most common element in the source list.
     */
    public static <T> T getMostCommonElementInList(List<T> sourceList) {
        if (sourceList == null || sourceList.isEmpty()) {
            return null;
        }
        Map<T, Integer> hashMap = new HashMap<T, Integer>();
        for (T element : sourceList) {
            Integer countOrNull = hashMap.get(element);
            int newCount = (countOrNull == null) ? 1 : (countOrNull + 1);
            hashMap.put(element, newCount);
        }
        // Find the largest entry. 
        // In the event of a tie, the first entry (the first entry in the hash map, not in the list) 
        // with the maximum count will be returned. 
        Entry<T, Integer> largestEntry = null;
        for (Entry<T, Integer> currentEntry : hashMap.entrySet()) {
            if (largestEntry == null || currentEntry.getValue() > largestEntry.getValue()) {
                largestEntry = currentEntry;
            }
        }
        T result = (largestEntry == null) ? null : largestEntry.getKey();
        return result;
    }

    /**
     * getProjectVersionString, Returns a string with the project version number.
     */
    public static String getProjectVersionString() {
        try {
            Properties properties = new Properties();
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            properties.load(classLoader.getResourceAsStream("project.properties"));
            return "v" + properties.getProperty("projectVersion");
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * getScreenInsets, This returns the insets of the screen, which are defined by any task bars
     * that have been set up by the user. This function accounts for multi-monitor setups. If a
     * window is supplied, then the the monitor that contains the window will be used. If a window
     * is not supplied, then the primary monitor will be used.
     */
    static public Insets getScreenInsets(Window windowOrNull) {
        Insets insets;
        if (windowOrNull == null) {
            insets = Toolkit.getDefaultToolkit().getScreenInsets(GraphicsEnvironment
                    .getLocalGraphicsEnvironment().getDefaultScreenDevice()
                    .getDefaultConfiguration());
        } else {
            insets = windowOrNull.getToolkit().getScreenInsets(
                    windowOrNull.getGraphicsConfiguration());
        }
        return insets;
    }

    /**
     * getScreenTotalArea, This returns the total area of the screen. (The total area includes any
     * task bars.) This function accounts for multi-monitor setups. If a window is supplied, then
     * the the monitor that contains the window will be used. If a window is not supplied, then the
     * primary monitor will be used.
     */
    static public Rectangle getScreenTotalArea(Window windowOrNull) {
        Rectangle bounds;
        if (windowOrNull == null) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            bounds = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
        } else {
            GraphicsConfiguration gc = windowOrNull.getGraphicsConfiguration();
            bounds = gc.getBounds();
        }
        return bounds;
    }

    /**
     * getScreenWorkingArea, This returns the working area of the screen. (The working area excludes
     * any task bars.) This function accounts for multi-monitor setups. If a window is supplied,
     * then the the monitor that contains the window will be used. If a window is not supplied, then
     * the primary monitor will be used.
     */
    static public Rectangle getScreenWorkingArea(Window windowOrNull) {
        Insets insets;
        Rectangle bounds;
        if (windowOrNull == null) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            insets = Toolkit.getDefaultToolkit().getScreenInsets(ge.getDefaultScreenDevice()
                    .getDefaultConfiguration());
            bounds = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
        } else {
            GraphicsConfiguration gc = windowOrNull.getGraphicsConfiguration();
            insets = windowOrNull.getToolkit().getScreenInsets(gc);
            bounds = gc.getBounds();
        }
        bounds.x += insets.left;
        bounds.y += insets.top;
        bounds.width -= (insets.left + insets.right);
        bounds.height -= (insets.top + insets.bottom);
        return bounds;
    }

    /**
     * forceNumberStringToTwoDigits, This takes a string of digits, and forces it to be two digits
     * long. First any extra leftmost digits are truncated, leaving only the right one digit or
     * right two digits. If there is only one digit, it is zero padded to enforce a two digit string
     * result. This function is used by the DatePickerUtilities.doesParsedDateMatchText() function.
     */
    private static String forceNumberStringToTwoDigits(String text) {
        while (text.length() < 2) {
            text = "0" + text;
        }
        if (text.length() > 2) {
            text = text.substring(text.length() - 2, text.length());
        }
        return text;
    }

    /**
     * generateDefaultFormatterCE, This returns a default formatter for the specified locale, that
     * can be used for displaying or parsing AD dates. The formatter is generated from the default
     * FormatStyle.LONG formatter in the specified locale.
     */
    public static DateTimeFormatter generateDefaultFormatterCE(Locale pickerLocale) {
        DateTimeFormatter formatCE = new DateTimeFormatterBuilder().parseLenient().
                parseCaseInsensitive().appendLocalized(FormatStyle.LONG, null).
                toFormatter(pickerLocale);
        // Get the local language as a string.
        String language = pickerLocale.getLanguage();
        // Override the format for the turkish locale to remove the name of the weekday.
        if ("tr".equals(language)) {
            formatCE = PickerUtilities.createFormatterFromPatternString(
                    "dd MMMM yyyy", pickerLocale);
        }
        // Return the generated format.
        return formatCE;
    }

    /**
     * generateDefaultFormatterBCE, This returns a default formatter for the specified locale, that
     * can be used for displaying or parsing BC dates. The formatter is generated from the default
     * FormatStyle.LONG formatter in the specified locale. The resulting format is intended to be
     * nearly identical to the default formatter used for AD dates.
     */
    public static DateTimeFormatter generateDefaultFormatterBCE(Locale pickerLocale) {
        // This is verified to work for the following locale languages:
        // en, de, fr, pt, ru, it, nl, es, pl, da, ro, sv, zh.
        String displayFormatterBCPattern = DateTimeFormatterBuilder.getLocalizedDateTimePattern(
                FormatStyle.LONG, null, IsoChronology.INSTANCE, pickerLocale);
        displayFormatterBCPattern = displayFormatterBCPattern.replace("y", "u");
        // Note: We could have used DateUtilities.createFormatterFromPatternString(), which should
        // have the same formatter options as this line. We kept this code independent in case
        // anyone ever mistakenly changes that utility function.
        DateTimeFormatter displayFormatterBC = new DateTimeFormatterBuilder().parseLenient()
                .parseCaseInsensitive().appendPattern(displayFormatterBCPattern)
                .toFormatter(pickerLocale);
        // Get the local language as a string.
        String language = pickerLocale.getLanguage();
        // Override the format for the turkish locale to remove the name of the weekday.
        if ("tr".equals(language)) {
            displayFormatterBC = PickerUtilities.createFormatterFromPatternString(
                    "dd MMMM uuuu", pickerLocale);
        }
        return displayFormatterBC;
    }

    /**
     * getParsedDateOrNull, This takes text from the date picker text field, and tries to parse it
     * into a java.time.LocalDate instance. If the text cannot be parsed, this will return null.
     *
     * Implementation note: The DateTimeFormatter parsing class was accepting invalid dates like
     * February 31st, and returning the last valid date of the month, like Feb 28. This could be
     * seen as an attempt to be lenient, but in the context of the date picker class it is
     * considered a mistake or a bug. There was no setting to disable that functionality. So, this
     * function calls another function called doesParsedDateMatchText(), to analyze and reject those
     * kinds of mistakes. If the parsed text does not match the day of the month (and year) of the
     * parsed date, then this function will return null.
     */
    static public LocalDate getParsedDateOrNull(String text, DateTimeFormatter displayFormatterAD,
            DateTimeFormatter displayFormatterBC, ArrayList<DateTimeFormatter> parsingFormatters,
            Locale formatLocale) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        text = text.trim().toLowerCase();
        LocalDate parsedDate = null;
        if (parsedDate == null) {
            try {
                parsedDate = LocalDate.parse(text, displayFormatterAD);
            } catch (Exception ex) {
            }
        }
        if (parsedDate == null) {
            try {
                // Note: each parse attempt must have its own try/catch block.
                parsedDate = LocalDate.parse(text, displayFormatterBC);
            } catch (Exception ex) {
            }
        }
        for (int i = 0; ((parsedDate == null) && (i < parsingFormatters.size())); ++i) {
            try {
                parsedDate = LocalDate.parse(text, parsingFormatters.get(i));
            } catch (Exception ex) {
            }
        }
        // Check for any "successfully" parsed but nonexistent dates like Feb 31.
        // Note, this function has been thoroughly tested. See the function docs for details.
        if ((parsedDate != null) && (!InternalUtilities.doesParsedDateMatchText(
                parsedDate, text, formatLocale))) {
            return null;
        }
        return parsedDate;
    }

    public static LocalTime getParsedTimeOrNull(String timeText,
            DateTimeFormatter formatForDisplayTime, DateTimeFormatter formatForMenuTimes,
            ArrayList<DateTimeFormatter> formatsForParsing, Locale timePickerLocale) {
        if (timeText == null || timeText.trim().isEmpty()) {
            return null;
        }
        timeText = timeText.trim().toLowerCase();
        LocalTime parsedTime = null;
        if (parsedTime == null) {
            try {
                parsedTime = LocalTime.parse(timeText, formatForDisplayTime);
            } catch (Exception ex) {
            }
        }
        if (parsedTime == null) {
            try {
                // Note: each parse attempt must have its own try/catch block.
                parsedTime = LocalTime.parse(timeText, formatForMenuTimes);
            } catch (Exception ex) {
            }
        }
        for (int i = 0; ((parsedTime == null) && (i < formatsForParsing.size())); ++i) {
            try {
                parsedTime = LocalTime.parse(timeText, formatsForParsing.get(i));
            } catch (Exception ex) {
            }
        }
        return parsedTime;
    }

    /**
     * capitalizeFirstLetterOfString, This capitalizes the first letter of the supplied string, in a
     * way that is sensitive to the specified locale.
     */
    static String capitalizeFirstLetterOfString(String text, Locale locale) {
        if (text == null || text.length() < 1) {
            return text;
        }
        String textCapitalized = text.substring(0, 1).toUpperCase(locale) + text.substring(1);
        return textCapitalized;
    }

    /**
     * getConstraints, This returns a grid bag constraints object that can be used for placing a
     * component appropriately into a grid bag layout.
     */
    static public GridBagConstraints getConstraints(int gridx, int gridy) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = gridx;
        gc.gridy = gridy;
        return gc;
    }

    /**
     * isDateVetoed, This is a convenience function for checking whether or not a particular date is
     * vetoed. Note that veto policies do not have any say about null dates, so this function always
     * returns false for null dates.
     */
    static public boolean isDateVetoed(DateVetoPolicy policy, LocalDate date) {
        if (policy == null || date == null) {
            return false;
        }
        return (!policy.isDateAllowed(date));
    }

    /**
     * isMouseWithinComponent, This returns true if the mouse is inside of the specified component,
     * otherwise returns false.
     */
    static public boolean isMouseWithinComponent(Component component) {
        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        Rectangle bounds = component.getBounds();
        bounds.setLocation(component.getLocationOnScreen());
        return bounds.contains(mousePos);
    }

    public static boolean isTimeVetoed(TimeVetoPolicy policy, LocalTime time) {
        if (policy == null) {
            return false;
        }
        return (!policy.isTimeAllowed(time));
    }

    /**
     * safeSubstring, This is a version of the substring function which is guaranteed to never throw
     * an exception. If the supplied string is null then this will return null. If the beginIndex or
     * endIndexExclusive are out of range for the string, then the indexes will be compressed to fit
     * within the bounds of the supplied string. If the beginIndex is greater than or equal to
     * endIndexExclusive, then this will return an empty string.
     */
    static public String safeSubstring(String text, int beginIndex, int endIndexExclusive) {
        if (text == null) {
            return null;
        }
        int textLength = text.length();
        if (beginIndex < 0) {
            beginIndex = 0;
        }
        if (endIndexExclusive < 0) {
            endIndexExclusive = 0;
        }
        if (endIndexExclusive > textLength) {
            endIndexExclusive = textLength;
        }
        if (beginIndex > endIndexExclusive) {
            beginIndex = endIndexExclusive;
        }
        if (beginIndex == endIndexExclusive) {
            return "";
        }
        return text.substring(beginIndex, endIndexExclusive);
    }

    /**
     * getCompiledJavaVersionFromJavaClassFile, Given an input stream to a Java class file, this
     * will return the major or minor version of Java that was used to compile the file. In a Maven
     * POM file, this is known as the "target" version of Java that was used to compile the file.
     *
     * Getting an input stream for a class file inside a jar file:
     *
     * InputStream input = getClass().getResourceAsStream("/classpath/to/my/file.class");
     */
    public static int getCompiledJavaVersionFromJavaClassFile(
            InputStream classByteStream, boolean majorVersionRequested)
            throws Exception {
        DataInputStream dataInputStream = new DataInputStream(classByteStream);
        // Skip the "magic number".
        dataInputStream.readInt();
        int minorVersion = dataInputStream.readUnsignedShort();
        int majorVersion = dataInputStream.readUnsignedShort();
        return (majorVersionRequested) ? majorVersion : minorVersion;
    }

    /**
     * getCompiledJavaMajorVersionFromJavaClassFileAsString, Given an input stream to a Java class
     * file, this will return the major version of Java that was used to compile the file (as a
     * string). In a Maven POM file, this is known as the "target" version of Java that was used to
     * compile the file.
     *
     * Getting an input stream for a class file inside a jar file:
     *
     * InputStream input = getClass().getResourceAsStream("/classpath/to/my/file.class");
     */
    public static String getCompiledJavaMajorVersionFromJavaClassFileAsString(
            InputStream classByteStream) throws Exception {
        int majorVersion = getCompiledJavaVersionFromJavaClassFile(classByteStream, true);
        switch (majorVersion) {
            case 50:
                return "Java 6";
            case 51:
                return "Java 7";
            case 52:
                return "Java 8";
            default:
                return "Could not find version string for major version: " + majorVersion;
        }
    }

    /**
     * setDefaultTableEditorsClicks, This sets the number of clicks required to start the default
     * table editors in the supplied table. Typically you would set the table editors to start after
     * 1 click or 2 clicks, as desired.
     *
     * The default table editors of the table editors that are supplied by the JTable class, for
     * Objects, Numbers, and Booleans. Note, the editor which is used to edit Objects, is the same
     * editor used for editing Strings.
     */
    public static void setDefaultTableEditorsClicks(JTable table, int clickCountToStart) {
        TableCellEditor editor;
        editor = table.getDefaultEditor(Object.class);
        if (editor instanceof DefaultCellEditor) {
            ((DefaultCellEditor) editor).setClickCountToStart(clickCountToStart);
        }
        editor = table.getDefaultEditor(Number.class);
        if (editor instanceof DefaultCellEditor) {
            ((DefaultCellEditor) editor).setClickCountToStart(clickCountToStart);
        }
        editor = table.getDefaultEditor(Boolean.class);
        if (editor instanceof DefaultCellEditor) {
            ((DefaultCellEditor) editor).setClickCountToStart(clickCountToStart);
        }
    }

}
