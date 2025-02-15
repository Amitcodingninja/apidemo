package com.apidemo.service;

import com.apidemo.entity.Registration;
import com.apidemo.exceptions.ResourceNotFound;
import com.apidemo.payload.RegistrationDto;
import com.apidemo.repository.RegistrationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegistrationService {
    private RegistrationRepository registrationRepository;
    // Library for object mapping
    private ModelMapper modelMapper;

    public RegistrationService(RegistrationRepository registrationRepository, ModelMapper modelMapper) {

        this.registrationRepository = registrationRepository;
        this.modelMapper = modelMapper;

    }

    public RegistrationDto createRegistration(RegistrationDto registrationDto) {
        Registration registration = convertToEntity(registrationDto);
        Registration savedRegistration = registrationRepository.save(registration);
        return convertToDto(savedRegistration);
    }

    // To convert dto to entity : take the dto content copy that to entity and return back
    private Registration convertToEntity(RegistrationDto registrationDto) {
//        Registration registration = new Registration();
//        registration.setName(registrationDto.getName());
//        registration.setEmailId(registrationDto.getEmailId());
//        registration.setMobile(registrationDto.getMobile());
//        return registration;


        Registration reg = modelMapper.map(registrationDto, Registration.class);
        return reg;
    }

    //    This method converts a `Registration` entity into a `RegistrationDto` by copying its fields.
/*
1)Encapsulation – Hides unnecessary entity details from the client.
2)Security – Prevents exposing sensitive database fields directly.
3)Performance – Reduces data transfer size by sending only required fields.
4)Flexibility – Allows different representations of data for different use cases.
5) Decoupling – Separates database models from API responses, making code more maintainable.
 */
    private RegistrationDto convertToDto(Registration registration) {
//        RegistrationDto registrationDto = new RegistrationDto();
//        registrationDto.setId(registration.getId());
//        registrationDto.setName(registration.getName());
//        registrationDto.setEmailId(registration.getEmailId());
//        registrationDto.setMobile(registration.getMobile());
//        return registrationDto;

        RegistrationDto dto = modelMapper.map(registration, RegistrationDto.class);
        return dto;


    }

    public void deleteRegistration(long id) {
        registrationRepository.deleteById(id);
    }

    //Logic for update
    public void updateRegistration(long id, RegistrationDto registrationDto) {
        Optional<Registration> opReg = registrationRepository.findById(id);
        Registration reg = opReg.get(); // it will convert optional object to entity object
        reg.setName(registrationDto.getName());
        reg.setEmailId(registrationDto.getEmailId());
        reg.setMobile(registrationDto.getMobile());
        registrationRepository.save(reg);


    }

    public List<RegistrationDto> getAllRegistrations(int pageNo, int pageSize, String sortBy, String sortDir) {
        // Logic for categories in asc or desc order
        Sort sort = sortDir.equals("asc") ? Sort.by(Sort.Order.asc(sortBy)) : Sort.by(Sort.Order.desc(sortBy));

        Pageable page = PageRequest.of(pageNo, pageSize, sort);
        Page<Registration> registrationPage = registrationRepository.findAll(page);

//        whether you are in the last page of table or first page how many record on per page ,  what the page no
//
//        System.out.println(page.getPageNumber());
//        System.out.println(page.getPageSize());

        return registrationPage.getContent().stream()
                .map(registration -> modelMapper.map(registration, RegistrationDto.class))
                .collect(Collectors.toList());

//        Old way
//        List<Registration> registrations = registrationRepository.findAll();
//        return registrations;
    }

    public Registration getRegistrationById(long id) {
        return registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Registration with ID " + id + " not found"));
    }


}
