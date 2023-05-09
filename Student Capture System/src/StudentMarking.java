import java.util.Scanner;
// DO NOT import anything else

/**
 * This class forms Java Assignment 1, 2021
 */
public class StudentMarking {

    /**
     * The message that the main menu must display --- DO NOT CHANGE THIS
     */
    public final String MENU_TEMPLATE =
            "%nWelcome to the Student System. Please enter an option 0 to 3%n"
                    + "0. Exit%n"
                    + "1. Generate a student ID%n"
                    + "2. Capture marks for students%n"
                    + "3. List student IDs and average mark%n";
    /**
     * DO NOT CHANGE THIS
     */
    public final String NOT_FOUND_TEMPLATE =
            "No student id of %s exists";
   /* Do NOT change the two templates ABOVE this comment.
      DO CHANGE the templates BELOW.
   */
    // TODO (All questions) - Complete these templates which will be used throughout the program
    public final String ENTER_MARK_TEMPLATE = "Please enter mark %d for student %s%n";
    public final String STUDENT_ID_TEMPLATE = "Your studID is %s%n";
    public final String NAME_RESPONSE_TEMPLATE = "You entered a given name of %s and a surname of %s%n";
    public final String LOW_HIGH_TEMPLATE = "Student %s has a lowest mark of %d%n A highest mark of %d%n";
    public final String AVG_MARKS_TEMPLATE = "Student ***%s*** has an average of %.2f%n";
    public final String COLUMN_1_TEMPLATE = "   *%n";
    public final String COLUMN_2_TEMPLATE = "   *           *%n";
    public final String CHART_KEY_TEMPLATE = "Highest     Lowest%n   %d           %d%n";
    public final String REPORT_PER_STUD_TEMPLATE = "| Student ID   %d is %s | Average is  %.2f |%n";

    /**
     * Creates a student ID based on user input
     *
     * @param sc Scanner reading {@link System#in} re-used from {@link StudentMarking#main(String[])}
     * @return a studentID according to the pattern specified in {@link StudentMarking#STUDENT_ID_TEMPLATE}
     */
    public String generateStudId(Scanner sc) {
        // TODO (3.4) - Complete the generateStudId method which will allow a user to generate a student id
        //String studId = "generateStudId is incomplete"; // TODO Don't have unnecessary initialisations
        String studId;
        String firstName;
        String secondName;

        System.out.printf("Please enter your given name and surname (Enter 0 to return to main menu)%n");
        sc.nextLine();
        String studentName = String.valueOf(sc.nextLine());

        if (studentName.equals("0")){
            return null;
        }
// \\s - matches single whitespace character
// \\s+ - matches sequence of one or more whitespace characters.
        String[] splitString = studentName.trim().split("\\s");

        firstName = splitString[0];
        secondName = splitString[1];

        int firstNameLength = firstName.length();
        int secondNameLength = secondName.length();
        String secondNameLength2; // second string for second name to add '0' to length if its single digits

        if (secondNameLength <= 9) {
            secondNameLength2 = "0" + secondNameLength;
        }
        else {
            secondNameLength2 = secondNameLength + "";
        } // (my way was better)

        int middleOfFirstName = firstNameLength / 2;
        int middleOfSecondName = secondNameLength / 2;

        studId = "" + Character.toUpperCase(firstName.charAt(0)) + Character.toUpperCase(secondName.charAt(0)) +
                secondNameLength2 + firstName.charAt(middleOfFirstName) + secondName.charAt(middleOfSecondName);

        System.out.printf(NAME_RESPONSE_TEMPLATE, firstName, secondName);
        System.out.printf(STUDENT_ID_TEMPLATE, studId); // this was correct
        return studId;
    }

