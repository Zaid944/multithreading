package Multithreaded;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer() {
        // return new Consumer<Socket>() {
        // @Override
        // public void accept(Socket s) {

        // }
        // };
        return (clientSocket) -> {
            try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("hello from server");
                toClient.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        int port = 8090;
        Server server = new Server();
        try {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                serverSocket.setSoTimeout(1000000);
                System.out.println("server is listening on port " + port);
                while (true) {
                    Socket acceptedSocket = serverSocket.accept();
                    Thread thread = new Thread(() -> server.getConsumer().accept(acceptedSocket));
                    thread.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
