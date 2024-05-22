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
@Table(name = "status_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusType extends AuditEntity<Long>{

    @Column(name = "status_name", unique = true)
    private String statusName;

    @OneToMany(mappedBy = "onlineStatus")
    private List<User> users;

    @OneToMany(mappedBy = "orderStatus")
    private List<Order> orders;
}
