package com.hszg.demo.data.impl;

import com.hszg.demo.data.api.BoardManager;
import com.hszg.demo.model.game.Board;
import com.hszg.demo.model.game.Tile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertyFileBoardManagerImpl implements BoardManager {

    String fileName;
    PropertyFileTileManagerImpl tileManager;

    static PropertyFileBoardManagerImpl propertyFileBoardManager = null;

    private PropertyFileBoardManagerImpl(String fileName) {
        this.fileName = fileName;
        String tileName = fileName.substring(0, fileName.lastIndexOf('/')+1);
        tileName += "TileList.properties";
        this.tileManager =PropertyFileTileManagerImpl.getPropertyFileTileManagerImpl(tileName);
    }

    static public PropertyFileBoardManagerImpl getPropertyFileBoardManagerImpl(String fileName) {
        if (propertyFileBoardManager == null)
            propertyFileBoardManager = new PropertyFileBoardManagerImpl(fileName);
        return propertyFileBoardManager;
    }


    @Override
    public Board getBoard(int i) {
        List<Board> boards = getAllBoards();
        return boards.get(i);
    }

    @Override
    public void storeAllBoards(List<Board> boards) {
        Properties properties = new Properties();

        int i = 1;
        for (Board board : boards) {
            properties.setProperty("Board." + i +".Size", board.getSize().toString());
            tileManager.storeAllTiles(board.getTiles());
            i++;
        }
        
        try {
            properties.store(Files.newOutputStream(Paths.get(fileName)), "Writing all boards to properties file");
        } catch (IOException e ){
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Board> getAllBoards() {
        List<Board> boards = new ArrayList<>();
        Properties properties = new Properties();

        try {
            properties.load(Files.newInputStream(Paths.get(fileName)));
            int i=1;
            while (properties.containsKey("Board." + i + ".Size")){
                int Size = Integer.parseInt(properties.getProperty("Board." + i + ".Size"));

                boards.add(new Board(Size, tileManager.getAllTiles()));

                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return boards;
    }

    @Override
    public void storeBoard(Board board) {
        List<Board> boards = getAllBoards();
        boards.add(board);
        storeAllBoards(boards);
    }

    @Override
    public void deleteBoard(int i) {
        List<Board> boards = getAllBoards();
        boards.remove(i);
        tileManager.deleteTile(i);
        storeAllBoards(boards);
    }
}
