package com.nashtech.dshop_api.data.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "STATUS_TYPES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusType extends AuditEntity<Long>{

    @Column(name = "STATUS_NAME", unique = true)
    private String statusName;

    @OneToMany(mappedBy = "onlineStatus")
    private List<CustomerInfo> customerInfos;

    @OneToMany(mappedBy = "orderStatus")
    private List<Order> orders;
}
