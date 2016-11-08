package com.github.lgooddatepicker.durationpicker_underconstruction;

import com.github.lgooddatepicker.zinternaltools.InternalUtilities;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 */
public class DurationConverter {

    /**
     *
     * Note: This function will return null if the string cannot be parsed for any reason.
     */
    static public Duration convertStringToDuration(String text, DurationConverterSettings settings) {
        if (text == null) {
            return null;
        }
        text = text.trim().toLowerCase();
        if (text.isEmpty()) {
            return null;
        }
        if (!text.contains(" ")) {
            for (int i = 0; i < text.length(); ++i) {
                char currentChar = text.charAt(i);
                if (!((Character.isDigit(currentChar)) || (currentChar == '.'))) {
                    text = InternalUtilities.safeSubstring(text, 0, i) + " "
                            + InternalUtilities.safeSubstring(text, i, text.length());
                    break;
                }
            }
        }
        String[] parts = text.split(" ");
        if (parts.length != 2) {
            return null;
        }
        String valueText = parts[0];
        String unitsText = parts[1];
        BigDecimal value;
        try {
            value = new BigDecimal(valueText);
        } catch (Exception e) {
            return null;
        }
        HashMap<DurationUnit, ArrayList<String>> parsingPrefixes
                = settings.translationsParsingPrefixes;

        for (String prefix : parsingPrefixes.get(DurationUnit.Year)) {
            if (unitsText.startsWith(prefix)) {
                value = value.multiply(new BigDecimal(DurationUnit.Year.inSeconds));
                return Duration.ofSeconds(value.longValue());
            }
        }
        // Note: Months must be parsed before minutes, otherwise the month prefixes will conflict
        // with the minute prefixes.
        for (String prefix : parsingPrefixes.get(DurationUnit.Month)) {
            if (unitsText.startsWith(prefix)) {
                value = value.multiply(new BigDecimal(DurationUnit.Month.inSeconds));
                return Duration.ofSeconds(value.longValue());
            }
        }
        for (String prefix : parsingPrefixes.get(DurationUnit.Week)) {
            if (unitsText.startsWith(prefix)) {
                value = value.multiply(new BigDecimal(DurationUnit.Week.inSeconds));
                return Duration.ofSeconds(value.longValue());
            }
        }
        for (String prefix : parsingPrefixes.get(DurationUnit.Day)) {
            if (unitsText.startsWith(prefix)) {
                value = value.multiply(new BigDecimal(DurationUnit.Day.inSeconds));
                return Duration.ofSeconds(value.longValue());
            }
        }
        for (String prefix : parsingPrefixes.get(DurationUnit.Hour)) {
            if (unitsText.startsWith(prefix)) {
                value = value.multiply(new BigDecimal(DurationUnit.Hour.inSeconds));
                return Duration.ofSeconds(value.longValue());
            }
        }
        for (String prefix : parsingPrefixes.get(DurationUnit.Minute)) {
            if (unitsText.startsWith(prefix)) {
                value = value.multiply(new BigDecimal(DurationUnit.Minute.inSeconds));
                return Duration.ofSeconds(value.longValue());
            }
        }
        for (String prefix : parsingPrefixes.get(DurationUnit.Second)) {
            if (unitsText.startsWith(prefix)) {
                value = value.multiply(new BigDecimal(DurationUnit.Second.inSeconds));
                BigDecimal fractionalPartInNanos = value.remainder(BigDecimal.ONE);
                fractionalPartInNanos = fractionalPartInNanos.multiply(new BigDecimal(1000000000));
                Duration result = Duration.ofSeconds(value.longValue());
                result = result.plusNanos(fractionalPartInNanos.longValue());
                return result;
            }
        }
        return null;
    }

