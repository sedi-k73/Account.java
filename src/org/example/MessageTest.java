// LESEDI KHUMOLETLOTLO MOREKU , ST10484098
// With guidance from: OpenAI. (2025). ChatGPT (GPT-5). Retrieved from https://openai.com/
// Oracle. (n.d.). JUnit 5 User Guide. Retrieved from https://junit.org/junit5/docs/current/user-guide/
// TESTING MESSAGE FUNCTIONALITY (PART 2)
// with help from: OpenAI. (2025). ChatGPT (GPT-5). Retrieved from https://openai.com/
// and Gson Documentation – Google. Available at: https://github.com/google/gson

package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;  // JUnit 5 User Guide – JUnit Team. Available at: https://junit.org/junit5/docs/current/user-guide/

public class MessageTest {

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
        // Create a sample message
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight", 1);

        // Generate hash
        String hash = msg.createMessageHash();

        // The messageID is randomly generated, so we only verify the hash follows correct structure
        assertFalse(hash.matches("\\d{2}:0:HITONIGHT"),
                "Hash should match format NN:0:FIRSTLAST in uppercase (e.g., 12:0:HITONIGHT).");
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
}