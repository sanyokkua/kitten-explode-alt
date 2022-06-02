package ua.kostenko.expkitten.explodingkitten.api.dto.request;

import lombok.Builder;
import lombok.Data;
import ua.kostenko.expkitten.explodingkitten.models.deck.GameEdition;

@Data
@Builder
public
class BeginGameDto {
    private final String gameSessionId;
    private final String playerName;
    private final GameEdition gameEdition;
}
