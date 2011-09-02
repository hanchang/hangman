package com.szuhanchang.hangman;

import java.io.IOException;
import java.util.ArrayList;

import com.szuhanchang.hangman.HangmanGame.Status;

public class HangmanStrategyRunner {
	private final static int DEFAULT_WRONG_GUESSES = 5;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		
		// TODO: Add functionality to allow customized maxWrongGuesses values.
		int maxWrongGuesses = DEFAULT_WRONG_GUESSES;
		
		if (args.length < 2) {
			System.out.println("Usage: HangmanStrategyRunner <dictionary-file> <mystery-word-1>, [mystery-word-2], ...");
			return;
		}
		
		String dictionaryPath = args[0];
		
		ArrayList<String> mysteryWordList = new ArrayList<String>();
		
		for (int iter = 1; iter < args.length; iter++) {
			mysteryWordList.add(args[iter].toUpperCase());
		}
		
		HangmanStrategyRunner strategyExecutor = new HangmanStrategyRunner();
		HanChangGuessingStrategy strategy = new HanChangGuessingStrategy(dictionaryPath);
		
		int totalScore = 0;
		int currentScore = 0;
		double averageScore = 0.0;
		
		long wordStartTime = 0;
		long wordElapsedTime = 0;
		
		for (String mysteryWord : mysteryWordList) {
			wordStartTime = System.currentTimeMillis();
			
			HangmanGame game = new HangmanGame(mysteryWord, maxWrongGuesses);
			currentScore = strategyExecutor.run(game, strategy);
			totalScore += currentScore;
			
			wordElapsedTime = System.currentTimeMillis() - wordStartTime;
			System.out.println(mysteryWord + ": " + currentScore + " (" + wordElapsedTime + "ms)");
		}
		
		averageScore = (double) totalScore / (double) mysteryWordList.size();
		
		System.out.println("Overall average score: " + averageScore);
		
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("Total time elapsed: " + elapsedTime + " ms");
	}

	// runs your strategy for the given game, then returns the score
	public int run(HangmanGame game, GuessingStrategy strategy) {
		strategy.init(game);
		
		while (game.gameStatus().equals(Status.KEEP_GUESSING)) {
			strategy.nextGuess(game).makeGuess(game);
			//System.out.println(game);
		}
		
		return game.currentScore();
	}

}
