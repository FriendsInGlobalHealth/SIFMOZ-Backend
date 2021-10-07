package mz.org.fgh.sifmoz.backend.stockinventory

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.drug.Drug
import mz.org.fgh.sifmoz.backend.stockadjustment.InventoryStockAdjustment

class Inventory {
    String id
    Date startDate
    Date endDate
    boolean open
    boolean generic
    int sequence
    static hasMany = [adjustments : InventoryStockAdjustment]
    static transients = ['inventoryDrugs']
    List<Drug> inventoryDrugs
    Clinic clinic
    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        startDate(nullable: false, blank: false, validator: { startDate, urc ->
            return ((startDate <= new Date() ))
        })

        endDate(nullable: false, blank: false, validator: { endDate, urc ->
            return ((urc.startDate < endDate))
        })
    }

    public int  generateSequence(){
        return 0;
    }

    public void close() {
        this.open = false
        this.endDate = new Date()
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", open=" + open +
                ", sequence=" + sequence +
                '}';
    }

}
