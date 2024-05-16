package com.nashtech.dshop_api.data.entities;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "CUSTOMER_INFO") 
public class CustomerInfo extends AuditEntity<Long>{

    @Column(name = "FULLNAME")
    private String fullName;

    @Column(name = "PHONE")
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AVATAR_ID")
    private Image avatar;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ONLINE_STATUS_ID")
    private StatusType onlineStatus;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DATE_OF_BIRTH")
    private LocalDateTime dateOfBirth;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CARD_ID")
    private Card card;

    @OneToOne(mappedBy = "info")
    private User user;
}
