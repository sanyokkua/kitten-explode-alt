package ua.kostenko.expkitten.explodingkitten.engine.processors.take;

import ua.kostenko.expkitten.explodingkitten.engine.MoveType;
import ua.kostenko.expkitten.explodingkitten.engine.processors.AbstractCardProcessor;
import ua.kostenko.expkitten.explodingkitten.engine.processors.ProcessCardModel;
import ua.kostenko.expkitten.explodingkitten.engine.tools.GameTools;
import ua.kostenko.expkitten.explodingkitten.models.Player;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;
import ua.kostenko.expkitten.explodingkitten.models.card.CardAction;

import java.util.Optional;

public class CatCardProcessor extends AbstractCardProcessor {
    @Override
    public void processCardModel(ProcessCardModel processCardModel) {
        if (processCardModel.getMoveType() == MoveType.TAKE_CARD) {
            Card currentCard = processCardModel.getCurrentCard();
            CardAction cardAction = currentCard.getCardAction();
            if (CardAction.CATS != cardAction) {
                next.process(processCardModel);
            } else {
                Player activePlayer = processCardModel.getActivePlayer();
                Optional<Card> defuseTemporal = activePlayer.getPlayerCards()
                        .stream()
                        .filter(p -> CardAction.DEFUSE == p.getCardAction())
                        .findAny();
                if (defuseTemporal.isEmpty()) {
                    activePlayer.setAlive(false);
                    GameTools.activateNextPlayer(activePlayer, processCardModel.getGameState());
                    activePlayer.setActive(false);
                } else {
                    processCardModel.getGameState().getDiscardPile().add(defuseTemporal.get());
                    activePlayer.getPlayerCards().remove(defuseTemporal.get());
                }
            }
        } else {
            next.process(processCardModel);
        }
    }
}
