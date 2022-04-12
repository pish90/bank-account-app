package com.bankaccount.service;

import com.bankaccount.entities.BankAccount;
import org.springframework.stereotype.Service;

@Service
public interface BankAccountService {

    void createAccount(BankAccount bankAccount);

}
