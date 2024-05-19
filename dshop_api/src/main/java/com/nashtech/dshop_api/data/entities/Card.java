package com.nashtech.dshop_api.data.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CARDS")
@Getter
@Setter
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
}
