package ua.kostenko.expkitten.explodingkitten.engine.processors;

import ua.kostenko.expkitten.explodingkitten.models.Player;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;
import ua.kostenko.expkitten.explodingkitten.models.card.CardAction;

import java.util.Objects;
import java.util.Optional;

public class CatCardProcessor extends AbstractCardProcessor {
    @Override
    public void process(ProcessCardModel processCardModel) {
        if (Objects.isNull(processCardModel)) {
            throw new NullPointerException("processCardModel is null");
        }
        Card currentCard = processCardModel.getCurrentCard();
        CardAction cardAction = currentCard.getCardAction();
        if (CardAction.CATS.equals(cardAction)) {
            Player activePlayer = processCardModel.getActivePlayer();
            Optional<Card> defuse = activePlayer.getPlayerCards().stream()
                    .filter(c -> CardAction.DEFUSE.equals(c.getCardAction()))
                    .findAny();
            if (defuse.isPresent()) {
                Card card = defuse.get();
                activePlayer.getPlayerCards().remove(card);
                activateNextPlayer(activePlayer, processCardModel.getGameState());
            } else {
                activePlayer.setAlive(false);
                activateNextPlayer(activePlayer, processCardModel.getGameState());
            }
        } else {
            next.process(processCardModel);
        }
    }
}
