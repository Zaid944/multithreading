package ThreadPool;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ExecutorService threadPool;

    public Server(int poolSize) {
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    public void handleClient(Socket clientSocket) {
        try (PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
            toSocket.println("Hello from server " + clientSocket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] aStrings) {
        int port = 8090;
        int poolSize = 10;
        Server server = new Server(poolSize);

        try {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                serverSocket.setSoTimeout(700000);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    server.threadPool.execute(() -> server.handleClient(clientSocket));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.threadPool.shutdown();
        }
    }
}
