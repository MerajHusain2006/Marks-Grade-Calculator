import java.util.*;
import java.io.*;

public class Marks_Grade {
    static Scanner sc = new Scanner(System.in);

    static String studentName;
    static int[] marks = new int[5];
    static boolean dataEntered = false;

    static String FILE_NAME = "student_data.txt";

    // 1. Enter Student Details
    
static void enterMarks() {

    System.out.print("Enter Student Name: ");
    String name = sc.nextLine();

    String[] inputMarks = new String[5];

    // Step 1: Take all 5 subject marks first
    for (int i = 0; i < 5; i++) {

        System.out.print("Enter Subject " + (i + 1) + " Marks: ");
        inputMarks[i] = sc.nextLine();
    }

    // Step 2: Validate marks after all inputs
    int[] newMarks = new int[5];

    for (int i = 0; i < 5; i++) {

        while (true) {

            try {

                int mark = Integer.parseInt(inputMarks[i]);

                if (mark >= 0 && mark <= 100) {

                    newMarks[i] = mark;
                    break;

                } else {

                    System.out.println("\nInvalid marks in Subject "
                            + (i + 1) + ": " + mark);

                    System.out.println(
                            "Please enter marks between 0 and 100.");

                }

            } catch (NumberFormatException e) {

                System.out.println("\nInvalid input in Subject "
                        + (i + 1));

                System.out.println("Please enter numbers only.");
            }

            System.out.print("Re-enter Subject " + (i + 1) + " Marks: ");
            inputMarks[i] = sc.nextLine();
        }
    }

    // Step 3: Store valid data
    studentName = name;
    marks = newMarks;
    dataEntered = true;

    System.out.println("\nMarks entered successfully!");
}

    // 2. Calculate Total
    static int calculateTotal() {

        int total = 0;

        for (int mark : marks) {
            total += mark;
        }

        return total;
    }

    // 3. Calculate Percentage
    static double calculatePercentage() {
        return calculateTotal() / 5.0;
    }

    // 4. Calculate Grade
    static String calculateGrade() {

        double percentage = calculatePercentage();

        if (percentage >= 90)
            return "A+";
        else if (percentage >= 75)
            return "A";
        else if (percentage >= 60)
            return "B";
        else if (percentage >= 40)
            return "C";
        else
            return "F";
    }

    // 5. Display Result
    static void displayResult() {

        if (!dataEntered) {
            System.out.println("Please enter marks first.");
            return;
        }

        System.out.println("\n========== STUDENT RESULT ==========");
        System.out.println("Student Name: " + studentName);
        System.out.println("Total Marks: " + calculateTotal() + "/500");
        System.out.printf("Percentage: %.1f%%\n", calculatePercentage());
        System.out.println("Grade: " + calculateGrade());
        System.out.println("====================================");
    }

    // 6. Find Highest and Lowest
    
   static void findHighestLowest() {

    if (!dataEntered) {
        System.out.println("Please enter marks first.");
        return;
    }

    int highest = marks[0];
    int lowest = marks[0];

    int highestSubject = 1;
    int lowestSubject = 1;

    for (int i = 1; i < 5; i++) {

        if (marks[i] > highest) {
            highest = marks[i];
            highestSubject = i + 1;
        }

        if (marks[i] < lowest) {
            lowest = marks[i];
            lowestSubject = i + 1;
        }
    }

    System.out.println("Highest: Subject " + highestSubject
            + " (" + highest + ")");

    System.out.println("Lowest: Subject " + lowestSubject
            + " (" + lowest + ")");
}


    // 7. Sort Marks
    static void sortMarks() {

        if (!dataEntered) {
            System.out.println("Please enter marks first.");
            return;
        }

        int[] sorted = marks.clone();

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4 - i; j++) {

                if (sorted[j] < sorted[j + 1]) {

                    int temp = sorted[j];
                    sorted[j] = sorted[j + 1];
                    sorted[j + 1] = temp;
                }
            }
        }

        System.out.println("Marks in High to Low Order:");

        for (int mark : sorted) {
            System.out.print(mark + " ");
        }

        System.out.println();
    }

    // 8. Search Marks
    static void searchMark() {

        if (!dataEntered) {
            System.out.println("Please enter marks first.");
            return;
        }

        System.out.print("Enter marks to search: ");

        try {

            int search = Integer.parseInt(sc.nextLine());
            boolean found = false;

            for (int i = 0; i < 5; i++) {

                if (marks[i] == search) {
                    System.out.println(search + " found in Subject " + (i + 1));
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Marks not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    // 9. Load Data From File
    static void loadFromFile() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (Scanner reader = new Scanner(file)) {

            String[] data = reader.nextLine().split(",");

            if (data.length != 6 || data[0].trim().isEmpty()) {
                System.out.println("Invalid file data.");
                return;
            }

            int[] loadedMarks = new int[5];

            for (int i = 0; i < 5; i++) {

                loadedMarks[i] = Integer.parseInt(data[i + 1]);

                if (loadedMarks[i] < 0 || loadedMarks[i] > 100) {
                    System.out.println("Invalid marks in file.");
                    return;
                }
            }

            studentName = data[0];
            marks = loadedMarks;
            dataEntered = true;

            System.out.println("Previous data loaded successfully.");

        } catch (Exception e) {
            System.out.println("Error loading data.");
        }
    }

    // 10. Save Data To File
    static void saveToFile() {

        if (!dataEntered) {
            return;
        }

        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {

            writer.print(studentName);

            for (int mark : marks) {
                writer.print("," + mark);
            }

            System.out.println("Data saved successfully.");

        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }

    // Main Method
    public static void main(String[] args) {

        loadFromFile();

        while (true) {

            System.out.println("\n===== MARKS & GRADE CALCULATOR =====");
            System.out.println("1. Enter Student Marks");
            System.out.println("2. Display Result");
            System.out.println("3. Find Highest & Lowest Marks");
            System.out.println("4. Sort Marks");
            System.out.println("5. Search Marks");
            System.out.println("6. Exit");

            System.out.print("\nEnter Your Choice: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice!");
                continue;
            }

            switch (choice) {

                case 1:
                    enterMarks();
                    break;

                case 2:
                    displayResult();
                    break;

                case 3:
                    findHighestLowest();
                    break;

                case 4:
                    sortMarks();
                    break;

                case 5:
                    searchMark();
                    break;

                case 6:
                    saveToFile();
                    System.out.println("Thank you for using the program!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Select 1 to 6.");
            }
        }
    }
}

