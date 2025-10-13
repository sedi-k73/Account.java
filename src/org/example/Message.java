// LESEDI KHUMOLETLOTLO MOREKU , ST10484098
// With guidance from: OpenAI. (2025). ChatGPT (GPT-5). Retrieved from https://openai.com/
// and Gson Documentation – Google. Available at: https://github.com/google/gson

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
    private int messageNumber;

    private static int totalMessages = 0;
    private static final ArrayList<Message> sentMessages = new ArrayList<>();

    // Constructor
    public Message(String recipientNumber, String message, int messageNumber) {
        this.recipientNumber = recipientNumber;
        this.message = message;
        this.messageID = generateNumericMessageID();
        this.messageNumber = messageNumber;
        this.messageHash = createMessageHash();
    }

    //  Generate numeric Message ID (8 random digits)
    private String generateNumericMessageID() {
        Random rand = new Random();
        int id = 10000000 + rand.nextInt(90000000); // ensures 8-digit number
        return String.valueOf(id);
    }

    //  Check Message ID - must not exceed 10 digits
    public boolean checkMessageID(String messageID) {
        return messageID.matches("\\d{1,10}");
    }

    //  Check Recipient Cell number format
    public boolean checkRecipientCell(String cellNumber) {
        return cellNumber.startsWith("+27") && cellNumber.length() == 12;
    }

    //  Validate Message Length (max 250 characters)
    public String validateMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int exceedBy = message.length() - 250;
            return "Message exceeds 250 characters by " + exceedBy + ", please reduce size.";
        }
    }

    //  Create Message Hash (e.g. 12:1:HITHANKS)
    public String createMessageHash() {
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0].toUpperCase() : "NONE";
        String lastWord = words.length > 1 ? words[words.length - 1].toUpperCase() : firstWord;

        String firstTwoDigits = messageID.substring(0, 2);
        return firstTwoDigits + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    //  Send Message Options: Send, Disregard, Store
    public String sendMessage(String option) {
        switch (option.toLowerCase()) {
            case "send" -> {
                sentMessages.add(this);
                totalMessages++;
                return "Message successfully sent.";
            }
            case "disregard" -> {
                return "Press 0 to delete message.";
            }
            case "store" -> {
                storeMessageJSON();
                return "Message successfully stored.";
            }
            default -> {
                return "Invalid option.";
            }
        }
    }

    //  Print all messages with full details
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }

        StringBuilder sb = new StringBuilder("Messages Sent:\n\n");
        for (Message msg : sentMessages) {
            sb.append("Message ID: ").append(msg.messageID).append("\n")
                    .append("Message Hash: ").append(msg.messageHash).append("\n")
                    .append("Recipient: ").append(msg.recipientNumber).append("\n")
                    .append("Message: ").append(msg.message).append("\n")
                    .append("-------------------------------\n");
        }
        sb.append("Total Messages Sent: ").append(totalMessages);
        return sb.toString();
    }

    //  Return Total Messages Sent
    public static int returnTotalMessages() {
        return totalMessages;
    }

    //  Store message in JSON file using GSON
    public void storeMessageJSON() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("stored_messages.json", true)) {
            gson.toJson(this, writer);
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipientNumber() { return recipientNumber; }
    public String getMessage() { return message; }
}
