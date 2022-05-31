package ua.kostenko.expkitten.explodingkitten.engine.processors;

import ua.kostenko.expkitten.explodingkitten.models.GameDirection;
import ua.kostenko.expkitten.explodingkitten.models.GameState;
import ua.kostenko.expkitten.explodingkitten.models.Player;

import java.util.LinkedList;

public abstract class AbstractCardProcessor implements Processor {
    protected Processor next;

    @Override
    public void setNext(Processor processor) {
        this.next = processor;
    }

    public void activateNextPlayer(Player active, GameState state) {
        GameDirection gameDirection = state.getGameDirection();
        LinkedList<Player> playersList = state.getPlayersList();
    }
}