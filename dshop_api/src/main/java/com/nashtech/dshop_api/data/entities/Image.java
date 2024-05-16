package com.nashtech.dshop_api.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "IMAGES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image extends AuditEntity<Long> {
    @Column(name = "IMAGE_URL", nullable = false)
    private String imageUrl;

    @OneToOne(mappedBy = "avatar")
    private CustomerInfo customerInfo;

    @OneToOne(mappedBy = "image")
    private Category category;
}