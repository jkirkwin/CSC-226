import java.util.*;
import java.io.*;
public class GenerateTests {

    public static final String testPath = "Testing" + File.separator;

    public static void main(String[] args) {
        int numTests = Integer.parseInt(args[0]);
        int textLen = Integer.parseInt(args[1]);
        int patLen = Integer.parseInt(args[2]);
        if(patLen >= textLen) {
            System.out.println("Pattern length cannot exceed text length");
            return;
        } else if(numTests <= 0) {
            System.out.println("Number of tests must be positive");
            return;
        }

        Random r = new Random();
        long suffix = getSuffix();
        for(long i = 0; i < numTests; i++) {
            makeTest(r, suffix + i, textLen, patLen);
        }

        System.out.println("Done making tests.");
    }

    private static long getSuffix() {
        long suffix = 0;
        while(new File(testPath + "test" + suffix + ".txt").exists()) {
            suffix++;
        }
        return suffix;
    }

    private static void makeTest(Random r, long suffix, int textLen, int patLen) {
        PrintStream ps = null;
        try {
            File testFile = new File(testPath + "test" + suffix + ".txt");
            ps = new PrintStream(testFile);
            StringBuilder patBuilder = new StringBuilder(), textBuilder = new StringBuilder();
            for(int i = 0; i < textLen; i++) {
                if(i< patLen) {
                    patBuilder.append(getChar(r.nextInt(4)));
                }
                textBuilder.append(getChar(r.nextInt(4)));
            }
            ps.println(patBuilder.toString());
            ps.println(textBuilder.toString());
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(ps != null) ps.close();
        }
    }

    private static char getChar(int i) {
        switch(i) {
            case 0:
                return 'A';
            case 1:
                return 'C';
            case 2:
                return 'G';
            case 3:
                return 'T';
            default:
                System.out.println("Error. Invalid int passed.");
                return 'X';
        }
    }
}