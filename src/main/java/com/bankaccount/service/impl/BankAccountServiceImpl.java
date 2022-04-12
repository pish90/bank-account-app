package com.bankaccount.service.impl;

import com.bankaccount.entities.BankAccount;
import com.bankaccount.repositories.BankAccountRepository;
import com.bankaccount.service.BankAccountService;
import org.springframework.stereotype.Component;

@Component
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * This method creates a bank account entry in the database
     *
     * @param bankAccount a bank account
     */
    @Override
    public void createAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }
}
