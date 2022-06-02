package ua.kostenko.expkitten.explodingkitten.api.dto;

import lombok.Builder;
import lombok.Data;
import ua.kostenko.expkitten.explodingkitten.api.MoveType;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;

@Data
@Builder
public
class MakeMoveDto {
    private final String gameSessionId;
    private final String playerName;
    private final String targetPlayerName;
    private final MoveType type;
    private final Card card;
}
