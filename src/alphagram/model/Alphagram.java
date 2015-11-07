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

    
    // -- Getters & Setters --
    
    public String getRaw() {
        return rawAlphagram;
    }
    
    public String getShort() {
        String result = "";
        
        for (Letter l : letterOccurenceList) {
            result += l.toStringReduced();
        }
        
        return result;
    }
}
