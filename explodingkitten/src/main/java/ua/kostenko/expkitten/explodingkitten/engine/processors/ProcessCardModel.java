package ua.kostenko.expkitten.explodingkitten.engine.processors;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ua.kostenko.expkitten.explodingkitten.engine.MoveType;
import ua.kostenko.expkitten.explodingkitten.models.GameState;
import ua.kostenko.expkitten.explodingkitten.models.Player;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;

@Data
@Builder
@RequiredArgsConstructor
public class ProcessCardModel {
    private final Player activePlayer;
    private final Card currentCard;
    private final GameState gameState;
    private final String targetPlayerName;
    private final MoveType moveType;
}
