package com.azrul.voucherpool.repository;

import com.azrul.voucherpool.model.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialOfferRepository extends JpaRepository<SpecialOffer, Long> {
    
}
