package com.juankevintrujillo;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static boolean correct = false;

    // Creating object of linkedList
    private static LinkedList linkedList = new LinkedList();

    public static void main(String[] args) {
        // write your code here
        while (!correct) {
            System.out.println(
                    "--------------------------------------\n" +
                            "\tDoubly linked list - selection:\n" +
                            "\t1) Search data\n" +
                            "\t2) Insert data into head\n" +
                            "\t3) Insert data after the element\n" +
                            "\t4) Insert data after tail\n" +
                            "\t5) Delete one element\n" +
                            "\t6) Print list from head to tail\n" +
                            "\t7) Print list from tail to head\n" +
                            "\t8) Benchmark\n" +
                            "\t9) End\n"
            );

            System.out.print("\tSelection: ");

            int numKey;
            try {
                int optionWritten = scanner.nextInt();

                switch (optionWritten) {
                    case 1:
                        if (linkedList.isListNotEmpty()) {
                            numKey = askForNumber("Insert a key to search");

                            if (isGreaterThanZero(numKey)) {
                                if (linkedList.search(numKey) != null) {
                                    System.out.println("\t|||--[" + numKey + "]--|||");
                                } else {
                                    errorMessage("The number is not in the list");
                                }
                            } else {
                                errorMessage("The number is not > 0");
                            }
                        } else {
                            errorMessage("Empty list - you cannot search elements");
                        }
                        break;
                    case 2:
                        numKey = askForNumber("Insert a key to insert");

                        if (isGreaterThanZero(numKey)) {
                            linkedList.insert_into_head(numKey);
                        } else {
                            errorMessage("The number is not > 0");
                        }
                        break;
                    case 3:
                        numKey = askForNumber("Insert a key to insert");
                        int numKeyAfter = askForNumber("Insert a key to insert after");

                        if (isGreaterThanZero(numKey) && isGreaterThanZero(numKeyAfter)) {
                            linkedList.insert_after_element(numKey, numKeyAfter);
                        } else {
                            errorMessage("The numbers has to be > 0");
                        }
                        break;
                    case 4:
                        numKey = askForNumber("Insert a key to insert");

                        if (isGreaterThanZero(numKey)) {
                            linkedList.insert_after_tail(numKey);
                        } else {
                            errorMessage("The number is not > 0");
                        }
                        break;
                    case 5:
                        if (linkedList.isListNotEmpty()) {
                            numKey = askForNumber("Insert a key to delete");

                            if (isGreaterThanZero(numKey)) {
                                Element elementToDelete = linkedList.search(numKey);

                                if (elementToDelete != null) {
                                    linkedList.delete_element(elementToDelete);
                                } else {
                                    errorMessage("The number is not in the list");
                                }
                            } else {
                                errorMessage("The number is not > 0");
                            }
                        } else {
                            errorMessage("Empty list - you cannot delete elements");
                        }
                        break;
                    case 6:
                        linkedList.print_head_to_tail();
                        break;
                    case 7:
                        linkedList.print_tail_to_head();
                        break;
                    case 8:
                        numKey = askForNumber("Insert value of N");

                        if (isGreaterThanZero(numKey)) {
                            linkedList.benchmark(numKey);
                        } else {
                            errorMessage("The number is not > 0");
                        }
                        break;
                    case 9:
                        correct = true;
                        //System.exit(0);
                        break;
                    default:
                        errorMessage(optionWritten + " is not an option (Menu-Default)\n");
                }

            } catch (Exception e) {
                errorMessage("It's not a number\n");
            }
        }
    }

    private static int askForNumber(String msn) {
        System.out.print("\t" + msn + ": ");
        return scanner.nextInt();
    }

    private static boolean isGreaterThanZero(int number) {
        return number > 0;
    }

    private static void refreshScanner() {
        scanner = new Scanner(System.in);
    }

    private static void errorMessage(String msg) {
        refreshScanner();
        System.out.println("\t\t==> ERROR: " + msg + "\n");
    }

}





