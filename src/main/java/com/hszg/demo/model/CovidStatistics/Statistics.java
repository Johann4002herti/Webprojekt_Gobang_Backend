
package com.hszg.demo.model.CovidStatistics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hszg.demo.model.game.Tile;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "continent",
    "country",
    "population",
    "cases",
    "deaths",
    "day",
    "time"
})
@Generated("jsonschema2pojo")
public class Statistics {

    @JsonProperty("continent")
    private String continent;
    @JsonProperty("country")
    private String country;
    @JsonProperty("population")
    private Integer population;
    @JsonProperty("cases")
    private Cases cases;
    @JsonProperty("deaths")
    private Deaths deaths;
    @JsonProperty("day")
    private String day;
    @JsonProperty("time")
    private String time;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Statistics() {
    }

    public Statistics(String continent, String country, Integer population, Cases cases, Deaths deaths, String day, String time) {
        super();
        this.continent = continent;
        this.country = country;
        this.population = population;
        this.cases = cases;
        this.deaths = deaths;
        this.day = day;
        this.time = time;
    }

    @JsonProperty("continent")
    public String getContinent() {
        return continent;
    }

    @JsonProperty("continent")
    public void setContinent(String continent) {
        this.continent = continent;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("population")
    public Integer getPopulation() {
        return population;
    }

    @JsonProperty("population")
    public void setPopulation(Integer population) {
        this.population = population;
    }

    @JsonProperty("cases")
    public Cases getCases() {
        return cases;
    }

    @JsonProperty("cases")
    public void setCases(Cases cases) {
        this.cases = cases;
    }

    @JsonProperty("deaths")
    public Deaths getDeaths() {
        return deaths;
    }

    @JsonProperty("deaths")
    public void setDeaths(Deaths deaths) {
        this.deaths = deaths;
    }

    @JsonProperty("day")
    public String getDay() {
        return day;
    }

    @JsonProperty("day")
    public void setDay(String day) {
        this.day = day;
    }

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public static Statistics fromJson(String json) {
        Statistics statistics = new Statistics();

        //{"get":"statistics","parameters":{"country":"Bahamas"},"errors":[],"results":1,"response":[
        json = json.replace("\"", "'");
        json = json.substring(json.indexOf('[')+1);
        json = json.substring(json.indexOf('[')+1);

        // Continent
        // {"continent":"North-America",
        json = json.substring(json.indexOf(":")+1);
        statistics.setContinent(json.substring(0,json.indexOf(",")));
        // Country
        // "country":"Bahamas",
        json = json.substring(json.indexOf(":")+1);
        statistics.setCountry(json.substring(0,json.indexOf(",")));
        // Population
        // "population":400516,
        json = json.substring(json.indexOf(":")+1);
        statistics.setPopulation(Integer.parseInt(json.substring(0,json.indexOf(","))));
        // Cases
        // "cases":{"new":null,"active":874,"critical":1,"recovered":36366,"1M_pop":"95087","total":38084},
        String caseSubString = json.substring(json.indexOf("{")+1, json.indexOf("}"));
        json = json.substring(json.indexOf("}"));
        statistics.setCases(Cases.fromJson(caseSubString));
        // Deaths
        // "deaths":{"new":null,"1M_pop":"2107","total":844},
        json = json.substring(json.indexOf(":")+1);
        String deathSubString = json.substring(json.indexOf("{")+1, json.indexOf("}"));
        statistics.setDeaths(Deaths.fromJson(deathSubString));
        //tests --> skip
        // "tests":{"1M_pop":"643767","total":257839},
        json = json.substring(json.indexOf("}")+1);
        json = json.substring(json.indexOf("}"));
        //day
        // "day":"2025-02-08",
        json = json.substring(json.indexOf(":")+1);
        statistics.setDay(json.substring(0,json.indexOf(",")));
        // time
        // "time":"2025-02-08T10:15:05+00:00"}]}
        json = json.substring(json.indexOf(":")+1);
        statistics.setTime(json.substring(0,json.indexOf("}")));

        /*json = json.substring(json.indexOf("=")+1);
        statistics.setSize(Integer.parseInt(json.substring(0,json.indexOf(","))));
        json = json.substring(json.indexOf("=")+1);
        json = json.substring(1);
        String tileSubString = json.substring(0, json.lastIndexOf(",")-1);
        tileSubString += ",";
        statistics.setTiles(new ArrayList<Tile>());
        while (!tileSubString.isEmpty()) {
            statistics.addTile(Tile.parseTile(tileSubString.substring(0,tileSubString.indexOf(" ")+1)));
            tileSubString = tileSubString.substring(tileSubString.indexOf(" ")+1);
        }*/
        
        return statistics;
    }

    public static void main(String[] args) {
        System.out.println(Statistics.fromJson("{\"get\":\"statistics\",\"parameters\":{\"country\":\"Bahamas\"},\"errors\":[],\"results\":1,\"response\":[{\"continent\":\"North-America\",\"country\":\"Bahamas\",\"population\":400516,\"cases\":{\"new\":null,\"active\":874,\"critical\":1,\"recovered\":36366,\"1M_pop\":\"95087\",\"total\":38084},\"deaths\":{\"new\":null,\"1M_pop\":\"2107\",\"total\":844},\"tests\":{\"1M_pop\":\"643767\",\"total\":257839},\"day\":\"2025-02-08\",\"time\":\"2025-02-08T10:15:05+00:00\"}]}")
        );
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Statistics.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("continent");
        sb.append('=');
        sb.append(((this.continent == null)?"<null>":this.continent));
        sb.append(',');
        sb.append("country");
        sb.append('=');
        sb.append(((this.country == null)?"<null>":this.country));
        sb.append(',');
        sb.append("population");
        sb.append('=');
        sb.append(((this.population == null)?"<null>":this.population));
        sb.append(',');
        sb.append("cases");
        sb.append('=');
        sb.append(((this.cases == null)?"<null>":this.cases));
        sb.append(',');
        sb.append("deaths");
        sb.append('=');
        sb.append(((this.deaths == null)?"<null>":this.deaths));
        sb.append(',');
        sb.append("day");
        sb.append('=');
        sb.append(((this.day == null)?"<null>":this.day));
        sb.append(',');
        sb.append("time");
        sb.append('=');
        sb.append(((this.time == null)?"<null>":this.time));
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
