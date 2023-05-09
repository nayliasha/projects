import java.util.Arrays;

public class BingoCard {
  /*
    The two arrays are private and their structure is NEVER exposed to another
    class, which is why the getCardNumbers returns a String that needs
    further processing.

    While this is not computationally efficient, it is good programming
    practice to hide data structures (information hiding).
   */
  private int[][]     numbers;
  private boolean[][] markedOff;

  private int numberOfRows;
  private int numberOfColumns;

  public BingoCard(int numberOfRows, int numberOfColumns) {
    setNumberOfRows(numberOfRows);
    setNumberOfColumns(numberOfColumns);

    numbers   = new int[numberOfRows][numberOfColumns];
    markedOff = new boolean[numberOfRows][numberOfColumns];
    resetMarked();
  }

  public void resetMarked() {
    //Reset the data structure to be entirely false. Java defaults booleans to false so you can make use of that.
    markedOff = new boolean [getNumberOfRows()][getNumberOfColumns()];
    // clues: - set already called so use get - get method returns ints
    // new to RE- SET
    /*for (boolean[] booleans : markedOff) {
      Arrays.fill(booleans, false);
    }*/
  }

  public int getNumberOfRows() {
    return numberOfRows; }

  public void setNumberOfRows(int numberOfRows) { this.numberOfRows = numberOfRows; }

  public int getNumberOfColumns() {
    return numberOfColumns; }

  public void setNumberOfColumns(int numberOfColumns) { this.numberOfColumns = numberOfColumns; }

//flatten the numbers array into a single string with each number separated by spaces
// but no leading or trailing copies of
// that character: that is no spaces before the first number nor after the last number.
    // all the cards are stored as a grid ([][] numbers) of rows / columns, so for example, numbers 3 4 5 6 will be
    // printed as follows:
    //      3  4
    //      5  6
    // this meant flatten the numbers array and use sb all at once
  public String getCardNumbers() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < getNumberOfRows(); i++) {
      for (int j = 0; j < getNumberOfColumns(); j++) {
        sb.append(String.format("%d ", numbers[i][j]));
      }
    }
    return sb.toString();
  }
    //using nested loops and StringBuilder
    // iterates through the numbers inputted by the user to get the card numbers,
    // separates numbers with a space, uses number of rows and columns
    //return the grid as a string // returns string representation of the object


  public void setCardNumbers(String[] numbersAsStrings) {
//the array of strings [] numbersAsStrings is cast to an integer as [] numbersList, for you set the grid from this list

    int[] numbersList =
        Arrays.stream(numbersAsStrings).mapToInt(Integer::parseInt).toArray();
    //the goal of this method is to get the numbers entered into the [][] numbers format
    int count = 0; //counter begins at 0
    for (int i = 0; i < numberOfRows; i++){
      for (int j = 0; j < numberOfColumns; j++){
        numbers[i][j] = numbersList[count];
        count++; // pass in numbers from numbersList as 'count', increment count with each iteration of inner loop
      } // 1D array to 2D array!
    }
  }

  public void markNumber(int number) {
//make use of the [][] markedOff to mark off numbers from [][] numbers as they match
// if not matching an appropriate message must be printed, verify against expected output files
    boolean matchFound = false;
    for (int i = 0; i < getNumberOfRows(); i++){
      for (int j = 0; j < getNumberOfColumns(); i++){
        if (numbers[i][j] == number){ // if user's value is present in the row or column
          matchFound = true; //then the boolean isMarked is true
          markedOff[i][j] = true; //and the boolean markedOff is also true
        }
      }
    }
    if (matchFound){ //if isMarked is evaluated as true, then print the following
      System.out.printf("Marked off %d %n", number);
    } else {
      System.out.printf("Number %d not on this card %n", number);
    }
  } // method: marking off cards if number is presented using nested loops and boolean

  //method: checking if player is a winner by using nested loops and boolean
  public boolean isWinner() {
    //check if there is a winner or not all elements of [][] markedOff should be
    // marked off to have a winner (full house)
    for (int i = 0; i < getNumberOfRows(); i++) {
      for (int j = 0; j < getNumberOfColumns(); j++) {
        if (!markedOff[i][j]) {
          return false; // if markedOff is false, so if a single value is not marked off, instantly not a winner
        } // - so no need to look at rest of the cards
      }
    }
    //change return statement accordingly (either true or false)
    return true; //if all values are marked off, then return true and isWinner is true
  } // matty and sam both have good method too, more matty
}