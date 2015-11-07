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
        System.out.println("Type \"quit\" to exit.\n");
        
        while(pursue) {
            System.out.print("> ");
            line = in.nextLine();
            
            // Quit condition --
            if(line.toLowerCase().equals("quit")) {
                pursue = false;
                System.out.println("iQtu. Bey!");
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
        
        // Multiple words --
        if(words.length > 1) {
            int i = 0;
                     
            for (String word : words) {            
                // Anagram --
                Anagram anagram = new Anagram(word);
                Alphagram alphagram = anagram.alphagram();
                
                System.out.print(alphagram.getRaw() + " (" + alphagram.getShort() + ")");
                
                // Separators --
                i++;
                System.out.print(i == words.length ? " = " : " + ");
            }
        }
        
        Anagram anagram = new Anagram(line);
        Alphagram alphagram = anagram.alphagram();

        System.out.println(alphagram.getRaw() + " (" + alphagram.getShort() + ")");
    }
}