    /**
     * This function will return an empty string if the duration is null or less than zero. This
     * function will never return null.
     */
    static public String convertStringFromDuration(
            Duration duration, DurationConverterSettings settings) {
        if ((duration == null) || (duration.isNegative())) {
            return "";
        }
        long seconds = duration.getSeconds();

        HashSet<DurationUnit> usedUnits
                = getUsedDurationUnitSet(settings.smallestUsedUnit, settings.largestUsedUnit);
        if (usedUnits.isEmpty()) {
            throw new RuntimeException("convertStringFromDuration(), The \"used duration unit\" "
                    + "variables do not allow any units to be used.");
        }
        boolean hasNoNanos = (duration.getNano() == 0);

        DurationUnit smallestUnit = settings.smallestUsedUnit;
        HashMap<DurationUnit, String> unitsSingular = settings.translationsUnitsSingular;
        HashMap<DurationUnit, String> unitsPlural = settings.translationsUnitsPlural;
        HashMap<DurationUnit, Boolean> pluralRules = settings.pluralUnitsMap;
        int oneYear = DurationUnit.Year.inSeconds;
        int oneMonth = DurationUnit.Month.inSeconds;
        int oneWeek = DurationUnit.Week.inSeconds;
        int oneDay = DurationUnit.Day.inSeconds;
        int oneHour = DurationUnit.Hour.inSeconds;
        int oneMinute = DurationUnit.Minute.inSeconds;
        int thirtyMinutes = DurationUnit.Second.thirtyMinutesInSeconds;

        String result = "";
        long value;
        boolean yearIsBestChoice = usedUnits.contains(DurationUnit.Year) && hasNoNanos
                && (seconds >= oneYear) && (seconds % oneYear == 0);
        if (yearIsBestChoice || (DurationUnit.Year == smallestUnit)) {
            value = seconds / oneYear;
            result += value + " ";
            result += ((pluralRules.get(DurationUnit.Year) && (value != 1))
                    ? unitsPlural.get(DurationUnit.Year) : unitsSingular.get(DurationUnit.Year));
            return result;
        }
        boolean monthIsBestChoice = usedUnits.contains(DurationUnit.Month) && hasNoNanos
                && (seconds >= oneMonth) && (seconds % oneMonth == 0);
        if (monthIsBestChoice || (DurationUnit.Month == smallestUnit)) {
            value = seconds / oneMonth;
            result += value + " ";
            result += ((pluralRules.get(DurationUnit.Month) && (value != 1))
                    ? unitsPlural.get(DurationUnit.Month) : unitsSingular.get(DurationUnit.Month));
            return result;
        }
        boolean weekIsBestChoice = usedUnits.contains(DurationUnit.Week) && hasNoNanos
                && (seconds >= oneWeek) && (seconds % oneWeek == 0);
        if (weekIsBestChoice || (DurationUnit.Week == smallestUnit)) {
            value = seconds / oneWeek;
            result += value + " ";
            result += ((pluralRules.get(DurationUnit.Week) && (value != 1))
                    ? unitsPlural.get(DurationUnit.Week) : unitsSingular.get(DurationUnit.Week));
            return result;
        }
        boolean dayIsBestChoice = usedUnits.contains(DurationUnit.Day) && hasNoNanos
                && (seconds >= oneDay) && (seconds % oneDay == 0);
        if (dayIsBestChoice || (DurationUnit.Day == smallestUnit)) {
            value = seconds / oneDay;
            result += value + " ";
            result += ((pluralRules.get(DurationUnit.Day) && (value != 1))
                    ? unitsPlural.get(DurationUnit.Day) : unitsSingular.get(DurationUnit.Day));
            return result;
        }

        boolean hourDecimalIsBestChoice = settings.hoursCanUseThirtyMinuteDecimals
                && usedUnits.contains(DurationUnit.Hour) && hasNoNanos
                && (seconds >= oneHour) && (seconds <= settings.hoursMaximumValueForDecimalsInSeconds)
                && (seconds % thirtyMinutes == 0) && (seconds % oneHour != 0);
        // Note, hourDecimal is never forced to be used as the "smallest used unit".
        if (hourDecimalIsBestChoice) {
            value = seconds / oneHour;
            // The full value is always "X.5" hour(s), due to the hourDecimalIsBestChoice criteria. 
            String decimalString = ".5";
            result += value + decimalString + " ";
            result += ((pluralRules.get(DurationUnit.Hour))
                    ? unitsPlural.get(DurationUnit.Hour) : unitsSingular.get(DurationUnit.Hour));
            return result;
        }

        boolean hourIntegerIsBestChoice = usedUnits.contains(DurationUnit.Hour) && hasNoNanos
                && (seconds >= oneHour) && (seconds % oneHour == 0);
        if (hourIntegerIsBestChoice || (DurationUnit.Hour == smallestUnit)) {
            value = seconds / oneHour;
            result += value + " ";
            result += ((pluralRules.get(DurationUnit.Hour) && (value != 1))
                    ? unitsPlural.get(DurationUnit.Hour) : unitsSingular.get(DurationUnit.Hour));
            return result;
        }

        boolean minuteIsBestChoice = usedUnits.contains(DurationUnit.Minute) && hasNoNanos
                && (seconds >= oneMinute) && (seconds % oneMinute == 0);
        if (minuteIsBestChoice || (DurationUnit.Minute == smallestUnit)) {
            value = seconds / oneMinute;
            result += value + " ";
            result += ((pluralRules.get(DurationUnit.Minute) && (value != 1))
                    ? unitsPlural.get(DurationUnit.Minute) : unitsSingular.get(DurationUnit.Minute));
            return result;
        }

        boolean secondIsBestChoice = usedUnits.contains(DurationUnit.Second);
        if (secondIsBestChoice || (DurationUnit.Second == smallestUnit)) {
            value = seconds;
            int nanosecondsFromDuration = duration.getNano();
            boolean includeNanoseconds = (nanosecondsFromDuration != 0);
            BigDecimal nanosecondsInSeconds = BigDecimal.valueOf(nanosecondsFromDuration, 9);
            BigDecimal secondsWithNanosAdded = nanosecondsInSeconds.add(new BigDecimal(value));
            secondsWithNanosAdded = secondsWithNanosAdded.stripTrailingZeros();
            result += ((includeNanoseconds) ? secondsWithNanosAdded.toPlainString() : value);
            result += " ";
            result += (pluralRules.get(DurationUnit.Second) && ((value != 1) || (includeNanoseconds)))
                    ? unitsPlural.get(DurationUnit.Second) : unitsSingular.get(DurationUnit.Second);
            return result;
        }
        throw new RuntimeException("convertStringFromDuration(), "
                + "The duration unit could not be chosen. (This should not happen.)");
    }

