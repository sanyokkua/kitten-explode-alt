package ua.kostenko.expkitten.explodingkitten.api.dto.response;

import lombok.Builder;
import lombok.Data;
import ua.kostenko.expkitten.explodingkitten.api.MoveType;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;

import java.util.List;

@Data
@Builder
public class PlayerFullInfoDto {
    private final String playerName;
    private final int amountOfCards;
    private final boolean isActive;
    private final boolean isAlive;
    private final List<Card> playerCards;
    private final List<MoveType> possibleMoves;
}
