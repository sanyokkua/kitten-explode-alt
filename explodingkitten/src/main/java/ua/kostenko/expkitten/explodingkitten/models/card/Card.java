package ua.kostenko.expkitten.explodingkitten.models.card;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Card {
    private final CardAction cardAction;
    private final String cardActionId;
    private final String cardName;
    private final String cardImage;
}
