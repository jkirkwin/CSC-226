/* 
   (originally from R.I. Nishat - 08/02/2014)
   (revised by N. Mehta - 11/7/2018)
   (completed by J. Kirkwin - 11/24/2018)
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class KMP{
    private final String pattern;

    private final int[][] DFA;

    public KMP(String pattern){
		assert patter != null;
		this.pattern = pattern;

		// TODO create DFA


    }
    
    public int search(String txt){
		// TODO Implement search
		/* Should return the index of the first occurrence of the pattern
		 * if it exists. Return length of text otherwise.
		 */
		return -1;
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
