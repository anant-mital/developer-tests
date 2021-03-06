package org.vaadin.hr;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.vaadin.hr.optimise.LongSummator;
import org.vaadin.hr.optimise.PascalNumberCalculator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for optimisation of redundant operations.
 *
 * Do not edit this file.
 *
 * @author miki
 * @since 2017-05-04
 */
public class OptimisationTest {

    private PascalNumberCalculator calculator;

    // increase this to 1 for extra challenge
    private static final int LEVEL = 0;

    public static final int[][] LEVELS = new int[][] {
        new int[]{25, 15, 17},
        new int[]{20, 4, 6}
    };

    @Before
    public void setUp() {
        // reset the counter and make new calculator
        LongSummator.getCounter().reset();
        this.calculator = new PascalNumberCalculator();
    }

    @Test
    public void testSingleNumber() {
        long number = calculator.getNumber(9, 3);
        assertEquals("incorrect calculation", 84, number);
        assertTrue(String.format("too many operations - %d (max %d)", LongSummator.getCounter().getTicks(), LEVELS[LEVEL][0]), LongSummator.getCounter().getTicks() <= LEVELS[LEVEL][0]);
    }

    @Test
    public void testSingleRow() {
        long[] row = calculator.getRow(5);
        assertArrayEquals("incorrect data", new long[]{1, 5, 10, 10, 5, 1}, row);
        assertTrue(String.format("too many operations - %d (max %d)", LongSummator.getCounter().getTicks(), LEVELS[LEVEL][1]), LongSummator.getCounter().getTicks() <= LEVELS[LEVEL][1]);
    }

    @Test
    public void testTwoRows() {
            long[] low = calculator.getRow(8);
            long ticksLow = LongSummator.getCounter().getTicks();
            long[] hi = calculator.getRow(3);
            long ticksHi = LongSummator.getCounter().getTicks();
            assertEquals("getting an already calculated row should not require extra computation", ticksLow, ticksHi);
    }

    @Test
    public void testElements() {
        long centre = calculator.getNumber(8, 4);
        long ticksCentre = LongSummator.getCounter().getTicks();
        long left = calculator.getNumber(7, 3);
        long ticksLeft = LongSummator.getCounter().getTicks();
        long right = calculator.getNumber(7, 4);
        long ticksRight = LongSummator.getCounter().getTicks();
        assertEquals("incorrect data for row 7", left, right);
        assertEquals("incorrect data for row 8", 70, centre);
        assertTrue(String.format("too many operations - %d (max %d)", LongSummator.getCounter().getTicks(), LEVELS[LEVEL][2]), LongSummator.getCounter().getTicks() <= LEVELS[LEVEL][2]);
        if(LEVEL == 1) {
            assertEquals("there should be no need for extra operations here", ticksCentre, ticksLeft);
            assertEquals("there should be no need for extra operations here", ticksRight, ticksLeft);
        }
    }

    /**
     * Runs the tests as a java app.
     * This method should be used if your IDE does not have support for Maven or JUnit.
     *
     * @param args
     *          Command line arguments. Ignored.
     */
    public static final void main(String[] args) {
        JUnitCore.main("org.vaadin.hr.OptimisationTest");
    }

}
