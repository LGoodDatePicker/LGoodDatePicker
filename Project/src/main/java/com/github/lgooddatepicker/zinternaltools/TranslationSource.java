package com.github.lgooddatepicker.zinternaltools;

import java.util.Locale;
import java.util.Properties;

/**
 * TranslationSource, This class has static functions that are used to provide translations of text
 * in the date pickers. This class accesses the "TranslationResources.properties" file to get its
 * translations.
 */
public class TranslationSource {

    /**
     * translationResources, This holds the translation properties. This variable is only loaded
     * once, the first time that it is needed.
     */
    static private Properties translationResources;

    /**
     * propertiesFileName, This holds the name of the properties file.
     */
    static final private String propertiesFileName = "TranslationResources.properties";

    /**
     * getTranslation, This returns a local language translation for the text that is represented by
     * the specified key. The supplied locale is used to indicate the desired language. If a
     * translation cannot be found, then the default text will be returned instead.
     */
    public static String getTranslation(Locale locale, String key, String defaultText) {
        initializePropertiesIfNeeded();
        String language = locale.getLanguage();
        if (language == null || language.isEmpty()) {
            return defaultText;
        }
        String propertyKey = language + ".text." + key;
        String result = translationResources.getProperty(propertyKey, defaultText);
        return result;
    }

    /**
     * initializePropertiesIfNeeded, If needed, this will initialize the translation properties. If
     * the translation properties have already been initialized, then this will do nothing.
     */
    private static void initializePropertiesIfNeeded() {
        if (translationResources != null) {
            return;
        }
        String lastException = "";
        try {
            translationResources = new Properties();
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            translationResources.load(classLoader.getResourceAsStream(
                    propertiesFileName));
        } catch (Exception exception) {
            lastException = exception.toString() + "\n----\n" + exception.getMessage();
        }
        if (translationResources == null) {
            throw new RuntimeException("TranslationSource.initializePropertiesIfNeeded()"
                    + "Could not load TranslationResources.properties file.\n"
                    + "The exception follows:\n" + lastException);
        }
    }

}
