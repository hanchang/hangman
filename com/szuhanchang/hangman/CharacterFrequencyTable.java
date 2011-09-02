package com.szuhanchang.hangman;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Maps a character in the alphabet to the frequency in which it occurs in the PossibleWordsSet.
 */
public class CharacterFrequencyTable extends ConcurrentHashMap<Character, Integer> {
	private static final long serialVersionUID = 1L;

	final static int LETTERS_IN_ALPHABET = 26; // Assume standard English alphabet for now, case-insensitive.
	
	public CharacterFrequencyTable() {
		super(LETTERS_IN_ALPHABET);
	}
    
	public void increment(char character) {
		Integer prevValue = this.get(character);
		if (prevValue == null) {
			prevValue = 0;
		}
		this.put(character, prevValue + 1);
	}
	
	public void decrement(char character) {
		Integer prevValue = this.get(character);
		if (prevValue == null) {
			return;
		}
		this.put(character, prevValue - 1);
	}
	
	public int getFrequency(char character) {
		return this.get(character);
	}
	
	public char getCharacterWithHighestFrequency() {
		//System.out.println(this.toString());

		int max = 0;
		char mostFrequentChar = ' ';
		for (Map.Entry<Character, Integer> e : this.entrySet()) {
			if (e.getValue() == 0) {
				this.remove(e.getKey());
			}
			else if (e.getValue() > max) {
				max = e.getValue();
				mostFrequentChar = e.getKey();
			}
		}
		
		return mostFrequentChar;
	}
}
