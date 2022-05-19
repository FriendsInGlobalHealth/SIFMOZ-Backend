package mz.org.fgh.sifmoz.backend.reports.patients

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.sifmoz.backend.clinic.Clinic
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.District
import mz.org.fgh.sifmoz.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.sifmoz.backend.multithread.ReportSearchParams
import mz.org.fgh.sifmoz.backend.patient.Patient
import mz.org.fgh.sifmoz.backend.reports.pharmacyManagement.historicoLevantamento.HistoricoLevantamentoReport

import java.text.SimpleDateFormat

@Transactional
@Service(ActivePatientReport)
abstract class ActivePatientReportService implements IActivePatientReportService {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy")

    @Override
    ActivePatientReport get(Serializable id) {
        return ActivePatientReport.findById(id as String)
    }

    @Override
    List<ActivePatientReport> list(Map args) {
        return null
    }

    @Override
    Long count() {
        return null
    }

    @Override
    ActivePatientReport delete(Serializable id) {
        return null
    }

    @Override
    void doSave(List<ActivePatientReport> patients) {
        for (patient in patients) {
            save(patient)
        }
    }

    @Override
    List<ActivePatientReport> getReportDataByReportId(String reportId) {
        return ActivePatientReport.findAllByReportId(reportId)
    }

    @Override
    List<ActivePatientReport> processamentoDados(ReportSearchParams reportSearchParams) {

        List<ActivePatientReport> resultList = new ArrayList<>()
//
        String reportId = reportSearchParams.getId()
        //---------------
        String province_id = reportSearchParams.getProvinceId()
        String clinicalService = reportSearchParams.getClinicalService()
        Clinic clinic = Clinic.findById(reportSearchParams.clinicId)
        //---------------

        def result = Patient.executeQuery(
                "SELECT  pat.firstNames, pat.middleNames, pat.lastNames, pat.gender,  pat.cellphone, psi.prefered, psi.value, idt.id as prefered, pack.pickupDate, pack.nextPickUpDate, pred.reasonForUpdate, regime.description, lt.description, dt.description, pat.dateOfBirth " +
                        "FROM Patient pat " +
                        "INNER JOIN PatientServiceIdentifier psi " +
                        "ON psi.patient.id = pat.id and pat.province.id = :province_id and psi.clinic.id = :clinic_id " +
                        "INNER JOIN ClinicalService cs " +
                        "ON psi.service.id = cs.id and cs.code = :clinicalService " +
                        "INNER JOIN IdentifierType idt " +
                        "ON psi.identifierType.id = idt.id " +
                        "INNER JOIN Episode ep " +
                        "ON ep.patientServiceIdentifier.id = psi.id " +
                        "INNER JOIN StartStopReason ststreason " +
                        "ON ep.startStopReason.id =  ststreason.id and ststreason.isStartReason = true " +
                        "INNER JOIN PatientVisitDetails pvd " +
                        "ON pvd.episode.id = ep.id " +
                        "INNER JOIN Pack pack " +
                        "ON pvd.pack.id = pack.id and pack.nextPickUpDate <= :endDate and DATE(pack.nextPickUpDate) + :days >= :endDate " +
                        "INNER JOIN Prescription pre " +
                        "ON pvd.prescription.id = pre.id " +
                        "INNER JOIN PrescriptionDetail pred " +
                        "ON pred.prescription.id = pre.id " +
                        "INNER JOIN TherapeuticRegimen regime " +
                        "ON pred.therapeuticRegimen.id = regime.id " +
                        "INNER JOIN DispenseType dt " +
                        "ON pred.dispenseType.id = dt.id " +
                        "INNER JOIN TherapeuticLine lt " +
                        "ON pred.therapeuticLine.id = lt.id",
                [clinic_id: clinic.id, clinicalService: clinicalService, endDate: reportSearchParams.endDate, province_id: province_id, days: 33]
        )


        //Preenchimento e registo de cada objecto do relatorio
        println(resultList.size())
        for (item in result) {
            ActivePatientReport activePatientReport = setGenericInfo(reportSearchParams, clinic, item[14] as Date)
            generateAndSaveActivePacient(item as List, reportSearchParams, activePatientReport, reportId)
            return resultList
        }
    }


    private ActivePatientReport setGenericInfo(ReportSearchParams searchParams, Clinic clinic, Date dateOfBirth) {

        ActivePatientReport activePatientReport = new ActivePatientReport()
        activePatientReport.setClinic(clinic.getClinicName())
        activePatientReport.setStartDate(searchParams.startDate)
        activePatientReport.setEndDate(searchParams.endDate)
        activePatientReport.setReportId(searchParams.id)
        activePatientReport.setPeriodType(searchParams.periodType)
        activePatientReport.setReportId(searchParams.id)
        activePatientReport.setYear(searchParams.year)
        activePatientReport.setAge(ConvertDateUtils.getAge(dateOfBirth).intValue() as String)
        activePatientReport.setProvince(Province.findById(searchParams.provinceId).description)
        activePatientReport.setDistrict(District.findById(searchParams.districtId).description)
        return activePatientReport
    }

    void generateAndSaveActivePacient(List item, ReportSearchParams reportSearchParams, ActivePatientReport activePatientReport, String reportId){

        activePatientReport.setReportId(reportId)
        activePatientReport.setFirstNames(item[0].toString())
        activePatientReport.setMiddleNames(item[1].toString())
        activePatientReport.setLastNames(item[2].toString())
        activePatientReport.setGender(item[3].toString())
        activePatientReport.setCellphone(item[4].toString())
        activePatientReport.setTherapeuticRegimen(item[11].toString())
        activePatientReport.setTherapeuticLine(item[12].toString())
        activePatientReport.setDispenseType(item[13].toString())


        //Tipo Paciente
        activePatientReport.setPatientType(" ")

        // set PickUpDate
        if (item[8] != null){
            Date pickUpDate = formatter.parse(item[8].toString())
            activePatientReport.setPickupDate(pickUpDate)
        }
        // set nextPickUpDate
        if (item[9] != null){
            Date nextPickUpDate = formatter.parse(item[9].toString())
            activePatientReport.setNextPickUpDate(nextPickUpDate)
        }

        // Setting nid
        if (item[5] != null) {
            if (item[5]) {
                activePatientReport.setNid(item[6].toString())
            } else {
                activePatientReport.setNid(item[7].toString())
            }
        }


        save(activePatientReport)

    }
}
