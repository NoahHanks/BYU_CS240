package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EvilHangman{
    public static void main(String[] args){
        String file = args[0];
        File dictionary = new File(file);
        int wordLength = Integer.parseInt(args[1]);
        int guesses = Integer.parseInt(args[2]);
        boolean gameWon = false;

        Scanner scan = new Scanner(System.in);
        EvilHangmanGame game = new EvilHangmanGame();

        try{
            game.startGame(dictionary, wordLength);
            char c;
            String line;

            while(guesses > 0 & !gameWon){

                System.out.println("You have " + guesses + " guesses left");
                System.out.println("Used letters: " + game.getGuessedLetters().toString());
                System.out.println("Word: " + game.getWordToGuess());
                System.out.println("Enter guess: ");

                line = scan.nextLine();
                c = line.toLowerCase().charAt(0);

                while(c < 'a' | c > 'z' | line.length() != 1){
                    System.out.println("Invalid input! Enter guess: ");
                    line = scan.nextLine();
                    c = line.toLowerCase().charAt(0);
                }

                try{
                    if(game.makeGuess(c) != null & game.getGuessCorrect() != 1)
                        guesses--;
                }
                catch(GuessAlreadyMadeException e){
                    System.out.println("Guess already made!");
                }
                if(game.isGameWon(game.getWordToGuess()))
                    gameWon = true;
            }
        }
        catch(EmptyDictionaryException | IOException e){
            e.printStackTrace();
        }

        if(gameWon)
            System.out.println("You win! You guessed the word: " + game.getWordToGuess());
        if(!gameWon)
            System.out.println("You lose! Word was: " + game.getLoseWord());

        scan.close();
    }
}
