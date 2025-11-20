package org.example;
//JetBrains (2025) IntelliJ IDEA Community Edition 2025.2. JetBrains s.r.o. Available at: https://www.jetbrains.com/idea/
// (Accessed: 18 November 2025).
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.File;  //Oracle (2024) Java SE Documentation. Oracle Corporation. Available at: https://docs.oracle.com/javase/(Accessed: 18 November 2025).
import java.io.FileWriter;

public class MessageManager {


    // Arrays* instead of ArrayList

    private Message[] sentMessages = new Message[100];
    private int sentCount = 0;

    private Message[] disregardedMessages = new Message[100];
    private int disregardedCount = 0;

    private Message[] storedMessages = new Message[100];
    private int storedCount = 0;




    // EXPAND ARRAY IF FULL

    private Message[] expand(Message[] arr) {
        Message[] bigger = new Message[arr.length * 2];
        System.arraycopy(arr, 0, bigger, 0, arr.length);
        return bigger;
    }



    // Add Messages

    public void addSent(Message m) {
        if (m != null) {
            if (sentCount == sentMessages.length)
                sentMessages = expand(sentMessages);
            sentMessages[sentCount++] = m;
        }
    }

    public void addDisregarded(Message m) {
        if (m != null) {
            if (disregardedCount == disregardedMessages.length)
                disregardedMessages = expand(disregardedMessages);
            disregardedMessages[disregardedCount++] = m;
        }
    }

    public void addStored(Message m) {
        if (m != null) {
            if (storedCount == storedMessages.length)
                storedMessages = expand(storedMessages);

            storedMessages[storedCount++] = m;
            appendMessageToJson(m, "stored_messages.json");
        }
    }




    //  Display sender + recipient

    public String displaySenderRecipient(String senderName) {
        if (sentCount == 0) return "No sent messages.";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentCount; i++) {
            Message m = sentMessages[i];
            sb.append("Sender: ").append(senderName)
                    .append(" | Recipient: ").append(m.getRecipientNumber())
                    .append(" | Message ID: ").append(m.getMessageID())
                    .append("\n");
        }
        return sb.toString();
    }




    //  Longest message

    public String getLongestMessage() {
        if (sentCount == 0) return "No sent messages.";

        Message longest = sentMessages[0];

        for (int i = 1; i < sentCount; i++) {
            if (sentMessages[i].getMessage().length() > longest.getMessage().length()) {
                longest = sentMessages[i];
            }
        }

        return longest.getMessage();
    }




    //  Search by message ID

    public String searchByMessageID(String messageID) {
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].getMessageID().equals(messageID)) {
                return "Recipient: " + sentMessages[i].getRecipientNumber() +
                        " | Message: " + sentMessages[i].getMessage();
            }
        }
        return "Message ID not found.";
    }



    //  Search by recipient

    public String searchByRecipient(String recipientNumber) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < sentCount; i++) {
            Message m = sentMessages[i];
            if (m.getRecipientNumber().equals(recipientNumber)) {
                sb.append("Message ID: ").append(m.getMessageID())
                        .append(" | Message Hash: ").append(m.getMessageHash())
                        .append(" | Message: ").append(m.getMessage())
                        .append("\n");
            }
        }

        if (sb.length() == 0) return "No messages for that recipient.";
        return sb.toString();
    }




    //  Delete by hash

    public String deleteByHash(String hash) {
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].getMessageHash().equalsIgnoreCase(hash)) {

                Message removed = sentMessages[i];

                // SHIFT ELEMENTS LEFT
                for (int j = i; j < sentCount - 1; j++) {
                    sentMessages[j] = sentMessages[j + 1];
                }

                sentMessages[--sentCount] = null;

                return "Message \"" + removed.getMessage() + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }




    //  Display full report

    public String displayReport() {
        if (sentCount == 0) return "No sent messages.";

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < sentCount; i++) {
            Message m = sentMessages[i];
            sb.append("Message ID: ").append(m.getMessageID()).append("\n")
                    .append("Message Hash: ").append(m.getMessageHash()).append("\n")
                    .append("Recipient: ").append(m.getRecipientNumber()).append("\n")
                    .append("Message: ").append(m.getMessage()).append("\n")
                    .append("---------------------------------------\n");
        }

        sb.append("Total Messages Sent So Far: ")
                .append(Message.returnTotalMessages()).append("\n");

        return sb.toString();
    }




    // Load stored messages (JSON line-by-line)

    public void loadStoredMessagesFromJson(String filename) {
        File f = new File(filename);
        if (!f.exists()) return;

        Gson gson = new Gson();

        try (FileReader fr = new FileReader(f);
             java.io.BufferedReader br = new java.io.BufferedReader(fr)) {

            String line;
            while ((line = br.readLine()) != null) {

                if (!line.trim().isEmpty()) {
                    Message m = gson.fromJson(line, Message.class);

                    if (m != null) {
                        if (storedCount == storedMessages.length)
                            storedMessages = expand(storedMessages);

                        storedMessages[storedCount++] = m;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // append JSON

    private void appendMessageToJson(Message m, String filename) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(gson.toJson(m));
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    // TEST so that junit may access

    public Message[] getSentMessages() {
        return sentMessages;
    }

    public int getSentCount() {
        return sentCount;
    }

    public Message[] getStoredMessages() {
        return storedMessages;
    }

    public int getStoredCount() {
        return storedCount;
    }
}
