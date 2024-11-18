package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String[] args) {
        try(ServerSocket ss = new ServerSocket(1234)) {
            System.out.println("Waiting for a client");
            try(Socket s = ss.accept()){
                System.out.println("Connected to client " + s.getRemoteSocketAddress());
                try(InputStream is = s.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br  = new BufferedReader(isr);
                    OutputStream os = s.getOutputStream();
                    PrintWriter pw = new PrintWriter(os, true)){
                    System.out.println("Waiting a String from client...");
                    String str = br.readLine();
                    System.out.println("Request : " + str);
                    String str_upper = str.toUpperCase();
                    System.out.println("Response : " + str_upper);
                    pw.println(str_upper);
                } catch (IOException e){
                    System.err.println("Error in handling Input Streams " + e.getMessage());
                }
            } catch (IOException e){
                System.err.println("Error with accepting a client : " + e.getMessage());
            }

        } catch (IOException e){
            System.err.println("Error with creating the Server : " + e.getMessage());
        }
    }
}
