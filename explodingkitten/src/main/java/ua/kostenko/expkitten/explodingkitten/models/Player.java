package ua.kostenko.expkitten.explodingkitten.models;

import lombok.Builder;
import lombok.Data;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;

import java.util.List;

@Builder
@Data
public class Player {
    /**
     * Unique ID of the current game session
     */
    private final String gameSessionId;
    /**
     * Unique Player Name in the current game session
     */
    private final String playerName;
    /**
     * Cards that are available for player
     */
    private final List<Card> playerCards;
    /**
     * Indicates if player is still alive and can make moves
     */
    private boolean isAlive;
    /**
     * Indicates that player for this move can do any actions or not
     */
    private boolean isActive;

}
