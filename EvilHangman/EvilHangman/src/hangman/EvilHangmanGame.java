package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {

    private Set<String> dictionaryWords;
    private Set<String> biggestSubset;
    private SortedSet<Character> usedLetters;
    private StringBuilder wordToGuess;
    private Map<String, Set<String>> map;
    private int guessCorrect;
    private int wordSize;

    public EvilHangmanGame(){
        dictionaryWords = new HashSet<>();
        biggestSubset = new HashSet<>();
        map = new HashMap<>();
        usedLetters = new TreeSet<>();
        wordToGuess = new StringBuilder();
        guessCorrect = 0;
        wordSize = 0;
    }

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {

        reset();
        wordSize = wordLength;
        Scanner scanner = new Scanner(dictionary);
        int longestWord = 0;

        while (scanner.hasNext()){
            String word = scanner.next().toLowerCase();
            if(word.length() == wordSize)
                dictionaryWords.add(word);
            if(word.length() > longestWord)
                longestWord = word.length();
        }

        if(dictionaryWords.size() == 0)
            throw new EmptyDictionaryException("Empty dictionary...");
        if(wordLength < 2)
            throw new EmptyDictionaryException(("Word length less than 2"));
        if(wordLength > longestWord)
            throw new EmptyDictionaryException("wordLength longer than longestWord");
        wordToGuess.append("_".repeat(wordSize));

        scanner.close();
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {

        guessCorrect = 0;
        if(guess >= 'A' & guess <= 'Z')
            guess = Character.toLowerCase(guess);

        // Check if guess has already been made. Add it to list if not and continue
        if(usedLetters.contains(guess))
            throw new GuessAlreadyMadeException("Guess already made!");
        usedLetters.add(guess);

        // Add all dictionary words to map. Use guessed character to determine where they go
        for(String word : dictionaryWords){
            StringBuilder s = new StringBuilder();
            for(int i = 0; i < word.length(); i++)
                if(word.charAt(i) == guess)
                    s.append(guess);
                else
                    s.append('_');
            if(!map.containsKey(s.toString()))
                map.put(s.toString(), new HashSet<>());
            map.get(s.toString()).add(word); // Uses map key s to access HashSet and add new word from dictionary
        }

        String s = ""; // Used to update wordToGuess and
        for(Map.Entry<String, Set<String>> entry : map.entrySet()){
            if(entry.getValue().size() > biggestSubset.size()){
                biggestSubset = entry.getValue(); s = entry.getKey();
            }
            // If the subsets are the same size go through 4 criteria to choose best
            else if(entry.getValue().size() == biggestSubset.size()){
                // First: words with no instances of the guess
                if(hasGuessedLetter(entry.getKey(), guess) == 0){ biggestSubset = entry.getValue(); s = entry.getKey(); }

                // Second: fewest instances of the letters
                else if(hasGuessedLetter(entry.getKey(), guess) < hasGuessedLetter(s, guess)){
                    biggestSubset = entry.getValue();
                    s = entry.getKey();
                }
                else if(hasGuessedLetter(entry.getKey(), guess) > hasGuessedLetter(s, guess)){ assert true; }

                // Third: If the number of instances is equal, choose the one where it is furthest right
                else if(rightMostLetter(entry.getKey(), guess, wordSize) > rightMostLetter(s, guess, wordSize)){
                    biggestSubset = entry.getValue();
                    s = entry.getKey();
                }
                else if(rightMostLetter(entry.getKey(), guess, wordSize) < rightMostLetter(s, guess, wordSize))
                    assert true;

                // Fourth: Get the next right most letter
                else if(rightMostLetter(entry.getKey(), guess, rightMostLetter(s, guess, wordSize)) > rightMostLetter(s, guess, rightMostLetter(s, guess, wordSize))){
                    biggestSubset = entry.getValue();
                    s = entry.getKey();
                }
            }
        }

        // Update wordToGuess with new
        int instances = 0;
        for(int i = 0; i < wordSize; i++){
            if(s.charAt(i) == guess){
                wordToGuess.setCharAt(i, guess);
                instances++;
            }
        }

        if(instances != 0){
            System.out.println("Yes, there are (" + instances + ") " + guess + "'s!");
            guessCorrect = 1;
        }
        else
            System.out.println("Sorry, there are no " + guess);

        dictionaryWords = biggestSubset;
        biggestSubset = new HashSet<>();
        map = new HashMap<>();
        return dictionaryWords;
    }

    @Override
    public SortedSet<Character> getGuessedLetters(){
        return usedLetters;
    }

    public int hasGuessedLetter(String s, char guess){
        int instances = 0;
        for(int i = 0; i < s.length(); i++)
            if(s.charAt(i) == guess)
                instances++;
        return instances;
    }

    public int rightMostLetter(String s, char guess, int maxIndex){
        int index = 0;
        for(int i = 0; i < maxIndex; i++)
            if(s.charAt(i) == guess)
                index = i;
        return index;
    }

    public boolean isGameWon(String s){
        for(int i = 0; i < s.length(); i++)
            if(s.charAt(i) == '_')
                return false;
        return true;
    }

    public void reset(){
        dictionaryWords = new HashSet<>();
        biggestSubset = new HashSet<>();
        map = new HashMap<>();
        usedLetters = new TreeSet<>();
        wordToGuess = new StringBuilder();
        guessCorrect = 0;
        wordSize = 0;
    }

    public String getWordToGuess(){ return wordToGuess.toString(); }
    public int getGuessCorrect(){ return guessCorrect; }
    public String getLoseWord(){ return dictionaryWords.toArray()[0].toString(); }
}