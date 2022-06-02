package ua.kostenko.expkitten.explodingkitten.api.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetInfoForPlayer {
    private final String playerId;
    private final String gameSessionId;
}
