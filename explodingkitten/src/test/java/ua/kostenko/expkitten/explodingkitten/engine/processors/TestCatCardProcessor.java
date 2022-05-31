package ua.kostenko.expkitten.explodingkitten.engine.processors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.kostenko.expkitten.explodingkitten.engine.GameDirector;
import ua.kostenko.expkitten.explodingkitten.models.Player;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;
import ua.kostenko.expkitten.explodingkitten.models.card.CardAction;
import ua.kostenko.expkitten.explodingkitten.models.deck.GameEdition;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCatCardProcessor {
    private GameDirector gd;
    private ProcessCardModel processCardModel;

    @BeforeEach
    public void setup() {
        gd = new GameDirector(GameEdition.ORIGINAL_EDITION, 4);

        gd.registerPlayer(gd.getGameId(), "player1");
        gd.registerPlayer(gd.getGameId(), "player2");
        gd.registerPlayer(gd.getGameId(), "player3");
        gd.registerPlayer(gd.getGameId(), "player4");

        gd.beginGame(gd.getGameId());

        Optional<Player> player2 = gd.getGameState().getPlayersList().getPlayers().stream().filter(p -> "player2".equals(p.getPlayerName())).findFirst();
        player2.get().setActive(true);

        Card cat = gd.getGameState().getCardDeck().stream().filter(c -> CardAction.CATS.equals(c.getCardAction())).findAny().get();

        processCardModel = ProcessCardModel.builder()
                .currentCard(cat)
                .activePlayer(player2.get())
                .gameState(gd.getGameState())
                .build();
    }

    @Test
    public void testWasDefused() {
        CatCardProcessor processor = new CatCardProcessor();

        processor.process(processCardModel);

        Optional<Player> player2 = gd.getGameState().getPlayersList().getPlayers().stream()
                .filter(p -> "player2".equals(p.getPlayerName()))
                .findFirst();

        assertTrue(player2.get().isAlive());
        assertFalse(player2.get().isActive());
        assertFalse(player2.get().getPlayerCards().stream().filter(c -> CardAction.CATS.equals(c.getCardAction())).findAny().isPresent());

        Optional<Player> player3 = gd.getGameState().getPlayersList().getPlayers().stream()
                .filter(p -> "player3".equals(p.getPlayerName()))
                .findFirst();

        assertTrue(player3.get().isAlive());
        assertTrue(player3.get().isActive());

        player2.get().setActive(true);
        player3.get().setActive(false);

        processor.process(processCardModel);

        Optional<Player> secondCatForPlayer2 = gd.getGameState().getPlayersList().getPlayers().stream()
                .filter(p -> "player2".equals(p.getPlayerName()))
                .findFirst();

        assertFalse(secondCatForPlayer2.get().isAlive());
        assertFalse(secondCatForPlayer2.get().isActive());

        Optional<Player> secondCatForPlayer2NextPlayer = gd.getGameState().getPlayersList().getPlayers().stream()
                .filter(p -> "player2".equals(p.getPlayerName()))
                .findFirst();

        assertTrue(player3.get().isAlive());
        assertTrue(player3.get().isActive());
    }
}
