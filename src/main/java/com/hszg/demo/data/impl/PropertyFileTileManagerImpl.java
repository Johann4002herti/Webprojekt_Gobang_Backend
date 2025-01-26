package com.hszg.demo.data.impl;

import com.hszg.demo.model.game.Tile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertyFileTileManagerImpl {

    String fileName;

    static PropertyFileTileManagerImpl propertyFileTileManager = null;

    private PropertyFileTileManagerImpl(String fileName) {
        this.fileName = fileName;
    }

    static public PropertyFileTileManagerImpl getPropertyFileTileManagerImpl(String fileName) {
        if (propertyFileTileManager == null)
            propertyFileTileManager = new PropertyFileTileManagerImpl(fileName);
        return propertyFileTileManager;
    }


    
    public Tile getTile(int i) {
        List<Tile> tiles = getAllTiles();
        return tiles.get(i);
    }

    public void storeAllTiles(List<Tile> tiles) {
        Properties properties = new Properties();

        int i = 1;
        for (Tile tile : tiles) {
            properties.setProperty("Tile." + i +".Status", tile.getStatus());
            properties.setProperty("Tile." + i +".x", tile.getxCoordinate().toString());
            properties.setProperty("Tile." + i +".y", tile.getyCoordinate().toString());
            i++;
        }
        
        try {
            properties.store(Files.newOutputStream(Paths.get(fileName)), "Writing all tiles to properties file");
        } catch (IOException e ){
            throw new RuntimeException(e);
        }

    }

    
    public List<Tile> getAllTiles() {
        List<Tile> tiles = new ArrayList<>();
        Properties properties = new Properties();

        try {
            properties.load(Files.newInputStream(Paths.get(fileName)));
            int i=1;
            while (properties.containsKey("Tile." + i + ".Status")){
                String status = properties.getProperty("Tile." + i + ".Status");
                int x = Integer.parseInt(properties.getProperty("Tile." + i + ".x"));
                int y = Integer.parseInt(properties.getProperty("Tile." + i + ".y"));

                tiles.add(new Tile(status, x, y));
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tiles;
    }

    
    public void storeTile(Tile tile) {
        List<Tile> tiles = getAllTiles();
        tiles.add(tile);
        storeAllTiles(tiles);
    }

    
    public void deleteTile(int i) {
        List<Tile> tiles = getAllTiles();
        tiles.remove(i);
        storeAllTiles(tiles);
    }
}
