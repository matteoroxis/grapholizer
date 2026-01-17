package it.matteoroxis.grapholizer.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateUser {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email not valid")
    private String email;

    @NotBlank(message = "Name is mandatory")
    private String name;

    public CreateUser() {
    }

    public CreateUser(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

}
