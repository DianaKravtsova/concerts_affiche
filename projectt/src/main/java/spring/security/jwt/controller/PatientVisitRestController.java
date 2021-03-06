package spring.security.jwt.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.security.jwt.bean.PatientVisit;
import spring.security.jwt.controller.dto.IdRequest;
import spring.security.jwt.controller.dto.PatientVisitRequest;
import spring.security.jwt.exception.ControllerException;
import spring.security.jwt.exception.RepositoryException;
import spring.security.jwt.exception.ServiceException;
import spring.security.jwt.repository.PatientVisitRepository;
import spring.security.jwt.service.impl.PatientVisitServiceImpl;

import java.util.List;

@RestController
public class PatientVisitRestController {
    @Autowired
    private PatientVisitServiceImpl patientVisitService;

    @Autowired
    private PatientVisitRepository patientVisitRepository;

    private static final Logger logger = Logger.getLogger(PatientVisitRestController.class);


    @PostMapping("patient/addPatientVisit")
    public ResponseEntity<?> add(@RequestBody PatientVisitRequest patientVisitRequest) throws ControllerException {
        try {
            PatientVisit patientVisit = new PatientVisit(
                    patientVisitRequest.getDate(),
                    patientVisitRequest.getPatientCard()
            );
            patientVisitService.create(patientVisit);
            return new ResponseEntity<>(patientVisit,HttpStatus.OK);
        } catch (ServiceException e) {
            logger.error("error add patient visit");
            throw new ControllerException("add", e);
        }
    }

    @DeleteMapping("/patient/deleteAllPatientVisit")
    public ResponseEntity<?> deleteAll(@RequestBody IdRequest idRequest) throws ControllerException {
        try {
            patientVisitService.deleteAllByPatientCardId(idRequest.getId());
            List<PatientVisit> list = patientVisitRepository.getAllByPatientCardId(idRequest.getId());
            return new ResponseEntity<>(list,HttpStatus.OK);
        } catch (ServiceException | RepositoryException e) {
            logger.error("error delete patient visit");

            throw new ControllerException("deleteAll", e);
        }
    }

    @GetMapping("/patient/getAllPatientVisit/{id}")
    public ResponseEntity<?> getAll(@PathVariable(name = "id") Long id) throws ControllerException {
        try {
            return new ResponseEntity<>(patientVisitService.getAllByPatientCardId(id), HttpStatus.OK);
        } catch (ServiceException e) {
            logger.error("error get all patient visit");

            throw new ControllerException("getAll", e);
        }
    }
}
