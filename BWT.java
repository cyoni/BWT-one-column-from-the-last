import java.util.Arrays;
import java.util.Random;

public class BWT_assignment {

	static String globalWord;
	static int NUMBER_OF_STEPS_FROM_LAST_COLUMN = 2;
	static boolean isPrintToConsoleAllowed = false;

	public static String BWT(String word_to_encode, boolean isToPrint) {
		isPrintToConsoleAllowed = isToPrint;
		globalWord = word_to_encode;
		
		String mat[][] = buildMatrix(word_to_encode);
		sortMatrix(mat);
		String[] encodedWordAndNumOfRowOfFirstLetter = getEncodedString_And_NumOfRowOfFirstLetter(mat);
		
		int num_of_row_of_the_first_letter = Integer.parseInt(encodedWordAndNumOfRowOfFirstLetter[1]);
		String first_letter_in_decoded_word = mat[Integer.parseInt(encodedWordAndNumOfRowOfFirstLetter[1])][0];
		String decoded_word = convertBWTStringToText(mat, first_letter_in_decoded_word, num_of_row_of_the_first_letter);
		
		if (isPrintToConsoleAllowed) {
			printMatrix(mat);
			System.out.println("Input: " + word_to_encode);
			System.out.println("Decoded word: " + decoded_word.toString());
		}
		return decoded_word;
	}

	private static String convertBWTStringToText(String[][] mat, String letter, int index) {
		int starting_index = index;
		StringBuffer answer = new StringBuffer();
		answer.append(mat[index][0]);
		
		int word_counter = 0;
		boolean loop_detected = false;
		while (word_counter != mat.length-1) {
			int count = 0;
			for (int i = 0; i < index+1; i++) {
			if (mat[i][0].equals(letter))
				count++;
		    }
			 
			int indexOfNextLetter = getIndexOfNextLetter(mat, count, letter);
			 
			if (word_counter > 0 && starting_index == indexOfNextLetter) 
				loop_detected = true;
			  
			String nextLetter = mat[indexOfNextLetter][0];
			answer.append(nextLetter);
			letter = nextLetter;
			index = indexOfNextLetter;
			word_counter++;
		 }
		
		// if (loop_detected)
	    // System.out.println("! loop detected");
		 
		 return answer.toString();
	}

	private static int getIndexOfNextLetter(String[][] mat, int count, String letter) {
		int i;
		 for (i = 0; i < mat.length && count > 0; i++) {
			if (mat[i][mat.length - NUMBER_OF_STEPS_FROM_LAST_COLUMN].equals(letter)) {
				count--;
			}
		}
		return i-1;
	}

