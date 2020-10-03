/*
Matthias Kim
Tester/Coder Lab
9/18/2020
*/

import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class pairing {
    public static void main(String[] args) {

        try {
            //setup with variables
            File classNames = new File("src/classList");
            Scanner s = new Scanner(System.in);
            Scanner reader = new Scanner(classNames);
            int totalStudents = 0, b1 = 0, b2 = 0;

            //ask for user input to determine the block used
            System.out.println("\nThis program will randomly match coders and testers from each/both blocks.");
            System.out.println("Please type the respective number for which block(s)'s pairs you'd like to see.\n1) Block 1 \n2) Block 2 \n3) Blocks 1 & 2");
            int blockChoice = s.nextInt();

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
            String [] studentNames = new String[totalStudents + 1];
            for (int i = 0; i < totalStudents; i++) {
                studentNames[i] = reader.nextLine();
            }

            //determine which area of the file is going to be read
            String [] selectionRange = { };
            int startRead = 0, endRead = 0;
            int tempCounter;
            char tempChar = ' ';
            if (blockChoice == 1)
            {
                endRead = b1;
                tempChar = '1';

                selectionRange = new String [endRead - startRead];
                tempCounter = 0;
                for (int i = startRead; i < endRead; i++) {
                    if (studentNames[i].charAt(0) == tempChar){
                        selectionRange[tempCounter] = studentNames[i].substring(2);
                        tempCounter++;
                    }
                    //exit the loop once the block 1/2 names are done
                    else{
                        break;
                    }
                }
            }
            else if (blockChoice == 2) {
                startRead = b1;
                endRead = b1 + b2;
                tempChar = '2';
                tempCounter = 0;
                for (int i = startRead; i < endRead; i++) {
                    if (studentNames[i].charAt(0) == tempChar){
                        selectionRange[tempCounter] = studentNames[i].substring(2);
                        tempCounter++;
                    }
                    //exit the loop once the block 1/2 names are done
                    else{
                        break;
                    }
                }
            }
            else if (blockChoice == 3) {
                selectionRange = new String[b1 + b2];
                tempCounter = 0;
                do {
                    selectionRange[tempCounter] = studentNames[tempCounter].substring(2);
                    tempCounter++;
                } while (tempCounter < b1 + b2);
            }

            String [] coders = new String[selectionRange.length];
            String [] testers = new String[selectionRange.length];
            //a boolean to tell whether the coder/tester has been taken
            boolean [] isTaken = new boolean[selectionRange.length];

            //take all the names out of the desired selection (block)
            int increment = 0;
            do{
                coders[increment] = selectionRange[increment];
                increment++;
            } while (increment < selectionRange.length);


            for (int i = 0; i < selectionRange.length; i++) {
                int testerPlace =  (int) Math.floor(Math.random() * selectionRange.length);
                if (coders[testerPlace].equals(testers[testerPlace])){
                    //changes the tester array placer at least once, but keeps going until the coder and tester are not equal
                    do {
                        testerPlace =  (int) Math.floor(Math.random() * selectionRange.length);
                    } while(coders[testerPlace].equals(testers[testerPlace]));
                }
                //if the names are already taken reroll the number
                else if (isTaken[testerPlace]){
                    do {
                        testerPlace =  (int) Math.floor(Math.random() * selectionRange.length);
                    } while(isTaken[testerPlace]);
                }

                //check whether the coder & tester do not match, then set the bool to true and rerun the loop
                if (!coders[testerPlace].equals(testers[testerPlace])){
                    isTaken[testerPlace] = true;
                    testers[i] = selectionRange[testerPlace];
                }
            }

            for (int i = 0; i < selectionRange.length; i++)
            {
                for (int j = i + 1; j < selectionRange.length; j++)
                {
                    if (coders[i].compareTo(coders[j]) > 0)
                    {
                        String temp = coders[i];
                        String temp2 = testers[i];
                        coders[i] = coders[j];
                        testers[i] = testers[j];
                        coders[j] = temp;
                        testers[j] = temp2;
                    }
                }
            }

            System.out.printf("%-15s %-30s", "Coder", "Tester");
            System.out.println("\n-----------------------------------------------------------");
            for (int i = 0; i < coders.length; i++) {
                System.out.format("%-30s %-30s", coders[i], testers[i]);
                System.out.println();
            }





        } catch (FileNotFoundException f) {
            System.out.println(f.toString());
        }
    }
}
