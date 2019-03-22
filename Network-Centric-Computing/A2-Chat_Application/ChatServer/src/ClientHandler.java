import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author J. Kevin Trujillo
 * Web: juankevintrujillo.com
 * */

public class ClientHandler implements Runnable {

    public String name;
    private final DataInputStream readFromClient;
    public final DataOutputStream writeToClient;
    private Socket client;
    private String privateUser;

    // Constructor
    public ClientHandler(Socket c, String n, DataInputStream dis, DataOutputStream dos) {
        readFromClient = dis;
        writeToClient = dos;
        name = n;
        client = c;
    }

    private void sendToAll(String message) throws IOException {
        for (ClientHandler clients : Server.clientsVector) {
            clients.writeToClient.writeUTF(name + ": " + message);
        }
    }

    private void sendToAllFromServer(String message) throws IOException {
        for (ClientHandler clients : Server.clientsVector) {
            clients.writeToClient.writeUTF(message);
        }
    }

    private void game(String action) throws IOException {
        switch (action) {
            case "START":
                Server.failedWords = 0;

                Server.wordToGuess = Server.toGuess[(int) Math.floor((Math.random() * (Server.toGuess.length)))];
                System.out.println("\t==> WORD_TO_GUESS_FOR_CLIENT: " + Server.wordToGuess);

                Server.wordInCHAR = new char[Server.wordToGuess.length()];
                for (int i = 0; i < Server.wordInCHAR.length; i++) {
                    Server.wordInCHAR[i] = Server.wordToGuess.charAt(i);
                }

                Server.wordEncoded = new char[Server.wordToGuess.length()];
                for (int i = 0; i < Server.wordEncoded.length; i++) {
                    Server.wordEncoded[i] = '_';
                }

                Server.failedWords = 0;
                Server.lengthGuessed = 0;
                enableOneLetter();

                sendToAllFromServer("\t==> GAME STARTED...");
                sendToAllFromServer("\t==> WORD TO GUESS: " + Server.wordEncodedShow);

                break;
            case "END":
                sendToAllFromServer("\t==> GAME FINISHED...");
                break;
        }
    }

    private void enableOneLetter() {
        int rand = random();

        if (Server.wordEncoded[rand] == '_') {
            Server.wordEncoded[rand] = Server.wordInCHAR[rand];
        } else if ((Server.wordEncoded[rand] != '_')) {
            while (Server.wordEncoded[rand] != '_') {
                rand = random();
                if (Server.wordEncoded[rand] == '_') {
                    Server.wordEncoded[rand] = Server.wordInCHAR[rand];
                    break;
                }
            }
        }

        Server.wordEncodedShow.setLength(0);
        for (int i = 0; i < Server.wordEncoded.length; i++) {
            Server.wordEncodedShow.append(Server.wordEncoded[i]);
        }
        Server.lengthGuessed++;
    }

    private int random() {
        return (int) (Math.random() * Server.wordToGuess.length());
    }


    /*--------------------------------------
        Synchronized METHODS
    --------------------------------------*/
    private synchronized void gameControl(String received) throws IOException {
        if (received.length() < Server.wordToGuess.length() || received.length() > Server.wordToGuess.length()) {
            writeToClient.writeUTF("\t==> Server: Playing!! Use a word with " +
                    "" + Server.wordToGuess.length() + " letters");
        } else {
            if (received.equals(Server.wordToGuess)) {
                sendToAllFromServer("\tCongratulations!! " + name + " has guessed the word");
                game("START");
            } else {
                sendToAll(received);
                Server.failedWords++;

                if (Server.failedWords == 3) {
                    Server.failedWords = 0;
                    enableOneLetter();

                    if (Server.lengthGuessed != Server.wordEncodedShow.length()) {
                        sendToAllFromServer("\tRevealing one letter");
                        sendToAllFromServer("\t==> Word to Guess: " + Server.wordEncodedShow);
                    }
                }

                if (Server.lengthGuessed == Server.wordEncodedShow.length()) {
                    sendToAllFromServer("\t==> Nobody Guessed!! Word: " + Server.wordToGuess);
                    game("START");
                }

            }
        }
    }

