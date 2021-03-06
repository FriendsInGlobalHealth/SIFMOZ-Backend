package sifmoz.backend

class UrlMappings {

    static mappings = {
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")
        patch "/inventory/close/$id(.$format)?"(controller:'inventory', action:'close')
        post "/patient/search(.$format)?"(controller:'patient', action:'search')
        get "/patient/openmrsSession/$interoperabilityId/$username/$password(.$format)?"(controller:'patient', action:'getOpenMRSSession')
        get "/patient/openmrsSearch/$interoperabilityId/$nid/$username/$password(.$format)?"(controller:'patient', action:'getOpenMRSPatient')
        get "/patient/clinic/$clinicId(.$format)?"(controller:'patient', action:'getByClinicId')
        get "/stockEntrance/clinic/$clinicId(.$format)?"(controller:'stockEntrance', action:'getByClinicId')
        get "/inventory/clinic/$clinicId(.$format)?"(controller:'inventory', action:'getByClinicId')
        get "/patientServiceIdentifier/clinic/$clinicId(.$format)?"(controller:'patientServiceIdentifier', action:'getByClinicId')
        get "/patientServiceIdentifier/patient/$patientId(.$format)?"(controller:'patientServiceIdentifier', action:'getByPatientId')
        get "/episode/clinic/$clinicId(.$format)?"(controller:'episode', action:'getByClinicId')
        get "/episode/identifier/$identifierId(.$format)?"(controller:'episode', action:'getByIdentifierId')
        get "/doctor/clinic/$clinicId(.$format)?"(controller:'doctor', action:'getByClinicId')
        get "/patientVisit/clinic/$clinicId(.$format)?"(controller:'patientVisit', action:'getAllByClinicId')
        get "/patientVisitDetails/clinic/$clinicId(.$format)?"(controller:'patientVisitDetails', action:'getAllByClinicId')
        get "/patientVisitDetails/episode/$episodeId(.$format)?"(controller:'patientVisitDetails', action:'getAllByEpisodeId')
        get "/prescription/clinic/$clinicId(.$format)?"(controller:'prescription', action:'getAllByClinicId')
        get "/pregnancyScreening/patientVisit/$patientVisitId(.$format)?"(controller:'pregnancyScreening', action:'getAllByPatientVisit')
        get "/prescription/visits/$pvdsId(.$format)?"(controller:'prescription', action:'getByVisitId')
        get "/pack/clinic/$clinicId(.$format)?"(controller:'pack', action:'getAllByClinicId')
        get "/pack/patientVisitDetails/$patientVisitDetailsId(.$format)?"(controller:'pack', action:'getAllByPatientVisitDetailsId')
        get "/packagedDrug/pack/$packId(.$format)?"(controller:'packagedDrug', action:'getAllByPackId')
        get "/prescribedDrug/prescription/$prescriptionId(.$format)?"(controller:'prescribedDrug', action:'getAllByPrescriptionId')
        get "/prescriptionDetail/prescription/$prescriptionId(.$format)?"(controller:'prescriptionDetail', action:'getAllByPrescriptionId')
        get "/patientVisit/patient/$patientId(.$format)?"(controller:'patientVisit', action:'getByPatientId')
        get "/group/clinic/$clinicId(.$format)?"(controller:'group', action:'getByClinicId')
        get "/patientVisit/lastofPatient/$patientId(.$format)?"(controller:'patientVisit', action:'getLastVisitOfPatient')
        get "/pack/prescription/$prescriptionId(.$format)?"(controller:'pack', action:'getAllByPrescriptionId')
        get "/patient/reportActiveByServiceCode/"(controller:'patient', action:'getReportActiveByServiceCode')
        post "/$controller/initReportProcess(.$format)?"(action:'initReportProcess')
        get "/$controller/printReport/$reportId(.$format)?"(action:'getProcessedData')
        get "/$controller/printReport/$reportId/$fileType(.$format)?"(action:'printReport')
        get "/$controller/getProcessingStatus/$reportId(.$format)?"(action:'getProcessingStatus')
        get "/$controller/printReport/$reportId(.$format)?"(action:'printReport')
        delete "/$controller/delete/$reportId(.$format)?"(action:'deleteByReportId')

        get "/$controller/getPatientNid/$nid/$destinationClinicUuid(.$format)?"(controller:'patientTransReference',action:'getDetailsByNid')
        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
