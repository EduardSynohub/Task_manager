package pl.eduard;


import pl.eduard.colors.ConsoleColors;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.SortedMap;

public class TaskManager {
    public static final String FILENAME = "tasks.csv";
    public static void main(String[] args) {
        selectingOptions();
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
            case "remove":
                removeTask();
                break;
            case "list":
                listOfTasks();
                break;
            case "exit":
                exitFromTasks();
                break;
            default:
                System.out.println("Please select a correct option.");
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

        try (FileWriter fileWriter = new FileWriter(FILENAME, true)) {
            fileWriter.append(addingTask[0]).append(",").append(addingTask[1]).append(",").append(addingTask[2]).append("\n");
            System.out.println("Task successfully added!");
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }

        selectingOptions();
    }

    public static String[] addElementToTable(String[] table, String elementToAdd) {
        String[] newTable = Arrays.copyOf(table, table.length + 1);
        newTable[newTable.length - 1] = elementToAdd;
        return newTable;
    }

    public static void removeTask() {
        String[][] taskList = tasks(FILENAME);
        int maxIndex = taskList.length - 1;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove:");

        while (!scanner.hasNextInt()) {
            System.out.println("Incorrect argument passed. Please enter a number:");
            scanner.nextLine();
        }

        int index = scanner.nextInt();

        while (index < 0 || index > maxIndex) {
            System.out.println("Task with that number does not exist. Try again:");
            index = scanner.nextInt();
        }

        String[][] newTasks = new String[maxIndex][taskList[0].length];

        int newIndex = 0;
        for (int i = 0; i < taskList.length; i++) {
            if (i != index) {
                newTasks[newIndex++] = taskList[i];
            }
        }

        try (FileWriter fileWriter = new FileWriter(FILENAME)) {
            for (String[] task : newTasks) {
                fileWriter.write(String.join(",", task) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Cannot save data to the file.");
        }

        selectingOptions();
    }

    public static void listOfTasks() {
        String[][] listOfTasks = tasks(FILENAME);
        for (int i = 0; i < listOfTasks.length; i++) {
            System.out.println(i + " : " + Arrays.toString(listOfTasks[i]));
        }

        selectingOptions();
    }

    public static void exitFromTasks() {
        System.out.println(ConsoleColors.RED + "Bye bye!" + ConsoleColors.RESET);
    }
}