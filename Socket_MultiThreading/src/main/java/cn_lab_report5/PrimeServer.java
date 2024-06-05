package cn_lab_report5;

import java.io.*;
import java.net.*;

public class PrimeServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1233)) {
            System.out.println("Server is listening on port 1233");
            
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket);
                
                new PrimeClientHandler(socket).start();
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

class PrimeClientHandler extends Thread {
    private Socket socket;

    public PrimeClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            while (true) {
                String received = input.readUTF();
                if (received.equalsIgnoreCase("bye")) {
                    System.out.println("Client " + socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                try {
                    int number = Integer.parseInt(received);
                    boolean isPrime = checkPrime(number);
                    output.writeUTF(number + " is " + (isPrime ? "a prime number." : "not a prime number."));
                } catch (NumberFormatException e) {
                    output.writeUTF("Invalid input. Please send a valid integer.");
                }
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean checkPrime(int number) {
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
}
