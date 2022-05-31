package ua.kostenko.expkitten.explodingkitten.models;

import lombok.Builder;
import lombok.Data;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;

import java.util.List;

@Builder
@Data
public class Player {
    private final String gameId;
    private final String playerName;
    private final List<Card> playerCards;
    private boolean isAlive;
    private boolean isActive;
}
