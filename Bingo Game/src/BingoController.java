import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class BingoController {

    private final String[] mainMenuItems = {"Exit",
            "Play bingo",
            "Set number separator",
            "Create a bingo card",
            "List existing cards",
            "Set bingo card size"};

    // complete constants attached to mainMenuItems
    private final String OPTION_EXIT = "0";
    private final String OPTION_PLAY = "1";
    private final String OPTION_SEPARATOR = "2";
    private final String OPTION_CREATE_CARD = "3";
    private final String OPTION_LIST_CARDS = "4";
    private final String OPTION_SIZE = "5";

    /* complete default size of rows / columns as specified in the Defaults class
          create an arraylist of BingoCard cards
          include getters and setters for row / column sizes */
    private int currentRowSize = Defaults.DEFAULT_NUMBER_OF_ROWS;
    private int currentColumnSize = Defaults.DEFAULT_NUMBER_OF_COLUMNS;;

    // create an ArrayList of BingoCard cards
    ArrayList<BingoCard> bingoCards = new ArrayList<>();

    // implement getters and setters for currentRowSize / currentColumnSize
    public int getCurrentRowSize() { return currentRowSize; }

    public void setCurrentRowSize(int currentRowSize) { this.currentRowSize = currentRowSize; }

    public int getCurrentColumnSize() { return currentColumnSize;}

    public void setCurrentColumnSize(int currentColumnSize) {this.currentColumnSize = currentColumnSize; }

    public void addNewCard(BingoCard card) {
        bingoCards.add(card); //Enter the number of columns for the card
    }

    public void setSize() {
        setCurrentRowSize(parseInt(Toolkit.getInputForMessage("Enter the number of rows for the card")));
        setCurrentColumnSize(parseInt(Toolkit.getInputForMessage("Enter the number of columns for the card")));
        System.out.printf("The bingo card size is set to %d rows X %d columns%n",
                getCurrentRowSize(), getCurrentColumnSize());
    } //include an appropriate message to get the number of rows as well as the number of columns

    //ensure that the correct amount of numbers are entered
    // print a message that shows the numbers entered using Toolkit.printArray(numbers)
    //create, setCardNumbers and add the card as a BingoCard
    public void createCard() {
        //calculate how many numbers are required to be entered based on the number or rows / columns
        //int numbersRequired = 0; change from 0 to something else
        int numbersRequired = getCurrentRowSize() * getCurrentColumnSize(); // numbers required by user is rown x column

        String[] numbers;

        boolean correctAmountOfNumbersEntered;

        do {
            numbers = Toolkit.getInputForMessage(String.format("Enter %d numbers for your card (separated by " + "'%s')",
                    numbersRequired, Defaults.getNumberSeparator())).trim().split(Defaults.getNumberSeparator());
        // verify if the correctAmountOfNumbersEntered is true or false dependant on the numbersRequired calculation
        // means after this to do, look down to next variable
            //correctAmountOfNumbersEntered = false; //changes according to calculation inserted here
            correctAmountOfNumbersEntered = numbers.length == numbersRequired;
// verify whether the numbers entered is not correct by printing an appropriate message
// verify against the expected output files
            if(!correctAmountOfNumbersEntered) {
                System.out.println(String.format("Try again: you entered %d numbers instead of %d", numbers.length, numbersRequired));
            }
        } while (!correctAmountOfNumbersEntered); // exit loop if this condition has not been met

        //print an appropriate message using ToolKit.printArray() to show the numbers entered
        System.out.printf("You entered %n");
        System.out.println(Toolkit.printArray(numbers));

        //create new BingoCard
        BingoCard card = new BingoCard(getCurrentRowSize(), getCurrentColumnSize());

        //setCardNumbers for the new card
        card.setCardNumbers(numbers);

        // add the card to the ArrayList
        addNewCard(card);
    }

    /*
         this method goes with printCardAsGrid() seen below
         when option 4 is chosen to list existing cards it prints each card accordingly
         for example, it should show the following
         Card 0 numbers:
         1  2
         3  4 (check with expected output files)
    */
    public void listCards() {
        //insert code here to find all cards to be printed accordingly
        //call printCardAsGrid() method here, Hint: use getCardNumbers() when getting cards
        for (int i = 0; i < bingoCards.size(); i++) { //using .size to go through whole arraylist
            System.out.printf("Card %d numbers: ", i); // using i as only variable here to use!
            //printCardAsGrid(bingoCards.get(i).getCardNumbers());
            printCardAsGrid(bingoCards.get(i).getCardNumbers());
        }
    }


    /*
          this is for option 4, list existing cards where all the cards are printed as a grid
          of rows / columns, so numbers 3 4 5 6 will be printed as follows:
          3  4
          5  6
          it is a follow on method from listCards() and ensures that the grid structure is printed
     */
    public void printCardAsGrid(String numbers) { //passes in a string of numbers
        String [] arr = numbers.split(" "); //split at white space
        StringBuilder sb = new StringBuilder();
        int count = 1;

        for(int i = 0; i < arr.length; i++){
            if (arr[i].length() < 2) { // if the value of the number is less than 2 digits (.length())
                sb.append(" "); // add a space
            }
            sb.append(String.format("%s", arr[i])); // what this
            // if not at end of row - add a space
            // if end of row, but not final of card, add new line
            if (count % getCurrentColumnSize() != 0) {
                sb.append(Defaults.getNumberSeparator()); // adding space
            } else if (count != arr.length) {
                sb.append("\n"); // adding new line
            }
            count++; //increment counter
        } // see other codes too
        System.out.println(sb);
    } //insert code here to print numbers as a grid

    public void setSeparator() {
        String sep = Toolkit.getInputForMessage("Enter the new separator");
        Defaults.setNumberSeparator(sep);
        //make use of setNumberSeparator() and getNumberSeparator()
        System.out.printf("Separator is '%s", Defaults.getNumberSeparator()); // note use of get and set here
    } //use Toolkit.getInputForMessage to enter a new separator print a message what the new separator is

    //reset all BingoCards using resetMarked (to false)
    public void resetAllCards() {
        for (BingoCard card : bingoCards) {
            card.resetMarked(); // for all cards in bingoCards array, reset
        }//reset all BingoCards using resetMarked (to false)
    } // marked to false in other BC class
    //enhanced for loop - for each (BingoCard class) card in array list cards

    public void markNumbers(int number) {
        for (int i = 0; i < bingoCards.size(); i++) {
            System.out.printf(String.format("Checking card %d for %d", i, number));
            bingoCards.get(i).markNumber(number); // calling markNumber for BC class
        }
    } //mark off a number that was called when it equals one of the numbers on the BingoCard

    public int getWinnerId() {
        for (int i = 0; i < bingoCards.size(); i++){
            if (bingoCards.get(i).isWinner()) {
                return i;
            }
        }
        return -1;
    } //make use of isWinner() to determine who the winner is the method should return the index of who the winner is

    /* TODO
          please take note that the game will not end until there is a winning sequence
     */
    public void play() {
        System.out.println("Eyes down, look in!");
        resetAllCards();

        boolean weHaveAWinner;

        do {
            markNumbers(parseInt(Toolkit.getInputForMessage("Enter the next number").trim()));
            int winnerID = getWinnerId();
            weHaveAWinner = winnerID != Defaults.NO_WINNER;
            if (weHaveAWinner)
                System.out.printf("And the winner is card %d%n", winnerID);
        } while (!weHaveAWinner);
    }

    public String getMenu(String[] menuItems) {
        // from the ExpectedOutput files menuText is returned
        StringBuilder menuText = new StringBuilder();
        for (int i = 0; i < menuItems.length; i++) {
            menuText.append(String.format(" %d: %s\n", i, menuItems[i])); //i are numbers 0 to 5, Items are the 5 strings
        } // Sam's good too
        return menuText.toString();
    } //change this method so it prints a proper numbered menu analyse the correct format

    //complete the menu using switch to call the appropriate method calls
    public void run() {
        boolean finished = false;
        do {
            switch (Toolkit.getInputForMessage(getMenu(mainMenuItems))) {
                case OPTION_EXIT:
                    finished = true;
                    break;
                case OPTION_PLAY:
                    play();
                    break;
                case OPTION_SEPARATOR:
                    setSeparator();
                    break;
                case OPTION_CREATE_CARD:
                    createCard();
                    break;
                case OPTION_LIST_CARDS:
                    listCards();
                    break;
                case OPTION_SIZE:
                    setSize();
                    break;
            }
        } while (!finished);
    }
}
