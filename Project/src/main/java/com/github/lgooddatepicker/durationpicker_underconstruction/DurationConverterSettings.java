package com.github.lgooddatepicker.durationpicker_underconstruction;

import com.github.lgooddatepicker.zinternaltools.TranslationSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

/**
 *
 */
public class DurationConverterSettings {

    public DurationUnit smallestUsedUnit = DurationUnit.Minute;
    public DurationUnit largestUsedUnit = DurationUnit.Week;
    public HashMap<DurationUnit, Boolean> pluralUnitsMap = getSimplePluralUnitsMap(true);
    public boolean hoursCanUseThirtyMinuteDecimals = true;
    public int hoursMaximumValueForDecimalsInSeconds = 24 * DurationUnit.Hour.inSeconds;

    public HashMap<DurationUnit, String> translationsUnitsSingular;
    public HashMap<DurationUnit, String> translationsUnitsPlural;
    public HashMap<DurationUnit, ArrayList<String>> translationsParsingPrefixes;
    private Locale locale;

    public DurationConverterSettings() {
        this(Locale.getDefault());
    }

    public DurationConverterSettings(Locale locale) {
        initializeSettingsFromLocale(locale);
    }

    private void initializeSettingsFromLocale(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        // Fix a problem where the Hindi locale is not recognized by language alone.
        if ("hi".equals(locale.getLanguage()) && (locale.getCountry().isEmpty())) {
            locale = new Locale("hi", "IN");
        }
        this.locale = locale;

        translationsUnitsSingular = new HashMap<DurationUnit, String>();
        translationsUnitsSingular.put(DurationUnit.Second,
                TranslationSource.getTranslation(locale, "singular.Second", "sec"));
        translationsUnitsSingular.put(DurationUnit.Minute,
                TranslationSource.getTranslation(locale, "singular.Minute", "min"));
        translationsUnitsSingular.put(DurationUnit.Hour,
                TranslationSource.getTranslation(locale, "singular.Hour", "hour"));
        translationsUnitsSingular.put(DurationUnit.Day,
                TranslationSource.getTranslation(locale, "singular.Day", "day"));
        translationsUnitsSingular.put(DurationUnit.Week,
                TranslationSource.getTranslation(locale, "singular.Week", "week"));
        translationsUnitsSingular.put(DurationUnit.Month,
                TranslationSource.getTranslation(locale, "singular.Month", "month"));
        translationsUnitsSingular.put(DurationUnit.Year,
                TranslationSource.getTranslation(locale, "singular.Year", "year"));

        translationsUnitsPlural = new HashMap<DurationUnit, String>();
        translationsUnitsPlural.put(DurationUnit.Second,
                TranslationSource.getTranslation(locale, "plural.Second", "secs"));
        translationsUnitsPlural.put(DurationUnit.Minute,
                TranslationSource.getTranslation(locale, "plural.Minute", "mins"));
        translationsUnitsPlural.put(DurationUnit.Hour,
                TranslationSource.getTranslation(locale, "plural.Hour", "hours"));
        translationsUnitsPlural.put(DurationUnit.Day,
                TranslationSource.getTranslation(locale, "plural.Day", "days"));
        translationsUnitsPlural.put(DurationUnit.Week,
                TranslationSource.getTranslation(locale, "plural.Week", "weeks"));
        translationsUnitsPlural.put(DurationUnit.Month,
                TranslationSource.getTranslation(locale, "plural.Month", "months"));
        translationsUnitsPlural.put(DurationUnit.Year,
                TranslationSource.getTranslation(locale, "plural.Year", "years"));

        translationsParsingPrefixes = new HashMap<>();
        for (DurationUnit unit : DurationUnit.values()) {
            translationsParsingPrefixes.put(unit, new ArrayList<>());
        }
        String[] prefixArray;
        String parseKey = "parsingprefixlist.";
        prefixArray = TranslationSource.getTranslation(locale, parseKey + "Second", "s").split(";");
        translationsParsingPrefixes.get(DurationUnit.Second).addAll(Arrays.asList(prefixArray));
        prefixArray = TranslationSource.getTranslation(locale, parseKey + "Minute", "m").split(";");
        translationsParsingPrefixes.get(DurationUnit.Minute).addAll(Arrays.asList(prefixArray));
        prefixArray = TranslationSource.getTranslation(locale, parseKey + "Hour", "h").split(";");
        translationsParsingPrefixes.get(DurationUnit.Hour).addAll(Arrays.asList(prefixArray));
        prefixArray = TranslationSource.getTranslation(locale, parseKey + "Day", "d").split(";");
        translationsParsingPrefixes.get(DurationUnit.Day).addAll(Arrays.asList(prefixArray));
        prefixArray = TranslationSource.getTranslation(locale, parseKey + "Week", "w").split(";");
        translationsParsingPrefixes.get(DurationUnit.Week).addAll(Arrays.asList(prefixArray));
        prefixArray = TranslationSource.getTranslation(locale, parseKey + "Month", "mo").split(";");
        translationsParsingPrefixes.get(DurationUnit.Month).addAll(Arrays.asList(prefixArray));
        prefixArray = TranslationSource.getTranslation(locale, parseKey + "Year", "y").split(";");
        translationsParsingPrefixes.get(DurationUnit.Year).addAll(Arrays.asList(prefixArray));
    }

    public HashMap<DurationUnit, Boolean> getSimplePluralUnitsMap(boolean settingForAllUnits) {
        HashMap<DurationUnit, Boolean> result = new HashMap<DurationUnit, Boolean>();
        result.put(DurationUnit.Second, settingForAllUnits);
        result.put(DurationUnit.Minute, settingForAllUnits);
        result.put(DurationUnit.Hour, settingForAllUnits);
        result.put(DurationUnit.Day, settingForAllUnits);
        result.put(DurationUnit.Week, settingForAllUnits);
        result.put(DurationUnit.Month, settingForAllUnits);
        result.put(DurationUnit.Year, settingForAllUnits);
        return result;
    }
}
