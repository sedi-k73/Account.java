// LESEDI KHUMOLETLOTLO MOREKU , ST10484098

package org.example;

public class Account {
    private String Username;
    private String Password;
    private String cellNumbers;
    private String firstName;
    private String lastName;

    // Constructor
    public Account(String Username, String Password, String cellNumbers, String firstName, String lastName) {

        this.Username = Username;
        this.Password = Password;
        this.cellNumbers = cellNumbers;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    // get Methods

    //Static methods for Username
    public static boolean checkUserName(String Username) {

        return Username.contains("_") && Username.length() <= 5;
    }


    //Static methods for Password
    public static boolean checkPasswordComplexity(String Password) {
        boolean hasUpper = !Password.equals(Password.toLowerCase());
        boolean hasDigit = Password.matches(".*[0-9].*");
        boolean hasSpecialCharacters = Password.matches(".*[&*()^%$£,./<>{}@!¬';?#].*");
        boolean isLongEnough = Password.length() >= 8;
        return hasUpper && hasDigit && hasSpecialCharacters && isLongEnough;
    }


    //Static methods for cell Numbers
    public static boolean checkCellNumbers(String cellNumbers) {

        return cellNumbers.startsWith("+27") && cellNumbers.length() == 12;
    }


    //instance methods for Registration Message
    public String registerUser(String Username , String Password , String cellNumbers) {

        //  Make sure we use StringBuilder
        StringBuilder errorMsg = new StringBuilder();

        if (checkUserName( Username) == false) {
            errorMsg.append("Username is not correctly formatted. Please ensure that the username contains an underscore and is no more than five characters in length.")
                    .append(System.lineSeparator());
        }

        if (checkPasswordComplexity(Password)== false) {
            errorMsg.append("Password is not correctly formatted. Please ensure your password contains at least 8 characters, an uppercase letter, a number, and a special character.")
                    .append(System.lineSeparator());
        }

        if (checkCellNumbers(cellNumbers) == false) {
            errorMsg.append("Cell numbers incorrectly formatted or does not contain the international code please write the correct number or try again")
                    .append(System.lineSeparator());
        }

        if (errorMsg.length() > 0) {                          // reference : JUnit 5 User Guide – JUnit Team. Available at: https://junit.org/junit5/docs/current/user-guide/

            return errorMsg.toString();  // return all errors with line breaks
        }

        return "User successfully registered.";
    }


    // static methods for Login Verification
    public Boolean loginUser (String inputUsername, String inputPassword ){
        return this.Username.equals(inputUsername) && this.Password.equals(inputPassword);
    }


    //instance methods for Login status Message
    public String returnLoginStatus (String inputUsername, String inputPassword){
        if (loginUser(inputUsername, inputPassword)) {
            return "Welcome User ! " + firstName + " " + lastName + " it is great to see you again. ";
        } else {
            return " Username or Password incorrect , Please try again. ";
        }
    }
}