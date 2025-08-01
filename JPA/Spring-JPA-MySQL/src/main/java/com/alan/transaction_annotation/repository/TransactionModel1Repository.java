package com.alan.transaction_annotation.repository;

import com.alan.transaction_annotation.entity.TransactionModel1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionModel1Repository extends JpaRepository<TransactionModel1,Integer> {
}
