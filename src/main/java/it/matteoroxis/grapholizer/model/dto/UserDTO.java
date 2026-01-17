package it.matteoroxis.grapholizer.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class UserDTO {

    private String id;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email not valid")
    private String email;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Valid
    private List<OrderDTO> orders;

    public UserDTO() {
    }

    public UserDTO(String id, String email, String name, List<OrderDTO> orders) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.orders = orders;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setName(String name) {
        this.name = name;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }
}

