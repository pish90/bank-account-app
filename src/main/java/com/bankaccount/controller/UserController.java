package com.bankaccount.controller;

import com.bankaccount.entities.User;
import com.bankaccount.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * User controller
 */
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieve the list of users
     *
     * @param model the model
     *
     * @return a list of users
     */
    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "index";
    }

    /**
     * Redirect the user to the signup form to add users
     * if there are no users on the system
     *
     * @param user a user
     *
     * @return a form to add a user
     */
    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }

    /**
     * Add a user to the system
     *
     * @param user a user
     * @param result the binding result
     * @param model the model
     *
     * @return a form to add a user
     */
    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        userService.saveUser(user);
        return "redirect:/index";
    }

    /**
     * Show the form to use to update a user's details
     *
     * @param id the user ID
     * @param model the model
     *
     * @return an editable user form
     */
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);

        return "update-user";
    }

    /**
     * Update a user's details
     *
     * @param id the user ID
     * @param user the user
     * @param result the result
     * @param model the model
     *
     * @return an user with updated details
     */
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        userService.saveUser(user);
        return "redirect:/index";
    }

    /**
     * Delete a user
     *
     * @param id the user ID
     * @param model the model
     *
     * @return the list of users without the deleted user
     */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userService.delete(user);
        return "redirect:/index";
    }
}
