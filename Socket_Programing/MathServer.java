package Socket_Programing;

import java.io.*;
import java.net.*;

public class MathServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server started. Waiting for clients...");

        int clientCount = 0;
        while (clientCount < 5) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client " + (clientCount + 1) + " connected.");

            Thread clientThread = new ClientHandler(clientSocket);
            clientThread.start();

            clientCount++;
        }
        serverSocket.close();
        System.out.println("Server closed.");
    }
}

class ClientHandler extends Thread {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                int num1 = dis.readInt();
                int num2 = dis.readInt();
                String operator = dis.readUTF();

                int result = 0;
                switch (operator) {
                    case "sum":
                        result = num1 + num2;
                        break;
                    case "subtract":
                        result = num1 - num2;
                        break;
                    case "multiplication":
                        result = num1 * num2;
                        break;
                    case "division":
                        result = num1 / num2;
                        break;
                    case "modules":
                        result = num1 % num2;
                        break;
                    default:
                        System.out.println("Invalid operator.");
                        break;
                }

                dos.writeInt(result);

                if (dis.readUTF().equals("ends")) {
                    System.out.println("Client disconnected.");
                    break;
                }
            }

            dis.close();
            dos.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
