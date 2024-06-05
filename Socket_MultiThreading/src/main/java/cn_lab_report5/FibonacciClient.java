package cn_lab_report5;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FibonacciClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {
            
            System.out.println("Connected to the server");

            while (true) {
                System.out.print("Enter a number to check (or 'Bye' to quit): ");
                String message = scanner.nextLine();

                output.writeUTF(message);

                if (message.equalsIgnoreCase("Bye")) {
                    System.out.println("Closing the connection");
                    break;
                }

                String response = input.readUTF();
                System.out.println("Server response: " + response);
            }
        } catch (IOException e) {
            System.err.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

