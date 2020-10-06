package com.meetup.ms.stock.repository;

import com.meetup.ms.stock.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, UUID> {

    Optional<StockEntity> findById(UUID id);
}
