package mz.org.fgh.sifmoz.backend.migration.entity.stock

import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.stockcenter.StockCenter
import mz.org.fgh.sifmoz.backend.stockentrance.StockEntrance

import java.text.DateFormat
import java.text.SimpleDateFormat

class StockMigrationRecord extends AbstractMigrationRecord {

    private Integer id;

    private String batchnumber;

    private Date datereceived;

    private Date expirydate;

    private char modified;

    private String shelfnumber;

    private String numeroguia;

    private int unitsreceived;

    private String manufacturer;

    private char hasunitsremaining;

    private BigDecimal unitprice;

    private String stockcentername

    String atccode_id

    String name

    private boolean  mainclinic

    private String clinicname

    private StockTakeMigrationRecord stockTakeMigrationRecord;

    private StockAdjustmentMigrationRecord stockAdjustmentMigrationRecord;

    //----------------------------------------------------

    @Override
    List<MigrationLog> migrate() {
        List<MigrationLog> logs = new ArrayList<>()
        Stock.withTransaction {
            def clinic = Clinic.findWhere(mainClinic: true)
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy")
            String dateFormated = dateFormat.format(this.datereceived)
            Date receivedDate = dateFormat.parse(dateFormated)
            StockEntrance stockEntrance = new  StockEntrance()

            // Setting StockEntrance
            stockEntrance.setClinic(clinic)
            stockEntrance.setDateReceived(receivedDate)
            stockEntrance.setOrderNumber(dateFormated + "_NA")

            // Setting Stock
            getMigratedRecord().setClinic(clinic)
            getMigratedRecord().setBatchNumber(this.batchnumber)
            getMigratedRecord().setModified(this.modified == (char) 'T')
            getMigratedRecord().setUnitsReceived(this.unitsreceived)
            getMigratedRecord().setExpireDate(this.expirydate)
            getMigratedRecord().setShelfNumber(this.shelfnumber)
            getMigratedRecord().setManufacture(this.manufacturer)
            getMigratedRecord().setHasUnitsRemaining(this.hasunitsremaining == (char) 'T')


            // Auxiliar
            def stockEntranceAux = StockEntrance.findWhere(dateReceived: receivedDate)
            def stockCenterAux = StockCenter.findWhere(name: this.stockcentername)
            List<Drug> drugList = Drug.list()
            def drugAux = Drug.findByFnmCode(this.atccode_id)
//            def drugAux = Drug.findWhere(fnmCode: this.atccode_id)
            getMigratedRecord().setCenter(stockCenterAux)
            getMigratedRecord().setDrug(drugAux)


            if (stockEntranceAux != null) {// Ja existe o stockEntrance // migrar apenas o Stock

                getMigratedRecord().setEntrance(stockEntranceAux) // Setting numero de guia => (id do stockEntrance)

                //Saving Stock
                if (getMigratedRecord().hasErrors()) {
                    MigrationLog migrationLog = new MigrationLog()
                    migrationLog.errors = getMigratedRecord().errors
                    logs.add(migrationLog)
                } else {
                    getMigratedRecord().save(flush: true)
                }
            } else {// O stockEntrance ainda nao existe
                // migrar entrance e o Stock

                def stockEntranceAux1 = null
                //Saving StockEntrance
                if (stockEntrance.hasErrors()) {
                    MigrationLog migrationLog = new MigrationLog()
                    migrationLog.errors = stockEntrance().errors
                    logs.add(migrationLog)
                } else {
                    stockEntranceAux1 = stockEntrance.save(flush: true)
                }

                if (stockEntranceAux1 != null) {
                    getMigratedRecord().setEntrance(stockEntranceAux1)
                    getMigratedRecord().setCenter(stockCenterAux)
                    getMigratedRecord().setHasUnitsRemaining(this.hasunitsremaining == (char) 'T')
                    getMigratedRecord().setDrug(drugAux)

                    //Saving Stock
                    if (getMigratedRecord().hasErrors()) {
                        MigrationLog migrationLog = new MigrationLog()
                        migrationLog.errors = getMigratedRecord().errors
                        logs.add(migrationLog)
                    } else {
                        getMigratedRecord().save(flush: true)
                    }
                }
            }
        }

        return null
    }

    @Override
    void updateIDMEDInfo() {

    }

    @Override
    int getId() {
        return this.id
    }

    @Override
    String getEntityName() {
        return "stock"
    }

    @Override
    MigratedRecord initMigratedRecord() {
        return new Stock()
    }

    Stock getMigratedRecord() {
        return (Stock) super.getMigratedRecord()
    }
}
