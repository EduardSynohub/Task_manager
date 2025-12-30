package pl.eduard;

import org.apache.commons.lang3.ArrayUtils;
import pl.eduard.colors.ConsoleColors;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager_2 {

    public static final String FILE_NAME = "tasks.csv";
    static String[][] tasks;
    public static String[] options = {"add", "remove", "list", "exit"};

    public static void main(String[] args) {
        tasks = readDataFromFile(FILE_NAME);

        Scanner scanner = new Scanner(System.in);
        printOptions(options);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            switch (input) {
                case "add" :
                    addTask();
                    break;
                case "list" :
                    listOfTasks(tasks);
                    break;
                case "remove" :
                    removeTask();
                    break;
                case "exit" :
                    exit();
                    System.out.println(ConsoleColors.RED + "Bye bye..." + ConsoleColors.RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
            printOptions(options);
        }
    }

    public static void printOptions(String[] table) {
        System.out.println(ConsoleColors.BLUE + "Please select an option: " + ConsoleColors.RESET);
        for (String s : table) {
            System.out.println(s);
        }
    }

    public static String[][] readDataFromFile(String filename) {
        Path pathOfFile = Paths.get(filename);
        if (!Files.exists(pathOfFile)) {
            System.out.println("File does not exist!");
            System.exit(0);
        }

        String[][] table = null;
        try {
            List<String> strings = Files.readAllLines(pathOfFile);
            table = new String[strings.size()][strings.get(0).split(",").length];

            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    table[i][j] = split[j];
                }
            }
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return table;
    }

    public static void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description:");
        String description = scanner.nextLine();
        System.out.println("Please add task due date:");
        String dueDate = scanner.nextLine();
        System.out.println("Is your task is important: true/false");
        String importance = scanner.nextLine();

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = dueDate;
        tasks[tasks.length - 1][2] = importance;

        System.out.println("Task added successfully!");
    }

    public static void removeTask() {
        int maxIndex = tasks.length - 1;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove:");
            while (!scanner.hasNextInt()) {
                System.out.println("Please select number to remove:");
                scanner.nextLine();
            }
        int indexToRemove = scanner.nextInt();
            while (indexToRemove < 0 || indexToRemove > maxIndex) {
                System.out.println("Incorrect number passed. Please give number greater or equal 0:");
                indexToRemove = scanner.nextInt();
            }
            tasks = ArrayUtils.remove(tasks, indexToRemove);

        System.out.println("Task removed successfully!");
    }

    public static void listOfTasks(String[][] table) {
        for (int i = 0; i < table.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void exit() {
        Path path = Paths.get(FILE_NAME);

        String[] lines = new String[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            lines[i] = String.join(",", tasks[i]);
        }
         try {
             Files.write(path, Arrays.asList(lines));
         } catch (IOException e) {
             System.out.println("Something went wrong: " + e.getMessage());
         }
    }
}
