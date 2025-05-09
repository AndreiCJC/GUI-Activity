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
import java.util.*;

public class FileManager {
    private final String usersFile = "C:/data/users.txt";
    

    // Write content to a file (overwrite)
    public void writeFile(String filename, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
        }
    }

    // Read content from a file
    public String readFile(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    // Update a file by appending content
    public void updateFile(String filename, String newContent) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(newContent);
        }
    }

    // Register a new user
    public boolean register(String username, String password) throws IOException {
        if (userExists(username)) {
            return false; // Username already exists
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile, true))) {
            writer.write(username + ":" + password);
            writer.newLine();
        }
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
        }
        return false; // Login failed
    }

    // Helper to check if a user already exists
    private boolean userExists(String username) throws IOException {
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
}