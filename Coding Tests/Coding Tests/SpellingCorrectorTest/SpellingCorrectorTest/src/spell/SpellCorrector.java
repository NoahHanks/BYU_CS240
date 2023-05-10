package spell;

import java.io.*;
import java.util.*;

public class SpellCorrector implements ISpellCorrector{
        private final Trie dictionary;
        private final TreeSet<String> wordList;
        private final TreeSet<String> wordSuggestions;

    public SpellCorrector() {
        dictionary = new Trie();
        wordList = new TreeSet<>();
        wordSuggestions = new TreeSet<>();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        addToDictionary(file);
    }

    public void addToDictionary(File file) throws IOException {
        Scanner scanner = new Scanner(file);

        while(scanner.hasNext()){
            String word = scanner.next();
            dictionary.add(word);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        if(inputWord.equals("")) return null;
        inputWord = inputWord.toLowerCase();
        if(dictionary.find(inputWord) != null) return inputWord;

        wordList.clear();
        wordSuggestions.clear();
        theFourEditDistances(inputWord);
        String mostCommonWord = mostCommonWord();

        if(wordSuggestions.isEmpty()){
            TreeSet<String> wordListClone = (TreeSet)wordList.clone();
            for(String word : wordListClone) theFourEditDistances(word);
            mostCommonWord = mostCommonWord();
            if(wordSuggestions.isEmpty()) return null;
        }
        if(wordSuggestions.size() == 1) return wordSuggestions.first();
        return mostCommonWord;
    }

    public void deletionDistance(String word){
        if(word.length() < 2)  return;
        for(int i = 0; i < word.length(); i++){
            StringBuilder newWord  = new StringBuilder(word);
            newWord.deleteCharAt(i);
            wordList.add(newWord.toString());
        }
    }

    public void transpositionDistance(String word){
        if(word.length() < 2)  return;
        StringBuilder newWord = new StringBuilder();
        StringBuilder subString = new StringBuilder();

        for(int i = 0; i < word.length() - 1; i++){
            newWord.append(word);
            subString.append(newWord.substring(i, i+2));
            subString.reverse();
            newWord.replace(i, i+2, subString.toString());
            wordList.add(newWord.toString());
            newWord.setLength(0);
            subString.setLength(0);
        }
    }

    public void alterationDistance(String word){
        for(int i = 0; i < word.length(); i++){
            StringBuilder newWord  = new StringBuilder(word);
            for(char c = 'a'; c <= 'z'; c++){
                if(word.charAt(i) != c){
                    newWord.setCharAt(i, c);
                    wordList.add(newWord.toString());
                }
            }
        }
    }

    public void insertionDistance(String word){
        for(int i = 0; i <= word.length(); i++){
            for(char c = 'a'; c <= 'z'; c++){
                StringBuilder newWord  = new StringBuilder(word);
                newWord.insert(i, c);
                wordList.add(newWord.toString());
            }
        }
    }

    public void theFourEditDistances(String word){
        deletionDistance(word);
        transpositionDistance(word);
        alterationDistance(word);
        insertionDistance(word);
    }

    public String mostCommonWord(){
        int max = 0;
        INode tempNode;
        String mostCommonWord = null;

        for(String word : wordList){
            tempNode = dictionary.find(word);
            if(tempNode != null){
                wordSuggestions.add(word);
                if(tempNode.getValue() > max){
                    max = tempNode.getValue();
                    mostCommonWord = word;
                }
            }
        }
        return mostCommonWord;
    }



}






















