package com.nashtech.dshop_api.data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.dshop_api.data.entities.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    public Optional<Card> findByUserId(Long userId);
}
