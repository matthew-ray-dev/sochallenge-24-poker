package com.example.matthewray.sochallenge24;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

import org.apache.commons.math3.util.CombinatoricsUtils;

/**
 * Unit tests for Card class.
 */
public class HandAnalyzerTest {
    private static final List<String> ALL_RANK_NAMES = Arrays.stream(Card.Rank.values())
            .map(e -> e.name().toLowerCase()).toList();
    private static final List<String> ALL_SUIT_NAMES = Arrays.stream(Card.Suit.values())
            .map(e -> e.name().toLowerCase()).toList();
    private static final int TEST_EXPECTED_HAND_SIZE = 5;

    @Test
    public void checkRoyalFlush() {
        for (String suitName : ALL_SUIT_NAMES) {
            final List<Card> hand = List.of(
                    new Card("jack", suitName),
                    new Card("king", suitName),
                    new Card("ace", suitName),
                    new Card("queen", suitName),
                    new Card("ten", suitName));
            checkOutcomeByResultObject(hand, "ROYAL_FLUSH");
            checkOutcomeByPoints(hand, 512);
        }
    }

    @Test
    public void checkNotRoyalFlushBadRank() {
        for (String suitName : ALL_SUIT_NAMES) {
            final List<Card> hand = List.of(
                    new Card("jack", suitName),
                    new Card("ten", suitName),
                    new Card("ace", suitName),
                    new Card("queen", suitName),
                    new Card("nine", suitName));
            checkOutcomeByResultObject(hand, "FLUSH");
            checkOutcomeByPoints(hand, 32);
        }
    }

    @Test
    public void checkNotRoyalFlushBadSuit() {
        final List<Card> hand = List.of(
                new Card("jack", "spades"),
                new Card("king", "spades"),
                new Card("ace", "spades"),
                new Card("queen", "diamonds"),
                new Card("ten", "spades"));
        checkOutcomeByResultObject(hand, "STRAIGHT");
        checkOutcomeByPoints(hand, 16);
    }

    @Test
    public void checkStraightFlush() {
        for (String suitName : ALL_SUIT_NAMES) {
            // Don't go past king as high-end card, or it becomes a royal flush
            final int rankCutoff = (ALL_RANK_NAMES.size() - 1) - (TEST_EXPECTED_HAND_SIZE - 1);
            for (int i = 0; i < rankCutoff; i++) {
                final List<Card> hand = List.of(
                        new Card(ALL_RANK_NAMES.get(i + 3), suitName),
                        new Card(ALL_RANK_NAMES.get(i + 1), suitName),
                        new Card(ALL_RANK_NAMES.get(i), suitName),
                        new Card(ALL_RANK_NAMES.get(i + 2), suitName),
                        new Card(ALL_RANK_NAMES.get(i + 4), suitName));
                // System.out.printf("Should be a straight flush: %s\n", hand);
                checkOutcomeByResultObject(hand, "STRAIGHT_FLUSH");
                checkOutcomeByPoints(hand, 256);
            }
        }
    }

    @Test
    public void checkNotStraightFlushBadRank() {
        for (String suitName : ALL_SUIT_NAMES) {
            final List<Card> hand = List.of(
                    new Card("five", suitName),
                    new Card("six", suitName),
                    new Card("eight", suitName),
                    new Card("nine", suitName),
                    new Card("ten", suitName));
            checkOutcomeByResultObject(hand, "FLUSH");
            checkOutcomeByPoints(hand, 32);
        }
    }

    @Test
    public void checkNotStraightFlushBecauseAceIsNotOne() {
        for (String suitName : ALL_SUIT_NAMES) {
            final List<Card> hand = List.of(
                    new Card("ace", suitName),
                    new Card("two", suitName),
                    new Card("three", suitName),
                    new Card("four", suitName),
                    new Card("five", suitName));
            checkOutcomeByResultObject(hand, "FLUSH");
            checkOutcomeByPoints(hand, 32);
        }
    }

    @Test
    public void checkNotStraightFlushBadSuit() {
        final List<Card> hand = List.of(
                new Card("six", "hearts"),
                new Card("seven", "hearts"),
                new Card("eight", "clubs"),
                new Card("nine", "hearts"),
                new Card("ten", "hearts"));
        checkOutcomeByResultObject(hand, "STRAIGHT");
        checkOutcomeByPoints(hand, 16);
    }

