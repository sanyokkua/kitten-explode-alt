package ua.kostenko.expkitten.explodingkitten.engine;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ua.kostenko.expkitten.explodingkitten.models.GameDirection;
import ua.kostenko.expkitten.explodingkitten.models.GameState;
import ua.kostenko.expkitten.explodingkitten.models.Player;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;
import ua.kostenko.expkitten.explodingkitten.models.card.CardAction;
import ua.kostenko.expkitten.explodingkitten.models.deck.GameEdition;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ua.kostenko.expkitten.explodingkitten.engine.GameFilterPredicates.*;

@Slf4j
@Data
public class GameDirector {
    private final GameEdition gameEdition;
    private final int initialNumberOfPlayers;
    private final int initialNumberOfCats;
    private final GameState gameState;
    private final Map<CardAction, Integer> initialTemporalDeckInformation;
    private final LocalDateTime startTime;

    public GameDirector(GameEdition gameEdition, int initialNumberOfPlayers) {
        startTime = LocalDateTime.now();
        if (initialNumberOfPlayers < 2 || initialNumberOfPlayers > 5) {
            throw new IllegalArgumentException("Number of players should be from 2 up to 5");
        }

        this.gameEdition = gameEdition;
        this.gameState = new GameState(UUID.randomUUID().toString(),
                new PlayersList(),
                new LinkedList<>(),
                new LinkedList<>(),
                GameDirection.FORWARD);
        this.initialNumberOfPlayers = initialNumberOfPlayers;
        this.initialNumberOfCats = initialNumberOfPlayers - 1;
        this.initialTemporalDeckInformation = GenerateStacksForEditions.getStackForEdition(gameEdition);
    }

    public String getGameId() {
        return gameState.getGameSessionId();
    }

    public int getAmountOfCardsInStack() {
        return gameState.getCardDeck().size();
    }


    public void registerPlayer(String gameId, String playerName) {
        if (this.gameState.getGameSessionId().equals(gameId)
                && !playerName.isBlank()
                && this.gameState.getPlayersList().getPlayers().stream()
                .noneMatch(user -> user.getPlayerName().equals(playerName))) {
            this.gameState.getPlayersList().addPlayer(Player.builder()
                    .isAlive(true)
                    .isActive(false)
                    .playerName(playerName)
                    .playerCards(new ArrayList<>())
                    .build());
        }
    }

    public void beginGame(String gameId) {
        if (this.gameState.getGameSessionId().equals(gameId)
                && this.gameState.getPlayersList().getPlayers().size() == initialNumberOfPlayers) {
            initGameDeck();
        } else {
            throw new RuntimeException("Incorrect state for game beginning");
        }
    }

    private void initGameDeck() {
        LinkedList<Card> temporalGeneralDeck = createInitialDeckWithoutCatsAndDefuse(IS_NOT_CAT_AND_NOT_DEFUSE);
        Collections.shuffle(temporalGeneralDeck);

        handOutCardsToPlayersWithoutDefuse(temporalGeneralDeck);

        LinkedList<Card> defuseDeck = createInitialDeckWithoutCatsAndDefuse(IS_DEFUSE_PREDICATE);
        handOutDefuseCardsToPlayers(defuseDeck);
        temporalGeneralDeck.addAll(defuseDeck);

        LinkedList<Card> catsDeck = createInitialDeckWithoutCatsAndDefuse(IS_CAT_PREDICATE);
        temporalGeneralDeck.addAll(catsDeck.stream().limit(initialNumberOfCats).collect(Collectors.toList()));

        Collections.shuffle(temporalGeneralDeck);

        this.gameState.getCardDeck().addAll(temporalGeneralDeck);
        this.gameState.getPlayersList().getPlayers().stream().findFirst().get().setActive(true);
    }

    private void handOutCardsToPlayersWithoutDefuse(LinkedList<Card> deck) {
        this.gameState.getPlayersList().forEach(player -> {
            for (int i = 0; i < gameEdition.getAmountOfCardsForPlayer(); i++) {
                player.getPlayerCards().add(deck.pollFirst());
            }
        });
    }

    private void handOutDefuseCardsToPlayers(LinkedList<Card> deck) {
        this.gameState.getPlayersList().forEach(player -> player.getPlayerCards().add(deck.pollFirst()));
    }

    private LinkedList<Card> createInitialDeckWithoutCatsAndDefuse(Predicate<Map.Entry<CardAction, Integer>> filterPredicate) {
        var temporalGeneralStack = new LinkedList<Card>();
        initialTemporalDeckInformation.entrySet().stream()
                .filter(filterPredicate)
                .forEach(stackEntry -> {
                    var cardId = UUID.randomUUID().toString();
                    var amountOfCardsForAction = stackEntry.getValue();
                    var cardAction = stackEntry.getKey();

                    for (int i = 0; i < amountOfCardsForAction; i++) {
                        temporalGeneralStack.add(Card.builder()
                                .cardActionId(cardId)
                                .cardImage(null)
                                .cardName(cardAction.name())
                                .cardAction(cardAction)
                                .build());
                    }
                });
        return temporalGeneralStack;
    }

    public void takeTheCard(String gameId, String username) {
        var gameStateGameId = gameState.getGameSessionId();
        var cardDeck = gameState.getCardDeck();
        var players = gameState.getPlayersList();
        var activePlayer = players.getPlayers().stream().filter(p -> username.equals(p.getPlayerName())).findFirst();

        if (!gameStateGameId.equals(gameId) || activePlayer.isEmpty()) {
            log.debug("Error with input. GameId = {}, username = {}", gameId, username);
            throw new IllegalArgumentException("GameId or username is invalid");
        }

        var takenCard = cardDeck.poll();
        if (Objects.nonNull(takenCard)) {
            processCard(activePlayer.get(), takenCard, this.gameState);
        }
    }

//    public void useTheCard(String gameId, String username, Card card, String targetPlayerName) {
//        var gameStateGameId = gameState.getGameSessionId();
//        var cardDeck = gameState.getCardDeck();
//        var players = gameState.getPlayersList();
//        var activePlayer = players.stream().filter(p -> username.equals(p.getPlayerName())).findFirst();
//
//        if (!gameStateGameId.equals(gameId) || activePlayer.isEmpty()) {
//            log.debug("Error with input. GameId = {}, username = {}", gameId, username);
//            throw new IllegalArgumentException("GameId or username is invalid");
//        }
//
//        var takenCard = cardDeck.poll();
//        if (Objects.nonNull(takenCard)) {
//            processCard(activePlayer.get(), takenCard, this.gameState);
//        }
//    }

    private void processCard(Player activePlayer, Card currentCard, GameState gameState) {

    }

}
