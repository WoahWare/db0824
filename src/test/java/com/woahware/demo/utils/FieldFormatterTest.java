
package com.woahware.demo.utils;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import org.junit.Rule;

import org.junit.jupiter.api.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class FieldFormatterTest {

    @Rule
    public TestRule watcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            System.out.println("Starting Test: " + description.getMethodName() + "()  for:  " + this.getClass().getSimpleName());
        }
    };
    
    @Test
    public void testFormatFieldValue_BigDecimal() throws NoSuchFieldException {
        Field field = TestEntity.class.getDeclaredField("price");
        BigDecimal value = new BigDecimal("1234.56");

        String expected = NumberFormat.getCurrencyInstance(Locale.US).format(value);
        String actual = FieldFormatter.formatFieldValue(field, value);

        assertEquals(expected, actual);
    }

    @Test
    public void testFormatFieldValue_LocalDate() throws NoSuchFieldException {
        Field field = TestEntity.class.getDeclaredField("date");
        LocalDate value = LocalDate.of(2023, 8, 22);

        String expected = "08/22/23"; // Expected formatted date
        String actual = FieldFormatter.formatFieldValue(field, value);

        assertEquals(expected, actual);
    }

    @Test
    public void testFormatFieldValue_IntegerPercent() throws NoSuchFieldException {
        Field field = TestEntity.class.getDeclaredField("discountPercent");
        Integer value = 15;

        String expected = "15%";
        String actual = FieldFormatter.formatFieldValue(field, value);

        assertEquals(expected, actual);
    }

    @Test
    public void testFormatFieldValue_Integer() throws NoSuchFieldException {
        Field field = TestEntity.class.getDeclaredField("quantity");
        Integer value = 100;

        String expected = "100";
        String actual = FieldFormatter.formatFieldValue(field, value);

        assertEquals(expected, actual);
    }

    @Test
    public void testFormatFieldValue_Other() throws NoSuchFieldException {
        Field field = TestEntity.class.getDeclaredField("name");
        String value = "Test Tool";

        String expected = "Test Tool";
        String actual = FieldFormatter.formatFieldValue(field, value);

        assertEquals(expected, actual);
    }

    @Test
    public void testFormatDate_ValidDate() {
        String dateString = "2023-08-22";
        String expected = "08/22/23";

        String actual = FieldFormatter.formatDate(dateString);

        assertEquals(expected, actual);
    }

    @Test
    public void testFormatDate_InvalidDate() {
        String invalidDateString = "22/08/2023";    // Invalid format

        String actual = FieldFormatter.formatDate(invalidDateString);

        assertEquals(invalidDateString, actual);
    }

    @Test
    public void testFormatDate_EmptyString() {
        String emptyDateString = "";

        String actual = FieldFormatter.formatDate(emptyDateString);

        assertEquals(emptyDateString, actual);
    }
    
    @Test
    public void testConvertFieldNameCamelCase() {
        String fieldName = "toolCode";
        String expected = "Tool code";

        String actual = FieldFormatter.convertFieldName(fieldName);

        assertEquals(expected, actual);
    }

    @Test
    public void testConvertFieldNamePascalCase() {
        String fieldName = "ToolCode";
        String expected = "Tool code";

        String actual = FieldFormatter.convertFieldName(fieldName);

        assertEquals(expected, actual);
    }
    
    @Test
    public void testConvertFieldNameCamelCaseThreeWords() {
        String fieldName = "checkOutDate";
        String expected = "Check out date";

        String actual = FieldFormatter.convertFieldName(fieldName);

        assertEquals(expected, actual);
    }
    
    @Test
    public void testConvertFieldNameCamelCasePre() {
        String fieldName = "preDiscountCharge";
        String expected = "Pre-discount charge";

        String actual = FieldFormatter.convertFieldName(fieldName);

        assertEquals(expected, actual);
    }
    
    @Test
    public void testConvertFieldNameEmptyString() {
        String fieldName = "";
        String expected = null;

        String actual = FieldFormatter.convertFieldName(fieldName);

        assertEquals(expected, actual);
    }

    
    // FieldFormatter classes or mock entity for testing
    private static class TestEntity {
        private BigDecimal price;
        private LocalDate date;
        private Integer discountPercent;
        private Integer quantity;
        private String name;
    }
}

