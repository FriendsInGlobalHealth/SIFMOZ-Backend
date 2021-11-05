package mz.org.fgh.sifmoz.backend.screening

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit

class RAMScreening {
    String id
    String adverseReaction
    boolean adverseReactionMedicine
   // boolean referedToUSRam
    static belongsTo = [visit: PatientVisit]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        adverseReaction(nullable: true, blank: true)
    }

    @Override
    public String toString() {
        return "RAMScreening{" +
                "adverseReaction='" + adverseReaction + '\'' +
                ", adverseReactionMedicine=" + adverseReactionMedicine +
                ", referedToUSRam=" + referedToUSRam +
                '}';
    }
}
