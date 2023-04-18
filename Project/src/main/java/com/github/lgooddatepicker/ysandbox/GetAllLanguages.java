/*
 * The MIT License
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
    TreeSet<String> languageCodes = new TreeSet<>();
    for (Locale locale : Locale.getAvailableLocales()) {
      languageCodes.add(locale.getLanguage());
    }

    LocalTime localTime = LocalTime.of(17, 30, 20);

    for (String languageCode : languageCodes) {

      Locale localeForLanguage = new Locale(languageCode);
      // Locale localeForLanguage = Locale.forLanguageTag(languageCode);
      DateTimeFormatter format =
          DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(localeForLanguage);
      System.out.print(localeForLanguage.getDisplayLanguage() + ": ");
      System.out.print(format.format(localTime) + "\n");
    }
  }
}
