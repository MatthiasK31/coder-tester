

/*
Matthias Kim
Tester/Coder Lab
9/18/2020
*/

// basic setup

import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class pairs {
    public static void main(String[] args) {

        try {
            //setup with variables
            File classNames = new File("src/classList");
            Scanner s = new Scanner(System.in);
            Scanner reader = new Scanner(classNames);
            int totalStudents = 0, b1 = 0, b2 = 0;

            //ask for which block they want to create partners for
            System.out.println("What group would you like to make groups for? \n1) Group 1 \n2) Group 2 \n3) Both Blocks\nSelect the block that you'd like to see: ");
            int blockChoice = s.nextInt();

            //find the # of ppl in file
            while (reader.hasNextLine()) {
                String block = reader.nextLine();
                totalStudents++;
                if (block.charAt(0) == '1') {
                    b1++;
                }
                if (block.charAt(0) == '2') {
                    b2++;
                }
            }

            //double check that the program is reading the file correctly
            if (totalStudents != b1 + b2) {
                System.out.println("The program did not have a valid file.");
                System.exit(1);
            } else {
                System.out.println("The file format is valid; the program will continue.");
                System.out.println("Total: " + totalStudents + "\tB1: " + b1 + "\tB2: " + b2);
            }

            //creates an array to store all student names
            String [] people = new String[totalStudents + 1];

            //clear scanner
            reader = new Scanner(classNames);

            //turn the names from the file into an array
            for (int i = 0; i < totalStudents; i++) {
                people[i] = reader.nextLine();
            }

            //continue here, go to create the sorting
            int startat = 0, runTo = 0;
            char block = 'x';
            String[] studentSet = { };
            //switch to determine reading from block 1 or block 2
            if (blockChoice == 1 || blockChoice == 2) {
                switch (blockChoice) {
                    case 1:
                        runTo = b1;
                        block = '1';
                    case 2:
                        startat = b1;
                        runTo = b2 + b1;
                        block = '2';
                }
                studentSet = new String[runTo - startat];

                //add people to the array of names
                int k = 0;
                for (int i = 0; i < runTo; i++) {
                    if (people[i].charAt(0) == block) {
                        studentSet[k] = people[i].substring(2);
                        k++;
                    }
                }
            }
            //read from both blocks
            else if (blockChoice == 3) {
                studentSet = new String[b1 + b2];
                for (int i = 0; i < b1 + b2; i++) {
                    studentSet[i] = people[i].substring(2);
                }
            }

            //assign the coders to testers
            String[] coders = new String[studentSet.length];
            String[] testers = new String[studentSet.length];
            //boolean me
            boolean[] isTaken = new boolean[studentSet.length];

            for (int i = 0; i < studentSet.length; i++) {
                coders[i] = studentSet[i];
            }
            for (int i = 0; i < studentSet.length; i++) {
                //prevent duplicates from occurring
                int tester = (int) Math.floor(Math.random() * studentSet.length);
                if (coders[tester].equals(testers[tester])) {
                    while (coders[tester].equals(testers[tester])) {
                        tester = (int) Math.floor(Math.random() * studentSet.length);
                    }
                }
                if (isTaken[tester]){
                    while (isTaken[tester]){
                        tester = (int) Math.floor(Math.random()*studentSet.length);
                    }
                }
                //marks slots as taken once a pair w/o duplicates has been chosen
                isTaken[tester] = true;
                testers[i] = studentSet[tester];
            }

            //sorts and then provides output
            if (blockChoice == 1) {
                for (int i = 0; i < studentSet.length; i++) {
                    for (int j = i + 1; j < studentSet.length; j++) {
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
                System.out.format("%-30s %-30s", "Coder", "Tester");
                System.out.println("\n-----------------------------------------------------------");
                for (int i = 0; i < coders.length; i++) {
                    System.out.format("%-30s %-30s", coders[i], testers[i]);
                    System.out.println();
                }
            } else if (blockChoice == 2) { // sort by tester
                for (int i = 0; i < studentSet.length; i++) {
                    for (int j = i + 1; j < studentSet.length; j++) {
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
                System.out.format("%-30s %-30s", "Tester", "Coder");
                System.out.println("\n-----------------------------------------------------------");
                for (int i = 0; i < coders.length; i++) {
                    System.out.format("%-30s %-30s", testers[i], coders[i]);
                    System.out.println();
                }
            }
            else if (blockChoice == 3) { // sort by coder, print, sort by tester, and print
                for (int i = 0; i < studentSet.length; i++) {
                    for (int j = i + 1; j < studentSet.length; j++) {
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
                System.out.format("%-30s %-30s", "Coder", "Tester");
                System.out.println("\n-----------------------------------------------------------");
                for (int i = 0; i < coders.length; i++) {
                    System.out.format("%-30s %-30s", coders[i], testers[i]);
                    System.out.println();
                }
                System.out.println("\n\n");
                for (int i = 0; i < studentSet.length; i++) {
                    for (int j = i + 1; j < studentSet.length; j++) {
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
            } else {
                System.out.println("invalid result.");
                System.exit(1);
            }


        } catch (FileNotFoundException fne) {
            System.out.println(fne.toString());
        }


    }

}
