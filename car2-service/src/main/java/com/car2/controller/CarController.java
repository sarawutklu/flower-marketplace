package com.car2.controller;


import com.car2.model.*;
import com.car2.service.CarService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<CarDTO>>> getMyCars() {
        try {
            logger.info("Received request to get all cars");
            List<CarDTO> flowers = carService.getMyFlowers();
            ApiResponse<List<CarDTO>> response = new ApiResponse<>(
                    "Flowers retrieved successfully",
                    flowers,
                    null,
                    "0000"
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to get cars", e);
            ApiResponse<List<CarDTO>> response = new ApiResponse<>(
                    "Failed to retrieve cars",
                    null,
                    e.getMessage(),
                    "0005"
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<ApiResponse<CarDTO>> createCar(@Valid @RequestBody CarCreate request) {
        try {
            logger.info("Received request to create car: {}", request.getName());
            CarDTO flower = carService.saveCar(request);
            logger.info("Successfully created car with ID: {}", flower.getId());
            ApiResponse<CarDTO> response = new ApiResponse<>(
                    "Car created successfully",
                    flower,
                    null,
                    "0000"
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to create car: {}", e.getMessage());
            ApiResponse<CarDTO> response = new ApiResponse<>(
                    "Failed to create car",
                    null,
                    e.getMessage(),
                    "0005"
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CarDTO>> updateCar(@PathVariable Long id,
                                                            @RequestBody CarUpdateDTO carUpdateDTO) {
        try {
            logger.info("Received request to update car with ID: {}", id);
            CarDTO updatedFlower = carService.updateCar(id, carUpdateDTO);
            ApiResponse<CarDTO> response = new ApiResponse<>(
                    "Flower updated successfully",
                    updatedFlower,
                    null,
                    "0000"
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to update car with ID: {}", id, e);
            ApiResponse<CarDTO> response = new ApiResponse<>(
                    "Failed to update car",
                    null,
                    e.getMessage(),
                    "0005"
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCar(@PathVariable Long id) {
        try {
            logger.info("Received request to delete car with ID: {}", id);
            carService.deleteFlower(id);
            ApiResponse<Void> response = new ApiResponse<>(
                    "Flower deleted successfully",
                    null,
                    null,
                    "0000"
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to delete flower with ID: {}", id, e);
            ApiResponse<Void> response = new ApiResponse<>(
                    "Failed to delete car",
                    null,
                    e.getMessage(),
                    "0005"
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{flowerId}/images")
    public ResponseEntity<ApiResponse<Void>> uploadCarImages(@PathVariable Long carId,
                                                                  @RequestParam("images") List<MultipartFile> images) {
        try {
            logger.info("Received request to upload {} images for flower ID: {}", images.size(), carId);
            List<CarImageDTO> uploadedImages = new ArrayList<>();
            for (MultipartFile image : images) {
                byte[] imageData = image.getBytes();
                CarImageDTO flowerImage = carService.saveCarImage(carId, imageData);
                uploadedImages.add(flowerImage);
            }
            logger.info("Successfully uploaded {} images for car ID: {}", uploadedImages.size(), carId);
            ApiResponse<Void> response = new ApiResponse<>(
                    "Images uploaded successfully",
                    null,
                    null,
                    "0000"
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to upload images for car ID: {}", carId, e);
            ApiResponse<Void> response = new ApiResponse<>(
                    "Failed to upload images",
                    null,
                    e.getMessage(),
                    "0005"
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getCarImage(@PathVariable Long id) {
        try {
            logger.info("Received request to read image with ID: {}", id);
            byte[] flowerImage = carService.getCarImageById(id);
            if (flowerImage == null) {
                logger.warn("Image with ID: {} not found", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            logger.info("Successfully retrieved image with ID: {}", id);
            return ResponseEntity
                    .ok()
                    .header("Content-Type", "image/jpeg")
                    .body(flowerImage);
        } catch (Exception e) {
            logger.error("Failed to read image with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
