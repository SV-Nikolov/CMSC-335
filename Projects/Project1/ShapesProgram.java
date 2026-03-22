/**
 * File: ShapesProgram.java
 * Author: Stefan Nikolov
 * Date: March 23, 2026
 * Purpose: Main driver program for the Java OO Shapes application
 */

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShapesProgram {
    private static Scanner scanner = new Scanner(System.in);
    
    /**
     * Main method that runs the shape menu program
     */
    public static void main(String[] args) {
        displayWelcome();
        
        boolean continueProgram = true;
        while (continueProgram) {
            displayMenu();
            int choice = getMenuChoice();
            
            switch (choice) {
                case 1:
                    handleCircle();
                    break;
                case 2:
                    handleRectangle();
                    break;
                case 3:
                    handleSquare();
                    break;
                case 4:
                    handleTriangle();
                    break;
                case 5:
                    handleSphere();
                    break;
                case 6:
                    handleCube();
                    break;
                case 7:
                    handleCone();
                    break;
                case 8:
                    handleCylinder();
                    break;
                case 9:
                    handleTorus();
                    break;
                case 10:
                    continueProgram = false;
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.\n");
                    continue;
            }
            
            if (choice != 10 && choice >= 1 && choice <= 9) {
                continueProgram = askContinue();
            }
        }
        
        displayExitMessage();
        scanner.close();
    }
    
    /**
     * Display welcome message
     */
    private static void displayWelcome() {
        System.out.println("==========================================");
        System.out.println("Welcome to the Java OO Shapes Program!");
        System.out.println("==========================================\n");
    }
    
    /**
     * Display the menu options
     */
    private static void displayMenu() {
        System.out.println("Select from the menu:");
        System.out.println("1. Construct a Circle");
        System.out.println("2. Construct a Rectangle");
        System.out.println("3. Construct a Square");
        System.out.println("4. Construct a Triangle");
        System.out.println("5. Construct a Sphere");
        System.out.println("6. Construct a Cube");
        System.out.println("7. Construct a Cone");
        System.out.println("8. Construct a Cylinder");
        System.out.println("9. Construct a Torus");
        System.out.println("10. Exit\n");
    }
    
    /**
     * Get menu choice from user with validation
     */
    private static int getMenuChoice() {
        System.out.print("Enter your choice (1-10): ");
        
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Invalid input. Please enter a number between 1 and 10: ");
        }
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }
    
    /**
     * Handle Circle construction
     */
    private static void handleCircle() {
        System.out.println("Creating a Circle...");
        try {
            double radius = getPositiveDouble("Enter the radius: ");
            Circle circle = new Circle(radius);
            System.out.printf("The area of the %s is %.2f\n\n", circle.getDescription(), circle.getArea());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * Handle Rectangle construction
     */
    private static void handleRectangle() {
        System.out.println("Creating a Rectangle...");
        try {
            double length = getPositiveDouble("Enter the length: ");
            double width = getPositiveDouble("Enter the width: ");
            Rectangle rectangle = new Rectangle(length, width);
            System.out.printf("The area of the %s is %.2f\n\n", rectangle.getDescription(), rectangle.getArea());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * Handle Square construction
     */
    private static void handleSquare() {
        System.out.println("Creating a Square...");
        try {
            double side = getPositiveDouble("Enter the side length: ");
            Square square = new Square(side);
            System.out.printf("The area of the %s is %.2f\n\n", square.getDescription(), square.getArea());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * Handle Triangle construction
     */
    private static void handleTriangle() {
        System.out.println("Creating a Triangle...");
        try {
            double base = getPositiveDouble("Enter the base: ");
            double height = getPositiveDouble("Enter the height: ");
            Triangle triangle = new Triangle(base, height);
            System.out.printf("The area of the %s is %.2f\n\n", triangle.getDescription(), triangle.getArea());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * Handle Sphere construction
     */
    private static void handleSphere() {
        System.out.println("Creating a Sphere...");
        try {
            double radius = getPositiveDouble("Enter the radius: ");
            Sphere sphere = new Sphere(radius);
            System.out.printf("The volume of the %s is %.2f\n\n", sphere.getDescription(), sphere.getVolume());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * Handle Cube construction
     */
    private static void handleCube() {
        System.out.println("Creating a Cube...");
        try {
            double side = getPositiveDouble("Enter the side length: ");
            Cube cube = new Cube(side);
            System.out.printf("The volume of the %s is %.2f\n\n", cube.getDescription(), cube.getVolume());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * Handle Cone construction
     */
    private static void handleCone() {
        System.out.println("Creating a Cone...");
        try {
            double radius = getPositiveDouble("Enter the radius: ");
            double height = getPositiveDouble("Enter the height: ");
            Cone cone = new Cone(radius, height);
            System.out.printf("The volume of the %s is %.2f\n\n", cone.getDescription(), cone.getVolume());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * Handle Cylinder construction
     */
    private static void handleCylinder() {
        System.out.println("Creating a Cylinder...");
        try {
            double radius = getPositiveDouble("Enter the radius: ");
            double height = getPositiveDouble("Enter the height: ");
            Cylinder cylinder = new Cylinder(radius, height);
            System.out.printf("The volume of the %s is %.2f\n\n", cylinder.getDescription(), cylinder.getVolume());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * Handle Torus construction
     */
    private static void handleTorus() {
        System.out.println("Creating a Torus...");
        try {
            double majorRadius = getPositiveDouble("Enter the major radius: ");
            double minorRadius = getPositiveDouble("Enter the minor radius: ");
            
            // Ensure major radius is larger than minor radius
            if (majorRadius <= minorRadius) {
                System.out.println("Error: Major radius must be greater than minor radius\n");
                return;
            }
            
            Torus torus = new Torus(majorRadius, minorRadius);
            System.out.printf("The volume of the %s is %.2f\n\n", torus.getDescription(), torus.getVolume());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }
    
    /**
     * Get a positive double value from the user
     */
    private static double getPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            
            if (!scanner.hasNextDouble()) {
                scanner.nextLine();
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }
            
            double value = scanner.nextDouble();
            scanner.nextLine();
            
            if (value <= 0) {
                System.out.println("Value must be positive. Please try again.");
                continue;
            }
            
            return value;
        }
    }
    
    /**
     * Ask the user if they want to continue
     */
    private static boolean askContinue() {
        System.out.print("Would you like to continue? (Y/N): ");
        String response = scanner.nextLine().trim();
        System.out.println();
        
        return response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("YES");
    }
    
    /**
     * Display exit message with date and time
     */
    private static void displayExitMessage() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        
        System.out.println("\n==========================================");
        System.out.println("Thank you for using the Java OO Shapes Program!");
        System.out.println("Exit time: " + formattedDateTime);
        System.out.println("==========================================");
    }
}
