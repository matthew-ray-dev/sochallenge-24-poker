package com.example.matthewray.sochallenge24;

import java.util.Arrays;
import java.util.Collection;

import com.example.matthewray.sochallenge24.Card.Rank;

public class HandAnalyzer {
    private static final int EXPECTED_HAND_SIZE = 5;

    private static enum Result {
        ROYAL_FLUSH(512),
        STRAIGHT_FLUSH(256),
        FOUR_OF_A_KIND(128),
        FULL_HOUSE(64),
        FLUSH(32),
        STRAIGHT(16),
        THREE_OF_A_KIND(8),
        TWO_PAIR(4),
        PAIR(2),
        HIGH_CARD(1);

        private final int points;

        Result(int points) {
            this.points = points;
        }

        public int getPoints() {
            return points;
        }
    }

    /**
     * Takes a "simple hand" of cards, finds the max-points "playable hand" that can
     * be formed from the simple hand, then returns the point value of the playable
     * hand.
     * <br/>
     * Playable hands have the following point values:
     * <br/>
     * <ul>
     * <li>Royal flush: 512</li>
     * <li>Straight flush: 256</li>
     * <li>Four-of-a-kind: 128</li>
     * <li>Full house: 64</li>
     * <li>Flush: 32</li>
     * <li>Straight: 16</li>
     * <li>Three-of-a-kind: 8</li>
     * <li>Two Pair: 4</li>
     * <li>Pair: 2</li>
     * <li>High card: 2</li>
     * </ul>
     * <br/>
     * Method will throw an exception if the simple hand has !=
     * {@value HandAnalyzer#EXPECTED_HAND_SIZE} cards, any
     * duplicate cards, or any null values.
     * 
     * @param hand {@value HandAnalyzer#EXPECTED_HAND_SIZE} unique cards
     * @return Max point value that can be gotten from hand
     */
    public static int getPointsForHand(Collection<Card> hand) {
        if (hand.size() != EXPECTED_HAND_SIZE) {
            throw new IllegalArgumentException("Hand must be size %d.".formatted(EXPECTED_HAND_SIZE));
        }
        Card[] handArr = new Card[EXPECTED_HAND_SIZE];
        hand.toArray(handArr);
        for (int i = 0; i < handArr.length; i++) {
            if (handArr[i] == null) {
                throw new IllegalArgumentException("A card in the hand is null.");
            }
            for (int j = i + 1; j < handArr.length; j++) {
                if (handArr[i].equals(handArr[j])) {
                    throw new IllegalArgumentException("The hand contains duplicate cards.");
                }
            }
        }
        return getResultForHand(handArr).getPoints();
    }

    /**
     * Does the actual analysis of the hand
     * 
     * @param hand Array of 5 unique {@link Card} objects.
     * @return
     */
    private static Result getResultForHand(Card[] hand) {
        // MUST BE SORTED FOR THE REST TO WORK
        Arrays.sort(hand);
        boolean hasStraight = true;
        boolean hasFlush = true;
        // Count how many cards of the same rank are in the hand.
        // E.g. after sorting the counts,
        // a four-of-a-kind hand will be [0,0,0,1,4],
        // a full house hand will be [0,0,0,2,3],
        // a single-pair hand will be [0,1,1,1,2], etc.
        int[] sameRankCounts = new int[hand.length];
        Arrays.fill(sameRankCounts, 1);

        for (int i = 1; i < hand.length; i++) {
            final Card last = hand[i - 1];
            final Card current = hand[i];
            hasStraight = hasStraight && (last.rankNum() + 1 == current.rankNum());
            hasFlush = hasFlush && (last.suit().equals(current.suit()));
            if (last.rank() == current.rank()) {
                sameRankCounts[i] = sameRankCounts[i - 1] + 1;
                sameRankCounts[i - 1] = 0;
            }
        }
        Arrays.sort(sameRankCounts);

        // Go down the list of results
        if (hasStraight && hasFlush) {
            if (hand[hand.length - 1].rank().equals(Rank.ACE)) {
                return Result.ROYAL_FLUSH;
            } else {
                return Result.STRAIGHT_FLUSH;
            }
        }
        if (hasFourOfAKind(sameRankCounts)) {
            return Result.FOUR_OF_A_KIND;
        }
        final boolean hasThreeOfAKind = hasThreeOfAKind(sameRankCounts);
        final int numOfPairs = tallyPairs(sameRankCounts);
        if (hasThreeOfAKind && (numOfPairs == 1)) {
            return Result.FULL_HOUSE;
        }
        if (hasFlush) {
            return Result.FLUSH;
        }
        if (hasStraight) {
            return Result.STRAIGHT;
        }
        if (hasThreeOfAKind) {
            return Result.THREE_OF_A_KIND;
        }
        if (numOfPairs == 2) {
            return Result.TWO_PAIR;
        }
        if (numOfPairs == 1) {
            return Result.PAIR;
        }
        // If all else fails...
        return Result.HIGH_CARD;
    }

    private static boolean hasFourOfAKind(int[] sortedSameRankCounts) {
        final int largestCount = sortedSameRankCounts[sortedSameRankCounts.length - 1];
        return largestCount == 4;
    }

    private static boolean hasThreeOfAKind(int[] sortedSameRankCounts) {
        final int largestCount = sortedSameRankCounts[sortedSameRankCounts.length - 1];
        return largestCount == 3;
    }

    private static int tallyPairs(int[] sortedSameRankCounts) {
        // 2-counts can only appear in the last two slots of the array.
        // Why? Because a 2-count can only co-occur with a 3-count, another 2-count and
        // a 1-count, or three 1-counts.
        int pairTally = 0;
        final int lastIdx = sortedSameRankCounts.length - 1;
        for (int i = lastIdx; i > lastIdx - 2; i--) {
            if (sortedSameRankCounts[i] == 2) {
                pairTally++;
            }
        }
        return pairTally;
    }
}