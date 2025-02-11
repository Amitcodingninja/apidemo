package com.apidemo.exceptions;

import com.apidemo.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
//@ControllerAdvice :-pura project me exceptions handle karega.
@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFound(ResourceNotFound ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(ex.getMessage(), new Date(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(ex.getMessage(), new Date(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

//@ExceptionHandler(ResourceNotFound.class):
//Jab ResourceNotFound exception aayegi, toh yeh catch karega aur ek proper JSON
// response return karega.
//

// ➡️ 💡 Complete Exception Flow Summary
//Client API call karega (Postman ya frontend se).
//Controller request receive karega aur Service layer ko call karega.
//Service database me id dhundhega:
//id mil gayi 🟢 → Data return karega.
//id nahi mili 🔴 → ResourceNotFound Exception throw karega.
//ExceptionHandling class exception ko catch karega aur ek proper JSON response dega.
//Agar koi aur unknown exception hoti hai toh handleAllException method usko handle karega.
