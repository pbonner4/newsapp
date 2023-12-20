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
        "dates",
        "page",
        "results"
})

@Generated("jsonschema2pojo")
public class MovieData {

    @JsonProperty("dates")
    private Dates dates;
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("results")
    private List<Movies> movies;

    @JsonProperty("dates")
    public Dates getDates() {
        return dates;
    }

    @JsonProperty("dates")
    public void setDates(Dates dates) {
        this.dates = dates;
    }

    @JsonProperty("page")
    public Integer getPage() {
        return page;
    }

    @JsonProperty("page")
    public void setPage(Integer page) {
        this.page = page;
    }

    @JsonProperty("results")
    public List<Movies> getResults() {
        return movies;
    }

    @JsonProperty("results")
    public void setResults(List<Movies> results) {
        this.movies = results;
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "dates=" + dates +
                ", page=" + page +
                ", results=" + movies +
                '}';
    }
}
