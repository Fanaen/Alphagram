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
public class Letter implements Comparable<Letter> {
    
    // -- Attributes --
    private char letter;
    private int nbOccurence;
    
    // -- Contructors --
    public Letter(char letter) {
        this.letter = letter;
        nbOccurence = 1;
    }
    
    // -- Methods --
    public String toStringExpanded() {
        String result = "";
        
        for (int i = 0; i < Math.abs(nbOccurence); i++) {
            result += letter;
        }
        
        return result;
    }
    
    public String toStringReduced() {
        if(nbOccurence == 0) return "";
        
        int nb = Math.abs(nbOccurence);        
        return String.valueOf(letter) + (nb == 1 ? "" : nb );
    }
    
    // -- Getters & Setters --

    public char getLetter() {
        return letter;
    }

    public int getNbOccurence() {
        return nbOccurence;
    }
    
    public void setNbOccurence(int value) {
        nbOccurence = value;
    }

    void increaseNbOccurence() {
        increaseNbOccurence(1);
    }

    void increaseNbOccurence(int value) {
        nbOccurence += value;
    }
    
    // -- Overrided methods --
    @Override
    public int compareTo(Letter l) {
        return this.letter >= l.letter ? 1 : -1;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Letter) {
            Letter letter = (Letter) obj;
            return letter.letter == this.letter;
        }
        return false;
    }
    
    
}
