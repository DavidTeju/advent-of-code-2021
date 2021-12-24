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
<p>Using the same logic from partOne, I </p>
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
        String[] toUse;
        while (input.hasNext()) {
            input.next();
            toUse = input.nextLine().replace("|", "").trim().split(" ");
            for (int i = 0; i<4; i++) {
                length = toUse[i].length();
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
            lineSegments = input.nextLine().split("\\|");
            valuesBeforeAnalysis = lineSegments[0].split(" ");
            outputValues = lineSegments[1].split(" ");
            analysedValues = analyseValues(valuesBeforeAnalysis);
            totalValue += parseValue(outputValues, analysedValues);
        }
        return totalValue;
    }

    static String[] analyseValues (String[] toAnalyse){
        String[] analysed = new String[10];
        for (int j = 0; j<3; j++)
            for (int i = 0, length = toAnalyse.length; i<length; i++){
                if (toAnalyse[i] != null){
                    initialAnalysis(toAnalyse, analysed, i);
                }
            }
        return analysed;
    }

    //The initial analysis gives us the values/keys for analysed[1], analysed[4], analysed[7], analysed[8]
    static void initialAnalysis (String[] toAnalyse, String[] analysed, int i) {
        switch (toAnalyse[i].length()) {
            case 7 -> {
                analysed[8] = toAnalyse[i];
                toAnalyse[i] = null;
            }
            case 2 -> {
                analysed[1] = toAnalyse[i];
                toAnalyse[i] = null;
            }
            case 4 -> {
                analysed[4] = toAnalyse[i];
                toAnalyse[i] = null;
            }
            case 3 -> {
                analysed[7] = toAnalyse[i];
                toAnalyse[i] = null;
            }
            default -> secondAnalysis(analysed, toAnalyse[i]);
        }
    }

    //This analysis can only be done after our initial analysis because it relies on the values of:
    //analysed[1], analysed[4], analysed[7], analysed[8]
    //which are initialized after the initial analysis
    static void secondAnalysis (String[] analysed, String toAnalyse){
        try {
            if (toAnalyse.length() == 6)//You might need to do an inordered contain method
                if (unorderedContains(toAnalyse, analysed[4]))
                    analysed[9] = toAnalyse;
                else if (unorderedContains(toAnalyse, analysed[1]))
                    analysed[0] = toAnalyse;
                else analysed[6] = toAnalyse;
            else
                lastAnalysis(analysed, toAnalyse);
        }
        catch (NullPointerException ignored){
            //Ignored because the first iteration will have some needed values as null but as soon as the first iteration is done, it will have been initialized
        }
    }

    //This analysis happens after the first two analysis
    static void lastAnalysis (String[] analysed, String toAnalyse) {
        if (unorderedContains(toAnalyse, analysed[1]))
            analysed[3] = toAnalyse;
        else if(unorderedContains(toAnalyse, intersection(analysed[6], analysed[1])))
            analysed[5] = toAnalyse;
        else analysed[2] = toAnalyse;
    }

    static int parseValue (String[] toParse, String[] key){
        String numberValue = "";
        for (String toFind: toParse)
            for (int j = 0; j<10; j++)
                if (unOrderedEquals(toFind, key[j]))
                    numberValue+=j;
        return Integer.parseInt(numberValue);
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

    //Number 1 and number 6 have only one intersection so we can return as soon as we find it
    //If there's no intersection (becca
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