    /**
     * Reads three marks (restricted to a floor and ceiling) for a student and returns their mean
     *
     * @param sc     Scanner reading {@link System#in} re-used from {@link StudentMarking#main(String[])}
     * @param studId a well-formed ID created by {@link StudentMarking#generateStudId(Scanner)}
     * @return the mean of the three marks entered for the student
     */
    public double captureMarks(Scanner sc, String studId) {
        // TODO (3.5) - Complete the captureMarks method which will allow a user to input three mark for a chosen student
        // DO NOT change MAX_MARK and MIN_MARK
        final int MAX_MARK = 100;
        final int MIN_MARK = 0;
        int mark1, mark2, mark3;
        String yesOrNo;

        while (true) {
            System.out.printf(ENTER_MARK_TEMPLATE, 1, studId);
            mark1 = sc.nextInt();
            if (mark1 < MIN_MARK || mark1 > MAX_MARK) { //continue if user input more than 0 and less than 100
                continue;
            }
            break;
        }
        while (true) {
            System.out.printf(ENTER_MARK_TEMPLATE, 2, studId);
            mark2 = sc.nextInt();
            if (mark2 < MIN_MARK || mark2 > MAX_MARK) { //continue if user input more than 0 and less than 100
                continue;
            }
            break;
        }
        while (true) {
            System.out.printf(ENTER_MARK_TEMPLATE, 3, studId);
            mark3 = sc.nextInt();
            if (mark3 < MIN_MARK || mark3 > MAX_MARK) { //continue if user input more than 0 and less than 100
                continue;
            }
            break;
        }
        int max = Math.max(mark1, Math.max(mark2, mark3)); // how does .math work
        int min = Math.min(mark1, Math.min(mark2, mark3));
        double avg = (mark1 + mark2 + mark3) / 3.0; //average of three input marks

        System.out.printf(LOW_HIGH_TEMPLATE, studId, min, max);
        System.out.printf(AVG_MARKS_TEMPLATE, studId, avg);

        //double avg = Double.MIN_VALUE; // TODO Don't have unnecessary initialisations
        // think it meant don't use this in the end
        while (true) { //why's this in a loop? - see exp output
            System.out.printf("Would you like to print a bar chart? [y/n]%n");
            yesOrNo = sc.nextLine();
            if (yesOrNo.equals("y")) {
                printBarChart(studId, max, min); //studId int int
                return avg;
            }
            if (yesOrNo.equals("n")) {
                return avg; // if no simply return the average of marks
            } else {
                System.out.printf("That input is incorrect%n"); // check why printing
                continue; }
        }
    }

    /**
     * outputs a simple character-based vertical bar chart with 2 columns
     *
     * @param studId a well-formed ID created by {@link StudentMarking#generateStudId(Scanner)}
     * @param high   a student's highest mark
     * @param low    a student's lowest mark
     */
    public void printBarChart(String studId, int high, int low) {
        // TODO (3.6) - Complete the printBarChart method which will print a bar chart of the highest and lowest results of a student
        int lcv = 0; // loop control variable?
        System.out.printf("Student id statistics: %s%n", studId);

        // printBarChart(studId, max, min); //studId int int call in captureMarks()
        while (lcv < high-low)
        {
            System.out.printf(COLUMN_1_TEMPLATE); //"   *%n"
            lcv = lcv + 1;
        }
        lcv = 0;
        while (lcv < low) {
            System.out.printf(COLUMN_2_TEMPLATE); //"   *           *%n"
            lcv = lcv + 1;
        }
        System.out.printf(CHART_KEY_TEMPLATE, high, low);
        // "Highest     Lowest%n   %d           %d%n"
    }


    /**
     * Prints a specially formatted report, one line per student
     *
     * @param studList student IDs originally generated by {@link StudentMarking#generateStudId(Scanner)}
     * @param count    the total number of students in the system
     * @param avgArray mean (average) marks
     */
    public void reportPerStud(String[] studList,
                              int count,
                              double[] avgArray) { //pass in array, int, array
        // TODO (3.7) - Complete the reportPerStud method which will print all student IDs and average marks
        int lcv = 0;
        while (lcv < count) {
            System.out.printf(REPORT_PER_STUD_TEMPLATE, lcv + 1, studList[lcv], avgArray[lcv]);
            lcv = lcv + 1; // don't get this bit
        }
        // "| Student ID   %d is %s | Average is  %.2f |%n"
        // | Student ID   1 is TW04tl | Average is  3.33 |
        // | Student ID   2 is LH08wl | Average is  6.00 |
        // loop through array of students inputted to print to console (each on new line)
        // - studList will start from one, count not used doesn't need to
        // avgAvg is array - start from 0 and amounts added and averaged
        /* case 3:
                    sm.reportPerStud(keepStudId, studentAmount, avgArray);
                    // List student IDs and average mark - relating to arrays above
                    // reportPerStud(String[] studList, int count, double[] avgArray) */
    }


