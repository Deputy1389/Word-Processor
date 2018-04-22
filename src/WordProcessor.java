/**
 * Represents a word processor that reads processes the text files and displays information about the files 
 * 
 * @author Sean Gallagher
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class WordProcessor {
	private List<String> list;
	private String[] strings;

	/*
	 * Word processor constructor
	 * Initializes the list of strings
	 */
	public WordProcessor(){
		list = new ArrayList<String>();
	}

	/*
	 * Runs the word processor
	 */
	public void run(){
		readFiles();
		fillData();
		printInfo();
	}

	/*
	 * Prints the relevant information after the text files have been processed
	 * Prints out the total words, the first word alphabetically, the longest word, all N letter word counts,
	 * the word with the most unique letters, the word with the most repeated letters, and a random word
	 */
	private void printInfo() {
		String repeats = repeats();
		String unique = uniques();
		Collections.sort(list);
		String alpha = list.get(0);

		System.out.print("There are a total of " + list.size() + " unique words");
		System.out.println(" and a grand total of " + strings.length + " words.");
		System.out.println("The first word alphabetically is " + alpha + ".");

		Collections.sort(list, new LenComparator());
		System.out.println("The longest word is " + list.get(0) + " with " +list.get(0).length() + " characters.");

		for(int i=1; i<list.get(0).length()+1; i++){
			if(i == 1){
				System.out.println("There are " + NLetters(i) + " words with " + i + " letter.");
			}else
				System.out.println("There are " + NLetters(i) + " words with " + i + " letters.");
		}
		System.out.println(unique  + ".");
		System.out.println("The word with the greatest number of repeating characters is " + repeats + ".");



		int rand = ThreadLocalRandom.current().nextInt(0, list.size());
		System.out.println("A random word is " + list.get(rand) + ".");
	}

	/*
	 * Inserts each word into the list
	 */
	private void fillData(){
		for(int i=0; i< strings.length; i++){
			list.add(strings[i]);

		}
	}

	/*
	 * Scans each file and inserts each word into an array of strings
	 */
	private void readFiles(){
		String fileString = "";
		File file1 = new File("words1.txt");
		File file2 = new File("words2.txt");
		File file3 = new File("words3.txt");
		File file4 = new File("words4.txt");

		try {
			fileString = new Scanner(file1).useDelimiter("\\Z").next();
			fileString +="\n";
			fileString += new Scanner(file2).useDelimiter("\\Z").next();
			fileString +="\n";
			fileString += new Scanner(file3).useDelimiter("\\Z").next();
			fileString +="\n";
			fileString += new Scanner(file4).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		strings = fileString.split("\\r?\\n");	
	}

	/*
	 * Counts the number of words of length n
	 * 
	 * @param n is the length of the word to search for
	 * 
	 * @return the count of n letter words
	 */
	private int NLetters(int n){
		int count=0;
		if(n >= list.get(0).length()){
			for(int i=0; i< list.size(); i++){
				if(list.get(i).length() < n){
					break;
				}
				if(list.get(i).length() == n){
					count++;
				}
			}
		}else{
			for(int i=list.size()-1; i>=0; i--){
				if(list.get(i).length() > n){
					break;
				}
				if(list.get(i).length() == n){
					count++;
				}
			}
		}
		return count;
	}

	/*
	 * Checks the string array for the string with the most repeated use of a single character
	 * 
	 * @return the string with the most repeated use of the same character
	 */
	private String repeats(){
		int index = 0, max = 0;

		for(int i = 0; i < list.size(); i++) {
			for(int j = 0; j < list.get(i).length(); j++){
				int count = 0;
				for(int k = j+1; k < list.get(i).length(); k++){
					if(list.get(i).charAt(j) == list.get(i).charAt(k)){
						count++;
					}
					if(max < count){
						max = count;
						index = i;
					}
				}

			}
		}

		return list.get(index);
	}

	/*
	 * Searches for the string with the most unique characters
	 * 
	 * @return the string with the most unique characters
	 */
	private String uniques(){
		int max = 0, index = 0;
		boolean[] alphabet;
		for(int i = 0; i < list.size(); i++){
			alphabet = new boolean[Character.MAX_VALUE];
			for (int j = 0; j < list.get(i).length(); j++) {
				alphabet[list.get(i).charAt(j)] = true;
			}

			int count = 0;
			for (int k = 0; k < alphabet.length; k++) {
				if (alphabet[k] == true){
					count++;
				}
			}
			if(count > max){
				max = count;
				index = i;
			}
		}
		return "The word with the greatest number of unique characters is " + strings[index]  + " with " + max + " unique characters";
	}
}

