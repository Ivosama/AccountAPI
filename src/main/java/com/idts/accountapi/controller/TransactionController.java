package com.idts.accountapi.controller;

import com.idts.accountapi.dao.TransactionRequest;
import com.idts.accountapi.model.Transaction;
import com.idts.accountapi.utils.TransactionService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PutMapping("/transferFunds")
    public ResponseEntity<?> transferFunds(@RequestBody TransactionRequest transactionRequest) {
        EntityModel<Transaction> transactionEntityModel =
                transactionService.createTransaction(transactionRequest.getFromAccountId(),
                        transactionRequest.getToAccountId(),
                        transactionRequest.getAmountToTransfer());

        return ResponseEntity.created(transactionEntityModel.getRequiredLink(IanaLinkRelations.SELF)
                .toUri()).body(transactionEntityModel);
    }

    @PutMapping("/addFunds")
    public ResponseEntity<?> addFunds(@RequestBody TransactionRequest transactionRequest) {
        EntityModel<Transaction> transactionEntityModel =
                transactionService.createTransaction(transactionRequest.getToAccountId(),
                        transactionRequest.getAmountToTransfer());

        return ResponseEntity.created(transactionEntityModel.getRequiredLink(IanaLinkRelations.SELF)
                .toUri()).body(transactionEntityModel);
    }
}
