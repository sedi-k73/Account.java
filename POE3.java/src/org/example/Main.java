// LESEDI KHUMOLETLOTLO MOREKU , ST10484098
// With guidance from: OpenAI. (2025). ChatGPT (GPT-5). Retrieved from https://openai.com/
// IntelliJ IDEA Documentation – JetBrains. Available at: https://www.jetbrains.com/idea/docs/

// MY UPGRADED MAIN****

package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        //  USER REGISTRATION SECTION
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

        // LOGIN PROCESS
        JOptionPane.showMessageDialog(null, "********** LOGIN USER **********");
        String loginUsername = JOptionPane.showInputDialog("Enter Username you registered with:");
        String loginPassword = JOptionPane.showInputDialog("Enter Password you registered with:");

        if (!account.loginUser(loginUsername, loginPassword)) {
            JOptionPane.showMessageDialog(null, "Username or Password incorrect. Please try again.");
            System.exit(0);
        }

        JOptionPane.showMessageDialog(null, "Welcome " + firstName + " " + lastName + "!\nWelcome to QuickChat.");

        //  PART 3 ADDITION: Create manager instance
        MessageManager manager = new MessageManager();
        manager.loadStoredMessagesFromJson("stored_messages.json"); // load if file exists

        boolean running = true;

        //  MAIN MENU LOOP
        while (running) {
            String menuInput = JOptionPane.showInputDialog("""
                    Choose an option:
                    1) Send a New Message
                    2) Show Message Reports / Search
                    3) Quit""");

            if (menuInput == null) break;

            int menuChoice;
            try {
                menuChoice = Integer.parseInt(menuInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numeric choice.");
                continue;
            }

            switch (menuChoice) {
                case 1 -> {
                    //  SEND MESSAGES
                    int totalMessages;
                    try {
                        totalMessages = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid number.");
                        continue;
                    }

                    int sentCount = 0;

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

                        //  Display message details with correct ID and count
                        JOptionPane.showMessageDialog(null, """
                                Message Details:
                                Message ID: %s
                                Message Hash: %s
                                Recipient: %s
                                Message: %s
                                Total Messages Sent So Far: %d
                                """.formatted(
                                msg.getMessageID(),
                                msg.getMessageHash(),
                                recipient,
                                messageText,
                                Message.returnTotalMessages()
                        ), "Message Details", JOptionPane.INFORMATION_MESSAGE);

                        //  ACTION MENU
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

                        String optionResult;
                        switch (choice) {
                            case 1 -> {
                                optionResult = msg.sendMessage("Send");
                                manager.addSent(msg);
                                sentCount++;
                            }
                            case 2 -> {
                                optionResult = msg.sendMessage("Disregard");
                                manager.addDisregarded(msg);
                            }
                            case 3 -> {
                                optionResult = msg.sendMessage("Store");
                                manager.addStored(msg);
                            }
                            default -> optionResult = "Invalid option.";
                        }

                        JOptionPane.showMessageDialog(null, optionResult);
                    }

                    JOptionPane.showMessageDialog(null,
                            "You have sent a total of " + sentCount + " message(s).",
                            "Total Messages Sent",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                case 2 -> {
                    //  MESSAGE REPORT / SEARCH SUB-MENU
                    String subMenu = JOptionPane.showInputDialog("""
                            Message Report & Search Options:
                            1) Display sender and recipient of all sent messages
                            2) Display longest message
                            3) Search for a message by ID
                            4) Search messages sent to a recipient
                            5) Delete a message using hash
                            6) Display full sent-messages report
                            7) Back to main menu""");

                    if (subMenu == null) continue;

                    int subChoice;
                    try {
                        subChoice = Integer.parseInt(subMenu);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid input.");
                        continue;
                    }

                    String result = switch (subChoice) {
                        case 1 -> manager.displaySenderRecipient(firstName + " " + lastName);
                        case 2 -> manager.getLongestMessage();
                        case 3 -> {
                            String id = JOptionPane.showInputDialog("Enter Message ID to search:");
                            yield manager.searchByMessageID(id);
                        }
                        case 4 -> {
                            String rec = JOptionPane.showInputDialog("Enter recipient number (+27...):");
                            yield manager.searchByRecipient(rec);
                        }
                        case 5 -> {
                            String hash = JOptionPane.showInputDialog("Enter Message Hash to delete:");
                            yield manager.deleteByHash(hash);
                        }
                        case 6 -> manager.displayReport();
                        case 7 -> "Returning to main menu...";
                        default -> "Invalid option.";
                    };

                    JOptionPane.showMessageDialog(null, result);
                }

                case 3 -> {
                    running = false;
                    JOptionPane.showMessageDialog(null, "Goodbye " + firstName + "!");
                }

                default -> JOptionPane.showMessageDialog(null, "Invalid option, please try again.");
            }
        }
    }
    }// IntelliJ IDEA Documentation – JetBrains. Available at: https://www.jetbrains.com/idea/docs/



