package newsApp.model.articleTemplate;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
    public class TotalResults {

        public TotalResults(int totalResults) {
            this.totalResults = totalResults;
        }
        @JsonProperty("totalResults")
        private int totalResults;

        @JsonProperty("totalResults")
        public int getTotalResults() {
            return totalResults;
        }

        @JsonProperty("totalResults")
        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }
    }

