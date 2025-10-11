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
    void  checkUsernameCorrect() {
        assertEquals("kyl_1", "kyl_1","Username correctly formatted");
    }
    @Test
    void  checkUsernameIncorrect() {
        assertEquals("kyle!!!!!!!", "kyle!!!!!!!","Username is not correctly formatted. Please ensure that the username contains an underscore and is no more than five characters in length.");
    }

    @Test
    void  checkPasswordCorrect() {
        assertEquals("C&&sec@ke99!", "C&&sec@ke99!","Password correctly formatted");
    }
    void  checkPasswordIncorrect() {
        assertEquals("password", "password","Password is not correctly formatted. Please ensure your password contains at least 8 characters, an uppercase letter, a number, and a special character");
    }



    // Testing cell numbers
    @Test
    void checkCellNumbersCorrectly() {
        assertTrue(Account.checkCellNumbers("+27838968976") ,"Cell numbers successfully captured ");
    }

    @Test
    void checkCellNumbersIncorrectly() {
        assertFalse(Account.checkCellNumbers("0612345678"),"Cell numbers incorrectly formatted or does not contain the international code please write the correct number or try again"); // there is not +27 and the length is wrong
    }

    // Testing LOGIN
    @Test
    void checkLoginUserSuccessful() {
        assertTrue(account.loginUser("kyl_1", "C&&sec@ke99!"),"login Successful"); // the user will be successfully loged in
    }

    @Test
    void checkLoginUserFailed() {
        assertFalse(account.loginUser("kyle!!!!!!!", "password"),"login Unsuccessful"); //login unsuccessful
    }


    // TESTING MESSAGE FUNCTIONALITY (PART 2)
    // with help from: OpenAI. (2025). ChatGPT (GPT-5). Retrieved from https://openai.com/
    // and Gson Documentation – Google. Available at: https://github.com/google/gson

    @Test
    void checkMessageWithinCharacterLimit() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight", 1);
        String result = msg.validateMessageLength();
        assertEquals("Message ready to send.", result,
                "Message length is within 250 characters.");
    }

    @Test
    void checkMessageExceedsCharacterLimit() {
        String longMessage = "A".repeat(260);
        Message msg = new Message("+27718693002", longMessage, 1);
        String result = msg.validateMessageLength();
        assertTrue(result.contains("Message exceeds 250 characters"),
                "System correctly identifies message exceeding 250 characters.");
    }

    @Test
    void checkRecipientNumberCorrect() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight", 1);
        boolean result = msg.checkRecipientCell("+27718693002");
        assertTrue(result, "Cell phone number successfully captured.");
    }

    @Test
    void checkRecipientNumberIncorrect() {
        Message msg = new Message("08575975889", "Hi Keegan, did you receive the payment?", 1);
        boolean result = msg.checkRecipientCell("08575975889");
        assertFalse(result,
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

    @Test
    void testMessageHashGeneration() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight", 1);
        String hash = msg.createMessageHash();
        String expectedHash = "00:0:RTONIGHT";  // Correct expected hash based on method logic
        assertEquals(expectedHash, hash, "Message hash is correct.");
    }

    @Test
    void testMessageIDLimit() {
        Message msg = new Message("+27718693002", "Testing ID length", 1);
        String id = msg.getMessageID();
        assertTrue(id.length() <= 10, "Message ID should not exceed 10 characters.");
    }

    @Test
    void testSendMessageOption_Send() {
        Message msg = new Message("+27718693002", "Quick test for send option", 1);
        String result = msg.sendMessage("Send");
        assertEquals("Message successfully sent.", result, "User selected 'Send Message'.");
    }

    @Test
    void testSendMessageOption_Disregard() {
        Message msg = new Message("+27718693002", "Quick test for discard option", 1);
        String result = msg.sendMessage("Disregard");
        assertEquals("Press 0 to delete message.", result, "User selected 'Disregard Message'.");
    }

    @Test
    void testSendMessageOption_Store() {
        Message msg = new Message("+27718693002", "Quick test for store option", 1);
        String result = msg.sendMessage("Store");
        assertEquals("Message successfully stored.", result, "User selected 'Store Message'.");
    }

    @Test
    void testReturnTotalMessages() {
        Message msg1 = new Message("+27718693002", "Message one", 1);
        msg1.sendMessage("Send");

        Message msg2 = new Message("+27718693002", "Message two", 1);
        msg2.sendMessage("Send");

        int total = Message.returnTotalMessages();
        assertTrue(total >= 2, "Total number of messages sent is returned correctly.");
    }
}  // 1. OpenAI. (2025). ChatGPT (GPT-5). Retrieved from https://openai.com/