package com.hszg.demo.model.game;

import java.util.*;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Size",
    "Tiles"
})
@Generated("jsonschema2pojo")
public class Board {

    @JsonProperty("Size")
    private Integer size;
    @JsonProperty("Tiles")
    private List<Tile> tiles;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Board() {
    }

    public Board(Integer size, List<Tile> tiles) {
        super();
        this.size = size;
        this.tiles = tiles;
    }

    public Board(Integer size) {
        this.size = size;
        fillBoard();
    }

    private void fillBoard(){
        this.tiles = new ArrayList<Tile>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                tiles.add(new Tile("empty",x,y));
            }
        }
    }

    @JsonProperty("Size")
    public Integer getSize() {
        return size;
    }

    @JsonProperty("Size")
    public void setSize(Integer size) {
        this.size = size;
    }

    @JsonProperty("Tiles")
    public List<Tile> getTiles() {
        return tiles;
    }

    @JsonProperty("Tiles")
    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public void addTile(Tile tile){
        this.tiles.add(tile);
    }

    public void setStatusOfTileAt(int x, int y, String status){
        getTileAt(x,y).setStatus(status);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
    
    public String checkWhoWon(int x, int y){
        int whoHasWon = 0;
        int player = 0;
        Tile t = getTileAt(x,y);
                if(t.getStatus().equals("playerOne")){
                    player = 1;
                }
                else if(t.getStatus().equals("playerTwo")){
                    player = 2;
                }
                else {
                    player = 0;
                }

        if (player !=0){
            //Diagonal
            if (count(player, x, y, 1, 1) + count(player, x, y, -1, -1) + 1 >= 5){
                whoHasWon=player;
            }
            //Diagonal
            else if (count(player, x, y, -1, 1) + count(player, x, y, 1, -1) + 1 >= 5){
                whoHasWon=player;
            }
            //Horizontal
            else if (count(player, x, y, 0, 1) + count(player, x, y, 0, -1) + 1 >= 5){
                whoHasWon=player;
            }
            //Vertical
            else if (count(player, x, y, 1, 0) + count(player, x, y, -1, 0) + 1 >= 5){
                whoHasWon=player;
            }
        }

        if (whoHasWon == 1){
            return "PlayerOneWon";
        }
        else if (whoHasWon == 2){
            return "PlayerTwoWon";
        }
        else {
            return null;
        }
    }

    //An auxiliary Function to help the method "void isWon(int y)""
    int count(int player, int xToCheck, int yToCheck, int xDirection, int yDirection){
    int numberOfRows = size;
    int numberOfColumns = size;
    int count = 0;
    String playerString;
    if (player == 1){
        playerString = "playerOne";
    }
    else if (player == 2){
        playerString = "playerTwo";
    }
    else {
        playerString = "empty";
    }
    while (true)
    {
        xToCheck += xDirection;
        yToCheck += yDirection;
        if (xToCheck < 0 || yToCheck < 0 || xToCheck >= numberOfRows || yToCheck >= numberOfColumns)
            break;
        if (getTileAt(xToCheck,yToCheck).getStatus().equals(player))
            count++;
        else
            break;
    }
    return count;
    }

    public boolean checkIsFull(){
        for (Tile t : tiles) {
            if (t.getStatus().equals("empty")) {
                return false;
            }
        }
        return true;
    }

    public Tile getTileAt(int x, int y){
        for (Tile t : tiles) {
            if(t.getxCoordinate() == x && t.getyCoordinate() == y){
                return t;
            }
        }
        return tiles.get(0);
    }

    public static Board parseBoard(String boardString){
        Board board = new Board();

        boardString = boardString.substring(boardString.indexOf("=")+1);
        board.setSize(Integer.parseInt(boardString.substring(0,boardString.indexOf(","))));
        boardString = boardString.substring(boardString.indexOf("=")+1);
        boardString = boardString.substring(1);
        String tileSubString = boardString.substring(0, boardString.lastIndexOf(",")-1);
        tileSubString += ",";
        board.setTiles(new ArrayList<Tile>());
        while (!tileSubString.isEmpty()) {
            board.addTile(Tile.parseTile(tileSubString.substring(0,tileSubString.indexOf(" ")+1)));
            tileSubString = tileSubString.substring(tileSubString.indexOf(" ")+1);
        }

    return board;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append("size");
        sb.append('=');
        sb.append(((this.size == null)?"<null>":this.size));
        sb.append(',');
        sb.append("tiles");
        sb.append('=');
        sb.append(((this.tiles == null)?"<null>":this.tiles));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(size, board.size) && Objects.equals(tiles, board.tiles);
    }

    public static void main(String[] args) {
        Board board1 = new Board(15);
        Board board2 = new Board(15);
        Board board3 = new Board(15);
        System.out.println(board1);
        System.out.println(board2);
        System.out.println(board3);
        board2 = Board.parseBoard(board1.toString());
        System.out.println(board1.toString());
        System.out.println(board2.toString());
    }
}
