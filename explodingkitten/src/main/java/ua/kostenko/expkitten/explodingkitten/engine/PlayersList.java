package ua.kostenko.expkitten.explodingkitten.engine;

import ua.kostenko.expkitten.explodingkitten.models.Player;

import java.util.*;
import java.util.function.Consumer;

public class PlayersList implements Iterable<Player> {

    private final Map<Integer, Player> players;
    private int lastIndex = -1;

    public PlayersList() {
        players = new HashMap<>();
    }

    public void addPlayer(Player player) {
        if (!players.containsValue(player)) {
            players.put(++lastIndex, player);
        }
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players.values());
    }

    public Player getNext(Player currentPlayer) {
        Optional<Map.Entry<Integer, Player>> currentPlayerEntry = getCurrentPlayerEntry(currentPlayer);
        if (currentPlayerEntry.isEmpty()) {
            throw new RuntimeException();
        }
        var currentPlayerIndex = currentPlayerEntry.get().getKey();
        if (currentPlayerIndex < lastIndex) {
            return players.get(++currentPlayerIndex);
        } else {
            return players.get(0);
        }
    }

    private Optional<Map.Entry<Integer, Player>> getCurrentPlayerEntry(Player currentPlayer) {
        return players.entrySet().stream().filter(integerPlayerEntry -> integerPlayerEntry.getValue().getPlayerName().equals(currentPlayer.getPlayerName())).findAny();
    }

    public Player getPrevious(Player currentPlayer) {
        Optional<Map.Entry<Integer, Player>> currentPlayerEntry = getCurrentPlayerEntry(currentPlayer);
        if (currentPlayerEntry.isEmpty()) {
            throw new RuntimeException();
        }
        var currentPlayerIndex = currentPlayerEntry.get().getKey();
        if (currentPlayerIndex > 0) {
            return players.get(currentPlayerIndex - 1);
        } else {
            return players.get(lastIndex);
        }
    }

    @Override
    public Iterator<Player> iterator() {
        return players.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super Player> action) {
        players.values().forEach(action);
    }

    @Override
    public Spliterator<Player> spliterator() {
        return players.values().spliterator();
    }
}
