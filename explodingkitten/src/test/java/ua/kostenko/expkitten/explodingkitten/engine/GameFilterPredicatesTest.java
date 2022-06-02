package ua.kostenko.expkitten.explodingkitten.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.kostenko.expkitten.explodingkitten.models.card.CardAction;
import ua.kostenko.expkitten.explodingkitten.models.deck.GameEdition;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static ua.kostenko.expkitten.explodingkitten.engine.GameFilterPredicates.*;

public class GameFilterPredicatesTest {
    private Map<CardAction, Integer> originalEdition;

    @BeforeEach
    public void beforeAll() {
        originalEdition = DeckGenerator.getDeckInformationForEdition(GameEdition.ORIGINAL_EDITION);
    }

    @Test
    public void testIsCatPredicate() {
        var originalEditionDeck = DeckGenerator.getDeckByPredicate(originalEdition, IS_CAT_PREDICATE);
        var actualNumberOfCards = originalEditionDeck.size();
        var hasCombo = originalEditionDeck.stream().anyMatch(c -> CardAction.COMBO.equals(c.getCardAction()));
        var hasDefuse = originalEditionDeck.stream().anyMatch(c -> CardAction.DEFUSE.equals(c.getCardAction()));
        var hasCat = originalEditionDeck.stream().anyMatch(c -> CardAction.CATS.equals(c.getCardAction()));

        assertEquals(originalEdition.get(CardAction.CATS), actualNumberOfCards);
        assertFalse(hasCombo);
        assertFalse(hasDefuse);
        assertTrue(hasCat);
    }

    @Test
    public void testIsDefusePredicate() {
        var originalEditionDeck = DeckGenerator.getDeckByPredicate(originalEdition, IS_DEFUSE_PREDICATE);
        var actualNumberOfCards = originalEditionDeck.size();
        var hasCombo = originalEditionDeck.stream().anyMatch(c -> CardAction.COMBO.equals(c.getCardAction()));
        var hasCat = originalEditionDeck.stream().anyMatch(c -> CardAction.CATS.equals(c.getCardAction()));
        var hasDefuse = originalEditionDeck.stream().anyMatch(c -> CardAction.DEFUSE.equals(c.getCardAction()));

        assertEquals(originalEdition.get(CardAction.DEFUSE), actualNumberOfCards);
        assertFalse(hasCombo);
        assertFalse(hasCat);
        assertTrue(hasDefuse);
    }

    @Test
    public void testIsNotCatPredicate() {
        var originalEditionDeck = DeckGenerator.getDeckByPredicate(originalEdition, IS_NOT_CAT_PREDICATE);
        var actualNumberOfCards = originalEditionDeck.size();
        var hasCombo = originalEditionDeck.stream().anyMatch(c -> CardAction.COMBO.equals(c.getCardAction()));
        var hasDefuse = originalEditionDeck.stream().anyMatch(c -> CardAction.DEFUSE.equals(c.getCardAction()));
        var hasCat = originalEditionDeck.stream().anyMatch(c -> CardAction.CATS.equals(c.getCardAction()));

        assertEquals(52, actualNumberOfCards);
        assertTrue(hasCombo);
        assertTrue(hasDefuse);
        assertFalse(hasCat);
    }

    @Test
    public void testIsNotDefusePredicate() {
        var originalEditionDeck = DeckGenerator.getDeckByPredicate(originalEdition, IS_NOT_DEFUSE_PREDICATE);
        var actualNumberOfCards = originalEditionDeck.size();
        var hasCombo = originalEditionDeck.stream().anyMatch(c -> CardAction.COMBO.equals(c.getCardAction()));
        var hasDefuse = originalEditionDeck.stream().anyMatch(c -> CardAction.DEFUSE.equals(c.getCardAction()));
        var hasCat = originalEditionDeck.stream().anyMatch(c -> CardAction.CATS.equals(c.getCardAction()));

        assertEquals(50, actualNumberOfCards);
        assertTrue(hasCombo);
        assertFalse(hasDefuse);
        assertTrue(hasCat);
    }

    @Test
    public void testIsNotCatAndNotDefusePredicate() {
        var originalEditionDeck = DeckGenerator.getDeckByPredicate(originalEdition, IS_NOT_CAT_AND_NOT_DEFUSE);
        var actualNumberOfCards = originalEditionDeck.size();
        var hasCombo = originalEditionDeck.stream().anyMatch(c -> CardAction.COMBO.equals(c.getCardAction()));
        var hasDefuse = originalEditionDeck.stream().anyMatch(c -> CardAction.DEFUSE.equals(c.getCardAction()));
        var hasCat = originalEditionDeck.stream().anyMatch(c -> CardAction.CATS.equals(c.getCardAction()));

        assertEquals(46, actualNumberOfCards);
        assertTrue(hasCombo);
        assertFalse(hasDefuse);
        assertFalse(hasCat);
    }
}
