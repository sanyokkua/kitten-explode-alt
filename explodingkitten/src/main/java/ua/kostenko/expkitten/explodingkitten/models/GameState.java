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

    private final String gameSessionId;
    private final PlayersList playersList;
    private final LinkedList<Card> cardDeck;
    private final LinkedList<Card> discardPile;
    private GameDirection gameDirection;
}
