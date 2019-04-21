package com.juankevintrujillo;

class Node {

    private int key;
    private Node parent, left, right;

    /**
     * Constructor with arguments
     */
    Node(int key) {
        this.key = key;
        this.parent = null;
        this.left = null;
        this.right = null;
    }

    int getKey() {
        return key;
    }

    public String toString() {
        return "\t\t\t" + key + " -->";
    }

    /*public void setKey(int key) {
        this.key = key;
    }*/

    Node getParent() {
        return parent;
    }

    void setParent(Node parent) {
        this.parent = parent;
    }

    Node getLeft() {
        return left;
    }

    void setLeft(Node left) {
        this.left = left;
    }

    Node getRight() {
        return right;
    }

    void setRight(Node right) {
        this.right = right;
    }
}
