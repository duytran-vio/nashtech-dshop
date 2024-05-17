package com.nashtech.dshop_api.data.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CARTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends AuditEntity<Long> {
    @OneToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private User customer;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;
}