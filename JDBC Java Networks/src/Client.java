import java.io.*;
import java.net.*;

public class Client {

    public static void main(String args[]) throws IOException {
        String message, serverMessage;

        Socket clientSocket = new Socket(Credentials.HOST, Credentials.PORT);
        System.out.println("Client is running");

        PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader inFromServer = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            System.out.println("Enter the artist name:");
            message = inFromUser.readLine();
            System.out.println("You entered " + message);
            outToServer.println(message);

            serverMessage = inFromServer.readLine();
            System.out.println("FROM SERVER: " + serverMessage);
            if (message.equals("stop"))
                break;
        }

        inFromServer.close();
        inFromUser.close();
        outToServer.close();
        clientSocket.close();
    }
}
