package newsApp.dao;

import newsApp.model.Commodity;
import newsApp.model.PercentageChange;
import newsApp.model.StockIndex;

import java.util.List;

public interface StockDataDao {

    void insertCommodity(String Date, String value, String commodity);
    void insertWeeklyTickerData(String Date, double closingPrice, String index_of);

    void deleteWeeklyTickerData();

    //weekly ticker percentage change
    List<PercentageChange> getWeeklyTickerPercentageChange();
List<Commodity> getAllCommodityData();

    List<StockIndex> getAllStockIndex();
    //COPPER
    List<Commodity> getCopperMonthly();
    List<Commodity> getCopperAnnually();
    List<Commodity> getCopperQuarterly();

    //ALUMINUM
    List<Commodity> getAluminumMonthly();
    List<Commodity> getAluminumAnnually();
    List<Commodity> getAluminumQuarterly();

    //WHEAT
    List<Commodity> getWheatMonthly();
    List<Commodity> getWheatAnnually();
    List<Commodity> getWheatQuarterly();

    //CORN
    List<Commodity> getCornMonthly();
    List<Commodity> getCornAnnually();
    List<Commodity> getCornQuarterly();

    //COTTON
    List<Commodity> getCottonMonthly();
    List<Commodity> getCottonAnnually();
    List<Commodity> getCottonQuarterly();

    //SUGAR
    List<Commodity> getSugarMonthly();
    List<Commodity> getSugarAnnually();
    List<Commodity> getSugarQuarterly();

    //COFFEE
    List<Commodity> getCoffeeMonthly();
    List<Commodity> getCoffeeAnnually();
    List<Commodity> getCoffeeQuarterly();

    //GLOBAL COMMODITY INDEX

    List<Commodity> getGlobalIndexMonthly();
    List<Commodity> getGlobalIndexAnnually();
    List<Commodity> getGlobalIndexQuarterly();

    //WTI OIL
    List<Commodity> getWtiDaily();
    List<Commodity> getWtiWeekly();
    List<Commodity> getWtiMonthly();

    //BRENT OIL
    List<Commodity> getBrentDaily();
    List<Commodity> getBrentWeekly();
    List<Commodity> getBrentMonthly();

    //NATURAL GAS
    List<Commodity> getNaturalGasDaily();
    List<Commodity> getNaturalGasWeekly();
    List<Commodity> getNaturalGaseMonthly();
}
