package spring.security.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.security.jwt.bean.CardDocument;
import spring.security.jwt.controller.dto.CardDocumentRequest;
import spring.security.jwt.controller.dto.IdRequest;
import spring.security.jwt.exception.ControllerException;
import spring.security.jwt.exception.ServiceException;
import spring.security.jwt.repository.CardDocumentRepository;
import spring.security.jwt.service.impl.CardDocumentServiceImpl;
import org.apache.log4j.Logger;


@RestController
public class CardDocumentRestController {
    @Autowired
    private CardDocumentServiceImpl cardDocumentService;

    @Autowired
    private CardDocumentRepository cardDocumentRepository;
    private static final Logger logger = Logger.getLogger(CardDocumentRestController.class);


    @PostMapping("/patient/createCardDocument")
    public ResponseEntity<?> create(@RequestBody CardDocumentRequest cardDocumentRequest) throws ControllerException {
        try {
            CardDocument cardDocument = new CardDocument(
                    cardDocumentRequest.getCard(),
                    cardDocumentRequest.getDoctorDocument()
            );
            cardDocumentService.create(cardDocument);
            return new ResponseEntity<>(cardDocument,HttpStatus.OK);
        } catch (ServiceException e) {
            logger.error("error create card document");

            throw new ControllerException("create", e);
        }
    }

    @PostMapping("/patient/isCardDocumentExist")
    public ResponseEntity<?> isExist(@RequestBody IdRequest idRequest) throws ControllerException {
        try {
            if (cardDocumentService.existsByCardId(idRequest.getId())) {
                return new ResponseEntity<>(cardDocumentRepository.getByCardId(idRequest.getId()),HttpStatus.FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (ServiceException e) {
            logger.error("error is exist card document");

            throw new ControllerException("isExist", e);
        }

    }

    @DeleteMapping("/patient/deleteCardDocumentByCardId")
    public ResponseEntity<?> deleteByCardIdPatient(@RequestBody IdRequest idRequest) throws ControllerException {
        try {
            CardDocument deleted = cardDocumentRepository.getByCardId(idRequest.getId());
            cardDocumentService.deleteByCardId(idRequest.getId());
            return new ResponseEntity<>(deleted,HttpStatus.OK);
        } catch (ServiceException e) {
            logger.error("error delete  card document");

            throw new ControllerException("deleteByCardIdPatient", e);
        }
    }

    @DeleteMapping("/doctor/deleteCardDocumentByCardId")
    public ResponseEntity<?> deleteByCardIdDoctor(@RequestBody IdRequest idRequest) throws ControllerException {
        try {
            CardDocument deleted = cardDocumentRepository.getByCardId(idRequest.getId());
            cardDocumentService.deleteByCardId(idRequest.getId());
            return new ResponseEntity<>(deleted,HttpStatus.OK);
        } catch (ServiceException e) {
            logger.error("error delete  card document");

            throw new ControllerException("deleteByCardIdDoctor", e);
        }

    }
}
