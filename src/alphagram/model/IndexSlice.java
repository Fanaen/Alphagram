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

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
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
    protected int combinationCount = 0;
    
    protected LinkedList<IndexLine> lineList;
    protected LinkedList<LinkedList<IndexLine>> combineList;
    protected Alphagram referent;
    
    
    // -- Constructors --
    
    public IndexSlice(Alphagram alphagram) {
        lineList = new LinkedList<>();
        referent = alphagram;
    }
    
    // -- Methods --
    
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
    
    public void reverse() {
        Collections.reverse(lineList);
    }
    
    public void combineToFile(String string) {
        
        File file = new File(string);        
        try (PrintStream stream = new PrintStream(file)) {
            
            combine(new IndexCombinatorListener() {
                @Override
                public void onNewCombination(LinkedList<IndexLine> combination) {
                    IndexSlice.displayCombination(combination, stream);
                    combinationCount++;
                }
            });
            stream.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void combine() {
        combine(new IndexCombinatorListener() {
            @Override
            public void onNewCombination(LinkedList<IndexLine> combination) {
                combineList.add(combination);
                combinationCount++;
            }
        });
    }
    
    // -- Intern processing methods --
    
    private void combine(IndexCombinatorListener listener) {
        // Prepare the list --
        sort();
        reverse();
        combineList = new LinkedList<>();
        combinationCount = 0;
        
        LinkedList<IndexLine> combinationBeginning = new LinkedList<>();
        combine((LinkedList<IndexLine>) lineList.clone(), combinationBeginning, listener, referent);
    }
    
    private void combine(LinkedList<IndexLine> remainingList, LinkedList<IndexLine> combinationList, IndexCombinatorListener listener, Alphagram alphagram) {
        // Parse the list --
        for (Iterator<IndexLine> iterator = remainingList.iterator(); iterator.hasNext();) {
            IndexLine next = iterator.next();
            
            // Add the current item to the combination --
            LinkedList<IndexLine> newCombinationList = (LinkedList<IndexLine>) combinationList.clone();
            newCombinationList.add(next);
            
            // If the referent alphagram contains the current item, next iteration --
            if(alphagram.contains(next.getAlphagram())) {
                
                // Remove the letters of the current alphagram from the referent --
                Alphagram newAlphagram = new Alphagram(alphagram);
                newAlphagram.apply(next.getAlphagram(), -1);

                // Next step --
                if(newAlphagram.isEmpty()) {
                    listener.onNewCombination(newCombinationList);
                }
                else {
                    combine((LinkedList<IndexLine>) remainingList.clone(), newCombinationList, listener, newAlphagram);
                }
            }
            // If not, the combination is finished --
            else {
                // TODO Or not ?
                //listener.onNewCombination(combinationList);
            }                
            
            iterator.remove();            
        }
    }
    
    
    // -- Getters and setters --

    public Alphagram getReferentAlphagram() {
        return referent;
    }

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
    
    public void displayStatisticsCombine() {
        System.out.println("# "+ wordCount + " words in " + alphaCount + " alphagrams for "+ combinationCount + " combinations.");
    }
    
    public void displayContent() {
        for (IndexLine indexLine : lineList) {
            indexLine.display(System.out);
            System.out.println();
        }
    }
    
    public void displayAllCombinations() {
        for (List<IndexLine> combination : combineList) {
            displayCombination(combination, System.out);
        }
    }
    
    public static void displayCombination(List<IndexLine> combination, PrintStream stream) {        
        int i = 0;
        for (IndexLine indexLine : combination) {
            if(i!= 0) stream.print("\n + ");
            indexLine.display(stream);
            i++;
        }
        stream.println();
    }
}
