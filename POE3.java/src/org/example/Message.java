// LESEDI KHUMOLETLOTLO MOREKU , ST10484098
// With guidance from: OpenAI. (2025). ChatGPT (GPT-5). Retrieved from https://openai.com/

package org.example;

import java.util.Random;

public class Message {
    private static int messageCounter = 0;

    private final String recipientNumber;
    private final String messageText;
    private final String messageID;
    private final String messageHash;

    // Constructor
    public Message(String recipientNumber, String messageText, int id) {
        this.recipientNumber = recipientNumber;
        this.messageText = messageText;

        //  Generate random 10-digit numeric message ID
        this.messageID = generateNumericID();

        //  Increase message counter
        messageCounter++;

        //  Generate hash immediately
        this.messageHash = createMessageHash();
    }

    // Generate 10-digit numeric ID
    private String generateNumericID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10)); // append a digit 0–9
        }
        return sb.toString();
    }

    // Validate Message Length
    public String validateMessageLength() {
        if (messageText.length() > 250) {
            return "Message too long. Please limit your message to 250 characters.";
        }
        return "Message ready to send.";
    }

    //  Check Recipient Cell Number
    public boolean checkRecipientCell(String number) {
        return number != null && number.startsWith("+27") && number.length() == 12;
    }

    //  Create Message Hash
    public String createMessageHash() {
        // Format: first two digits of ID : messageCounter : first+last words
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        String firstTwoDigits = messageID.substring(0, 2);
        return (firstTwoDigits + ":" + messageCounter + ":" + firstWord + lastWord).toUpperCase();
    }

    //  Send Message Action
    public String sendMessage(String action) {
        switch (action) {
            case "Send":
                return "Message sent successfully to " + recipientNumber + ".";
            case "Disregard":
                return "Message disregarded.";
            case "Store":
                return "Message stored for later.";
            default:
                return "Invalid action.";
        }
    }

    // Static tracking methods
    public static int returnTotalMessages() {
        return messageCounter;
    }

    // --- Getters ---
    public String getMessage() {
        return messageText;
    }

    public String getRecipientNumber() {
        return recipientNumber;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    //  Optional placeholder
    public static String printMessages() {
        return "Coming Soon.";       // this is just optional.
    }
}