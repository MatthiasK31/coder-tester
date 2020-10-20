/*
Matthias Kim
Tester/Coder Lab
9/18/2020
Extra: user can run the program again (if desired so)
*/

import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class finalCode {
    public static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        char userChoice = ' ';
        boolean hmm = false;
        //run the program once initially
        runProgram();
        do {
            System.out.println("\nWould you like to run the program again? (y/n)");
            userChoice = s.next().charAt(0);
            if (userChoice == 'y') {
                System.gc();
                runProgram();
            }
            if (userChoice == 'n') {
                hmm = true;
                System.exit(1);
            }
        } while (!hmm);
    }

    public static void runProgram() {
        try {
            //setup with variables
            File classNames = new File("src/names");
            Scanner reader = new Scanner(classNames);
            int totalStudents = 0, b1 = 0, b2 = 0;
            int blockChoice = 0;

            //ask for user input to determine the block used
            System.out.println("\nThis program will randomly match coders and testers from each/both blocks.");
            System.out.println("Please type the respective number for which block(s)'s pairs you'd like to see.\n1) Block 1 \n2) Block 2 \n3) Blocks 1 & 2\nEnter your choice: ");
            blockChoice = s.nextInt();
            System.out.println("How would you like to see the sorted output? \n1) By Coder\n2) By Tester\n3) Print Both\nEnter your choice: ");
            int sortingChoice = s.nextInt();

            //run loop until it has gone through the entire file
            //add to a variable for block 1/block2
            do {
                String tempReader = reader.nextLine();
                if (tempReader.charAt(0) == '1') {
                    b1++;
                }
                if (tempReader.charAt(0) == '2') {
                    b2++;
                }
            } while (reader.hasNextLine());
            totalStudents = b1 + b2;

            //print the info for the user to see
            switch (blockChoice) {
                case 1: {
                    System.out.println("Block 1 has " + b1 + " total students.");
                    break;
                }
                case 2: {
                    System.out.println("Block 2 has " + b2 + " total students.");
                    break;
                }
                case 3: {
                    System.out.println("Block 1 has " + b1 + " students.");
                    System.out.println("Block 2 has " + b2 + " students.");
                    System.out.println("There are " + totalStudents + " total students.");
                    break;
                }
            }

            //reset the scanner
            reader = new Scanner(classNames);

            //put all the names from the file into an array
            String[] studentNames = new String[totalStudents + 1];
            for (int i = 0; i < totalStudents; i++) {
                studentNames[i] = reader.nextLine();
            }

            //determine which area of the file is going to be read
            String[] range = {};
            int startRead = 0, endRead = 0;
            int tempCounter;
            char tempChar = ' ';
            if (blockChoice == 1) {
                endRead = b1;
                tempChar = '1';

                range = new String[endRead - startRead];
                tempCounter = 0;
                for (int i = startRead; i < endRead; i++) {
                    if (studentNames[i].charAt(0) == tempChar) {
                        range[tempCounter] = studentNames[i];
                        tempCounter++;
                    }
                    //exit the loop once the block 1/2 names are done
                    else {
                        break;
                    }
                }
            } else if (blockChoice == 2) {
                startRead = b1;
                endRead = b1 + b2;
                tempChar = '2';
                tempCounter = 0;
                range = new String[endRead - startRead];
                for (int i = startRead; i < endRead; i++) {
                    if (studentNames[i].charAt(0) == tempChar) {
                        range[tempCounter] = studentNames[i];
                        tempCounter++;
                    }
                    //exit the loop once the block 1/2 names are done
                    else {
                        break;
                    }
                }
            } else if (blockChoice == 3) {
                range = new String[b1 + b2];
                tempCounter = 0;
                do {
                    range[tempCounter] = studentNames[tempCounter];
                    tempCounter++;
                } while (tempCounter < b1 + b2);
            }

            String[] coders = new String[range.length];
            String[] testers = new String[range.length];
            //a boolean to tell whether the coder/tester has been taken at each place
            boolean[] isTaken = new boolean[range.length];

            //take all the coder names out of the desired selection (block)
            int increment = 0;
            for (int i = 0; i < range.length; i++) {
                coders[increment] = range [increment];
                increment++;
            }


            //checking whether a  name has been matched already
            int testerPlace;
            for (int i = 0; i < range.length; i++) {
                testerPlace = (int) Math.floor(Math.random() * range.length);
                if (coders[testerPlace].equals(testers[testerPlace])) {
                    //changes the tester array placer at least once, but keeps going until the coder and tester are not equal
                    while (coders[testerPlace].equals(testers[testerPlace])) {
                        testerPlace = (int) Math.floor(Math.random() * range.length);

                    }
                }
                //if the names are already taken re-roll the number
                if (isTaken[testerPlace]) {
                    while (isTaken[testerPlace]) {
                        testerPlace = (int) Math.floor(Math.random() * range.length);
                    }
                }

                //check whether the coder & tester do not match, then set the bool to true and rerun the loop
                if (!coders[testerPlace].equals(testers[testerPlace])) {
                    isTaken[testerPlace] = true;
                    testers[i] = range[testerPlace];
                }
            }

            //alphabetical sort method


            //format and print output based on the user's choice of sorting

            //sorting by coder
            if (sortingChoice == 1) {
                for (int i = 0; i < range.length; i++) {
                    for (int j = i + 1; j < range.length; j++) {
                        if (coders[i].compareTo(coders[j]) > 0) {
                            String temp = coders[i];
                            String temp2 = testers[i];
                            coders[i] = coders[j];
                            testers[i] = testers[j];
                            coders[j] = temp;
                            testers[j] = temp2;
                        }
                    }
                }
                System.out.format("%23s %40s", "Coder", "Tester");
                System.out.println("\n");
                System.out.format("%-15s %-15s %-10s %-15s %-15s %-10s", "First Name", "Last Name", "Block", "First Name", "Last Name", "Block");
                System.out.println("\n--------------------------------------------------------------------------------");
                for (int i = 0; i < coders.length; i++) {
                    int firstCComma = coders[i].indexOf(",") + 1;
                    int firstTComma = testers[i].indexOf(",") + 1;
                    int lastCComma = coders[i].lastIndexOf(",");
                    int lastTComma = testers[i].lastIndexOf(",");
                    char studentCBlock = coders[i].charAt(0);
                    char studentTBlock = testers[i].charAt(0);
                    System.out.format("%-15s %-15s %-10s %-15s %-15s %-10s", coders[i].substring(lastCComma + 1), coders[i].substring(firstCComma, lastCComma), studentCBlock, testers[i].substring(lastTComma + 1), testers[i].substring(firstTComma, lastTComma), testers[i].charAt(0));
                    System.out.println();
                }
            }
            //sorting by tester
            if (sortingChoice == 2) {
                for (int i = 0; i < range.length; i++) {
                    for (int j = i + 1; j < range.length; j++) {
                        if (testers[i].compareTo(testers[j]) > 0) {
                            String temp = coders[i];
                            String temp2 = testers[i];
                            coders[i] = coders[j];
                            testers[i] = testers[j];
                            coders[j] = temp;
                            testers[j] = temp2;
                        }
                    }
                }
                System.out.format("%23s %40s", "Tester", "Coder");
                System.out.println("\n");
                System.out.format("%-15s %-15s %-10s %-15s %-15s %-10s", "First Name", "Last Name", "Block", "First Name", "Last Name", "Block");
                System.out.println("\n--------------------------------------------------------------------------------");
                for (int i = 0; i < coders.length; i++) {
                    int firstCComma = coders[i].indexOf(",") + 1;
                    int firstTComma = testers[i].indexOf(",") + 1;
                    int lastCComma = coders[i].lastIndexOf(",");
                    int lastTComma = testers[i].lastIndexOf(",");
                    char studentCBlock = coders[i].charAt(0);
                    char studentTBlock = testers[i].charAt(0);
                    System.out.format("%-15s %-15s %-10s %-15s %-15s %-10s", testers[i].substring(lastTComma + 1), testers[i].substring(firstTComma, lastTComma), testers[i].charAt(0), coders[i].substring(lastCComma + 1), coders[i].substring(firstCComma, lastCComma), coders[i].charAt(0));
                    System.out.println();
                }
            }
            if (sortingChoice == 3) {
                for (int i = 0; i < range.length; i++) {
                    for (int j = i + 1; j < range.length; j++) {
                        if (coders[i].compareTo(coders[j]) > 0) {
                            String temp = coders[i];
                            String temp2 = testers[i];
                            coders[i] = coders[j];
                            testers[i] = testers[j];
                            coders[j] = temp;
                            testers[j] = temp2;
                        }
                    }
                }
                System.out.format("%23s %40s", "Coder", "Tester");
                System.out.println("\n");
                System.out.format("%-15s %-15s %-10s %-15s %-15s %-10s", "First Name", "Last Name", "Block", "First Name", "Last Name", "Block");
                System.out.println("\n--------------------------------------------------------------------------------");
                for (int i = 0; i < coders.length; i++) {
                    int firstCComma = coders[i].indexOf(",") + 1;
                    int firstTComma = testers[i].indexOf(",") + 1;
                    int lastCComma = coders[i].lastIndexOf(",");
                    int lastTComma = testers[i].lastIndexOf(",");
                    char studentCBlock = coders[i].charAt(0);
                    char studentTBlock = testers[i].charAt(0);
                    System.out.format("%-15s %-15s %-10s %-15s %-15s %-10s", coders[i].substring(lastCComma + 1), coders[i].substring(firstCComma, lastCComma), studentNames[i].charAt(0), testers[i].substring(lastTComma + 1), testers[i].substring(firstTComma, lastTComma), studentNames[i].charAt(0));
                    System.out.println();
                }

                System.out.println("\n");

                for (int i = 0; i < range.length; i++) {
                    for (int j = i + 1; j < range.length; j++) {
                        if (testers[i].compareTo(testers[j]) > 0) {
                            String temp = coders[i];
                            String temp2 = testers[i];
                            coders[i] = coders[j];
                            testers[i] = testers[j];
                            coders[j] = temp;
                            testers[j] = temp2;
                        }
                    }
                }
                System.out.format("%23s %40s", "Tester", "Coder");
                System.out.println("\n");
                System.out.format("%-15s %-15s %-10s %-15s %-15s %-10s", "First Name", "Last Name", "Block", "First Name", "Last Name", "Block");
                System.out.println("\n--------------------------------------------------------------------------------");
                for (int i = 0; i < coders.length; i++) {
                    int firstCComma = coders[i].indexOf(",") + 1;
                    int firstTComma = testers[i].indexOf(",") + 1;
                    int lastCComma = coders[i].lastIndexOf(",");
                    int lastTComma = testers[i].lastIndexOf(",");
                    char studentCBlock = coders[i].charAt(0);
                    char studentTBlock = testers[i].charAt(0);
                    System.out.format("%-15s %-15s %-10s %-15s %-15s %-10s", testers[i].substring(lastTComma + 1), testers[i].substring(firstTComma, lastTComma), testers[i].charAt(0), coders[i].substring(lastCComma + 1), coders[i].substring(firstCComma, lastCComma), coders[i].charAt(0));
                    System.out.println();
                }
            }

        } catch (FileNotFoundException f) {
            System.out.println(f.toString());
        }
    }
}
