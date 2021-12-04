package day3;

import java.io.File;
import java.util.Scanner;

public class Day3 {

    public static void main(String[] args) throws Exception {
        System.out.println(part1());
    }

    public static int part1() throws Exception {
        final Scanner sc = new Scanner(new File("src\\day3\\input.txt"));
        StringBuilder gamma = new StringBuilder();
        int intGamma = 0;
        StringBuilder epsilon = new StringBuilder();
        int intEpsilon = 0;
        String nextValue;
        int[] dominantCounter = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        while (sc.hasNext()) {
            nextValue = sc.next();
            for (int i = 0; i < nextValue.length(); i++) {
                if (nextValue.charAt(i) == '0')
                    dominantCounter[i]--;
                else if (nextValue.charAt(i) == '1')
                    dominantCounter[i]++;
                else
                    throw new IllegalArgumentException("invalid input detected. All input must me in binary");
            }
        }

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
        * Much more concise but harder to read and slower at ~721 ms
        for (int i = gamma.length(), j = 0; i>0; i--, j++)
            intGamma += Integer.parseInt(""+gamma.charAt(j)) * (int) Math.pow(2, i-1);

        for (int i = epsilon.length(), j = 0; i>0; i--, j++)
            intEpsilon += Integer.parseInt(""+epsilon.charAt(j)) * Math.pow(2, i-1);
            */

        long binaryGamma = Long.parseLong(""+gamma);
        long binaryEpsilon = Long.parseLong(""+epsilon);
        int n = 0;
        //This method is faster and saves time with ~404 ms
        while(binaryGamma > 0)
        {
            long temp = binaryGamma%10;
            intGamma += temp*Math.pow(2, n);
            binaryGamma = binaryGamma/10;
            n++;
        }
        n = 0;

        while(binaryEpsilon > 0)
        {

            long temp = binaryEpsilon%10;
            intEpsilon += temp*Math.pow(2, n);
            binaryEpsilon = binaryEpsilon/10;
            n++;
        }

        return intGamma*intEpsilon;
    }

}
