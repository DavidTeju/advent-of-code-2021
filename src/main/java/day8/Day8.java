package main.java.day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Day8 {
	/**
	 * @author David Tejuosho
	 * @see <a href="https://adventofcode.com/2021/day/8">Day 8</a>
	 * @see <a href="https://github.com/DavidTeju/advent-of-code-2021/blob/66f0e5b94fac176171435e64614205dd97d75458/src/day8/Day8.java">Previous solution using Strings</a>
	 * <p>For partOne, I needed to count the occurences of 1, 4, 7, and 8.</p>
	 * <p>This was easy because the signal patterns for those numbers had unique lengths of 2, 3, 4, and 7.</p>
	 * <p> All I had to do was:</p>
	 * <p>ignore the values before the "|", split the values after the "|" into an array of 4 Strings,</p>
	 * <p>For each value/String in the array,</p>
	 * <p>check the length of the String,</p>
	 * <p>If it is 2, 3, 4, or 7, increment the number of vaues(numOfValues)</p>
	 * <p>
	 * <p>partTwo was significantly harder</p>
	 * <p>For this one, I analysed the patterns in a three step process:</p>
	 * <p>The first step was to identify the patterns for 1, 4, 7, and 8 using the same logic from partOne</p>
	 * <p>The second and third steps are explained in-line in methods day8.Day8.secondAnalysis() and day8.Day8.thirdAnalysis()</p>
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
		Scanner input = new Scanner(new File("src" + File.separator +  "day8" + File.separator + "input.txt"));
		int numOfValues = 0;
		while (input.hasNext())
			for (String pattern : input.nextLine().split("\\|")[1].trim().split(" "))
				//Take the next line, split it using "|", take the second half of the subsequent array,
				//trim it, and split it into each word/pattern/digit then for each, check if the length is 2, 3, 4, 7
				//and if true, increment the number of values.
				if (pattern.length() == 2 || pattern.length() == 3 || pattern.length() == 4 || pattern.length() == 7)
					numOfValues++;
		return numOfValues;
	}
	
	@SuppressWarnings("unchecked")
    static int partTwo() throws FileNotFoundException {
        Scanner input = new Scanner(new File("src" + File.separator +  "day8" + File.separator + "input.txt"));
		int totalValue = 0;
		String[] lineSegments;
		Set<Character>[] valuesBeforeAnalysisSet;
		Set<Character>[] analysedValues;
		Set<Character>[] outputValues;
		while (input.hasNext()) {
			//Divide the line into the pattern and the output.
			lineSegments = input.nextLine().split("\\|");
			//Parse the patterns into an array
			valuesBeforeAnalysisSet = Arrays.stream(lineSegments[0]
							.split(" "))
					.map(word -> word.chars()
							.mapToObj(c -> (char) c)
							.collect(Collectors.toCollection(HashSet::new)))
					.toArray(HashSet[]::new);
			//Parse the output into an array
			outputValues = Arrays.stream(lineSegments[1]
							.split(" "))
					.map(word -> word.chars()
							.mapToObj(c -> (char) c)
							.collect(Collectors.toCollection(HashSet::new)))
					.toArray(HashSet[]::new);
			//Analyse the vales: put the string patters into array indexes that represent what number the patterns represent
			analysedValues = analyseValues(valuesBeforeAnalysisSet);
			totalValue += parseValue(outputValues, analysedValues);
		}
		return totalValue;
	}
	
	@SuppressWarnings("unchecked")
	static Set<Character>[] analyseValues(Set<Character>[] toAnalyse) {
		Set<Character>[] analysed = new HashSet[10];
		initialAnalysis(toAnalyse, analysed);
		secondAnalysis(toAnalyse, analysed);
		lastAnalysis(toAnalyse, analysed);
		return analysed;
	}
	
	//The initial analysis gives us the values/keys for analysed[1], analysed[4], analysed[7], analysed[8]
	static void initialAnalysis(Set<Character>[] toAnalyse, Set<Character>[] analysed) {
		for (int i = 0; i < 10; i++) {
			if (toAnalyse[i] == null) continue;
			switch (toAnalyse[i].size()) {
				case 7 -> {
					analysed[8] = toAnalyse[i];
					toAnalyse[i] = null;
				}
				case 2 -> {
					analysed[1] = toAnalyse[i];
					toAnalyse[i] = null;
				}
				case 4 -> {
					analysed[4] = toAnalyse[i];
					toAnalyse[i] = null;
				}
				case 3 -> {
					analysed[7] = toAnalyse[i];
					toAnalyse[i] = null;
				}
			}
		}
	}
	
	//This analysis can only be done after our initial analysis because it relies on the values of:
	//analysed[1], analysed[4], analysed[7], analysed[8]
	//which are initialized after the initial analysis
	static void secondAnalysis(Set<Character>[] toAnalyse, Set<Character>[] analysed) {
		for (int i = 0; i < 10; i++)
			//0, 6, and 9 are the only patterns with length 6
			if (toAnalyse[i] != null && toAnalyse[i].size() == 6)
				//Nine is the only one of the three that contains all the lines that four's pattern contains
				if (toAnalyse[i].containsAll(analysed[4])) {
					analysed[9] = toAnalyse[i];
					toAnalyse[i] = null;
					//After this, we have 0 and 6 left
					//Zero is the only one of the two that contains all the lines that one's pattern contains
				} else if (toAnalyse[i].containsAll(analysed[1])) {
					analysed[0] = toAnalyse[i];
					toAnalyse[i] = null;
				} else {//Only Six is left
					analysed[6] = toAnalyse[i];
					toAnalyse[i] = null;
				}
	}
	
	static void lastAnalysis(Set<Character>[] toAnalyse, Set<Character>[] analysed) {
		for (int i = 0; i < 10; i++) {//2, 3, and 5 are the only patterns left
			if (toAnalyse[i] == null) continue; //Skips analysis for all values that are null/have been analysed
			if (toAnalyse[i].containsAll(analysed[1])) {
				//Nine is the only one of the three that contains all the lines that one's pattern contains
				analysed[3] = toAnalyse[i];
				toAnalyse[i] = null;
			} else if (toAnalyse[i].containsAll(new HashSet<>(analysed[6].stream().filter(analysed[1]::contains).toList()))) {
				analysed[5] = toAnalyse[i];
				toAnalyse[i] = null;
			} else {
				analysed[2] = toAnalyse[i];
				toAnalyse[i] = null;
			}
		}
	}
	
	static int parseValue(Set<Character>[] toParse, Set<Character>[] key) {
		StringBuilder numberValue = new StringBuilder();
		
		for (Set<Character> toFind : toParse)
			for (int j = 0; j < 10; j++)
				if (toFind.equals(key[j]))
					numberValue.append(j);
		return Integer.parseInt(numberValue.toString());
	}
}