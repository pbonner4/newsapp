package newsApp.model.articleTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import newsApp.model.MovieTrailer;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "results"
})

@Generated("jsonschema2pojo")
public class MovieTrailerData {
    @JsonProperty("id")
    private int id;
    @JsonProperty("results")
    private List<MovieTrailer> videos;

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }
    @JsonProperty("results")
    public List<MovieTrailer> getVideos() {
        return videos;
    }

    public void setVideos(List<MovieTrailer> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "id=" + id +
                ", videos =" + videos +
                '}';
    }


}
