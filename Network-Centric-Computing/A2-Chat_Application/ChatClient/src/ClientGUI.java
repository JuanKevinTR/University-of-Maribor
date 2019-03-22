import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author J. Kevin Trujillo
 * Web: juankevintrujillo.com
 * */

public class ClientGUI extends JFrame {
    private JLabel userName;
    private JTextField nicknameTXT;
    private JButton connectButton;
    private JButton disconnectButton;
    private JTextArea chat;
    private JTextField msgTXT;
    private JButton sendButton;
    private JPanel mainPanel;
    private JTable listClientTable;
    DefaultTableModel model = (DefaultTableModel) listClientTable.getModel();

    private static Scanner scanner = new Scanner(System.in);
    private final static int PORT = 2000;
    public Socket server;
    private DataInputStream readFromServer;
    public DataOutputStream sendToServer;
    private Thread sendMessageThread;
    private Thread readMessageThread;

    StringTokenizer st;

    public ClientGUI() {
        // Setting colors
        Color bgPanel = Color.decode("#0E192B"); //  #0E192B #0E294B #00825A #5BD36D #FFFFFF
        mainPanel.setBackground(bgPanel);

        listClientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listClientTable.setCellSelectionEnabled(true);
        model.addColumn("--- List Clients ---");

        // This uses the form designer form
        add(mainPanel);

        setTitle("ChatApp - Client View");
        setSize(460, 342);
        setResizable(false);

        nicknameTXT.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {

                if (!(Character.isLetter(evt.getKeyChar())) &&
                        !(evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) &&
                        !(evt.getKeyChar() == KeyEvent.VK_ENTER) ||
                        ((evt.getKeyChar() == 'º') ||
                                (evt.getKeyChar() == 'ª') ||
                                (evt.getKeyChar() == 'ç') ||
                                (evt.getKeyChar() == 'Ç'))) {
                    JOptionPane.showMessageDialog(null, "Only letters here", "Error", JOptionPane.ERROR_MESSAGE);
                    nicknameTXT.setText("");
                    evt.consume();
                }

            }
        });

        nicknameTXT.addActionListener(e -> {
            try {
                if (!nicknameTXT.getText().equals("")) {
                    init();
                } else {
                    JOptionPane.showMessageDialog(null, "Write a username!!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        connectButton.addActionListener(e -> {
            try {
                if (!nicknameTXT.getText().equals("")) {
                    init();
                } else {
                    JOptionPane.showMessageDialog(null, "Write a username!!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        disconnectButton.addActionListener(e -> {
            try {
                disconnect();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        sendButton.addActionListener(e -> {
            if (!msgTXT.getText().equals("")) {
                send();
            } else {
                JOptionPane.showMessageDialog(null, "Write a username!!");
            }
        });
        msgTXT.addActionListener(e -> {
            if (!msgTXT.getText().equals("")) {
                send();
            } else {
                JOptionPane.showMessageDialog(null, "Write a message!!");
            }
        });

        listClientTable.getSelectionModel().addListSelectionListener(e -> {
            if (listClientTable.getSelectedRow() > -1) {
                String privateU = listClientTable.getValueAt(listClientTable.getSelectedRow(), 0).toString();
                msgTXT.setText("");
                msgTXT.setText("@" + privateU + " ");
                msgTXT.requestFocus();
                listClientTable.clearSelection();
            }
        });
    }

    private void init() throws IOException {
        // Getting localhost ip
        InetAddress IP = InetAddress.getByName("localhost");

        // Establish the connection
        server = new Socket(IP, PORT);

        // Obtaining input and out streams
        readFromServer = new DataInputStream(server.getInputStream());
        sendToServer = new DataOutputStream(server.getOutputStream());

        // Sending username
        sendToServer.writeUTF(nicknameTXT.getText());
        nicknameTXT.setEditable(false);

        // Getting correct username
        nicknameTXT.setText(readFromServer.readUTF().toUpperCase());

        // readMessage thread
        readMessageThread = new Thread(() -> {
            while (true) {
                try {
                    // read the message sent to this client
                    String msg = readFromServer.readUTF();

                    if (msg.charAt(0) != '#') {
                        chat.append(msg.concat("\n"));
                    } else {
                        st = new StringTokenizer(msg, "#");

                        if (st.nextToken().equals("LIST_UPDATE")) {

                            if (model.getRowCount() >= 1) {
                                int rowLength = model.getRowCount();

                                for (int i = rowLength - 1; i >= 0; i--) {
                                    model.removeRow(i);
                                }
                            }

                            while (st.hasMoreElements()) {
                                String cClient = st.nextToken();
                                if (!(cClient.equals(nicknameTXT.getText().toUpperCase()))) {
                                    model.addRow(new Object[]{cClient});
                                }
                            }
                        }

                    }

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        });

        // sendMessage thread
        sendMessageThread = new Thread(() -> {
            while (true) {
                // Read the message to deliver
                String msg = scanner.nextLine();

                try {
                    // Write on the output stream
                    sendToServer.writeUTF(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        readMessageThread.start();
        sendMessageThread.start();

        connectButton.setEnabled(false);
        disconnectButton.setEnabled(true);
        sendButton.setEnabled(true);
    }

    private void send() {
        // Read the message to deliver.
        String msg = msgTXT.getText();

        try {
            // Write on the output stream
            sendToServer.writeUTF(msg);
            msgTXT.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() throws IOException {
        sendToServer.writeUTF("logout");

        readFromServer.close();
        sendToServer.close();
        readMessageThread.stop();
        sendMessageThread.stop();

        System.exit(0);

        /*connectButton.setEnabled(true);
        disconnectButton.setEnabled(false);
        sendButton.setEnabled(false);*/
    }

}
