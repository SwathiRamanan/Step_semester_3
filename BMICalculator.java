import java.util.*;

public class BMICalculator {

    static String getBmiStatus(double bmi) {

        if (bmi < 18.5) {
            return "Underweight";
        }
        else if (bmi < 25) {
            return "Normal";
        }
        else if (bmi < 30) {
            return "Overweight";
        }
        else {
            return "Obese";
        }
    }

    static void printWellnessReport(double[] heights, double[] weights) {

        System.out.println("\n------------------------------------------------");
        System.out.println("Person\tHeight(m)\tWeight(kg)\tBMI\tStatus");
        System.out.println("------------------------------------------------");

        for (int i = 0; i < heights.length; i++) {

            double bmi = weights[i] / (heights[i] * heights[i]);

            String status = getBmiStatus(bmi);

            System.out.printf("%d\t%.2f\t\t%.2f\t\t%.2f\t%s\n",
                    i + 1, heights[i], weights[i], bmi, status);
        }

        System.out.println("------------------------------------------------");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of people: ");
        int n = sc.nextInt();

        double[] heights = new double[n];
        double[] weights = new double[n];

        for (int i = 0; i < n; i++) {

            System.out.println("\nPerson " + (i + 1));

            System.out.print("Enter height in meters: ");
            heights[i] = sc.nextDouble();

            System.out.print("Enter weight in kg: ");
            weights[i] = sc.nextDouble();
        }

        printWellnessReport(heights, weights);

        sc.close();
    }
}