    private synchronized void logoutClient() throws IOException {
        // Informing disconnection and Deleting client from List on Server
        StringBuilder allUsers = new StringBuilder();
        allUsers.append("#LIST_UPDATE");
        int aux = 0;
        int delete = 0;
        for (ClientHandler clients : Server.clientsVector) {
            if (!clients.name.equals(name)) {
                allUsers.append("#").append(Server.clientsVector.get(aux).name);
            } else {
                delete = aux;
            }
            aux++;
        }
        Server.clientsVector.remove(delete);
        System.out.println("\t==> " + name + " is disconnected...");
        for (ClientHandler clients : Server.clientsVector) {
            clients.writeToClient.writeUTF("\t==> " + name + " is disconnected...");
            clients.writeToClient.writeUTF(allUsers.toString());
        }

        Server.amountClients--;
        if (Server.amountClients == 0) {
            Server.adminUser = "";
        }
        if (Server.adminUser.equals(name) && Server.amountClients >= 1) {
            Server.adminUser = Server.clientsVector.get(0).name;
            Server.clientsVector.get(0).writeToClient.writeUTF("\t==> Server: NOW, You're ADMIN");
        }

        client.close();

        System.out.println("-------------------------------------" + "\nWaiting for a new client...");
    }


    /*--------------------------------------
        RUN METHOD
    --------------------------------------*/
    @Override
    public void run() {
        String received;
        while (true) {
            try {
                // Receive the string
                received = readFromClient.readUTF().trim();
                String msgToSend = "";

                if (received.equals("logout")) {
                    logoutClient();
                    break;
                }

                StringTokenizer st;
                String privateMsg;
                if (received.charAt(0) == '@') {
                    if (!Server.gameActive) {
                        st = new StringTokenizer(received, "@");
                        privateMsg = st.nextToken();

                        String[] words = privateMsg.split("\\s+");
                        int first = 0;
                        for (String word : words) {
                            if (first > 0) {
                                msgToSend += " " + word;
                            } else {
                                privateUser = word.toUpperCase();
                                first++;
                            }
                        }

                        if (!name.equals(privateUser)) {
                            // clientsVector is the vector storing active clients, in Server
                            boolean userExist = false;
                            for (ClientHandler clients : Server.clientsVector) {
                                // if the client is found, write on its output stream
                                if (clients.name.equals(privateUser)) {
                                    clients.writeToClient.writeUTF(name + " (private): " + msgToSend.trim());
                                    writeToClient.writeUTF(name + ": " + "@" + privateUser.toUpperCase() + " " + msgToSend.trim());
                                    userExist = true;
                                    break;
                                }
                            }
                            if (!userExist) {
                                writeToClient.writeUTF("\t==> Server: '@" + privateUser + "' not exists");
                            }
                        } else {
                            writeToClient.writeUTF("\t==> Server: You cannot send private msgs to yourself");
                        }
                    } else {
                        writeToClient.writeUTF("\t==> Server: You cannot send private msgs while playing");
                    }

                } else if (received.charAt(0) == '#') {
                    if (Server.adminUser.equals(name)) {
                        st = new StringTokenizer(received, "#");
                        privateMsg = st.nextToken();

                        String[] words = privateMsg.split("\\s+");
                        String first = words[0].toUpperCase();

                        if (first.equals("GAMESTART")) {
                            if (!Server.gameActive) {
                                Server.gameActive = true;
                                Server.wordEncodedShow = new StringBuilder();
                                game("START");
                            } else {
                                writeToClient.writeUTF("\t==> Server: We're already playing, use #GAMEEND");
                            }
                        } else if (first.equals("GAMEEND")) {
                            if (Server.gameActive) {
                                Server.gameActive = false;
                                game("END");
                            } else {
                                writeToClient.writeUTF("\t==> Server: First use #GAMESTART");
                            }
                        } else {
                            writeToClient.writeUTF("\t==> Server: Use #GAMESTART or #GAMEEND only");
                        }

                    } else {
                        writeToClient.writeUTF("\t==> Server: You cannot use #");
                    }

                } else {
                    if (!Server.gameActive) {
                        sendToAll(received);
                    } else {
                        gameControl(received);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // Closing resources
            readFromClient.close();
            writeToClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
