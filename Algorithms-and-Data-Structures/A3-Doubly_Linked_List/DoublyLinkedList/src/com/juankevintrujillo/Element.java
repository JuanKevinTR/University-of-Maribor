package com.juankevintrujillo;

class Element {

    private int key;
    private Element prev, next;

    /**
     * Constructor with arguments
     */
    Element(Element p, int k, Element n) {
        prev = p;
        key = k;
        next = n;
    }

    /**
     * Empty Constructor
     */
    Element(int k) {
        this(null, k, null);
    }

    int getKey() {
        return key;
    }

    /*public void setKey(int key) {
        this.key = key;
    }*/

    Element getPrev() {
        return prev;
    }

    void setPrev(Element prev) {
        this.prev = prev;
    }

    Element getNext() {
        return next;
    }

    void setNext(Element next) {
        this.next = next;
    }
}


