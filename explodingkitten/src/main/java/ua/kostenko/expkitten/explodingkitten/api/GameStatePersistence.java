package ua.kostenko.expkitten.explodingkitten.api;

import ua.kostenko.expkitten.explodingkitten.models.GameState;

public interface GameStatePersistence {
    void saveGameState(GameState gameState);

    GameState loadGameState(String gameSessionId);
}
