package com.poywallet.poywalletbackend.repository;

import com.poywallet.poywalletbackend.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
}