
package com.hszg.demo.model.CovidStatistics;

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
    "new",
    "active",
    "critical",
    "recovered",
    "total"
})
@Generated("jsonschema2pojo")
public class Cases {

    @JsonProperty("new")
    private Object _new;
    @JsonProperty("active")
    private Integer active;
    @JsonProperty("critical")
    private Integer critical;
    @JsonProperty("recovered")
    private Integer recovered;
    @JsonProperty("total")
    private Integer total;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Cases() {
    }

    public Cases(Object _new, Integer active, Integer critical, Integer recovered, Integer total) {
        super();
        this._new = _new;
        this.active = active;
        this.critical = critical;
        this.recovered = recovered;
        this.total = total;
    }

    @JsonProperty("new")
    public Object getNew() {
        return _new;
    }

    @JsonProperty("new")
    public void setNew(Object _new) {
        this._new = _new;
    }

    @JsonProperty("active")
    public Integer getActive() {
        return active;
    }

    @JsonProperty("active")
    public void setActive(Integer active) {
        this.active = active;
    }

    @JsonProperty("critical")
    public Integer getCritical() {
        return critical;
    }

    @JsonProperty("critical")
    public void setCritical(Integer critical) {
        this.critical = critical;
    }

    @JsonProperty("recovered")
    public Integer getRecovered() {
        return recovered;
    }

    @JsonProperty("recovered")
    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public static Cases fromJson(String json) {
        Cases cases = new Cases();
        String temp;

        // "new":null,"active":874,"critical":1,"recovered":36366,"1M_pop":"95087","total":38084},
        //new
        json = json.substring(json.indexOf(":")+1);
        temp = json.substring(0,json.indexOf(","));
        if (temp.equals("null")) {
            cases.setNew(0);
        } else {
            cases.setNew(Integer.parseInt(temp));
        }
        //active
        json = json.substring(json.indexOf(":")+1);
        temp = json.substring(0,json.indexOf(","));
        if (temp.equals("null")) {
            cases.setActive(0);
        } else {
            cases.setActive(Integer.parseInt(temp));
        }
        //critical
        json = json.substring(json.indexOf(":")+1);
        temp = json.substring(0,json.indexOf(","));
        if (temp.equals("null")) {
            cases.setCritical(0);
        } else {
            cases.setCritical(Integer.parseInt(temp));
        }
        //recovered
        json = json.substring(json.indexOf(":")+1);
        temp = json.substring(0,json.indexOf(","));
        if (temp.equals("null")) {
            cases.setRecovered(0);
        } else {
            cases.setRecovered(Integer.parseInt(temp));
        }
        //1M_pop
        json = json.substring(json.indexOf(":")+1);
        //total
        json = json.substring(json.indexOf(":")+1);
        temp = json;
        if (temp.equals("null")) {
            cases.setTotal(0);
        } else {
            cases.setTotal(Integer.parseInt(temp));
        }

        return cases;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Cases.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("_new");
        sb.append('=');
        sb.append(((this._new == null)?"<null>":this._new));
        sb.append(',');
        sb.append("active");
        sb.append('=');
        sb.append(((this.active == null)?"<null>":this.active));
        sb.append(',');
        sb.append("critical");
        sb.append('=');
        sb.append(((this.critical == null)?"<null>":this.critical));
        sb.append(',');
        sb.append("recovered");
        sb.append('=');
        sb.append(((this.recovered == null)?"<null>":this.recovered));
        sb.append(',');
        sb.append("total");
        sb.append('=');
        sb.append(((this.total == null)?"<null>":this.total));
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
