package com.szuhanchang.hangman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.eaio.stringsearch.BNDMWildcards;

public class HanChangGuessingStrategy implements GuessingStrategy {
    final int LETTERS_IN_ALPHABET = 26; // Assume standard English alphabet for now.                    
    final char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
    private int index = 0;
    
    protected int secretWordLength = -1;
    
    // Key: Number of characters in word.
    // Value: Set of words in dictionary that contain that many number of characters in word.
    protected final Map<Integer, Set<String>> dictionary = new HashMap<Integer, Set<String>>();
    
    protected final PossibleWordsSet possibleWords = new PossibleWordsSet();
    
    // Search algorithm for wildcards.
    protected final BNDMWildcards bndm = new BNDMWildcards(HangmanGame.MYSTERY_LETTER);
        
    public HanChangGuessingStrategy(String dictionaryPath) throws IOException {                         
        
    	BufferedReader inputStream = null; 
        
        try {                                                                                           
            inputStream = new BufferedReader(new FileReader(dictionaryPath));                           
            
            String line;                                                                                
            while ((line = inputStream.readLine()) != null) {                                           
                // TODO: trim line of whitespace.
                // TODO: Make sure each character in the string is one of the 26 ASCII lowercase English alphabet characters.
                int length = line.length();
                if (!dictionary.containsKey(length)) {                                                  
                    dictionary.put(length, new HashSet<String>());                                      
                }
                dictionary.get(length).add(line);
            }   
        } catch (IOException e) {
            e.printStackTrace();                                                                        
        } finally {
            if (inputStream != null) {                                                                  
                inputStream.close();                                                                    
            }
        }       
    }
    
    public void init(HangmanGame game) {
    	secretWordLength = game.getSecretWordLength();
    	
    	// Populate possibleWords based on length of secret word.
    	possibleWords.clear();
    	
    	if (dictionary.get(secretWordLength) == null) {
    		System.err.println("The secret word does not appear in the provided dictionary.");
    	}
    	
    	possibleWords.addAll(dictionary.get(secretWordLength));
    }

	public Guess nextGuess(HangmanGame game) {
		char g = possibleWords.getCharacterWithHighestFrequency();
		System.out.println("nextGuess: " + g);
		return new GuessLetter(g);
	}
}
