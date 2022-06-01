package ua.kostenko.expkitten.explodingkitten.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ua.kostenko.expkitten.explodingkitten.api.GameControllerApi;
import ua.kostenko.expkitten.explodingkitten.api.GameStatePersistence;
import ua.kostenko.expkitten.explodingkitten.api.MoveType;
import ua.kostenko.expkitten.explodingkitten.api.dto.GameStateDto;
import ua.kostenko.expkitten.explodingkitten.api.dto.PlayerInfoDto;
import ua.kostenko.expkitten.explodingkitten.engine.GameInitializer;
import ua.kostenko.expkitten.explodingkitten.engine.PlayersList;
import ua.kostenko.expkitten.explodingkitten.models.GameDirection;
import ua.kostenko.expkitten.explodingkitten.models.GameState;
import ua.kostenko.expkitten.explodingkitten.models.Player;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;
import ua.kostenko.expkitten.explodingkitten.models.deck.GameEdition;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GameController implements GameControllerApi {
    private final GameStatePersistence gameStatePersistence;

    @Override
    public GameStateDto startGameSession(int numberOfPlayers, GameEdition gameEdition) {
        GameState gameState = new GameState(UUID.randomUUID().toString(),
                new PlayersList(),
                new LinkedList<>(),
                new LinkedList<>(),
                GameDirection.FORWARD);
        gameStatePersistence.saveGameState(gameState);
        return buildGameStateDto(gameState);
    }

    @Override
    public GameStateDto addUserToSession(String gameSessionId, String playerName) {
        validateGameSessionId(gameSessionId);
        validatePlayerName(playerName);

        GameState gameState = gameStatePersistence.loadGameState(gameSessionId);
        validateGameState(gameState);

        gameState.getPlayersList().addPlayer(Player.builder()
                .isActive(false)
                .isAlive(true)
                .playerName(playerName)
                .playerCards(new ArrayList<>())
                .gameSessionId(gameState.getGameSessionId())
                .build());
        gameStatePersistence.saveGameState(gameState);
        return buildGameStateDto(gameState);
    }

    @Override
    public GameStateDto beginGame(String gameSessionId, String playerName, GameEdition gameEdition) {
        validateGameSessionId(gameSessionId);
        validatePlayerName(playerName);

        GameState gameState = gameStatePersistence.loadGameState(gameSessionId);
        validateGameState(gameState);

        if (gameState.getPlayersList().getPlayers().size() > 1) {
            GameInitializer.initGameDeck(gameEdition, gameState, playerName);
        } else {
            throw new IllegalStateException("Not enough players to start the game");
        }

        gameStatePersistence.saveGameState(gameState);
        return buildGameStateDto(gameState);
    }

    @Override
    public GameStateDto getUpdatedInfo(String gameSessionId) {
        validateGameSessionId(gameSessionId);
        GameState gameState = gameStatePersistence.loadGameState(gameSessionId);
        return buildGameStateDto(gameState);
    }

    @Override
    public GameStateDto makeMove(String gameSessionId, String playerName, MoveType type, Card card, String targetPlayerName) {
        validateGameSessionId(gameSessionId);
        validatePlayerName(playerName);

        return null;
    }

    @Override
    public List<GameEdition> getGameEditions() {
        return List.of(GameEdition.values());
    }

    private GameStateDto buildGameStateDto(GameState gameState) {
        return GameStateDto.builder()
                .gameSessionId(gameState.getGameSessionId())
                .gameDirection(gameState.getGameDirection())
                .amountOfCardsInDeck(gameState.getCardDeck().size())
                .discardPile(gameState.getDiscardPile())
                .players(gameState.getPlayersList().getPlayers().stream().map(player -> PlayerInfoDto.builder()
                        .amountOfCards(player.getPlayerCards().size())
                        .isActive(player.isActive())
                        .isAlive(player.isAlive())
                        .playerName(player.getPlayerName())
                        .build()).collect(Collectors.toList()))
                .build();
    }

    private void validatePlayerName(String playerName) {
        if (Objects.isNull(playerName) || playerName.isBlank()) {
            throw new IllegalArgumentException("Player Name can't be blank");
        }
    }

    private void validateGameSessionId(String gameSessionId) {
        if (Objects.isNull(gameSessionId) || gameSessionId.isBlank()) {
            throw new IllegalArgumentException("gameSessionId can't be blank");
        }
    }

    private void validateGameState(GameState gameState) {
        if (Objects.isNull(gameState)) {
            throw new IllegalArgumentException("gameSession is not exists");
        }
    }
}
