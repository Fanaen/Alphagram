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

import alphagram.model.Alphagram;
import fr.fanaen.wordlist.WordListGenerator;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Fanaen <contact@fanaen.fr>
 */
public class WordListHelper {
    
    public static void generateIndex(String source, final String index) {
        try {
            IndexGenerator listener = new IndexGenerator(index);
            WordListGenerator generator = new WordListGenerator("data/" + source, listener);
            generator.readFile();
            generator.displayStatistics();
            System.out.println(" * " + listener.getCount() + " alphagrams");
        } catch (Exception ex) {
            Logger.getLogger(WordListHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void searchInIndex(String index, Alphagram alpha) {        
        // Get some info converted --
        File file = new File("index/" + index);
        Charset charset = Charset.forName("UTF-8");
        
        // Check file --
        if(!file.exists()) {
            System.err.println("# This index does not exist.");
            return;
        }
        
        // Counter --
        int wordCount = 0;
        int alphaCount = 0;
        
        // Read the file --
        try(BufferedReader br = Files.newBufferedReader(file.toPath(), charset)) {
            for(String line; (line = br.readLine()) != null; ) {                
                if(alpha.contains(line.split(":")[0])) {
                    String[] info = line.split(":");
                    String[] words = line.substring(info[0].length() + 1).split(";");
                    
                    System.out.print(info[0] + ": ");
                    for (int i = 0; i < words.length; i++) {
                         System.out.print(words[i]);
                    }
                    
                    System.out.println();
                    wordCount += words.length;
                    alphaCount++;
                    
                }       
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("# "+ wordCount + " words in " + alphaCount + " alphagrams.");
    }
    
}
