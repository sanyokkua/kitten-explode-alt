package ua.kostenko.expkitten.explodingkitten.models.deck;

import lombok.Getter;

public enum GameEdition {
    ORIGINAL_EDITION(4),
    NSFW_EDITION(6);
    @Getter
    private final int amountOfCardsForPlayer;

    GameEdition(int amountOfCardsForPlayer) {
        this.amountOfCardsForPlayer = amountOfCardsForPlayer;
    }
}
