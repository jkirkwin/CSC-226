import java.util.*;
import java.io.*;

public class RunTests {
    public static void main(String[] args) {
        File testDir = new File(GenerateTests.testPath);
        for(File f : testDir.listFiles()) {
            runTest(f);
        }

    }

    private static void runTest(File f) {
        try {
            Scanner sc = new Scanner(f);
            String pattern = sc.next();
            String text = sc.next();
            int oracle = text.indexOf(pattern);
            KMP kmp = new KMP(pattern);
            int result = kmp.search(text);

            if(oracle == result) {
                System.out.print("Pass.");
            } else {
                System.out.println("Fail.");
                System.out.println("Oracle: " + oracle);
                System.out.println("Result: " + result);
                System.out.println("File: " + f.getPath());
                System.out.println("\n");
            }

        } catch(Exception e) {
            System.out.println("Error.");
            e.printStackTrace();
        }
    }
}