package ua.kostenko.expkitten.explodingkitten.engine.processors;

import ua.kostenko.expkitten.explodingkitten.engine.tools.PlayersList;
import ua.kostenko.expkitten.explodingkitten.models.GameDirection;
import ua.kostenko.expkitten.explodingkitten.models.GameState;
import ua.kostenko.expkitten.explodingkitten.models.Player;

public abstract class AbstractCardProcessor implements Processor {
    protected Processor next;

    @Override
    public void setNext(Processor processor) {
        this.next = processor;
    }

    public void activateNextPlayer(Player active, GameState state) {
        GameDirection gameDirection = state.getGameDirection();
        PlayersList playersList = state.getPlayersList();
        switch (gameDirection) {
            case FORWARD: {
                active.setActive(false);
                Player nextPlayer = playersList.getNext(active);
                if (nextPlayer.isAlive()) {
                    nextPlayer.setActive(true);
                } else {
                    activateNextPlayer(nextPlayer, state);
                }
                break;
            }
            case BACKWARD: {
                active.setActive(false);
                Player nextPlayer = playersList.getPrevious(active);
                if (nextPlayer.isAlive()) {
                    nextPlayer.setActive(true);
                } else {
                    activateNextPlayer(nextPlayer, state);
                }
                break;
            }
            default:
                throw new IllegalArgumentException();
        }
    }
}