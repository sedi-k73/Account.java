// LESEDI KHUMOLETLOTLO MOREKU , ST10484098
// With guidance from: OpenAI. (2025). ChatGPT (GPT-5). Retrieved from https://openai.com/
// Oracle. (n.d.). Class JOptionPane — Java Platform SE Documentation. Retrieved from:

//MY UPGRADED MAIN**
package org.example;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {

        // --- USER REGISTRATION SECTION ---
        String firstName = JOptionPane.showInputDialog("Please enter your First Name:");
        String lastName = JOptionPane.showInputDialog("Please enter your Last Name:");
        String Username = JOptionPane.showInputDialog("Please enter Username (must contain '_' and be <= 5 characters):");
        String Password = JOptionPane.showInputDialog("Please enter Password (must contain special character, uppercase, number, and be >= 8 characters):");
        String cellNumbers = JOptionPane.showInputDialog("Please enter your cell number (must start with +27 and be 12 characters):");

        Account account = new Account(Username, Password, cellNumbers, firstName, lastName);
        String registrationStatus = account.registerUser(Username, Password, cellNumbers);
        JOptionPane.showMessageDialog(null, registrationStatus);

        if (!registrationStatus.equals("User successfully registered.")) {
            JOptionPane.showMessageDialog(null, "Registration unsuccessful. Exiting program.");
            System.exit(0);
        }

        // --- LOGIN PROCESS ---
        JOptionPane.showMessageDialog(null, "********** LOGIN USER **********");
        String loginUsername = JOptionPane.showInputDialog("Enter Username you registered with:");
        String loginPassword = JOptionPane.showInputDialog("Enter Password you registered with:");

        if (!account.loginUser(loginUsername, loginPassword)) {
            JOptionPane.showMessageDialog(null, "Username or Password incorrect. Please try again.");
            System.exit(0);
        }

        JOptionPane.showMessageDialog(null, "Welcome " + firstName + " " + lastName + "!\nWelcome to QuickChat.");

        // --- MAIN MENU LOOP ---
        boolean running = true;

        while (running) {
            String menuInput = JOptionPane.showInputDialog("""
                    Choose an option:
                    1) Send a New Message
                    2) Show Recently Sent Messages
                    3) Quit""");

            if (menuInput == null) break; // user closed dialog

            int menuChoice;
            try {
                menuChoice = Integer.parseInt(menuInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numeric choice.");
                continue;
            }

            switch (menuChoice) {
                case 1 -> {
                    // --- SEND MESSAGES ---
                    int totalMessages;
                    try {
                        totalMessages = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid number.");
                        continue;
                    }

                    int sentCount = 0; // count successfully sent messages

                    for (int i = 0; i < totalMessages; i++) {
                        String recipient = JOptionPane.showInputDialog("Enter recipient number (must start with +27 and be 12 digits):");
                        String messageText = JOptionPane.showInputDialog("Enter message text (max 250 characters):");

                        Message msg = new Message(recipient, messageText, 1);

                        String lengthValidation = msg.validateMessageLength();
                        JOptionPane.showMessageDialog(null, lengthValidation);
                        if (!lengthValidation.equals("Message ready to send.")) continue;

                        if (!msg.checkRecipientCell(recipient)) {
                            JOptionPane.showMessageDialog(null, "Cell phone number is incorrectly formatted or does not contain an international code.\nPlease correct the number and try again.");
                            continue;
                        }

                        String hash = msg.createMessageHash();
                        String msgID = msg.getMessageID();

                        // ✅ Display full message details as per POE requirement #7
                        String messageDetails = """
                                Message Sent Details:
                                Message ID: %s
                                Message Hash: %s
                                Recipient: %s
                                Message: %s
                                """.formatted(msgID, hash, recipient, messageText);

                        JOptionPane.showMessageDialog(null, messageDetails, "Message Details", JOptionPane.INFORMATION_MESSAGE);

                        String choiceInput = JOptionPane.showInputDialog("""
                                Choose an option:
                                1) Send Message
                                2) Disregard Message
                                3) Store Message for Later""");

                        int choice;
                        try {
                            choice = Integer.parseInt(choiceInput);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Invalid selection, try again.");
                            continue;
                        }

                        String optionResult = switch (choice) {
                            case 1 -> msg.sendMessage("Send");
                            case 2 -> msg.sendMessage("Disregard");
                            case 3 -> msg.sendMessage("Store");
                            default -> "Invalid option.";
                        };

                        JOptionPane.showMessageDialog(null, optionResult);

                        if (choice == 1) sentCount++; // only count sent messages
                    }

                    //  Show total number of messages sent (requirement #6)
                    JOptionPane.showMessageDialog(null,
                            "You have sent a total of " + sentCount + " message(s).",
                            "Total Messages Sent",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                case 2 -> {
                    // --- SHOW SENT MESSAGES ---
                    JOptionPane.showMessageDialog(null, Message.printMessages() +
                            "\nTotal messages sent: " + Message.returnTotalMessages());
                }

                case 3 -> {
                    running = false;
                    JOptionPane.showMessageDialog(null, "Goodbye " + firstName + "!");
                }

                default -> JOptionPane.showMessageDialog(null, "Invalid option, please try again.");
            }
        }
    }
}
// IntelliJ IDEA Documentation – JetBrains. Available at: https://www.jetbrains.com/idea/docs/