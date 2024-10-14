package com.car2.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CarCreate {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Positive
    private double price;

    private String make;
    private String model;
    private int year;
    private String color;

}
