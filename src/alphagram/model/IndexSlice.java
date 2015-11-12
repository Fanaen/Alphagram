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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Fanaen <contact@fanaen.fr>
 */
public class IndexSlice {
    
    // -- Attributes --
    protected int wordCount = 0;
    protected int alphaCount = 0;
    
    protected List<IndexLine> lineList;
    protected Alphagram referent;
    
    
    // -- Constructors --
    
    public IndexSlice(Alphagram alphagram) {
        lineList = new LinkedList<>();
        referent = alphagram;
    }
    
    // -- Methods --

    public void addLine(String line) {
        String alpha = line.split(":")[0];
        String[] words = line.substring(alpha.length() + 1).split("; ");
        
        lineList.add(new IndexLine(alpha, words, this));
        
        alphaCount++;
        wordCount += words.length;
    }
    
    public void displayStatistics() {
        System.out.println("# "+ wordCount + " words in " + alphaCount + " alphagrams.");
    }
    
    public void displayContent() {
        for (IndexLine indexLine : lineList) {
            indexLine.display();
        }
    }
    
    public void keep(int limit, boolean upper) {
        for (Iterator<IndexLine> iterator = lineList.iterator(); iterator.hasNext();) {
            IndexLine next = iterator.next();
            
            if(next.getRatio()*100 < limit && upper) {
                // System.out.println("Remove lesser");
                iterator.remove();
            } 
            else if(next.getRatio()*100 > limit && !upper) {
                // System.out.println("Remove upper");
                iterator.remove();
            }
        }
    }
    
    public void sort() {
        Collections.sort(lineList);
    }
    
    // -- Getters and setters --

    public Alphagram getReferentAlphagram() {
        return referent;
    }
    
}
