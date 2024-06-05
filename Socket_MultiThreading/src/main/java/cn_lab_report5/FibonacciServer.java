
package cn_lab_report5;

import java.io.*;
import java.net.*;

public class FibonacciServer {
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
                
                try {
                    int number = Integer.parseInt(received);
                    String result = isFibonacci(number) ? "Fibonacci Number" : "Not a Fibonacci Number";
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
}

