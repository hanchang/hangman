package com.szuhanchang.hangman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.eaio.stringsearch.BNDMWildcards;

public class HanChangGuessingStrategy implements GuessingStrategy {
    private char previousGuessedChar = ' ';
    protected int secretWordLength = -1;
    
    // Key: Number of characters in word.
    // Value: Set of words in dictionary that contain that many number of characters in word.
    protected final Map<Integer, Set<String>> dictionary = new HashMap<Integer, Set<String>>();
    
    protected final PossibleWordsSet possibleWords = new PossibleWordsSet();
    
    // Search algorithm for wildcards.
    protected final BNDMWildcards bndmWild = new BNDMWildcards(HangmanGame.MYSTERY_LETTER);
        
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
                dictionary.get(length).add(line.toUpperCase());
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
		// Update strategy with whether or not the previous guess was successful.
		if (game.getCorrectlyGuessedLetters().contains(previousGuessedChar)) {
			String pattern = game.getGuessedSoFar();
			Object processed = bndmWild.processString(pattern, HangmanGame.MYSTERY_LETTER);
			for (String word : possibleWords.toArray()) {
				if (bndmWild.searchString(word, pattern, processed) == -1) {
					possibleWords.remove(word);
				}
			}
			
			// Remove the correctly guessed letter from the frequencyTable since it won't ever be used again.
			possibleWords.clearCharacter(previousGuessedChar);
		}
		else if (game.getIncorrectlyGuessedLetters().contains(previousGuessedChar)) {
			// Remove all words in possibleWords containing the previously guessed letter.
			String searchChar = Character.toString(previousGuessedChar);
			for (String word : possibleWords.toArray()) {
				if (StringUtils.containsAny(word, searchChar)) {
					possibleWords.remove(word);
				}
			}
		}
		
		System.out.println(possibleWords.toString());
		
		if (possibleWords.size() == 1) {
			return new GuessWord(possibleWords.getLastWord());
		}
		
		previousGuessedChar = possibleWords.getCharacterWithHighestFrequency();
		System.out.println("nextGuess: " + previousGuessedChar);
		return new GuessLetter(previousGuessedChar);
	}
}
