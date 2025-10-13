// LESEDI KHUMOLETLOTLO MOREKU , ST10484098
// With guidance from: OpenAI. (2025). ChatGPT (GPT-5). Retrieved from https://openai.com/
// Gson Documentation – Google. Available at: https://github.com/google/gson

package org.example;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Message {

    private String recipientNumber;
    private String message;
    private String messageID;
    private String messageHash;

    // Keeps all sent messages in memory
    private static int totalMessages = 0;
    private static final ArrayList<String> sentMessages = new ArrayList<>();

    // Constructor
    public Message(String recipientNumber, String message, int i) {
        this.recipientNumber = recipientNumber;
        this.message = message;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Check Message ID - must not exceed 10 characters
    public boolean checkMessageID(String messageID) {
        return messageID.length() <= 10;
    }

    // Generate random Message ID (auto)
    private String generateMessageID() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder id = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 8; i++) {
            id.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return id.toString();
    }

    public String getMessageID() {
        return messageID;
    }

    // Check Recipient Cell number format
    public boolean checkRecipientCell(String cellNumber) {
        return cellNumber.startsWith("+27") && cellNumber.length() == 12;
    }

    // Validate Message Length (max 250 characters)
    public String validateMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int exceedBy = message.length() - 250;
            return "Message exceeds 250 characters by " + exceedBy + ", please reduce size.";
        }
    }

    //  Create Message Hash
    public String createMessageHash() {
        // Split message into words
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words.length > 1 ? words[words.length - 1].toUpperCase() : firstWord;

        // Get first two characters that are numbers (if any) from messageID
        String digits = messageID.replaceAll("\\D", ""); // remove non-digits
        String firstTwoNumbers = digits.length() >= 2 ? digits.substring(0, 2) : "00";

        // Combine into final hash format: 00:0:FIRSTLAST
        return firstTwoNumbers + ":0:" + firstWord + lastWord;
    }


    // Send Message Options: Send, Disregard, Store
    public String sendMessage(String option) {
        switch (option.toLowerCase()) {
            case "send":
                sentMessages.add(message);
                totalMessages++;
                return "Message successfully sent.";
            case "disregard":
                return "Press 0 to delete message.";
            case "store":
                storeMessageJSON();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    // Print all messages sent while running
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }
        StringBuilder sb = new StringBuilder("Messages Sent:\n");
        for (String msg : sentMessages) {
            sb.append("- ").append(msg).append("\n");
        }
        return sb.toString();
    }

    // Return Total Messages Sent
    public static int returnTotalMessages() {
        return totalMessages;
    }

    // Store message in JSON file using GSON
    public void storeMessageJSON() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("stored_messages.json", true)) {
            gson.toJson(this, writer);
            writer.write(System.lineSeparator());  //  Stack Overflow Community. (2025). Discussions on Java OOP and JSON handling. Available at: https://stackoverflow.com/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}