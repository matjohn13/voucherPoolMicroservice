package com.azrul.voucherpool.repository;

import com.azrul.voucherpool.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {
    
}
