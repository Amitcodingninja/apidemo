package com.apidemo.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RegistrationDto {

    private long id;
    @NotNull(message = "Cannot be null")
    @Size(min = 2 , max = 10,message = "Should be between 2 to 10 character")
    private  String name;

    @Email(message = "Invalid Email address")
    private  String emailId;
    @NotNull(message = "Cannot be null")
    @Size(min = 10 , max = 10, message = "Should be exactly 10 digit")
    private String mobile;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }



}
