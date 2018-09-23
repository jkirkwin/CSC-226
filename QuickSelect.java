/**
 *
 * @author Rahnuma Islam Nishat
 * September 19, 2018
 * CSC 226 - Fall 2018
 */
import java.util.*;
import java.io.*;

// Testing intellij commit

public class QuickSelect {


    public static int QuickSelect(int[] A, int k) {
        //Write your code here

        return -1;
    }

    public static void main(String[] args) {
        Scanner s;
        int[] array;
        int k;
        if(args.length > 0) {
            try{
                s = new Scanner(new File(args[0]));
                int n = s.nextInt();
                array = new int[n];
                for(int i = 0; i < n; i++){
                    array[i] = s.nextInt();
                }
            } catch(java.io.FileNotFoundException e) {
                System.out.printf("Unable to open %s\n",args[0]);
                return;
            }
            System.out.printf("Reading input values from %s.\n", args[0]);
        }
        else {
            s = new Scanner(System.in);
            System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
            int temp = s.nextInt();
            ArrayList<Integer> a = new ArrayList();
            while(temp >= 0) {
                a.add(temp);
                temp=s.nextInt();
            }
            array = new int[a.size()];
            for(int i = 0; i < a.size(); i++) {
                array[i]=a.get(i);
            }

            System.out.println("Enter k");
        }
        k = s.nextInt();
        System.out.println("The " + k + "th smallest number is the list is "
                + QuickSelect(array,k));
    }
}