package mz.org.fgh.sifmoz.backend.serviceattribute

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.base.BaseEntity
import mz.org.fgh.sifmoz.backend.service.ClinicalService
import mz.org.fgh.sifmoz.backend.serviceattributetype.ClinicalServiceAttributeType

class ClinicalServiceAttribute extends BaseEntity {
    String id
    @JsonManagedReference
    ClinicalServiceAttributeType clinicalServiceAttributeType

    @JsonIgnore
    ClinicalService clinicalService
    static belongsTo = [ClinicalService]

    static mapping = {
        id generator: "assigned"
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
    }
}
