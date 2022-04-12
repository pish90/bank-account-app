package com.bankaccount.service.impl;

import com.bankaccount.entities.BankAccount;
import com.bankaccount.entities.Transaction;
import com.bankaccount.entities.User;
import com.bankaccount.repositories.UserRepository;
import com.bankaccount.service.BankAccountService;
import com.bankaccount.service.TransactionService;
import com.bankaccount.service.UserService;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BankAccountService bankAccountService;

    private final TransactionService transactionService;

    public UserServiceImpl(UserRepository userRepository, BankAccountService bankAccountService, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.bankAccountService = bankAccountService;
        this.transactionService = transactionService;
    }

    /**
     * This method creates a user entity in the database
     *
     * @param user a user
     */
    @Override
    public void saveUser(User user) {
        BankAccount bankAccount = new BankAccount();
        Transaction transaction = new Transaction();
        UUID uuid= UUID.randomUUID();
        bankAccount.setAccountNumber(uuid.toString());
        bankAccount.setCustomerId(user.getCustomerId());
        bankAccount.setInitialCredit(user.getInitialCredit());
        bankAccount.setAccountBalance(user.getBalance());
        // check if initialCredit = 0, then send a transaction to the account
        if (Double.compare(user.getInitialCredit(), 0) == 0) {
            transaction.setName("initialized");
            transaction.setBankAccount(bankAccount);
            List<Transaction> transactionList = new ArrayList<>();
            transactionList.add(transaction);
            bankAccount.setTransactionList(transactionList);
        }
        else {
            transaction.setName("deposit");
            transaction.setBankAccount(bankAccount);
            List<Transaction> transactionList = new ArrayList<>();
            transactionList.add(transaction);
            bankAccount.setTransactionList(transactionList);
        }

        User savedUser = userRepository.save(user);

        bankAccount.setUser(savedUser);

        savedUser.setBankAccount(bankAccount);

        transactionService.saveTransaction(transaction);

        bankAccountService.createAccount(bankAccount);

    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    /**
     *
     * @return
     */
    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    /**
     *
     * @param user
     */
    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}
