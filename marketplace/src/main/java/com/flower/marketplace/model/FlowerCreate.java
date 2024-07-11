package com.flower.marketplace.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FlowerCreate {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Positive
    private double price;

}
