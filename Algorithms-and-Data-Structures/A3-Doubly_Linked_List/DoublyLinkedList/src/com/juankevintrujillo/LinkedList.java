package com.juankevintrujillo;

import java.io.*;

class LinkedList {

    private Element head;   // START
    private Element tail;   // TAIL

    private int[] array;

    /**
     * Constructor
     */
    LinkedList() {
        head = null;
        tail = null;
    }

    /**
     * 1. Search data
     */
    Element search(int element) {
        Element aux = head;

        while (aux != null && aux.getKey() != element) {
            aux = aux.getNext();
        }
        return aux;
    }

    /**
     * 2. Insert data into head
     */
    void insert_into_head(int new_element) {
        if (search(new_element) == null) {
            if (isListNotEmpty()) {
                head = new Element(null, new_element, head);
                head.getNext().setPrev(head); // head.next.prev = head

                successMessage("Element [" + new_element + "] inserted into head");
            } else {
                head = tail = new Element(new_element);
                successMessage("Empty List -> First element [" + new_element + "] inserted");
            }
        } else {
            errorMessage("The key [" + new_element + "] is already in the list");
        }
    }

    /**
     * 3. Insert data after the element
     */
    void insert_after_element(int new_element, int exist_element) {
        if (search(new_element) == null) {
            Element aux = search(exist_element);

            if (aux != null) {
                Element tmp = new Element(new_element);

                tmp.setPrev(aux);
                tmp.setNext(aux.getNext());

                aux.setNext(tmp);

                if (tmp.getNext() != null) {
                    tmp.getNext().setPrev(tmp); // new_el.next.prev := new_el;
                } else {
                    tail = tmp;
                }
                successMessage("Element [" + new_element + "] inserted after element [" + exist_element + "]");
            }
        } else {
            errorMessage("The key [" + new_element + "] is already in the list");
        }
    }

    /**
     * 4. Insert data after tail
     */
    void insert_after_tail(int new_element) {
        if (search(new_element) == null) {
            if (isListNotEmpty()) {
                tail = new Element(tail, new_element, null);
                tail.getPrev().setNext(tail); // tail.prev.next = tail
                successMessage("Element [" + new_element + "] inserted after tail");
            } else {
                head = tail = new Element(new_element);
                successMessage("Empty List -> First element [" + new_element + "] inserted");
            }
        } else {
            errorMessage("The key [" + new_element + "] is already in the list");
        }
    }

    /**
     * 5. Delete one element
     */
    void delete_element(Element element) {
        if (element.getPrev() == null && element.getNext() == null) {
            head = tail = null;
            successMessage("Element [" + element.getKey() + "] deleted and the list is empty");
        } else {
            if (element.getPrev() != null) {
                element.getPrev().setNext(element.getNext()); // elem.prev.next := elem.next;
            } else {
                head = element.getNext();
                head.setPrev(null); // head.prev := NIL;
            }

            if (element.getNext() != null) {
                element.getNext().setPrev(element.getPrev()); // elem.next.prev := elem.prev
            } else {
                tail = element.getPrev();
                tail.setNext(null);
            }

            successMessage("Element [" + element.getKey() + "] deleted");
        }
    }

    /**
     * 6. Print list from head to tail
     */
    void print_head_to_tail() {
        if (isListNotEmpty()) {

            StringBuilder list = new StringBuilder();
            list.append("\t|||--");

            Element aux = head;

            while (aux != null) {
                String format = "[" + aux.getKey() + "]";
                list.append(format);

                if (aux.getNext() != null) {
                    list.append("<-->");
                }
                aux = aux.getNext();
            }
            list.append("--|||");

            System.out.println("\t\t==> PRINTING FROM HEAD TO TAIL\n\n\t\t" + list.toString() + "\n");
        } else {
            errorMessage("Empty list");
        }
    }

    /**
     * 7. Print list from tail to head
     */
    void print_tail_to_head() {
        if (isListNotEmpty()) {

            StringBuilder list = new StringBuilder();
            list.append("\t|||--");

            Element aux = tail;

            while (aux != null) {
                String format = "[" + aux.getKey() + "]";
                list.append(format);

                if (aux.getPrev() != null) {
                    list.append("<-->");
                }
                aux = aux.getPrev();
            }
            list.append("--|||");

            System.out.println("\t\t==> PRINTING FROM TAIL TO HEAD\n\n\t\t" + list.toString() + "\n");
        } else {
            errorMessage("Empty list");
        }
    }

