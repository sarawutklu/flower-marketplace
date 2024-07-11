package com.flower.marketplace.repository;

import com.flower.marketplace.entity.FlowerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowerImageRepository extends JpaRepository<FlowerImage, Long> {
    List<FlowerImage> findByFlowerId(Long flowerId);
}