package com.juankevintrujillo;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    // Creating object of linkedList
    private static BinaryTree binaryTree = new BinaryTree();

    public static void main(String[] args) {
        // write your code here

        //insertForTesting(1);
        //insertForTesting(2);

        while (true) {
            System.out.println(
                    "--------------------------------------\n" +
                            "\tBinary search tree – operations:\n" +
                            "\t1) Insert a value\n" +
                            "\t2) Ordered print of keys\n" +
                            "\t3) Print of connections\n" +
                            "\t4) Search\n" +
                            "\t5) Print min/max\n" +
                            "\t6) Print successor and predecessor\n" +
                            "\t7) Remove a node\n" +
                            "\t8) End\n" //+ "\t9) Print all Nodes\n"
            );

            System.out.print("\tOperation: ");

            try {
                int optionWritten = scanner.nextInt();

                int numKey;
                switch (optionWritten) {
                    case 1:
                        numKey = askForValue("Insert a key to insert");
                        binaryTree.insert_value(numKey);
                        break;
                    case 2:
                        if (binaryTree.isNotEmptyTree()) {
                            System.out.println("\tPRINTING IN ORDER:");
                            binaryTree.ordered_print_values(binaryTree.getRootNode());
                            System.out.println("\n");
                        } else {
                            binaryTree.errorMessage("Empty Binary Tree!");
                        }
                        break;
                    case 3:
                        if (binaryTree.isNotEmptyTree()) {
                            System.out.println("\tPRINTING CONNECTIONS:");

                            if (binaryTree.getRootNode().getLeft() != null ||
                                    binaryTree.getRootNode().getRight() != null) {
                                binaryTree.print_values(binaryTree.getRootNode());
                            } else {
                                System.out.println("\t" + binaryTree.getRootNode().getKey());
                            }
                        } else {
                            binaryTree.errorMessage("Empty Binary Tree!");
                        }
                        break;
                    case 4:
                        if (binaryTree.isNotEmptyTree()) {
                            numKey = askForValue("Insert a key to search");

                            Node aux = binaryTree.search_node(numKey);

                            if (aux != null) {
                                System.out.println("\t********************");
                                binaryTree.printFormat(aux);
                            } else {
                                binaryTree.errorMessage("Node not found!");
                            }
                        } else {
                            binaryTree.errorMessage("Empty Binary Tree!");
                        }
                        break;
                    case 5:
                        if (binaryTree.isNotEmptyTree()) {
                            binaryTree.print_min_max();
                        } else {
                            binaryTree.errorMessage("Empty Binary Tree!");
                        }
                        break;
                    case 6:
                        if (binaryTree.isNotEmptyTree()) {
                            numKey = askForValue("Insert a key");

                            if (binaryTree.search_node(numKey) != null) {
                                binaryTree.print_successor_predecessor(binaryTree.search_node(numKey));
                            } else {
                                binaryTree.errorMessage("Node not found!");
                            }

                        } else {
                            binaryTree.errorMessage("Empty Binary Tree!");
                        }
                        break;
                    case 7:
                        if (binaryTree.isNotEmptyTree()) {
                            numKey = askForValue("Insert a key to remove");
                            if (binaryTree.remove_node(numKey)) {
                                binaryTree.successMessage("Node removed!");
                            } else {
                                if (binaryTree.previously_deleted(numKey)) {
                                    binaryTree.errorMessage("Node have been already removed previously!");
                                } else {
                                    binaryTree.errorMessage("Node not found!");
                                }
                            }
                        } else {
                            binaryTree.errorMessage("Empty Binary Tree!");
                        }
                        break;
                    case 8:
                        System.exit(0);
                        //break;
                    case 9:
                        if (binaryTree.isNotEmptyTree()) {
                            System.out.println("\tPRINTING EACH NODE:");
                            binaryTree.print_all_nodes(binaryTree.getRootNode());
                        } else {
                            binaryTree.errorMessage("Empty Binary Tree!");
                        }
                        break;
                    default:
                        errorMessage(optionWritten + " is not an option\n");
                }

            } catch (Exception e) {
                errorMessage("It's not a number\n");
            }
        }

    }

    private static int askForValue(String msn) {
        System.out.print("\t" + msn + ": ");
        return scanner.nextInt();
    }

    private static void insertForTesting(int option) {
        switch (option) {
            case 1:
                binaryTree.insert_value(5);
                binaryTree.insert_value(3);
                binaryTree.insert_value(2);
                binaryTree.insert_value(4);
                binaryTree.insert_value(7);
                binaryTree.insert_value(6);
                binaryTree.insert_value(8);
                break;
            case 2:
                binaryTree.insert_value(8);
                binaryTree.insert_value(4);
                binaryTree.insert_value(2);
                binaryTree.insert_value(1);
                binaryTree.insert_value(3);
                binaryTree.insert_value(5);
                binaryTree.insert_value(6);
                binaryTree.insert_value(12);
                break;
        }
    }

    private static void refreshScanner() {
        scanner = new Scanner(System.in);
    }

    private static void errorMessage(String msg) {
        refreshScanner();
        System.out.println("\t\t==> ERROR: " + msg + "\n");
    }

}
