package com.flower.marketplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "flower_images")
@Getter
@Setter
public class FlowerImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "encrypted_image", nullable = false, length = Integer.MAX_VALUE)
    private String encryptedImage;
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
    @Column(name = "created_by", nullable = false)
    private String createdBy;
    @Column(name = "modified_date", nullable = false)
    private LocalDateTime modifiedDate;
    @Column(name = "modified_by", nullable = false)
    private String modifiedBy;
    @Column(name = "flower_id", nullable = false)
    private Long flowerId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "flower_id", nullable = false, insertable=false, updatable=false)
    private Flower flower;
}