package main.java.day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Day4 {
    /**
     @author David Tejuosho
     @see <a href="https://adventofcode.com/2021/day/4">Day 4</a>
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
        Scanner input = new Scanner(new File("src\\day4\\input.txt"));
        String numberCall = input.nextLine();
        int winningScore = 0;
        int minTries = 50000;
        int[] boardProfile;
        while (input.hasNext()) {
            boardProfile = checkBoard(input, numberCall);
            if (minTries > boardProfile[0]){
                winningScore = boardProfile[1];
                minTries = boardProfile[0];
            }
        }

        return winningScore;
    }

    static int partTwo() throws FileNotFoundException {
        Scanner input = new Scanner(new File("src\\day4\\input.txt"));
        String numberCall = input.nextLine();
        int winningScore = 0;
        int maxTries = 0;
        int[] boardProfile;
        while (input.hasNext()) {
            boardProfile = checkBoard(input, numberCall);
            if (maxTries < boardProfile[0]){
                winningScore = boardProfile[1];
                maxTries = boardProfile[0];
            }
        }

        return winningScore;
    }

    static int[] checkBoard(Scanner input, String numberCall){
        int[][] board = new int[5][5];

        //load board
        for (int i = 0; i<5; i++)
            for (int j = 0; j<5; j++)
                board[i][j] = input.nextInt();

        return analyseBoard(board, new Scanner(numberCall), 0);
    }

    static int[] analyseBoard(int[][] board, Scanner numberCall, int timesChecked){
        numberCall.useDelimiter(",");
        int numToCheck = Integer.parseInt(numberCall.next());
        boolean numFound = false;
        boolean isWinningNumber = false;
        //Runs through each number in array.
        //When the number is found, replaces it with a zero,
        // then checks if it's rows and columns are won
        for (int i = 0; i<5 && !numFound; i++)
            for (int j = 0; j<5 && !numFound; j++)
                if (board[i][j] == numToCheck){
                    board[i][j] = 0;
                    isWinningNumber = isWinningNumber(board, i, j);
                    numFound = true; //to escape both loops
                }
        if (isWinningNumber){
            int sumOfUnfoundedNumbers = 0;
            for (int i = 0; i<5; i++)
                sumOfUnfoundedNumbers += Arrays.stream(board[i]).sum();

            return new int[]{timesChecked, sumOfUnfoundedNumbers * numToCheck};
        }
        else
            return analyseBoard(board, numberCall, ++timesChecked); //Essentially loops until board is completed
    }

    static boolean isWinningNumber(int[][] board, int row, int column){
        //If all the numbers on the row are 0(found), it will return true
        if (Arrays.stream(board[row]).sum()==0)
            return true;
        int sum = 0;
        for (row = 0; row<5; row++)
            sum+=board[row][column];
        return sum == 0;//If all the numbers on the column are 0(found), it will return true, otherwise false
    }
}
