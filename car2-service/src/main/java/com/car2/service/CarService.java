package com.car2.service;

import com.car2.entity.Car;
import com.car2.model.CarCreate;
import com.car2.repository.CarRepository;
import com.car2.util.EncryptUtil;
import com.car2.entity.CarImage;
import com.car2.model.CarDTO;
import com.car2.model.CarImageDTO;
import com.car2.model.CarUpdateDTO;
import com.car2.repository.CarImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    private final EncryptUtil encryptUtil;
    private final CarRepository carRepository;
    private final CarImageRepository carImageRepository;

    @Autowired
    public CarService(EncryptUtil encryptUtil, CarRepository carRepository, CarImageRepository carImageRepository) {
        this.encryptUtil = encryptUtil;
        this.carRepository = carRepository;
        this.carImageRepository = carImageRepository;
    }
     private String currentUserNanme() {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String username = null;
         if (authentication instanceof JwtAuthenticationToken) {
             JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
             Jwt jwt = jwtToken.getToken();
             // Retrieve preferred_username claim
             username = jwt.getClaim("preferred_username");
         }

         return username;
     }
    public List<CarDTO> getMyFlowers() {
        try {
            String userName = currentUserNanme();
            logger.info("Fetching all cars created by: {}", userName);
            List<Car> flowers = carRepository.findByCreatedBy(userName);
            return flowers.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error occurred while fetching cars", e);
            throw new RuntimeException("Failed to fetch cars: " + e.getMessage());
        }
    }
    public CarDTO saveCar(CarCreate request) {
        try {
            logger.info("Saving new car: {}", request.getName());
            Car car = new Car();
            car.setName(request.getName());
            car.setDescription(request.getDescription());
            car.setPrice(request.getPrice());
            car.setMake(request.getMake());
            car.setModel(request.getModel());
            car.setYear(request.getYear());
            car.setColor(request.getColor());
            car.setCreatedDate(LocalDateTime.now());
            car.setCreatedBy(currentUserNanme());
            car.setModifiedDate(LocalDateTime.now());
            car.setModifiedBy(currentUserNanme());
            Car savedFlower = carRepository.save(car);
            logger.info("Saved car with ID: {}", savedFlower.getId());
            return convertToDTO(savedFlower);
        } catch (Exception e) {
            logger.error("Error occurred while saving car: {}", e.getMessage());
            throw new RuntimeException("Failed to save car: " + e.getMessage());
        }
    }


    @Transactional
    public CarDTO updateCar(Long id, CarUpdateDTO carUpdateDTO) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        if (!car.getCreatedBy().equals(currentUserNanme())) {
            throw new RuntimeException("You are not authorized to delete this car");
        }
        car.setName(carUpdateDTO.getName());
        car.setDescription(carUpdateDTO.getDescription());
        car.setPrice(carUpdateDTO.getPrice());
        car.setMake(carUpdateDTO.getMake());
        car.setModel(carUpdateDTO.getModel());
        car.setYear(carUpdateDTO.getYear());
        car.setColor(carUpdateDTO.getColor());
        car.setModifiedBy(currentUserNanme());
        car.setModifiedDate(LocalDateTime.now());
        car = carRepository.save(car);
        return convertToDTO(car);
    }

    @Transactional
    public void deleteFlower(Long id) {
        try {
            Car car = carRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Flower not found with id: " + id));
            if (!car.getCreatedBy().equals(currentUserNanme())) {
                throw new RuntimeException("You are not authorized to delete this car");
            }
            carRepository.delete(car);
        } catch (Exception e) {
            logger.error("Error occurred while deleting car with ID: {}", id, e);
            throw new RuntimeException("Failed to delete car: " + e.getMessage());
        }
    }

    @Transactional
    public CarImageDTO saveCarImage(Long carId, byte[] imageData) {
        try {
            // Get current authenticated user


            logger.info("Saving new car image for car ID: {}", carId);
            CarImage carImage = new CarImage();
            carImage.setCarId(carId);
            carImage.setEncryptedImage(encryptUtil.encrypt(imageData));
            carImage.setCreatedDate(LocalDateTime.now());
            carImage.setCreatedBy("System");
            carImage.setModifiedDate(LocalDateTime.now());
            carImage.setModifiedBy("System");
            CarImage savedImage = carImageRepository.save(carImage);
            logger.info("Saved car image with ID: {}", savedImage.getId());
            return convertToImageDTO(savedImage);
        } catch (Exception e) {
            logger.error("Error occurred while saving car image: {}", e.getMessage());
            throw new RuntimeException("Failed to save car image: " + e.getMessage());
        }
    }

    public byte[] getCarImageById(Long id) {
        try {
            logger.info("Fetching car image with ID: {}", id);
            CarImage carImage = carImageRepository.findById(id).orElse(null);
            assert carImage != null;
            return encryptUtil.decrypt(carImage.getEncryptedImage());
        } catch (Exception e) {
            logger.error("Error occurred while fetching car image with ID: {}", id, e);
            throw new RuntimeException("Failed to fetch car image: " + e.getMessage());
        }
    }

    private CarDTO convertToDTO(Car car) {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(car.getId());
        carDTO.setName(car.getName());
        carDTO.setDescription(car.getDescription());
        carDTO.setPrice(car.getPrice());
        carDTO.setMake(car.getMake());
        carDTO.setModel(car.getModel());
        carDTO.setYear(car.getYear());
        carDTO.setColor(car.getColor());
        carDTO.setImages(car.getImages().stream().map(this::convertToImageDTO).collect(Collectors.toList()));
        return carDTO;
    }

    private CarImageDTO convertToImageDTO(CarImage carImage) {
        CarImageDTO carImageDTO = new CarImageDTO();
        carImageDTO.setId(carImage.getId());
        return carImageDTO;
    }
}