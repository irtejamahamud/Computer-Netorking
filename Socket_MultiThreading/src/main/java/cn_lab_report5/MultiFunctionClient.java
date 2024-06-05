package cn_lab_report5;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiFunctionClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server");

            while (true) {
                System.out.println("Choose an operation: primecheck, oddeven, fibonaccicheck, palindrome, factorial");
                String operation = scanner.nextLine();

                System.out.print("Enter a number to check (or 'Bye' to quit): ");
                String number = scanner.nextLine();

                output.writeUTF(operation);
                output.writeUTF(number);

                if (operation.equalsIgnoreCase("Bye") || number.equalsIgnoreCase("Bye")) {
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
