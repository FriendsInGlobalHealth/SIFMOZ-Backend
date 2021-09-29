package mz.org.fgh.sifmoz.backend.patientVisitDetails

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PatientVisitDetailsClinicalServiceSpec extends Specification {

    PatientVisitDetailsService patientVisitDetailsService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PatientVisitDetails(...).save(flush: true, failOnError: true)
        //new PatientVisitDetails(...).save(flush: true, failOnError: true)
        //PatientVisitDetails patientVisitDetails = new PatientVisitDetails(...).save(flush: true, failOnError: true)
        //new PatientVisitDetails(...).save(flush: true, failOnError: true)
        //new PatientVisitDetails(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //patientVisitDetails.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        patientVisitDetailsService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PatientVisitDetails> patientVisitDetailsList = patientVisitDetailsService.list(max: 2, offset: 2)

        then:
        patientVisitDetailsList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        patientVisitDetailsService.count() == 5
    }

    void "test delete"() {
        Long patientVisitDetailsId = setupData()

        expect:
        patientVisitDetailsService.count() == 5

        when:
        patientVisitDetailsService.delete(patientVisitDetailsId)
        datastore.currentSession.flush()

        then:
        patientVisitDetailsService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PatientVisitDetails patientVisitDetails = new PatientVisitDetails()
        patientVisitDetailsService.save(patientVisitDetails)

        then:
        patientVisitDetails.id != null
    }
}
