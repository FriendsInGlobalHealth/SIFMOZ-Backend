package mz.org.fgh.sifmoz.backend.screening

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.sifmoz.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class TBScreeningController extends RestfulController{

    TBScreeningService TBScreeningService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    TBScreeningController() {
        super(TBScreening)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(TBScreeningService.list(params)) as JSON
    }

    def show(Long id) {
        render JSONSerializer.setJsonObjectResponse(TBScreeningService.get(id)) as JSON
    }

    @Transactional
    def save(TBScreening TBScreening) {
        if (TBScreening == null) {
            render status: NOT_FOUND
            return
        }
        if (TBScreening.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond TBScreening.errors
            return
        }

        try {
            TBScreeningService.save(TBScreening)
        } catch (ValidationException e) {
            respond TBScreening.errors
            return
        }

        respond TBScreening, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TBScreening TBScreening) {
        if (TBScreening == null) {
            render status: NOT_FOUND
            return
        }
        if (TBScreening.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond TBScreening.errors
            return
        }

        try {
            TBScreeningService.save(TBScreening)
        } catch (ValidationException e) {
            respond TBScreening.errors
            return
        }

        respond TBScreening, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || TBScreeningService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
