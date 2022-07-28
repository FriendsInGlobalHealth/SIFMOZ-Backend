package mz.org.fgh.sifmoz.migration.params.stock;

import mz.org.fgh.sifmoz.migration.base.search.params.AbstractMigrationSearchParams;
import mz.org.fgh.sifmoz.migration.entity.stock.StockCenterMigrationRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grails.web.json.JSONArray;

import java.util.Arrays;
import java.util.List;

public class StockCenterMigrationSearchParams extends AbstractMigrationSearchParams<StockCenterMigrationRecord> {

    static Logger logger = LogManager.getLogger(StockMigrationSearchParams.class);

    @Override
    public List<StockCenterMigrationRecord> doSearch(long limit) {
        JSONArray jsonArray = getRestServiceProvider().get("/stockcenter?limit=100");
        this.searchResults.clear();
        StockCenterMigrationRecord[] stockCenterMigrationRecords = gson.fromJson(jsonArray.toString(), StockCenterMigrationRecord[].class);
        logger.info(jsonArray.toString());

        if (stockCenterMigrationRecords != null && stockCenterMigrationRecords.length > 0) {
            this.searchResults.addAll(Arrays.asList(stockCenterMigrationRecords));
        }
        return this.searchResults;
    }
}