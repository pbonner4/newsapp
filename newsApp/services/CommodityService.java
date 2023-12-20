package newsApp.services;

import newsApp.dao.JdbcCommodityDataDao;
import newsApp.model.articleTemplate.Commodity;
import newsApp.model.articleTemplate.CommodityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CommodityService {

    public static String ALPHAVANTAGE_API_DOMAIN_URL = "https://www.alphavantage.co/query?function=";

    @Value("${alphaVantageApiKey}")
    private String alphaVantageApiKey;
    private String ALPHAVANTAGE_API_KEY ;
    @PostConstruct
    private void initializeMovieApiKey() {
        ALPHAVANTAGE_API_KEY = "apikey=" + alphaVantageApiKey;
    }

    private final String COPPER = "COPPER";
    private final String WTI = "WTI";
    private final String BRENT = "BRENT";
    private final String NATURAL_GAS = "NATURAL_GAS";
    private final String ALUMINUM = "ALUMINUM";
    private final String WHEAT = "WHEAT";
    private final String CORN = "CORN";
    private final String COTTON = "COTTON";
    private final String SUGAR = "SUGAR";
    private final String COFFEE = "COFFEE";
    private final String ALL_COMMODITIES = "ALL_COMMODITIES";

    private RestTemplate restTemplate = new RestTemplate();

    private final JdbcCommodityDataDao stockDataDao;

    @Autowired
    public CommodityService(JdbcCommodityDataDao stockDataDao) {
        this.stockDataDao = stockDataDao;
    }

    public List<Commodity> getCopperValues() {

        //Strings monthly, quarterly, and annual are accepted.

        CommodityResponse commodityResponse =  restTemplate.getForObject(ALPHAVANTAGE_API_DOMAIN_URL + COPPER + "&interval=monthly&" + ALPHAVANTAGE_API_KEY, CommodityResponse.class);

        assert commodityResponse != null;

        List<Commodity> commodities = commodityResponse.getCommodity();
        for (Commodity commodity : commodities) {
            if(!commodity.getValue().equals(".")) {
                stockDataDao.insertCommodity(commodity.getDate(), commodity.getValue(), COPPER);
            }
        }
        return commodities;
    }

    public List<Commodity> getWTIValues() {

        //By default, interval=monthly. Strings daily, weekly, and monthly are accepted.

        CommodityResponse commodityResponse =  restTemplate.getForObject(ALPHAVANTAGE_API_DOMAIN_URL + WTI + "&interval=daily&" + ALPHAVANTAGE_API_KEY, CommodityResponse.class);

        assert commodityResponse != null;

        List<Commodity> commodities = commodityResponse.getCommodity();
        for (Commodity commodity : commodities) {
            if(!commodity.getValue().equals(".")) {
                stockDataDao.insertCommodity(commodity.getDate(), commodity.getValue(), WTI);
            }
        }
        return commodities;
    }

    public List<Commodity> getBrentValues() {

        //By default, interval=monthly. Strings daily, weekly, and monthly are accepted.

        CommodityResponse commodityResponse =  restTemplate.getForObject(ALPHAVANTAGE_API_DOMAIN_URL + BRENT + "&interval=daily&" + ALPHAVANTAGE_API_KEY, CommodityResponse.class);

        assert commodityResponse != null;

        List<Commodity> commodities = commodityResponse.getCommodity();
        for (Commodity commodity : commodities) {
            if(!commodity.getValue().equals(".")) {
                stockDataDao.insertCommodity(commodity.getDate(), commodity.getValue(), BRENT);
            }
        }
        return commodities;
    }

    public List<Commodity> getNaturalGasValues() {

        //By default, interval=monthly. Strings daily, weekly, and monthly are accepted.

        CommodityResponse commodityResponse =  restTemplate.getForObject(ALPHAVANTAGE_API_DOMAIN_URL + NATURAL_GAS + "&interval=daily&" + ALPHAVANTAGE_API_KEY, CommodityResponse.class);

        assert commodityResponse != null;

        List<Commodity> commodities = commodityResponse.getCommodity();
        for (Commodity commodity : commodities) {
            if(!commodity.getValue().equals(".")) {
                stockDataDao.insertCommodity(commodity.getDate(), commodity.getValue(), NATURAL_GAS);
            }
        }
        return commodities;
    }

    public List<Commodity> getAluminumValues() {

        //By default, interval=monthly. Strings monthly, quarterly, and annual are accepted.

        CommodityResponse commodityResponse =  restTemplate.getForObject(ALPHAVANTAGE_API_DOMAIN_URL + ALUMINUM + "&interval=monthly&" + ALPHAVANTAGE_API_KEY, CommodityResponse.class);

        assert commodityResponse != null;

        List<Commodity> commodities = commodityResponse.getCommodity();
        for (Commodity commodity : commodities) {
            if(!commodity.getValue().equals(".")) {
                stockDataDao.insertCommodity(commodity.getDate(), commodity.getValue(), ALUMINUM);
            }
        }
        return commodities;
    }

    public List<Commodity> getWheatValues() {

        //By default, interval=monthly. Strings monthly, quarterly, and annual are accepted.

        CommodityResponse commodityResponse =  restTemplate.getForObject(ALPHAVANTAGE_API_DOMAIN_URL + WHEAT + "&interval=monthly&" + ALPHAVANTAGE_API_KEY, CommodityResponse.class);

        assert commodityResponse != null;

        List<Commodity> commodities = commodityResponse.getCommodity();
        for (Commodity commodity : commodities) {
            if(!commodity.getValue().equals(".")) {
                stockDataDao.insertCommodity(commodity.getDate(), commodity.getValue(), WHEAT);
            }
        }
        return commodities;
    }

    public List<Commodity> getCornValues() {

        //By default, interval=monthly. Strings monthly, quarterly, and annual are accepted.

        CommodityResponse commodityResponse =  restTemplate.getForObject(ALPHAVANTAGE_API_DOMAIN_URL + CORN + "&interval=monthly&" + ALPHAVANTAGE_API_KEY, CommodityResponse.class);

        assert commodityResponse != null;

        List<Commodity> commodities = commodityResponse.getCommodity();
        for (Commodity commodity : commodities) {
            if(!commodity.getValue().equals(".")) {
                stockDataDao.insertCommodity(commodity.getDate(), commodity.getValue(), CORN);
            }
        }
        return commodities;
    }

    public List<Commodity> getCottonValues() {

        //By default, interval=monthly. Strings monthly, quarterly, and annual are accepted.

        CommodityResponse commodityResponse =  restTemplate.getForObject(ALPHAVANTAGE_API_DOMAIN_URL + COTTON + "&interval=monthly&" + ALPHAVANTAGE_API_KEY, CommodityResponse.class);

        assert commodityResponse != null;

        List<Commodity> commodities = commodityResponse.getCommodity();
        for (Commodity commodity : commodities) {
            if(!commodity.getValue().equals(".")) {
                stockDataDao.insertCommodity(commodity.getDate(), commodity.getValue(), COTTON);
            }
        }
        return commodities;
    }

    public List<Commodity> getSugarValues() {

        //By default, interval=monthly. Strings monthly, quarterly, and annual are accepted.

        CommodityResponse commodityResponse =  restTemplate.getForObject(ALPHAVANTAGE_API_DOMAIN_URL + SUGAR + "&interval=monthly&" + ALPHAVANTAGE_API_KEY, CommodityResponse.class);

        assert commodityResponse != null;

        List<Commodity> commodities = commodityResponse.getCommodity();
        for (Commodity commodity : commodities) {
            if(!commodity.getValue().equals(".")) {
                stockDataDao.insertCommodity(commodity.getDate(), commodity.getValue(), SUGAR);
            }
        }
        return commodities;
    }

    public List<Commodity> getCoffeeValues() {

        //By default, interval=monthly. Strings monthly, quarterly, and annual are accepted.

        CommodityResponse commodityResponse =  restTemplate.getForObject(ALPHAVANTAGE_API_DOMAIN_URL + COFFEE + "&interval=monthly&" + ALPHAVANTAGE_API_KEY, CommodityResponse.class);

        assert commodityResponse != null;

        List<Commodity> commodities = commodityResponse.getCommodity();
        for (Commodity commodity : commodities) {
            if(!commodity.getValue().equals(".")) {
                stockDataDao.insertCommodity(commodity.getDate(), commodity.getValue(), COFFEE);
            }
        }
        return commodities;
    }

    public List<Commodity> getCommodityIndex() {

        //By default, interval=monthly. Strings monthly, quarterly, and annual are accepted.

        CommodityResponse commodityResponse =  restTemplate.getForObject(ALPHAVANTAGE_API_DOMAIN_URL + ALL_COMMODITIES + "&interval=monthly&" + ALPHAVANTAGE_API_KEY, CommodityResponse.class);

        assert commodityResponse != null;

        List<Commodity> commodities = commodityResponse.getCommodity();
        for (Commodity commodity : commodities) {
            if(!commodity.getValue().equals(".")) {
                stockDataDao.insertCommodity(commodity.getDate(), commodity.getValue(), ALL_COMMODITIES);
            }
        }
        return commodities;
    }


}
