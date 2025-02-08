
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
    "total"
})
@Generated("jsonschema2pojo")
public class Deaths {

    @JsonProperty("new")
    private Object _new;
    @JsonProperty("total")
    private Integer total;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Deaths() {
    }

    public Deaths(Object _new, Integer total) {
        super();
        this._new = _new;
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

    public static Deaths fromJson(String json) {
        Deaths deaths = new Deaths();
        String temp;
        // "new":null,"1M_pop":"2107","total":844},
        //new
        json = json.substring(json.indexOf(":")+1);
        temp = json.substring(0,json.indexOf(","));
        if (temp.equals("null")) {
            deaths.setNew(0);
        } else {
            deaths.setNew(Integer.parseInt(temp));
        }
        //1M_pop
        json = json.substring(json.indexOf(":")+1);
        //total
        json = json.substring(json.indexOf(":")+1);
        temp = json;
        if (temp.equals("null")) {
            deaths.setTotal(0);
        } else {
            deaths.setTotal(Integer.parseInt(temp));
        }

        return deaths;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Deaths.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("_new");
        sb.append('=');
        sb.append(((this._new == null)?"<null>":this._new));
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
