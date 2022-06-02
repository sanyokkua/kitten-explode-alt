package ua.kostenko.expkitten.explodingkitten.api.dto.response;

import lombok.Builder;
import lombok.Data;
import ua.kostenko.expkitten.explodingkitten.models.card.Card;

@Data
@Builder
public class CardAndStatusDto {
    private final Card card;
    private final boolean isActive;
}