    @Test
    public void checkFourOfAKind() {
        final int numRanks = ALL_RANK_NAMES.size();
        final int oddRankOutOffset = 5; // arbitrary
        for (int i = 0; i < numRanks; i++) {
            final String selectedRank = ALL_RANK_NAMES.get(i);
            final String oddRankOut = ALL_RANK_NAMES.get((i + oddRankOutOffset) % numRanks);
            final List<Card> hand = List.of(
                    new Card(selectedRank, "spades"),
                    new Card(selectedRank, "hearts"),
                    new Card(selectedRank, "diamonds"),
                    new Card(selectedRank, "clubs"),
                    new Card(oddRankOut, "spades"));
            checkOutcomeByResultObject(hand, "FOUR_OF_A_KIND");
            checkOutcomeByPoints(hand, 128);
        }
    }

    @Test
    public void checkFullHouse() {
        final int numRanks = ALL_RANK_NAMES.size();
        final int pairRankOffset = 5; // arbitrary
        for (int i = 0; i < numRanks; i++) {
            final String trioRank = ALL_RANK_NAMES.get(i);
            final String pairRank = ALL_RANK_NAMES.get((i + pairRankOffset) % numRanks);
            final List<Card> hand = List.of(
                    new Card(trioRank, "spades"),
                    new Card(trioRank, "hearts"),
                    new Card(trioRank, "diamonds"),
                    new Card(pairRank, "clubs"),
                    new Card(pairRank, "spades"));
            checkOutcomeByResultObject(hand, "FULL_HOUSE");
            checkOutcomeByPoints(hand, 64);
        }
    }

    @Test
    public void checkFlush() {
        for (String suitName : ALL_SUIT_NAMES) {
            final List<Card> hand = List.of(
                    new Card("king", suitName),
                    new Card("jack", suitName),
                    new Card("nine", suitName),
                    new Card("seven", suitName),
                    new Card("five", suitName));
            checkOutcomeByResultObject(hand, "FLUSH");
            checkOutcomeByPoints(hand, 32);
        }
    }

    @Test
    public void checkStraight() {
        // Go up through ace as high-end card
        final int cutoff = ALL_RANK_NAMES.size() - (TEST_EXPECTED_HAND_SIZE - 1);
        for (int i = 0; i < cutoff; i++) {
            final List<Card> hand = List.of(
                    new Card(ALL_RANK_NAMES.get(i + 3), "spades"),
                    new Card(ALL_RANK_NAMES.get(i + 1), "hearts"),
                    new Card(ALL_RANK_NAMES.get(i), "diamonds"),
                    new Card(ALL_RANK_NAMES.get(i + 2), "clubs"),
                    new Card(ALL_RANK_NAMES.get(i + 4), "diamonds"));
            // System.out.printf("Should be a straight: %s\n", hand);
            checkOutcomeByResultObject(hand, "STRAIGHT");
            checkOutcomeByPoints(hand, 16);
        }
    }

    @Test
    public void checkNotStraightBecauseAceIsNotOne() {
        final List<Card> hand = List.of(
                new Card("ace", "hearts"),
                new Card("two", "hearts"),
                new Card("three", "clubs"),
                new Card("four", "diamonds"),
                new Card("five", "spades"));
        checkOutcomeByResultObject(hand, "HIGH_CARD");
        checkOutcomeByPoints(hand, 1);
    }

    @Test
    public void checkThreeOfAKind() {
        final int numRanks = ALL_RANK_NAMES.size();
        final int firstOtherCardOffset = 4; // arbitrary
        final int secondOtherCardOffset = 5; // arbitrary
        for (int i = 0; i < numRanks; i++) {
            final String trioRank = ALL_RANK_NAMES.get(i);
            final String firstOtherRank = ALL_RANK_NAMES.get((i + firstOtherCardOffset) % numRanks);
            final String secondOtherRank = ALL_RANK_NAMES.get((i + secondOtherCardOffset) % numRanks);
            final List<Card> hand = List.of(
                    new Card(trioRank, "clubs"),
                    new Card(trioRank, "hearts"),
                    new Card(trioRank, "diamonds"),
                    new Card(firstOtherRank, "spades"),
                    new Card(secondOtherRank, "spades"));
            checkOutcomeByResultObject(hand, "THREE_OF_A_KIND");
            checkOutcomeByPoints(hand, 8);
        }
    }

