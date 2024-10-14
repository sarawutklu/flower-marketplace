package com.car2.model;

import lombok.Data;

@Data
public class CarUpdateDTO {
    private String name;
    private String description;
    private double price;
}
