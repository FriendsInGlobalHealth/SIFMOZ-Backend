package mz.org.fgh.sifmoz.backend.patientIdentifier

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.identifierType.IdentifierType
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.service.ClinicalService

class PatientServiceIdentifier {
    String id
    Date startDate
    Date endDate
    Date reopenDate
    String value
    String state
    boolean prefered
    @JsonManagedReference
    IdentifierType identifierType
    @JsonManagedReference
    ClinicalService service
    @JsonIgnore
    Clinic clinic

    @JsonManagedReference
    Patient patient
    static belongsTo = [Patient]

    @JsonManagedReference
    static hasMany = [episodes: Episode]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        value unique: ['patient', 'service']
        startDate(nullable: true, blank: true, validator: { startDate, urc ->
            return startDate != null ? startDate <= new Date() : null
        })
        endDate nullable: true
        reopenDate nullable: true
    }


//    @Override
//    public String toString() {
//        return "PatientServiceIdentifier{" +
//                "patient=" + patient +
//                ", episodes=" + episodes +
//                ", id='" + id + '\'' +
//                ", startDate=" + startDate +
//                ", endDate=" + endDate +
//                ", reopenDate=" + reopenDate +
//                ", value='" + value + '\'' +
//                ", state='" + state + '\'' +
//                ", prefered=" + prefered +
//                ", identifierType=" + identifierType +
//                ", service=" + service +
//                ", clinic=" + clinic +
//                '}';
//    }
}
