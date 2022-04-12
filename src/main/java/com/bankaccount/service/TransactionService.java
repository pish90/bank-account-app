package com.bankaccount.service;

import com.bankaccount.entities.Transaction;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

    void saveTransaction(Transaction transaction);
}
