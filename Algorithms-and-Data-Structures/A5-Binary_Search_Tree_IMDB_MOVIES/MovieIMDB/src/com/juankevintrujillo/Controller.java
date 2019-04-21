package com.juankevintrujillo;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Controller {

    private TreeMap<Integer, LinkedList<String>> treeMap;
    private LinkedList<String> linkedList;

    /**
     * Constructor
     */
    Controller() {
        treeMap = new TreeMap<>();
    }


    /**
     * 1. Insert a movie
     *
     * @param date object to insert
     */
    void insertMovie(Integer date, String name) {
        if (searchIndexInTree(date)) {
            linkedList = treeMap.get(date);
            linkedList.add(name);
        } else {
            linkedList = new LinkedList<>();
            linkedList.add(name);

            treeMap.put(date, linkedList);
        }
        //successMessage("Film '" + name + "' added!");
    }

    /**
     * 2. Print all movies
     */
    void printAllMovies() {
        // Display elements
        for (Map.Entry<Integer, LinkedList<String>> entry : treeMap.entrySet()) {
            System.out.println("\t********************");
            System.out.println("\t" + intToDate(entry.getKey()));

            linkedList = entry.getValue();
            for (String name : linkedList) {
                System.out.println("\t\t" + name);
            }
        }
    }

    /**
     * 3. Find movies from specific date
     * <p>
     * QUESTION: (exclude measuring the printout)
     *
     * @param date object to insert
     */
    void searchMovies(int date) {
        if (searchIndexInTree(date)) {
            System.out.println("\t********************");
            linkedList = treeMap.get(date);

            for (String name : linkedList) {
                System.out.println("\t\t" + name);
            }
        } else {
            errorMessage("The date is not in the tree");
        }
    }

    private boolean searchIndexInTree(int index) {
        // Get a datesInTree of the entries
        Set<Map.Entry<Integer, LinkedList<String>>> datesInTree = treeMap.entrySet();

        // Display elements
        for (Object date : datesInTree) {
            Map.Entry map = (Map.Entry) date;

            if ((int) map.getKey() == index) {
                return true;
            }
        }

        return false;
    }

    /**
     * 4. Print minimum and maximum date
     */
    void printMinMaxDate() {
        String MIN = intToDate(treeMap.firstKey());
        String MAX = intToDate(treeMap.lastKey());
        System.out.println("\tMinimum: " + MIN + "\tand\tMaximum: " + MAX);
    }

    /**
     * 5. Print successor and predecessor
     */
    void printSuccessorPredecessor(int date) {
        if (searchIndexInTree(date)) {
            String dateToCheck = intToDate(date);
            int predecessor = -1;
            int successor = -1;

            for (Map.Entry<Integer, LinkedList<String>> entry : treeMap.entrySet()) {
                if (entry.getKey() < date) {
                    predecessor = entry.getKey();
                } else if (entry.getKey() > date) {
                    successor = entry.getKey();
                    break;
                }
            }

            String predecessorDate;
            String successorDate;
            if (predecessor != -1 && successor != -1) {
                predecessorDate = intToDate(predecessor);
                successorDate = intToDate(successor);
                System.out.println("\t" + predecessorDate + " <== " + dateToCheck + " ==> " + successorDate);
            } else if (predecessor != -1) {
                predecessorDate = intToDate(predecessor);
                System.out.println("\t" + predecessorDate + " <== " + dateToCheck + " ==> -");
            } else if (successor != -1) {
                successorDate = intToDate(successor);
                System.out.println("\t - <== " + dateToCheck + " ==> " + successorDate);
            }
        } else {
            errorMessage("The date is not in the tree");
        }
    }

    /**
     * 6. Remove all movies at specific date
     *
     * @param date object to insert
     */
    void removeMovies(int date) {
        if (searchIndexInTree(date)) {
            treeMap.remove(date);
            successMessage("Date removed!");
        } else {
            errorMessage("The date is not in the tree");
        }
    }

    /**
     * 7. Load movies from file
     */
    void loadMoviesFromFile(String pathFile) {
        try {
            String line;
            FileReader fr = new FileReader(pathFile);
            BufferedReader br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {
                String[] values = line.split("\\s+");
                if (values.length > 2) {
                    int date = Integer.parseInt(values[0]);

                    StringBuilder fullName = new StringBuilder();
                    for (int i = 1; i < values.length; i++) {
                        fullName.append(values[i]).append(" ");
                    }
                    insertMovie(date, fullName.toString());
                }
            }
            br.close();
        } catch (IOException e) {
            errorMessage(e.getMessage());
        }
        //successMessage("It's everything imported");
    }

    /**
     * 8. Benchmark
     */
    void benchmark() {
        int[] randomDates = new int[5];

        File benchmarkFile = new File("src/com/juankevintrujillo/benchmark_report.txt");
        BufferedWriter benchmark;

        try {
            benchmark = new BufferedWriter(new FileWriter(benchmarkFile));


            /*
             * 1. Reading file and inserting all movies into the tree (no.3 - IMDB_date_name_mini.list)
             */
            System.out.println("\t==> 1. Starting with IMDB_date_name_mini.list.....");
            treeMap = new TreeMap<>(); // Clean tree

            long start = System.currentTimeMillis();
            loadMoviesFromFile("src/com/juankevintrujillo/test_files/IMDB_date_name_mini.list");
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;

            benchmark.write("Total time of reading the file 'IMDB_date_name_mini.list' and " +
                    "inserting all movies into the tree\n" +
                    "\t==> Start: " + start + " milliseconds" +
                    "\t==> Finish: " + finish + " milliseconds" +
                    "\t==> Total time: " + timeElapsed / 1000F + " seconds");

            System.out.println("\t==> 1.1 Generating random dates.....");

            benchmark.write("\n\t==> Random dates:\t");

            for (int i = 0; i < randomDates.length; i++) {
                String date = randomDate();

                randomDates[i] = Integer.parseInt(date);
                benchmark.write(intToDate(Integer.parseInt(date)) + "\t");
            }

            System.out.println("\t==> 1.2 Searching data from random days.....");

            start = System.currentTimeMillis();
            for (int i = 0; i < randomDates.length; i++) {
                benchmark.write("\n\t\t==> " + intToDate(randomDates[i]) + "\n");

                if (searchIndexInTree(randomDates[i])) {
                    linkedList = treeMap.get(randomDates[i]);

                    for (String name : linkedList) {
                        benchmark.write("\t\t\t==> " + name + "\n");
                    }
                } else {
                    benchmark.write("\t\t\t==> Not founded!");
                }
            }
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;

            benchmark.write("\n\t==> Total time of searching 5 random days: " + timeElapsed / 1000F + " seconds");


            System.out.println("\t==> 1. IMDB_date_name_mini.list.....FINISH\n");



            /*
             * 2. Reading file and all movies into the tree (no.4 - IMDB_date_name_mini_sorted.list)
             */
            System.out.println("\t==> 2. IMDB_date_name_mini_sorted.list.....WORKING");
            treeMap = new TreeMap<>(); // Clean tree

            start = System.currentTimeMillis();
            loadMoviesFromFile("src/com/juankevintrujillo/test_files/IMDB_date_name_mini_sorted.list");
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;

            benchmark.write("\n\nTotal time of reading the file 'IMDB_date_name_mini_sorted.list' and " +
                    "inserting all movies into the tree\n" +
                    "\t==> Start: " + start + " milliseconds" +
                    "\t==> Finish: " + finish + " milliseconds" +
                    "\t==> Total time: " + timeElapsed / 1000F + " seconds");


            System.out.println("\t==> 2.1 Generating random dates.....");

            benchmark.write("\n\t==> Random dates:\t");

            randomDates = new int[5];

            for (int i = 0; i < randomDates.length; i++) {
                String date = randomDate();

                randomDates[i] = Integer.parseInt(date);
                benchmark.write(intToDate(Integer.parseInt(date)) + "\t");
            }


            start = System.currentTimeMillis();
            for (int i = 0; i < randomDates.length; i++) {
                benchmark.write("\n\t\t==> " + intToDate(randomDates[i]) + "\n");

                if (searchIndexInTree(randomDates[i])) {
                    linkedList = treeMap.get(randomDates[i]);

                    for (String name : linkedList) {
                        benchmark.write("\t\t\t==> " + name + "\n");
                    }
                } else {
                    benchmark.write("\t\t\t==> Not founded!");
                }
            }
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;

            benchmark.write("\n\t==> Total time of searching 5 random days: " + timeElapsed / 1000F + " seconds");


            System.out.println("\t==> 2 IMDB_date_name_mini_sorted.list.....FINISH");



            /*
             * 3-4 Regarding to 'IMDB_date_name_full.list' and 'IMDB_date_name_full_sorted.list'
             */
            benchmark.write("\n\n\n" +
                    "Regarding to 'IMDB_date_name_full.list' and 'IMDB_date_name_full_sorted.list' files, both files have many movies and \n" +
                    "reading and inserting them, are too slow");


            benchmark.close();
            System.out.println("\n\t==> Report File created.....DONE");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Make a random between two values
     */
    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    String randomDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int myYEAR;
        int myMONTH;
        int myDAY;

        StringBuilder dateToAdd = new StringBuilder();

        myYEAR = randBetween(1900, 2019);
        gc.set(gc.YEAR, myYEAR);
        dateToAdd.append(myYEAR);

        myMONTH = randBetween(1, gc.getActualMaximum(gc.MONTH));
        gc.set(gc.MONTH, myMONTH);
        if (myMONTH < 10) {
            dateToAdd.append(0).append(myMONTH);
        } else {
            dateToAdd.append(myMONTH);
        }

        myDAY = randBetween(1, gc.getActualMaximum(gc.DAY_OF_MONTH));
        gc.set(gc.DAY_OF_MONTH, myDAY);
        if (myDAY < 10) {
            dateToAdd.append(0).append(myDAY);
        } else {
            dateToAdd.append(myDAY);
        }
        return dateToAdd.toString();
    }

    /**
     * Convert a Date in int format to String format
     *
     * @return date in String format
     */
    private String intToDate(int intDate) {
        String dateToReturn = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = sdf.parse(String.valueOf(intDate));
            SimpleDateFormat newFormat = new SimpleDateFormat("dd.MM.yyyy");
            dateToReturn = newFormat.format(date);
        } catch (ParseException e) {
            errorMessage("Parsing date in intToDate");
            e.printStackTrace();
        }
        return dateToReturn;
    }

    /**
     * Function to check if tree is not empty
     */
    boolean isNotEmptyTree() {
        return !treeMap.isEmpty(); // if true then empty, ! false
    }

    private void successMessage(String msg) {
        System.out.println("\t\t==> SUCCESS: " + msg + "\n");
    }

    private void errorMessage(String msg) {
        System.out.println("\t\t==> ERROR: " + msg + "\n");
    }


}



