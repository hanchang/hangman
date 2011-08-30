package com.szuhanchang.hangman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class HanChangGuessingStrategy implements GuessingStrategy {
	final int LETTERS_IN_ALPHABET = 26; // Assume standard English alphabet for now.
	final char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	int index = 0;
	
	Set<String> dictionary = new HashSet<String>();
	
	public HanChangGuessingStrategy(String dictionaryPath) throws IOException {
		BufferedReader inputStream = null;

        try {
            inputStream = new BufferedReader(new FileReader(dictionaryPath));

            String line;
            while ((line = inputStream.readLine()) != null) {
            	dictionary.add(line);
            }
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        
        System.out.println(dictionary.toString());
	}

	public Guess nextGuess(HangmanGame game) {
		return new GuessLetter(alphabet[index++]);
	}

}
