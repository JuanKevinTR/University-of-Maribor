package sample;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {

    // Global variables for Frequency Analysis
    private File encryptedFile = new File("src/sample/test_files/encrypted_file.txt");
    private File originalFile = new File("src/sample/test_files/reference_file.txt");
    private File decryptedFile = new File("src/sample/test_files/decrypted_file.txt");

    private HashMap<Integer, Integer> hash_encrypted;
    private HashMap<Integer, Integer> hash_original;

    private Letter[] enc_sort;
    private Letter[] ori_sort;

    private Scanner scanner;


    // Global variables for Views
    @FXML
    private ImageView frequencyArrow;
    @FXML
    private ImageView comparisonArrow;
    @FXML
    private ImageView visibleArrow;
    @FXML
    private ImageView endecryptArrow;

    @FXML
    private AnchorPane frequencyPanel;
    @FXML
    private AnchorPane comparisonPanel;
    @FXML
    private AnchorPane visiblePanel;
    @FXML
    private AnchorPane endecryptPanel;

    // FREQUENCY
    @FXML
    private JFXTextArea encryptedTextArea;
    @FXML
    private JFXTextArea originalTextArea;

    // COMPARISON
    @FXML
    private JFXTextArea comparisonTextArea;

    // VISIBLE
    @FXML
    private JFXTextArea decryptedFileTextArea;


    // ENDECRYPT
    @FXML
    private JFXTextField endeText;
    @FXML
    private JFXTextField endeOutText;
    @FXML
    private JFXRadioButton encryptRadioButton;
    @FXML
    private JFXRadioButton decryptRadioButton;
    @FXML
    private JFXTextField encryptLetterText;
    @FXML
    private JFXTextField originalLetterText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // FREQUENCY
        try {
            hash_encrypted = frecuencyAnalysis(encryptedFile);
            hash_original = frecuencyAnalysis(originalFile);

            enc_sort = sort(hash_encrypted);
            ori_sort = sort(hash_original);
            enc_ori_sort_Ordered();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR - Opening File");
            alert.setContentText("" +
                    "\n\t------------ ERROR ------------" +
                    "\n\t\tCannot open the file" +
                    "\n\t-------------------------------");
            alert.showAndWait();
        }


        // ENDECRYPT
        ToggleGroup group = new ToggleGroup();
        encryptRadioButton.setToggleGroup(group);
        decryptRadioButton.setToggleGroup(group);

        encryptLetterText.addEventFilter(KeyEvent.ANY, handlerLetters);
        originalLetterText.addEventFilter(KeyEvent.ANY, handlerLetters);


    }

    public void onViewFrequencyButtonClicked(MouseEvent event) {
        if (!frequencyPanel.isVisible()) {
            frequencyArrow.setVisible(true);
            frequencyPanel.setVisible(true);

            comparisonArrow.setVisible(false);
            comparisonPanel.setVisible(false);
            visibleArrow.setVisible(false);
            visiblePanel.setVisible(false);
            endecryptArrow.setVisible(false);
            endecryptPanel.setVisible(false);
        }

    }

    public void onViewComparisonButtonClicked(MouseEvent event) {
        if (!comparisonPanel.isVisible()) {
            comparisonArrow.setVisible(true);
            comparisonPanel.setVisible(true);

            frequencyArrow.setVisible(false);
            frequencyPanel.setVisible(false);
            visibleArrow.setVisible(false);
            visiblePanel.setVisible(false);
            endecryptArrow.setVisible(false);
            endecryptPanel.setVisible(false);
        }

    }

    public void onViewVisibleButtonClicked(MouseEvent event) {
        if (!visiblePanel.isVisible()) {
            visibleArrow.setVisible(true);
            visiblePanel.setVisible(true);

            frequencyArrow.setVisible(false);
            frequencyPanel.setVisible(false);
            comparisonArrow.setVisible(false);
            comparisonPanel.setVisible(false);
            endecryptArrow.setVisible(false);
            endecryptPanel.setVisible(false);
        }

    }

    public void onViewEnDecryptButtonClicked(MouseEvent event) {
        if (!endecryptPanel.isVisible()) {
            endecryptArrow.setVisible(true);
            endecryptPanel.setVisible(true);

            frequencyArrow.setVisible(false);
            frequencyPanel.setVisible(false);
            comparisonArrow.setVisible(false);
            comparisonPanel.setVisible(false);
            visibleArrow.setVisible(false);
            visiblePanel.setVisible(false);
        }
    }

    public void onViewExitButtonClicked(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }


    private EventHandler<KeyEvent> handlerLetters = new EventHandler<KeyEvent>() {

        private boolean willConsume = false;
        private int maxLength = 1;

        @Override
        public void handle(KeyEvent event) {
            JFXTextField temp = (JFXTextField) event.getSource();

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

            if (temp.getText().length() > maxLength - 1) {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    willConsume = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                    willConsume = false;
                }
            }

        }
    };


    public void onFrecuencyAnalisysButtonClicked(ActionEvent event) {
        encryptedTextArea.setText(printFrequency(hash_encrypted));
        originalTextArea.setText(printFrequency(hash_original));
    }

    public void onComparisonButtonClicked(ActionEvent event) {
        try {
            comparisonTextArea.setText(printComparison());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void onVisibleButtonClicked(ActionEvent event) throws IOException {
        createDecrypted();
        decryptedFileTextArea.setText(printDecryptedFile());
    }

    public void onConvertButtonClicked(ActionEvent event) {
        if (!(endeText.getText().equals("")) && (encryptRadioButton.isSelected() || decryptRadioButton.isSelected())) {

            endeOutText.setText(convertText());

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Converting text");
            alert.setContentText("You need to write a text and selected one action for converting");
            alert.showAndWait();
        }
    }

    public void onSwitchLettersButtonClicked(ActionEvent event) {
        int valueOriginalLetter = -1;
        int valueEncryptLetter = -1;

        try {
            valueOriginalLetter = (int) originalLetterText.getText().charAt(0);
            valueEncryptLetter = (int) encryptLetterText.getText().charAt(0);
        } catch (Exception e) {
            return;
        }

        if (!(valueOriginalLetter < 97 || valueEncryptLetter < 97) &&
                !(valueOriginalLetter > 122 || valueEncryptLetter > 122)) {

            switchLetters();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Switching letters");
            alert.setContentText("Your changes are set");
            alert.showAndWait();
            originalLetterText.setText("");
            encryptLetterText.setText("");

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Switching letters");
            alert.setContentText("You need to write one lower case only in both imputs");
            alert.showAndWait();
        }


    }


    /*--------------------------------------
        METHODS FOR FREQUENCY ANALYSIS
    --------------------------------------*/
    private HashMap<Integer, Integer> frecuencyAnalysis(File file) throws IOException {
        HashMap<Integer, Integer> hash_base = new HashMap<>();

        BufferedReader reader = new BufferedReader(new FileReader(file));

        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                int aux = (int) c;

                // Letter in Decimal - ASCII (A-Z 65 to 90) (a-z: 97 to 122)
                if ((aux >= 97 && aux <= 122)) {

                    int value = hash_base.getOrDefault((int) c, 0);
                    hash_base.put((int) c, value + 1);

                }
            }
        }

        reader.close();

        return hash_base;

    }

    private Letter[] sort(HashMap<Integer, Integer> hash_sort) {
        Letter[] letters = new Letter[26];
        int i = 0;
        for (int key : hash_sort.keySet()) {
            letters[i] = new Letter((char) key, hash_sort.get(key));
            i++;
        }

        for (i = 1; i < 25; i++) {
            Letter x = letters[i];

            int j = i - 1;
            while ((j >= 0) && (letters[j].getFrecuency() > x.getFrecuency())) {
                letters[j + 1] = letters[j];
                j--;
            }

            letters[j + 1] = x;
        }

        return letters;
    }

    private void enc_ori_sort_Ordered() {
        Letter[] originalOrdered = new Letter[25];
        Letter[] encryptOrdered = new Letter[25];

        int aux = 97;
        int position = 0;

        while (true) {
            if (aux > 122) {
                break;
            }

            for (int i = 0; i < 25; i++) {
                if (aux == (int) ori_sort[i].getLetter()) {
                    originalOrdered[position] = new Letter(ori_sort[i].getLetter(), ori_sort[i].getFrecuency());
                    encryptOrdered[position] = new Letter(enc_sort[i].getLetter(), enc_sort[i].getFrecuency());
                    position++;
                    break;
                }
            }
            aux++;
        }

        try {
            for (int i = 0; i < 25; i++) {
                ori_sort[i] = originalOrdered[i];
                enc_sort[i] = encryptOrdered[i];
            }
        }catch (Exception e){
            System.out.println("\t\tFallo");
        }

        ori_sort = originalOrdered;
        enc_sort = encryptOrdered;

        /*System.out.println("LONGITUD: " + ori_sort.length);

        for (int i = 0; i < 25; i++) {
            System.out.print(i + "\tOriginal: \t" + ori_sort[i].getLetter() + "\t");
            System.out.print("\tEncrypt: \t" + enc_sort[i].getLetter() + "\n");
        }*/

    }

    private String printFrequency(HashMap<Integer, Integer> hash_print) {
        StringBuilder textToprint = new StringBuilder();

        int aux = 0;
        for (int key : hash_print.keySet()) {
            aux = aux + hash_print.get(key);
        }

        textToprint.append("\tAMOUNT OF LETTERS: " + aux + "\n\n");

        for (int key : hash_print.keySet()) {
            double percentage = ((double) hash_print.get(key) / (double) aux);

            textToprint.append("\t" + (char) key + "\t----->\t" + hash_print.get(key) + "\t, " + percentage + "\n");
        }

        return textToprint.toString();
    }

    private String printComparison() {
        StringBuilder textToprint = new StringBuilder();

        textToprint.append("\t\t\tOriginal\t\t\t\tEncrypted\n\n");

        for (int i = 0; i < 25; i++) {
            textToprint.append("\t\t\t" + ori_sort[i].getLetter() + "\t\t~~~~~>\t\t" + enc_sort[i].getLetter() + "\n");
        }

        return textToprint.toString();
    }

    private void createDecrypted() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(encryptedFile));

        BufferedWriter decrypted = null;

        try {
            decrypted = new BufferedWriter(new FileWriter(decryptedFile));

            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    decrypted.write(findToDecrypted(c));
                }
                decrypted.write("\n");
            }

            reader.close();
            decrypted.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (decrypted != null) {
                decrypted.close();
            }
        }

    }

    private char findToEncrypted(char letterEncrypted) {
        boolean letterFounded = false;
        int position = -1;

        for (int j = 0; j < 25; j++) {
            if (ori_sort[j].getLetter() == letterEncrypted) {
                position = j;
                letterFounded = true;
                break;
            }
        }
        if (letterFounded) {
            return enc_sort[position].getLetter();
        } else {
            return letterEncrypted;
        }
    }

    private char findToDecrypted(char letterEncrypted) {
        boolean letterFounded = false;
        int position = -1;

        for (int j = 0; j < 25; j++) {
            if (enc_sort[j].getLetter() == letterEncrypted) {
                position = j;
                letterFounded = true;
                break;
            }
        }
        if (letterFounded) {
            return ori_sort[position].getLetter();
        } else {
            return letterEncrypted;
        }
    }

    private String printDecryptedFile() throws IOException {
        StringBuilder textToprint = new StringBuilder();

        BufferedReader reader = new BufferedReader(new FileReader(decryptedFile));

        try {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                textToprint.append(line).append("\n");
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return textToprint.toString();
    }

    private String convertText() {
        StringBuilder textToConvert = new StringBuilder();

        if (encryptRadioButton.isSelected()) {

            for (int i = 0; i < endeText.getText().length(); i++) {
                char c = endeText.getText().charAt(i);
                textToConvert.append(findToEncrypted(c));
            }

        } else if (decryptRadioButton.isSelected()) {
            for (int i = 0; i < endeText.getText().length(); i++) {
                char c = endeText.getText().charAt(i);
                textToConvert.append(findToDecrypted(c));
            }
        }

        return textToConvert.toString();
    }

    private void switchLetters() {
        //j		~~~~~>		k   z-k
        //z		~~~~~>		u   j-u

        // z-k
        for (int i = 0; i < 25; i++) {

            // Searching for 'z'
            if (originalLetterText.getText().equals(String.valueOf(ori_sort[i].getLetter()))) {

                char valueEncryptedOld = enc_sort[i].getLetter(); // z-(U)

                // Searching for 'k' (j-k old)
                for (int j = 0; j < 25; j++) {

                    if (encryptLetterText.getText().equals(String.valueOf(enc_sort[j].getLetter()))) {
                        enc_sort[j].setLetter(valueEncryptedOld); // (J=)-(U)
                        break;
                    }
                }

                enc_sort[i].setLetter(encryptLetterText.getText().charAt(0)); // z-k
                break;
            }
        }

    }

}
