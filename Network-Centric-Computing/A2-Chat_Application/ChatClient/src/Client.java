import javax.swing.*;
import java.io.*;

/**
 *
 * @author J. Kevin Trujillo
 * Web: juankevintrujillo.com
 * */

public class Client {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClientGUI clientGUI = new ClientGUI();
            clientGUI.setVisible(true);

            clientGUI.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    try {
                        if (clientGUI.server == null) {
                            clientGUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        } else {
                            clientGUI.disconnect();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        });
    }
}
