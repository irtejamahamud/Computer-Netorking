package cn_lab_report5;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class PrimeClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1233);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server");

            while (true) {
                System.out.print("Enter a number to check if it is prime (or type 'bye' to exit): ");
                String userInput = scanner.nextLine();
                
                output.writeUTF(userInput);

                if (userInput.equalsIgnoreCase("bye")) {
                    System.out.println("Closing the connection...");
                    break;
                }

                String response = input.readUTF();
                System.out.println("Server response: " + response);
            }
        } catch (IOException ex) {
            System.out.println("Client exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
