package com.szuhanchang.hangman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.eaio.stringsearch.BNDMWildcards;

public class HanChangGuessingStrategy implements GuessingStrategy {
	
	public final static String[] STRING_ARRAY_PLACEHOLDER = new String[0];
	protected Map<Integer, StrategyGameState> stateMap = new ConcurrentHashMap<Integer, StrategyGameState>();
	
    /*
     * Dictionary populated by list of words in a text file.
     * 
     * Key: Number of characters in word.
     * Value: Set of words in dictionary that contain that many number of characters in word.
     */
    protected final Map<Integer, Set<String>> dictionary = new HashMap<Integer, Set<String>>();
    
    // Specialized, crazy fast search algorithm that handles wildcards.
    protected final BNDMWildcards bndmWild = new BNDMWildcards(HangmanGame.MYSTERY_LETTER);
        
    public HanChangGuessingStrategy(String dictionaryPath) throws IOException {                         
    	BufferedReader inputStream = null; 
        
        try {                                                                                           
            inputStream = new BufferedReader(new FileReader(dictionaryPath));                           
            
            String line;                                                                                
            while ((line = inputStream.readLine()) != null) { 
            	line = line.trim();
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
    	StrategyGameState state = new StrategyGameState();
    	
    	state.secretWordLength = game.getSecretWordLength();
    	
    	// Populate possibleWords based on length of secret word.
    	state.possibleWords.clear();
    	if (dictionary.get(state.secretWordLength) == null) {
    		System.err.println("The secret word does not appear in the provided dictionary.");
    	}
    	state.possibleWords.addAll(dictionary.get(state.secretWordLength));
    	
    	stateMap.put(game.hashCode(), state);
    }

	public Guess nextGuess(HangmanGame game) {
		StrategyGameState state = stateMap.get(game.hashCode());
	    
		if (game.getCorrectlyGuessedLetters().contains(state.previousGuessedChar)) {
			String pattern = game.getGuessedSoFar();
			Object processed = bndmWild.processString(pattern);
			for (String word : state.possibleWords.toArray(STRING_ARRAY_PLACEHOLDER)) {
				if (bndmWild.searchString(word, pattern, processed) == -1) {
					state.possibleWords.remove(word);
				}
			}
			
			// Remove the correctly guessed letter from the frequencyTable since it won't ever be used again.
			state.possibleWords.removeCharacter(state.previousGuessedChar);
		}
		else if (game.getIncorrectlyGuessedLetters().contains(state.previousGuessedChar)) {
			// Remove all words in possibleWords containing the previously guessed letter.
			String searchChar = Character.toString(state.previousGuessedChar);
			for (String word : state.possibleWords.toArray(STRING_ARRAY_PLACEHOLDER)) {
				if (StringUtils.containsAny(word, searchChar)) {
					state.possibleWords.remove(word);
				}
			}
		}
		
		System.out.println(state.possibleWords.toString());
		
		if (state.possibleWords.size() == 0 || state.possibleWords.getFrequencyTableSize() == 0) {
			System.err.println("The secret word does not appear in the provided dictionary.");
			System.exit(-1);
		}
		
		if (state.possibleWords.size() == 1) {
			return new GuessWord(state.possibleWords.getLastWord());
		}
		
		state.previousGuessedChar = state.possibleWords.getCharacterWithHighestFrequency();
		System.out.println("nextGuess: " + state.previousGuessedChar);
		return new GuessLetter(state.previousGuessedChar);
	}
}
