package ua.kostenko.expkitten.explodingkitten.engine.tools;

import org.junit.jupiter.api.Test;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;
import ua.kostenko.expkitten.explodingkitten.models.card.CardAction;
import ua.kostenko.expkitten.explodingkitten.models.deck.GameEdition;

import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static ua.kostenko.expkitten.explodingkitten.engine.tools.GameFilterPredicates.*;

public class DeckGeneratorTest {

    @Test
    public void testGetDeckInformationForEdition() {
        Map<CardAction, Integer> originalEdition = DeckGenerator.getDeckInformationForEdition(GameEdition.ORIGINAL_EDITION);
        int originalEditionCount = originalEdition.values().stream().mapToInt(val -> val).sum();

        assertEquals(56, originalEditionCount);

        Map<CardAction, Integer> nsfwEdition = DeckGenerator.getDeckInformationForEdition(GameEdition.NSFW_EDITION);
        int nsfwEditionCount = nsfwEdition.values().stream().mapToInt(val -> val).sum();

        assertEquals(76, nsfwEditionCount);

        assertEquals(4, originalEdition.get(CardAction.CATS));
        assertEquals(5, nsfwEdition.get(CardAction.CATS));

        assertEquals(20, originalEdition.get(CardAction.COMBO));
        assertNull(originalEdition.get(CardAction.COMBO_JOKER));
        assertEquals(4, nsfwEdition.get(CardAction.COMBO_JOKER));
    }

    @Test
    public void testGetDeckByPredicate() {
        Map<CardAction, Integer> originalEdition = DeckGenerator.getDeckInformationForEdition(GameEdition.ORIGINAL_EDITION);
        LinkedList<Card> originalEditionDeck = new LinkedList<>(DeckGenerator.getDeckByPredicate(originalEdition, IS_NOT_CAT_AND_NOT_DEFUSE));
        LinkedList<Card> originalEditionDefuseDeck = new LinkedList<>(DeckGenerator.getDeckByPredicate(originalEdition, IS_DEFUSE_PREDICATE));
        LinkedList<Card> originalEditionCatsDeck = new LinkedList<>(DeckGenerator.getDeckByPredicate(originalEdition, IS_CAT_PREDICATE));

        assertEquals(46, originalEditionDeck.size());
        assertTrue(originalEditionDeck.stream().filter(card -> CardAction.DEFUSE.equals(card.getCardAction())).findAny().isEmpty());
        assertTrue(originalEditionDeck.stream().filter(card -> CardAction.CATS.equals(card.getCardAction())).findAny().isEmpty());
        assertEquals(6, originalEditionDefuseDeck.size());
        assertEquals(4, originalEditionCatsDeck.size());

        Map<CardAction, Integer> nsfwEdition = DeckGenerator.getDeckInformationForEdition(GameEdition.NSFW_EDITION);
        LinkedList<Card> nsfwEditionDeck = new LinkedList<>(DeckGenerator.getDeckByPredicate(nsfwEdition, IS_NOT_CAT_AND_NOT_DEFUSE));
        LinkedList<Card> nsfwEditionDefuseDeck = new LinkedList<>(DeckGenerator.getDeckByPredicate(nsfwEdition, IS_DEFUSE_PREDICATE));
        LinkedList<Card> nsfwEditionCatsDeck = new LinkedList<>(DeckGenerator.getDeckByPredicate(nsfwEdition, IS_CAT_PREDICATE));

        assertEquals(65, nsfwEditionDeck.size());
        assertTrue(nsfwEditionDeck.stream().filter(card -> CardAction.DEFUSE.equals(card.getCardAction())).findAny().isEmpty());
        assertTrue(nsfwEditionDeck.stream().filter(card -> CardAction.CATS.equals(card.getCardAction())).findAny().isEmpty());
        assertEquals(6, nsfwEditionDefuseDeck.size());
        assertEquals(5, nsfwEditionCatsDeck.size());
    }

} 
