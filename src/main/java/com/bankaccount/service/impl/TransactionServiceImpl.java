package com.bankaccount.service.impl;

import com.bankaccount.entities.Transaction;
import com.bankaccount.repositories.TransactionRepository;
import com.bankaccount.service.TransactionService;
import org.springframework.stereotype.Component;

@Component
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * This method creates a transaction entry in the database
     *
     * @param transaction a transaction
     */
    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