    /**
     * 8. Benchmark
     */
    void benchmark(int N) {
        File benchmarkFile = new File("src/com/juankevintrujillo/benchmark_report.txt");
        BufferedWriter benchmark;

        try {
            benchmark = new BufferedWriter(new FileWriter(benchmarkFile));

            /*
             * 0. Clean list
             */
            head = tail = null;
            System.out.println("\n\t==> 0. List cleaned.....DONE");


            /*
             * 1. Insert into the head
             */
            long start = System.currentTimeMillis();

            for (int i = 1; i <= N; i++) {
                //insert_into_head(i);
                if (isListNotEmpty()) {
                    head = new Element(null, i, head);
                    head.getNext().setPrev(head); // head.next.prev = head
                } else {
                    head = tail = new Element(i);
                }
            }

            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            benchmark.write("Total time of inserting " + N + " elements (1 tp " + N + ") into the head (of list)\n" +
                    "\t==> Start: " + start + " milliseconds" +
                    "\t==> Finish: " + finish + " milliseconds" +
                    "\t==> Total time: " + timeElapsed / 1000F + " seconds");

            System.out.println("\t==> 1. Insert into the head.....DONE");


            /*
             * 2. Sum of keys inside the linked list
             */
            long sumKeys = 0;
            Element aux = head;

            start = System.currentTimeMillis();
            while (aux != null) {
                sumKeys = sumKeys + aux.getKey();
                aux = aux.getNext();
            }
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;

            benchmark.write("\n\t==> The sum of " + N + " keys (from the list) = " + sumKeys +
                    " (" + timeElapsed / 1000F + " seconds)\n\n");

            System.out.println("\t==> 2. Sum of keys inside the linked list.....DONE");


            /*
             * 3. Insert to the first place
             */
            benchmark.write("Total time of inserting " + N + " values (1 to " + N + ") to the first place of array " +
                    "(with shifting)\n");

            array = new int[N];

            start = System.currentTimeMillis();
            for (int i = 1; i <= N; i++) {
                // shift_values_to_right(N)
                System.arraycopy(array, 0, array, 1, N - 1);
                array[0] = i;
            }
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;

            benchmark.write("\t==> Start: " + start + " milliseconds" +
                    "\t==> Finish: " + finish + " milliseconds" +
                    "\t==> Total time: " + timeElapsed / 1000F + " seconds");

            System.out.println("\t==> 3. Insert to the first place.....DONE");


            /*
             * 4. Sum of all values (of the array)
             */
            long sumValues = 0;

            start = System.currentTimeMillis();
            sumValues = sumValuesArray(sumValues, N);
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;

            benchmark.write("\n\t==> The sum of " + N + " values (from the array and with shifting) = " + sumValues +
                    " (" + timeElapsed / 1000F + " seconds)\n\n");

            System.out.println("\t==> 4. Sum of all values (of the array and with shifting).....DONE");


            /*
             * 5. Insert to the end of array
             */
            benchmark.write("Total time of inserting " + N + " values (1 to " + N + ") to the end of array " +
                    "(without shifting)\n");

            array = new int[N];

            start = System.currentTimeMillis();

            for (int i = 0; i < N; i++) {
                array[i] = i + 1;
            }

            finish = System.currentTimeMillis();
            timeElapsed = finish - start;

            benchmark.write("\t==> Start: " + start + " milliseconds" +
                    "\t==> Finish: " + finish + " milliseconds" +
                    "\t==> Total time: " + timeElapsed / 1000F + " seconds");

            System.out.println("\t==> 5. Insert to the end of array.....DONE");

            sumValues = 0;

            start = System.currentTimeMillis();
            sumValues = sumValuesArray(sumValues, N);
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;

            benchmark.write("\n\t==> The sum of " + N + " values (from the array and without shifting) = " + sumValues +
                    " (" + timeElapsed / 1000F + " seconds)\n");

            System.out.println("\t==> 6. Sum of all values (of the array and without shifting).....DONE");

            benchmark.close();

            System.out.println("\n\t==> Report File created.....DONE");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to check if list is not empty
     */
    boolean isListNotEmpty() {
        return head != null; // if true then not empty
    }

    /*private void shift_values_to_right(int N) {
        // Shifting array elements
        for (int i = N - 1; i > 0; i--) {
            array[i] = array[i - 1];
        }
    }*/

    private long sumValuesArray(long sumValues, int N) {
        for (int i = 0; i < N; i++) {
            sumValues += array[i];
        }
        return sumValues;
    }

    private void successMessage(String msg) {
        System.out.println("\t\t==> SUCCESS: " + msg + "\n");
    }

    private void errorMessage(String msg) {
        System.out.println("\t\t==> ERROR: " + msg + "\n");
    }


}



