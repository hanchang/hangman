package com.szuhanchang.hangman;
/**
 * A Guess that represents guessing a letter for the current Hangman game
 */
public class GuessLetter implements Guess {
  private final char guess;

  public GuessLetter(char guess) {
    this.guess = guess;
  }

  public void makeGuess(HangmanGame game) {
    game.guessLetter(guess);
  }

  @Override
  public String toString() {
    return "GuessLetter[" + guess + "]";
  }
}
