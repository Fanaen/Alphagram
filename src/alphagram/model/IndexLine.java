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

/**
 *
 * @author Fanaen <contact@fanaen.fr>
 */
public class IndexLine implements Comparable<IndexLine>{
    
    // -- Attributes --
    protected Alphagram alphagram;
    protected String[] wordArray;
    protected IndexSlice parent;
    
    protected float ratio; // Nb of letters compared to the referent alphagram
    
    // -- Constructors --
    
    public IndexLine(String alpha, String[] words, IndexSlice slice) {
        alphagram = new Alphagram(alpha);
        wordArray = words;
        parent = slice;
        updateRatio();
    }
    
    // -- Methods --
    
    private void updateRatio() {
        ratio = (float) alphagram.getRaw().length() / (float) parent.getReferentAlphagram().getRaw().length();
    }
    
    @Override
    public int compareTo(IndexLine o) {
        return o.getRatio() < ratio ? 1 : -1;
    }
    
    // -- Getters and setters --

    void display() {
        
        // Display ratio --
        if(ratio == 1) 
            System.out.print("**");
        else
            System.out.format("%02d", (int) Math.round(ratio * 100));
        
        // Display content --
        System.out.print("% " + alphagram.getRaw() + ": ");
        for (int i = 0; i < wordArray.length; i++) {
            System.out.print((i == 0 ? "" : ", ")+ wordArray[i]);
        }
        System.out.println();
    }

    public Alphagram getAlphagram() {
        return alphagram;
    }

    public String[] getWordArray() {
        return wordArray;
    }

    public float getRatio() {
        return ratio;
    }
}
