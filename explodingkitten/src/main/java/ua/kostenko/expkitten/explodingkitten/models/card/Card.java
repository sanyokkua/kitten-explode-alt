package ua.kostenko.expkitten.explodingkitten.models.card;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Card {
    /**
     * Indicates what this card can do, for example this card can be a part of combo or can skip player's move
     */
    private final CardAction cardAction;
    /**
     * Unique identifier of several cards of one type, for example, in the game can be 20 Combo cards,
     * but only 4 of them are equal, so by cardActionId we can see if these 4 cards are equal or different
     */
    private final String cardActionId;
    /**
     * Just a simple Card Name (or funny description)
     */
    private final String cardName;
    /**
     * The path to the Card Image on the Server or WEB
     */
    private final String cardImage;
}
