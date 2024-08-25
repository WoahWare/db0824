package com.woahware.demo.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class FieldFormatter {

    // transforms field name (something like finalCharge into: Final charge)
    public static String convertFieldName(String fieldName) {
        if (fieldName.isEmpty()) {      // if fieldName is empty, then return null
            return null;
        }
        String result = convertCamelCaseToNormalCase(fieldName);
        // if after conversion, edge case where we have "Pre ", then we want it to be "Pre-"
        if (result.substring(0, 4).equals("Pre ")) {
            return result.replaceFirst(" ", "-");
        }
        return result;
    }

    // helper method for convertFieldName which does the actual conversion from camelCase into "normal case"
    //      where "normal case" defined as: "finalCharge" -> "Final charge"
    private static String convertCamelCaseToNormalCase(String fieldName) {
        StringBuilder result = new StringBuilder(fieldName.length());
        result.append(Character.toUpperCase(fieldName.charAt(0)));
        // loop through string, detect if next char is Uppercase letter, and insert space + lowercase version of that letter
        for (int i = 1; i < fieldName.length(); i++) {
            char ch = fieldName.charAt(i);
            if (Character.isUpperCase(ch)) {
                result.append(' ');
            }
            result.append(Character.toLowerCase(ch));
        }
        return result.toString();
    }

    // formats field values based on the class type
    public static String formatFieldValue(Field field, Object value) {
        if (value instanceof BigDecimal) {
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
            return currencyFormat.format(value);
        } else if (value instanceof LocalDate) {
            return formatDate(value.toString());
        } else if (value instanceof Integer) {
            if (field.toString().toUpperCase().contains("PERCENT")) {
                return value.toString() + "%";
            }
            return value.toString();
        }
        return String.valueOf(value);
    }

    // helps format dates from one format into another
    public static String formatDate(String dateString) {
        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("MM/dd/yy"); // New format
        String formattedDate;
        try {
            // Reformat if the date is in the original format
            LocalDate date = LocalDate.parse(dateString, originalFormatter);
            formattedDate = date.format(newFormatter);
        } catch (DateTimeParseException e) {
            // If parsing fails, fallback to original value...
            formattedDate = dateString;
        }
        return formattedDate;
    }

}
