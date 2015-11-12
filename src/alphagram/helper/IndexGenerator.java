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
import fr.fanaen.wordlist.WordListGeneratorListener;
import fr.fanaen.wordlist.model.Word;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Fanaen <contact@fanaen.fr>
 */
public class IndexGenerator implements WordListGeneratorListener {
    
    // -- Attributes --
    
    protected String outputPath;
    protected Map<String, Set<String>> storage;
    
    // -- Constructors --
    
    public IndexGenerator(String indexName) {
        outputPath = "index/" + indexName;
        storage = new HashMap<>();
    }

    // -- Override Methods --
            
    @Override
    public void onNewWord(Word newWord) {
        // Skip words with numbers --
        String word = newWord.getContent();
        if(!word.matches("[^0-9]+")) 
            return;
        
        // Prepare the alphagram --
        Alphagram alpha = new Alphagram(word);
        String key = alpha.getRaw();
        
        // Skip one letter words --
        if(alpha.getRaw().length() == 1)
            return;
        
        // Add the alphagram --
        if(storage.containsKey(key)) {
            storage.get(key).add(newWord.getContent());
        }
        else {
            HashSet<String> newSet = new HashSet<>();
            newSet.add(newWord.getContent());
            storage.put(key, newSet);
        }
    }

    @Override
    public void onGenerationEnd() {
        try {
            // Write the index --
            PrintWriter writer = new PrintWriter(outputPath, "UTF-8");
            
            for (Entry<String, Set<String>> entry : storage.entrySet()) {
                writer.print(entry.getKey() + ":");
                
                int i = 0;
                for (String word : entry.getValue()) {
                    writer.print((i == 0 ? "":"; ") + word);
                    i++;
                }
                
                writer.println();
            }
            
            writer.close();       
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }
    
    // -- Getters & Setters --
     
    public int getCount() {
        return storage.size();
    }
    
}
