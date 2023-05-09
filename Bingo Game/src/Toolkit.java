import java.util.Scanner;

public class Toolkit {
  private static final Scanner stdIn = new Scanner(System.in);

  public static final String GOODBYEMESSAGE = "Thank you for playing";

  public static String getInputForMessage(String message) {
    System.out.println(message);
    return stdIn.nextLine().trim();
    //.trim(); //built-in method to trim leading and trailing white spaces of a String
  }

  public static String printArray(String[] array) { // prints bingo card numbers
    StringBuilder sb = new StringBuilder();
    /* TODO
        create a loop to print the numbers out once a user has inputted the BingoCard numbers,
        separated by commas (trim leading / trailing spaces)
        check the expected output here to ensure that it appears as it should
        return as a sb.toString()
   */
    // Enter 16 numbers for your card (separated by ' ')
    //You entered
    //1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16
    // we get all the numbers from the user - they're put into an array
    // now we want to print this array of numbers as a string - use sb
    for(int i = 0; i < array.length; i++){
      sb.append(array[i]); // append each character of the array
      if (i < array.length - 1){
        sb.append(", ");
      }
    }
    return sb.toString(); //return object as String
  }
}