    @Test
    public void checkTwoPair() {
        final int numRanks = ALL_RANK_NAMES.size();
        final int secondPairOffset = 1; // arbitrary
        final int otherCardOffset = 2; // arbitrary
        for (int i = 0; i < numRanks; i++) {
            final String firstPairRank = ALL_RANK_NAMES.get(i);
            final String secondPairRank = ALL_RANK_NAMES.get((i + secondPairOffset) % numRanks);
            final String otherCardRank = ALL_RANK_NAMES.get((i + otherCardOffset) % numRanks);
            final List<Card> hand = List.of(
                    new Card(firstPairRank, "spades"),
                    new Card(firstPairRank, "hearts"),
                    new Card(secondPairRank, "spades"),
                    new Card(secondPairRank, "clubs"),
                    new Card(otherCardRank, "spades"));
            checkOutcomeByResultObject(hand, "TWO_PAIR");
            checkOutcomeByPoints(hand, 4);
        }
    }

    @Test
    public void checkPair() {
        final int numRanks = ALL_RANK_NAMES.size();
        final int firstOtherCardOffset = 1; // arbitrary
        final int secondOtherCardOffset = 3; // arbitrary
        final int thirdOtherCardOffset = 7; // arbitrary
        for (int i = 0; i < numRanks; i++) {
            final String pairRank = ALL_RANK_NAMES.get(i);
            final String firstOtherCardRank = ALL_RANK_NAMES.get((i + firstOtherCardOffset) % numRanks);
            final String secondOtherCardRank = ALL_RANK_NAMES.get((i + secondOtherCardOffset) % numRanks);
            final String thirdOtherCardRank = ALL_RANK_NAMES.get((i + thirdOtherCardOffset) % numRanks);
            final List<Card> hand = List.of(
                    new Card(pairRank, "clubs"),
                    new Card(pairRank, "hearts"),
                    new Card(firstOtherCardRank, "clubs"),
                    new Card(secondOtherCardRank, "clubs"),
                    new Card(thirdOtherCardRank, "spades"));
            checkOutcomeByResultObject(hand, "PAIR");
            checkOutcomeByPoints(hand, 2);
        }
    }

    @Test
    public void checkHighCard() {
        for (int i = 0; i < ALL_SUIT_NAMES.size(); i++) {
            final String mainSuit = ALL_SUIT_NAMES.get(i);
            final String offSuit = ALL_SUIT_NAMES.get((i + 1) % ALL_SUIT_NAMES.size());
            for (int j = 0; j < ALL_RANK_NAMES.size(); j++) {
                final String firstRank = ALL_RANK_NAMES.get(i);
                final String secondRank = ALL_RANK_NAMES.get((i + 1) % ALL_RANK_NAMES.size());
                final String thirdRank = ALL_RANK_NAMES.get((i + 2) % ALL_RANK_NAMES.size());
                final String fourthRank = ALL_RANK_NAMES.get((i + 3) % ALL_RANK_NAMES.size());
                final String oddballFifthRank = ALL_RANK_NAMES.get((i + 7) % ALL_RANK_NAMES.size());
                final List<Card> hand = List.of(
                        new Card(firstRank, mainSuit),
                        new Card(secondRank, mainSuit),
                        new Card(thirdRank, mainSuit),
                        new Card(fourthRank, mainSuit),
                        new Card(oddballFifthRank, offSuit));
                checkOutcomeByResultObject(hand, "HIGH_CARD");
                checkOutcomeByPoints(hand, 1);
            }
        }
    }

    @Test
    public void sanityCheckAllValidHands() {
        final int expectedNumberOfHands = 2_598_960; // 52 choose 5
        final List<Card> fullDeck = new ArrayList<>();
        for (Card.Rank r : Card.Rank.values()) {
            for (Card.Suit s : Card.Suit.values()) {
                fullDeck.add(new Card(r, s));
            }
        }
        final Iterator<int[]> handItr = CombinatoricsUtils.combinationsIterator(
                fullDeck.size(), TEST_EXPECTED_HAND_SIZE);
        int handsChecked = 0;
        while (handItr.hasNext()) {
            final int[] handIndices = handItr.next();
            List<Card> hand = new LinkedList<>();
            for (int i : handIndices) {
                hand.add(fullDeck.get(i));
            }
            final int points = HandAnalyzer.getPointsForHand(hand);
            final boolean isSane = (points >= 1)
                    && (points <= 512)
                    && ((points & -points) == points); // power-of-2 bitwise check
            assertTrue("Failure on hand: " + hand, isSane);
            handsChecked++;
        }
        assertEquals(expectedNumberOfHands, handsChecked);
    }

    /*
     * Bad-arg tests
     */

    @Test
    public void checkBadHandTooBig() {
        final List<Card> hand = List.of(
                new Card("king", "spades"),
                new Card("jack", "hearts"),
                new Card("nine", "diamonds"),
                new Card("seven", "clubs"),
                new Card("five", "spades"),
                new Card("three", "hearts"));
        boolean gotExpectedException = false;
        try {
            checkOutcomeByPoints(hand, -111111);
        } catch (IllegalArgumentException e) {
            gotExpectedException = true;
            assertEquals("Hand must be size 5.", e.getMessage());
        }
        assertTrue(gotExpectedException);
    }

