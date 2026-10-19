package com.example.matthewray.sochallenge24;

import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Card(int rank, Suit suit) implements Comparable<Card> {
    private static final String[] rankNames = new String[] {
            "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT",
            "NINE", "TEN", "JACK", "QUEEN", "KING", "ACE" };
    private static final int rankNamesOffset = 2;

    /**
     * Converts from a rank name ("two", "seven", "Jack", etc.) to an int
     * representing the rank. Int values range from 2 ("two") to 14 ("Ace"). If the
     * rank name is not valid, returns -1. Case-insensitive.
     * 
     * @param name The name of a card rank
     * @return The equivalent int value (2 to 14), or -1 for an invalid rank
     */
    private static final int convertRankNameToInt(String name) {
        for (int i = 0; i < rankNames.length; i++) {
            if (rankNames[i].equalsIgnoreCase(name)) {
                return i + rankNamesOffset;
            }
        }
        return -1;
    }

    public static enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;
    }

    private static final Map<String, Suit> suitNamesToSuits = Map.ofEntries(
            Map.entry("CLUB", Suit.CLUBS),
            Map.entry("CLUBS", Suit.CLUBS),
            Map.entry("C", Suit.CLUBS),
            Map.entry("DIAMOND", Suit.DIAMONDS),
            Map.entry("DIAMONDS", Suit.DIAMONDS),
            Map.entry("D", Suit.DIAMONDS),
            Map.entry("HEART", Suit.HEARTS),
            Map.entry("HEARTS", Suit.HEARTS),
            Map.entry("H", Suit.HEARTS),
            Map.entry("SPADE", Suit.SPADES),
            Map.entry("SPADES", Suit.SPADES),
            Map.entry("S", Suit.SPADES));

    private static final Suit convertSuitNameToEnum(String name) {
        if (name == null) {
            return null;
        }
        final String nameAllCaps = name.toUpperCase(Locale.US);
        return suitNamesToSuits.getOrDefault(nameAllCaps, null);
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Card(
            @JsonProperty("rank") String rank,
            @JsonProperty("suit") String suit) {
        int rankInt = Card.convertRankNameToInt(rank);
        if (rankInt < 0) {
            throw new IllegalArgumentException("\"%s\" is not a recognized rank.".formatted(rank));
        }
        Suit suitEnum = Card.convertSuitNameToEnum(suit);
        if (suitEnum == null) {
            throw new IllegalArgumentException("\"%s\" is not a recognized suit.".formatted(suit));
        }
        this(rankInt, suitEnum);
    }

    /**
     * Compare poses first by rank, then suit
     */
    @Override
    public int compareTo(Card o) {
        final int rankComparison = Integer.compare(this.rank(), o.rank());
        if (rankComparison != 0) {
            return rankComparison;
        }
        return this.suit().compareTo(o.suit());
    }
}
