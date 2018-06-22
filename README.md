# LGoodDatePicker
Java Swing Date Picker. Easy to use, good looking, nice features, and localized. Uses the JSR-310 standard. 

## Project Links:
Demo Application and Downloads in the: [Release Section](https://github.com/LGoodDatePicker/LGoodDatePicker/releases).  
Maven Central repository pages: [Page 1](http://search.maven.org/#search%7Cga%7C1%7CLGoodDatePicker), [Page 2](http://mvnrepository.com/artifact/com.github.lgooddatepicker/LGoodDatePicker).  
Get release updates: <a href="https://feedburner.google.com/fb/a/mailverify?uri=LGoodDatePickerUpdates&amp;loc=en_US">Subscribe for Updates</a>.<br>
Contact the primary developer: [Email Form](http://www.emailmeform.com/builder/form/ZQcYut4393).  
Submit bugs or feature requests: [Issues Section](https://github.com/LGoodDatePicker/LGoodDatePicker/issues).  
General Discussion: [Discussion Thread](https://github.com/LGoodDatePicker/LGoodDatePicker/issues/2).  

## News:

* [LGoodDatePicker 10.4.1](https://github.com/LGoodDatePicker/LGoodDatePicker/releases) was released on June 22, 2018. This version fixes a few minor issues and bugs, and adds a new function DatePickerSettings.setDefaultYearMonth().

* [LGoodDatePicker 10.3.1](https://github.com/LGoodDatePicker/LGoodDatePicker/releases) was released on July 5, 2017. This version adds listener notifications for changes to the YearMonth, and adds the colors for the selected date to the customizable colors in the DatePickerSettings. The pom file was enhanced to allow developers to optionally build the project from source, without first installing the GPG signing tools. This release was added to the [Release Section](https://github.com/LGoodDatePicker/LGoodDatePicker/releases), [Maven Central](http://search.maven.org/#search%7Cga%7C1%7CLGoodDatePicker), and [LGoodDatePicker Backports](https://github.com/LGoodDatePicker/LGoodDatePicker/releases/tag/Backports).

* [LGoodDatePicker 10.2.3](https://github.com/LGoodDatePicker/LGoodDatePicker/releases) was released on May 8, 2017. This version fixes a MacOSX related bug, adds the Norwegian language translation, and adds the option to customize any used font or component color. 

* [LGoodDatePicker 8.2.0](https://github.com/LGoodDatePicker/LGoodDatePicker/releases) was released on November 29, 2016. This version fixes a critical Linux related bug, adds the Bulgarian language translation, and adds to the optional visual customization capabilities. 

* [LGoodDatePicker 7.6.3](https://github.com/LGoodDatePicker/LGoodDatePicker/releases) was added [to Maven Central](http://search.maven.org/#search%7Cga%7C1%7CLGoodDatePicker).

* [LGoodDatePicker 7.5.1](https://github.com/LGoodDatePicker/LGoodDatePicker/releases) was released on June 30, 2016. This release adds support to the library for JTable editors, and JavaBeans data binding.

* The [LGoodDatePicker Backports](https://github.com/LGoodDatePicker/LGoodDatePicker/releases/tag/Backports) were released on May 4, 2016. With these backports, the library can now be used in projects which are built for older Java versions, Java 6 or 7. 

* [LGoodDatePicker 5.2.2](https://github.com/LGoodDatePicker/LGoodDatePicker/releases) was released April 25, 2016. This release adds the CalendarPanel component to the library. This component allows the programmer to (optionally) use the calendar panel controls independently from the DatePicker component. An example for the independent calendar panel was also added to the [demo program](https://github.com/LGoodDatePicker/LGoodDatePicker/releases).

* [LGoodDatePicker 4.3.1](https://github.com/LGoodDatePicker/LGoodDatePicker/releases) was released March 17, 2016. The requirements for publishing to the Maven Central repository were added to the project. (Including jar signing, global package name conventions, Pom file specifications, etc.). The project now has an entry [at Maven Central](http://search.maven.org/#search%7Cga%7C1%7CLGoodDatePicker). This makes the library easier to include in applications that use Apache Maven.

## General Features:
* The included components are the DatePicker, the TimePicker, the DateTimePicker, and the CalendarPanel.
* Provides automatic internationalization.  
(Month names, weekday names, the default first day of the week, default date and time formats, and button text.)
* Translations include 25 languages.
(Arabic, Bulgarian, Chinese, Czech, Danish, Dutch, English, Finnish, French, German, Greek, Hindi, Italian, Indonesian, Japanese, Korean, Norwegian, Polish, Portuguese, Romanian, Russian, Spanish, Swedish, Turkish, Vietnamese.)
* Natively uses the standard Java 8 time package (java.time.LocalDate). This package is also called "JSR-310".<br/>
(Can also run in Java 6 or 7, using the [Parallel Backports](https://github.com/LGoodDatePicker/LGoodDatePicker/releases/tag/Backports).) 
* Supports other legacy and common data types, such as "java.util.Date".
* Compatible with Java IDE visual form designers, including NetBeans, Eclipse, IntelliJ, and the JFormDesigner program.
* The pickers can optionally be added to the cells or columns of a JTable, using the supplied TableEditor classes.
* Customizable visual elements.  
(Including button icons, calendar size, fonts, colors, and the popup border.)
* The TimePicker GUI provides the time selection list, up/down arrow key usage, and optional spinner buttons. 
* Automatic date and time validation on all components.
* Optional highlight policies and veto policies.
* Optional week of the year numbers.
* Can allow or disallow empty values. (Null dates or times.)
* Can optionally disallow keyboard editing. 
* Relatively compact source code.
* Simple usage. Creating a picker requires only one line of code.
* Includes the [Demo Program](https://github.com/LGoodDatePicker/LGoodDatePicker/releases). (Found in the Github release files.)
* Code and usage examples.
* Complete Javadoc documentation.
* Active developer support. 
* Open source MIT license.

## Screenshots:

![Screenshots DatePicker, TimePicker, and DateTimePicker](/Site/ScreenShots/LGoodDatePicker_DatePicker_TimePicker_And_DateTimePicker.png?raw=true "")

![Screenshots DatePicker](/Site/ScreenShots/LGoodDatePicker_Screenshots_1_FullSize.png?raw=true "")

![Screenshots Demo](/Site/ScreenShots/DemoProgramScreenshot1.png?raw=true "") 

![Screenshots TableEditors Demo](/Site/ScreenShots/TableEditorsDemoScreenshot1.png?raw=true "") 
    
## Thanks and acknowledgment to:

[Microba DatePicker](https://github.com/tdbear/microba)  
The basic visual form design of LGoodDatePicker was originally inspired by Microba.
