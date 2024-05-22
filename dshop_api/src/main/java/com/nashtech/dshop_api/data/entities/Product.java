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

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;
}