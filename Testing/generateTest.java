import java.util.*;
import java.io.*;

/*
 * To be used to generate vanilla and large test files for this assignment.
 */
public class generateTest {
    public static void main(String[] args) {
        try {
            if(!args[0].equals("vanilla") && !args[0].equals("large")) {
                System.out.println("invalid test type specified: " + args[0]);
                System.exit(1);
            }

            File inputFile = new File(args[0] + "In0" + Integer.parseInt(args[1]) + ".txt");
            File outputFile = new File(args[0] + "Out0" + Integer.parseInt(args[1]) + ".txt");
            PrintStream pInputFile = new PrintStream(inputFile);
            PrintStream pOutputFile = new PrintStream(outputFile);

            Random r = new Random();
            boolean isVanilla = args[0].equals("vanilla");
            int bound = isVanilla ? 50 : 1000;
            int range = isVanilla ? 100 : 10000;
            int n = r.nextInt(bound) + 7;

            pInputFile.println(n);

            int [] values = new int[n];
            for(int i = 0; i < n; i++) {
                int tmp = r.nextInt(range);
                while(duplicateInput(values, i, tmp)) {
                    tmp = r.nextInt(range);
                }
                pInputFile.print(tmp + " ");
                values[i] = tmp;
            }
            pInputFile.println();

            int k = r.nextInt(n) + 1;
            pInputFile.print(k);

            pOutputFile.print(kSelect(values, k));

        } catch(Exception e) {
            System.out.println("Exception thrown when making files/printstreams");
        }
    }

    private static boolean duplicateInput(int[] values, int position, int input) {
        for(int i = 0; i < position; i++) {
            if(values[i] == input) return true;
        }
        return false;
    }

    private static int kSelect(int [] values, int k) {
        Arrays.sort(values);
        return values[k-1];
    }
}