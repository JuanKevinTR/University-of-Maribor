package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    // Global variables for AES and RSA
    private String encoding;
    private int sizeKey;
    private SecretKey secretKey;
    private PrivateKey privateKey;
    private PublicKey pubKey;
    private Cipher cipher;

    private File fileToConvert;
    private String pathFile;

    private File fileWithKey;

    private String whatDo;
    private boolean isKeyGenerated;


    // Global variables for Views
    @FXML
    private JFXTextField fileTextField;
    @FXML
    private JFXTextField pwdTextField;
    @FXML
    private JFXTextField lengthEncode;

    @FXML
    private JFXRadioButton encryptRadioButton;
    @FXML
    private JFXRadioButton decryptRadioButton;
    @FXML
    private JFXRadioButton aesRadioButton;
    @FXML
    private JFXRadioButton rsaRadioButton;
    @FXML
    private MenuButton lengthKeyMenu;

    @FXML
    private JFXButton createKeysButton;
    @FXML
    private JFXButton uploadKeyButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        whatDo = "encrypt";

        encryptRadioButton.setSelected(true);
        uploadKeyButton.setDisable(true);
        aesRadioButton.setSelected(true);

        encoding = "AES";
        sizeKey = -1;
        isKeyGenerated = false;

        setMenuItems();
    }

    public void onViewFileButtonClicked() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        /*FileChooser.ExtensionFilter fileExtensions =
        new FileChooser.ExtensionFilter(
                "Cryptography", "*.zip", "*.exe", "*.doc");
        //fileChooser.getExtensionFilters().add(fileExtensions);*/

        // Getting file selected by user
        File userFile = fileChooser.showOpenDialog(null);

        if (userFile != null) {
            fileToConvert = userFile;
            fileTextField.setText(fileToConvert.getName());
        }
    }

    public void onEncryptClicked() {
        encryptRadioButton.setSelected(true);
        decryptRadioButton.setSelected(false);
        whatDo = "encrypt";
        if (!lengthEncode.getText().equals("")) {
            createKeysButton.setDisable(false);
            uploadKeyButton.setDisable(true);
        } else {
            createKeysButton.setDisable(true);
            uploadKeyButton.setDisable(true);
        }
    }

    public void onDecryptClicked() {
        encryptRadioButton.setSelected(false);
        decryptRadioButton.setSelected(true);
        whatDo = "decrypt";
        if (!lengthEncode.getText().equals("")) {
            createKeysButton.setDisable(true);
            uploadKeyButton.setDisable(false);
        } else {
            createKeysButton.setDisable(true);
            uploadKeyButton.setDisable(true);
        }
    }

    public void onAESClicked() {
        aesRadioButton.setSelected(true);
        rsaRadioButton.setSelected(false);
        encoding = "AES";
        setMenuItems();
    }

    public void onRSAClicked() {
        aesRadioButton.setSelected(false);
        rsaRadioButton.setSelected(true);
        encoding = "RSA";
        setMenuItems();
    }

    public void onCreateKeysButtonClicked() throws NoSuchAlgorithmException, IOException {
        // KEY
        if (encoding.equals("AES")) {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(encoding);
            keyGenerator.init(sizeKey);

            secretKey = keyGenerator.generateKey();

            pathFile = "src/sample/test_files/AES/0.asymmetricKey_AES" + sizeKey + ".txt";

            FileOutputStream fos = new FileOutputStream(pathFile);
            fos.write(secretKey.getEncoded()); // we write the array of bytes of key created
            fos.close();

            isKeyGenerated = true;
            alertGood("Key generated and saved successfully");
            pwdTextField.setText("KeyS Available for " + encoding + "-" + sizeKey);
            //System.out.println("CLAVE ENCRYPT: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        } else {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(encoding);
            keyPairGenerator.initialize(sizeKey);
            KeyPair keyPair = keyPairGenerator.genKeyPair();

            pubKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();

            pathFile = "src/sample/test_files/RSA/0.publicKey_RSA" + sizeKey + ".txt";

            FileOutputStream fos = new FileOutputStream(pathFile);
            fos.write(pubKey.getEncoded()); // we write the array of bytes of key created
            fos.close();

            isKeyGenerated = true;
            alertGood("Keys generated, and public saved successfully");
            pwdTextField.setText("KeyS Available for " + encoding + "-" + sizeKey);
        }
    }

    public void onUploadKeyButtonClicked() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        FileChooser fileChooser = new FileChooser();

        // Getting file selected by user
        File userKeyFile = fileChooser.showOpenDialog(null);

        if (userKeyFile != null) {
            fileWithKey = userKeyFile;

            FileInputStream fis = new FileInputStream(fileWithKey);
            byte[] encodedKey = new byte[(int) fileWithKey.length()];

            fis.read(encodedKey);

            if (encoding.equals("AES")) {
                secretKey = new SecretKeySpec(encodedKey, 0, (int) fileWithKey.length(), encoding);
            } else {
                KeyFactory keyFactory = KeyFactory.getInstance(encoding);
                X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedKey);
                pubKey = keyFactory.generatePublic(publicKeySpec);
            }

            fis.close();
            isKeyGenerated = true;
            alertGood("Key saved successfully");
            pwdTextField.setText("KeyS Available for " + encoding + "-" + sizeKey);
        }
    }

    public void onViewRunButtonClicked() {
        if ((encryptRadioButton.isSelected() || decryptRadioButton.isSelected()) &&
                (aesRadioButton.isSelected() || rsaRadioButton.isSelected()) &&
                sizeKey != -1 && fileToConvert != null
        ) {
            if (isKeyGenerated) {
                switch (whatDo) {
                    case "encrypt":
                        encrypt();
                        break;
                    case "decrypt":
                        decrypt();
                        break;
                }
            } else {
                alertWarning("Check if you have generated/upload the key/S needed");
            }
        } else {
            alertWarning("Upload a file to encrypt/decrypt");
        }
    }

    public void onViewExitButtonClicked() {
        Platform.exit();
        System.exit(0);
    }

    private void setMenuItems() {
        lengthKeyMenu.getItems().clear();
        sizeKey = -1;
        isKeyGenerated = false;
        lengthEncode.setText("");
        pwdTextField.setText("Generate / Upload a keyS");
        createKeysButton.setDisable(true);
        uploadKeyButton.setDisable(true);

        if (encoding.equals("AES")) {
            MenuItem AES128 = new MenuItem("AES 128");
            AES128.setOnAction(t -> {
                sizeKey = 128;
                lengthEncode.setText("AES 128");
                pwdTextField.setText("Generate / Upload a keyS-128");
                if (whatDo.equals("encrypt")) {
                    isKeyGenerated = false;
                    createKeysButton.setDisable(false);
                } else {
                    uploadKeyButton.setDisable(false);
                }
            });
            MenuItem AES192 = new MenuItem("AES 192");
            AES192.setOnAction(t -> {
                sizeKey = 192;
                lengthEncode.setText("AES 192");
                pwdTextField.setText("Generate / Upload a keyS-192");
                if (whatDo.equals("encrypt")) {
                    isKeyGenerated = false;
                    createKeysButton.setDisable(false);
                } else {
                    uploadKeyButton.setDisable(false);
                }
            });

            MenuItem AES256 = new MenuItem("AES 256");
            AES256.setOnAction(t -> {
                sizeKey = 256;
                lengthEncode.setText("AES 256");
                pwdTextField.setText("Generate / Upload a keyS-256");
                if (whatDo.equals("encrypt")) {
                    isKeyGenerated = false;
                    createKeysButton.setDisable(false);
                } else {
                    uploadKeyButton.setDisable(false);
                }
            });

            lengthKeyMenu.getItems().add(AES128);
            lengthKeyMenu.getItems().add(AES192);
            lengthKeyMenu.getItems().add(AES256);
        } else {
            MenuItem RSA1024 = new MenuItem("RSA 1024");
            RSA1024.setOnAction(t -> {
                sizeKey = 1024;
                lengthEncode.setText("RSA 1024");
                pwdTextField.setText("Generate / Upload a keyS-1024");
                if (whatDo.equals("encrypt")) {
                    isKeyGenerated = false;
                    createKeysButton.setDisable(false);
                } else {
                    uploadKeyButton.setDisable(false);
                }
            });

            MenuItem RSA2048 = new MenuItem("RSA 2048");
            RSA2048.setOnAction(t -> {
                sizeKey = 2048;
                lengthEncode.setText("RSA 2048");
                pwdTextField.setText("Generate / Upload a keyS-2048");
                if (whatDo.equals("encrypt")) {
                    isKeyGenerated = false;
                    createKeysButton.setDisable(false);
                } else {
                    uploadKeyButton.setDisable(false);
                }
            });

            lengthKeyMenu.getItems().add(RSA1024);
            lengthKeyMenu.getItems().add(RSA2048);
        }
    }

    private void alertWarning(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Something is wrong");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void alertGood(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Everything right!");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void encrypt() {
        // Cipher
        try {

            byte[] fileByteArray = Files.readAllBytes(fileToConvert.toPath());
            cipher = Cipher.getInstance(encoding);
            byte[] cipherText;

            if (encoding.equals("AES")) {
                pathFile = "src/sample/test_files/AES/1.encrypted_AES" + sizeKey + "_" + fileToConvert.getName();
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            } else {
                pathFile = "src/sample/test_files/RSA/1.encrypted_RSA" + sizeKey + "_" + fileToConvert.getName();
                cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            }

            cipherText = cipher.doFinal(fileByteArray);
            FileOutputStream fos = new FileOutputStream(pathFile);
            fos.write(cipherText);
            fos.close();

            alertGood("Encrypted file with " + encoding + "-" + sizeKey + " saved");

            reset();

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException e) {
            isKeyGenerated = false;
            System.out.println("\t==> Exception: " + e.getMessage());
            alertWarning(e.getMessage());
        }
    }

    private void decrypt() {
        // Decipher
        try {

            byte[] fileEncryptedBytes = Files.readAllBytes(fileToConvert.toPath());
            cipher = Cipher.getInstance(encoding);
            byte[] cipherText;

            if (encoding.equals("AES")) {
                pathFile = "src/sample/test_files/AES/2.decrypted_AES" + sizeKey + "_" + fileToConvert.getName();
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            } else {
                pathFile = "src/sample/test_files/RSA/2.decrypted_RSA" + sizeKey + "_" + fileToConvert.getName();
                cipher.init(Cipher.DECRYPT_MODE, pubKey);
            }

            cipherText = cipher.doFinal(fileEncryptedBytes);

            FileOutputStream fos = new FileOutputStream(pathFile);
            fos.write(cipherText);
            fos.close();
            alertGood("Decrypted File saved");

        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            isKeyGenerated = false;
            alertWarning("Wrong key for decrypting the file");
            System.out.println("\t==> Exception: " + e.getMessage());
        }

        reset();

    }

    private void reset() {
        fileTextField.setText("<== Upload a File");

        fileWithKey = new File("");
        pathFile = "";
        setMenuItems();
    }

}