    /**
     * The main menu
     */
    public void displayMenu() {
        // TODO (3.2) - Complete the displayMenu method to use the appropriate template with printf
        System.out.printf(MENU_TEMPLATE);
    }

    /**
     * The controlling logic of the program. Creates and re-uses a {@link Scanner} that reads from {@link System#in}.
     *
     * @param args Command-line parameters (ignored)
     */
    public static void main(String[] args) {
        // TODO (3.3) - Complete the main method to give the program its core

        // DO NOT change sc, sm, EXIT_CODE, and MAX_STUDENTS
        Scanner sc = new Scanner(System.in);
        StudentMarking sm = new StudentMarking();
        final int EXIT_CODE = 0; //this needed to be used - on outside
        final int MAX_STUDENTS = 5;

        // TODO Initialise these
        String[] keepStudId = new String[MAX_STUDENTS]; // an array of size 5
        double[] avgArray = new double[MAX_STUDENTS]; // an array of size 5

        int studentAmount = 0;
        int selection;

        // TODO Build a loop around displaying the menu and reading then processing input
        do {
            sm.displayMenu(); //menu won't be displayed if press 0
            selection = sc.nextInt(); // scanner answer is saved to int variable 'selection'

            switch (selection){
                case 0:
                    System.out.printf("Goodbye%n"); //end
                    break;
                case 1: // generate a student ID on menu
                    if(studentAmount != MAX_STUDENTS) { keepStudId[studentAmount] = sm.generateStudId(sc);
                        // if statement runs (if student amount is not max students (5))
                        /* output of generateStudId returned to keepStudId array
                        with variable studentAmount used as index (counterweight) */
                        studentAmount++; // increment student amount to keep in time
                        break;
                    }  else {
                        System.out.printf("You cannot add any more students to the array");
                        continue;
                    }
                case 2:
                    System.out.printf(
                            "Please enter the studId to capture their marks (Enter 0 to return to main menu)%n");
                    sc.nextLine();
                    String verifyId = sc.nextLine();
                    boolean found = false; // boolean called found (set to false)
                    if (verifyId.equals("0")) // if statement to re-loop menu if 0 entered? why needed - check
                    { continue; }
                    for (int i = 0; i < keepStudId.length; i++) {
                        if (verifyId.equals(keepStudId[i])) {
                            avgArray[i] = sm.captureMarks(sc, verifyId); /* then call captureMarks method (passing scanner and
                            ID (verifyId)) and save returned average to avgArray at index position */
                            //capture marks has captureMarks(Scanner sc, String studId)
                            found = true;
                        } // if a match between sc input and keepStudId array is found...
                    } // search through entries in keepStudId array
                    if (!found) { // what does boolean bit do - watch youtube vide or ask
                        System.out.printf(sm.NOT_FOUND_TEMPLATE, verifyId); } //String NOT_FOUND_TEMPLATE = "No student id of %s exists";
                    break; // note using sm.NOT FOUND
                case 3:
                    sm.reportPerStud(keepStudId, studentAmount, avgArray);
                    // List student IDs and average mark - relating to arrays above
                    // reportPerStud(String[] studList, int count, double[] avgArray)
                    break;
                default:
                    System.out.printf(
                            "You have entered an invalid option. Enter 0, 1, 2 or 3%n");
            }
        } while(selection != EXIT_CODE); // a do while loop
        //keeps doing the loop inside until user input is 0
    }
}

/*
    TODO Before you submit:
         1. ensure your code compiles
         2. ensure your code does not print anything it is not supposed to
         3. ensure your code has not changed any of the class or method signatures from the skeleton code
         4. check the Problems tab for the specific types of problems listed in the assignment document
         5. reformat your code: Code > Reformat Code
         6. ensure your code still compiles (yes, again)
 */