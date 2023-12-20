package newsApp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "iso_639_1",
        "iso_3166_1",
        "name",
        "key",
        "site",
        "type",
        "official",
        "published_at",
        "id"
})

@Generated("jsonschema2pojo")
public class MovieTrailer {

    @JsonProperty("iso_639_1")
    private String iso_639_1;
    @JsonProperty("iso_3166_1")
    private String iso_3166_1;
    @JsonProperty("name")
    private String name;
    @JsonProperty("key")
    private String key;
    @JsonProperty("site")
    private String site;
    @JsonProperty("type")
    private String type;
    @JsonProperty("official")
    private String official;
    @JsonProperty("published_at")
    private String published_at;
    @JsonProperty("id")
    private String id;

    @JsonProperty("iso_639_1")
    public String getIso_639_1() {
        return iso_639_1;
    }
    @JsonProperty("iso_639_1")
    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }
    @JsonProperty("iso_3166_1")
    public String getIso_3166_1() {
        return iso_3166_1;
    }
    @JsonProperty("iso_3166_1")
    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("site")
    public String getSite() {
        return site;
    }
    @JsonProperty("site")
    public void setSite(String site) {
        this.site = site;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }
@JsonProperty("official")
    public String getOfficial() {
        return official;
    }
    @JsonProperty("official")
    public void setOfficial(String official) {
        this.official = official;
    }
    @JsonProperty("published_at")
    public String getPublished_at() {
        return published_at;
    }
    @JsonProperty("published_at")
    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }
}
