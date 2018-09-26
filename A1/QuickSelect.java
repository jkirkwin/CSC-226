/*
 * Jamie Kirkwin
 * V00875987
 * CSC 226 Assignemtn 1
 * 2018/09/25
 */
import java.util.*;
import java.io.*;

public class QuickSelect {
    /**
     * -1 is returned in the case of invalid input. See expected criteria below:
     * A != null
     * All values in A are distinct
     * 1 <= k <= A.length

     * @param A
     * @param k
     * @return the kth lowest element in A
     */
    public static int QuickSelect(int[] A, int k) {
        if(A == null || k < 1 || A.length < k || hasDuplicates(A)) {
            return -1;
        }
        return doQuickSelect(A, k);
    }

    private static int doQuickSelect(int [] A, int k) {
        int n = A.length;
        if(n <= 7) {
            return bruteForceSelect(A, k);
        }
        int numGroups = n / 7;
        if(n%7 != 0) numGroups++;
        int[] medians = new int[numGroups];
        for(int i = 0; i < numGroups; i++) {
            int position = i * 7;
            int groupLen = n-position <= 7 ? n-position : 7;
            medians[i] = bruteForceSelect(Arrays.copyOfRange(A, position, position + groupLen), (groupLen+1)/ 2);
        }
        int pivot = QuickSelect(medians, numGroups / 2);
        int[] less = new int[n-1], greater = new int[n-1];
        int l = 0, g = 0;
        for(int a : A) {
            if(a < pivot) {
                less[l] = a;
                l++;
            } else if(a > pivot) {
                greater[g] = a;
                g++;
            }
        }
        less = Arrays.copyOfRange(less, 0, l);
        greater = Arrays.copyOfRange(greater, 0, g);
        if(less.length >= k) {
            return QuickSelect(less, k);
        } else if(k == less.length + 1) {
            return pivot;
        } else {
            return QuickSelect(greater, k - less.length - 1);
        }
    }

    /*
     * Returns the median if A has an odd number of elements, or the
     * smaller of the middle pair if A has an even number of elements
     *
     * Only to be called with 1 <= A.length <= 7
     *
     * Sorts the array in the process!
     */
    private static int bruteForceSelect(int [] A, int k) {
        if(A == null) {
            return -1;
        }
        Arrays.sort(A);
        return A[k-1];
    }

    /**
     * All tests run from here check for correctness only, they do not check
     * that the implementation is O(n).
     */
    private static void runTests() {
        doTest(3, "duplicateValue");
        doTest(2, "invalidK");
        doTest(9, "vanilla");
        doTest(7, "small");
        doTest(9, "large");
    }

    /**
     * @pre A != null
     * @param A array
     * @return true iff A contains duplicates
     */
    private static boolean hasDuplicates(int [] A) {
        int [] copy = Arrays.copyOf(A, A.length);
        assert copy.length == A.length;
        Arrays.sort(copy);
        for(int i = 0; i < copy.length - 1; i++) {
            if(copy[i] == copy[i+1]) {
                return true;
            }
        }
        return false;
    }

    private static void doTest(int tests, String identifier) {
        try {
            System.out.println("==================");
            System.out.println(identifier + "Tests");
            Scanner[] inputScanners = new Scanner[tests + 1];
            Scanner resultScanner;
            int[] expectedResults = new int[tests + 1], results = new int[tests + 1];
            for(int i = 1; i <= tests; i++) {
                inputScanners[i] = new Scanner(new File("Testing\\" + identifier +"In0"+ i +".txt"));
                resultScanner = new Scanner(new File ("Testing\\" + identifier +"Out0"+ i +".txt"));
                expectedResults[i] = resultScanner.nextInt();
            }

            int n, k;
            int [] A;
            for(int i = 1; i <= tests; i++) {
                n = inputScanners[i].nextInt();
                A = new int[n];
                for(int j = 0; j < n; j++){
                    A[j] = inputScanners[i].nextInt();
                }
                k = inputScanners[i].nextInt();
                results[i] = QuickSelect(A, k);
                System.out.print("test 0" + i + ':');
                if(expectedResults[i] == results[i]) {
                    System.out.println("Passed");
                } else {
                    System.out.println("Failed.\n\tExpected Result: " + expectedResults[i]
                            + "\n\tActual Result: " + results[i]);
                }
            }
            System.out.println("");
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException thrown.");
            System.out.println(e);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception thrown.");
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
         runTests();
    }
}