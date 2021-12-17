package day3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day3 {

    public static void main(String[] args) {
        try {
            System.out.println(partOne());
            System.out.println(partTwo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static long partOne() throws Exception {
        final Scanner sc = new Scanner(new File("src\\day3\\input.txt"));
        String gamma = "";
        String epsilon = "";
        String nextValue = sc.next();
        int[] dominantCounter = new int[nextValue.length()];
        analyseNumber(dominantCounter, nextValue);

        while (sc.hasNext()) {
            analyseNumber(dominantCounter, nextValue);
            nextValue = sc.next();
        }

        for (int bit : dominantCounter) {
            if (bit > 0){
                gamma+="1";
                epsilon+="0";
            }else if (bit<0) {
                gamma+="0";
                epsilon+="1";
            }else
                throw  new Exception("Challenge doesn't specify what to do if equal values");
        }
        return Long.valueOf(epsilon, 2) * Long.valueOf(gamma, 2);
    }

    static long partTwo() throws Exception {
        final Scanner sc = new Scanner(new File("src\\day3\\input.txt"));
        List<String> fullInput = new ArrayList<>();
        while (sc.hasNext())
            fullInput.add(sc.next());

        return
                Long.valueOf(rating("oxygen", fullInput, 0).replace("\n", ""), 2) *
                        Long.valueOf(rating("CO2", fullInput, 0).replace("\n", ""), 2);
    }

    /**
     Iterates through each bit of nextValue.
     <p>If the value of the Nth bit is 1, it adds to the Nth int of the dominantCounter array</p>
     <p>If the value is 0, It subtracts 1 from the corresponding int of the dominicanCounter array instead</p>
     <p>This allows us to end up with an array where, for each value, a positive number means the bit, 1, appears more times than 1</p>
     <p>Meanwhile, a negative number means the bit, 0, appears more times than 0.</p>
     @param dominantCounter Takes the array(mutable) and modifies
     @param nextValue Is the number to analyse
     */
    static void analyseNumber(int[] dominantCounter, String nextValue) {
        for (int n = 0; n < nextValue.length(); n++) {
            if (nextValue.charAt(n) == '0')
                dominantCounter[n]--;
            else if (nextValue.charAt(n) == '1')
                dominantCounter[n]++;
            else
                throw new IllegalArgumentException("invalid input detected. All input must be in binary");
        }
    }

    @Deprecated
    static long convertBinary(String toConvert){
        long binary = Long.parseLong(toConvert);
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

    static String rating(String element, List<String> toAnalyze, int position){
        if (toAnalyze.size()==1)//If there is only one number left, it returns that number
            return toAnalyze.get(0);

        List<List<String>> lists = new ArrayList<>();
        lists.add(new ArrayList<>());
        lists.add(new ArrayList<>());
        List<String> toPass;

        for (String nextValue: toAnalyze){
            if (nextValue.charAt(position) == '0') {
                lists.get(0).add(nextValue);
            }else if (nextValue.charAt(position) == '1') {
                lists.get(1).add(nextValue);
            }else {
                throw new IllegalArgumentException("invalid input detected. All input must be in binary");
            }
        }

        int dominant;
        if (lists.get(0).size()>lists.get(1).size())
            dominant = 0;
        else
            dominant = 1;

        if (element.equals("oxygen"))
            toPass = lists.get(dominant);
        else
            toPass = lists.get((dominant+1)%2);

        return rating(element, toPass, ++position);
    }
}