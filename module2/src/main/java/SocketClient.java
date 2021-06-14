import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) throws IOException {
        startConnection("localhost", 8080);

        Scanner s = new Scanner(System.in);

        System.out.println("""
                1. Select all weather by region id\s
                2. Select all weather by temperature\s
                3. Delete weather by id\s
                 Select:""");
        int variant = s.nextInt();

        switch (variant) {
            case 1, 3 -> {
                System.out.println("Input id:");
                int id = s.nextInt();
                sendMessage(variant + ";id=" + id);
            }
            case 2 -> {
                System.out.println("Input date:");
                float temperature = s.nextFloat();
                sendMessage(variant + ";temperature=" + temperature);
            }
            default -> {
                return;
            }
        }
        try {
            stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public static void sendMessage(String msg) throws IOException {
        out.println(msg);
        StringBuilder res = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
    }

    public static void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
