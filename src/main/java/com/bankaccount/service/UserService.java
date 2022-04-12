package com.bankaccount.service;

import com.bankaccount.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    void saveUser(User user);

    Optional<User> findById(long id);

    List<User> findAll();

    void delete(User user);
}
