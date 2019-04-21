package com.juankevintrujillo;

import java.util.ArrayList;

class BinaryTree {

    private Node rootNode;
    private ArrayList<Integer> keysDeleted = new ArrayList<>();
    private StringBuilder keysOrdered = new StringBuilder();

    /**
     * Constructor
     */
    BinaryTree() {
        rootNode = null;
    }

    Node getRootNode() {
        return rootNode;
    }

    /**
     * 1. Insert a value
     *
     * @param value object to insert
     */
    void insert_value(int value) {
        Node newNode = new Node(value);

        if (isNotEmptyTree()) {
            Node aux = rootNode;
            Node parent;

            while (true) {
                parent = aux;
                if (value < aux.getKey()) {
                    aux = aux.getLeft();

                    if (aux == null) {
                        parent.setLeft(newNode);
                        newNode.setParent(parent);
                        break;
                    }
                } else if (value > aux.getKey()) {
                    aux = aux.getRight();

                    if (aux == null) {
                        parent.setRight(newNode);
                        newNode.setParent(parent);
                        break;
                    }
                } else {
                    errorMessage("Node [" + value + "] is already inside the tree!");
                    break;
                }
            }
        } else {
            rootNode = newNode;
        }

        successMessage("Node [" + newNode.getKey() + "] added!");

        add_deleted_key_again(value);
    }

    /**
     * 2. Ordered print of keys - IN_ORDER
     */
    void ordered_print_values(Node x) {
        if (x != null) {
            ordered_print_values(x.getLeft());
            System.out.print("\t" + x.getKey());
            ordered_print_values(x.getRight());
        }
    }

    /**
     * 3. Print of connections - PRE_ORDER
     */
    void print_values(Node x) {
        if (x.getLeft() != null) {
            System.out.println("\t" + x.getKey() + " -> " + x.getLeft().getKey());
            print_values(x.getLeft());
        }

        if (x.getRight() != null) {
            System.out.println("\t" + x.getKey() + " -> " + x.getRight().getKey());
            print_values(x.getRight());
        }
    }


    /**
     * 4. Search
     *
     * @param value object to search
     */
    Node search_node(int value) {
        Node aux = rootNode;

        while (aux.getKey() != value) {

            aux = value < aux.getKey() ? aux.getLeft() : aux.getRight();

            if (aux == null) {
                return null;
            }
        }
        return aux;
    }


    /**
     * 5. Print min/max
     */
    void print_min_max() {
        //Node aux = rootNode;

        System.out.println("\t\tMIN: " + minimum(rootNode).getKey());

        //aux = rootNode;
        System.out.println("\t\tMAX: " + maximum(rootNode).getKey());
    }

    private Node minimum(Node x) {
        while (x.getLeft() != null) {
            x = x.getLeft();
        }
        return x;
    }

    private Node maximum(Node x) {
        while (x.getRight() != null) {
            x = x.getRight();
        }
        return x;
    }

    /**
     * 6. Print successor and predecessor
     */
    void print_successor_predecessor(Node x) {
        String predecessorValue;
        String successorValue;

        // Predecessor - Max inside the left subtree
        predecessorValue = x.getLeft() != null ? String.valueOf(maximum(x.getLeft()).getKey()) : String.valueOf(x.getParent().getKey());

        // Successor - Min inside the right subtree
        Node successorNode = getsuccessor(x);
        successorValue = successorNode != null ? String.valueOf(successorNode.getKey()) : "-";

        if (predecessorValue.equals(successorValue)) {
            predecessorValue = "-";
        }

        System.out.println("\t" + predecessorValue + " <== " + x.getKey() + " ==> " + successorValue);

    }

    private Node getsuccessor(Node x) {
        if (x.getRight() != null) {
            return minimum(x.getRight());
        }

        // INVERSE PATH
        Node aux = x.getParent();
        while (aux != null && x == aux.getRight()) {
            x = aux;
            aux = aux.getParent();
        }

        return aux;
    }

