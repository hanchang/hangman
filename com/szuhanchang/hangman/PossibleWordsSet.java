package com.szuhanchang.hangman;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PossibleWordsSet {
	private Set<String> possibleWords = new HashSet<String>();
	private CharacterFrequencyAtPositionTable frequencyTable = new CharacterFrequencyAtPositionTable();
	
	private static final long serialVersionUID = 1L;

	public boolean addAll(Collection<? extends String> collection) {
		for (String s : collection) {
			for (int index = 0; index < s.length(); index++) {
				frequencyTable.increment(index, s.charAt(index));
			}
		}
		
		return possibleWords.addAll(collection);
	}
	
	public boolean remove(String arg) {
		for (int index = 0; index < arg.length(); index++) {
			frequencyTable.decrement(index, arg.charAt(index));
		}
		return possibleWords.remove(arg);
	}
	
	public void clear() {
		possibleWords.clear();
	}
	
	public char getCharacterWithHighestFrequency() {
		return frequencyTable.getCharacterWithHighestFrequency();
	}
}
