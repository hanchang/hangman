package com.szuhanchang.hangman;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

// TODO: This really needs to extend set instead of wrapping it.
public class PossibleWordsSet {
	private Set<String> possibleWords = new HashSet<String>();
	private CharacterFrequencyTable frequencyTable = new CharacterFrequencyTable();
	
	private static final long serialVersionUID = 1L;

	public boolean addAll(Collection<? extends String> collection) {
		for (String s : collection) {
			for (char c : s.toCharArray()) {
				frequencyTable.increment(c);
			}
		}
		
		return possibleWords.addAll(collection);
	}
	
	public String getLastWord() {
		if (possibleWords.size() != 1) {
			return null;
		}
		
		String[] retval = possibleWords.toArray(new String[0]);
		return retval[0];
	}
	
	public boolean remove(String arg) {
		for (char c : arg.toCharArray()) {
			frequencyTable.decrement(c);
		}
		return possibleWords.remove(arg);
	}
	
	public void clear() {
		possibleWords.clear();
	}
	
	public int size() {
		return possibleWords.size();
	}
	
	public String[] toArray() {
		return possibleWords.toArray(new String[0]);
	}
	
	public String toString() {
		return possibleWords.toString();
	}
	
	public int clearCharacter(char c) {
		return frequencyTable.remove(c);
	}
	
	public char getCharacterWithHighestFrequency() {
		return frequencyTable.getCharacterWithHighestFrequency();
	}
}
