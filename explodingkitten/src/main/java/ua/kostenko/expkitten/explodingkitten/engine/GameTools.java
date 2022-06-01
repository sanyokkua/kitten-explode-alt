package ua.kostenko.expkitten.explodingkitten.engine;

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
}
