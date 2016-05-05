package com.github.lgooddatepicker.ysandbox;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.TreeSet;

/**
 * testStart, This is a class used to test various functions while programming. This class is not
 * involved with the normal operation of the date pickers.
 */
public class GetAllLanguages {

    public static void main(String[] args) {
        TreeSet<String> languageCodes = new TreeSet<String>();
        for (Locale locale : Locale.getAvailableLocales()) {
            languageCodes.add(locale.getLanguage());

        }

        LocalTime localTime = LocalTime.of(17, 30, 20);

        for (String languageCode : languageCodes) {

            Locale localeForLanguage = new Locale(languageCode);
            // Locale localeForLanguage = Locale.forLanguageTag(languageCode);
            DateTimeFormatter format = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(localeForLanguage);
            System.out.print(localeForLanguage.getDisplayLanguage() + ": ");
            System.out.print(format.format(localTime) + "\n");
        }

    }
}
