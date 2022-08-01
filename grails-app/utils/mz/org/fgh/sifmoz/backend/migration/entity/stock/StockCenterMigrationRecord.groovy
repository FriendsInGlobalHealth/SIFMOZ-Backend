package mz.org.fgh.sifmoz.backend.migration.entity.stock

import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.clinic.ClinicService;
import mz.org.fgh.sifmoz.backend.migration.base.record.AbstractMigrationRecord
import mz.org.fgh.sifmoz.backend.migration.base.record.MigratedRecord
import mz.org.fgh.sifmoz.backend.migrationLog.MigrationLog
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.stockcenter.StockCenter
import mz.org.fgh.sifmoz.backend.stockcenter.StockCenterService

class StockCenterMigrationRecord extends AbstractMigrationRecord {
    private Integer id

    private String stockcentername

    private boolean preferred

    private Set<StockMigrationRecord> stockMigrationRecords

    private String clinicuuid

    private boolean migration_status

    //------------------------------------------------

    StockCenterService stockCenterService
    ClinicService clinicService

    @Override
    List<MigrationLog> migrate() {
        System.out.println("Inside migrate method...");

        List<MigrationLog> logs = new ArrayList<>()
        StockCenter.withTransaction {
        getMigratedRecord().setName(this.stockcentername)
        getMigratedRecord().setCode(this.stockcentername.replaceAll(" ", "_").toUpperCase())
        getMigratedRecord().setPrefered(this.preferred)
        System.out.println(getMigratedRecord())
        System.out.println(this.clinicuuid)
            Clinic clinicAux
        try {
            clinicAux = Clinic.findByUuid(this.clinicuuid)
        } catch (Exception e) {
            System.out.println(e.getMessage())
        } finally {
            System.out.println(clinicAux)
        }
        getMigratedRecord().setClinic(clinicAux)
            if (getMigratedRecord().hasErrors()) {
                System.println("ERROR: "+getMigratedRecord().errors)
                MigrationLog migrationLog = new MigrationLog()
                migrationLog.errors = getMigratedRecord().errors
                logs.add(migrationLog)
            } else {
                System.println("NO ERROR...")
                getMigratedRecord().save(flush: true)
            }
        }

//        stockCenterService.save(getMigratedRecord())
        return logs
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
        return "stockcenter"
    }
    @Override
    MigratedRecord initMigratedRecord() {
        return new StockCenter()
    }

    @Override
    StockCenter getMigratedRecord() {
        return (StockCenter) super.getMigratedRecord();
    }
}
