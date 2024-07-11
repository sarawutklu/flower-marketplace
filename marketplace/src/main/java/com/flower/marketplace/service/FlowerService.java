package com.flower.marketplace.service;

import com.flower.marketplace.entity.Flower;
import com.flower.marketplace.entity.FlowerImage;
import com.flower.marketplace.model.FlowerCreate;
import com.flower.marketplace.model.FlowerDTO;
import com.flower.marketplace.model.FlowerImageDTO;
import com.flower.marketplace.model.FlowerUpdateDTO;
import com.flower.marketplace.repository.FlowerImageRepository;
import com.flower.marketplace.repository.FlowerRepository;
import com.flower.marketplace.util.EncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlowerService {

    private static final Logger logger = LoggerFactory.getLogger(FlowerService.class);

    private final EncryptUtil encryptUtil;
    private final FlowerRepository flowerRepository;
    private final FlowerImageRepository flowerImageRepository;

    @Autowired
    public FlowerService(EncryptUtil encryptUtil, FlowerRepository flowerRepository, FlowerImageRepository flowerImageRepository) {
        this.encryptUtil = encryptUtil;
        this.flowerRepository = flowerRepository;
        this.flowerImageRepository = flowerImageRepository;
    }

    public List<FlowerDTO> getFlowersByCreatedBy(String createdBy) {
        try {
            logger.info("Fetching all flowers created by: {}", createdBy);
            List<Flower> flowers = flowerRepository.findByCreatedBy(createdBy);
            return flowers.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error occurred while fetching flowers created by: {}", createdBy, e);
            throw new RuntimeException("Failed to fetch flowers: " + e.getMessage());
        }
    }
    public FlowerDTO saveFlower(FlowerCreate request) {
        try {
            logger.info("Saving new flower: {}", request.getName());
            Flower flower = new Flower();
            flower.setName(request.getName());
            flower.setDescription(request.getDescription());
            flower.setPrice(request.getPrice());
            flower.setCreatedDate(LocalDateTime.now());
            flower.setCreatedBy("System");
            flower.setModifiedDate(LocalDateTime.now());
            flower.setModifiedBy("System");
            Flower savedFlower = flowerRepository.save(flower);
            logger.info("Saved flower with ID: {}", savedFlower.getId());
            return convertToDTO(savedFlower);
        } catch (Exception e) {
            logger.error("Error occurred while saving flower: {}", e.getMessage());
            throw new RuntimeException("Failed to save flower: " + e.getMessage());
        }
    }


    @Transactional
    public FlowerDTO updateFlower(Long id, FlowerUpdateDTO flowerUpdateDTO) {
        Flower flower = flowerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flower not found with id: " + id));
        if (!flower.getCreatedBy().equals("System")) {
            throw new RuntimeException("You are not authorized to delete this flower");
        }
        flower.setName(flowerUpdateDTO.getName());
        flower.setDescription(flowerUpdateDTO.getDescription());
        flower.setPrice(flowerUpdateDTO.getPrice());
        flower.setModifiedBy("System");
        flower.setModifiedDate(LocalDateTime.now());
        flower = flowerRepository.save(flower);
        return convertToDTO(flower);
    }

    @Transactional
    public void deleteFlower(Long id) {
        try {
            Flower flower = flowerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Flower not found with id: " + id));
            if (!flower.getCreatedBy().equals("System")) {
                throw new RuntimeException("You are not authorized to delete this flower");
            }
            flowerRepository.delete(flower);
        } catch (Exception e) {
            logger.error("Error occurred while deleting flower with ID: {}", id, e);
            throw new RuntimeException("Failed to delete flower: " + e.getMessage());
        }
    }

    @Transactional
    public FlowerImageDTO saveFlowerImage(Long flowerId, byte[] imageData) {
        try {
            logger.info("Saving new flower image for flower ID: {}", flowerId);
            FlowerImage flowerImage = new FlowerImage();
            flowerImage.setFlowerId(flowerId);
            flowerImage.setEncryptedImage(encryptUtil.encrypt(imageData));
            flowerImage.setCreatedDate(LocalDateTime.now());
            flowerImage.setCreatedBy("System");
            flowerImage.setModifiedDate(LocalDateTime.now());
            flowerImage.setModifiedBy("System");
            FlowerImage savedImage = flowerImageRepository.save(flowerImage);
            logger.info("Saved flower image with ID: {}", savedImage.getId());
            return convertToImageDTO(savedImage);
        } catch (Exception e) {
            logger.error("Error occurred while saving flower image: {}", e.getMessage());
            throw new RuntimeException("Failed to save flower image: " + e.getMessage());
        }
    }

    public byte[] getFlowerImageById(Long id) {
        try {
            logger.info("Fetching flower image with ID: {}", id);
            FlowerImage flowerImage = flowerImageRepository.findById(id).orElse(null);
            assert flowerImage != null;
            return encryptUtil.decrypt(flowerImage.getEncryptedImage());
        } catch (Exception e) {
            logger.error("Error occurred while fetching flower image with ID: {}", id, e);
            throw new RuntimeException("Failed to fetch flower image: " + e.getMessage());
        }
    }

    private FlowerDTO convertToDTO(Flower flower) {
        FlowerDTO flowerDTO = new FlowerDTO();
        flowerDTO.setId(flower.getId());
        flowerDTO.setName(flower.getName());
        flowerDTO.setDescription(flower.getDescription());
        flowerDTO.setPrice(flower.getPrice());
        flowerDTO.setImages(flower.getImages().stream().map(this::convertToImageDTO).collect(Collectors.toList()));
        return flowerDTO;
    }

    private FlowerImageDTO convertToImageDTO(FlowerImage flowerImage) {
        FlowerImageDTO flowerImageDTO = new FlowerImageDTO();
        flowerImageDTO.setId(flowerImage.getId());
        return flowerImageDTO;
    }
}