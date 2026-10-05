import java.util.*;

public class StudentTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();

        String[] names = new String[n];
        double[] marks = new double[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter student " + (i + 1) + " name: ");
            names[i] = sc.nextLine();

            System.out.print("Enter marks: ");
            marks[i] = sc.nextDouble();
            sc.nextLine();
        }

        double total = 0;
        double highest = marks[0];
        double lowest = marks[0];

        for (int i = 0; i < n; i++) {
            total += marks[i];

            if (marks[i] > highest)
                highest = marks[i];

            if (marks[i] < lowest)
                lowest = marks[i];
        }

        double average = total / n;

        System.out.println("\n--- Student Grade Report ---");

        for (int i = 0; i < n; i++) {
            String grade;

            if (marks[i] >= 90)
                grade = "A+";
            else if (marks[i] >= 80)
                grade = "A";
            else if (marks[i] >= 70)
                grade = "B";
            else if (marks[i] >= 60)
                grade = "C";
            else if (marks[i] >= 50)
                grade = "D";
            else
                grade = "F";

            System.out.println(
                names[i] + " - Marks: " + marks[i] + " - Grade: " + grade
            );
        }

        System.out.println("\nAverage Marks: " + average);
        System.out.println("Highest Marks: " + highest);
        System.out.println("Lowest Marks: " + lowest);

        sc.close();
    }
}