package ua.kostenko.expkitten.explodingkitten.api;

import ua.kostenko.expkitten.explodingkitten.api.dto.AddPlayerToSessionDto;
import ua.kostenko.expkitten.explodingkitten.api.dto.BeginGameDto;
import ua.kostenko.expkitten.explodingkitten.api.dto.GameStateDto;
import ua.kostenko.expkitten.explodingkitten.api.dto.MakeMoveDto;
import ua.kostenko.expkitten.explodingkitten.models.deck.GameEdition;

import java.util.List;

public interface GameControllerApi {

    GameStateDto startGameSession();

    GameStateDto addPlayerToSession(AddPlayerToSessionDto addPlayerToSessionDto);

    GameStateDto beginGame(BeginGameDto beginGameDto);

    GameStateDto getUpdatedInfo(String gameSessionId);

    GameStateDto makeMove(MakeMoveDto makeMoveDto);

    List<GameEdition> getGameEditions();

}
