package ua.kostenko.expkitten.explodingkitten.api;

import ua.kostenko.expkitten.explodingkitten.models.card.Card;

public interface GameControllerApi {
    SessionRoom startGameSession(int numberOfPlayers);

    SessionRoom addUserToSession(String gameId, String username);

    SessionRoom beginGame(String gameId);

    SessionRoom getUpdatedInfo(String gameId);

    SessionRoom makeMove(String gameId, String playerName, MoveType type, Card card, String targetPlayerName);
}
