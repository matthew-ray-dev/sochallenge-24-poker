package com.example.matthewray.sochallenge24;

import java.io.File;
import java.util.Collection;
import java.util.List;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * Reads in poker hands from files
 *
 */
public class App {
    public static void main(String[] args) {
        int pointsTotal = 0;
        final String pokerHandsFilename = args[0];
        final ObjectMapper mapper = JsonMapper.builder().build();
        final JsonNode root = mapper.readTree(new File(pokerHandsFilename));
        Collection<JsonNode> pokerHands = root.get("poker_hands").values();
        for (JsonNode node : pokerHands) {
            final long handId = node.get("hand_id").asLong();
            final List<Card> hand = mapper.treeToValue(node.get("cards"),
                    new TypeReference<List<Card>>() {
                    });
            final int points = HandAnalyzer.getPointsForHand(hand);
            System.out.println("Hand %d: %d points".formatted(handId, points));
            pointsTotal += points;
        }
        System.out.println("TOTAL: %d points".formatted(pointsTotal));
    }
}
