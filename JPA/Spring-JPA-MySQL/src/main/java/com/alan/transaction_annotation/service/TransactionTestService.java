package com.alan.transaction_annotation.service;

import com.alan.transaction_annotation.entity.TransactionModel1;
import com.alan.transaction_annotation.entity.TransactionModel2;
import com.alan.transaction_annotation.repository.TransactionModel1Repository;
import com.alan.transaction_annotation.repository.TransactionModel2Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionTestService {

    private final TransactionModel1Repository transactionModel1Repository;
    private final TransactionModel2Repository transactionModel2Repository;

    @Transactional
    public TransactionModel2 addTransaction() {
        TransactionModel1 model1 = new TransactionModel1("Transaction 1");
        transactionModel1Repository.save(model1);

        TransactionModel2 model2 = new TransactionModel2();
        model2.setTransactionModel1(model1);
        model2.setAddress("Address 1");

        return transactionModel2Repository.save(model2);
    }

    //don't save TransactionModel1 because Transaction was rollback TransactionModel1
    @Transactional(rollbackFor = Exception.class)
    public TransactionModel2 addTransactionTransactionAnnotation() {
        TransactionModel1 model1 = new TransactionModel1("Transaction 1");
        transactionModel1Repository.save(model1);

        TransactionModel2 model2 = null;
        model2.setTransactionModel1(model1);
        model2.setAddress("Address 1");

        return transactionModel2Repository.save(model2);
    }

    //save TransactionModel1, although TransactionModel2 Throw NullPointerException
    public TransactionModel2 addTransactionButNotTransactionAnnotation() {
        TransactionModel1 model1 = new TransactionModel1("Transaction 1");
        transactionModel1Repository.save(model1);

        TransactionModel2 model2 = null;
        model2.setTransactionModel1(model1);
        model2.setAddress("Address 1");

        return transactionModel2Repository.save(model2);
    }

}
