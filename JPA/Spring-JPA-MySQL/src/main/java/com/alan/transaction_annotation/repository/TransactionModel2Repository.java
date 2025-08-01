package com.alan.transaction_annotation.repository;

import com.alan.transaction_annotation.entity.TransactionModel2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionModel2Repository extends JpaRepository<TransactionModel2,Integer> {
}
