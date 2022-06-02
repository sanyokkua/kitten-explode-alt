package ua.kostenko.expkitten.explodingkitten.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public
class AddPlayerToSessionDto {
    private final String gameSessionId;
    private final String playerName;
}
