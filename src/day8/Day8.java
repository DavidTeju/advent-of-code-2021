package day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day8 {
    /**
     @author David Tejuosho
     @see <a href="https://adventofcode.com/2021/day/8">Day 8</a>
     <p>For partOne, I needed to count the occurences of 1, 4, 7, and 8.</p>
     <p>This was easy because the signal patterns for those numbers had unique lengths of 2, 3, 4, and 7.</p>
     <p> All I had to do was:</p>
     <p>ignore the values before the "|", split the values after the "|" into an array of 4 Strings,</p>
     <p>For each value/String in the array,</p>
     <p>check the length of the String,</p>
     <p>If it is 2, 3, 4, or 7, increment the number of vaues(numOfValues)</p>
     <p>
     <p>partTwo was significantly harder</p>
     <p>For this one, I analysed the patterns in a three step process:</p>
     <p>The first step was to identify the patterns for 1, 4, 7, and 8 using the same logic from partOne</p>
     <p>The second and third steps are explained in-line in methods day8.Day8.secondAnalysis() and day8.Day8.thirdAnalysis()</p>
     */
    public static void main (String[] args) {
        try {
            System.out.println(partOne());
            System.out.println(partTwo());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static int partOne() throws FileNotFoundException {
        Scanner input = new Scanner(new File("src\\day8\\input.txt")).useDelimiter("\\|");
        int numOfValues = 0;
        int length;
        String[] arrayOfPatterns;
        while (input.hasNext()) {
            input.next();
            arrayOfPatterns = input.nextLine().replace("|", "").trim().split(" ");
            for (String pattern: arrayOfPatterns) {
                length = pattern.length();
                if (length == 2 || length == 3 || length == 4 || length ==7)
                    numOfValues++;
            }
        }
        return numOfValues;
    }

    static int partTwo() throws FileNotFoundException {
        Scanner input = new Scanner(new File("src\\day8\\input.txt"));
        int totalValue = 0;
        String[] lineSegments;
        String[] valuesBeforeAnalysis;
        String[] analysedValues;
        String[] outputValues;
        while (input.hasNext()) {
            //Divide the line into the pattern and the output.
            lineSegments = input.nextLine().split("\\|");
            //Parse the patterns into an array
            valuesBeforeAnalysis = lineSegments[0].split(" ");
            //Parse the output into an array
            outputValues = lineSegments[1].split(" ");
            //Analyse the vales: put the string patters into array indexes that represent what number the patterns represent
            analysedValues = analyseValues(valuesBeforeAnalysis);
            totalValue += parseValue(outputValues, analysedValues);
        }
        return totalValue;
    }

    static String[] analyseValues (String[] toAnalyse){
        String[] analysed = new String[10];
        initialAnalysis(toAnalyse, analysed);
        secondAnalysis(toAnalyse, analysed);
        lastAnalysis(toAnalyse, analysed);
        return analysed;
    }

    //The initial analysis gives us the values/keys for analysed[1], analysed[4], analysed[7], analysed[8]
    static void initialAnalysis (String[] toAnalyse, String[] analysed) {
        for (int i = 0; i<10; i++) {
            if (toAnalyse[i] == null) continue;
            switch (toAnalyse[i].length()) {
                case 7 -> {
                    analysed[8] = toAnalyse[i];
                    toAnalyse[i] = null;
                } case 2 -> {
                    analysed[1] = toAnalyse[i];
                    toAnalyse[i] = null;
                } case 4 -> {
                    analysed[4] = toAnalyse[i];
                    toAnalyse[i] = null;
                } case 3 -> {
                    analysed[7] = toAnalyse[i];
                    toAnalyse[i] = null;
                }
            }
        }
    }

    //This analysis can only be done after our initial analysis because it relies on the values of:
    //analysed[1], analysed[4], analysed[7], analysed[8]
    //which are initialized after the initial analysis
    static void secondAnalysis (String[] toAnalyse, String[] analysed){
        for (int i = 0; i<10; i++)
            //0, 6, and 9 are the only patterns with length 6
            if (toAnalyse[i] != null && toAnalyse[i].length() == 6)
                //Nine is the only one of the three that contains all the lines that four's pattern contains
                if (unorderedContains(toAnalyse[i], analysed[4])) {
                    analysed[9] = toAnalyse[i];
                    toAnalyse[i] = null;
                    //After this, we have 0 and 6 left
                    //Zero is the only one of the two that contains all the lines that one's pattern contains
                } else if (unorderedContains(toAnalyse[i], analysed[1])) {
                    analysed[0] = toAnalyse[i];
                    toAnalyse[i] = null;
                } else {//Only Six is left
                    analysed[6] = toAnalyse[i];
                    toAnalyse[i] = null;
                }
    }

    static void lastAnalysis (String[] toAnalyse, String[] analysed) {
        for (int i = 0; i<10; i++) {//2, 3, and 5 are the only patterns left
            if (toAnalyse[i] == null) continue; //Skips analysis for all values that are null/have been analysed
            if (unorderedContains(toAnalyse[i], analysed[1])) {
                //Nine is the only one of the three that contains all the lines that one's pattern contains
                analysed[3] = toAnalyse[i];
                toAnalyse[i] = null;
            } else if (unorderedContains(toAnalyse[i], intersection(analysed[6], analysed[1]))) {
                analysed[5] = toAnalyse[i];
                toAnalyse[i] = null;
            } else {
                analysed[2] = toAnalyse[i];
                toAnalyse[i] = null;
            }
        }
    }

    static int parseValue (String[] toParse, String[] key){
        StringBuilder numberValue = new StringBuilder();
        for (String toFind: toParse)
            for (int j = 0; j<10; j++)
                if (unOrderedEquals(toFind, key[j]))
                    numberValue.append(j);
        return Integer.parseInt(numberValue.toString());
    }

    static boolean unorderedContains (String container, String contained){
        for (char letter: contained.toCharArray())
            if (!container.contains("" + letter))
                return false;
        return true;
    }

    static boolean unOrderedEquals(String first, String second){
        if (first.length() != second.length())
            return false;
        for (char letter: first.toCharArray())
            if (!second.contains("" + letter))
                return false;
        return true;
    }

    //Number 1 and number 6 have only one intersection, so we can return as soon as we find it
    static String intersection(String first, String second){
        try {
            for (char letter : first.toCharArray())
                if (second.contains("" + letter))
                    return "" + letter;
        }
        catch (NullPointerException ignored){
            //Ignored because this intersection depends on the values of analysed[1] which is initialized after the first iteration
            // and analysed[6] which is initialized after the second iteration
        }
        return "0";//Dead return for first and second iteration
    }
}