    private static HashSet<DurationUnit> getUsedDurationUnitSet(
            DurationUnit smallestUnit, DurationUnit largestUnit) {
        HashSet<DurationUnit> result = new HashSet<DurationUnit>();
        for (DurationUnit unit : DurationUnit.values()) {
            if (unit.compareTo(smallestUnit) >= 0 && unit.compareTo(largestUnit) <= 0) {
                result.add(unit);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        DurationConverterSettings settings = new DurationConverterSettings();
        settings.smallestUsedUnit = DurationUnit.Second;
        settings.largestUsedUnit = DurationUnit.Year;
        settings.pluralUnitsMap = settings.getSimplePluralUnitsMap(true);

        String s1 = convertStringFromDuration(null, settings);
        String s2 = convertStringFromDuration(Duration.ofSeconds(-1), settings);
        String s3 = convertStringFromDuration(Duration.ofSeconds(0), settings);
        String s4 = convertStringFromDuration(Duration.ofSeconds(1), settings);
        String s5 = convertStringFromDuration(Duration.ofSeconds(1).plusNanos(2), settings);
        Duration m1 = convertStringToDuration("", settings);
        Duration m2 = convertStringToDuration(" ", settings);
        Duration m3 = convertStringToDuration("gobbldy gook", settings);
        Duration m4 = convertStringToDuration("gobbldy gook 55", settings);
        Duration m5 = convertStringToDuration("5.5g", settings);
        Duration m6 = convertStringToDuration("5.5m", settings);
        Duration m7 = convertStringToDuration("3.62h", settings);
        String ss7 = convertStringFromDuration(m7, settings);
        Duration m8 = convertStringToDuration("5.5d", settings);
        Duration m9 = convertStringToDuration("5.5w", settings);
        for (Duration duration = Duration.ofSeconds(0).plusNanos(0);
                duration.compareTo(Duration.ofDays(1000)) <= 0;
                duration = duration.plusHours(1)) {
            String textDuration = convertStringFromDuration(duration, settings);
            Duration durationParsed = convertStringToDuration(textDuration, settings);
            System.out.println("Duration: " + duration.toString() + ", Text: " + textDuration
                    + ", DurationParsed: " + ((durationParsed == null) ? "null" : durationParsed.toString()));
            if (durationParsed == null || (!durationParsed.equals(duration))) {
                throw new RuntimeException("");
            }
        }
    }

}
