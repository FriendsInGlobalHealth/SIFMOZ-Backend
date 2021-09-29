package mz.org.fgh.sifmoz.backend.patientVisit

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PatientVisitClinicalServiceSpec extends Specification {

    PatientVisitService patientVisitService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PatientVisit(...).save(flush: true, failOnError: true)
        //new PatientVisit(...).save(flush: true, failOnError: true)
        //PatientVisit patientVisit = new PatientVisit(...).save(flush: true, failOnError: true)
        //new PatientVisit(...).save(flush: true, failOnError: true)
        //new PatientVisit(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //patientVisit.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        patientVisitService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PatientVisit> patientVisitList = patientVisitService.list(max: 2, offset: 2)

        then:
        patientVisitList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        patientVisitService.count() == 5
    }

    void "test delete"() {
        Long patientVisitId = setupData()

        expect:
        patientVisitService.count() == 5

        when:
        patientVisitService.delete(patientVisitId)
        datastore.currentSession.flush()

        then:
        patientVisitService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PatientVisit patientVisit = new PatientVisit()
        patientVisitService.save(patientVisit)

        then:
        patientVisit.id != null
    }
}
