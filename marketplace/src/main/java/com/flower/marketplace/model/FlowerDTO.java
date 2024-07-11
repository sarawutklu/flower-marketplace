package com.flower.marketplace.model;

import lombok.Data;

import java.util.List;

@Data
public class FlowerDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private List<FlowerImageDTO> images;
}
