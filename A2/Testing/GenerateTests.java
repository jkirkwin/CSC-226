import java.io.*;
import java.util.*;
public class GenerateTests
{
    private static final int MAX_VERTICES = 999;
    private static final String TEST_PATH = "Generated" + File.separator;

    /*
     * Randomly generates a batch of input/output test files
     * for connected, weighted graphs and the weight of their MST
     */
    public static void main(String [] args)
    {
        PrintStream readMeStream = null;
        try
        {
            int batch = getBatchNumber();
            String dirName = "batch" + padToSize(batch);

            // Create directory and README
            File dir = new File(TEST_PATH + dirName);
            assert !dir.exists();
            dir.mkdir();

            String dirPath = TEST_PATH + dirName + File.separator;
            File readMe = new File(dirPath + "README.txt");
            boolean readMeCreated = readMe.createNewFile();
            assert readMeCreated;

            readMeStream = new PrintStream(readMe);
            readMeStream.println("Test file batch " +batch+ " for assignment 2");
            readMeStream.println();
            readMeStream.println("Generating a batch of connected graphs on n vertices for n up to " +MAX_VERTICES);
            readMeStream.println();

            // generate the input/output file pairs
            File inputFile, outputFile;
            for(int n = 1; n <= MAX_VERTICES; n++)
            {
                String paddedNumVertices = padToSize(n);
                inputFile = new File(dirPath + "in" + paddedNumVertices + ".txt");
                outputFile = new File(dirPath + "out" + paddedNumVertices + ".txt");
                assert inputFile.createNewFile();
                assert outputFile.createNewFile();
                PrintStream inputPS = new PrintStream(inputFile);
                PrintStream outputPS = new PrintStream(outputFile);

                System.out.println("Creating graphwrapper on " + n + " vertices");
                GraphWrapper gw = new GraphWrapper(n);

                System.out.println("--");
                System.out.println("GW Stats");
                System.out.println("n = " + gw.n);
                System.out.println("m = " + gw.m);
                System.out.println("mstWeight = " + gw.mstWeight);
                System.out.println("--");

                inputPS.println(n);
                for(int i = 0; i < n; i++)
                {
                    for(int j = 0; j < n; j++)
                    {
                        inputPS.print(gw.adj[i][j] + " ");
                    }
                    inputPS.println();
                }

                outputPS.println(gw.mstWeight);

            }
            readMeStream.println("Tests generated");
        } catch (IOException ioe)
        {
            if(readMeStream != null)
            {
                ioe.printStackTrace();
                readMeStream.println("Error. IOException thrown.");
                readMeStream.println(ioe);
            }
            System.exit(1);
        } catch(Exception e)
        {
            if(readMeStream != null)
            {
                e.printStackTrace();
                readMeStream.println("Error. IOException thrown.");
                readMeStream.println(e);
            }
            System.exit(1);
        }
    }

    // pad to 3 digits
    private static String padToSize(int x)
    {
        String padded = "" + x;
        for(int i = 0; i < 2 - x/10; i++) {
            padded = "0" + padded;
        }
        return padded;
    }

    private static int getBatchNumber()
    {
        int batch = 0;
        String pathPrefix = TEST_PATH + "batch";
        File f = new File(pathPrefix + padToSize(batch));
        while(f.exists()) {
            batch++;
            f = new File(pathPrefix + padToSize(batch));
        }
        return batch;
    }


}