package cn_lab_report5;
import java.io.*;
import java.net.*;
import java.util.Scanner;
public class MathClient {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket("localhost", 1234);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        while (true) {
            System.out.println("Enter first number:");
            int num1 = scanner.nextInt();
            System.out.println("Enter second number:");
            int num2 = scanner.nextInt();
            System.out.println("Enter operator (Sum/Subtract/Multiplication/Division/Modules):");
            String operator = scanner.next();
            operator = operator.toLowerCase();

            dos.writeInt(num1);
            dos.writeInt(num2);
            dos.writeUTF(operator);

            int result = dis.readInt();
            System.out.println("Result: " + result);
            System.out.println("Do you want to end the connection? (Type 'ENDS' to end, otherwise press any key)");
            String end = scanner.next();
            dos.writeUTF(end);
            if (end.equals("ENDS")) {
                break;
            }
        }
        dis.close();
        dos.close();
        socket.close();
        scanner.close();
    }
}
