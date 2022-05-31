package ua.kostenko.expkitten.explodingkitten.engine;

import ua.kostenko.expkitten.explodingkitten.models.card.CardAction;
import ua.kostenko.expkitten.explodingkitten.models.deck.GameEdition;

import java.util.HashMap;
import java.util.Map;

import static ua.kostenko.expkitten.explodingkitten.models.card.CardAction.*;

public class GenerateStacksForEditions {
    public static Map<CardAction, Integer> getStackForEdition(GameEdition gameEdition) {
        switch (gameEdition) {
            case ORIGINAL_EDITION:
                return generateOriginalEditionStack();
            case NSFW_EDITION:
                return generateNsfwEditionStack();
            default:
                return null;
        }
    }

    private static Map<CardAction, Integer> generateNsfwEditionStack() {
        var nsfwEdition = new HashMap<CardAction, Integer>();
        nsfwEdition.put(DEFUSE, 6);
        nsfwEdition.put(NOPE, 5);
        nsfwEdition.put(CATS, 5);
        nsfwEdition.put(ATTACK, 4);
        nsfwEdition.put(SKIP, 4);
        nsfwEdition.put(FAVOR, 4);
        nsfwEdition.put(SHUFFLE, 4);
        nsfwEdition.put(SEE, 5);
        nsfwEdition.put(COMBO, 20);
        nsfwEdition.put(COMBO_JOKER, 4);
        nsfwEdition.put(ATTACK_TARGETED, 3);
        nsfwEdition.put(ALTER_THE_FUTURE, 4);
        nsfwEdition.put(DRAW_FROM_THE_BOTTOM, 4);
        nsfwEdition.put(REVERSE, 4);
        return nsfwEdition;
    }

    private static Map<CardAction, Integer> generateOriginalEditionStack() {
        var originalEdition = new HashMap<CardAction, Integer>();
        originalEdition.put(DEFUSE, 6);
        originalEdition.put(NOPE, 5);
        originalEdition.put(CATS, 4);
        originalEdition.put(ATTACK, 4);
        originalEdition.put(SKIP, 4);
        originalEdition.put(FAVOR, 4);
        originalEdition.put(SHUFFLE, 4);
        originalEdition.put(SEE, 5);
        originalEdition.put(COMBO, 20);
        return originalEdition;

    }
}
