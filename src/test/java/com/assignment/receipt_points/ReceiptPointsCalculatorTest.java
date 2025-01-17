package com.assignment.receipt_points;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReceiptPointsCalculatorTest {

    private ReceiptPointsCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new ReceiptPointsCalculator();
    }

    @Test
    void testProcessReceipt_Success() {
        Receipt receipt = createSampleReceipt();
        String receiptId = calculator.processReceipt(receipt);
        assertNotNull(receiptId);
        assertTrue(receiptId.length() > 0);
    }

    @Test
    void testCalculatePoints_AlphanumericRetailerName() {
        Receipt receipt = createSampleReceipt();
        receipt.setRetailer("Retailer123"); // 10 alphanumeric characters
        assertEquals(109, calculator.calculatePoints(receipt));
    }

    @Test
    void testCalculatePoints_RoundDollarAmount() {
        Receipt receipt = createSampleReceipt();
        receipt.setTotal("100.00");
        assertEquals(110, calculator.calculatePoints(receipt));
    }

    @Test
    void testCalculatePoints_MultipleOfQuarter() {
        Receipt receipt = createSampleReceipt();
        receipt.setTotal("0.50");
        assertEquals(60, calculator.calculatePoints(receipt));
    }

    @Test
    void testCalculatePoints_ItemPairs() {
        Receipt receipt = createSampleReceipt();
        receipt.setItemList(Arrays.asList(new Item("Item1", "1.00"), new Item("Item2", "2.00")));
        assertEquals(108, calculator.calculatePoints(receipt));
    }

    @Test
    void testCalculatePoints_ItemDescriptionMultipleOfThree() {
        Receipt receipt = createSampleReceipt();
        receipt.setItemList(Collections.singletonList(new Item("abc", "3.00"))); // "abc" length is 3
        assertEquals(104, calculator.calculatePoints(receipt));
    }

    @Test
    void testCalculatePoints_OddDay() {
        Receipt receipt = createSampleReceipt();
        receipt.setPurchaseDate("2024-01-15"); // 15 is odd
        assertEquals(110, calculator.calculatePoints(receipt));
    }

    @Test
    void testCalculatePoints_PurchaseTimeBetween2And4PM() {
        Receipt receipt = createSampleReceipt();
        receipt.setPurchaseTime("14:30"); // Between 2 PM and 4 PM
        assertEquals(110, calculator.calculatePoints(receipt));
    }

    @Test
    void testCalculatePoints_InvalidTotal() {
        Receipt receipt = createSampleReceipt();
        receipt.setTotal("invalid");
        assertThrows(IllegalArgumentException.class, () -> calculator.calculatePoints(receipt));
    }

    @Test
    void testCalculatePoints_InvalidPurchaseDate() {
        Receipt receipt = createSampleReceipt();
        receipt.setPurchaseDate("invalid-date");
        assertThrows(IllegalArgumentException.class, () -> calculator.calculatePoints(receipt));
    }

    @Test
    void testCalculatePoints_InvalidPurchaseTime() {
        Receipt receipt = createSampleReceipt();
        receipt.setPurchaseTime("invalid-time");
        assertThrows(IllegalArgumentException.class, () -> calculator.calculatePoints(receipt));
    }

    @Test
    void testGetPoints_Success() {
        Receipt receipt = createSampleReceipt();
        String receiptId = calculator.processReceipt(receipt);
        Long points = calculator.getPoints(receiptId);
        assertNotNull(points);
    }

    @Test
    void testGetPoints_InvalidReceiptId() {
        assertNull(calculator.getPoints("invalid-receipt-id"));
    }

    private Receipt createSampleReceipt() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("TestRetailer");
        receipt.setTotal("10.00");
        receipt.setItemList(Arrays.asList(
            new Item("TestItem1", "1.00"),
            new Item("TestItem2", "2.00")
        ));
        receipt.setPurchaseDate("2024-01-01");
        receipt.setPurchaseTime("14:00");
        return receipt;
    }
}