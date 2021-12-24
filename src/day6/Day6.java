package day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day6 {
    /**
     @author David Tejuosho
     @see <a href="https://adventofcode.com/2021/day/6">Day 6</a>
     The intention in partOne was to add all the fish as an independent integer in a List where the integer was the number of days left to reproduction
     For each day, for 80 days,
     it would iterate over the List and change the number of days left as well as creating the new fishes. Clearly inefficient but simple

     The intention in partTwo was, instead,
     to add all the days left to reproduction as independent integers in an array (since the num of days left to reproduction are fixed)
     For each day, for 256 days,
     it would iterate over the array,
     move the number of fishes from one day to the lower day,
     move the number of fishes from 0 to 6,
     and added new fishes (the same number as the ones from 0) to the array at 8
     Way more efficient but more complicated
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
        Scanner input = new Scanner(new File("src\\day6\\input.txt"));
        input.useDelimiter(",");
        List<Byte> fishes = new ArrayList<>();

        while (input.hasNext())
            fishes.add(Byte.parseByte(input.next()));//Add in all the initial fishes

        for (int i = 0; i<80; i++)//For 80 days
            for (int j = 0; j< fishes.size(); j++)//For each fish
                if (fishes.get(j)==0){//If the fish is giving birth
                    fishes.set(j, (byte) 6);
                    fishes.add((byte) 9);//add it as 9 because this for loop will iterate to the end after this is created and remove 1 from it
                }
                else
                    fishes.set(j, (byte) (fishes.get(j)-1));

        return fishes.size();
    }

    static long partTwo() throws FileNotFoundException {
        Scanner input = new Scanner(new File("src\\day6\\input.txt"));
        input.useDelimiter(",");
        long [] fishes = new long[9];

        while (input.hasNext())
            fishes[Integer.parseInt(input.next())]++;
        //Load the fishes into the array
        //The value of the array would be the number of fishes with index days left to reproduction

        long temp;
        for (int i = 0; i<256; i++) {//For 80 days
            temp = fishes[0]; //Store the number of fish that are reproducing
            for (int j = 0; j<8; j++)
                fishes[j] = fishes[j+1];//Move the fishes to the lower day
            //Could be replaced with:
            // System.arraycopy(fishes, 1, fishes, 0, 8);
            fishes[6]+=temp; //Add in the fishes that just reproduced
            fishes[8] = temp; //Put in the fishes that were just created
        }

        return Arrays.stream(fishes).sum();//return the sum of all the fishes
    }

}
