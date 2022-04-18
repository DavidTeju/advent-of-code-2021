
package main.java.day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Day1 {
    /**
     @author David Tejuosho
     @see <a href="https://adventofcode.com/2021/day/1">Day 1</a>
     */
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(partOne());
        System.out.println(partTwo());
    }

    static int partOne() throws FileNotFoundException {
        final Scanner sc = new Scanner (new File("src\\day1\\input"));
        short previous;
        short after = sc.nextShort();
        short numToReturn = 0;

        while (sc.hasNextShort()){
            previous = after;
            after = sc.nextShort();
            if (after>previous)
                numToReturn++;
        }
        return numToReturn;
    }

    static  int partTwo() throws FileNotFoundException {
        final Scanner sc = new Scanner (new File("src\\day1\\input"));
        int[] sumWindow = {sc.nextInt(), sc.nextInt(), sc.nextInt()};
        int numToReturn = 0;
        int previousSum;
        int sumAfter = Arrays.stream(sumWindow).sum();

        int posInArray = -1;
        while (sc.hasNext()) {
            posInArray++;
            previousSum = sumAfter;
            sumWindow[posInArray%3] = sc.nextInt();
            sumAfter = Arrays.stream(sumWindow).sum();

            if (sumAfter>previousSum)
                numToReturn++;
        }
        return numToReturn;
    }
}
