package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerMT extends Thread{
    private int NumClient;
    public static void main(String[] args) {
        new MyServerMT().start();
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(1234);) {
            System.out.println("Server is Running...");
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    NumClient++;
                    System.out.println("Client number "+NumClient + " is Connected, IP : " + socket.getRemoteSocketAddress());
                    new Conversation(socket, NumClient).start();
                } catch (IOException e) {
                    System.err.println("Error with creating socket " + e.getMessage());
                }
            }
        } catch (IOException e){
            System.err.println("Error with creating the server " + e.getMessage());
        }
    }
    private class Conversation extends Thread{
        private Socket socket;
        private int NumClient;
        private Conversation(Socket socket, int NumClient){
            this.socket = socket;
            this.NumClient = NumClient;
        }
        @Override
        public void run(){
            try(InputStream is = this.socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                OutputStream os = this.socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true)){
                    pw.println("Hello, You are client number " + this.NumClient);
                    String input_str;
                    int len_str;
                    while(true){
                        try {
                            input_str = br.readLine();
                            if(input_str == null){
                                System.out.println("Client number " + this.NumClient + " IP : " + this.socket.getRemoteSocketAddress() + " disconnected !");
                                break;
                            }
                            System.out.println("Client number " + this.NumClient + " sends request : " + input_str);
                            len_str = input_str.strip().length();
                            pw.println(len_str);
                        } catch (Exception e){
                            System.err.println("Error occurred: " + e.getMessage());
                            break;
                        }
                    }
            } catch (IOException e){
                System.err.println("Error with Streams " + e.getMessage());
            } finally {
                // Ensure the socket is closed when the thread ends
                try {
                    if (this.socket != null && !this.socket.isClosed()) {
                        this.socket.close();
                    }
                    System.out.println("Resources for Client number " + this.NumClient + " released.");
                } catch (IOException e) {
                    System.err.println("Error closing socket for Client number " + this.NumClient + ": " + e.getMessage());
                }
            }
        }
    }
}
