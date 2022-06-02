package ua.kostenko.expkitten.explodingkitten.engine.processors;

import ua.kostenko.expkitten.explodingkitten.api.MoveType;
import ua.kostenko.expkitten.explodingkitten.models.GameState;
import ua.kostenko.expkitten.explodingkitten.models.Player;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;
import ua.kostenko.expkitten.explodingkitten.models.card.CardAction;

import java.util.Objects;

public class TakeTheCardProcessor extends AbstractCardProcessor {
    @Override
    public void process(ProcessCardModel processCardModel) {
        if (Objects.isNull(processCardModel)) {
            throw new NullPointerException("processCardModel is null");
        }
        Player activePlayer = processCardModel.getActivePlayer();
        Card currentCard = processCardModel.getCurrentCard();
        GameState gameState = processCardModel.getGameState();
        MoveType moveType = processCardModel.getMoveType();

        if (moveType == MoveType.TAKE_CARD) {
            if (CardAction.CATS.equals(currentCard.getCardAction())) {
                next.process(processCardModel);
            } else {
                activePlayer.getPlayerCards().add(currentCard);
                activateNextPlayer(activePlayer, gameState);
            }
        } else {
            next.process(processCardModel);
        }

    }
}
