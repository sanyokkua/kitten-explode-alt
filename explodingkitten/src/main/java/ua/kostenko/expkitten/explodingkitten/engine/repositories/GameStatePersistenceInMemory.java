package ua.kostenko.expkitten.explodingkitten.engine.repositories;

import org.springframework.stereotype.Repository;
import ua.kostenko.expkitten.explodingkitten.engine.GameStatePersistence;
import ua.kostenko.expkitten.explodingkitten.models.GameState;

import java.util.HashMap;
import java.util.Map;

@Repository
public class GameStatePersistenceInMemory implements GameStatePersistence {
    private final Map<String, GameState> inMemoryMap;

    public GameStatePersistenceInMemory() {
        this.inMemoryMap = new HashMap<>();
    }

    @Override
    public void saveGameState(GameState gameState) {
        var id = gameState.getGameSessionId();
        inMemoryMap.put(id, gameState);
    }

    @Override
    public GameState loadGameState(String gameSessionId) {
        return inMemoryMap.get(gameSessionId);
    }
}
