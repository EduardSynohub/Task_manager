package pl.eduard;


import pl.eduard.colors.ConsoleColors;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        addTask();
        String[][] t = tasks("tasks.csv");
        for (int i = 0; i < t.length; i++) {
            System.out.println(Arrays.toString(t[i]));
        }
    }

    public static String selectingOptions() {
        String[] options = {"add", "remove", "list", "exit"};
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        for (String s : options) {
            System.out.println(s);
        }
        String s = scanner.nextLine();
        switch (s) {
            case "add":
                addTask();
                break;
//            case "remove":
//                removeTask();
//                break;
//            case "list":
//                listOfTasks();
//                break;
//            case "exit":
//                exitFromTasks();
//                break;
//            default:
//                System.out.println("Please select a correct option.");
        }
        return s;
    }

    public static String[][] tasks (String fileName) {
        File file = new File(fileName);
        int countOfLines = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                countOfLines++;
                scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist.");
        }

        String[][] tasks = new String[countOfLines][3];
        try (Scanner scanner = new Scanner(file)) {
            int rowIndex = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                for (int colIndex = 0; colIndex < 3; colIndex++) {
                    tasks[rowIndex][colIndex] = data[colIndex];
                }
                rowIndex++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist.");
        }
        return tasks;
    }

    public static void addTask() {
        Scanner scanner = new Scanner(System.in);
        String[] addingTask = new String[3];
        System.out.println("Please add task description:");
        addingTask[0] = scanner.nextLine();
        System.out.println("Please add task due date:");
        addingTask[1] = scanner.nextLine();
        System.out.println("Is your task is important: true/false");
        addingTask[2] = scanner.nextLine();

        try (FileWriter fileWriter = new FileWriter("tasks.csv", true)) {
            fileWriter.append(addingTask[0]).append(",").append(addingTask[1]).append(",").append(addingTask[2]).append("\n");
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
    }
}