package ua.kostenko.expkitten.explodingkitten.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.kostenko.expkitten.explodingkitten.api.GameControllerApi;
import ua.kostenko.expkitten.explodingkitten.api.GameStatePersistence;
import ua.kostenko.expkitten.explodingkitten.api.MoveType;
import ua.kostenko.expkitten.explodingkitten.api.dto.request.AddPlayerToSessionDto;
import ua.kostenko.expkitten.explodingkitten.api.dto.request.BeginGameDto;
import ua.kostenko.expkitten.explodingkitten.api.dto.request.GetInfoForPlayer;
import ua.kostenko.expkitten.explodingkitten.api.dto.request.MakeMoveDto;
import ua.kostenko.expkitten.explodingkitten.api.dto.response.CommonPlayerInfoDto;
import ua.kostenko.expkitten.explodingkitten.api.dto.response.GameStateDto;
import ua.kostenko.expkitten.explodingkitten.api.dto.response.GameStateWithPlayerIdDto;
import ua.kostenko.expkitten.explodingkitten.api.dto.response.PlayerFullInfoDto;
import ua.kostenko.expkitten.explodingkitten.engine.processors.ProcessCardModel;
import ua.kostenko.expkitten.explodingkitten.engine.processors.Processor;
import ua.kostenko.expkitten.explodingkitten.engine.tools.GameInitializer;
import ua.kostenko.expkitten.explodingkitten.engine.tools.PlayersList;
import ua.kostenko.expkitten.explodingkitten.models.GameDirection;
import ua.kostenko.expkitten.explodingkitten.models.GameState;
import ua.kostenko.expkitten.explodingkitten.models.Player;
import ua.kostenko.expkitten.explodingkitten.models.deck.GameEdition;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GameController implements GameControllerApi {
    private final GameStatePersistence gameStatePersistence;
    private final Processor moveProcessor;

    @PostMapping("/startGameSession")
    @Override
    public GameStateDto startGameSession() {
        GameState gameState = new GameState(UUID.randomUUID().toString(),
                new PlayersList(),
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                GameDirection.FORWARD);
        gameStatePersistence.saveGameState(gameState);
        return buildGameStateDto(gameState);
    }

    @PostMapping("/addUserToSession")
    @Override
    public GameStateWithPlayerIdDto addPlayerToSession(@RequestBody AddPlayerToSessionDto addPlayerToSessionDto) {
        validateGameSessionId(addPlayerToSessionDto.getGameSessionId());
        validatePlayerNameOrId(addPlayerToSessionDto.getPlayerName());

        GameState gameState = gameStatePersistence.loadGameState(addPlayerToSessionDto.getGameSessionId());
        validateGameState(gameState);

        String playerId = UUID.randomUUID().toString();
        gameState.getPlayersList().addPlayer(Player.builder()
                .isActive(false)
                .isAlive(true)
                .playerName(addPlayerToSessionDto.getPlayerName())
                .playerCards(new ArrayList<>())
                .gameSessionId(gameState.getGameSessionId())
                .playerId(playerId)
                .build());
        gameStatePersistence.saveGameState(gameState);
        return buildGameStateWithPlayerIdDto(gameState, playerId);
    }

    @PostMapping("/beginGame")
    @Override
    public GameStateDto beginGame(@RequestBody BeginGameDto beginGameDto) {
        validateGameSessionId(beginGameDto.getGameSessionId());
        validatePlayerNameOrId(beginGameDto.getPlayerName());

        GameState gameState = gameStatePersistence.loadGameState(beginGameDto.getGameSessionId());
        validateGameState(gameState);

        if (gameState.getPlayersList().getPlayers().size() > 1) {
            GameInitializer.initGameDeck(beginGameDto.getGameEdition(), gameState, beginGameDto.getPlayerName());
        } else {
            throw new IllegalStateException("Not enough players to start the game");
        }

        gameStatePersistence.saveGameState(gameState);
        return buildGameStateDto(gameState);
    }

    @GetMapping("/getUpdatedInfo/{gameSessionId}")
    @Override
    public GameStateDto getUpdatedInfo(@PathVariable("gameSessionId") String gameSessionId) {
        validateGameSessionId(gameSessionId);
        GameState gameState = gameStatePersistence.loadGameState(gameSessionId);
        return buildGameStateDto(gameState);
    }

    @PostMapping("/makeMove")
    @Override
    public GameStateDto makeMove(@RequestBody MakeMoveDto makeMoveDto) {
        validateGameSessionId(makeMoveDto.getGameSessionId());
        validatePlayerNameOrId(makeMoveDto.getPlayerId());
        GameState gameState = gameStatePersistence.loadGameState(makeMoveDto.getGameSessionId());
        moveProcessor.process(ProcessCardModel.builder()
                .gameState(gameState)
                .activePlayer(gameState.getPlayersList().getPlayerById(makeMoveDto.getPlayerId()))
                .currentCard(makeMoveDto.getCard())
                .targetPlayerName(makeMoveDto.getTargetPlayerName())
                .moveType(makeMoveDto.getType())
                .build());
        gameStatePersistence.saveGameState(gameState);
        return buildGameStateDto(gameState);
    }

    @PostMapping("/getInfoForPlayer")
    @Override
    public PlayerFullInfoDto getInfoForPlayer(@RequestBody GetInfoForPlayer getInfoForPlayer) {
        validateGameSessionId(getInfoForPlayer.getGameSessionId());
        validatePlayerNameOrId(getInfoForPlayer.getPlayerId());
        GameState gameState = gameStatePersistence.loadGameState(getInfoForPlayer.getGameSessionId());
        Player playerById = gameState.getPlayersList().getPlayerById(getInfoForPlayer.getPlayerId());
        return PlayerFullInfoDto.builder()
                .playerName(playerById.getPlayerName())
                .isActive(playerById.isActive())
                .isAlive(playerById.isAlive())
                .playerCards(playerById.getPlayerCards())
                .amountOfCards(playerById.getPlayerCards().size())
                .possibleMoves(List.of(MoveType.TAKE_CARD))
                .build();
    }

    @GetMapping("/getGameEditions")
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
                .players(gameState.getPlayersList().getPlayers().stream().map(player -> CommonPlayerInfoDto.builder()
                        .amountOfCards(player.getPlayerCards().size())
                        .isActive(player.isActive())
                        .isAlive(player.isAlive())
                        .playerName(player.getPlayerName())
                        .build()).collect(Collectors.toList()))
                .build();
    }

    private GameStateWithPlayerIdDto buildGameStateWithPlayerIdDto(GameState gameState, String playerId) {
        return GameStateWithPlayerIdDto.builder()
                .gameSessionId(gameState.getGameSessionId())
                .gameDirection(gameState.getGameDirection())
                .amountOfCardsInDeck(gameState.getCardDeck().size())
                .discardPile(gameState.getDiscardPile())
                .players(gameState.getPlayersList().getPlayers().stream().map(player -> CommonPlayerInfoDto.builder()
                        .amountOfCards(player.getPlayerCards().size())
                        .isActive(player.isActive())
                        .isAlive(player.isAlive())
                        .playerName(player.getPlayerName())
                        .build()).collect(Collectors.toList()))
                .playerId(playerId)
                .build();
    }

    private void validatePlayerNameOrId(String playerName) {
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
