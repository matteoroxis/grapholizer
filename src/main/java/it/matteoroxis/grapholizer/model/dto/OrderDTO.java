package it.matteoroxis.grapholizer.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class OrderDTO {

    private String id;

    @NotNull(message = "Importo totale è obbligatorio")
    @Positive(message = "L'importo totale deve essere positivo")
    private Double totalAmount;

    @NotEmpty(message = "La lista di prodotti non può essere vuota")
    @Valid
    private List<ProductDTO> products;

    public OrderDTO() {
    }

    public OrderDTO(String id, Double totalAmount, List<ProductDTO> products) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}



