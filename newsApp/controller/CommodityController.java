package newsApp.controller;

import newsApp.dao.JdbcCommodityDataDao;
import newsApp.model.PercentageChange;
import newsApp.model.StockIndex;
import newsApp.model.articleTemplate.Commodity;
import newsApp.services.CommodityService;
import newsApp.services.TickerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class CommodityController {

    private JdbcCommodityDataDao jdbcCommodityDataDao;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private TickerDataService tickerDataService;

    public CommodityController(JdbcCommodityDataDao jdbcCommodityDataDao) {
        this.jdbcCommodityDataDao = jdbcCommodityDataDao;
    }

    @CrossOrigin
    @RequestMapping(path = "/commodities", method = RequestMethod.POST)
    public ResponseEntity<String> insertCommodities() {

        List<Commodity> wtiValues = commodityService.getWTIValues();
        List<Commodity> brentValues = commodityService.getBrentValues();
        List<Commodity> naturalGasValues = commodityService.getNaturalGasValues();
        List<Commodity> copperValues = commodityService.getCopperValues();
        List<Commodity> aluminumValues = commodityService.getAluminumValues();

        if (wtiValues != null && brentValues != null && naturalGasValues != null && copperValues != null && aluminumValues != null) {
            return ResponseEntity.ok("commodities inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting commodities");
        }
    }

    @CrossOrigin
    @RequestMapping(path = "/commodities2", method = RequestMethod.POST)
    public ResponseEntity<String> insertCommodities2() {

        //neeeded bc of limit 5 calls a minute

        List<Commodity> wheatValues = commodityService.getWheatValues();
        List<Commodity> cornValues = commodityService.getCornValues();
        List<Commodity> cottonValues = commodityService.getCottonValues();
        List<Commodity> sugarValues = commodityService.getSugarValues();
        List<Commodity> coffeeValues = commodityService.getCoffeeValues();

        if (wheatValues != null && cornValues != null && cottonValues != null && sugarValues != null && coffeeValues != null) {
            return ResponseEntity.ok("commodities inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting commodities");
        }
    }

    @CrossOrigin
    @RequestMapping(path = "/commodities3", method = RequestMethod.POST)
    public ResponseEntity<String> insertCommodities3() {

        //neeeded bc of limit 5 calls a minute

        List<Commodity> allCommodities = commodityService.getCommodityIndex();

        if (allCommodities != null) {
            return ResponseEntity.ok("commodities inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting commodities");
        }
    }

    @CrossOrigin
    @RequestMapping(path = "/tickerData", method = RequestMethod.POST)
    public ResponseEntity<String> insertTickerData() {

        jdbcCommodityDataDao.deleteWeeklyTickerData();
        Map<String, Map<String, String>> goldValues = tickerDataService.getGoldValues();
        Map<String, Map<String, String>> silverValues = tickerDataService.getSilverValues();
        Map<String, Map<String, String>> NasdaqValues = tickerDataService.getNasdaqValues();
        Map<String, Map<String, String>> SP500Values = tickerDataService.getSP500Values();
        Map<String, Map<String, String>> DowValues = tickerDataService.getDowValues();

        if (goldValues != null) {
            return ResponseEntity.ok("commodities inserted successfully");
        } else {
            return ResponseEntity.badRequest().body("Error inserting commodities");
        }
    }

    //WEEKLY PERCENTAGE CHANGES FOR TICKER DATA
    @CrossOrigin
    @RequestMapping(path = "/weeklyPercentageChanges", method = RequestMethod.GET)
    public List<PercentageChange> listWeeklyPercentageChangeValues(){
        return jdbcCommodityDataDao.getWeeklyTickerPercentageChange();
    }
    @CrossOrigin
    @RequestMapping(path = "/allWeeklyTickers", method = RequestMethod.GET)
    public List<StockIndex> listWeeklyTickers(){
        return jdbcCommodityDataDao.getAllStockIndex();
    }
    @CrossOrigin
    @RequestMapping(path = "/allCommodities", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listWeeklyCommodities(){
        return jdbcCommodityDataDao.getAllCommodityData();
    }

    //COPPER
    @CrossOrigin
    @RequestMapping( path ="/copperAnnually", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listAnnualCopperValues(){
        return jdbcCommodityDataDao.getCopperAnnually();
    }

    @CrossOrigin
    @RequestMapping( path ="/copperMonthly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listMonthlyCopperValues(){
        return jdbcCommodityDataDao.getCopperMonthly();
    }

    @CrossOrigin
    @RequestMapping( path ="/copperQuarterly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listQuarterlyCopperValues(){
        return jdbcCommodityDataDao.getCopperQuarterly();
    }

    //ALUMINUM
    @CrossOrigin
    @RequestMapping( path ="/aluminumAnnually", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listAnnualAluminumValues(){
        return jdbcCommodityDataDao.getAluminumAnnually();
    }

    @CrossOrigin
    @RequestMapping( path ="/aluminumMonthly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listMonthlyAluminumValues(){
        return jdbcCommodityDataDao.getAluminumMonthly();
    }

    @CrossOrigin
    @RequestMapping( path ="/aluminumQuarterly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listQuarterlyAluminumValues(){
        return jdbcCommodityDataDao.getAluminumQuarterly();
    }

    //WHEAT
    @CrossOrigin
    @RequestMapping( path ="/wheatAnnually", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listAnnualWheatValues(){
        return jdbcCommodityDataDao.getWheatAnnually();
    }

    @CrossOrigin
    @RequestMapping( path ="/wheatMonthly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listMonthlyWheatValues(){
        return jdbcCommodityDataDao.getWheatMonthly();
    }

    @CrossOrigin
    @RequestMapping( path ="/wheatQuarterly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listQuarterlyWheatValues(){
        return jdbcCommodityDataDao.getWheatQuarterly();
    }

    //CORN
    @CrossOrigin
    @RequestMapping( path ="/cornAnnually", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listAnnualCornValues(){
        return jdbcCommodityDataDao.getCornAnnually();
    }

    @CrossOrigin
    @RequestMapping( path ="/cornMonthly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listMonthlyCornValues(){
        return jdbcCommodityDataDao.getCornMonthly();
    }

    @CrossOrigin
    @RequestMapping( path ="/cornQuarterly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listQuarterlyCornValues(){
        return jdbcCommodityDataDao.getCornQuarterly();
    }

    //COTTON
    @CrossOrigin
    @RequestMapping( path ="/cottonAnnually", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listAnnualCottonValues(){
        return jdbcCommodityDataDao.getCottonAnnually();
    }

    @CrossOrigin
    @RequestMapping( path ="/cottonMonthly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listMonthlyCottonValues(){
        return jdbcCommodityDataDao.getCottonMonthly();
    }

    @CrossOrigin
    @RequestMapping( path ="/cottonQuarterly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listQuarterlyCottonValues(){
        return jdbcCommodityDataDao.getCottonQuarterly();
    }

    //SUGAR
    @CrossOrigin
    @RequestMapping( path ="/sugarAnnually", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listAnnualSugarValues(){
        return jdbcCommodityDataDao.getSugarAnnually();
    }

    @CrossOrigin
    @RequestMapping( path ="/sugarMonthly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listMonthlySugarValues(){
        return jdbcCommodityDataDao.getSugarMonthly();
    }

    @CrossOrigin
    @RequestMapping( path ="/sugarQuarterly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listQuarterlySugarValues(){
        return jdbcCommodityDataDao.getSugarQuarterly();
    }

    //COFFEE
    @CrossOrigin
    @RequestMapping( path ="/coffeeAnnually", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listAnnualCoffeeValues(){
        return jdbcCommodityDataDao.getCoffeeAnnually();
    }

    @CrossOrigin
    @RequestMapping( path ="/coffeeMonthly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listMonthlyCoffeeValues(){
        return jdbcCommodityDataDao.getCoffeeMonthly();
    }

    @CrossOrigin
    @RequestMapping( path ="/coffeeQuarterly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listQuarterlyCoffeeValues(){
        return jdbcCommodityDataDao.getCoffeeQuarterly();
    }

    //ALL COMMODITIES
    @CrossOrigin
    @RequestMapping( path ="/commodityIndexAnnually", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listCommodityIndexAnnualValues(){
        return jdbcCommodityDataDao.getGlobalIndexAnnually();
    }

    @CrossOrigin
    @RequestMapping( path ="/commodityIndexMonthly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listCommodityIndexMonthlyValues(){
        return jdbcCommodityDataDao.getGlobalIndexMonthly();
    }

    @CrossOrigin
    @RequestMapping( path ="/commodityIndexQuarterly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listCommodityIndexQuarterlyValues(){
        return jdbcCommodityDataDao.getGlobalIndexQuarterly();
    }

    @CrossOrigin
    @RequestMapping( path ="/wtiDaily", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listWtiDailyValues(){
        return jdbcCommodityDataDao.getWtiDaily();
    }

    @CrossOrigin
    @RequestMapping( path ="/wtiWeekly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listWtiWeeklyValues(){
        return jdbcCommodityDataDao.getWtiWeekly();
    }

    @CrossOrigin
    @RequestMapping( path ="/wtiMonthly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listWtiMonthlyValues(){
        return jdbcCommodityDataDao.getWtiMonthly();
    }

    @CrossOrigin
    @RequestMapping( path ="/brentDaily", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listBrentDailyValues(){
        return jdbcCommodityDataDao.getBrentDaily();
    }

    @CrossOrigin
    @RequestMapping( path ="/brentWeekly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listBrentWeeklyValues(){
        return jdbcCommodityDataDao.getBrentWeekly();
    }

    @CrossOrigin
    @RequestMapping( path ="/brentMonthly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listBrentMonthlyValues(){
        return jdbcCommodityDataDao.getBrentMonthly();
    }

    @CrossOrigin
    @RequestMapping( path ="/naturalGasDaily", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listNaturalGasDailyValues(){
        return jdbcCommodityDataDao.getNaturalGasDaily();
    }

    @CrossOrigin
    @RequestMapping( path ="/naturalGasWeekly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listNaturalGasWeeklyValues(){
        return jdbcCommodityDataDao.getNaturalGasWeekly();
    }

    @CrossOrigin
    @RequestMapping( path ="/naturalGasMonthly", method = RequestMethod.GET)
    public List<newsApp.model.Commodity> listNaturalGasMonthlyValues(){
        return jdbcCommodityDataDao.getNaturalGaseMonthly();
    }


}
