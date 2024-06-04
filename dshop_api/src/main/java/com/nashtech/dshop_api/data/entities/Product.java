package com.nashtech.dshop_api.data.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AuditEntity<Long> {
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "review_num", nullable = false)
    private Long reviewNum;

    @Column(name = "avg_rating", nullable = false)
    private Float avgRating;

    @Column(name = "sold_num", nullable = false)
    private Long soldNum;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "stock", nullable = false)
    private Long stock;

    @Column(name = "status_id")
    private StatusType status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "create_user_id")
    private User createUser;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_featured")
    private Boolean isFeatured;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    // @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    // private List<ProductImage> productImages;

    @OneToMany
    @JoinTable(name = "product_images",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private List<Image> images;
}