package main.java.day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day7 {
    public static void main (String[] args) throws FileNotFoundException {
        System.out.println(partOne());
        System.out.println(partTwo());
    }

    static int partOne() throws FileNotFoundException {
        Scanner input = new Scanner(new File("src" + File.separator +  "day7" + File.separator + "input.txt")).useDelimiter(",");
        List<Integer> list = new ArrayList<>();
        while (input.hasNext())
            list.add(Integer.parseInt(input.next()));
        Collections.sort(list);
        return list.stream()
                .mapToInt(i -> Math.abs(
                        i-list.get(
                                (int) Math.ceil(
                                        list.size()/2.0
                                )
                        )
                ))
                .sum();
    }

    static int partTwo() throws FileNotFoundException {
        Scanner input = new Scanner(new File("src" + File.separator +  "day7" + File.separator + "input.txt")).useDelimiter(",");
        List<Integer> list = new ArrayList<>();
        while (input.hasNext())
            list.add(Integer.parseInt(input.next()));

        int average = (int) Math.round(list.stream().mapToInt(i -> i).average().orElse(0.0));

        return Math.min(
        list.stream().mapToInt(i -> getFuelExpenditure(Math.abs(i-average))).sum(), Math.min(
                list.stream().mapToInt(i -> getFuelExpenditure(Math.abs(i-average-1))).sum(),
                list.stream().mapToInt(i -> getFuelExpenditure(Math.abs(i-average+1))).sum()
        ));
    }
    static int getFuelExpenditure(int movements){
        if (movements<=0)
            return movements;
            return movements+getFuelExpenditure(movements-1);
    }

}
