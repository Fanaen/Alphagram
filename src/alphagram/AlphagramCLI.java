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

import alphagram.helper.WordListHelper;
import alphagram.model.Alphagram;
import alphagram.model.Anagram;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Fanaen <contact@fanaen.fr>
 */
public class AlphagramCLI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AlphagramCLI process = new AlphagramCLI();
        process.run();
    }
    
    // -- Attributes --
    
    private Map<String, Alphagram> variableMap = new HashMap<>();
    
    // -- Methods --
    
    private void run() {
        Scanner in = new Scanner(System.in, "UTF-8");
        String line = "";
        boolean pursue = true;
        
        // Display welcome message --
        System.out.println("-- Alphagram --");
        System.out.println("Type !q to exit and !h for help.");
        
        while(pursue) {
            System.out.print("> ");
            line = in.nextLine();
            
            // Commands --
            Pattern p = Pattern.compile("^!([a-z])(.*)$");
            Matcher m = p.matcher(line.trim());

            if(m.matches()) {
                String command = m.group(1), param = m.group(2).trim();
                switch(command) {
                    case "q":
                        pursue = false;
                        System.out.println("ahknsT for ginsu Aaaghlmpr. Bey!");
                        break;
                    case "d":
                        System.out.println(variableMap.remove(param) == null ? 
                                "# Variable not found." : "# Variable unset.");
                        break;
                    case "l":
                        for (Map.Entry entry : variableMap.entrySet()) {
                            System.out.print("# " + entry.getKey() + ": ");
                            display((Alphagram) entry.getValue());
                            System.out.println();
                        }
                        break;
                    case "g":
                        String[] params = param.trim().split(" ");
                        if(params.length == 2) {
                            WordListHelper.generateIndex(params[0], params[1]);
                        }
                        break;
                    case "s":
                        String index = param.split(" ")[0];
                        String alpha = param.substring(param.indexOf(index) + index.length());
                        WordListHelper.searchInIndex(index, processOperation(alpha));
                        break;
                    case "h":
                        System.out.println("# Help:"
                                + "\n# \"a - b\" to substract alphagrams"
                                + "\n# \"key = value\" to set a variable."
                                + "\n# \"$key\" to use it"
                                + "\n# \"!d\" to unset a variable"
                                + "\n# \"!l\" list variables."
                                + "\n# \"!d\" to unset a variable"
                                + "\n# \"!g <source> <index>\" to generate a alphagram index from hunspell dictionaries"
                                + "\n# \"!s <index> <alphagram>\" to search possibilities in an alphagram index "
                                + "\n# \"!q\" to exit the program.");
                        break;
                }
            }
            // Standard line --
            else {
                processOperation(line);
            }
        }
    }
    
    private Alphagram processOperation(String line) {
        
        Pattern p = Pattern.compile("^([a-zA-Z0-9]+) *= *(.+)$");
        Matcher m = p.matcher(line.trim());
        Alphagram alphagram;
        
        if(m.matches()) {
            String key = m.group(1);
            System.out.print("# $" + key + ": ");
            
            alphagram = processLine(m.group(2));
            variableMap.put(key, alphagram);            
        }
        else {
            System.out.print("# ");
            alphagram = processLine(line); 
        }
        
        return alphagram;
    }
    
    private String insertVariables(String line) {
        Pattern p = Pattern.compile("\\$([a-zA-Z0-9]+)");
        Matcher m = p.matcher(line);
        
        while(m.find()) {
            String key = m.group(1);
            Alphagram alpha = variableMap.get(key);
            
            if(alpha != null) {
                line = line.replaceAll("\\$"+ key, alpha.getRaw());
            }
        }
        
        return line;
    }
   
    private Alphagram processLine(String line) {
        line = insertVariables(line);
        String[] words = line.split(" ");
        boolean add = true;
        
        // Multiple words --
        if(words.length > 1) {
            int i = 0;
            Alphagram alphagram = new Alphagram("");
                     
            for (String word : words) {   
                // Dismiss empty words --
                if(word.trim().equals("")) continue;              
                
                // Handle operators --
                if(word.trim().equals("+") || word.trim().equals("-")) {
                    add = word.equals("+");
                    continue;
                }
                
                i++;  
                
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
            return alphagram;
        }
        // Single words --
        else {
            Alphagram alphagram = processWord(line);
            System.out.println();
            return alphagram;
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
