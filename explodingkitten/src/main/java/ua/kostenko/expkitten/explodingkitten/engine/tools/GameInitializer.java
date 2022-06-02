package ua.kostenko.expkitten.explodingkitten.engine.tools;

import ua.kostenko.expkitten.explodingkitten.models.GameState;
import ua.kostenko.expkitten.explodingkitten.models.Player;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;
import ua.kostenko.expkitten.explodingkitten.models.card.CardAction;
import ua.kostenko.expkitten.explodingkitten.models.deck.GameEdition;

import java.util.*;
import java.util.stream.Collectors;

import static ua.kostenko.expkitten.explodingkitten.engine.tools.GameFilterPredicates.*;

public class GameInitializer {

    private GameInitializer() {
    }

    public static void initGameDeck(GameEdition gameEdition, GameState gameState, String playerName) {
        if (Objects.isNull(gameEdition) || Objects.isNull(gameState)) {
            throw new NullPointerException("GameEdition or GameState is NULL");
        }
        if (Objects.isNull(playerName) || playerName.isBlank()) {
            throw new IllegalArgumentException("PlayerName can't be null or blank");
        }

        Optional<Player> firstPlayer = gameState.getPlayersList().getPlayers().stream().filter(player -> playerName.equals(player.getPlayerName())).findFirst();
        if (firstPlayer.isEmpty()) {
            throw new IllegalArgumentException("Player is not exists in this session");
        }
        firstPlayer.get().setActive(true);

        var initialNumberOfPlayers = gameState.getPlayersList().getPlayers().size();
        if (initialNumberOfPlayers < 2) {
            throw new IllegalArgumentException("Number of players can't be less than 2");
        }

        var initialNumberOfCats = initialNumberOfPlayers - 1;

        Map<CardAction, Integer> deckInformationForEdition = DeckGenerator.getDeckInformationForEdition(gameEdition);

        LinkedList<Card> temporalGeneralDeck = new LinkedList<>(DeckGenerator.getDeckByPredicate(deckInformationForEdition, IS_NOT_CAT_AND_NOT_DEFUSE));
        Collections.shuffle(temporalGeneralDeck);

        GameTools.handOutCardsToPlayersWithoutDefuse(gameState, gameEdition, temporalGeneralDeck);

        LinkedList<Card> defuseDeck = new LinkedList<>(DeckGenerator.getDeckByPredicate(deckInformationForEdition, IS_DEFUSE_PREDICATE));
        GameTools.handOutDefuseCardsToPlayers(gameState, defuseDeck);
        temporalGeneralDeck.addAll(defuseDeck);

        LinkedList<Card> catsDeck = new LinkedList<>(DeckGenerator.getDeckByPredicate(deckInformationForEdition, IS_CAT_PREDICATE));
        temporalGeneralDeck.addAll(catsDeck.stream().limit(initialNumberOfCats).collect(Collectors.toList()));

        Collections.shuffle(temporalGeneralDeck);

        gameState.getCardDeck().addAll(temporalGeneralDeck);
    }
}
