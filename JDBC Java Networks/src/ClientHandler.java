import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    //declare variables
    Socket clientSocket;
    int clientId;
    Database db;

    //Constructor
    public ClientHandler (Socket socket, int clientId, Database db) {
        //complete the constructor
        clientSocket = socket;
        this.clientId = clientId;
        this.db = db;
    }

    public void run() {
        System.out.println("ClientHandler started...");
        try{
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
            String clientMessage;

            while(!(clientMessage = inFromClient.readLine()).equals("stop")) {
                System.out.println("Client sent the artist name " + clientMessage);
                int titlesNum = this.db.getTitles(clientMessage);
                outToClient.println("Number of titles: " + titlesNum + " records found");
            }
            System.out.println("Client " + clientId + " has disconnected");
            outToClient.println("Connection closed, Goodbye!");

            inFromClient.close();
            outToClient.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
