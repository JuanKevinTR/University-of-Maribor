package com.juankevintrujillo;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static int numDisks;

    private static String[] tONE;
    private static String[] tTWO;
    private static String[] tTHREE;

    private static int Y;
    private static int Z;

    private static int upperDisk;
    private static int positionY;

    private static int positionZ;
    private static int underDisk;

    public static void main(String[] args) {
        // write your code here
        initGame();

        while (true) {
            System.out.println(
                    "--------------------------------------\n" +
                            "\tStack - selection:\n" +
                            "\t1) Restart game Tower-Hanoi\n" +
                            "\t2) Move the upper disk from tower Y to tower Z\n" +
                            "\t3) Print the entire tower\n" +
                            "\t4) Exit\n"
            );

            System.out.print("\tSelection: ");

            try {
                int optionWritten = scanner.nextInt();

                switch (optionWritten) {
                    case 1:
                        initGame();
                        break;
                    case 2:
                        checkMove();
                        if (gameOver()) {
                            System.out.println("\n\t***********************************" +
                                    "\n\t\tGAME OVER - CONGRATULATIONS" +
                                    "\n\t***********************************");
                            printTowers();
                            System.exit(0);
                        }
                        break;
                    case 3:
                        printTowers();
                        break;
                    case 4:
                        System.exit(0);
                        break;
                    default:
                        errorMessage(optionWritten + " is not an option (Menu-Default)\n");
                }

            } catch (Exception e) {
                errorMessage("(Menu) It's not a number + " + e.getCause());
            }

        }
    }

    private static void initGame() {
        while (true) {
            System.out.print("\nSpecify the number (>= 3) of disks to set the initial state: ");
            try {
                numDisks = scanner.nextInt();

                if (numDisks < 3) {
                    errorMessage("Write a number greater than or equal 3");
                } else {
                    tONE = new String[numDisks];
                    tTWO = new String[numDisks];
                    tTHREE = new String[numDisks];

                    for (int i = 0; i < numDisks; i++) {
                        StringBuilder stars = new StringBuilder();
                        for (int j = 0; j < i + 1; j++) {
                            stars.append("*");
                        }
                        tONE[i] = stars.toString();
                        tTWO[i] = " ";
                        tTHREE[i] = " ";
                    }
                    break;
                }

            } catch (Exception e) {
                errorMessage("It's not a number");
            }
        }
    }

    private static void menu() {


    }

    private static void checkMove() {
        refreshScanner();

        System.out.print("\n\tEnters Y and Z in format (Y-Z): ");
        String userInput = scanner.nextLine();

        try {
            if (userInput.length() == 3
                    && Integer.parseInt(String.valueOf(userInput.charAt(0))) >= 1
                    && Integer.parseInt(String.valueOf(userInput.charAt(0))) <= 3
                    && userInput.charAt(1) == '-'
                    && Integer.parseInt(String.valueOf(userInput.charAt(2))) >= 1
                    && Integer.parseInt(String.valueOf(userInput.charAt(2))) <= 3) {

                Y = Integer.parseInt(String.valueOf(userInput.charAt(0)));
                Z = Integer.parseInt(String.valueOf(userInput.charAt(2)));

                get_UpperDisk_PositionY();

                if (upperDisk >= 1 && upperDisk <= numDisks) {

                    get_UnderDisk_PositionZ();

                    if (upperDisk <= underDisk) {
                        moveDisk();
                    } else {
                        errorMessage("Desired action: Large disk to small one");
                    }

                } else {
                    errorMessage("The tower " + Y + " is empty");
                }
            } else {
                errorMessage("Use only number (1 2 3) and the right format 'Y-Z': ");
            }
        } catch (Exception e) {
            errorMessage("Use only number (1 2 3) and the right format 'Y-Z': ");
        }
    }

    private static void get_UpperDisk_PositionY() {
        upperDisk = 0;
        switch (Y) {    // TOWER Y
            case 1:
                for (int i = 0; i < tONE.length; i++) {
                    if (!tONE[i].equals(" ")) {
                        upperDisk = tONE[i].length();
                        positionY = i;
                        break;
                    }
                }
                break;
            case 2:
                for (int i = 0; i < tTWO.length; i++) {
                    if (!tTWO[i].equals(" ")) {
                        upperDisk = tTWO[i].length();
                        positionY = i;
                        break;
                    }
                }
                break;
            case 3:
                for (int i = 0; i < tTHREE.length; i++) {
                    if (!tTHREE[i].equals(" ")) {
                        upperDisk = tTHREE[i].length();
                        positionY = i;
                        break;
                    }
                }
                break;
        }
    }

    private static void get_UnderDisk_PositionZ() {
        underDisk = 0;
        switch (Z) {    // TOWER Z
            case 1:
                for (int i = tONE.length - 1; i >= 0; i--) {
                    if (tONE[i].equals(" ")) {
                        positionZ = i;
                        if (i == (numDisks - 1)) {
                            underDisk = numDisks;
                        }
                        break;
                    } else {
                        underDisk = tONE[i].length();
                    }
                }
                break;
            case 2:
                for (int i = tTWO.length - 1; i >= 0; i--) {
                    if (tTWO[i].equals(" ")) {
                        positionZ = i;
                        if (i == (numDisks - 1)) {
                            underDisk = numDisks;
                        }
                        break;
                    } else {
                        underDisk = tTWO[i].length();
                    }
                }
                break;
            case 3:
                for (int i = tTHREE.length - 1; i >= 0; i--) {
                    if (tTHREE[i].equals(" ")) {
                        positionZ = i;
                        if (i == (numDisks - 1)) {
                            underDisk = numDisks;
                        }
                        break;
                    } else {
                        underDisk = tTHREE[i].length();
                    }
                }
                break;
        }
    }

    private static void moveDisk() {
        switch (Z) {    // TOWER Z
            case 1:
                cleanPositionY();
                tONE[positionZ] = fillWithStars();
                break;
            case 2:
                cleanPositionY();
                tTWO[positionZ] = fillWithStars();
                break;
            case 3:
                cleanPositionY();
                tTHREE[positionZ] = fillWithStars();
                break;
        }
    }

    private static String fillWithStars() {
        StringBuilder newStars = new StringBuilder();

        for (int i = 0; i < upperDisk; i++) {
            newStars.append("*");
        }

        return newStars.toString();
    }

    private static void cleanPositionY() {
        switch (Y) {    // TOWER Y
            case 1:
                tONE[positionY] = " ";
                break;
            case 2:
                tTWO[positionY] = " ";
                break;
            case 3:
                tTHREE[positionY] = " ";
                break;
        }
    }

    private static void printTowers() {
        StringBuilder headerSpaces = new StringBuilder();
        StringBuilder disksTowers = new StringBuilder();

        StringBuilder tOneDisks = new StringBuilder();
        StringBuilder tTwoDisks = new StringBuilder();
        StringBuilder tThreeDisks = new StringBuilder();

        for (int i = 0; i < numDisks; i++) {

            disksTowers.append("\t|").append(tONE[i]).append(builtSpaces("body", tONE[i].length())).append("| ");
            disksTowers.append("|").append(tTWO[i]).append(builtSpaces("body", tTWO[i].length())).append("| ");
            disksTowers.append("|").append(tTHREE[i]).append(builtSpaces("body", tTHREE[i].length())).append("|\n");

            if (!tONE[i].equals(" ")) {
                tOneDisks.append(tONE[i].length()).append(" ");
            }

            if (!tTWO[i].equals(" ")) {
                tTwoDisks.append(tTWO[i].length()).append(" ");
            }

            if (!tTHREE[i].equals(" ")) {
                tThreeDisks.append(tTHREE[i].length()).append(" ");
            }
        }

        System.out.println("\n" +
                "\tTower1: " + tOneDisks.reverse().toString() + "\n" +
                "\tTower2: " + tTwoDisks.reverse().toString() + "\n" +
                "\tTower3: " + tThreeDisks.reverse().toString());

        headerSpaces.append(builtSpaces("header", 6));

        System.out.println("\n\t" +
                "|Tower1" + headerSpaces.toString() + "| " +
                "|Tower2" + headerSpaces.toString() + "| " +
                "|Tower3" + headerSpaces.toString() + "|");
        System.out.print(disksTowers.toString());
    }

    private static boolean gameOver() {
        boolean gameOver = false;
        for (int i = 0; i < numDisks; i++) {
            if (tONE[i].equals(" ") && tTWO[i].equals(" ") && (tTHREE[i].length() == (i + 1))) {
                gameOver = true;
            } else {
                gameOver = false;
            }
        }
        return gameOver;
    }

    private static String builtSpaces(String who, int length) {
        StringBuilder spaces = new StringBuilder();

        if (numDisks >= 6) {
            for (int j = 0; j < (numDisks - length); j++) {
                spaces.append(" ");
            }
        } else {
            if (who.equals("header")) {
                spaces.append(" ");
            } else {
                for (int j = 0; j < (7 - length); j++) {
                    spaces.append(" ");
                }
            }
        }

        return spaces.toString();
    }

    private static void refreshScanner() {
        scanner = new Scanner(System.in);
    }

    private static void errorMessage(String msg) {
        refreshScanner();
        System.out.println("\t\t==> ERROR: " + msg + "\n");
    }


}



