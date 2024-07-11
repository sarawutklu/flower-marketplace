package com.flower.marketplace.model;

import lombok.Data;

@Data
public class FlowerUpdateDTO {
    private String name;
    private String description;
    private double price;
}
