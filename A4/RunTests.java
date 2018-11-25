import java.util.*;
import java.io.*;

public class RunTests {

    private static int passCount = 0;
    private static int testCount = 0;

    public static void main(String[] args) {
        File testDir = new File(GenerateTests.testPath);
        for(File f : testDir.listFiles()) {
//            System.out.println("Running " + f.getPath());
            runTest(f);
        }
        System.out.println("Passed " + passCount + " of " + testCount + " tests");
    }

    private static void runTest(File f) {
        try {
            Scanner sc = new Scanner(f);
            String pattern = sc.next();
            String text = sc.next();
            int index = text.indexOf(pattern);
            int oracle = index == -1 ? text.length() : index;
            KMP kmp = new KMP(pattern);
            int result = kmp.search(text);

            if(oracle == result) {
                System.out.println("Pass test " + (testCount + 1));
                passCount++;
            } else {
                System.out.println("Fail test " + (testCount + 1));
                System.out.println("Oracle: " + oracle);
                System.out.println("Result: " + result);
                System.out.println("File: " + f.getPath());
                System.out.println("\n");
            }
            testCount++;
        } catch(Exception e) {
            System.out.println("Error.");
            e.printStackTrace();
        }
    }
}