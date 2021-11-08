package mz.org.fgh.sifmoz.backend.screening

import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.patientVisit.PatientVisit

class TBScreening {
    String id
    boolean parentTBTreatment
    boolean cough
    boolean fever
    boolean losingWeight
    boolean treatmentTB
    boolean treatmentTPI
  //  boolean referedToUSTB
    Date startTreatmentDate
    boolean fatigueOrTirednessLastTwoWeeks
    boolean sweating

    static belongsTo = [visit: PatientVisit]

    static mapping = {
        id generator: "uuid"
    }

    static constraints = {
        startTreatmentDate(nullable: true, blank: true)
        visit(nullable: true, blank: true)
    }

    @Override
    public String toString() {
        return "TBScreening{" +
                "parentTBTreatment=" + parentTBTreatment +
                ", cough=" + cough +
                ", fever=" + fever +
                ", losingWeight=" + losingWeight +
                ", treatmentTB=" + treatmentTB +
                ", treatmentTPI=" + treatmentTPI +
                ", startTreatmentDate=" + startTreatmentDate +
                ", fatigueOrTirednessLastTwoWeeks=" + fatigueOrTirednessLastTwoWeeks +
                ", sweating=" + sweating +
                '}';
    }
}
