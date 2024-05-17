package com.nashtech.dshop_api.data.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CARDS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Card extends AuditEntity<Long> {
    @Column(name = "CARD_NUMBER", nullable = false)
    private String cardNumber;

    @Column(name = "CARD_HOLDER", nullable = false)
    private String cardHolder;

    @Column(name = "EXPIRED_DATE", nullable = false)
    private LocalDateTime expiredDate;

    @Column(name = "CVV", nullable = false)
    private String cvv;

    @OneToOne(mappedBy = "card")
    private CustomerInfo customerInfo;
}
