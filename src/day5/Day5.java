package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Day5 {
    /**
     @author David Tejuosho
     @see <a href="https://adventofcode.com/2021/day/5">Day 5</a>
     */
    public static void main(String[] args) {
        try {
            System.out.println(partOne());
            System.out.println(partTwo());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static int partOne() throws FileNotFoundException {
        HashMap<String, Integer> map = new HashMap<>();
        Scanner input = new Scanner(new File("src\\day5\\input.txt"));
        int [] points = new int[4];

        while (input.hasNext()) {
            loadPoints(input, points);
                /*
                points[0] is x1
                points[1] is y1
                points[2] is x2
                points[3] is y2
                 */

            if (points[0] == points[2]|| points[1] == points[3])
            mapLine(map, points);
        }

        return checkNumDanger(map);
    }

    static int partTwo() throws FileNotFoundException {
        HashMap<String, Integer> map = new HashMap<>();
        Scanner input = new Scanner(new File("src\\day5\\input.txt"));
        int [] points = new int[4];

        while (input.hasNext()) {
            loadPoints(input, points);

            if (points[0] == points[2]|| points[1] == points[3])
                mapLine(map, points);
            else mapDiagonal(map, points);
        }

        return checkNumDanger(map);
    }

    static void loadPoints (Scanner input, int[] points) {
        Scanner nextLine;
        nextLine = new Scanner(input.nextLine().replace(" -> ", ",")).useDelimiter(",");
        //Takes next line, replaces the -> with a comma and uses commas as the delimiter

        for (int i = 0; i < 4; i++)
            points[i] = Integer.parseInt(nextLine.next()); //Loads in the bound points on the pine
    }

    static void mapLine (HashMap<String, Integer> map, int[] points) {
        //Magical nested noop with O(n) time complexity
        for (int i = Math.min(points[0], points[2]); i<=Math.max(points[0], points[2]); i++)
            for (int j = Math.min(points[1], points[3]); j<=Math.max(points[1], points[3]); j++)
                fillPoint(map, i, j);
    }

    static void mapDiagonal(HashMap<String, Integer> map, int[] points){
        int j = points[1];
        int i = points[0];
        int numOfTimesToLoop = Math.abs(points[0]-points[2])+1;

        for (int counter = 0; counter<numOfTimesToLoop; counter++){
            fillPoint(map, i, j);

            if (points[0]<=points[2])
                i++;
            else i--;

            if (points[1]<=points[3])
                j++;
            else j--;
        }

    }

    static void fillPoint (HashMap<String, Integer> map, int i, int j) {
        if (map.containsKey("" + i + "," + j))//Checks if that point has been touched by a line
            map.put("" + i + "," + j, map.get("" + i + "," + j)+1);//If yes, it adds to the value of the point
        else
            map.put("" + i + "," + j, 1);//If no, it puts the point with a value of 1
    }

    static int checkNumDanger(HashMap<String, Integer> map){
        int numOfDanger = 0;
        for (var value: map.values()){
            if (value>=2)
                numOfDanger++;
        }//Counts the number of points with values >= 2
        return numOfDanger;
    }
}