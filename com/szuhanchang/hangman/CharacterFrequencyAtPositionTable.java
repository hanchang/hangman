package com.szuhanchang.hangman;

import java.util.TreeMap;

public class CharacterFrequencyAtPositionTable {
	// { positionIndex => { character => frequency } }
	protected TreeMap<Integer, TreeMap<Character, Integer>> table =                            
        new TreeMap<Integer, TreeMap<Character, Integer>>();
	// TODO: Needs a custom comparator.
	
	private void init(int positionIndex, char character) {
		if (table.get(positionIndex) == null) {
			TreeMap<Character, Integer> map = new TreeMap<Character, Integer>();
			map.put(character, 0);
			table.put(positionIndex, map);
		}
		
		if (table.get(positionIndex).get(character) == null) {
			table.get(positionIndex).put(character, 0);
		}
	}
	
	public void increment(int positionIndex, char character) {
		init(positionIndex, character);
		table.get(positionIndex).put(character, getFrequency(positionIndex, character) + 1);
	}
	
	public void decrement(int positionIndex, char character) {
		init(positionIndex, character);
		table.get(positionIndex).put(character, getFrequency(positionIndex, character) - 1);
	}
	
	public int getFrequency(int positionIndex, char character) {
		init(positionIndex, character);
		return table.get(positionIndex).get(character);
	}
	
	public char getCharacterWithHighestFrequency() {
		// FIXME: This returns by alphabetic sorting. Fix to use frequency sorting.
		return table.lastEntry().getValue().lastKey();
	}
}
