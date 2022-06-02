package ua.kostenko.expkitten.explodingkitten.api.dto.request;

import lombok.Builder;
import lombok.Data;
import ua.kostenko.expkitten.explodingkitten.api.MoveType;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;

import java.util.List;

@Data
@Builder
public
class MakeMoveDto {
    private final String gameSessionId;
    private final String playerId;
    private final String targetPlayerName;
    private final MoveType type;
    private final Card card;
    private final List<Card> combo;
}
