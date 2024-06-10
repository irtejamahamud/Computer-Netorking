/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Socket_MultiThreading.src.main.java.cn_lab_report5;

import java.io.*;
import java.net.*;

public class PalindromeServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Server is listening on port 1234");
            
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            
            while (true) {
                String received = input.readUTF();
                
                if (received.equalsIgnoreCase("Bye")) {
                    System.out.println("Client disconnected");
                    break;
                }
                
                String result = isPalindrome(received) ? "Palindrome" : "Not Palindrome";
                output.writeUTF(result);
            }
        } catch (IOException e) {
            System.err.println("Client handler exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }

    private boolean isPalindrome(String str) {
        String reverseStr = "";
    
        int strLength = str.length();
    
        for (int i = (strLength - 1); i >=0; --i) {
          reverseStr = reverseStr + str.charAt(i);
        }
         if (str.toLowerCase().equals(reverseStr.toLowerCase())) {
            return true;
          }
          else 
            return false;
    }
}

