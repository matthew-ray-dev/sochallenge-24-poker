package com.example.matthewray.sochallenge24;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.example.matthewray.sochallenge24.Card.Rank;
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
        assertEquals("Bad rank enum for 01", Rank.TWO, card01.rank());
        assertEquals("Bad rank int for 01", 2, card01.rankNum());
        assertEquals("Bad suit for 01", Suit.SPADES, card01.suit());
        //
        final String inputRank02 = "SEVEN";
        final String inputSuit02 = "HEARTS";
        final Card card02 = new Card(inputRank02, inputSuit02);
        assertEquals("Bad rank enum for 02", Rank.SEVEN, card02.rank());
        assertEquals("Bad rank int for 02", 7, card02.rankNum());
        assertEquals("Bad suit for 02", Suit.HEARTS, card02.suit());
        //
        final String inputRank03 = "Jack";
        final String inputSuit03 = "Diamonds";
        final Card card03 = new Card(inputRank03, inputSuit03);
        assertEquals("Bad rank enum for 03", Rank.JACK, card03.rank());
        assertEquals("Bad rank int for 03", 11, card03.rankNum());
        assertEquals("Bad suit for 03", Suit.DIAMONDS, card03.suit());
        //
        final String inputRank04 = "aCE";
        final String inputSuit04 = "cLUBS";
        final Card card04 = new Card(inputRank04, inputSuit04);
        assertEquals("Bad rank enum for 04", Rank.ACE, card04.rank());
        assertEquals("Bad rank int for 04", 14, card04.rankNum());
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

    /**
     * We expect a sort from lowest to highest, first by rank (two, three, ...,
     * king, ace) and then by suit (clubs, diamonds, hearts, spades).
     */
    @Test
    public void sortCards() {
        final Card[] cardsToSort = new Card[] {
                new Card("eight", "spades"),
                new Card("two", "clubs"),
                new Card("ace", "diamonds"),
                new Card("queen", "hearts"),
                new Card("eight", "hearts"),
                new Card("ace", "clubs"),
                new Card("queen", "diamonds"),
                new Card("two", "spades"),
                new Card("queen", "clubs"),
                new Card("two", "hearts"),
                new Card("ace", "spades"),
                new Card("eight", "diamonds")
        };
        final Card[] expectedSort = new Card[] {
                new Card("two", "clubs"),
                new Card("two", "hearts"),
                new Card("two", "spades"),
                new Card("eight", "diamonds"),
                new Card("eight", "hearts"),
                new Card("eight", "spades"),
                new Card("queen", "clubs"),
                new Card("queen", "diamonds"),
                new Card("queen", "hearts"),
                new Card("ace", "clubs"),
                new Card("ace", "diamonds"),
                new Card("ace", "spades")
        };
        Arrays.sort(cardsToSort);
        assertEquals(expectedSort.length, cardsToSort.length);
        for (int i = 0; i < expectedSort.length; i++) {
            assertEquals(expectedSort[i], cardsToSort[i]);
        }
    }
}
