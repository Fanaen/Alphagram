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
package alphagram.model;

import alphagram.helper.TextHelper;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Fanaen <contact@fanaen.fr>
 */
public class Alphagram {

    // -- Attributes --
    private String rawAlphagram;
    private List<Letter> letterOccurenceList;
    
    // -- Constructors --
    public Alphagram(String rawAnagram) {
        rawAlphagram = buildRawAlphagram(rawAnagram);
        letterOccurenceList = Alphagram.buildLetterOccurenceList(rawAlphagram);
    }
    
    private String buildRawAlphagram(String rawAnagram) {
        String alphagram = rawAnagram;
        
        alphagram = TextHelper.removeDiacritics(alphagram);
        alphagram = alphagram.toLowerCase(Locale.ENGLISH);
        alphagram = TextHelper.removeNonLetters(alphagram);
        alphagram = TextHelper.sortLetters(alphagram);
        
        return alphagram;
    }
    
    private static List<Letter> buildLetterOccurenceList(String rawAnagram) {
        char[] ar = rawAnagram.toCharArray();
        List<Letter> newList = new LinkedList();
        Letter currentLetter = null;
        
        for (char c : ar) {
            // New character --
            if(currentLetter == null || currentLetter.getLetter() != c) {
                currentLetter = new Letter(c);
                newList.add(currentLetter);
            }
            // Existing character --
            else {
                currentLetter.increaseNbOccurence();
            }
        }
        
        return newList;
    }

    public void apply(Alphagram processedWord, int coef) {
        // Transform item to add as modifier --
        processedWord.applyCoef(coef);
        
        // Merge lists and sort the result --
        letterOccurenceList.addAll(processedWord.letterOccurenceList);
        Collections.sort(letterOccurenceList);
        
        // Merge multiple items --
        Letter lastLetter = null;
        for (Iterator<Letter> iterator = letterOccurenceList.iterator(); iterator.hasNext();) {
            Letter next = iterator.next();
            
            if(lastLetter != null && lastLetter.equals(next)) {
                // Items are identical, merge them --
                lastLetter.increaseNbOccurence(next.getNbOccurence());
                iterator.remove();
            }
            else {
                // Items are not identical, pursue --
                lastLetter = next;
            }
        }
        
        // Remove items with 0 occurences --
        for (Iterator<Letter> iterator = letterOccurenceList.iterator(); iterator.hasNext();) {
            Letter next = iterator.next();
            if(next.getNbOccurence() == 0) iterator.remove();
        }
    }

    
    // -- Getters & Setters --
    
    public String getRaw() {
        String positive = "", negative = "";
        
        if(letterOccurenceList.size() == 0) {
            return "*";
        }
        
        for (Letter l : letterOccurenceList) {
            if(l.getNbOccurence() > 0) {
                positive += l.toStringExpanded();
            }
            else {
                negative += l.toStringExpanded();
            }
        }
        
        return positive + (negative.equals("") ? "" : "-" + negative);
    }
    
    public String getShort() {
        String positive = "", negative = "";
        
        if(letterOccurenceList.size() == 0) {
            return "*";
        }
        
        for (Letter l : letterOccurenceList) {
            if(l.getNbOccurence() > 0) {
                positive += l.toStringReduced();
            }
            else {
                negative += l.toStringReduced();
            }
        }
        
        return positive + (negative.equals("") ? "" : "-" + negative);
    }

    private void applyCoef(int coef) {
        for (Letter l : letterOccurenceList) {
            l.setNbOccurence(coef * l.getNbOccurence());
        }
    }
}
