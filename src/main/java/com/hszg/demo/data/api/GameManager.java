package com.hszg.demo.data.api;

import com.hszg.demo.model.game.Game;

import java.util.List;

public interface GameManager {

    Game getGame(String gameCode);
    List<Game> getAllGames();
    void storeGame(Game game);
    void updateGame(String gameCode, Game game);
    void storeAllGames(List<Game> games);
    void deleteGame(String gameCode);
    void clearProps();
}
