package mz.org.fgh.sifmoz.backend.drug

import com.fasterxml.jackson.annotation.JsonBackReference
import grails.rest.Resource
import mz.org.fgh.sifmoz.backend.clinicSector.ClinicSector
import mz.org.fgh.sifmoz.backend.doctor.Doctor
import mz.org.fgh.sifmoz.backend.episode.Episode
import mz.org.fgh.sifmoz.backend.form.Form
import mz.org.fgh.sifmoz.backend.stock.Stock
import mz.org.fgh.sifmoz.backend.therapeuticRegimen.TherapeuticRegimen
import mz.org.fgh.sifmoz.backend.service.ClinicalService

class Drug {
    String id
    int packSize
    String name
    double defaultTreatment // numero de toma
    int defaultTimes// numero de vezes a tomar
    String defaultPeriodTreatment //  periofo a tomar --commbo (dia , semana , mes, ano)
    String fnmCode
    String uuidOpenmrs
    ClinicalService clinicalService
    @JsonBackReference
    Form form
    static belongsTo = [Form]
    boolean active
    static hasMany = [stockList: Stock]
    static mapping = {
        id generator: "uuid"
        form lazy: true
    }

    static constraints = {
        name nullable: false, blank: false
        fnmCode nullable: false, unique: true
        packSize(min: 0)
        uuidOpenmrs nullable: true
        clinicalService nullable: true
     //   defaultTreatment(min: 1.00)
        defaultTimes(min:1)
        stockList nullable: true
    }
}
