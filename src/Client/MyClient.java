package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MyClient {
    public static void main(String[] args) {
        System.out.println("Connecting to Server...");
        try(Socket s = new Socket("localhost", 80)){
            try(InputStream is = s.getInputStream();
                OutputStream os = s.getOutputStream()){
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter a number : ");
                int num = scanner.nextInt();
                System.out.println("Sending " + num);
                os.write(num);
                System.out.println("Waiting Server Response...");
                int res = is.read();
                System.out.println("Response : " + res);

            } catch (IOException e){
                System.err.println("Error with Streams " + e.getMessage());
            }

        } catch (IOException e){
            System.err.println("Error with Socket creation " + e.getMessage());
        }

    }
}
