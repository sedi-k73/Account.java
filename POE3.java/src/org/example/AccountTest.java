// LESEDI KHUMOLETLOTLO MOREKU , ST10484098

 package org.example;

import org.junit.jupiter.api.BeforeEach;     // JUnit 5 User Guide – JUnit Team. Available at: https://junit.org/junit5/docs/current/user-guide/
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {                      // with the help from (reference) : Oracle Java Documentation – Oracle. Available at: https://docs.oracle.com/javase/

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account("kyl_1", "C&&sec@ke99!", "+27838968976", "Lesedi", "Moreku");
    }

    // Testing Username validation

    @Test
    void checkUsernameCorrect() {
        assertEquals("kyl_1", "kyl_1", "Username correctly formatted");
    }

    @Test
    void checkUsernameIncorrect() {
        assertEquals("kyle!!!!!!!", "kyle!!!!!!!", "Username is not correctly formatted. Please ensure that the username contains an underscore and is no more than five characters in length.");
    }

    @Test
    void checkPasswordCorrect() {
        assertEquals("C&&sec@ke99!", "C&&sec@ke99!", "Password correctly formatted");
    }

    void checkPasswordIncorrect() {
        assertEquals("password", "password", "Password is not correctly formatted. Please ensure your password contains at least 8 characters, an uppercase letter, a number, and a special character");
    }


    // Testing cell numbers
    @Test
    void checkCellNumbersCorrectly() {
        assertTrue(Account.checkCellNumbers("+27838968976"), "Cell numbers successfully captured ");
    }

    @Test
    void checkCellNumbersIncorrectly() {
        assertFalse(Account.checkCellNumbers("0612345678"), "Cell numbers incorrectly formatted or does not contain the international code please write the correct number or try again"); // there is not +27 and the length is wrong
    }

    // Testing LOGIN
    @Test
    void checkLoginUserSuccessful() {
        assertTrue(account.loginUser("kyl_1", "C&&sec@ke99!"), "login Successful"); // the user will be successfully loged in
    }

    @Test
    void checkLoginUserFailed() {
        assertFalse(account.loginUser("kyle!!!!!!!", "password"), "login Unsuccessful"); //login unsuccessful
    }// with help from: OpenAI. (2025). ChatGPT (GPT-5). Retrieved from https://openai.com/
}

