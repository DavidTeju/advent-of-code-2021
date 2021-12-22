package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Day5 {
    public static void main(String[] args) {
        //For each line,
        //Create Scanner with line after replacing " -> " with ","
        //Check if it's vertical/horizontal by checking if points[0] = points[2] or points[1] = points[3]
        //You can either check them individually and then work based on if it's vertical or horizontal
        //Or you could have a double loop, and it would be still O(n) time
        //Load the points unto a HashMap with xy as key and number of intersections as content
        //Whenever it contains the point already, add one to the content
        //At the end, go through every point and count the number that have 2 or more as content
        try {
            System.out.println(partOne());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    static int partOne() throws FileNotFoundException {
        HashMap<String, Integer> map = new HashMap<>();
        Scanner input = new Scanner(new File("src\\day5\\input.txt"));
        Scanner nextLine;
        int [] points = new int[4];

        while (input.hasNext()) {
            nextLine = new Scanner(input.nextLine().replace(" -> ", ","));
            nextLine.useDelimiter(",");

            for (int i = 0; i<4; i++)
                points[i] = Integer.parseInt(nextLine.next());

                /*
                points[0] is x1
                points[1] is y1
                points[2] is x2
                points[3] is y2
                 */
            //Magical nested noop with O(n) time complexity
            if (points[0] == points[2]||points[1] == points[3])
                for (int i = Math.min(points[0], points[2]); i<=Math.max(points[0], points[2]); i++)
                    for (int j = Math.min(points[1], points[3]); j<=Math.max(points[1], points[3]);j++)
                        if (map.containsKey("" + i + "," + j))
                            map.put("" + i + "," + j, map.get("" + i + "," + j)+1);
                        else
                            map.put("" + i + "," + j, 1);
        }

        int numOfDanger = 0;
        for (var value: map.values()){
            if (value>=2)
                numOfDanger++;
        }
        return numOfDanger;
    }
}