	private static String[] getEncodedString_And_NumOfRowOfFirstLetter(String[][] mat) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mat.length; i++) 
			sb.append(mat[i][mat.length - NUMBER_OF_STEPS_FROM_LAST_COLUMN]);
		if (isPrintToConsoleAllowed)
			System.out.println("BWT(T) = " + sb.toString() + ", " + findStartingIndex(mat));
		return new String[]{sb.toString(), findStartingIndex(mat)+""};
	}

	private static int findStartingIndex(String[][] mat) {
		int index = 0;
		StringBuffer my_word = new StringBuffer();

		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) 
				my_word.append(mat[i][j]);
			
			if (my_word.toString().equals(globalWord)){
					index = i;
					break;
			}
			my_word.delete(0, globalWord.length());
			
		}

		return index;
	}

	private static void sortMatrix(String[][] mat) {
		String words[] = new String[mat.length];
		StringBuffer word = new StringBuffer();
		
		// taking words from matrix and putting them in 'words' array
		for (int i = 0; i < words.length; i++) {
			word.delete(0, word.length());
			for (int j = 0; j < words.length; j++) {
				word.append(mat[i][j]);
			}
			words[i] = word.toString();
		}
		
	    for(int i = 0; i < words.length-1; ++i) {
            for (int j = i + 1; j < words.length; ++j) {
                if (words[i].compareTo(words[j]) > 0) {
                    String temp = words[i];
                    words[i] = words[j];
                    words[j] = temp;
                }
            }
        }
	    
	    // copy words in array into matrix
	    for (int i = 0; i < words.length; i++) {
			for (int j = 0; j < words.length; j++) {
				mat[i][j] = words[i].charAt(j) + "";
			}
		}
	}

	private static void printMatrix(String[][] mat) {
		for (int i=0; i<mat.length; i++) {
			  for (int j = 0; j < mat.length; j++) 
				  System.out.print(mat[i][j] + " "); 
			  System.out.println(); 
		}
	}
	
	private static String[][] buildMatrix(String word) {
		int size = word.length();
		String mat[][] = new String[size][size];
		
		for (int i = 0; i < mat.length; i++) 
			mat[0][i] = word.charAt(i) + "";
		
		for (int i=0; i<size-1; i++) {
			for (int j=1; j<size; j++)
				mat[i+1][j-1] = mat[i][j];
			mat[i+1][size-1] = mat[i][0];
		}
		
		return mat;
	}
	
	
	private static void print_S_Array(String[][] mat) {
		//clone array
		String[][] newMat = new String[mat.length][mat.length];
		for (int i = 0; i < newMat.length; i++) {
			for (int j = 0; j < newMat.length; j++) {
				newMat[i][j] = mat[i][j];
			}
		}

		int result[] = new int[mat.length];
		for (int i = 0; i < result.length; i++) {
			String letter = newMat[i][0];
			boolean flag = true;
			for (int j = 0; j < result.length && flag; j++) {
				if (newMat[j][newMat.length - NUMBER_OF_STEPS_FROM_LAST_COLUMN].equals(letter)) {
					newMat[j][newMat.length - NUMBER_OF_STEPS_FROM_LAST_COLUMN] = "null";
					result[i] = j;
					flag = false;
				}
			}
		}
		System.out.println("S: " + Arrays.toString(result));
	}
	

	private static void brutForce(int words_with_length_of) {
		int iterations = 10000;
		for ( ; words_with_length_of < 100 ; words_with_length_of++ ) {
			System.out.println("WORDS OF LENGTH: " + words_with_length_of);
		for (int i=0; i<iterations; i++) {
			String[] words = generateRandomWords(iterations, words_with_length_of);
			//System.out.println(words[i]);
			if (BWT(words[i], false).equals(words[i])) {
				int different_letters = howManyLetters(words[i]);
				System.out.println("diffrent letters "+different_letters + ": " + words[i]);
			}
		}
		System.gc();
		}
	}
	
	private static int howManyLetters(String string) {
		int letters[] = new int[26];
		
		for (int i=0; i<string.length(); i++) {
			letters[string.charAt(i) - 'a']++;
		}
		
		int counter = 0;
		for (int i=0; i<26; i++) {
			if (letters[i] > 0)
				counter++;
		}
		
		return counter;	
	}

	public static String[] generateRandomWords(int numberOfWords, int words_with_length_of){
	    String[] randomStrings = new String[numberOfWords];
	    Random random = new Random();
	    for(int i = 0; i < numberOfWords; i++){
	        int word_length = words_with_length_of;
			char[] word = new char[word_length]; 
			int max_different_letters = 4;
			
	        for(int j = 0; j < word.length; j++)
	            word[j] = (char)('a' +  random.nextInt(max_different_letters)); 
	        
	        randomStrings[i] = new String(word);
	    }
	    return randomStrings;
	}

	public static void main(String[] args) {
		
		// single run:
		boolean printResults = true;
		BWT("mississippi", printResults);  
		
		
		// find all possibilities:
		// int start_looking_with_length_of = 6;
		// brutForce(start_looking_with_length_of);
		
	
	}
	
	
}
