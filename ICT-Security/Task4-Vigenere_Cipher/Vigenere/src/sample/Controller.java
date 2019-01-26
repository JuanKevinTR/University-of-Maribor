package sample;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    // Global variables for Vigenere cypher
    private String fileToShowName;
    private StringBuilder fileToShow;
    private String password;
    private ArrayList<Integer> list_numbers;
    private String textEncrypted;
    private boolean textHasNumbers;


    // Global variables for Views
    @FXML
    private JFXTextField fileTextField;
    @FXML
    private JFXTextField pwdTextField;

    @FXML
    private JFXTextArea normalEncryptedTextArea;
    @FXML
    private JFXTextArea resultTextArea;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileToShowName = "";
        fileToShow = new StringBuilder();
        password = "JUANKEVIN";
        pwdTextField.addEventFilter(KeyEvent.ANY, handlerLetters);
        list_numbers = new ArrayList<>();
        textEncrypted = "";
        textHasNumbers = false;
    }

    private EventHandler<KeyEvent> handlerLetters = new EventHandler<KeyEvent>() {

        private boolean willConsume = false;

        @Override
        public void handle(KeyEvent event) {
            if (willConsume) {
                event.consume();
            }

            if (!event.getCode().toString().matches("[a-zA-Z]")) {

                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    willConsume = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                    willConsume = false;
                }
            }
        }
    };


    public void onViewFileButtonClicked(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Search a file");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );

        // Getting file selected by user
        File userFile = fileChooser.showOpenDialog(null);

        if (userFile != null) {
            copyFile(userFile);
        }
    }

    public void onViewReloadButtonClicked(MouseEvent event) {
        try {
            if ((fileToShowName != "") && (fileToShow.toString() != "")) {
                fileTextField.setText(fileToShowName);
                normalEncryptedTextArea.setText(fileToShow.toString().toUpperCase());
                //resultTextArea.setText("");

                list_numbers.clear();
            } else {
                alert_Warning("Upload a file");
            }
        } catch (Exception e) {
            System.out.println("Something was wrong");
        }
    }

    public void onEncryptButtonClicked(ActionEvent event) {
        if (!normalEncryptedTextArea.getText().equals("")) {

            if (!pwdTextField.getText().equals("")) {
                password = pwdTextField.getText().toUpperCase();
            }
            pwdTextField.setText(password);
            encrypt(normalEncryptedTextArea.getText());

        } else {
            alert_Warning("Upload a file or write something");
        }
    }

    public void onDecryptButtonClicked(ActionEvent event) {
        if (!normalEncryptedTextArea.getText().equals("")) {

            if (!pwdTextField.getText().equals("")) {
                password = pwdTextField.getText().toUpperCase();
            }
            pwdTextField.setText(password);
            decrypt(normalEncryptedTextArea.getText());

        } else {
            alert_Warning("Upload a file or write something");
        }
    }

    public void onSwitchButtonClicked(ActionEvent event) {
        if (!resultTextArea.getText().equals("")) {
            normalEncryptedTextArea.setText(resultTextArea.getText());
            resultTextArea.setText("");
        }
    }

    public void onClearNormalEncryptedButtonClicked(ActionEvent event) {
        if (!normalEncryptedTextArea.getText().equals("")) {
            normalEncryptedTextArea.setText("");

            if (resultTextArea.getText().equals("")) {
                if ((fileToShowName != "") && (fileToShow.toString() != "")) {
                    fileTextField.setText("<== Upload a File || Reload file ==>");
                } else {
                    fileTextField.setText("<== Upload a File");
                }
            }

        }
    }

    public void onClearResultButtonClicked(ActionEvent event) {
        if (!resultTextArea.getText().equals("")) {
            resultTextArea.setText("");

            if (normalEncryptedTextArea.getText().equals("")) {
                if ((fileToShowName != "") && (fileToShow.toString() != "")) {
                    fileTextField.setText("<== Upload a File || Reload file ==>");
                } else {
                    fileTextField.setText("<== Upload a File");
                }
            }
        }
    }

    public void onViewExitButtonClicked(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    private void alert_Warning(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Something is wrong");
        alert.setContentText(msg);
        alert.showAndWait();
    }


    /*--------------------------------------
        METHODS FOR VIGENERE CYPHER
    --------------------------------------*/
    private void encrypt(String text) {

        if (! text.equals(textEncrypted) || ! textHasNumbers){
            list_numbers.clear();

            // C(xi) = (xi + ki) mod L
            char Cxi;   // Result of the function
            int xi;     // xi  = letter at the position of i
            char ki;    // ki  = character of password at the position of i
            int sum;    // (xi + ki)
            int L = 26; // length of the alphabet (English = 26)

            StringBuilder res = new StringBuilder();
            text = text.toUpperCase();

            for (int i = 0, j = 0; i < text.length(); i++) {
                char character = text.charAt(i);

                if (character >= '0' && character <= '9') {
                    textHasNumbers = true;
                    int number = (character - '0');
                    int encryptNumber = number + password.length();

                    if (encryptNumber > 9) {
                        list_numbers.add(number);
                    }

                    res.append(encryptNumber);

                } else if (character < 'A' || character > 'Z') {
                    continue;
                } else {
                    xi = character;
                    ki = password.charAt(j);
                    sum = (xi + ki) - (2 * 'A');        // Adding 2 letter, so, we subtract 2 x 65(A) = 130 to get a letter
                    Cxi = (char) ((sum % L) + 'A');     // Mod until length, so, we add 65(A) to get a letter

                    res.append(Cxi);
                    //res.append((char)((character + password.charAt(j) - 2 * 'A') % 26 + 'A'));

                    j = ++j % password.length();
                }
            }
            textEncrypted = res.toString();
            resultTextArea.setText(res.toString());
        }else{
            alert_Warning("The text is already encrypted, if you encrypt it again numbers will not work properly");
        }

    }

    private void decrypt(String text) {

        // D(ci) = (ci - ki) mod L
        char Dci;   // Result of the function
        int ci;     // ci  = letter cyphered at the position of i
        char ki;    // ki  = character of password at the position of i
        int sub;    // (ci - ki)
        int L = 26; // length of the alphabet (English = 26)

        StringBuilder res = new StringBuilder();
        text = text.toUpperCase();

        for (int i = 0, j = 0, k = 0; i < text.length(); i++) {
            char character = text.charAt(i);

            if (character >= '0' && character <= '9') {
                int numberEncrypted = (character - '0');
                int decryptNumber = numberEncrypted - password.length();

                if (decryptNumber >= 0) {
                    res.append(decryptNumber);
                } else {
                    int originalNumber = list_numbers.get(k);
                    res.append(originalNumber);

                    // It's not all correct
                    String jump = String.valueOf(originalNumber + password.length());
                    i = i + (jump.length() - 1);

                    k++;
                }

            } else if (character < 'A' || character > 'Z') {
                continue;
            } else {
                ci = character;
                ki = password.charAt(j);
                sub = (ci - ki) + L;                // Subtracting 2 letter, so, we add the length
                Dci = (char) ((sub % L) + 'A');     // Mod until length, so, we add 65(A) to get a letter

                res.append(Dci);
                //res.append((char) ((character - password.charAt(j) + 26) % 26 + 'A'));

                j = ++j % password.length();
            }
        }
        resultTextArea.setText(res.toString());
    }

    private void copyFile(File userFile) throws IOException {
        fileToShow.setLength(0);
        fileToShowName = userFile.getName();
        fileTextField.setText(fileToShowName);

        BufferedReader reader = new BufferedReader(new FileReader(userFile));

        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                fileToShow.append(c);
            }
        }

        reader.close();

        normalEncryptedTextArea.setText("");
        resultTextArea.setText("");

        normalEncryptedTextArea.setText(fileToShow.toString().toUpperCase());

    }

}
