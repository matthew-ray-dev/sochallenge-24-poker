package com.example.matthewray.sochallenge24;

import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Card(Rank rank, Suit suit) implements Comparable<Card> {
    public static enum Rank {
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(11),
        QUEEN(12),
        KING(13),
        ACE(14);

        private final int rankAsNumber;

        private Rank(int rankAsNumber) {
            this.rankAsNumber = rankAsNumber;
        }

        public int getRankAsNumber() {
            return rankAsNumber;
        }
    }

    /**
     * Converts from a rank name ("two", "seven", "Jack", etc.) to a Rank enum. If
     * the rank name is not valid, returns null. Case-insensitive.
     * 
     * @param name The name of a card rank
     * @return The equivalent instance of the Rank enum, or null for an invalid rank
     */
    private static final Rank convertRankNameToRank(String name) {
        for (Rank r : Rank.values()) {
            if (r.name().equalsIgnoreCase(name)) {
                return r;
            }
        }
        return null;
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
            @JsonProperty("rank") String rankName,
            @JsonProperty("suit") String suitName) {
        Rank rank = Card.convertRankNameToRank(rankName);
        if (rank == null) {
            throw new IllegalArgumentException("\"%s\" is not a recognized rank.".formatted(rankName));
        }
        Suit suit = Card.convertSuitNameToEnum(suitName);
        if (suit == null) {
            throw new IllegalArgumentException("\"%s\" is not a recognized suit.".formatted(suitName));
        }
        this(rank, suit);
    }

    /**
     * Convenience wrapper for {@link Card#rank()}{@link Rank#getRankAsNumber()
     * .getRankAsNumber()}.
     */
    public int rankNum() {
        return rank.getRankAsNumber();
    }

    /**
     * Compare cards first by rank, then suit
     */
    @Override
    public int compareTo(Card o) {
        final int rankComparison = Integer.compare(this.rank().getRankAsNumber(), o.rank().getRankAsNumber());
        if (rankComparison != 0) {
            return rankComparison;
        }
        return this.suit().compareTo(o.suit());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        final Card other = (Card) o;
        return (this.rank().getRankAsNumber() == other.rank().getRankAsNumber())
                && this.suit().equals(other.suit());
    }

    @Override
    public String toString() {
        return this.rank.toString() + " of " + this.suit.toString();
    }
}
