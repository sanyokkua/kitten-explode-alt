package ua.kostenko.expkitten.explodingkitten.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ua.kostenko.expkitten.explodingkitten.engine.PlayersList;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;

import java.util.LinkedList;

@Slf4j
@Data
@AllArgsConstructor
public class GameState {

    /**
     * Unique ID of the current game session
     */
    private final String gameSessionId;
    /**
     * List of the all players for current game session
     */
    private final PlayersList playersList;
    /**
     * Deck with cards that can be taken by players
     */
    private final LinkedList<Card> cardDeck;
    /**
     * Deck with cards that were already played by players during moves
     */
    private final LinkedList<Card> discardPile;
    /**
     * Deck with cards that are playing by players during current move
     */
    private final LinkedList<Card> activeTable;
    /**
     * Direction of moves
     */
    private GameDirection gameDirection;
}
