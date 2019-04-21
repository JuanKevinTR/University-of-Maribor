package com.juankevintrujillo;

import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    // Creating object of linkedList
    private static Controller controller = new Controller();

    // String date format
    final private static String originalFormat = "yyyyMMdd";

    private static int intDateToSend;

    public static void main(String[] args) {
        // write your code here

        //insertForTesting(5);

        while (true) {
            System.out.println(
                    "--------------------------------------\n" +
                            "\tBinary search tree – operations:\n" +
                            "\t1) Insert a movie\n" +
                            "\t2) Print all movies\n" +
                            "\t3) Find movies from specific date\n" +
                            "\t4) Print minimum and maximum date\n" +
                            "\t5) Print successor and predecessor\n" +
                            "\t6) Remove all movies at specific date\n" +
                            "\t7) Load movies from file\n" +
                            "\t8) Benchmark\n" +
                            "\n\t9) End\n"
            );

            System.out.print("\tOperation: ");

            try {
                int optionWritten = scanner.nextInt();

                refreshScanner();
                switch (optionWritten) {
                    case 1:
                        // Some variables
                        String dateWritten = askForStringValue("Insert a date (dd.MM.yyyy)");
                        if (isADateValid(dateWritten)) {
                            String nameToInsert = askForStringValue("Insert the name");
                            controller.insertMovie(intDateToSend, nameToInsert);
                        } else {
                            errorMessage("It's not a valid date");
                        }
                        break;
                    case 2:
                        if (controller.isNotEmptyTree()) {
                            controller.printAllMovies();
                        } else {
                            errorMessage("Empty Tree");
                        }
                        break;
                    case 3:
                        if (controller.isNotEmptyTree()) {
                            dateWritten = askForStringValue("Insert a date (dd.MM.yyyy) to search");
                            if (isADateValid(dateWritten)) {
                                controller.searchMovies(intDateToSend);
                            } else {
                                errorMessage("It's not a valid date");
                            }
                        } else {
                            errorMessage("Empty Tree");
                        }
                        break;
                    case 4:
                        if (controller.isNotEmptyTree()) {
                            controller.printMinMaxDate();
                        } else {
                            errorMessage("Empty Tree");
                        }
                        break;
                    case 5:
                        if (controller.isNotEmptyTree()) {
                            dateWritten = askForStringValue("Insert a date (dd.MM.yyyy) to search");
                            if (isADateValid(dateWritten)) {
                                controller.printSuccessorPredecessor(intDateToSend);
                            } else {
                                errorMessage("It's not a valid date");
                            }
                        } else {
                            errorMessage("Empty Tree");
                        }
                        break;
                    case 6:
                        if (controller.isNotEmptyTree()) {
                            dateWritten = askForStringValue("Insert a date (dd.MM.yyyy) to remove");
                            if (isADateValid(dateWritten)) {
                                controller.removeMovies(intDateToSend);
                            } else {
                                errorMessage("It's not a valid date");
                            }
                        } else {
                            errorMessage("Empty Tree");
                        }
                        break;
                    case 7:
                        showFile();
                        int option = askForIntValue();

                        String pathFile = "src/com/juankevintrujillo/test_files/";

                        switch (option) {
                            case 1:
                                pathFile = pathFile + "IMDB_date_name_full.list";
                                break;
                            case 2:
                                pathFile = pathFile + "IMDB_date_name_full_sorted.list";
                                break;
                            case 3:
                                pathFile = pathFile + "IMDB_date_name_mini.list";
                                break;
                            case 4:
                                pathFile = pathFile + "IMDB_date_name_mini_sorted.list";
                                break;
                            default:
                                errorMessage(option + " is not an option\n");
                        }
                        controller.loadMoviesFromFile(pathFile);
                        break;
                    case 8:
                        controller.benchmark();
                        break;
                    case 9:
                        System.exit(0);
                        //break;
                    default:
                        errorMessage(optionWritten + " is not an option\n");
                }

            } catch (Exception e) {
                errorMessage("It's not a number or other thing happened = " + e.getMessage() + "\n");
            }
        }

    }

    private static int askForIntValue() {
        System.out.print("\t" + "Which file wants to load?: " + ": ");
        return scanner.nextInt();
    }

    private static String askForStringValue(String msn) {
        System.out.print("\t" + msn + ": ");
        return scanner.nextLine();
    }

    private static boolean isADateValid(String dateToValidate) {
        if (dateToValidate == null) {
            return false;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(originalFormat);
        simpleDateFormat.setLenient(false);

        try {
            String[] values = dateToValidate.split("\\.");

            String YEAR = values[2];
            String MONTH = values[1];
            String DAY = values[0];

            String correctFormat = YEAR + MONTH + DAY;

            //if not valid, it will throw ParseException
            intDateToSend = Integer.parseInt(correctFormat);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static void showFile() {
        System.out.println("\n" +
                "\t1. IMDB_date_name_full.list\n" +
                "\t2. IMDB_date_name_full_sorted.list\n" +
                "\t3. IMDB_date_name_mini.list\n" +
                "\t4. IMDB_date_name_mini_sorted.list\n");
    }

    /*private static void insertForTesting(int amount) {
        for (int i = 0; i < amount; i++) {
            String date = controller.randomDate();
            controller.insertMovie(Integer.parseInt(date), "1_Movie_" + date);
            controller.insertMovie(Integer.parseInt(date), "2_Movie_" + date);
        }
    }*/

    private static void refreshScanner() {
        scanner = new Scanner(System.in);
    }

    private static void errorMessage(String msg) {
        refreshScanner();
        System.out.println("\t\t==> ERROR: " + msg + "\n");
    }


}


