package com.trujillo.juankevin;

import java.lang.System;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // Globals variables
    private static Scanner scanner;
    private static String type_number = "";
    private static boolean thereIsAnError = false;

    public static void main(String[] args) {
        // Display options
        menu();

        // Ask for an options
        int optionSelected = askForOption();

        // Ask for number
        String numberInputted = optionSelected == 1 ? askForNumberEncode() : askForNumberDecode();

        // Encode and Decode
        String printResult = optionSelected == 1 ? encode(numberInputted) : decode(numberInputted);
        System.out.println(printResult);
    }


    /**
     * MENU
     */
    private static void menu() {
        System.out.println("##################################");
        System.out.println("#								 #");
        System.out.println("#	 		MAIN MENU 		     #");
        System.out.println("#								 #");
        System.out.println("##################################");
        System.out.println("#	Press 1 for Encoding 	 	 #");
        System.out.println("#	Press 2 for Decoding 	     #");
        System.out.println("##################################\n");
    }








    /*
     * ##################################################
     * ENCODE METHODS
     * ##################################################
     */

    /**
     * @return string with number writting by user
     */
    private static String askForNumberEncode() {
        String stringInputted;
        resetScanner();

        do {
            System.out.print("Enter number for encoding" + " (binary, hexadecimal, decimal, char): ");
            stringInputted = scanner.nextLine().replaceAll("\\s", "");

            if (isBinary(stringInputted)) {
                type_number = "BINARY";
            } else if (isHexadecimal(stringInputted)) {
                type_number = "HEXADECIMAL";

                if (isDecimal(stringInputted)) {
                    type_number = isHexORDec(stringInputted) == 1 ? "HEXADECIMAL" : "DECIMAL";
                }

                if (!type_number.equals("DECIMAL")) {
                    if (isChar(stringInputted)) {
                        type_number = isHexORChar(stringInputted) == 1 ? "HEXADECIMAL" : "CHAR_SYMBOL";
                        break;
                    }
                }
            } else if (isChar(stringInputted)) {
                type_number = "CHAR_SYMBOL";
            }

            if (stringInputted.equals("") || isValid(type_number, stringInputted)) {
                System.out.println("\t----- ERROR; Invalid number -----");
                type_number = "";
            }

        } while (stringInputted.equals("") || type_number.equals(""));

        System.out.println("\n\t-----> Type = " + type_number + ": " + stringInputted);

        return stringInputted;
    }

    /**
     * @param numberToEncode number written by user
     * @return string with a text and number encoded or empty string if there is some error
     */
    private static String encode(String numberToEncode) {

        StringBuilder encodeOut = new StringBuilder();
        encodeOut.append("\tResult of UTF-8 encoding in Hexadecimal is: \t");
        StringBuilder encodeOutBinary = new StringBuilder();
        encodeOutBinary.append("\n\tResult of UTF-8 encoding in Binary is: \t\t\t");

        int numberLength = numberToEncode.length();
        int maxArrayLength = numberLength * 4;          // Need 4 bytes max in UTF8

        ArrayList<Byte> byteList = new ArrayList<>(maxArrayLength);
        byte[] encodedChar;

        String binaryToEncode;

        switch (type_number) {
            case "BINARY":
                try {
                    encodedChar = encodeChar(Long.parseLong(numberToEncode, 2));
                    addEncodedCharToByteList(byteList, encodedChar);
                } catch (Exception e) {
                    System.out.println("\t-----> Something was wrong ... \n Probably number cannot be encode in UTF-8");

                    encodeOut.setLength(0);
                    encodeOutBinary.setLength(0);
                }
                break;
            case "HEXADECIMAL":
                try {
                    binaryToEncode = codeHEX(numberToEncode);
                    encodedChar = encodeChar(Long.parseLong(binaryToEncode, 2));
                    addEncodedCharToByteList(byteList, encodedChar);
                } catch (Exception e) {
                    System.out.println("\t-----> Something was wrong ... \n\t\t Probably, number cannot be encode in UTF-8");

                    encodeOut.setLength(0);
                    encodeOutBinary.setLength(0);
                }
                break;
            case "DECIMAL":
                try {
                    binaryToEncode = decToBIN(Integer.parseInt(numberToEncode));
                    encodedChar = encodeChar(Long.parseLong(binaryToEncode, 2));
                    addEncodedCharToByteList(byteList, encodedChar);
                } catch (Exception e) {
                    System.out.println("\t-----> Something was wrong ... \n\t\t Probably, number cannot be encode in UTF-8");

                    encodeOut.setLength(0);
                    encodeOutBinary.setLength(0);
                }
                break;
            case "CHAR_SYMBOL":
                try {
                    char character = numberToEncode.charAt(0);
                    String charToBinary = Integer.toBinaryString(
                            Integer.valueOf(Integer.toHexString(character | 0x10000).substring(1), 16));
                    encodedChar = encodeChar(Long.parseLong(charToBinary, 2));
                    addEncodedCharToByteList(byteList, encodedChar);
                } catch (Exception e) {
                    System.out.println("\t-----> Something was wrong ... \n Probably number cannot be encode in UTF-8");

                    encodeOut.setLength(0);
                    encodeOutBinary.setLength(0);
                }
                break;
        }

        for (byte b : byteList) {
            encodeOut.append(Integer.toHexString(b & 0xFF).toUpperCase());
            encodeOut.append("\t\t\t");

            encodeOutBinary.append(decToBIN((b & 0xFF)));
            encodeOutBinary.append("\t");
        }

        System.out.println(encodeOutBinary);
        return encodeOut.toString();
    }

    /**
     * @param codepoint number in binary to encoded
     * @return an array of bytes with number encoded
     */
    private static byte[] encodeChar(long codepoint) {
        int FIRST_RANGE_MAX_VALUE = 0x0000007F;
        int SECOND_RANGE_MAX_VALUE = 0x000007FF;
        int THIRD_RANGE_MAX_VALUE = 0x0000FFFF;
        int FOURTH_RANGE_MAX_VALUE = 0x0010FFFF;

        byte[] encodedChar;

        System.out.println("\t-----> Codepoint: " + codepoint);

        if (codepoint <= FIRST_RANGE_MAX_VALUE) {
            // FORMAT: 0xxxxxxx

            encodedChar = new byte[1];
            encodedChar[0] = (byte) codepoint;   /* 0xxxxxxx -> since it's < 0x7F, no need for bit mask */
            System.out.println("\t-----> Grade 1 of bytes");
        } else if (codepoint <= SECOND_RANGE_MAX_VALUE) {
            // FORMAT: 110yyyyy 10xxxxxx x16

            encodedChar = new byte[2];

            encodedChar[0] = (byte) ((codepoint >>> 6 & 0b00011111) | 0b11000000);    /* 110xxxxx */

            encodedChar[1] = (byte) ((codepoint & 0b00111111) | 0b10000000);          /* 10xxxxxx */
            System.out.println("\t-----> Grade 2 of bytes");
        } else if (codepoint <= THIRD_RANGE_MAX_VALUE) {
            // FORMAT: 1110zzzz 10yyyyyy 10xxxxxx

            encodedChar = new byte[3];
            encodedChar[0] = (byte) ((codepoint >>> 12 & 0b00011111) | 0b11100000);   /* 1110xxxx */
            encodedChar[1] = (byte) ((codepoint >>> 6 & 0b00111111) | 0b10000000);    /* 10xxxxxx */
            encodedChar[2] = (byte) ((codepoint & 0b00111111) | 0b10000000);          /* 10xxxxxx */
            System.out.println("\t-----> Grade 3 of bytes");
        } else if (codepoint <= FOURTH_RANGE_MAX_VALUE) {
            // FORMAT: 11110uuu 10uuzzzz 10yyyyyy 10xxxxxx

            encodedChar = new byte[4];
            encodedChar[0] = (byte) ((codepoint >>> 18 & 0b00000111) | 0b11110000);   /* 11110xxxx */
            encodedChar[1] = (byte) ((codepoint >>> 12 & 0b00111111) | 0b10000000);   /* 10xxxxxx */
            encodedChar[2] = (byte) ((codepoint >>> 6 & 0b00111111) | 0b10000000);    /* 10xxxxxx */
            encodedChar[3] = (byte) ((codepoint & 0b00111111) | 0b10000000);          /* 10xxxxxx */
            System.out.println("\t-----> Grade 4 of bytes");
        } else throw new IllegalArgumentException("Not valid number in UTF-8");

        return encodedChar;
    }

    /*
     * AUXILIARY METHODS
     */

    /**
     * @param numberToEncode number written by user
     * @return an array of bytes with number encoded
     */
    private static String codeHEX(String numberToEncode) {
        int numberLength = numberToEncode.length();
        StringBuilder charToBinary = new StringBuilder();

        for (int i = 0; i < numberLength; i++) {
            if (numberToEncode.charAt(i) != ' ') {

                // Char to Decimal
                int charToDEC = charToDEC(numberToEncode.charAt(i));

                // Decimal to Binary
                String decToBIN = decToBIN(charToDEC);

                if (decToBIN.length() < 4) {
                    switch (decToBIN.length()) {
                        case 1:
                            charToBinary.append("000").append(decToBIN);
                            break;
                        case 2:
                            charToBinary.append("00").append(decToBIN);
                            break;
                        case 3:
                            charToBinary.append("0").append(decToBIN);
                            break;
                    }
                } else {
                    charToBinary.append(decToBIN);
                }
            }
        }

        return charToBinary.toString();
    }

    private static void addEncodedCharToByteList(ArrayList<Byte> byteList, byte[] encodedChar) {
        for (byte b : encodedChar) {
            byteList.add(b);
        }
    }








    /*
     * ##################################################
     * DECODE METHODS
     * ##################################################
     */

    /**
     * @return string with number writting by user
     */
    private static String askForNumberDecode() {
        String stringInputted;
        resetScanner();

        do {
            System.out.print("Enter number for decoding" + " (binary, hexadecimal): ");
            stringInputted = scanner.nextLine().replaceAll("\\s", "").toUpperCase();

            if (isBinary(stringInputted)) {
                type_number = "BINARY";
                break;
            } else if (isHexadecimal(stringInputted)) {
                type_number = "HEXADECIMAL";

                if (isDecimal(stringInputted)) {
                    type_number = isHexORDec(stringInputted) == 1 ? "HEXADECIMAL" : "";
                }

                if (isChar(stringInputted)) {
                    type_number = isHexORChar(stringInputted) == 1 ? "HEXADECIMAL" : "";
                }
            }

            if (stringInputted.equals("") || isValid(type_number, stringInputted)) {
                System.out.println("\t----- ERROR; Invalid number -----");
                type_number = "";
            }

        } while (stringInputted.equals("") || type_number.equals(""));

        System.out.println("\n\t-----> Type = " + type_number + ": " + stringInputted);

        return stringInputted;
    }

    /**
     * @param numberToDecode number written by user to decode
     * @return string with a text and number decoded or empty string if there is some error
     */
    private static String decode(String numberToDecode) {

        StringBuilder decodeOut = new StringBuilder();
        decodeOut.append("\tResult of UTF-8 decoding in Hexadecimal is: \tU+");

        String numberDivided;

        int length;

        switch (type_number) {
            case "BINARY":
                numberDivided = dividedNumber(numberToDecode).trim();
                String[] partsBin = numberDivided.split(" ");
                length = lengthEncoded(partsBin[0]);

                if (length != 0) {
                    decodeOut.append(decodeChar(partsBin, length)).append("\t\t\t");
                } else {
                    thereIsAnError = true;
                }


                break;
            case "HEXADECIMAL":
                String hexToBin = new BigInteger(numberToDecode, 16).toString(2);

                numberDivided = dividedNumber(hexToBin).trim();
                String[] partsHex = numberDivided.split(" ");
                length = lengthEncoded(partsHex[0]);

                if (length != 0) {
                    decodeOut.append(decodeChar(partsHex, length)).append("\t\t\t");
                } else {
                    thereIsAnError = true;
                }

                break;
        }

        if (thereIsAnError) {
            decodeOut.setLength(0);
        }

        return decodeOut.toString();
    }

    /**
     * @param encodedChar array with number written by user divided in bytes
     * @param length      length of bytes
     * @return string with a text and number decoded or empty string if there is some error
     */
    private static String decodeChar(String[] encodedChar, int length) {
        StringBuilder decodeOutDecimal = new StringBuilder();
        decodeOutDecimal.append("\n\tResult of UTF-8 decoding in Decimal is: \t\t");
        StringBuilder decodeOutBinary = new StringBuilder();
        decodeOutBinary.append("\tResult of UTF-8 decoding in Binary is: \t\t\t");

        long codepoint = 0x00000000;
        String decodedChar;
        String hexStr;

        if (isValidEncoded(encodedChar)) {
            switch (length) {
                case 1:
                    codepoint = Long.parseLong(encodedChar[0], 2); /* No need for bit masking */
                    System.out.println("\t-----> Grade 1 of bytes");
                    break;

                case 2:
                    codepoint |= ((Long.parseLong(encodedChar[0], 2) & 0b00011111) << 6);  // ___xxxxx
                    codepoint |= ((Long.parseLong(encodedChar[1], 2) & 0b00111111));       // __xxxxxx
                    System.out.println("\t-----> Grade 2 of bytes");
                    break;

                case 3:
                    codepoint |= ((Long.parseLong(encodedChar[0], 2) & 0b00001111) << 12); // ____xxxx
                    codepoint |= ((Long.parseLong(encodedChar[1], 2) & 0b00111111) << 6);  // __xxxxxx
                    codepoint |= ((Long.parseLong(encodedChar[2], 2) & 0b00111111));       // __xxxxxx
                    System.out.println("\t-----> Grade 3 of bytes");
                    break;

                case 4:
                    codepoint |= ((Long.parseLong(encodedChar[0], 2) & 0b00000111) << 18); // _____xxx
                    codepoint |= ((Long.parseLong(encodedChar[1], 2) & 0b00111111) << 12); // __xxxxxx
                    codepoint |= ((Long.parseLong(encodedChar[2], 2) & 0b00111111) << 6);  // __xxxxxx
                    codepoint |= ((Long.parseLong(encodedChar[3], 2) & 0b00111111));       // __xxxxxx
                    System.out.println("\t-----> Grade 4 of bytes");
                    break;
            }

            decodedChar = Long.toString(codepoint);

            try {
                hexStr = Long.toString(codepoint, 16);
            } catch (NumberFormatException e) {
                thereIsAnError = true;
                return "ERROR IN DECODECHAR";
            }

            switch (hexStr.length()) {
                case 1:
                    hexStr = "000" + hexStr;
                    break;
                case 2:
                    hexStr = "00" + hexStr;
                    break;
                case 3:
                    hexStr = "0" + hexStr;
                    break;
            }

            decodeOutDecimal.append(decodedChar);
            decodeOutDecimal.append("\t");

            System.out.println(decodeOutDecimal);
            System.out.println(decodeOutBinary + Long.toBinaryString(codepoint));

            char unicode = (char) codepoint;

            hexStr = hexStr.toUpperCase().concat("\n\t\t\t\t\t\t\t\t\t\t\t\t\t " + unicode + " (unicode)");

        } else {
            thereIsAnError = true;
            hexStr = "";
            System.out.println("\n\t---------------ERROR---------------");
            System.out.println("\tInvalid encoded character: ");
            System.out.println("\t-----------------------------------");
        }

        return hexStr;
    }

    /*
     * AUXILIARY METHODS
     */

    /**
     * @param firstByte string with first byte of the number written by user
     * @return length of bytes or 0 if it's not correct
     */
    private static int lengthEncoded(String firstByte) {

        int length = 0;

        if ((firstByte.charAt(0) == '0' || firstByte.charAt(0) == '1') && firstByte.charAt(1) != '1') {
            length = 1;
        }
        if (firstByte.charAt(0) == '1' && firstByte.charAt(1) == '1' && firstByte.charAt(3) != '1') {
            length = 2;
        }
        if (firstByte.charAt(0) == '1' && firstByte.charAt(1) == '1' && firstByte.charAt(2) == '1' && firstByte.charAt(3) != '1') {
            length = 3;
        }
        if (firstByte.charAt(0) == '1' && firstByte.charAt(1) == '1' && firstByte.charAt(2) == '1' && firstByte.charAt(3) == '1') {
            length = 4;
        }

        return length;
    }

    private static String dividedNumber(String number) {

        System.out.println("\t-----> Number to divide in bytes: " + number);

        StringBuilder newString = new StringBuilder();

        if (number.length() <= 8) {
            number = fixBinary(number);
        }

        for (int i = 0; i < number.length(); i++) {
            if ((i % 8 == 0) && (number.length() > 8)) {
                newString.append(" ");
            }
            newString.append(String.valueOf(number.charAt(i)));
        }

        return newString.toString();
    }

    private static String fixBinary(String number) {

        String addCero;

        switch (number.length()) {
            case 7:
                addCero = "0" + number;
                break;
            case 6:
                addCero = "00" + number;
                break;
            case 5:
                addCero = "000" + number;
                break;
            case 4:
                addCero = "0000" + number;
                break;
            case 3:
                addCero = "00000" + number;
                break;
            case 2:
                addCero = "000000" + number;
                break;
            case 1:
                addCero = "0000000" + number;
                break;
            default:
                addCero = number;
        }

        return addCero;

    }

    private static boolean isValidEncoded(String[] encodedChar) {
        boolean errorFound = false;

        switch (encodedChar.length) {
            case 1:
                if ((Long.parseLong(encodedChar[0], 2) & 0b10000000) != 0b00000000) {      /* (encodedChar[0] <=> 0xxxxxxx) ? */
                    errorFound = true;
                }
                break;

            case 2:
                if ((Long.parseLong(encodedChar[0], 2) & 0b11100000) != 0b11000000) {      /* (encodedChar[0] <=> 110xxxxx) ? */
                    errorFound = true;
                } else if ((Long.parseLong(encodedChar[1], 2) & 0b11000000) != 0b10000000) { /* (encodedChar[1] <=> 10xxxxxx) ? */
                    errorFound = true;
                }
                break;

            case 3:
                if ((Long.parseLong(encodedChar[0], 2) & 0b11110000) != 0b11100000) {      /* (encodedChar[0] <=> 1110xxxx) ? */
                    errorFound = true;
                } else if ((Long.parseLong(encodedChar[1], 2) & 0b11000000) != 0b10000000) { /* (encodedChar[1] <=> 10xxxxxx) ? */
                    errorFound = true;
                } else if ((Long.parseLong(encodedChar[2], 2) & 0b11000000) != 0b10000000) { /* (encodedChar[2] <=> 10xxxxxx) ? */
                    errorFound = true;
                }
                break;

            case 4:
                if ((Long.parseLong(encodedChar[0], 2) & 0b11111000) != 0b11110000) {      /* (encodedChar[0] <=> 11110xxx) ? */
                    errorFound = true;
                } else if ((Long.parseLong(encodedChar[1], 2) & 0b11000000) != 0b10000000) { /* (encodedChar[1] <=> 10xxxxxx) ? */
                    errorFound = true;
                } else if ((Long.parseLong(encodedChar[2], 2) & 0b11000000) != 0b10000000) { /* (encodedChar[2] <=> 10xxxxxx) ? */
                    errorFound = true;
                } else if ((Long.parseLong(encodedChar[3], 2) & 0b11000000) != 0b10000000) { /* (encodedChar[3] <=> 10xxxxxx) ? */
                    errorFound = true;
                }
                break;

            default:
                errorFound = true;
        }

        return !errorFound;
    }


    /**
     * ##################################################
     * COMMON METHODS
     * ##################################################
     */

    private static void resetScanner() {
        scanner = new Scanner(System.in);
    }

    private static int askForOption() {
        boolean validNumber = false;
        int optionInputted = 0;
        resetScanner();

        do {
            System.out.print("Please, choose on option: ");

            if (scanner.hasNextInt()) {
                optionInputted = scanner.nextInt();

                if ((optionInputted != 1) && (optionInputted != 2)) {
                    System.out.println("\t----- ERROR: Invalid option -----");
                } else {
                    validNumber = true;
                }
            } else {
                resetScanner();
                System.out.println("\t----- ERROR: Enter a valid Integer value -----");
            }
        } while (!validNumber);

        return optionInputted;
    }

    private static boolean isBinary(String number) {
        StringBuilder newString = new StringBuilder();

        String part1 = "";
        int copyOfpart1 = 0;
        String part2 = "";
        int copyOfpart2 = 0;
        String part3 = "";
        int copyOfpart3 = 0;
        String part4 = "";
        int copyOfpart4 = 0;

        for (int i = 0; i < number.length(); i++) {
            if (i % 8 == 0) {
                newString.append(" ");
            }

            newString.append(String.valueOf(number.charAt(i)));
        }

        String[] parts = newString.toString().trim().split(" ");

        if (number.length() <= 8) {
            part1 = parts[0];
        } else if (number.length() <= 16) {
            part1 = parts[0];
            part2 = parts[1];
        } else if (number.length() <= 24) {
            part1 = parts[0];
            part2 = parts[1];
            part3 = parts[2];
        } else if (number.length() <= 32) {
            part1 = parts[0];
            part2 = parts[1];
            part3 = parts[2];
            part4 = parts[3];
        }

        try {
            if (number.length() <= 8) {
                copyOfpart1 = Integer.parseInt(part1);
            } else if (number.length() <= 16) {
                copyOfpart1 = Integer.parseInt(part1);
                copyOfpart2 = Integer.parseInt(part2);
            } else if (number.length() <= 24) {
                copyOfpart1 = Integer.parseInt(part1);
                copyOfpart2 = Integer.parseInt(part2);
                copyOfpart3 = Integer.parseInt(part3);
            } else if (number.length() <= 32) {
                copyOfpart1 = Integer.parseInt(part1);
                copyOfpart2 = Integer.parseInt(part2);
                copyOfpart3 = Integer.parseInt(part3);
                copyOfpart4 = Integer.parseInt(part4);
            }
        } catch (NumberFormatException e) {
            return false;
        }

        while (copyOfpart1 != 0 || copyOfpart2 != 0 || copyOfpart3 != 0 || copyOfpart4 != 0) {
            if (copyOfpart1 % 10 > 1 || copyOfpart2 % 10 > 1 || copyOfpart3 % 10 > 1 || copyOfpart4 % 10 > 1) {
                return false;
            }
            copyOfpart1 = copyOfpart1 / 10;
            copyOfpart2 = copyOfpart2 / 10;
            copyOfpart3 = copyOfpart3 / 10;
            copyOfpart4 = copyOfpart4 / 10;
        }

        return true;
    }

    private static boolean isHexadecimal(String number) {
        if (number.length() == 0 ||
                (number.charAt(0) != '-' && Character.digit(number.charAt(0), 16) == -1)) {
            return false;
        }

        if (number.length() == 1 && number.charAt(0) == '-') {
            return false;
        }

        for (int i = 1; i < number.length(); i++) {
            if (Character.digit(number.charAt(i), 16) == -1) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDecimal(String number) {
        long copyOfInput;
        int length;

        try {
            copyOfInput = Long.parseLong(number);
            length = String.valueOf(copyOfInput).length();
        } catch (Exception e) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (Character.isLetter(number.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private static boolean isChar(String number) {
        return number.length() == 1;
    }

    private static int isHexORDec(String stringInputted) {
        int HEX_OR_DEC;
        resetScanner();

        System.out.println("\n##################################");
        System.out.println("#	Press 1 for Hexadecimal	 	 #");
        System.out.println("#	Press 2 for Decimal 	     #");
        System.out.println("##################################\n");
        System.out.println("What type of number is " + stringInputted + ", Hexadecimal or Decimal?: ");

        HEX_OR_DEC = askForOption();

        resetScanner();
        return HEX_OR_DEC;
    }

    private static int isHexORChar(String stringInputted) {
        int HEX_OR_CHAR;
        resetScanner();

        System.out.println("\n##################################");
        System.out.println("#	Press 1 for Hexadecimal	     #");
        System.out.println("#	Press 2 for Char-Symbol	     #");
        System.out.println("##################################\n");

        System.out.println("Hexadecimal in CAPITAL LETTER ...");
        System.out.println("What type of number is " + stringInputted + ", Hexadecimal or Char-Symbol?: ");

        HEX_OR_CHAR = askForOption();

        resetScanner();
        return HEX_OR_CHAR;
    }

    private static boolean isValid(String type, String number) {
        /*
         * NOT VALID:
         *   11111110 - FE - 254
         *   11111111 - FF - 255
         * */

        switch (type) {
            case "BINARY":
                if (number.equals("11111110") || number.equals("11111111")) {
                    return true;
                }
                break;
            case "HEXADECIMAL":
                number = number.toUpperCase();
                if (number.equals("FE") || number.equals("FF")) {
                    return true;
                }
                break;
            case "DECIMAL":
                if (number.equals("254") || number.equals("255")) {
                    return true;
                }
                break;
        }

        return false;
    }


    /**
     * CONVERTIONS
     */

    private static int charToDEC(char character) {
        return Integer.parseInt(String.valueOf(character), 16);
    }

    private static String decToBIN(long decimal) {
        return Long.toBinaryString(decimal);
    }


}
