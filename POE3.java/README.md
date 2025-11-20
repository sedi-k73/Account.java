QuickChat — Part 3


Language: Java
Tools: IntelliJ, Maven, Gson, JUnit 5

 What This Part Does

Part 3 completes the QuickChat message system by adding:

 Array-Based Message Storage

Messages are stored using fixed arrays:

Sent

Stored

Disregarded

Counters are used to track how many messages each array holds.

 JSON Saving & Loading

Stored messages are saved to a JSON file using Gson.
The system now handles empty JSON files safely (no more errors).

 Search Features

You can now search messages by:

Message ID

Recipient phone number

 Longest Message

The system finds and returns the longest message out of all arrays.

 Delete by Message Hash

Messages can be removed using their unique hash value.

 Full Report

Displays a list of all sent messages neatly.

 JUnit 5 Testing

A full test suite checks:

Array population

Longest message

Searching

Deleting

Report output

All tests now run correctly.

 How to Run

Open the project in IntelliJ IDEA

Make sure Gson is added to your project

Run Main.java

Run tests with JUnit or mvn test

 References (Harvard)

Google 2022, Gson Library, GitHub, accessed 18 Nov 2025.
JUnit Team 2023, JUnit 5 Documentation, accessed 18 Nov 2025.
Oracle 2024, Java SE Docs, accessed 18 Nov 2025.
JetBrains 2025, IntelliJ IDEA, accessed 18 Nov 2025.
OpenAI 2025, ChatGPT 5.1, accessed 18 Nov 2025.

