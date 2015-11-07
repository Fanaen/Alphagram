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
package alphagram;

import alphagram.model.Alphagram;
import alphagram.model.Anagram;
import java.util.Scanner;

/**
 *
 * @author Fanaen <contact@fanaen.fr>
 */
public class AlphagramCLI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in, "UTF-8");
        String line = "";
        boolean pursue = true;
        
        // Display welcome message --
        System.out.println("-- Alphagram --");
        System.out.println("Type \"quit\" to exit.");
        
        while(pursue) {
            System.out.print("> ");
            line = in.nextLine();
            
            // Quit condition --
            if(line.toLowerCase().equals("quit")) {
                pursue = false;
                System.out.println("ahknsT for ginsu Aaaghlmpr. Bey!");
            }
            // Standard line --
            else {
                processLine(line);
            }
        }
    }
   
    private static void processLine(String line) {
        String[] words = line.split(" ");
        System.out.print("# ");
        boolean add = true;
        
        // Multiple words --
        if(words.length > 1) {
            int i = 0;
            Alphagram alphagram = new Alphagram("");
                     
            for (String word : words) {   
                i++;
                
                // Handle operators --
                if(word.trim().equals("+") || word.trim().equals("-")) {
                    add = word.equals("+");
                    continue;
                }
                
                if(add) {
                    // Add the word to the result --
                    if(i>1) System.out.print(" + ");
                    alphagram.apply(processWord(word), +1);
                    
                }
                else {
                    // Withdraw the word to the result --
                    if(i>1) System.out.print(" - ");
                    alphagram.apply(processWord(word), -1);
                }
            }
                
            System.out.print(" = ");
            display(alphagram);
            System.out.println();
        }
        // Single words --
        else {
            processWord(line);
            System.out.println();
        }
        
    }
    
    private static Alphagram processWord(String word) {
        Anagram anagram = new Anagram(word);
        Alphagram alphagram = anagram.alphagram();
        display(alphagram);
        return alphagram;
    }
    
    private static void display(Alphagram alphagram) {
        System.out.print(alphagram.getRaw() + " (" + alphagram.getShort() + ")");
    }
}
