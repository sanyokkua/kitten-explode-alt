package ua.kostenko.expkitten.explodingkitten.engine;

import ua.kostenko.expkitten.explodingkitten.models.card.Card;
import ua.kostenko.expkitten.explodingkitten.models.card.CardAction;
import ua.kostenko.expkitten.explodingkitten.models.deck.GameEdition;

import java.util.*;
import java.util.function.Predicate;

import static ua.kostenko.expkitten.explodingkitten.models.card.CardAction.*;

public class DeckGenerator {

    private DeckGenerator() {
    }

    public static Map<CardAction, Integer> getDeckInformationForEdition(GameEdition gameEdition) {
        switch (gameEdition) {
            case ORIGINAL_EDITION:
                return generateOriginalEditionDeckInfo();
            case NSFW_EDITION:
                return generateNsfwEditionDeckInfo();
            default:
                throw new IllegalArgumentException("Edition is not supported");
        }
    }

    private static Map<CardAction, Integer> generateOriginalEditionDeckInfo() {
        Map<CardAction, Integer> originalEdition = new EnumMap<>(CardAction.class);
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

    private static Map<CardAction, Integer> generateNsfwEditionDeckInfo() {
        Map<CardAction, Integer> nsfwEdition = new EnumMap<>(CardAction.class);
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

    public static List<Card> getDeckByPredicate(Map<CardAction, Integer> gameEditionDeckInformation, Predicate<Map.Entry<CardAction, Integer>> filterPredicate) {
        List<Card> temporalGeneralStack = new ArrayList<>();

        gameEditionDeckInformation.entrySet().stream()
                .filter(filterPredicate)
                .forEach(stackEntry -> {
                    var cardId = UUID.randomUUID().toString();
                    var amountOfCardsForAction = stackEntry.getValue();
                    var cardAction = stackEntry.getKey();

                    if (CardAction.COMBO.equals(cardAction)) {
                        var comboCardId = UUID.randomUUID().toString();
                        for (int i = 0; i < amountOfCardsForAction; i++) {
                            if (i % 4 == 0) { // Each 4 Combo cards should have different ID
                                comboCardId = UUID.randomUUID().toString();
                            }
                            temporalGeneralStack.add(Card.builder()
                                    .cardActionId(comboCardId)
                                    .cardImage(null)
                                    .cardName(cardAction.name())
                                    .cardAction(cardAction)
                                    .build());
                        }
                    } else {
                        for (int i = 0; i < amountOfCardsForAction; i++) {
                            temporalGeneralStack.add(Card.builder()
                                    .cardActionId(cardId)
                                    .cardImage(null)
                                    .cardName(cardAction.name())
                                    .cardAction(cardAction)
                                    .build());
                        }
                    }

                });

        return temporalGeneralStack;
    }
}
