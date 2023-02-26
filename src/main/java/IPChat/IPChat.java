package IPChat;

import ipchatExceptions.IPChatExceptions;
import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.*;

public class IPChat {
    public static ArrayList<Task> tasks = new ArrayList<>();
    public static int tasksCount = 0; // used camelCase
    private static int checkInput = 0;

    // Saves
    public static void saveContent(ArrayList<Task> tasks) {
        String path = "C:\\Users\\deepa\\OneDrive\\Documents\\OneNote\\ip\\SaveInfo.txt";
        try {
            FileWriter fw = new FileWriter(path, false);
            PrintWriter pw = new PrintWriter(fw, false);
            pw.flush();
            pw.close();
            fw.close();
            for (int i = 0; i < tasksCount; i += 1) {
                String value = tasks.get(i).saveStuff() + "\n";
                Files.write(Paths.get(path), value.getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadContent() throws FileNotFoundException, IPChatExceptions {
        File currFile = new File("C:\\Users\\deepa\\OneDrive\\Documents\\OneNote\\ip\\SaveInfo.txt");
        Scanner input = new Scanner(currFile);
        String taskCase;
        String str;
        int taskNum = 1;

        while (input.hasNext()) {
            String info = input.nextLine();
            String[] arr1 = info.split(" \\| ");
            String[] arr2 = arr1[0].split(" ");
            taskCase = arr2[0];
            str = Integer.toString(taskNum);

            switch (taskCase) {
            case "deadline":
                deadlineTasks(arr1[0]);
                if (arr1[1].equals("1")) {
                    markDone(str);
                }
                break;
            case "todo":
                toDoTasks(arr1[0]);
                if (arr1[1].equals("1")) {
                    markDone(str);
                }
                break;
            case "event":
                eventTasks(arr1[0]);
                if (arr1[1].equals("1")) {
                    markDone(str);
                }
                break;
            default:
            }
            taskNum += 1;
        }
        input.close();
    }

    // Says bye to the user and exits the program
    public static void sayBye(String statements) throws IPChatExceptions {
        if (statements.length() != 3) {
            throw new IPChatExceptions("Too long a goodbye makes me emotional and too short a goodbye makes me feel ignored!\n");
        } else {
            System.out.println("------------------------------------------");
            System.out.println("Bye, Hope to see you soon");
            System.out.println("------------------------------------------");
            checkStatements();
        }
        System.exit(0);
    }

    public static void checkStatements() {
        checkInput = 1;
    }

    // Create a list of the task
    public static void listTasks(String statements) throws IPChatExceptions {
        if (statements.length() == 4) {
            if (tasksCount > 0) {
                System.out.println("------------------------------------------");
                System.out.println("Here is the list of tasks for the day! All the best :) \n");
                for (int i = 0; i < tasksCount; i += 1) {
                    System.out.println((i + 1) + "." + "[" + tasks.get(i).getStatusIcon() + "] " + tasks.get(i).toString());
                }
                System.out.println("------------------------------------------");
            }
        }
    }

    // Marks a task as done
    public static void markDone(String statements) throws IPChatExceptions {
        if (tasksCount != 0) {
            try {
                int taskIndex = Integer.parseInt(statements.substring(statements.length() - 1)) - 1; // changed index to taskIndex
                tasks.get(taskIndex).markAsDone();
                System.out.println("I have marked the task as done");
                System.out.println("------------------------------------------");
            } catch (NullPointerException e) {
                System.out.println("Invalid Task Number");
            }
        } else {
            throw new IPChatExceptions("To start listing");
        }
    }

    // tasks to do
    public static void toDoTasks(String statements) {
        if (statements.length() == 4) {
            System.out.println("Please continue");
        } else {
            int todoLength = statements.length();
            String todoName = statements.substring(5, todoLength);
            tasks.add(tasksCount, new ToDo(todoName));
            System.out.println("------------------------------------------");
            System.out.println("Got it. I've added this task:\n" + tasks.get(tasksCount).toString() + "\n");
            tasksCount += 1;
            System.out.println("Now you have " + tasksCount + " tasks in the list.\n");
            System.out.println("------------------------------------------");
        }
    }

    // Tasks having deadlines
    public static void deadlineTasks(String statements) {
        if (statements.length() == 8) {
            System.out.println("Please provide correct input");
        } else if (statements.contains("/")) {
            System.out.println("Please provide correct deadline");
        } else {
            int deadlineLength = statements.length();
            String deadlineName = statements.substring(9, statements.lastIndexOf("/"));
            String by = statements.substring((statements.lastIndexOf("/") + 4), deadlineLength);
            tasks.add(tasksCount, new Deadline(deadlineName, by));
            System.out.println("------------------------------------------");
            System.out.println("Got it. I've added this task:\n" + tasks.get(tasksCount).toString() + "\n");
            tasksCount += 1;
            System.out.println("Now you have " + tasksCount + " tasks in the list.\n");
            System.out.println("------------------------------------------");
        }
    }

    // Events of the task
    public static void eventTasks(String statements) {
        if (statements.length() == 5) {
            System.out.println("Please provide correct input");
        } else if (!statements.contains("/")) {
            System.out.println("Please provide timings");
        } else {
            int eventLength = statements.length();
            String eventName = statements.substring(6, statements.lastIndexOf("/"));
            int index = statements.lastIndexOf("/") + 4;
            String to = statements.substring(index, eventLength);
            tasks.add(tasksCount, new Event(eventName, to));
            System.out.println("------------------------------------------");
            System.out.println("Got it. I'e added this task:\n" + tasks.get(tasksCount).toString() + "\n");
            tasksCount += 1;
            System.out.println("Now you have " + tasksCount + " tasks in the list.\n");
            System.out.println("------------------------------------------");
        }
    }

    // Delete a task
    public static void deleteTasks(String statements) throws IPChatExceptions {
        if (tasksCount != 0) {
            try {
                int finalNum = Integer.parseInt(statements.substring(statements.lastIndexOf(" ") + 1)) - 1;
                Task removeTask = tasks.get(finalNum);
                tasks.remove(finalNum);
                tasksCount--;
                System.out.println("------------------------------------------");
                System.out.println("Deleting the following items");
                System.out.println("  " + removeTask.toString() + "\n" + "\nYou now have " + tasksCount + " tasks left.\n");
                System.out.println("------------------------------------------");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid Task number !!");
            }
        } else {
            throw new IPChatExceptions("Please give tasks to delete!");
        }
    }

    // Compilation
    public static void mySequence() throws IPChatExceptions, IOException{
        while (checkInput == 0) {
            Scanner input = new Scanner(System.in);
            String statements = input.nextLine();
            String words = statements.split(" ")[0];
            switch (words) {
            case "bye":
                sayBye(statements);
                break;
            case "list":
                listTasks(statements);
                break;
            case "done":
                markDone(statements);
                saveContent(tasks);
                break;
            case "todo":
                toDoTasks(statements);
                saveContent(tasks);
                break;
            case "deadline":
                deadlineTasks(statements);
                saveContent(tasks);
                break;
            case "event":
                eventTasks(statements);
                saveContent(tasks);
                break;
            case "delete":
                deleteTasks(statements);
                saveContent(tasks);
                break;
            default:
                System.out.println("------------------------------------------");
                System.out.println("Please provide accurate readings");
            }
        }
    }

    public static void main(String[] args) throws IPChatExceptions , IOException{
        System.out.println("Hello I'm IPChat, What can I do for you");
        System.out.println("------------------------------------------");
        mySequence();
    }
}
