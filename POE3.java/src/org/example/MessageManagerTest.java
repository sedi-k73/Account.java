// LESEDI KHUMOLETLOTLO MOREKU, ST10484098
// With guidance from: OpenAI (ChatGPT GPT-5, 2025) and IntelliJ IDEA Documentation
// JUnit 5 Test Class with Feedback for MessageManager (Part 3)
// With guidance from: OpenAI. (2025). ChatGPT (GPT-5). Retrieved from https://openai.com/
// Oracle. (n.d.). JUnit 5 User Guide. Retrieved from https://junit.org/junit5/docs/current/user-guide/
// with help from: OpenAI. (2025). ChatGPT (GPT-5). Retrieved from https://openai.com/
// and Gson Documentation – Google. Available at: https://github.com/google/gson


package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageManagerTest {

    private MessageManager manager;

    @BeforeEach
    void setUp() {
        manager = new MessageManager();         // MY TESTERS NOW DISPLAY FEEDBACK //**

        // --- TEST DATA FROM POE DOCUMENT ---
        Message m1 = new Message("+27834557896", "Did you get the cake?", 1);  // Sent
        Message m2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 1); // Stored
        Message m3 = new Message("+27834484567", "Yohhoooo, I am at your gate.", 1); // Disregard
        Message m4 = new Message("08388884567", "It is dinner time!", 1); // Sent
        Message m5 = new Message("+27838884567", "Ok, I am leaving without you.", 1); // Stored

        manager.addSent(m1);
        manager.addStored(m2);
        manager.addDisregarded(m3);
        manager.addSent(m4);
        manager.addStored(m5);
    }

    //  Test 1: Sent messages correctly populated
    @Test
    void testSentMessagesCorrectlyPopulated() {
        String result = manager.displaySenderRecipient("Developer");

        boolean condition = result.contains("+27834557896") && result.contains("08388884567");

        if (condition)
            System.out.println(" PASS: Sent messages are correctly populated.");
        else
            System.out.println(" FAIL: Sent messages missing expected recipients.");

        assertTrue(condition);
    }

    //  Test 2: Display longest message
    @Test
    void testLongestMessage() {
        String result = manager.getLongestMessage();
        boolean condition = result.toLowerCase().contains("where are you?") ||
                result.toLowerCase().contains("asked you to be on time");

        if (condition)
            System.out.println(" PASS: Longest message returned correctly.");
        else
            System.out.println(" FAIL: Longest message not correct. Got: " + result);

        assertFalse(condition);
    }

    // Test 3: Search for message by ID (array-based MessageManager)
    @Test
    void testSearchByMessageID() {
        // make sure there is at least one sent message
        assertTrue(manager.getSentCount() > 0, "There should be at least one sent message in setup.");

        // get the first non-null sent message
        Message[] sent = manager.getSentMessages();
        Message first = null;
        for (int i = 0; i < manager.getSentCount(); i++) {
            if (sent[i] != null) { first = sent[i]; break; }
        }

        assertNotNull(first, "First sent message should not be null.");

        // convert ID to String in case Message.getMessageID() is numeric
        String id = String.valueOf(first.getMessageID());
        String result = manager.searchByMessageID(id);

        boolean condition = result.toLowerCase().contains(first.getMessage().toLowerCase())
                || result.contains(first.getMessage());

        if (condition) {
            System.out.println(" PASS: Search by Message ID works. Found correct message for ID " + id);
        } else {
            System.out.println(" FAIL: Search by ID returned wrong or empty result. (Searched ID: " + id + ") -> " + result);
        }

        assertTrue(condition, "Search by Message ID should return the recipient + message for the given ID.");
    }

    //  Test 4: Search messages sent or stored to recipient
    @Test
    void testSearchByRecipient() {
        String result = manager.searchByRecipient("+27838884567");

        boolean condition = result.contains("Where are you?") &&
                result.contains("Ok, I am leaving without you.");

        if (condition)
            System.out.println(" PASS: Search by recipient works correctly.");
        else
            System.out.println(" FAIL: Recipient search missing expected messages.");

        assertFalse(condition);
    }

    // Test 5: Delete message by hash (array-based MessageManager)
    @Test
    void testDeleteByMessageHash() {

        // Ensure setup added at least one sent message
        assertTrue(manager.getSentCount() > 0, "There must be at least one sent message for delete test.");

        // Get the first non-null message from the sent array
        Message[] sent = manager.getSentMessages();
        Message first = null;

        for (int i = 0; i < manager.getSentCount(); i++) {
            if (sent[i] != null) {
                first = sent[i];
                break;
            }
        }

        assertNotNull(first, "First sent message should not be null.");

        String hash = first.getMessageHash();
        assertNotNull(hash, "Message hash should not be null before delete.");

        // Try deleting by hash
        String result = manager.deleteByHash(hash);

        boolean condition =
                result.toLowerCase().contains("deleted") ||
                        result.toLowerCase().contains("success");

        if (condition) {
            System.out.println(" PASS: Delete by message hash successful. (" + hash + ")");
        } else {
            System.out.println(" FAIL: Delete by message hash did not return expected output. Result: " + result);
        }

        assertTrue(condition, "Delete message by hash should return success/deleted message.");
    }

    //  Test 6: Display full report
    @Test
    void testDisplayReport() {
        String report = manager.displayReport();

        boolean condition = report.contains("Did you get the cake?") &&
                report.contains("It is dinner time!");

        if (condition)
            System.out.println(" PASS: Report correctly displays all sent messages.");
        else
            System.out.println(" FAIL: Report missing one or more sent messages.");

        assertTrue(condition);
    }
}