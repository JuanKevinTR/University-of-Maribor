import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author J. Kevin Trujillo
 * Web: juankevintrujillo.com
 * */

public class Server {

    // Listening port
    private final static int PORT = 2000;

    // Vector to store active clients
    static Vector<ClientHandler> clientsVector = new Vector<>();

    // Counter for clients
    public static int amountClients = 0;

    // OTHER GLOBAL VARIABLES
    public static String adminUser = "";

    // GAME VARIABLES
    public static boolean gameActive = false;
    public static String[] toGuess = {"hello", "networking", "blockchain", "security"}; //, "networking", "blockchain", "security"
    public static String wordToGuess;
    public static char[] wordInCHAR;
    public static char[] wordEncoded;
    public static StringBuilder wordEncodedShow;
    public static int lengthGuessed;
    public static int failedWords;

    public static void main(String[] args) throws IOException {
        // init server
        ServerSocket server = new ServerSocket(PORT);
        Socket client;

        // running infinite loop for getting client request
        while (true) {
            // Wait client
            System.out.println("Waiting for a new client...");
            client = server.accept();

            System.out.println("\t==> New client request received: " + client);

            // Obtain input and output streams
            DataInputStream readFromClient = new DataInputStream(client.getInputStream());
            DataOutputStream writeToClient = new DataOutputStream(client.getOutputStream());

            // Getting username
            System.out.println("\t==> Getting username...");
            String username = "";
            while (username.equals("")) {
                username = readFromClient.readUTF().replaceAll(" ", "").toUpperCase();
            }

            if (clientsVector.size() >= 1) {
                int count = 0;
                int i = 0;
                for (ClientHandler clients : clientsVector) {
                    if (clients.name.equals(username)) {
                        count++;
                    } else if (clients.name.equals(username + i)) {
                        count = i;
                    }
                    i++;
                }
                if (count > 0) {
                    username = username + (count + 1);
                }
            }

            // Sending correct username
            writeToClient.writeUTF(username);

            // Create a new handler object for handling this request.
            System.out.println("\t==> Creating a new handler for this client...");
            ClientHandler mtch = new ClientHandler(client, username, readFromClient, writeToClient);

            if (adminUser.equals("")) {
                adminUser = username;
                writeToClient.writeUTF("\t==> Server: You're ADMIN");
            }

            // Create a new Thread with this object.
            Thread clientThread = new Thread(mtch);

            System.out.println("\t==> Adding this client (" + username + ") to the active client list" +
                    "\n-------------------------------------");

            // Add this client to active clients list
            clientsVector.add(mtch);

            // Start the client thread.
            clientThread.start();

            StringBuilder allUsers = new StringBuilder();
            allUsers.append("#LIST_UPDATE");
            int i = 0;
            for (ClientHandler clients : clientsVector) {
                allUsers.append("#").append(clientsVector.get(i).name);
                i++;

                clients.writeToClient.writeUTF("\t==> " + username + " is connected...");
            }
            for (ClientHandler clients : clientsVector) {
                clients.writeToClient.writeUTF(allUsers.toString());
            }

            // Increment amountClients for new client.
            amountClients++;

        }

    }

}