package com.bankaccount.controller;

import com.bankaccount.entities.User;
import com.bankaccount.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private static UserController userController;
    private static UserService mockedUserService;
    private static BindingResult mockedBindingResult;
    private static Model mockedModel;
    private static User user;

    @BeforeEach
    public void setUp() {
        mockedUserService = mock(UserService.class);
        mockedBindingResult = mock(BindingResult.class);
        mockedModel = mock(Model.class);
        userController = new UserController(mockedUserService);
        user = new User("John", "Doe");
    }

    @Test
    public void whenCalledIndex_thenCorrect() {
        assertThat(userController.showUserList(mockedModel)).isEqualTo("index");
    }

    @Test
    public void whenCalledShowSignUpForm_thenCorrect() {
        assertThat(userController.showSignUpForm(user)).isEqualTo("add-user");
    }

    @Test
    public void whenCalledaddUserAndValidUser_thenCorrect() {
        when(mockedBindingResult.hasErrors()).thenReturn(false);

        assertThat(userController.addUser(user, mockedBindingResult, mockedModel)).isEqualTo("redirect:/index");
    }

    @Test
    public void whenCalledaddUserAndInvalidUser_thenCorrect() {
        when(mockedBindingResult.hasErrors()).thenReturn(true);

        assertThat(userController.addUser(user, mockedBindingResult, mockedModel)).isEqualTo("add-user");
    }

    @Test
    public void whenCalledshowUpdateForm_thenIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            assertThat(userController.showUpdateForm(0, mockedModel)).isEqualTo("update-user");
        });

        String expectedMessage = "Invalid user Id:";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenCalledupdateUserAndInvalidUser_thenCorrect() {
        when(mockedBindingResult.hasErrors()).thenReturn(true);

        assertThat(userController.updateUser(1L, user, mockedBindingResult, mockedModel)).isEqualTo("update-user");
    }

    @Test
    public void whenCalleddeleteUser_thenIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
           assertThat(userController.deleteUser(1L, mockedModel)).isEqualTo("redirect:/index");
        });

        String expectedMessage = "Invalid user Id:";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}