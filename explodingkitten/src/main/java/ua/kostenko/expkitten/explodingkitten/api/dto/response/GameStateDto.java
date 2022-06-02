package ua.kostenko.expkitten.explodingkitten.api.dto.response;

import lombok.Builder;
import lombok.Data;
import ua.kostenko.expkitten.explodingkitten.models.GameDirection;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;

import java.util.List;

@Data
@Builder
public class GameStateDto {
    protected final String gameSessionId;
    protected final int amountOfCardsInDeck;
    protected final GameDirection gameDirection;
    protected final List<CommonPlayerInfoDto> players;
    protected final List<Card> discardPile;
}
