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

import alphagram.model.Anagram;
import java.util.Scanner;

/**
 *
 * @author Fanaen <contact@fanaen.fr>
 */
public class Alphagram {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in, "UTF-8");
        int choice = 0;
        
        // Display welcome message --
        System.out.println("-- Alphagram --");
        System.out.println("  1. Register an anagram");
        System.out.println("  0. Quit");
        
        do {
            System.out.print("Your choice:");
            choice = in.nextInt();
            
            switch(choice) {
                case 0:
                    System.out.println("Bye!");
                    break;
                case 1:
                    register();
                    break;
            }
            
        } while(choice != 0);
    }

    private static void register() {
        Scanner in = new Scanner(System.in, "UTF-8");
        Anagram anagram = null;
        
        System.out.print("Anagram:");
        anagram = new Anagram(in.nextLine());
        System.out.println(anagram.alphagram().getRaw() + " <" + anagram.alphagram().getShort() + ">");
    }
    
}
