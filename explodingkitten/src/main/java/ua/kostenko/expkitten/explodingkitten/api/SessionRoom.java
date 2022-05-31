package ua.kostenko.expkitten.explodingkitten.api;

import lombok.Builder;
import lombok.Data;
import ua.kostenko.expkitten.explodingkitten.engine.GameDirector;

import java.time.LocalDateTime;

@Data
@Builder
public class SessionRoom {
    private final GameDirector gameDirector;
    private final LocalDateTime startTime;
}
