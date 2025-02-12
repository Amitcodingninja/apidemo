package com.apidemo.controller;

import com.apidemo.entity.Registration;
import com.apidemo.payload.RegistrationDto;
import com.apidemo.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/register")
public class RegistrationController {
    @Autowired
    private final RegistrationService registrationService;

    // Constructor-based Dependency Injection
    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<RegistrationDto> createRegistration(@RequestBody RegistrationDto registrationDto) {
        // Call the service method to save the registration and get the DTO back
        RegistrationDto registration = registrationService.createRegistration(registrationDto);

        // Return the success message with the ID from the DTO
        return ResponseEntity.status(HttpStatus.CREATED).header("Custom-Header", "Value").body(registration);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteRegistration(@RequestParam long id) {
        registrationService.deleteRegistration(id);
//        return "Registration deleted successfully with ID: "+id; // 1st way
        return new ResponseEntity<>("Registration deleted successfully: " + id, HttpStatus.OK);

    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateRegistration(@PathVariable long id, @RequestBody RegistrationDto registrationDto) {
        registrationService.updateRegistration(id, registrationDto);
        return ResponseEntity.ok("Updated Successfully! ID: " + id);
    }


    // http://localhost:8080/api/v1/register?pageNo=1&pageSize=3
    // http://localhost:8080/api/v1/register?pageNo=1&pageSize=3&sortBy=name
    @GetMapping
    public ResponseEntity<List<RegistrationDto>> getAllRegistrations(
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "3") int pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortByDir,
            String sortDir) {
        // OLD one
//        List<Registration> registrations =registrationService.getAllRegistrations();
//        return registrations;

        List<RegistrationDto> registrations = registrationService.getAllRegistrations(pageNo, pageSize, sortBy,sortDir);
        return ResponseEntity.ok(registrations);
    }


    @GetMapping("/byId")
    public ResponseEntity<Registration> getRegistration(@RequestParam long id) {
        Registration reg = registrationService.getRegistrationById(id);
        return new ResponseEntity<>(reg, HttpStatus.OK);
    }


}





/*

  //http://localhost:8080/api/v1/register

    // @RequestBody it will convert json data to java object
    // Here controller layer replace with restController and html page replace with postman

    //Dto to entity conversion means : Converting a DTO (Data Transfer Object) to an Entity
    // in a Spring Boot project means mapping the data from a lightweight object
    // (used for transferring data) to a database entity object that is managed by
    // JPA/Hibernate for persistence.
 // Code Flow
//        Here's a one-line code flow summary:
//        JSON → RestController (deserialize to DTO) → Service (DTO to Entity)
//       →  Database (save Entity) → Service (Entity to DTO)
//    → RestController (return success message)


The `@RequestParam` annotation in Spring binds a query parameter to a method parameter, with options for default
values and required status (e.g., `@RequestParam(value = "name", required = false, defaultValue = "Guest")`).
 */

// Response Entity returning
//it can send the response back as json
//i can send some message in header as response
//i can return back the status