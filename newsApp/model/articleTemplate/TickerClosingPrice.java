package newsApp.model.articleTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "4. close",
})
public class TickerClosingPrice {
    @JsonProperty("4. close")
    private int closing;

    public int getClosing() {
        return closing;
    }

    public void setClosing(int closing) {
        this.closing = closing;
    }

    @Override
    public String toString() {
        return "Price {" +
                "closing='" + closing + '\'' +
                '}';
    }
}
