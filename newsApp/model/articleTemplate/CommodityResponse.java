package newsApp.model.articleTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.cucumber.java.it.Date;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "interval",
        "data"
})

@Generated("jsonschema2pojo")
public class CommodityResponse {

@JsonProperty("name")
    private String name;

@JsonProperty("interval")
    private String interval;

@JsonProperty("data")
    List<Commodity> commodity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public List<Commodity> getCommodity() {
        return commodity;
    }

    public void setCommodity(List<Commodity> commodity) {
        this.commodity = commodity;
    }

    @Override
    public String toString() {
        return "CommodityResponse {" +
                "name='" + name + '\'' +
                ", interval = '" + interval + '\'' +
                ", data = '" + commodity + '\'' +
                '}';
    }
}
