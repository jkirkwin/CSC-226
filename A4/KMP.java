/* 
   (originally from R.I. Nishat - 08/02/2014)
   (revised by N. Mehta - 11/7/2018)
   (completed by J. Kirkwin - 11/24/2018)
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class KMP {
    private final String pattern;
    private final String alphabet = "ACGT";
    private final int[][] DFA;

    public KMP(String pattern){
		assert pattern != null;
		this.pattern = pattern;
        DFA = new int[this.alphabet.length()][this.pattern.length()];
        DFA[getMapping(pattern.charAt(0))][0] = 1;
        for(int i = 0, j = 1; j < this.pattern.length(); j++) {
            for(int k = 0; k < this.alphabet.length(); k++) {
                DFA[k][j] = DFA[k][i];
            }
            DFA[getMapping(this.pattern.charAt(j))][j] = j+1;
            i = DFA[getMapping(this.pattern.charAt(j))][i];
        }
    }
    
    public int search(String txt){
        int state = 0, position = 0;
        while(state < this.pattern.length() && position < txt.length()) {
            char nextChar = txt.charAt(position++);
            int row = getMapping(nextChar);
            int col = state;
            state = DFA[row][col];
        }

        if(state >= this.pattern.length()) {
            return position - this.pattern.length();
        } else {
            return position; // Pattern not found
        }
    }

    private int getMapping(char c) {
        c = Character.toUpperCase(c);
        return alphabet.indexOf(c);
    }
    
    public static void main(String[] args) throws FileNotFoundException{
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.println("Unable to open "+args[0]+ ".");
				return;
			}
			System.out.println("Opened file "+args[0] + ".");
			String text = "";
			while(s.hasNext()){
				text += s.next() + " ";
			}

			for(int i = 1; i < args.length; i++){
				KMP k = new KMP(args[i]);
				int index = k.search(text);
				if(index >= text.length()){
					System.out.println(args[i] + " was not found.");
				}
				else {
					System.out.println("The string \"" + args[i] + "\" was found at index " + index + ".");
				}
			}
		} else{
			System.out.println("usage: java SubstringSearch <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
		}
    }
}