    @Test
    public void checkBadHandTooSmall() {
        final List<Card> hand = List.of(
                new Card("king", "spades"),
                new Card("jack", "hearts"),
                new Card("nine", "diamonds"),
                new Card("seven", "clubs"));
        boolean gotExpectedException = false;
        try {
            checkOutcomeByPoints(hand, -111111);
        } catch (IllegalArgumentException e) {
            gotExpectedException = true;
            assertEquals("Hand must be size 5.", e.getMessage());
        }
        assertTrue(gotExpectedException);
    }

    @Test
    public void checkBadHandDuplicateCard() {
        final List<Card> hand = List.of(
                new Card("king", "spades"),
                new Card("jack", "hearts"),
                new Card("nine", "diamonds"),
                new Card("seven", "clubs"),
                new Card("king", "spades"));
        boolean gotExpectedException = false;
        try {
            checkOutcomeByPoints(hand, -111111);
        } catch (IllegalArgumentException e) {
            gotExpectedException = true;
            assertEquals("The hand contains duplicate cards.", e.getMessage());
        }
        assertTrue(gotExpectedException);
    }

    @Test
    public void checkBadHandNullCard() {
        final List<Card> hand = new ArrayList<>();
        hand.add(new Card("king", "spades"));
        hand.add(new Card("jack", "hearts"));
        hand.add(null);
        hand.add(new Card("nine", "diamonds"));
        hand.add(new Card("seven", "clubs"));

        boolean gotExpectedException = false;
        try {
            checkOutcomeByPoints(hand, -111111);
        } catch (IllegalArgumentException e) {
            gotExpectedException = true;
            assertEquals("A card in the hand is null.", e.getMessage());
        }
        assertTrue(gotExpectedException);
    }

    /*
     * Helper methods
     */

    private void checkOutcomeByPoints(List<Card> hand, int expectedPoints) {
        checkOutcomeByPoints(null, hand, expectedPoints);
    }

    private void checkOutcomeByPoints(final String failureMsg, List<Card> hand, int expectedPoints) {
        final int actualPoints = HandAnalyzer.getPointsForHand(hand);
        assertEquals(failureMsg, expectedPoints, actualPoints);
    }

    private void checkOutcomeByResultObject(List<Card> hand, String expectedResultName) {
        checkOutcomeByResultObject("Failed on hand: %s".formatted(hand), hand, expectedResultName);
    }

    private void checkOutcomeByResultObject(final String failureMsg, List<Card> hand, String expectedResultName) {
        Card[] handArr = new Card[hand.size()];
        hand.toArray(handArr);
        var expectedResult = getResultEnumConstant(expectedResultName);
        var actualResult = callHandAnalyzerGetResultForHand(handArr);
        assertEquals(failureMsg, expectedResult, actualResult);
    }

    /**
     * Uses reflection to access private class / enum
     * {@link HandAnalyzer#Result} and return the named constant
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Enum getResultEnumConstant(String name) {
        final String expectedClassName = "Result";
        try {
            Class[] innerClasses = HandAnalyzer.class.getDeclaredClasses();
            for (Class c : innerClasses) {
                if (c.getSimpleName().equals(expectedClassName)) {
                    Enum handResult = Enum.valueOf(c, name);
                    return handResult;
                }
            }
            throw new RuntimeException("\"%s\" didn't match an enum constant".formatted(name));
            // Method m = PoseComboCalculator.class.getDeclaredMethod(expectedMethodName,
            // int.class);
            // m.setAccessible(true);
            // return (PoseComboComputationResults) m.invoke(calculator, targetTotal);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("Couldn't get result enum constant via reflection", false);
            // Shouldn't reach here anyway, but...
            throw new RuntimeException(e);
        }
    }

    /**
     * Uses reflection to access private method
     * {@link HandAnalyzer#getResultForHand(Card[])}.
     */
    private Object callHandAnalyzerGetResultForHand(Card[] hand) {
        final String expectedMethodName = "getResultForHand";
        try {
            Method m = HandAnalyzer.class.getDeclaredMethod(expectedMethodName,
                    Card[].class);
            m.setAccessible(true);
            return m.invoke(null, new Object[] { hand });
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            assertTrue("Couldn't get %s(Card[]) via reflection".formatted(expectedMethodName), false);
            // Shouldn't reach here anyway, but...
            throw new RuntimeException(e);
        }
    }
}
