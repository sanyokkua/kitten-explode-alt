package ua.kostenko.expkitten.explodingkitten.api.dto.response;

import lombok.Builder;
import lombok.Data;
import ua.kostenko.expkitten.explodingkitten.models.GameDirection;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;

import java.util.List;

@Data
@Builder
public class GameStateWithPlayerIdDto {
    private final String gameSessionId;
    private final String playerId;
    private final int amountOfCardsInDeck;
    private final GameDirection gameDirection;
    private final List<CommonPlayerInfoDto> players;
    private final List<Card> discardPile;
}
