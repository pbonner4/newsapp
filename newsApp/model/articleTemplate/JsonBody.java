package newsApp.model.articleTemplate;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "totalResults",
        "articles"
})
@Generated("jsonschema2pojo")
public class JsonBody {
    @JsonProperty("articles")
    private List<Result> articles;

    @JsonProperty("totalResults")
    private TotalResults totalResults;
    @JsonProperty("status")
    private String status;
    @JsonProperty("status")
    public String getStatus(){
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status){
        this.status = status;
    }

    @JsonProperty("articles")
    public List<Result> getArticles() {
        return articles;
    }
    @JsonProperty("articles")
    public void setArticles(List<Result> articles) {
        this.articles = articles;
    }

    @JsonProperty("totalResults")
    public TotalResults getTotalResults() {
        return totalResults;
    }
    @JsonProperty("totalResults")
    public void setTotalResults(TotalResults totalResults) {
        this.totalResults = totalResults;
    }


    @Override
    public String toString() {
        return "JsonBody{" +
                "status='" + status + '\'' +
                ", totalResults = '" + totalResults + '\'' +
                ", articles=" + articles +
                '}';
    }
}

