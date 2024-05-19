package com.nashtech.dshop_api.data.entities;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "CUSTOMER_INFO") 
@Getter
@Setter
public class CustomerInfo extends AuditEntity<Long>{

    @Column(name = "FULLNAME")
    private String fullName;

    @Column(name = "PHONE")
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AVATAR_ID")
    private Image avatar;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DATE_OF_BIRTH")
    private LocalDateTime dateOfBirth;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
