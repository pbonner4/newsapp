package newsApp.services;

import newsApp.dao.JdbcCommodityDataDao;
import newsApp.model.articleTemplate.Commodity;
import newsApp.model.articleTemplate.CommodityResponse;
import newsApp.model.articleTemplate.TickerData;
import newsApp.model.articleTemplate.TickerClosingPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TickerDataService {

    public static String ALPHAVANTAGE_API_DOMAIN_URL = "https://www.alphavantage.co/query?function=";

    @Value("${alphaVantageTickerApiKey}")
    private String alphaVantageTickerApiKey;
    public static String ALPHAVANTAGE_API_KEY;

    @PostConstruct
    private void initializeMovieApiKey() {
        ALPHAVANTAGE_API_KEY = "apikey=" + alphaVantageTickerApiKey;
    }

    private final String DOW = "DIA";
    private final String SP500 = "VOO";
    private final String NASDAQ = "QQQ";
    private final String GOLD = "IAUM";
    private final String SILVER = "SLV";


    private RestTemplate restTemplate = new RestTemplate();

    private final JdbcCommodityDataDao stockDataDao;

    @Autowired
    public TickerDataService(JdbcCommodityDataDao stockDataDao) {
        this.stockDataDao = stockDataDao;
    }


    public Map<String, Map<String, String>> getGoldValues() {
        TickerData tickerData = restTemplate.getForObject(ALPHAVANTAGE_API_DOMAIN_URL + "TIME_SERIES_WEEKLY_ADJUSTED&symbol=" + GOLD + "&apikey=" + ALPHAVANTAGE_API_KEY, TickerData.class);
        assert tickerData != null;
        Map<String, Map<String, String>> data = tickerData.getWeeklyAdjustedPrices();
        for (Map.Entry<String, Map<String, String>> entry : data.entrySet()) {
            String date = entry.getKey();
            String closingPrice = entry.getValue().get("4. close");
            stockDataDao.insertWeeklyTickerData(date, Double.parseDouble(closingPrice), "GOLD");
        }
        return data;
    }

    public Map<String, Map<String, String>> getSilverValues() {
        TickerData tickerData = restTemplate.getForObject(ALPHAVANTAGE_API_DOMAIN_URL + "TIME_SERIES_WEEKLY_ADJUSTED&symbol=" + SILVER + "&apikey=" + ALPHAVANTAGE_API_KEY, TickerData.class);
        assert tickerData != null;
        Map<String, Map<String, String>> data = tickerData.getWeeklyAdjustedPrices();
        for (Map.Entry<String, Map<String, String>> entry : data.entrySet()) {
            String date = entry.getKey();
            String closingPrice = entry.getValue().get("4. close");
            stockDataDao.insertWeeklyTickerData(date, Double.parseDouble(closingPrice), "SILVER");
        }
        return data;
    }

    public Map<String, Map<String, String>> getNasdaqValues() {
        TickerData tickerData = restTemplate.getForObject(ALPHAVANTAGE_API_DOMAIN_URL + "TIME_SERIES_WEEKLY_ADJUSTED&symbol=" + NASDAQ + "&apikey=" + ALPHAVANTAGE_API_KEY, TickerData.class);
        assert tickerData != null;
        Map<String, Map<String, String>> data = tickerData.getWeeklyAdjustedPrices();
        for (Map.Entry<String, Map<String, String>> entry : data.entrySet()) {
            String date = entry.getKey();
            String closingPrice = entry.getValue().get("4. close");
            stockDataDao.insertWeeklyTickerData(date, Double.parseDouble(closingPrice), "NASDAQ");
        }
        return data;
    }

    public Map<String, Map<String, String>> getSP500Values() {
        TickerData tickerData = restTemplate.getForObject(ALPHAVANTAGE_API_DOMAIN_URL + "TIME_SERIES_WEEKLY_ADJUSTED&symbol=" + SP500 + "&apikey=" + ALPHAVANTAGE_API_KEY, TickerData.class);
        assert tickerData != null;
        Map<String, Map<String, String>> data = tickerData.getWeeklyAdjustedPrices();
        for (Map.Entry<String, Map<String, String>> entry : data.entrySet()) {
            String date = entry.getKey();
            String closingPrice = entry.getValue().get("4. close");
            stockDataDao.insertWeeklyTickerData(date, Double.parseDouble(closingPrice), "SP500");
        }
        return data;
    }

    public Map<String, Map<String, String>> getDowValues() {
        TickerData tickerData = restTemplate.getForObject(ALPHAVANTAGE_API_DOMAIN_URL + "TIME_SERIES_WEEKLY_ADJUSTED&symbol=" + DOW + "&apikey=" + ALPHAVANTAGE_API_KEY, TickerData.class);
        assert tickerData != null;
        Map<String, Map<String, String>> data = tickerData.getWeeklyAdjustedPrices();
        for (Map.Entry<String, Map<String, String>> entry : data.entrySet()) {
            String date = entry.getKey();
            String closingPrice = entry.getValue().get("4. close");
            stockDataDao.insertWeeklyTickerData(date, Double.parseDouble(closingPrice), "DOW");
        }
        return data;
    }

}
