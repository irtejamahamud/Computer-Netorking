package cn_lab_report5;

import java.io.*;
import java.net.*;

public class MultiFunctionServer {
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
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            while (true) {
                String operation = input.readUTF();
                if (operation.equalsIgnoreCase("Bye")) {
                    System.out.println("Client disconnected");
                    break;
                }

                String received = input.readUTF();
                if (received.equalsIgnoreCase("Bye")) {
                    System.out.println("Client disconnected");
                    break;
                }

                try {
                    int number = Integer.parseInt(received);
                    String result = performOperation(operation, number);
                    output.writeUTF(result);
                } catch (NumberFormatException e) {
                    output.writeUTF("Invalid number");
                }
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

    private String performOperation(String operation, int number) {
        switch (operation.toLowerCase()) {
            case "primecheck":
                return isPrime(number) ? "Prime" : "Not Prime";
            case "oddeven":
                return number % 2 == 0 ? "Even" : "Odd";
            case "fibonaccicheck":
                return isFibonacci(number) ? "Fibonacci Number" : "Not a Fibonacci Number";
            case "palindrome":
                return isPalindrome(number) ? "Palindrome" : "Not Palindrome";
            case "factorial":
                return "Factorial of " + number + " is " + factorial(number);
            default:
                return "Invalid operation";
        }
    }

    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isFibonacci(int number) {
        if (number < 0) {
            return false;
        }
        int a = 0, b = 1;
        if (number == a || number == b) {
            return true;
        }
        int c;
        while (true) {
            c = a + b;
            if (c == number) {
                return true;
            }
            if (c > number) {
                return false;
            }
            a = b;
            b = c;
        }
    }

    private boolean isPalindrome(int number) {
        String str = String.valueOf(number);
        int len = str.length();
        for (int i = 0; i < len / 2; i++) {
            if (str.charAt(i) != str.charAt(len - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    private int factorial(int number) {
        if (number < 0) {
            return -1; // Undefined for negative numbers
        }
        int fact = 1;
        for (int i = 2; i <= number; i++) {
            fact *= i;
        }
        return fact;
    }
}
