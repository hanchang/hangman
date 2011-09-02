package com.szuhanchang.hangman;

import java.io.IOException;

import com.szuhanchang.hangman.HangmanGame.Status;

public class HangmanStrategyRunner {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 3) {
			System.out.println("Usage: HangmanStrategyRunner <dictionary-path> <secret-word> <max-wrong-guesses>");
			return;
		}

		String dictionaryPath = args[0];
		String secretWord = args[1];
		int maxWrongGuesses = Integer.parseInt(args[2]);

		HangmanStrategyRunner strategyExecutor = new HangmanStrategyRunner();
		HangmanGame game = new HangmanGame(secretWord, maxWrongGuesses);
		HanChangGuessingStrategy strategy = new HanChangGuessingStrategy(dictionaryPath);
		int score = strategyExecutor.run(game, strategy);
		System.out.println(
			"Final score of " + score + " for secret word = '" + secretWord + "' with " + maxWrongGuesses + " maximum guesses."
		);
	}

	// runs your strategy for the given game, then returns the score
	public int run(HangmanGame game, GuessingStrategy strategy) {
		strategy.init(game);
		
		while (game.gameStatus().equals(Status.KEEP_GUESSING)) {
			strategy.nextGuess(game).makeGuess(game);
			System.out.println(game);
		}
		
		return game.currentScore();
	}
}
