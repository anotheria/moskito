package net.anotheria.moskito.core.decorators.value;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class DoubleValueAOTest {


    @Test
    public void nANIsFormattedCorrectly() {
        assertFormatting(Double.NaN, "0.000");
    }

    @Test
    public void negativeInfinityIsFormattedCorrectly() {
        assertFormatting(Double.NEGATIVE_INFINITY, "-\u221e");
    }

    @Test
    public void positiveInfinityIsFormattedCorrectly() {
        assertFormatting(Double.POSITIVE_INFINITY, "\u221e");
    }

    @Test
    public void zeroIsFormattedCorrectly1() {
        assertFormatting(0.0, "0.000");
    }

    @Test
    public void zeroIsFormattedCorrectly2() {
        assertFormatting(-0.0, "0.000");
    }

    @Test
    public void wholeNumberIsFormattedCorrectly() {
        assertFormatting(42.0, "42.000");
    }

    @Test
    public void smallPositiveDoubleIsFormattedCorrectly() {
        assertFormatting(.123456789, "0.123");
    }

    @Test
    public void smallNegativeDoubleIsFormattedCorrectly() {
        assertFormatting(-.123456789, "-0.123");
    }

    @Test
    public void veryShortPositiveDoubleIsFormattedCorrectly7() {
        assertFormatting(Double.MIN_VALUE, "0.000");
    }

    @Test
    public void shortPositiveDoubleIsFormattedCorrectly1() {
        assertFormatting(1.0, "1.000");
    }

    @Test
    public void shortPositiveDoubleIsFormattedCorrectly2() {
        assertFormatting(47.11, "47.110");
    }

    @Test
    public void longPositiveDoubleIsFormattedCorrectly1() {
        assertFormatting(1.23456789, "1.235");
    }

    @Test
    public void longPositiveDoubleIsFormattedCorrectly2() {
        assertFormatting(12.3456789, "12.346");
    }

    @Test
    public void longPositiveDoubleIsFormattedCorrectly3() {
        assertFormatting(123.456789, "123.457");
    }

    @Test
    public void longPositiveDoubleIsFormattedCorrectly4() {
        assertFormatting(12345.6789, "12345.679");
    }

    @Test
    public void longPositiveDoubleIsFormattedCorrectly5() {
        assertFormatting(123456.789, "123456.789");
    }

    @Test
    public void longPositiveDoubleIsFormattedCorrectly6() {
        assertFormatting(1234567.89, "1234567.890");
    }

    @Test
    public void longPositiveDoubleIsFormattedCorrectly7() {
        assertFormatting(12345678.9, "12345678.900");
    }

    @Test
    public void longNegativeDoubleIsFormattedCorrectly1() {
        assertFormatting(-1.23456789, "-1.235");
    }

    @Test
    public void longNegativeDoubleIsFormattedCorrectly2() {
        assertFormatting(-12.3456789, "-12.346");
    }

    @Test
    public void longNegativeDoubleIsFormattedCorrectly3() {
        assertFormatting(-123.456789, "-123.457");
    }

    @Test
    public void longNegativeDoubleIsFormattedCorrectly4() {
        assertFormatting(-12345.6789, "-12345.679");
    }

    @Test
    public void longNegativeDoubleIsFormattedCorrectly5() {
        assertFormatting(-123456.789, "-123456.789");
    }

    @Test
    public void longNegativeDoubleIsFormattedCorrectly6() {
        assertFormatting(-1234567.89, "-1234567.890");
    }

    @Test
    public void longNegativeDoubleIsFormattedCorrectly7() {
        assertFormatting(-12345678.9, "-12345678.900");
    }

    @Test
    public void veryLongPositiveDoubleIsFormattedCorrectly7() {
        assertFormatting(Double.MAX_VALUE, "9223372036854776.000");
    }

    @Test
    public void piIsFormattedCorrectly() {
        assertFormatting(Math.PI, "3.142");
    }

    public void assertFormatting(double doubleValue, String expected) {
        DoubleValueAO doubleValueAO = new DoubleValueAO("name", doubleValue);
        String returnValue = doubleValueAO.getValue();
        assertThat(returnValue, is(expected));
    }

}