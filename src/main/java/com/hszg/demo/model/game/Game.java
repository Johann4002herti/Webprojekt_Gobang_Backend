package com.hszg.demo.model.game;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "GameCode",
    "PlayerOnesTurn",
    "Board",
    "Type",
    "BenefitSharing",
    "GameStatus"
})
@Generated("jsonschema2pojo")
public class Game {

    @JsonProperty("GameCode")
    private String gameCode;
    @JsonProperty("PlayerOnesTurn")
    private Boolean playerOnesTurn;
    @JsonProperty("Board")
    private Board board;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("BenefitSharing")
    private Boolean benefitSharing;
    @JsonProperty("GameStatus")
    private String gameStatus;  //"isFull", "PlayerOneWon", "PlayerTwoWon", "isRunning", "Error"
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Game() {
    }

    public Game(String gameCode, Boolean playerOnesTurn, Board board, String type, Boolean benefitSharing, String gameStatus) {
        super();
        this.gameCode = gameCode;
        this.playerOnesTurn = playerOnesTurn;
        this.board = board;
        this.type = type;
        this.benefitSharing = benefitSharing;
        this.gameStatus = gameStatus;
    }
    public Game(String gameCode, int size, String type, Boolean benefitSharing) {
        super();
        this.gameCode = gameCode;
        this.playerOnesTurn = true;
        this.board = new Board(size);
        this.type = type;
        this.benefitSharing = benefitSharing;
        this.gameStatus = "isRunning";
    }

    @JsonProperty("GameCode")
    public String getGameCode() {
        return gameCode;
    }

    @JsonProperty("GameCode")
    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    @JsonProperty("PlayerOnesTurn")
    public Boolean getPlayerOnesTurn() {
        return playerOnesTurn;
    }

    @JsonProperty("PlayerOnesTurn")
    public void setPlayerOnesTurn(Boolean playerOnesTurn) {
        this.playerOnesTurn = playerOnesTurn;
    }

    @JsonProperty("Board")
    public Board getBoard() {
        return board;
    }

    @JsonProperty("Board")
    public void setBoard(Board board) {
        this.board = board;
    }

    @JsonProperty("Type")
    public String getType() {
        return type;
    }

    @JsonProperty("Type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("BenefitSharing")
    public Boolean getBenefitSharing() {
        return benefitSharing;
    }

    @JsonProperty("BenefitSharing")
    public void setBenefitSharing(Boolean benefitSharing) {
        this.benefitSharing = benefitSharing;
    }

    @JsonProperty("GameStatus")
    public String getGameStatus() {
        return gameStatus;
    }

    @JsonProperty("GameStatus")
    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void makeMove(int x, int y){
        if(gameStatus == "isRunning"){
            if (this.getBoard().getTileAt(x,y).getStatus().equals("empty")) {
                if(playerOnesTurn )     this.getBoard().setStatusOfTileAt(x,y,"playerOne");
                else                    this.getBoard().setStatusOfTileAt(x,y,"playerTwo");
            }

        checkGameStatus(x,y);
        playerOnesTurn = !playerOnesTurn;
        }
    }

    private void checkGameStatus(int x,int y){

        String whoHasWon = board.checkWhoWon(x,y);

        if(whoHasWon != null){
            gameStatus = whoHasWon;
        }
        else if(board.checkIsFull()){
            gameStatus = "isFull";
        }
        else {
            gameStatus = "isRunning";
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Game.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("gameCode");
        sb.append('=');
        sb.append(((this.gameCode == null)?"<null>":this.gameCode));
        sb.append(',');
        sb.append("playerOnesTurn");
        sb.append('=');
        sb.append(((this.playerOnesTurn == null)?"<null>":this.playerOnesTurn));
        sb.append(',');
        sb.append("board");
        sb.append('=');
        sb.append(((this.board == null)?"<null>":this.board));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("benefitSharing");
        sb.append('=');
        sb.append(((this.benefitSharing == null)?"<null>":this.benefitSharing));
        sb.append(',');
        sb.append("gameStatus");
        sb.append('=');
        sb.append(((this.gameStatus == null)?"<null>":this.gameStatus));
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

    public static void main(String[] args) {
        Game game = new Game("Test",15,"Gobang",true);

        game.makeMove(0,0);
        game.makeMove(1,1);
        game.makeMove(0,1);
        game.makeMove(1,2);
        game.makeMove(0,2);
        game.makeMove(1,3);
        game.makeMove(0,3);
        game.makeMove(1,4);
        game.makeMove(0,4);
        game.makeMove(1,5);
        game.makeMove(0,5);
        game.makeMove(1,6);

        System.out.println(game.getBoard().displayBoard());

        System.out.println(game.gameStatus);
        System.out.println(game.getBoard().getTileAt(0,0));
        System.out.println(game.getBoard().getTileAt(1,0));
        System.out.println(game.getBoard().getTileAt(1,1));
    }

}
