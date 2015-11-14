/* 
 * The MIT License
 *
 * Copyright 2015 Fanaen <contact@fanaen.fr>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.fanaen.alphagram.model;

import fr.fanaen.alphagram.helper.TextHelper;
import java.util.Locale;

/**
 *
 * @author Fanaen <contact@fanaen.fr>
 */
public class Alphagram {

    // -- Attributes --
    private int[] occurenceList; 
    
    // -- Constructors --
    public Alphagram(String rawAnagram) {
        String rawAlphagram = buildRawAlphagram(rawAnagram);
        occurenceList = Alphagram.buildLetterOccurenceList(rawAlphagram);
    }
    
    public Alphagram(Alphagram alphagram) {
        occurenceList = initOccurenceList();
        apply(alphagram, 1);
    }
    
    // -- Init methods --
    
    private static int[] initOccurenceList() {
        int[] occurenceList = new int[26];
        
        // Init occurenceList --
        for (int i = 0; i < 26; i++) {
            occurenceList[i] = 0;
        }
        
        return occurenceList;
    }
    
    private String buildRawAlphagram(String rawAnagram) {
        String alphagram = rawAnagram;
        
        alphagram = TextHelper.removeDiacritics(alphagram);
        alphagram = alphagram.toLowerCase(Locale.ENGLISH);
        alphagram = TextHelper.handleNumbers(alphagram);
        alphagram = TextHelper.removeNonLetters(alphagram);
        alphagram = TextHelper.sortLetters(alphagram);
        
        return alphagram;
    }
    
    private static int[] buildLetterOccurenceList(String rawAnagram) {
        char[] ar = rawAnagram.toCharArray();
        int[] occurenceList = initOccurenceList();
        int index;
        
        // Parse string --
        for (char c : ar) {
            index = charToIndex(c);
            
            if(index >= 0 && index < 26) {
                occurenceList[index]++;
            }
        }
        
        return occurenceList;
    }
    
    // -- Operation methods --

    public void apply(Alphagram processedWord, int coef) {
        for (int i = 0; i < 26; i++) {
            occurenceList[i] += processedWord.occurenceList[i] * coef;
        }
    }
    
    public boolean contains(Alphagram alpha) {
        for (int i = 0; i < 26; i++) {
            // If an occurence is lower than in the given alphagram, return false --
            if(occurenceList[i] < alpha.occurenceList[i])
                return false;
        }
        // Otherwise, the local alphagram contains the given one --
        return true;
    }
    
    public boolean contains(String value) {
        return contains(new Alphagram(value));
    }
    
    // -- Getters & Setters --
    
    public String getRaw() {
        // Build an letter only alphagram --
        StringBuilder positive = new StringBuilder(""), negative = new StringBuilder("");
        
        for (int i = 0; i < 26; i++) {
            int occurences = occurenceList[i];
            
            if(occurences > 0) {
                positive.append(repeatChar(indexToChar(i), occurences));
            }
            else if(occurences < 0) {
                negative.append(repeatChar(indexToChar(i), occurences));
            }
        }
        
        return positive + (negative.length() == 0 ? "" : "-" + negative);
    }
    
    
    public String getShort() {
        // Build an alphagram with numbers after letters --
        StringBuilder positive = new StringBuilder(""), negative = new StringBuilder("");
        
        for (int i = 0; i < 26; i++) {
            int occurences = occurenceList[i];
            
            if(occurences > 0) {
                positive.append(indexToChar(i));
                if(occurences != 1) positive.append(occurences);
            }
            else if(occurences < 0) {
                negative.append(indexToChar(i));
                if(occurences != -1) negative.append(-occurences);
            }
        }
        
        return positive + (negative.length() == 0 ? "" : "-" + negative);
    }
    
    
    public boolean isEmpty() {
        return countLetters() == 0;
    }   
    
    public int countLetters() {
        int nb = 0;
        for (int i = 0; i < 26; i++) {
            nb += occurenceList[i];
        }
        return nb;
    }   
    
    // -- Helpers --
    
    private static String repeatChar(char c, int number) {
        number = Math.abs(number);
        StringBuilder result = new StringBuilder(number);
        
        for (int i = 0; i < number; i++) {
            result.append(c);
        }
        
        return result.toString();
    }
    
    private static char indexToChar(int index) {
        return (char) (index + 97);
    }
    
    private static int charToIndex(char c) {
        return (int) (c - 97);
    }
}
