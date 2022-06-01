package ua.kostenko.expkitten.explodingkitten.api.dto;

import lombok.Builder;
import lombok.Data;
import ua.kostenko.expkitten.explodingkitten.models.GameDirection;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;

import java.util.List;

@Data
@Builder
public class GameStateDto {
    private final String gameSessionId;
    private final List<PlayerInfoDto> players;
    private final int amountOfCardsInDeck;
    private final List<Card> discardPile;
    private final GameDirection gameDirection;
}
