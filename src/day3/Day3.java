package day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day3 {

    public static void main(String[] args) {
        try {
            System.out.println(part1());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int part1() throws Exception {
        final Scanner sc = new Scanner(new File("src\\day3\\input.txt"));
        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();
        String nextValue = sc.next();
        int[] dominantCounter = new int[nextValue.length()];
        boolean toContinue;

        /*
        The point of this loop is to go through each number in the file, and go through each bit of each number
        If the value of the first bit is 1, I add to the first int of the dominantCounter array
        If the value is 0, I subtract 1 from the corresponding int of the dominicanCounter array instead
        */
        int x = 0;
        do {
            toContinue = sc.hasNext();
            for (int i = 0; i < nextValue.length(); i++) {
                if (nextValue.charAt(i) == '0')
                    dominantCounter[i]--;
                else if (nextValue.charAt(i) == '1')
                    dominantCounter[i]++;
                else
                    throw new IllegalArgumentException("invalid input detected. All input must be in binary");
            }
            if (toContinue)
                nextValue = sc.next();
        } while (toContinue);

        for (int bit : dominantCounter) {
            if (bit > 0){
                gamma.append("1");
                epsilon.append("0");
            }else if (bit<0) {
                gamma.append("0");
                epsilon.append("1");
            }else
                throw  new Exception("Challenge doesn't specify what to do if equal values");
        }

        /* alternative method to convert String gamma in binary form to int intGamma in Decimal
        * Much more concise but harder to read and slower
        int intGamma = 0;
        int intEpsilon = 0;
        for (int i = gamma.length(), j = 0; i>0; i--, j++)
            intGamma += Integer.parseInt(""+gamma.charAt(j)) * (int) Math.pow(2, i-1);

        for (int i = epsilon.length(), j = 0; i>0; i--, j++)
            intEpsilon += Integer.parseInt(""+epsilon.charAt(j)) * Math.pow(2, i-1);
        return intGamma*intEpsilon;
        */
        return convertBinary(epsilon)*convertBinary(gamma);
    }

    static int convertBinary(StringBuilder toConvert){
        long binary = Long.parseLong(""+toConvert);
        int n = 0;
        long temp;
        int ternary = 0;
        //This method is faster and saves runtime
        while(binary > 0){
            temp = binary%10;
            ternary += (int) (temp * Math.pow(2, n));
            binary = binary/10;
            n++;
        }
        return ternary;
    }

//    static int partTwo() throws Exception {
//        final Scanner sc = new Scanner(new File("src\\day3\\input.txt"));
//        String nextValue;
//        int dominantCounter = 0;
//nextValue = sc.next();
//        while (sc.hasNext()){
//            nextValue = sc.next();
//            if (nextValue.charAt(position) == '0')
//                dominantCounter--;
//            else if (nextValue.charAt(i) == '1')
//                dominantCounter++;
//            else
//                throw new IllegalArgumentException("invalid input detected. All input must be in binary");
//
//        }
//
//        return 0;
//    }
//
//    static int scrubberRating (String element, ArrayList<Integer> inConsideration, int position){
//        return 0;
//    }
}