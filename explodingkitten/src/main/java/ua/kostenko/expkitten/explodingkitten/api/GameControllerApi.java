package ua.kostenko.expkitten.explodingkitten.api;

import ua.kostenko.expkitten.explodingkitten.api.dto.request.AddPlayerToSessionDto;
import ua.kostenko.expkitten.explodingkitten.api.dto.request.BeginGameDto;
import ua.kostenko.expkitten.explodingkitten.api.dto.request.GetInfoForPlayer;
import ua.kostenko.expkitten.explodingkitten.api.dto.request.MakeMoveDto;
import ua.kostenko.expkitten.explodingkitten.api.dto.response.GameStateDto;
import ua.kostenko.expkitten.explodingkitten.api.dto.response.PlayerFullInfoDto;
import ua.kostenko.expkitten.explodingkitten.models.deck.GameEdition;

import java.util.List;

public interface GameControllerApi {

    GameStateDto startGameSession();

    GameStateDto addPlayerToSession(AddPlayerToSessionDto addPlayerToSessionDto);

    GameStateDto beginGame(BeginGameDto beginGameDto);

    GameStateDto getUpdatedInfo(String gameSessionId);

    GameStateDto makeMove(MakeMoveDto makeMoveDto);

    PlayerFullInfoDto getInfoForPlayer(GetInfoForPlayer getInfoForPlayer);

    List<GameEdition> getGameEditions();

}
