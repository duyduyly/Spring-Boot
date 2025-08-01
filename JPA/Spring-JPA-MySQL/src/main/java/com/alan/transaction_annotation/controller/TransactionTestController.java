package com.alan.transaction_annotation.controller;

import com.alan.transaction_annotation.entity.TransactionModel2;
import com.alan.transaction_annotation.service.TransactionTestService;
import com.alan.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction-annotation")
@RequiredArgsConstructor
public class TransactionTestController {

    private final TransactionTestService testService;
    private final JsonUtils jsonUtils;

    @GetMapping("/create")
    public String create() {
        TransactionModel2 transactionModel2 = testService.addTransaction();
        return jsonUtils.convertToJson(transactionModel2);
    }

    @GetMapping("/create-transaction")
    public String createTransaction() {
        TransactionModel2 transactionModel2 = testService.addTransactionTransactionAnnotation();
        return jsonUtils.convertToJson(transactionModel2);
    }

    @GetMapping("/create-not-transaction")
    public String createNotTransaction() {
        TransactionModel2 transactionModel2 = testService.addTransactionButNotTransactionAnnotation();
        return jsonUtils.convertToJson(transactionModel2);
    }
}
