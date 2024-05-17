package com.nashtech.dshop_api.data.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "PRODUCTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AuditEntity<Long> {
    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "REVIEW_NUM", nullable = false)
    private Integer reviewNum;

    @Column(name = "AVG_RATING", nullable = false)
    private Float avgRating;

    @Column(name = "SOLD_NUM", nullable = false)
    private Integer soldNum;

    @Column(name = "PRICE", nullable = false)
    private Float price;

    @Column(name = "STOCK", nullable = false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "STATUS_ID")
    private StatusType status;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "CREATE_USER_ID")
    private User createUser;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;
}