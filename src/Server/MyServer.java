package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String[] args) {
        try(ServerSocket ss = new ServerSocket(80)) {
            System.out.println("Waiting for a client");
            try(Socket s = ss.accept()){
                System.out.println("Connected to client " + s.getRemoteSocketAddress());
                try(InputStream is = s.getInputStream();
                    OutputStream os = s.getOutputStream()){
                    System.out.println("Waiting a number from client...");
                    int num = is.read();
                    System.out.println("Number sent " + num);
                    int res = num * 5;
                    System.out.println("Sending response " + res);
                    os.write(res);
                } catch (IOException e){
                    System.err.println("Error in handling Streams : " + e.getMessage());
                }

            } catch (IOException e){
                System.err.println("Error with accepting a client : " + e.getMessage());
            }

        } catch (IOException e){
            System.err.println("Error with creating the Server : " + e.getMessage());
        }
    }
}
