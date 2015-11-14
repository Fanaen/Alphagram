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
package fr.fanaen.alphagram.helper;

import fr.fanaen.alphagram.index.IndexGenerator;
import fr.fanaen.alphagram.model.Alphagram;
import fr.fanaen.alphagram.index.IndexSlice;
import fr.fanaen.wordlist.WordListGenerator;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Fanaen <contact@fanaen.fr>
 */
public class WordListHelper {
    
    // -- Generation methods --
    
    public static void generateIndex(String source, final String index) {
        // Read the given dictionary (source) and write a file with an alphagram list with corresponding words --
        try {
            IndexGenerator listener = new IndexGenerator(index);
            WordListGenerator generator = new WordListGenerator("data/" + source, listener);
            
            generator.readFile();
            generator.displayStatistics();
            
            System.out.println(" * " + listener.getCount() + " alphagrams");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // -- Search methods --

    public static void searchInIndex(String index, Alphagram alpha) {
        String[] params = index.split("/");
        IndexSlice slice = generateSlice(params[0], alpha);
        handleSearchParams(params, slice);        
        
        slice.sort();
        slice.displayContent();
        slice.displayStatistics();
    }
    
    public static void handleSearchParams(String[] params, IndexSlice slice) {
        // Handle ratio filter "50+" --
        for (int i = 1; i < params.length; i++) {
            String param = params[i];
            
            Pattern p = Pattern.compile("([0-9]{1,2})([\\+-])?");
            Matcher m = p.matcher(params[i].trim());
            
            if(m.find()) {
                boolean upper = true;
                int limit = 0;

                // Handle "+" and "-" --
                if(m.group(2) != null) {
                    upper = m.group(2).contains("+");
                }

                // Handle "50" in "50+" --
                limit = Integer.parseInt(m.group(1));

                slice.keep(limit, upper);
            }
        }
    }
    
    // Generate a small part of the index as an object --
    public static IndexSlice generateSlice(String index, Alphagram alpha) {
        // Create the index slice --
        IndexSlice slice = new IndexSlice(alpha);
                
        // Get some info converted --
        File file = new File("index/" + index);
        Charset charset = Charset.forName("UTF-8");
        
        // Check file --
        if(!file.exists()) {
            System.err.println("# This index does not exist.");
            return null;
        }
        
        // Read the file --
        try(BufferedReader br = Files.newBufferedReader(file.toPath(), charset)) {
            for(String line; (line = br.readLine()) != null; ) {                
                if(alpha.contains(line.split(":")[0])) {
                    slice.addLine(line);                    
                }       
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return slice;
    }
    
    // -- Combinations methods (require a generated index) --
    
    public static void combineFromSearchInIndex(String index, Alphagram alpha) {
        String[] params = index.split("/");
        IndexSlice slice = generateSlice(params[0], alpha);
        
        slice.combineToFile("gen/" + index + ".comb." + alpha.getShort());
        
        slice.displayStatisticsCombine();
    }
    
}
