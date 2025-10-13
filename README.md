Project Section: Part 2 – Message Creation, Validation, and Testing

 Overview

This part of the QuickChat application focuses on building a message management system that allows a registered user to:

Create and send messages,

Validate the message’s length and recipient number,

Automatically generate a unique Message ID and a Message Hash, and

Store and display all messages that have been sent or saved.

The program also includes JUnit tests to ensure that core features like message validation and hashing work correctly.

 Key Classes and Their Responsibilities
 
 Message.java

The Message class encapsulates all logic related to creating and processing a message.

Main Attributes
Variable	Description
recipientNumber	Stores the destination cellphone number (must start with +27).
message	Holds the content text of the message.
messageID	Randomly generated alphanumeric ID used to identify the message.
messageHash	A short hash that summarizes message details.
sentMessages	Static list that stores all sent messages for display.
totalMessages	Tracks the total number of messages successfully sent.
Main Methods
Method	Function
validateMessageLength()	Ensures the message does not exceed 250 characters. Returns a custom success/failure message.
checkRecipientCell(String cellNumber)	Checks that the cell number starts with +27 and is 12 characters long.
generateMessageID()	Generates a random 8-character alphanumeric ID.
createMessageHash()	Automatically generates a hash in the format NN:0:FIRSTLAST where:
– NN = first two digits of the message ID
– 0 = message number
– FIRST and LAST are the first and last words of the message in uppercase.
sendMessage(String option)	Executes user choices (Send, Store, or Disregard) and returns appropriate feedback.
printMessages()	Displays all sent messages in an easy-to-read list.
storeMessageJSON()	Saves a message object into a local JSON file (stored_messages.json) using the Gson library.


 Main.java

The Main class handles user interaction through JOptionPane dialogs.

Features

User Registration & Login
Users register with their details and must log in before sending messages.

Message Menu

Send a New Message

Show Recently Sent Messages

Quit Application

Message Creation Flow

User enters recipient and message.

System validates both fields.

If valid, system displays the message details:
ID, Hash, Recipient, Message Text.

User chooses whether to Send, Store, or Disregard the message.

Data Storage

Messages sent are stored temporarily in memory (ArrayList).

Stored messages are also written to a JSON file for persistence.

 MessageTest.java

The test class validates message logic using JUnit 5 assertions.

Unit Tests Implemented
Test Name	Description	Expected Outcome
testMessageHashGeneration()	Confirms the generated hash follows the correct format.	 Passes if hash matches pattern NN:0:FIRSTLAST.
testValidateMessageLength()	Ensures the message respects the 250-character rule.	 “Message ready to send.” or failure message with exceeded count.
testCheckRecipientCell()	Verifies correct cellphone number format.	 “Cell phone number successfully captured.” or failure message.
Example Output

When the user sends a message successfully, the system might display:

Message Details:
ID: AB12CD34
To: +27718693002
Msg: Hi Mike, can you join us for dinner tonight
Hash: 12:0:HITONIGHT
Message successfully sent.


And in the “Show Recently Sent Messages” view:

Sent Messages:
ID: AB12CD34 | To: +27718693002 | Msg: Hi Mike, can you join us for dinner tonight | Hash: 12:0:HITONIGHT
Total messages sent: 1

 Technologies Used
Tool	Purpose
Java 17+	Main programming language.
IntelliJ IDEA Community Edition 2025.2	IDE for development.
JUnit 5	Framework for writing and automating unit tests.
Gson 2.10.1	Library for converting Java objects to/from JSON.
Swing (JOptionPane)	GUI dialogs for user input and output.
 Continuous Integration (CI)

The project can be linked to GitHub Actions for automated testing:

Every time code is committed, GitHub Actions can automatically compile and run all JUnit tests.

This ensures consistent quality and prevents regression errors during development.

 References

OpenAI. (2025). ChatGPT (GPT-5). Retrieved from https://openai.com/

Google. (2025). Gson – Java JSON Library. Retrieved from https://github.com/google/gson

Oracle. (n.d.). Class JOptionPane – Java Platform SE Documentation. Retrieved from https://docs.oracle.com/javase/8/docs/api/javax/swing/JOptionPane.html

JetBrains. (2025). IntelliJ IDEA Documentation. Retrieved from https://www.jetbrains.com/idea/docs/

Stack Overflow Community. (2025). Discussions on Java OOP and JSON Handling. Retrieved from https://stackoverflow.com/
