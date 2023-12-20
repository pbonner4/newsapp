package newsApp.dao;

import newsApp.model.Commodity;
import newsApp.model.Movie;
import newsApp.model.PercentageChange;
import newsApp.model.StockIndex;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcCommodityDataDao implements StockDataDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCommodityDataDao(JdbcTemplate JdbcTemplate){
        this.jdbcTemplate = JdbcTemplate;
    }


    @Override
    public void insertCommodity(String Date, String value, String commodity) {
        String sql = "INSERT into commodities (Date, value, commodity_type) VALUES  (?,?,?);";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        java.util.Date parsedDate = null;
        try {
            parsedDate = sdf.parse(Date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, sqlTimestamp, value, commodity);
    }

    @Override
    public void insertWeeklyTickerData(String Date, double closingPrice, String index_of) {
        String sql = "INSERT into weekly_ticker_closing_prices (closing_price, index_of, date) VALUES  (?,?,?);";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        java.util.Date parsedDate = null;
        try {
            parsedDate = sdf.parse(Date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

        jdbcTemplate.update(sql, closingPrice, index_of, sqlTimestamp);
    }

    @Override
    public void deleteWeeklyTickerData(){
        String sql = "DELETE FROM weekly_ticker_closing_prices;";

        jdbcTemplate.update(sql);
    }

    @Override
    public List<PercentageChange> getWeeklyTickerPercentageChange(){

        List<PercentageChange> percentageChanges = new ArrayList<>();

        String dow = "SELECT\n" +
                "  'DOW ' AS index_of,\n" +
                "  (\n" +
                "    (\n" +
                "      SELECT closing_price\n" +
                "      FROM weekly_ticker_closing_prices\n" +
                "      WHERE index_of = 'DOW'\n" +
                "      LIMIT 1\n" +
                "    )\n" +
                "    -\n" +
                "    (\n" +
                "      SELECT closing_price\n" +
                "      FROM weekly_ticker_closing_prices\n" +
                "      WHERE index_of = 'DOW'\n" +
                "      LIMIT 1 OFFSET 1\n" +
                "    )\n" +
                "  )\n" +
                "  /\n" +
                "  (\n" +
                "    SELECT closing_price\n" +
                "    FROM weekly_ticker_closing_prices\n" +
                "    WHERE index_of = 'DOW'\n" +
                "    LIMIT 1 OFFSET 1\n" +
                "  )\n" +
                "  * 100 AS percentage_change;\n";

        String sp500 = "SELECT\n" +
                "  'SP500' AS index_of,\n" +
                "  (\n" +
                "    (\n" +
                "      SELECT closing_price\n" +
                "      FROM weekly_ticker_closing_prices\n" +
                "      WHERE index_of = 'SP500'\n" +
                "      LIMIT 1\n" +
                "    )\n" +
                "    -\n" +
                "    (\n" +
                "      SELECT closing_price\n" +
                "      FROM weekly_ticker_closing_prices\n" +
                "      WHERE index_of = 'SP500'\n" +
                "      LIMIT 1 OFFSET 1\n" +
                "    )\n" +
                "  )\n" +
                "  /\n" +
                "  (\n" +
                "    SELECT closing_price\n" +
                "    FROM weekly_ticker_closing_prices\n" +
                "    WHERE index_of = 'SP500'\n" +
                "    LIMIT 1 OFFSET 1\n" +
                "  )\n" +
                "  * 100 AS percentage_change;\n";

        String nasdaq = "SELECT\n" +
                "  'NASDAQ' AS index_of,\n" +
                "  (\n" +
                "    (\n" +
                "      SELECT closing_price\n" +
                "      FROM weekly_ticker_closing_prices\n" +
                "      WHERE index_of = 'NASDAQ'\n" +
                "      LIMIT 1\n" +
                "    )\n" +
                "    -\n" +
                "    (\n" +
                "      SELECT closing_price\n" +
                "      FROM weekly_ticker_closing_prices\n" +
                "      WHERE index_of = 'NASDAQ'\n" +
                "      LIMIT 1 OFFSET 1\n" +
                "    )\n" +
                "  )\n" +
                "  /\n" +
                "  (\n" +
                "    SELECT closing_price\n" +
                "    FROM weekly_ticker_closing_prices\n" +
                "    WHERE index_of = 'NASDAQ'\n" +
                "    LIMIT 1 OFFSET 1\n" +
                "  )\n" +
                "  * 100 AS percentage_change;\n";

        String gold = "SELECT\n" +
                "  'GOLD' AS index_of,\n" +
                "  (\n" +
                "    (\n" +
                "      SELECT closing_price\n" +
                "      FROM weekly_ticker_closing_prices\n" +
                "      WHERE index_of = 'GOLD'\n" +
                "      LIMIT 1\n" +
                "    )\n" +
                "    -\n" +
                "    (\n" +
                "      SELECT closing_price\n" +
                "      FROM weekly_ticker_closing_prices\n" +
                "      WHERE index_of = 'GOLD'\n" +
                "      LIMIT 1 OFFSET 1\n" +
                "    )\n" +
                "  )\n" +
                "  /\n" +
                "  (\n" +
                "    SELECT closing_price\n" +
                "    FROM weekly_ticker_closing_prices\n" +
                "    WHERE index_of = 'GOLD'\n" +
                "    LIMIT 1 OFFSET 1\n" +
                "  )\n" +
                "  * 100 AS percentage_change;\n";

        String silver = "SELECT\n" +
                "  'SILVER' AS index_of,\n" +
                "  (\n" +
                "    (\n" +
                "      SELECT closing_price\n" +
                "      FROM weekly_ticker_closing_prices\n" +
                "      WHERE index_of = 'SILVER'\n" +
                "      LIMIT 1\n" +
                "    )\n" +
                "    -\n" +
                "    (\n" +
                "      SELECT closing_price\n" +
                "      FROM weekly_ticker_closing_prices\n" +
                "      WHERE index_of = 'SILVER'\n" +
                "      LIMIT 1 OFFSET 1\n" +
                "    )\n" +
                "  )\n" +
                "  /\n" +
                "  (\n" +
                "    SELECT closing_price\n" +
                "    FROM weekly_ticker_closing_prices\n" +
                "    WHERE index_of = 'SILVER'\n" +
                "    LIMIT 1 OFFSET 1\n" +
                "  )\n" +
                "  * 100 AS percentage_change;\n";

        SqlRowSet silverRowSet = jdbcTemplate.queryForRowSet(silver);
        SqlRowSet goldRowSet = jdbcTemplate.queryForRowSet(gold);
        SqlRowSet nasdaqRowSet = jdbcTemplate.queryForRowSet(nasdaq);
        SqlRowSet sp500RowSet = jdbcTemplate.queryForRowSet(sp500);
        SqlRowSet dowRowSet = jdbcTemplate.queryForRowSet(dow);

        while (silverRowSet.next()) {
            percentageChanges.add(mapRowFromPercentageChange(silverRowSet));
        }
        while (goldRowSet.next()) {
            percentageChanges.add(mapRowFromPercentageChange(goldRowSet));
        }
        while (nasdaqRowSet.next()) {
            percentageChanges.add(mapRowFromPercentageChange(nasdaqRowSet));
        }
        while (sp500RowSet.next()) {
            percentageChanges.add(mapRowFromPercentageChange(sp500RowSet));
        }
        while (dowRowSet.next()) {
            percentageChanges.add(mapRowFromPercentageChange(dowRowSet));
        }

        return percentageChanges;
    }

    @Override
    public List<StockIndex> getAllStockIndex(){
        List<StockIndex> StockIndexes = new ArrayList<>();

        String sql = "SELECT * FROM weekly_ticker_closing_prices ORDER BY date Desc;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            StockIndexes.add(mapRowFromStockIndex(rowSet));
        }

        return StockIndexes;
    }
    @Override
    public List<Commodity> getAllCommodityData(){
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT * FROM commodities ORDER BY date Desc;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getCopperAnnually(){
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT * FROM commodities WHERE EXTRACT(MONTH FROM date) = 1 AND commodity_type = 'COPPER' ORDER BY date;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }
    @Override
    public List<Commodity> getCopperQuarterly(){
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'COPPER'\n" +
                "GROUP BY DATE_TRUNC('quarter', date), date, value, commodity_type, id\n" +
                "ORDER BY date;\n";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getCopperMonthly(){
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'COPPER'\n" +
                "GROUP BY DATE_TRUNC('month', date), date, value, commodity_type, id\n" +
                "ORDER BY date;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getAluminumMonthly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'ALUMINUM'\n" +
                "GROUP BY DATE_TRUNC('month', date), date, value, commodity_type, id\n" +
                "ORDER BY date;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getAluminumAnnually() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT * FROM commodities WHERE EXTRACT(MONTH FROM date) = 1 AND commodity_type = 'ALUMINUM' ORDER BY date;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getAluminumQuarterly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'ALUMINUM'\n" +
                "GROUP BY DATE_TRUNC('quarter', date), date, value, commodity_type, id\n" +
                "ORDER BY date;\n";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getWheatMonthly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'WHEAT'\n" +
                "GROUP BY DATE_TRUNC('month', date), date, value, commodity_type, id\n" +
                "ORDER BY date;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getWheatAnnually() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT * FROM commodities WHERE EXTRACT(MONTH FROM date) = 1 AND commodity_type = 'WHEAT' ORDER BY date;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getWheatQuarterly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'WHEAT'\n" +
                "GROUP BY DATE_TRUNC('quarter', date), date, value, commodity_type, id\n" +
                "ORDER BY date;\n";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getCornMonthly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'CORN'\n" +
                "GROUP BY DATE_TRUNC('month', date), date, value, commodity_type, id\n" +
                "ORDER BY date;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getCornAnnually() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT * FROM commodities WHERE EXTRACT(MONTH FROM date) = 1 AND commodity_type = 'CORN' ORDER BY date;;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getCornQuarterly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'CORN'\n" +
                "GROUP BY DATE_TRUNC('quarter', date), date, value, commodity_type, id\n" +
                "ORDER BY date;\n";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getCottonMonthly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'COTTON'\n" +
                "GROUP BY DATE_TRUNC('month', date), date, value, commodity_type, id\n" +
                "ORDER BY date;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getCottonAnnually() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT * FROM commodities WHERE EXTRACT(MONTH FROM date) = 1 AND commodity_type = 'COTTON' ORDER BY date;;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getCottonQuarterly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'COTTON'\n" +
                "GROUP BY DATE_TRUNC('quarter', date), date, value, commodity_type, id\n" +
                "ORDER BY date;\n";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getSugarMonthly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'SUGAR'\n" +
                "GROUP BY DATE_TRUNC('month', date), date, value, commodity_type, id\n" +
                "ORDER BY date;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getSugarAnnually() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT * FROM commodities WHERE EXTRACT(MONTH FROM date) = 1 AND commodity_type = 'SUGAR' ORDER BY date;;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getSugarQuarterly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'SUGAR'\n" +
                "GROUP BY DATE_TRUNC('quarter', date), date, value, commodity_type, id\n" +
                "ORDER BY date;\n";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getCoffeeMonthly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'COFFEE'\n" +
                "GROUP BY DATE_TRUNC('month', date), date, value, commodity_type, id\n" +
                "ORDER BY date;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getCoffeeAnnually() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT * FROM commodities WHERE EXTRACT(MONTH FROM date) = 1 AND commodity_type = 'COFFEE' ORDER BY date;;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getCoffeeQuarterly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'COFFEE'\n" +
                "GROUP BY DATE_TRUNC('quarter', date), date, value, commodity_type, id\n" +
                "ORDER BY date;\n";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getGlobalIndexMonthly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'ALL_COMMODITIES'\n" +
                "GROUP BY DATE_TRUNC('month', date), date, value, commodity_type, id\n" +
                "ORDER BY date;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getGlobalIndexAnnually() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT * FROM commodities WHERE EXTRACT(MONTH FROM date) = 1 AND commodity_type = 'ALL_COMMODITIES' ORDER BY date;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getGlobalIndexQuarterly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT *\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'ALL_COMMODITIES'\n" +
                "GROUP BY DATE_TRUNC('quarter', date), date, value, commodity_type, id\n" +
                "ORDER BY date;\n";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getWtiDaily() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT * FROM commodities WHERE commodity_type = 'WTI' ORDER BY date;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getWtiWeekly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT DATE_TRUNC('week', date) as date, commodity_type, id, value\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'WTI'\n" +
                "GROUP BY date, commodity_type, id, value\n" +
                "ORDER BY DATE_TRUNC('week', date)";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getWtiMonthly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT DATE_TRUNC('month', date) as date, commodity_type, id, value\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'WTI'\n" +
                "GROUP BY date, commodity_type, id, value\n" +
                "ORDER BY DATE_TRUNC('month', date)";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getBrentDaily() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT * FROM commodities WHERE commodity_type = 'BRENT' ORDER BY date;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getBrentWeekly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT DATE_TRUNC('month', date) as date, commodity_type, id, value\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'BRENT'\n" +
                "GROUP BY date, commodity_type, id, value\n" +
                "ORDER BY DATE_TRUNC('month', date)";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getBrentMonthly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT DATE_TRUNC('week', date) as date, commodity_type, id, value\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'BRENT'\n" +
                "GROUP BY date, commodity_type, id, value\n" +
                "ORDER BY DATE_TRUNC('month', date)";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getNaturalGasDaily() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT * FROM commodities WHERE commodity_type = 'NATURAL_GAS' ORDER BY date;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getNaturalGasWeekly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT DATE_TRUNC('week', date) as date, commodity_type, id, value\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'NATURAL_GAS'\n" +
                "GROUP BY date, commodity_type, id, value\n" +
                "ORDER BY DATE_TRUNC('week', date)";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }

    @Override
    public List<Commodity> getNaturalGaseMonthly() {
        List<Commodity> commodities = new ArrayList<>();

        String sql = "SELECT DATE_TRUNC('week', date) as date, commodity_type, id, value\n" +
                "FROM commodities\n" +
                "WHERE commodity_type = 'NATURAL_GAS'\n" +
                "GROUP BY date, commodity_type, id, value\n" +
                "ORDER BY DATE_TRUNC('month', date)";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            commodities.add(mapRowFromCommodities(rowSet));
        }

        return commodities;
    }


    private Commodity mapRowFromCommodities(SqlRowSet rs){
        Commodity commodity = new Commodity();
        commodity.setId(rs.getInt("id"));
        commodity.setDate(rs.getString("date"));
        commodity.setValue(rs.getString("value"));
        commodity.setCommodityType(rs.getString("commodity_type"));

        return commodity;
    }

    private StockIndex mapRowFromStockIndex(SqlRowSet rs){
        StockIndex stockIndex = new StockIndex();
        stockIndex.setId(rs.getInt("id"));
        stockIndex.setClosing_price(rs.getFloat("closing_price"));
        stockIndex.setIndex_of(rs.getString("index_of"));
        stockIndex.setDate(rs.getString("date"));

        return stockIndex;
    }

    private PercentageChange mapRowFromPercentageChange(SqlRowSet rs){
        PercentageChange percentageChange = new PercentageChange();
        percentageChange.setIndex(rs.getString("index_of"));
        percentageChange.setPercentage_change(rs.getDouble("percentage_change"));

        return percentageChange;
    }
}
