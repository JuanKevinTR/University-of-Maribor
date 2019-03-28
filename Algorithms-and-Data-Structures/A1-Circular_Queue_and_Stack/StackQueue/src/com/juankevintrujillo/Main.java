package com.juankevintrujillo;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static int sizeMAX;

    private static String[] S;
    private static int top = 0;

    private static String[] Q;
    private static int head = 0;
    private static int tail = 0;

    public static void main(String[] args) {
        // write your code here
        setSize();
        menu();
    }

    private static void setSize() {
        while (true) {
            System.out.print("\nWelcome! Specify the maximum number of elements: ");
            try {
                sizeMAX = scanner.nextInt();

                S = new String[sizeMAX];

                for (int i = 0; i < S.length; i++) {
                    S[i] = " ";
                }

                Q = new String[sizeMAX];

                for (int i = 0; i < Q.length; i++) {
                    Q[i] = " ";
                }

                break;
            } catch (Exception e) {
                errorMessage(e.getMessage());
            }
        }
    }

    private static void menu() {
        while (true) {
            System.out.println(
                    "--------------------------------------\n" +
                            "\tStack - selection:\n" +
                            "\t1) Insert a number\n" +
                            "\t2) Read a number\n" +
                            "\t3) Print the stack\n\n" +

                            "\tQueue - selection:\n" +
                            "\t4) Insert a number\n" +
                            "\t5) Read a number\n" +
                            "\t6) Print the queue from head to tail\n\n" +

                            "\t7) Exit\n"
            );

            System.out.print("\tSelection: ");

            try {
                int optionWritten = scanner.nextInt();

                switch (optionWritten) {
                    case 1:
                        refreshScanner();
                        System.out.print("Write a value: ");
                        push(scanner.nextLine());
                        break;
                    case 2:
                        pop();
                        break;
                    case 3:
                        printArray("S");
                        break;
                    case 4:
                        refreshScanner();
                        System.out.print("Write a value: ");
                        enqueue(scanner.nextLine());
                        System.out.println("\t\t\tHEAD: " + (head + 1) + "\tTAIL: " + (tail + 1));
                        break;
                    case 5:
                        dequeue();
                        System.out.println("\t\t\tHEAD: " + (head + 1) + "\tTAIL: " + (tail + 1));
                        break;
                    case 6:
                        printArray("Q");
                        break;
                    case 7:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("\t\t==> ERROR: " + optionWritten + " is not an option\n");
                }

            } catch (Exception e) {
                errorMessage("It's not a number + " + e.getCause());
            }

        }
    }

    private static void push(String value) {
        if (top == sizeMAX) {
            errorMessage("Overflow - Array (S) is full");
        } else {
            S[top] = value;         // First index 0
            if ((top + 1) <= sizeMAX) {
                top++;
            }
        }
    }

    private static void pop() {
        if (top == 0) {
            errorMessage("Underflow - Array (S) is empty");
        } else {
            System.out.println("\n\tPOP: " + S[top - 1]); // indexes Array = max - 1 ... start in 0
            S[top - 1] = " ";
            top--;
        }
    }

    private static void enqueue(String value) {
        int new_tail = (tail % sizeMAX) + 1; // % = mod

        if (head == new_tail || (sizeMAX == new_tail && head == 0)) {
            errorMessage("Overflow - Array (Q) is full");
        } else {
            Q[tail] = value;
            if (new_tail <= sizeMAX) {
                tail = new_tail;
            }

            if (tail >= sizeMAX) {
                tail = 0; // reset tail to begin again
            }
        }
    }

    private static void dequeue() {
        if (head == tail) {
            errorMessage("Underflow - Array (Q) is empty");
        } else {
            System.out.println("\n\tDEQUEUE: " + Q[head]);
            Q[head] = " ";
            head = (head % sizeMAX) + 1;

            if (head >= sizeMAX) {
                head = 0; // reset head to begin again
            }
        }
    }

    private static void printArray(String whichArray) {
        StringBuilder showIndexes = new StringBuilder();
        StringBuilder showArrayStack = new StringBuilder();

        if (whichArray.equals("S")) {
            for (int i = 0; i < sizeMAX; i++) {
                showIndexes.append("[").append((i) + 1).append("]\t");
                showArrayStack.append(S[i]).append("\t");
            }
        } else {
            showIndexes.append("Printing from head (" + head + ") to tail (" + tail + "):");
            if (head < tail) {
                for (int i = head; i < tail; i++) {
                    showArrayStack.append(Q[i]).append("\t");
                    //System.out.println(Q[i]);
                }
            } else {
                for (int i = head; i < sizeMAX; i++) {
                    showArrayStack.append(Q[i]).append("\t");
                    //System.out.println(Q[i]);
                }
                for (int i = 0; i < tail; i++) {
                    showArrayStack.append(Q[i]).append("\t");
                    //System.out.println(Q[i]);
                }
            }
            /*for (int i = 0; i < sizeMAX; i++) {
                showIndexes.append("[").append((i) + 1).append("]\t");
                showArrayStack.append(Q[i]).append("\t");
            }*/
        }

        System.out.println("\n\t" + showIndexes.toString());
        System.out.println("\t" + showArrayStack.toString());
    }

    private static void refreshScanner() {
        scanner = new Scanner(System.in);
    }

    private static void errorMessage(String msg) {
        refreshScanner();
        System.out.println("\t\t==> ERROR: " + msg + "\n");
    }


}
