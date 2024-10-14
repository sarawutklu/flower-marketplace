package com.car2.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CarDTO {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private double price;

    private String make;
    private String model;
    private int year;
    private String color;

    private List<CarImageDTO> images;
}
