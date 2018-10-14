/* MST.java
   CSC 226 - Fall 2018
   Problem Set 2 - Template for Minimum Spanning Tree algorithm

   The assignment is to implement the mst() method below, using Kruskal's algorithm
   equipped with the Weighted Quick-Union version of Union-Find. The mst() method computes
   a minimum spanning tree of the provided graph and returns the total weight
   of the tree. To receive full marks, the implementation must run in time O(m log m)
   on a graph with n vertices and m edges.

   This template includes some testing code to help verify the implementation.
   Input graphs can be provided with standard input or read from a file.

   To provide test inputs with standard input, run the program with
       java MST
   To terminate the input, use Ctrl-D (which signals EOF).

   To read test inputs from a file (e.g. graphs.txt), run the program with
       java MST graphs.txt

   The input format for both methods is the same. Input consists
   of a series of graphs in the following format:

       <number of vertices>
       <adjacency matrix row 1>
       ...
       <adjacency matrix row n>

   For example, a path on 3 vertices where one edge has weight 1 and the other
   edge has weight 2 would be represented by the following

   3
   0 1 0
   1 0 2
   0 2 0

   An input file can contain an unlimited number of graphs; each will be processed separately.

   NOTE: For the purpose of marking, we consider the runtime (time complexity)
         of your implementation to be based only on the work done starting from
	 the mst() method. That is, do not not be concerned with the fact that
	 the current main method reads in a file that encodes graphs via an
	 adjacency matrix (which takes time O(n^2) for a graph of n vertices).

   (originally from B. Bird - 03/11/2012)
   (revised by N. Mehta - 10/9/2018)
*/

import java.util.Scanner;
import java.io.File;
import java.util.LinkedList;
import java.util.Iterator;

import java.util.ArrayList;

public class MST {


    /* mst(adj)
       Given an adjacency matrix adj for an undirected, weighted graph, return the total weight
       of all edges in a minimum spanning tree.

       The number of vertices is adj.length
       For vertex i:
         adj[i].length is the number of edges
         adj[i][j] is an int[2] that stores the j'th edge for vertex i, where:
           the edge has endpoints i and adj[i][j][0]
           the edge weight is adj[i][j][1] and assumed to be a positive integer
    */
    static int mst(int[][][] adj) {
        int n = adj.length;
        int totalWeight = 0;

        // Set up UF -> O(n) time
        int [] id = new int[n];
        int [] componentSize = new int [n];
        for(int i = 0; i < id.length; i++) {
            id[i] = i;
            componentSize[i] = 0;
        }

        // Build sorted list of edges -> O(2m) = O(m)
        ArrayList<int[]> edgeList = buildEdgeList(adj);

        // Main loop -> O(m x log*(n))
        int m = 0;
        for(int [] edge : edgeList) {
            if(!(connected(edge[0], edge[1], id))) {
                union(edge[0], edge[1], componentSize, id);
                totalWeight += edge[2];
                m++;
            }

            if(m >= n-1) break;
        }
        return totalWeight;
    }

    /*
     * Builds and returns a sorted ArrayList containing each edge in ascending order by
     * weight.
     * Edges are represented as 3-element int arrays {vertex 1, vertex 2, edgeWeight).
     * @pre adj is not null and well formed
     * @post return is not null
     */
    private static ArrayList<int []> buildEdgeList(int[][][] adj) {
        ArrayList<int[]> edgeList = new ArrayList<int[]>();
        int weight, v;
        int [] edge;
        for(int i = 0; i < adj.length; i++) {
            for(int j = 0; j < adj[i].length; j++) {
                v = adj[i][j][0];
                weight = adj[i][j][1];

                // weight == 0 -> we've looked at all edges of i
                // v < i -> we've already added this edge
                if(weight == 0) {
                    break;
                } else if(v < i) {
                    continue;
                }

                edge = new int[3];
                edge[0] = i;
                edge[1] = v;
                edge[2] = weight;
                edgeList.add(edge);
            }
        }
        edgeList.sort( (int [] a, int [] b) -> (a[2] - b[2]) );
        return edgeList;
    }

    /*
     * preforms UF union operation as defined for weighted quick union
     * @pre !connected(u, v) && id != null && size != null
     * @param u, v the two vertices to union
     * @param size an array tracking the size of each component
     * @param id the array of parents
     */
    private static void union(int u, int v, int [] size, int [] id) {
        int parentV = find(v, id), parentU = find(u, id);
        if(size[parentU] < size[parentV]) {
            id[parentU] = parentV;
            size[parentV] += size[parentU];
        } else {
            id[parentV] = parentU;
            size[parentU] += size[parentV];
        }
    }

    /*
     * preforms UF find operation as defined for weighted quick union
     * (with path compression because that's way cooler)
     * @pre id != null
     * @param v the vertex whose component is to be returned
     * @param id the array of parents
     */
    private static int find(int v, int [] id) {
        ArrayList<Integer> path = new ArrayList<Integer>();
        while(v != id[v]) {
            path.add(v);
            v = id[v];
        }
        for(int p : path) {
            id[p] = v;
        }
        return v;
    }

    /*
     * preforms connected operation as defined for weighted quick union
     * @pre id != null
     * @param u, v the two vertices to check
     * @param id the array of parents
     */
    private static boolean connected(int u, int v, int [] id) {
        return find(u, id) == find(v, id);
    }

    // testing helper function
    private static String padToSize(int x)
    {
        String padded = "" + x;
        for(int i = 0; i < 2 - x/10; i++) {
            padded = "0" + padded;
        }
        return padded;
    }

    public static void main(String[] args) {
        try{

            int testsFailed = 0;
            int batchNum = Integer.parseInt(args[0]);
            int numTests = Integer.parseInt(args[1]);
            Scanner s, sOutput;
            String prefix = "Testing" + File.separator + "Generated" + File.separator + "batch" + padToSize(batchNum) + File.separator;
            int oracle, n;
            for(int x = 1; x <= numTests; x++)
            {
                s = new Scanner(new File(prefix + "in" + padToSize(x) + ".txt"));
                sOutput = new Scanner(new File(prefix + "out" + padToSize(x) + ".txt"));

                oracle = sOutput.nextInt();
                n = s.nextInt();

                int[][][] adj = new int[n][][];

                int valuesRead = 0;
                for (int i = 0; i < n && s.hasNextInt(); i++) {
                    LinkedList<int[]> edgeList = new LinkedList<int[]>();
                    for (int j = 0; j < n && s.hasNextInt(); j++) {
                        int weight = s.nextInt();
                        if(weight > 0) {
                            edgeList.add(new int[]{j, weight});
                        }
                        valuesRead++;
                    }
                    adj[i] = new int[edgeList.size()][2];
                    Iterator it = edgeList.iterator();
                    for(int k = 0; k < edgeList.size(); k++) {
                        adj[i][k] = (int[]) it.next();
                    }
                }

                int totalWeight = mst(adj);

                if(totalWeight != oracle)
                {
                    System.out.println("Failed test: batch " + batchNum + ", n = " + n);
                    testsFailed++;
                } else
                {
                    System.out.println("Passed test " + n);
                }
            }

            System.out.println("=======================================");
            System.out.println("Finished Testing. " + numTests + " tests executed.");
            if(testsFailed == 0) {
                System.out.println("All tests pass");
            } else {
                System.out.println("Failed " +testsFailed+ " tests");
            }
            System.out.println("=======================================");
        } catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}