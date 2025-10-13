// LESEDI KHUMOLETLOTLO MOREKU , ST10484098
// with the help from: OpenAI. (2025). ChatGPT (GPT-5). Retrieved from https://openai.com/

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

    private static int totalMessages = 0;
    private static final ArrayList<String> sentMessages = new ArrayList<>();

    // Constructor
    public Message(String recipientNumber, String message, int i) {
        this.recipientNumber = recipientNumber;
        this.message = message;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    //  Message ID (numbers only)
    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 8; i++) { // 8-digit numeric ID
            id.append(rand.nextInt(10));
        }
        return id.toString();
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    //  Check Message ID not exceed 10 digits
    public boolean checkMessageID(String messageID) {
        return messageID.matches("\\d{1,10}");
    }

    //  Check Recipient Cell number format
    public boolean checkRecipientCell(String cellNumber) {
        return cellNumber.startsWith("+27") && cellNumber.length() == 12;
    }

    //  Validate Message Length (max 250 chars)
    public String validateMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int exceedBy = message.length() - 250;
            return "Message exceeds 250 characters by " + exceedBy + ", please reduce size.";
        }
    }

    //  Create proper Message Hash format
    public String createMessageHash() {
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0].toUpperCase() : "";
        String lastWord = words.length > 1 ? words[words.length - 1].toUpperCase() : firstWord;

        String firstTwoDigits = messageID.substring(0, 2);
        int messageNumber = totalMessages + 1; // count next message

        return String.format("%s:%d:%s%s", firstTwoDigits, messageNumber, firstWord, lastWord);
    }

    //  Send Message Options
    public String sendMessage(String option) {
        switch (option.toLowerCase()) {
            case "send" -> {
                sentMessages.add(message);
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

    //  Print all messages sent while running
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

    //  Return Total Messages Sent
    public static int returnTotalMessages() {
        return totalMessages;
    }

    //  Store message in JSON file       // and Gson Documentation – Google. Available at: https://github.com/google/gson
    public void storeMessageJSON() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("stored_messages.json", true)) {
            gson.toJson(this, writer);
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
