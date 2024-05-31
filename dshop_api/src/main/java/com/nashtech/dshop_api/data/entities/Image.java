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
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image extends AuditEntity<Long> {
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "size", nullable = false)
    private Long size;

    @OneToOne(mappedBy = "avatar")
    private CustomerInfo customerInfo;

    @OneToOne(mappedBy = "image")
    private Category category;
}