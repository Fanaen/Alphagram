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
package alphagram.helper;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Fanaen <contact@fanaen.fr>
 */
public class TextHelper {
    
    public static String removeDiacritics(String input) {
        return Normalizer.normalize(input, Form.NFD)
            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    } 

    public static String removeNonLetters(String input) {
        return input.replaceAll("[^a-zA-Z]", "");
    }
    
    public static String sortLetters(String input) {
        char[] ar = input.toCharArray();
        Arrays.sort(ar);
        return String.valueOf(ar);
    }

    public static String handleNumbers(String input) {
        Pattern p = Pattern.compile("([a-zA-Z])([0-9]+)");
        Matcher m = p.matcher(input);
        
        while(m.find()) {
            String letter = m.group(1);
            String sNumber = m.group(2);
            int number = Integer.parseInt(sNumber);
            
            input = input.replace(letter + sNumber, new String(new char[number]).replace("\0", letter));
        }
        
        return input;
    }
    
}
