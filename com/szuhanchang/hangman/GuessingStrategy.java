package com.szuhanchang.hangman;

/**
 * A strategy for generating guesses given the current state of a Hangman game.
 */
public interface GuessingStrategy {
	void init(HangmanGame game);
	Guess nextGuess(HangmanGame game);
}
