package ua.kostenko.expkitten.explodingkitten.engine.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.kostenko.expkitten.explodingkitten.models.GameDirection;
import ua.kostenko.expkitten.explodingkitten.models.GameState;
import ua.kostenko.expkitten.explodingkitten.models.Player;
import ua.kostenko.expkitten.explodingkitten.models.card.CardAction;
import ua.kostenko.expkitten.explodingkitten.models.deck.GameEdition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameInitializerTest {

    @Test
    public void testValidation() {
        Assertions.assertThrows(NullPointerException.class, () -> GameInitializer.initGameDeck(null,
                new GameState("sessionId",
                        new PlayersList(),
                        new LinkedList<>(),
                        new LinkedList<>(),
                        new LinkedList<>(),
                        GameDirection.FORWARD),
                "player1"));
        Assertions.assertThrows(NullPointerException.class, () -> GameInitializer.initGameDeck(GameEdition.ORIGINAL_EDITION,
                null,
                "player1"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> GameInitializer.initGameDeck(GameEdition.ORIGINAL_EDITION,
                new GameState("sessionId",
                        new PlayersList(),
                        new LinkedList<>(),
                        new LinkedList<>(),
                        new LinkedList<>(),
                        GameDirection.FORWARD),
                null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> GameInitializer.initGameDeck(GameEdition.ORIGINAL_EDITION,
                new GameState("sessionId",
                        new PlayersList(),
                        new LinkedList<>(),
                        new LinkedList<>(),
                        new LinkedList<>(),
                        GameDirection.FORWARD),
                "  "));
        Assertions.assertThrows(IllegalArgumentException.class, () -> GameInitializer.initGameDeck(GameEdition.ORIGINAL_EDITION,
                new GameState("sessionId",
                        new PlayersList(),
                        new LinkedList<>(),
                        new LinkedList<>(),
                        new LinkedList<>(),
                        GameDirection.FORWARD),
                "player2"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            GameState gameState = new GameState("gameSessionId",
                    new PlayersList(),
                    new LinkedList<>(),
                    new LinkedList<>(),
                    new LinkedList<>(),
                    GameDirection.FORWARD);
            gameState.getPlayersList().addPlayer(
                    Player.builder()
                            .isActive(false)
                            .isAlive(true)
                            .playerName("player2")
                            .playerCards(new ArrayList<>())
                            .gameSessionId(gameState.getGameSessionId())
                            .build()
            );
            GameInitializer.initGameDeck(GameEdition.ORIGINAL_EDITION,
                    gameState,
                    "player2");
        });


    }

    @Test
    public void testInitializationWithAppropriateParamsFor4Players() {
        final int numberOfPlayers = 4;
        final int numberOfCardsInPlayerForOriginalEdition = 5;
        final int numberOfCardsInDeckFor4Players = 55 - numberOfPlayers * numberOfCardsInPlayerForOriginalEdition; //For 5-6 players = 56
        String gameSessionId = "session-id-1";
        String playerName1 = "Player-1";
        String playerName2 = "Player-2";
        String playerName3 = "Player-3";
        String playerName4 = "Player-4";
        GameState gameState = new GameState(gameSessionId,
                new PlayersList(),
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                GameDirection.FORWARD);
        gameState.getPlayersList().addPlayer(
                Player.builder()
                        .isActive(false)
                        .isAlive(true)
                        .playerName(playerName1)
                        .playerCards(new ArrayList<>())
                        .gameSessionId(gameState.getGameSessionId())
                        .build()
        );
        gameState.getPlayersList().addPlayer(
                Player.builder()
                        .isActive(false)
                        .isAlive(true)
                        .playerName(playerName2)
                        .playerCards(new ArrayList<>())
                        .gameSessionId(gameState.getGameSessionId())
                        .build()
        );
        gameState.getPlayersList().addPlayer(
                Player.builder()
                        .isActive(false)
                        .isAlive(true)
                        .playerName(playerName3)
                        .playerCards(new ArrayList<>())
                        .gameSessionId(gameState.getGameSessionId())
                        .build()
        );
        gameState.getPlayersList().addPlayer(
                Player.builder()
                        .isActive(false)
                        .isAlive(true)
                        .playerName(playerName4)
                        .playerCards(new ArrayList<>())
                        .gameSessionId(gameState.getGameSessionId())
                        .build()
        );
        GameInitializer.initGameDeck(
                GameEdition.ORIGINAL_EDITION,
                gameState,
                playerName2);

        long cardsInPlayers = gameState.getPlayersList().getPlayers().stream()
                .map(Player::getPlayerCards)
                .mapToLong(Collection::size)
                .sum();
        long cardsInDeck = gameState.getCardDeck().size();

        assertEquals(numberOfPlayers * numberOfCardsInPlayerForOriginalEdition, cardsInPlayers);
        assertEquals(numberOfCardsInDeckFor4Players, cardsInDeck);
        assertEquals(3, gameState.getCardDeck().stream().filter(c -> CardAction.CATS.equals(c.getCardAction())).count());
        assertEquals(2, gameState.getCardDeck().stream().filter(c -> CardAction.DEFUSE.equals(c.getCardAction())).count());
        long actualCountOfDefuse = gameState.getPlayersList().getPlayers().stream()
                .map(Player::getPlayerCards)
                .flatMap(Collection::stream)
                .filter(c -> CardAction.DEFUSE.equals(c.getCardAction()))
                .count();
        assertTrue(actualCountOfDefuse >= 4);

        Optional<Player> player2Optional = gameState.getPlayersList().getPlayers().stream()
                .filter(pl -> playerName2.equals(pl.getPlayerName()))
                .findAny();
        assertTrue(player2Optional.isPresent());
        assertTrue(player2Optional.get().isActive());
        assertTrue(player2Optional.get().isAlive());
    }


}
