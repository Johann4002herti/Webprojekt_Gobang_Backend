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
    "Status",
    "x-coordinate",
    "y-coordinate"
})
@Generated("jsonschema2pojo")
public class Tile {

    @JsonProperty("Status")
    private String status;      //"playerOne", "playerTwo", "empty"
    @JsonProperty("x-coordinate")
    private Integer xCoordinate;
    @JsonProperty("y-coordinate")
    private Integer yCoordinate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Tile() {
    }

    public Tile(String status, Integer xCoordinate, Integer yCoordinate) {
        super();
        this.status = status;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    @JsonProperty("Status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("Status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("x-coordinate")
    public Integer getxCoordinate() {
        return xCoordinate;
    }

    @JsonProperty("x-coordinate")
    public void setxCoordinate(Integer xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    @JsonProperty("y-coordinate")
    public Integer getyCoordinate() {
        return yCoordinate;
    }

    @JsonProperty("y-coordinate")
    public void setyCoordinate(Integer yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public static Tile parseTile(String tileString){
        Tile tile = new Tile();

        if (!tileString.isEmpty()){
            tileString = tileString.substring(tileString.indexOf("=")+1);
            tile.setStatus(tileString.substring(0,tileString.indexOf(",")));
            tileString = tileString.substring(tileString.indexOf("=")+1);
            tile.setxCoordinate(Integer.parseInt(tileString.substring(0,tileString.indexOf(","))));
            tileString = tileString.substring(tileString.indexOf("=")+1);
            tile.setyCoordinate(Integer.parseInt(tileString.substring(0,tileString.indexOf(","))));
        }
        return tile;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null)?"<null>":this.status));
        sb.append(',');
        sb.append("xCoordinate");
        sb.append('=');
        sb.append(((this.xCoordinate == null)?"<null>":this.xCoordinate));
        sb.append(',');
        sb.append("yCoordinate");
        sb.append('=');
        sb.append(((this.yCoordinate == null)?"<null>":this.yCoordinate));
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

}
