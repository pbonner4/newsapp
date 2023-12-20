package newsApp.model.articleTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.cucumber.java.it.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonPropertyOrder({
        "Weekly Adjusted Time Series",
        "4. close"
})
public class TickerData {
    @JsonProperty("Weekly Adjusted Time Series")
    private Map<String, Map<String, String>> weeklyAdjustedPrices;

    @JsonProperty("4. close")
    private List<TickerClosingPrice> tickerClosingPriceList;

    public List<Double> getClosingPrices() {
        List<Double> closingPrices = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : weeklyAdjustedPrices.entrySet()) {
            String closePrice = entry.getValue().get("4. close");
            closingPrices.add(Double.parseDouble(closePrice));
        }
        return closingPrices;
    }

    public List<String> getDates() {
        List<String> dates = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : weeklyAdjustedPrices.entrySet()) {
            dates.add(entry.getKey());
        }
        return dates;
    }

    public Map<String, Map<String, String>> getWeeklyAdjustedPrices() {
        return weeklyAdjustedPrices;
    }

    public void setWeeklyAdjustedPrices(Map<String, Map<String, String>> weeklyAdjustedPrices) {
        this.weeklyAdjustedPrices = weeklyAdjustedPrices;
    }

    public List<TickerClosingPrice> getTickerClosingPriceList() {
        return tickerClosingPriceList;
    }

    public void setTickerClosingPriceList(List<TickerClosingPrice> tickerClosingPriceList) {
        this.tickerClosingPriceList = tickerClosingPriceList;
    }
}