package it.matteoroxis.grapholizer.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductDTO {

    private String id;

    @NotBlank(message = "Nome è obbligatorio")
    private String name;

    @NotNull(message = "Prezzo è obbligatorio")
    @Positive(message = "Il prezzo deve essere positivo")
    private Double price;

    public ProductDTO() {
    }

    public ProductDTO(String id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

