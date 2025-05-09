/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FileHandling_GUI;

/**
 *
 * @author Andrei
 */
import java.io.*;

public class UltraFileHandling {
    public static String username;
    private final String usersFile = "C:/data/users.txt";
    private final String usersDirectory = "C:/data/UsersData/"; // base path for user folders

    // Write content to a file (overwrite)
    public void writeFile(String filename, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(name() + filename))) {
            writer.write(content);
            
        }
    }

    // Read content from a file
    public String readFile(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(name() + filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }


    // Register a new user and create a folder for them
public boolean register(String username, String password) throws IOException {
    // Make sure the file exists
    File file = new File(usersFile);
    if (!file.exists()) {
        file.createNewFile();  // This will create the file if it doesn't exist
    }

    if (userExists(username)) {
        return false; // Username already exists
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
        writer.write(username + ":" + password);
        writer.newLine();
    }

    createUserFolder(username);
    return true;
}


    // Login user by checking credentials
    public boolean login(String username, String password) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true; // Login success
                }
            }
            this.username = username;
        }
        return false; // Login failed
    }

    // Helper to check if a user already exists
    public boolean userExists(String username) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length > 0 && parts[0].equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Creates a folder for the user
    public void createUserFolder(String username) {
        File folder = new File(usersDirectory + username.trim());
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("User folder created at: " + folder.getAbsolutePath());
            } else {
                System.out.println("Failed to create folder for user: " + username);
            }
        }
    }
    
    public String name()
    {
        String FileHandler = usersDirectory + username + "/";
        return FileHandler;
    }
    
    public String viewAllDiariesFromFiles() {
    StringBuilder content = new StringBuilder();
    File userFolder = new File(name()); // path to the user's folder

    if (userFolder.exists() && userFolder.isDirectory()) {
        File[] files = userFolder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files != null && files.length > 0) {
            for (File file : files) {
                content.append("=== ").append(file.getName()).append(" ===\n");
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                } catch (IOException e) {
                    content.append("[Error reading ").append(file.getName()).append("]\n");
                }
                content.append("--------------------\n\n");
            }
        } else {
            content.append("No .txt files found.\n");
        }
    } else {
        content.append("User folder not found.\n");
    }

    return content.toString();
}
    
    public String searchDiaryEntries(String filename) {
    StringBuilder content = new StringBuilder();
    File userFolder = new File(name()); // user's directory

    if (userFolder.exists() && userFolder.isDirectory()) {
        File[] files = userFolder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files != null && files.length > 0) {
            for (File file : files) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    boolean foundInFile = false;

                    while ((line = br.readLine()) != null) {
                        if (line.toLowerCase().contains(filename.toLowerCase())) {
                            if (!foundInFile) {
                                content.append("=== Found in: ").append(file.getName()).append(" ===\n");
                                foundInFile = true;
                            }
                            content.append(line).append("\n");
                        }
                    }

                    if (foundInFile) {
                        content.append("-------------------------\n\n");
                    }

                } catch (IOException e) {
                    content.append("[Error reading ").append(file.getName()).append("]\n");
                }
            }
        } else {
            content.append("No .txt files found.\n");
        }
    } else {
        content.append("User folder not found.\n");
    }

    return content.toString();
}

}
