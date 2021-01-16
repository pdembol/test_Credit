package com.pd.testCredit.core.utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Class which helps getting messages from bundle with parameters
 */
public class MessagesUtils {
    private static final String BUNDLE_NAME = "messages";

    public static String msg(String key, Object... args) {
        try {
            String value = getResourceBundle().getString(key);
            return MessageFormat.format(value, args);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String msg(String key) {
        try {
            return getResourceBundle().getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    private static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle(BUNDLE_NAME);
    }
}
