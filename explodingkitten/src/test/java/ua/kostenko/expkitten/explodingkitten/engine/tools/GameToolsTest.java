package ua.kostenko.expkitten.explodingkitten.engine.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.kostenko.expkitten.explodingkitten.models.GameDirection;
import ua.kostenko.expkitten.explodingkitten.models.GameState;
import ua.kostenko.expkitten.explodingkitten.models.Player;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;
import ua.kostenko.expkitten.explodingkitten.models.card.CardAction;
import ua.kostenko.expkitten.explodingkitten.models.deck.GameEdition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ua.kostenko.expkitten.explodingkitten.engine.tools.GameFilterPredicates.IS_DEFUSE_PREDICATE;
import static ua.kostenko.expkitten.explodingkitten.engine.tools.GameFilterPredicates.IS_NOT_CAT_AND_NOT_DEFUSE;

public class GameToolsTest {
    private final String playerName1 = "Player-1";
    private final String playerName2 = "Player-2";
    private final String playerName3 = "Player-3";
    private final String playerName4 = "Player-4";
    GameState gameState;
    Map<CardAction, Integer> originalEdition;

    @BeforeEach
    public void beforeEach() {
        originalEdition = DeckGenerator.getDeckInformationForEdition(GameEdition.ORIGINAL_EDITION);
        String gameSessionId = "session-id-1";
        gameState = new GameState(gameSessionId,
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
    }

    @Test
    public void handOutCardsToPlayersWithoutDefuseTest() {
        LinkedList<Card> originalEditionDeck = new LinkedList<>(DeckGenerator.getDeckByPredicate(originalEdition, IS_NOT_CAT_AND_NOT_DEFUSE));

        GameTools.handOutCardsToPlayersWithoutDefuse(gameState, GameEdition.ORIGINAL_EDITION, originalEditionDeck);
        Optional<Player> p1 = gameState.getPlayersList().getPlayers().stream().filter(p -> playerName1.equals(p.getPlayerName())).findAny();
        Optional<Player> p2 = gameState.getPlayersList().getPlayers().stream().filter(p -> playerName2.equals(p.getPlayerName())).findAny();
        Optional<Player> p3 = gameState.getPlayersList().getPlayers().stream().filter(p -> playerName3.equals(p.getPlayerName())).findAny();
        Optional<Player> p4 = gameState.getPlayersList().getPlayers().stream().filter(p -> playerName4.equals(p.getPlayerName())).findAny();

        assertTrue(p1.isPresent());
        assertTrue(p2.isPresent());
        assertTrue(p3.isPresent());
        assertTrue(p4.isPresent());

        assertEquals(4, p1.get().getPlayerCards().size());
        assertEquals(4, p2.get().getPlayerCards().size());
        assertEquals(4, p3.get().getPlayerCards().size());
        assertEquals(4, p4.get().getPlayerCards().size());
    }

    @Test
    public void handOutDefuseCardsToPlayersTest() {
        LinkedList<Card> originalEditionDeck = new LinkedList<>(DeckGenerator.getDeckByPredicate(originalEdition, IS_DEFUSE_PREDICATE));

        GameTools.handOutDefuseCardsToPlayers(gameState, originalEditionDeck);
        Optional<Player> p1 = gameState.getPlayersList().getPlayers().stream().filter(p -> playerName1.equals(p.getPlayerName())).findAny();
        Optional<Player> p2 = gameState.getPlayersList().getPlayers().stream().filter(p -> playerName2.equals(p.getPlayerName())).findAny();
        Optional<Player> p3 = gameState.getPlayersList().getPlayers().stream().filter(p -> playerName3.equals(p.getPlayerName())).findAny();
        Optional<Player> p4 = gameState.getPlayersList().getPlayers().stream().filter(p -> playerName4.equals(p.getPlayerName())).findAny();

        assertTrue(p1.isPresent());
        assertTrue(p2.isPresent());
        assertTrue(p3.isPresent());
        assertTrue(p4.isPresent());

        assertEquals(1, p1.get().getPlayerCards().size());
        assertEquals(1, p2.get().getPlayerCards().size());
        assertEquals(1, p3.get().getPlayerCards().size());
        assertEquals(1, p4.get().getPlayerCards().size());
    }

    @Test
    public void putCatCardIntoDeckTest() {
        GameInitializer.initGameDeck(GameEdition.ORIGINAL_EDITION, gameState, playerName1);
        LinkedList<Card> cardDeck = gameState.getCardDeck();
        LinkedList<Card> cats = cardDeck.stream().filter(c -> CardAction.CATS.equals(c.getCardAction())).collect(Collectors.toCollection(LinkedList::new));
        cats.forEach(cardDeck::remove);

        assertEquals(3, cats.size());

        var cat1 = cats.pollFirst();
        var cat2 = cats.pollFirst();
        var cat3 = cats.pollFirst();

        assertNotNull(cat1);
        assertNotNull(cat2);
        assertNotNull(cat3);

        GameTools.putCatCardIntoDeck(gameState, cat1, 0);
        assertEquals(cat1, gameState.getCardDeck().get(0));

        GameTools.putCatCardIntoDeck(gameState, cat2, gameState.getCardDeck().size() - 1);
        assertEquals(cat2, gameState.getCardDeck().getLast());

        GameTools.putCatCardIntoDeck(gameState, cat3, 13);
        assertEquals(cat3, gameState.getCardDeck().get(13));
    }

    @Test
    public void testPutCatCardIntoDeckFailure() {
        GameInitializer.initGameDeck(GameEdition.ORIGINAL_EDITION, gameState, playerName1);
        LinkedList<Card> cardDeck = gameState.getCardDeck();
        LinkedList<Card> cats = cardDeck.stream().filter(c -> CardAction.CATS.equals(c.getCardAction())).collect(Collectors.toCollection(LinkedList::new));
        cats.forEach(cardDeck::remove);

        assertEquals(3, cats.size());

        var cat1 = cats.pollFirst();
        var cat2 = cats.pollFirst();
        var cat3 = cats.pollFirst();

        assertNotNull(cat1);
        assertNotNull(cat2);
        assertNotNull(cat3);

        Assertions.assertThrows(IllegalArgumentException.class, () -> GameTools.putCatCardIntoDeck(gameState, cat1, -1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> GameTools.putCatCardIntoDeck(gameState, cat1, gameState.getCardDeck().size()));
    }

    @Test
    public void testNextPlayerForward() {
        GameInitializer.initGameDeck(GameEdition.ORIGINAL_EDITION, gameState, playerName1);
        gameState.setGameDirection(GameDirection.FORWARD);

        gameState.getPlayersList().getPlayers().forEach(p -> p.setActive(false));
        Player player3 = gameState.getPlayersList().getPlayers().stream()
                .filter(p -> playerName3.equals(p.getPlayerName()))
                .findFirst()
                .get();
        player3.setActive(true);

        GameTools.activateNextPlayer(player3, gameState);

        Player player4 = gameState.getPlayersList().getPlayers().stream()
                .filter(p -> playerName4.equals(p.getPlayerName()))
                .findFirst()
                .get();
        assertFalse(player3.isActive());
        assertTrue(player4.isActive());
    }

    @Test
    public void testNextPlayerBackward() {
        GameInitializer.initGameDeck(GameEdition.ORIGINAL_EDITION, gameState, playerName1);
        gameState.setGameDirection(GameDirection.BACKWARD);

        gameState.getPlayersList().getPlayers().forEach(p -> p.setActive(false));
        Player player3 = gameState.getPlayersList().getPlayers().stream()
                .filter(p -> playerName3.equals(p.getPlayerName()))
                .findFirst()
                .get();
        player3.setActive(true);

        GameTools.activateNextPlayer(player3, gameState);

        Player player2 = gameState.getPlayersList().getPlayers().stream()
                .filter(p -> playerName2.equals(p.getPlayerName()))
                .findFirst()
                .get();
        assertFalse(player3.isActive());
        assertTrue(player2.isActive());
    }

    @Test
    public void testNextPlayerForwardWithNotAlive() {
        GameInitializer.initGameDeck(GameEdition.ORIGINAL_EDITION, gameState, playerName1);
        gameState.setGameDirection(GameDirection.FORWARD);

        gameState.getPlayersList().getPlayers().forEach(p -> p.setAlive(false));
        Player player1 = gameState.getPlayersList().getPlayers().stream()
                .filter(p -> playerName1.equals(p.getPlayerName()))
                .findFirst()
                .get();
        Player player2 = gameState.getPlayersList().getPlayers().stream()
                .filter(p -> playerName2.equals(p.getPlayerName()))
                .findFirst()
                .get();
        player1.setAlive(true);
        player2.setAlive(true);

        player2.setActive(true);

        GameTools.activateNextPlayer(player2, gameState);

        Player actualPl2 = gameState.getPlayersList().getPlayers().stream()
                .filter(p -> playerName2.equals(p.getPlayerName()))
                .findFirst()
                .get();
        Player actualPl1 = gameState.getPlayersList().getPlayers().stream()
                .filter(p -> playerName1.equals(p.getPlayerName()))
                .findFirst()
                .get();
        assertFalse(actualPl2.isActive());
        assertTrue(actualPl1.isActive());
    }

    @Test
    public void testNextPlayerBackwardWithNotAlive() {
        GameInitializer.initGameDeck(GameEdition.ORIGINAL_EDITION, gameState, playerName1);
        gameState.setGameDirection(GameDirection.BACKWARD);

        gameState.getPlayersList().getPlayers().forEach(p -> p.setAlive(false));
        Player player1 = gameState.getPlayersList().getPlayers().stream()
                .filter(p -> playerName1.equals(p.getPlayerName()))
                .findFirst()
                .get();
        Player player2 = gameState.getPlayersList().getPlayers().stream()
                .filter(p -> playerName2.equals(p.getPlayerName()))
                .findFirst()
                .get();
        player1.setAlive(true);
        player2.setAlive(true);

        player1.setActive(true);

        GameTools.activateNextPlayer(player1, gameState);

        Player actualPl2 = gameState.getPlayersList().getPlayers().stream()
                .filter(p -> playerName2.equals(p.getPlayerName()))
                .findFirst()
                .get();
        Player actualPl1 = gameState.getPlayersList().getPlayers().stream()
                .filter(p -> playerName1.equals(p.getPlayerName()))
                .findFirst()
                .get();
        assertFalse(actualPl1.isActive());
        assertTrue(actualPl2.isActive());
    }
}
