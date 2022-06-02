package ua.kostenko.expkitten.explodingkitten.engine.tools;

import ua.kostenko.expkitten.explodingkitten.models.GameState;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;
import ua.kostenko.expkitten.explodingkitten.models.deck.GameEdition;

import java.util.LinkedList;

public class GameTools {

    private GameTools() {
    }

    public static void handOutCardsToPlayersWithoutDefuse(GameState gameState, GameEdition gameEdition, LinkedList<Card> deck) {
        gameState.getPlayersList().forEach(player -> {
            for (int i = 0; i < gameEdition.getAmountOfCardsForPlayer(); i++) {
                player.getPlayerCards().add(deck.pollFirst());
            }
        });
    }

    public static void handOutDefuseCardsToPlayers(GameState gameState, LinkedList<Card> deck) {
        gameState.getPlayersList().forEach(player -> player.getPlayerCards().add(deck.pollFirst()));
    }

    public static void putCatCardIntoDeck(GameState gameState, Card card, int position) {
        int size = gameState.getCardDeck().size();
        if (position < 0 || position >= size) {
            throw new IllegalArgumentException("Position should be between 0 and deck size");
        }
        if (position == 0) {
            gameState.getCardDeck().addFirst(card);
        } else if (position == (gameState.getCardDeck().size() - 1)) {
            gameState.getCardDeck().addLast(card);
        } else {
            gameState.getCardDeck().add(position, card);
        }
    }
}
