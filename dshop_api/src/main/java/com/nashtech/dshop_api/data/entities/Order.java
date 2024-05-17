package com.nashtech.dshop_api.data.entities;

import java.util.List;

import jakarta.persistence.CascadeType;

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
@Table(name = "ORDERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AuditEntity<Long> {
    @Column(name = "TOTAL", nullable = false)
    private double total;

    @Column(name = "CREATE_AT", nullable = false)
    private String createAt;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "CUSTOMER_ID")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "ORDER_STATUS_ID")
    private StatusType orderStatus;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}