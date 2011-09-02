package com.szuhanchang.hangman;

public class StrategyGameState {
	char previousGuessedChar = ' ';
	int secretWordLength = -1;
	
	// Subset of words from dictionary that could still be the mystery word.
    final PossibleWordsSet possibleWords = new PossibleWordsSet();
}
