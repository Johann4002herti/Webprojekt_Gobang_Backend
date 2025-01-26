package com.hszg.demo.data.impl;

import com.hszg.demo.data.api.GameManager;
import com.hszg.demo.model.game.Board;
import com.hszg.demo.model.game.Game;
import com.hszg.demo.model.game.Tile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class PropertyFileGameManagerImpl implements GameManager {

    String fileName;
    //PropertyFileTileManagerImpl tileManager;

    static PropertyFileGameManagerImpl propertyFileGameManager = null;

    private PropertyFileGameManagerImpl(String fileName) {
        this.fileName = fileName;
        /*String tileName = fileName.substring(0, fileName.lastIndexOf('/')+1);
        tileName += "TileList.properties";
        this.tileManager =PropertyFileTileManagerImpl.getPropertyFileTileManagerImpl(tileName);*/
    }

    static public PropertyFileGameManagerImpl getPropertyFileGameManagerImpl(String fileName) {
        if (propertyFileGameManager == null)
            propertyFileGameManager = new PropertyFileGameManagerImpl(fileName);
        return propertyFileGameManager;
    }


    @Override
    public Game getGame(String gameCode) {
        List<Game> games = getAllGames();
        for (Game game : games) {
            if (game.getGameCode().equals(gameCode)){
                return game;
            }
        }
        return null;
    }

    @Override
    public void storeAllGames(List<Game> games) {
        Properties properties = new Properties();

        int i = 1;
        for (Game game : games) {
            properties.setProperty("Game." + i +".GameCode", game.getGameCode());
            properties.setProperty("Game." + i +".PlayerOnesTurn", game.getPlayerOnesTurn().toString());
            properties.setProperty("Board." + i +".Size", game.getBoard().getSize().toString() );
            int j = 1;
            for (Tile tile : game.getBoard().getTiles()) {
                properties.setProperty("Tile." + i + "."+ j +".Status", tile.getStatus());
                properties.setProperty("Tile." + i + "."+ j +".x", tile.getxCoordinate().toString());
                properties.setProperty("Tile." + i + "."+ j +".y", tile.getyCoordinate().toString());
                j++;
            }
            properties.setProperty("Game." + i +".Type", game.getType());
            properties.setProperty("Game." + i +".BenefitSharing", game.getBenefitSharing().toString());
            properties.setProperty("Game." + i +".GameStatus", game.getGameStatus());
            i++;
        }

        try {
            properties.store(Files.newOutputStream(Paths.get(fileName)), "Writing all games to properties file");
        } catch (IOException e ){
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        Properties properties = new Properties();

        try {
            properties.load(Files.newInputStream(Paths.get(fileName)));
            int i=1;
            while (properties.containsKey("Game." + i + ".GameCode")){
                String gameCode = properties.getProperty("Game." + i + ".GameCode");
                Boolean playerOnesTurn = Boolean.parseBoolean(properties.getProperty("Game." + i + ".PlayerOnesTurn"));
                int boardSize = Integer.parseInt(properties.getProperty("Board." + i +".Size"));
                List<Tile> tiles = new ArrayList<>();
                int j = 1;
                while (properties.containsKey("Tile." + i + "."+ j +".Status")){
                    tiles.add(new Tile(
                            properties.getProperty("Tile." + i + "."+ j +".Status"),
                            Integer.parseInt(properties.getProperty("Tile." + i + "."+ j +".x")),
                            Integer.parseInt(properties.getProperty("Tile." + i + "."+ j +".y"))
                    ));

                    j++;
                }
                String type = properties.getProperty("Game." + i + ".Type");
                Boolean benefitSharing = Boolean.parseBoolean(properties.getProperty("Game." + i + ".BenefitSharing"));
                String gameStatus = properties.getProperty("Game." + i + ".GameStatus");

                games.add(new Game(gameCode, playerOnesTurn, new Board(boardSize,tiles), type, benefitSharing, gameStatus));

                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return games;
    }

    @Override
    public void storeGame(Game game) {
        List<Game> games = getAllGames();
        games.add(game);
        storeAllGames(games);
    }

    @Override
    public void deleteGame(String gameCode) {
        List<Game> games = getAllGames();
        List<Game> toRemove = new ArrayList<>();
        for (Game game : games) {
            if (game.getGameCode().equals(gameCode)) {
                toRemove.add(game);
            }
        }
        games.removeAll(toRemove);
        storeAllGames(games);
    }

    public static void main(String[] args) {
        Game g = new Game("Test",15,"Gobang", true);
        PropertyFileGameManagerImpl fileGameManager = getPropertyFileGameManagerImpl("src/main/resources/GameList.properties");

        //fileGameManager.storeGame(g);
        System.out.println(fileGameManager.getGame(g.getGameCode()));
        fileGameManager.deleteGame(g.getGameCode());

    }
}
