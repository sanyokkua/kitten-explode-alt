package ua.kostenko.expkitten.explodingkitten.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonPlayerInfoDto {
    private final String playerName;
    private final int amountOfCards;
    private final boolean isActive;
    private final boolean isAlive;
}
