package ua.kostenko.expkitten.explodingkitten.engine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.kostenko.expkitten.explodingkitten.models.Player;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerListTest {

    @Test
    public void testCreationOfList() {
        PlayersList list = new PlayersList();
        assertTrue(Objects.nonNull(list.getPlayers()));
    }

    @Test
    public void testAddingPlayersToList() {
        PlayersList list = new PlayersList();
        Player player1 = Player.builder().playerName("player1").build();
        Player player2 = Player.builder().playerName("player2").build();
        Player player3 = Player.builder().playerName("player3").build();
        Player player4 = Player.builder().playerName("player4").build();

        list.addPlayer(player1);
        list.addPlayer(player2);
        list.addPlayer(player3);
        list.addPlayer(player4);

        assertEquals(4, list.getPlayers().size());
    }

    @Test
    public void testProperPlayersIndexes() {
        PlayersList list = new PlayersList();
        Player player1 = Player.builder().playerName("player1").build();
        Player player2 = Player.builder().playerName("player2").build();
        Player player3 = Player.builder().playerName("player3").build();
        Player player4 = Player.builder().playerName("player4").build();

        list.addPlayer(player1);
        list.addPlayer(player2);
        list.addPlayer(player3);
        list.addPlayer(player4);

        Player actualPlayer1 = list.getPlayers().get(0);
        Player actualPlayer2 = list.getPlayers().get(1);
        Player actualPlayer3 = list.getPlayers().get(2);
        Player actualPlayer4 = list.getPlayers().get(3);

        assertEquals("player1", actualPlayer1.getPlayerName());
        assertEquals("player2", actualPlayer2.getPlayerName());
        assertEquals("player3", actualPlayer3.getPlayerName());
        assertEquals("player4", actualPlayer4.getPlayerName());
    }

    @Test
    public void testNextAndPrevious() {
        PlayersList list = new PlayersList();
        Player player1 = Player.builder().playerName("player1").build();
        Player player2 = Player.builder().playerName("player2").build();
        Player player3 = Player.builder().playerName("player3").build();

        list.addPlayer(player1);
        list.addPlayer(player2);
        list.addPlayer(player3);

        Player actualPlayer2 = list.getNext(player1);
        Player actualPlayer1 = list.getNext(player3);

        Player actualPrevPlayer3 = list.getPrevious(player1);
        Player actualPrevPlayer1 = list.getPrevious(player2);
        Player actualPrevPlayer2 = list.getPrevious(player3);

        assertEquals(player1, actualPlayer1);
        assertEquals(player2, actualPlayer2);

        assertEquals(player1, actualPrevPlayer1);
        assertEquals(player2, actualPrevPlayer2);
        assertEquals(player3, actualPrevPlayer3);
    }

    @Test
    public void testGettingIterator() {
        PlayersList list = new PlayersList();
        Player player1 = Player.builder().playerName("player1").build();
        Player player2 = Player.builder().playerName("player2").build();
        list.addPlayer(player1);
        list.addPlayer(player2);

        Iterator<Player> iterator = list.iterator();

        assertNotNull(iterator);

        Player actualPl1 = iterator.next();
        Player actualPl2 = iterator.next();

        assertEquals(player1, actualPl1);
        assertEquals(player2, actualPl2);
    }

    @Test
    public void testForEach() {
        PlayersList list = new PlayersList();
        Player player1 = Player.builder().playerName("player1").build();
        Player player2 = Player.builder().playerName("player2").build();
        list.addPlayer(player1);
        list.addPlayer(player2);

        list.forEach(pl -> pl.setActive(true));

        assertTrue(list.getPlayers().stream().filter(pl -> "player1".equals(pl.getPlayerName())).findFirst().get().isActive());
        assertTrue(list.getPlayers().stream().filter(pl -> "player2".equals(pl.getPlayerName())).findFirst().get().isActive());
    }

    @Test
    public void testGettingSpliterator() {
        PlayersList list = new PlayersList();
        Player player1 = Player.builder().playerName("player1").build();
        Player player2 = Player.builder().playerName("player2").build();
        list.addPlayer(player1);
        list.addPlayer(player2);

        Spliterator<Player> spliterator = list.spliterator();

        assertNotNull(spliterator);
    }

    @Test
    public void testGetPlayerByName() {
        PlayersList list = new PlayersList();
        Player player1 = Player.builder().playerName("player1").build();
        Player player2 = Player.builder().playerName("player2").build();
        Player player3 = Player.builder().playerName("player3").build();

        list.addPlayer(player1);
        list.addPlayer(player2);
        list.addPlayer(player3);

        Player actualPl1 = list.getPlayerByName("player1");
        Player actualPl2 = list.getPlayerByName("player2");
        Player actualPl3 = list.getPlayerByName("player3");

        assertEquals(player1, actualPl1);
        assertEquals(player2, actualPl2);
        assertEquals(player3, actualPl3);
    }

    @Test
    public void testNextValidation() {
        PlayersList list = new PlayersList();
        Assertions.assertThrows(RuntimeException.class, () -> {
            list.getNext(Player.builder().playerName("noExisting").build());
        });
    }

    @Test
    public void testPreviousValidation() {
        PlayersList list = new PlayersList();
        Assertions.assertThrows(RuntimeException.class, () -> {
            list.getPrevious(Player.builder().playerName("noExisting").build());
        });
    }
}
