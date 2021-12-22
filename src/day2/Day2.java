package day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 {
    /**
     @author David Tejuosho
     @see <a href="https://adventofcode.com/2021/day/2">Day 2</a>
     */
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(partOne());
        System.out.println(partTwo());
    }

    static int partOne() throws FileNotFoundException {
        final Scanner sc = new Scanner (new File("src\\day2\\input"));
        short depth = 0;
        short pos = 0;

        while (sc.hasNext())
            switch (sc.next()) {
                case "forward" -> pos += sc.nextShort();
                case "down" -> depth += sc.nextShort();
                case "up" -> depth -= sc.nextShort();
            }
        return pos*depth;
    }

    static int partTwo() throws FileNotFoundException {
        final Scanner sc = new Scanner (new File("src\\day2\\input"));
        int depth = 0;
        int pos = 0;
        int aim = 0;
        int forwardMove;
        while (sc.hasNext()) {
            switch (sc.next()) {
                case "forward" -> {
                    forwardMove = sc.nextInt();
                    depth += aim * forwardMove;
                    pos += forwardMove;
                }
                case "down" -> aim += sc.nextInt();
                case "up" -> aim -= sc.nextInt();
            }
        }
        return depth * pos;
    }
}
