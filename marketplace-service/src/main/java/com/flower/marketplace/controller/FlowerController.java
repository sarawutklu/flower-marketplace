package com.flower.marketplace.controller;


import com.flower.marketplace.model.*;
import com.flower.marketplace.service.FlowerService;
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
@RequestMapping("/flowers")
public class FlowerController {

    private static final Logger logger = LoggerFactory.getLogger(FlowerController.class);
    private final FlowerService flowerService;

    @Autowired
    public FlowerController(FlowerService flowerService) {
        this.flowerService = flowerService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<FlowerDTO>>> getMyFlowers() {
        try {
            logger.info("Received request to get all flowers");
            List<FlowerDTO> flowers = flowerService.getMyFlowers();
            ApiResponse<List<FlowerDTO>> response = new ApiResponse<>(
                    "Flowers retrieved successfully",
                    flowers,
                    null,
                    "0000"
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to get flowers", e);
            ApiResponse<List<FlowerDTO>> response = new ApiResponse<>(
                    "Failed to retrieve flowers",
                    null,
                    e.getMessage(),
                    "0005"
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<ApiResponse<FlowerDTO>> createFlower(@Valid @RequestBody FlowerCreate request) {
        try {
            logger.info("Received request to create flower: {}", request.getName());
            FlowerDTO flower = flowerService.saveFlower(request);
            logger.info("Successfully created flower with ID: {}", flower.getId());
            ApiResponse<FlowerDTO> response = new ApiResponse<>(
                    "Flower created successfully",
                    flower,
                    null,
                    "0000"
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to create flower: {}", e.getMessage());
            ApiResponse<FlowerDTO> response = new ApiResponse<>(
                    "Failed to create flower",
                    null,
                    e.getMessage(),
                    "0005"
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FlowerDTO>> updateFlower(@PathVariable Long id,
                                                               @RequestBody FlowerUpdateDTO flowerUpdateDTO) {
        try {
            logger.info("Received request to update flower with ID: {}", id);
            FlowerDTO updatedFlower = flowerService.updateFlower(id, flowerUpdateDTO);
            ApiResponse<FlowerDTO> response = new ApiResponse<>(
                    "Flower updated successfully",
                    updatedFlower,
                    null,
                    "0000"
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to update flower with ID: {}", id, e);
            ApiResponse<FlowerDTO> response = new ApiResponse<>(
                    "Failed to update flower",
                    null,
                    e.getMessage(),
                    "0005"
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFlower(@PathVariable Long id) {
        try {
            logger.info("Received request to delete flower with ID: {}", id);
            flowerService.deleteFlower(id);
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
                    "Failed to delete flower",
                    null,
                    e.getMessage(),
                    "0005"
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{flowerId}/images")
    public ResponseEntity<ApiResponse<Void>> uploadFlowerImages(@PathVariable Long flowerId,
                                                                  @RequestParam("images") List<MultipartFile> images) {
        try {
            logger.info("Received request to upload {} images for flower ID: {}", images.size(), flowerId);
            List<FlowerImageDTO> uploadedImages = new ArrayList<>();
            for (MultipartFile image : images) {
                byte[] imageData = image.getBytes();
                FlowerImageDTO flowerImage = flowerService.saveFlowerImage(flowerId, imageData);
                uploadedImages.add(flowerImage);
            }
            logger.info("Successfully uploaded {} images for flower ID: {}", uploadedImages.size(), flowerId);
            ApiResponse<Void> response = new ApiResponse<>(
                    "Images uploaded successfully",
                    null,
                    null,
                    "0000"
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to upload images for flower ID: {}", flowerId, e);
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
    public ResponseEntity<byte[]> getFlowerImage(@PathVariable Long id) {
        try {
            logger.info("Received request to read image with ID: {}", id);
            byte[] flowerImage = flowerService.getFlowerImageById(id);
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
