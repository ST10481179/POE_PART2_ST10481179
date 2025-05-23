/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.chatapp;

import javax.swing.JOptionPane;
import java.util.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author RC_Student_lab
 */
public class ChatApp {
    
    private static int messageCounter = 0;
    private static int totalMessage = 0;
    private static JSONArray messageStorage = new JSONArray();
    private static boolean exit;
    private static int totalMessages;
    private static int maxMessage;
    
    public static void main(String[] args) {
   
       
        if (!login()) return;
        JOptionPane.showMessageDialog(null,"Welcome to ChatApp.");
        int maxMessages = 0;
        while (true) {
            String input = JOptionPane.showInputDialog("How many messages would you like to enter");
            try {
                maxMessages = Integer.parseInt(input);
                if (maxMessages > 0) break;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Enter a valid Postive number.");
            }
        }
        boolean exit = false;
        while (!exit) {
        String[] options = {"send Message", "Show Recently Sent Message", "Quit"};
        int choice = JOptionPane.showOptionDialog(null, "Choose and option:", "QuikChat Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        switch (choice) {
            case 0: // send message
                if (messageCounter < maxMessage) {
                 sendMessages();
                } else {
                JOptionPane.showMessageDialog(null, "message limit reached");
                }
                break;
            case 1: // Coming soon
                JOptionPane.showMessageDialog(null, "coming soon");
                break;
            case 2: // Quit
                exit = true;
                break;
            default:
                JOptionPane.showMessageDialog(null, "invalid option.");
        }

    }
        JOptionPane.showMessageDialog(null, "Total messages sent: "+ totalMessages);
        saveMessagesToJSON();
    }

    static boolean login() {
        String user = JOptionPane.showInputDialog("Enter username");
        String pass = JOptionPane.showInputDialog("Enter password");

        if ("admin".equals(user) && "1234".equals(pass)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Login Failed.");
            return false;
        }
    }

    static void senMessage() {
        long messageId = 1000000000L + new Random().nextInt(900000000);
        messageCounter++;

        String recipient = JOptionPane.showInputDialog("Enter recipient number (+ccxxxxxxxxxx)");
        if (recipient == null || !recipient.matches("\\+\\d{9,12}")) {
            JOptionPane.showMessageDialog(null, "Invalid number. Must include international code and be <= 12 digits.");
            return;
        }

        String message = JOptionPane.showInputDialog("Enter your Message (max 250 chars):");
        if (message == null || message.length() > 250) {
            JOptionPane.showMessageDialog(null, "please enter a message of less than 250 character.");
            return;
        }

        String[] words = message.trim().split("\\s+");
        String hash = String.format("%02d:%d:%s%s",
        Long.parseLong(Long.toString(messageId).substring(0, 2)),
                messageCounter,
                words[0].toUpperCase(),
                words.length > 1 ? words[words.length - 1].toUpperCase() : "");
                
        String[] actions = {"send", "Disregard", "Store"};
        int action = JOptionPane.showOptionDialog(null,
                "Choose what to do with this message:",
                "Message Options",
                JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null, actions, actions[0]);
if (action == 1) {
    JOptionPane.showMessageDialog(null, "Message disregarded.");
    return;
}
JSONObject jsonMessage = new JSONObject();
jsonMessage.put("Message", messageId);
jsonMessage.put("Recipient",recipient);
jsonMessage.put("Message", message);

if (action == 2) {
    messageStorage.add(jsonMessage);
    JOptionPane.showMessageDialog(null, "Message stored");
    return;
} 
totalMessages++;

JOptionPane.showMessageDialog(null,
        "Message Sent!\n"+
                "Message ID:" + messageId + "\n" +
                "Recipient: " + recipient + "\n" +
                "Message: " + message);
    }
    static void saveMessageToJSON() {
        try (FileWriter file = new FileWriter("storedMessages.json")) {
            file.write(messageStorage.toJSONString());
            file.flush();
            System.out.println("Stored messages saved to storedMessages.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
     }

    private static void sendMessages() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static void saveMessagesToJSON() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}