    /**
     * 7. Remove a node
     *
     * @param value object to remove
     */
    boolean remove_node(int value) {
        Node aux = rootNode;
        Node parent = rootNode;
        boolean leftChild = true;

        while (aux.getKey() != value) {
            parent = aux;

            if (value < aux.getKey()) {
                leftChild = true;
                aux = aux.getLeft();
            } else {
                leftChild = false;
                aux = aux.getRight();
            }
            if (aux == null) {
                return false;
            }
        }

        // Node is leaf or root node
        if (aux.getLeft() == null && aux.getRight() == null) {
            if (aux == rootNode) {
                rootNode = null;
            } else if (leftChild) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }

            // Remove a node with one left child
        } else if (aux.getRight() == null) {
            if (aux == rootNode) {
                rootNode = aux.getLeft();
                rootNode.setParent(null);
                System.out.println("\t\tThe Replace Node is: " + aux.getLeft().getKey());
            } else if (leftChild) {
                parent.setLeft(aux.getLeft());
            } else {
                parent.setRight(aux.getLeft());
            }
            // Remove a node with one right child
        } else if (aux.getLeft() == null) {
            if (aux == rootNode) {
                rootNode = aux.getRight();
                rootNode.setParent(null);
                System.out.println("\t\tThe Replace Node is: " + aux.getRight().getKey());
            } else if (leftChild) {
                parent.setLeft(aux.getRight());
            } else {
                parent.setRight(aux.getLeft());
            }
            // Remove a node with two children
        } else {
            Node replace = getReplaceNode(aux);

            if (aux == rootNode) {
                if (aux.getLeft() != null) {
                    aux.getLeft().setParent(replace);
                }
                if (aux.getRight() != null) {
                    aux.getRight().setParent(replace);
                }

                rootNode = replace;
                rootNode.setParent(null);

            } else if (leftChild) {
                parent.setLeft(replace);
                replace.setParent(parent);
            } else {
                parent.setRight(replace);
                replace.setParent(parent);
            }
            replace.setLeft(aux.getLeft());
        }

        // This will add the element, resizing the ArrayList if necessary.
        keysDeleted.add(value);
        return true;
    }

    private Node getReplaceNode(Node nodeToReplace) {
        Node replaceParent = nodeToReplace;
        Node replace = nodeToReplace;
        Node aux = nodeToReplace.getRight();

        while (aux != null) {
            replaceParent = replace;
            replace = aux;
            aux = aux.getLeft();
        }

        if (replace != nodeToReplace.getRight()) {
            replaceParent.setLeft(replace.getRight());
            replace.setRight(nodeToReplace.getRight());
        }
        System.out.println("\t\tThe Replace Node is: " + replace.getKey());
        return replace;
    }

    boolean previously_deleted(int value) {
        for (int key : keysDeleted) {
            if (key == value) {
                return true;
            }
        }
        return false;
    }

    private void add_deleted_key_again(int value) {
        for (int i = 0; i < keysDeleted.size(); i++) {
            if (keysDeleted.get(i) == value) {
                keysDeleted.remove(i);
                break;
            }
        }
    }

    /**
     * Function to check if tree is empty
     */
    boolean isNotEmptyTree() {
        return rootNode != null; // if true then not empty
    }

    void successMessage(String msg) {
        System.out.println("\t\t==> SUCCESS: " + msg + "\n");
    }

    void errorMessage(String msg) {
        System.out.println("\t\t==> ERROR: " + msg + "\n");
    }


    /*
     * ++++++++++++++++++++++++++++++++++++++++
     *
     *
     * OTHER FUNCTIONS
     *
     *
     * ++++++++++++++++++++++++++++++++++++++++
     * */

    void print_all_nodes(Node x) {
        getValuesAllNodes(x);
        System.out.println("\t********************");

        try {
            String[] values_keys = keysOrdered.toString().split(":");

            for (String values_key : values_keys) {
                Node printNode = search_node(Integer.parseInt(values_key));
                printFormat(printNode);
            }
        } catch (Exception e) {
            System.out.println("WARNING: " + e.getMessage());
        }

        keysOrdered.setLength(0);
    }

    void printFormat(Node printNode) {
        String toPrint = (printNode.getParent() == null ? "\t\t\t-" : "\t\t\t" + printNode.getParent().getKey()) +
                "\n\t\t\t" + printNode.getKey() +
                (printNode.getLeft() == null ? "\n\t\t-" : "\n\t\t" + printNode.getLeft().getKey()) +
                (printNode.getRight() == null ? "\t\t-" : "\t\t" + printNode.getRight().getKey()) +
                "\n\t********************";
        System.out.println(toPrint);
    }

    private void getValuesAllNodes(Node x) {
        if (x.getLeft() != null) {
            addKey(x.getKey());
            getValuesAllNodes(x.getLeft());
        }

        if (x.getRight() != null) {
            addKey(x.getKey());
            getValuesAllNodes(x.getRight());
        }
    }

    private void addKey(int keyValue) {
        if (keysOrdered.length() > 0) {
            if (!isKeyInside(keyValue)) {
                keysOrdered.append(keyValue).append(":");
            }
        } else {
            keysOrdered.append(keyValue).append(":");
        }
    }

    private boolean isKeyInside(int value) {
        String[] values = keysOrdered.toString().split(":");
        int[] enteros = new int[values.length];

        for (int i = 0; i < values.length; i++) {
            enteros[i] = Integer.parseInt(values[i]);
        }

        for (int entero : enteros) {
            if (entero == value) {
                return true;
            }
        }
        return false;
    }


}


