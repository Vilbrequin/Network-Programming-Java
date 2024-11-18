package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MyClient {
    public static void main(String[] args) {
        System.out.println("Connecting to Server...");
        try(Socket s = new Socket("localhost", 1234)){
            try(OutputStream os = s.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                InputStream is = s.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br  = new BufferedReader(isr)){
                System.out.println(br.readLine());
                while(true){
                    try {
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("Enter a String : ");
                        String str = scanner.nextLine();
                        System.out.println("Sending String : " + str);
                        pw.println(str);
                        String output_str = br.readLine();
                        System.out.println("Output String : " + output_str);
                    } catch (IOException e){
                        System.err.println("Connection lost: " + e.getMessage());
                        break;
                    }
                }
            } catch (IOException e){
                System.err.println("Error with Output Streams " + e.getMessage());
            }

        } catch (IOException e){
            System.err.println("Error with Socket creation " + e.getMessage());
        }

    }
}
