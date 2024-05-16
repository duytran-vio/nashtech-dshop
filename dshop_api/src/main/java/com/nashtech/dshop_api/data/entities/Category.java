package com.nashtech.dshop_api.data.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CATEGORIES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends AuditEntity<Long> {
    @Column(name = "CATEGORY_NAME", nullable = false, unique = true)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @Column(name = "LAYER_NUM")
    private Integer layerNum;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parentCategory;

    @OneToOne
    @JoinColumn(name = "IMAGE_ID")
    private Image image;

    @OneToMany(mappedBy = "parentCategory")
    private List<Category> subCategories;
}