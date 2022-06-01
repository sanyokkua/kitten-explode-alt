package ua.kostenko.expkitten.explodingkitten.api;

import ua.kostenko.expkitten.explodingkitten.api.dto.GameStateDto;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;
import ua.kostenko.expkitten.explodingkitten.models.deck.GameEdition;

import java.util.List;

public interface GameControllerApi {
    GameStateDto startGameSession(int numberOfPlayers, GameEdition gameEdition);

    GameStateDto addUserToSession(String gameSessionId, String username);

    GameStateDto beginGame(String gameSessionId, String playerName, GameEdition gameEdition);

    GameStateDto getUpdatedInfo(String gameSessionId);

    GameStateDto makeMove(String gameSessionId, String playerName, MoveType type, Card card, String targetPlayerName);

    List<GameEdition> getGameEditions();

}
