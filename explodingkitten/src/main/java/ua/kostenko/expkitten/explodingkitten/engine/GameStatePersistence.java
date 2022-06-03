package ua.kostenko.expkitten.explodingkitten.engine;

import ua.kostenko.expkitten.explodingkitten.models.GameState;

public interface GameStatePersistence {
    void saveGameState(GameState gameState);

    GameState loadGameState(String gameSessionId);
}
