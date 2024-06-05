package Socket_Programing;
import java.io.*;
import java.net.*;
import java.util.Scanner;


public class ClientThread {
    public static void main(String []args)throws IOException{
        Socket s = new Socket("localhost", 1233);
        System.out.println("Connected");
        
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        Scanner scn = new Scanner(System.in);
        while(true){
         System.out.println(dis.readUTF());
         String str = scn.nextLine(); // date/time
         
         dos.writeUTF(str);
         
          if(str.equals("Bye")){
                  System.out.println("Closing The Connection "+s);
                  s.close();
                  break;
              }
          String received = dis.readUTF();
          System.out.println(received);
         
        }
        
        dis.close();
        dos.close();
        s.close();
        
    }
    
}
