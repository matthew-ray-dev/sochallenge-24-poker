package com.example.matthewray.sochallenge24;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.example.matthewray.sochallenge24.Card.Suit;

/**
 * Unit tests for Card class.
 */
public class CardTest {

    @Test
    public void createBasicCards() {
        final String inputRank01 = "two";
        final String inputSuit01 = "spades";
        final Card card01 = new Card(inputRank01, inputSuit01);
        assertEquals("Bad rank for 01", 2, card01.rank());
        assertEquals("Bad suit for 01", Suit.SPADES, card01.suit());
        //
        final String inputRank02 = "SEVEN";
        final String inputSuit02 = "HEARTS";
        final Card card02 = new Card(inputRank02, inputSuit02);
        assertEquals("Bad rank for 02", 7, card02.rank());
        assertEquals("Bad suit for 02", Suit.HEARTS, card02.suit());
        //
        final String inputRank03 = "Jack";
        final String inputSuit03 = "Diamonds";
        final Card card03 = new Card(inputRank03, inputSuit03);
        assertEquals("Bad rank for 03", 11, card03.rank());
        assertEquals("Bad suit for 03", Suit.DIAMONDS, card03.suit());
        //
        final String inputRank04 = "aCE";
        final String inputSuit04 = "cLUBS";
        final Card card04 = new Card(inputRank04, inputSuit04);
        assertEquals("Bad rank for 04", 14, card04.rank());
        assertEquals("Bad suit for 04", Suit.CLUBS, card04.suit());
    }

    @Test
    public void createCardWithBadRankArg() {
        final String inputRank01 = "one";
        final String inputSuit01 = "Spades";
        boolean threwException01 = false;
        try {
            new Card(inputRank01, inputSuit01);
        } catch (IllegalArgumentException e) {
            assertEquals("\"one\" is not a recognized rank.", e.getMessage());
            threwException01 = true;
        }
        assertTrue(threwException01);
    }

    @Test
    public void createCardWithNullRankArg() {
        final String inputRank01 = null;
        final String inputSuit01 = "Spades";
        boolean threwException01 = false;
        try {
            new Card(inputRank01, inputSuit01);
        } catch (IllegalArgumentException e) {
            assertEquals("\"null\" is not a recognized rank.", e.getMessage());
            threwException01 = true;
        }
        assertTrue(threwException01);
    }

    @Test
    public void createCardWithBadSuitArg() {
        final String inputRank01 = "two";
        final String inputSuit01 = "Circles";
        boolean threwException01 = false;
        try {
            new Card(inputRank01, inputSuit01);
        } catch (IllegalArgumentException e) {
            assertEquals("\"Circles\" is not a recognized suit.", e.getMessage());
            threwException01 = true;
        }
        assertTrue(threwException01);
    }

    @Test
    public void createCardWithNullSuitArg() {
        final String inputRank01 = "two";
        final String inputSuit01 = null;
        boolean threwException01 = false;
        try {
            new Card(inputRank01, inputSuit01);
        } catch (IllegalArgumentException e) {
            assertEquals("\"null\" is not a recognized suit.", e.getMessage());
            threwException01 = true;
        }
        assertTrue(threwException01);
    }
}
