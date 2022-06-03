package ua.kostenko.expkitten.explodingkitten.engine.processors.take;

import ua.kostenko.expkitten.explodingkitten.engine.MoveType;
import ua.kostenko.expkitten.explodingkitten.engine.processors.AbstractCardProcessor;
import ua.kostenko.expkitten.explodingkitten.engine.processors.ProcessCardModel;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;
import ua.kostenko.expkitten.explodingkitten.models.card.CardAction;

public class TakeTheCardProcessor extends AbstractCardProcessor {
    @Override
    public void processCardModel(ProcessCardModel processCardModel) {
        if (processCardModel.getMoveType() == MoveType.TAKE_CARD) {
            Card currentCard = processCardModel.getCurrentCard();
            CardAction cardAction = currentCard.getCardAction();
            if (CardAction.CATS == cardAction) {
                next.process(processCardModel);
            } else {

            }
        } else {
            next.process(processCardModel);
        }
    }
}
