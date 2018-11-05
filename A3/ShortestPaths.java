/* ShortestPaths.java
   CSC 226 - Fall 2018

   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
   java ShortestPaths

   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
   java ShortestPaths file.txt
   where file.txt is replaced by the name of the text file.

   The input consists of a series of graphs in the following format:

   <number of vertices>
   <adjacency matrix row 1>
   ...
   <adjacency matrix row n>

   Entry A[i][j] of the adjacency matrix gives the weight of the edge from
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].

   An input file can contain an unlimited number of graphs; each will be processed separately.

   NOTE: For the purpose of marking, we consider the runtime (time complexity)
         of your implementation to be based only on the work done starting from
	 the ShortestPaths() method. That is, do not not be concerned with the fact that
	 the current main method reads in a file that encodes graphs via an
	 adjacency matrix (which takes time O(n^2) for a graph of n vertices).


   (originally from B. Bird - 08/02/2014)
   (revised by N. Mehta - 10/24/2018)
*/

/*
    Assignment done by Jamie K
    Nov 2018
 */

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Iterator;

public class ShortestPaths{

    private static PriorityQueue<WeightedEdge> pq;
    private static boolean [] marked;
    private static int [] d; // distance to a node from src
    public static int n; // number of vertices
    public static int [] pi; // gives path from src to each vertex

    // A helper used to make the code readible; otherwise we have to work directly with adj and the total lack of
    // intuition that comes with using that array of ints.
    private static class WeightedEdge {
        private int to;
        private int from;
        private int weight;

        public WeightedEdge(int t, int f, int w) {
            this.to = t;
            this.from = f;
            this.weight = w;
        }

        public int to() {
            return to;
        }

        public int from() {
            return from;
        }

        public int weight() {
            return weight;
        }
    }

    /* ShortestPaths(adj)
       Given an adjacency list for an undirected, weighted graph, calculates and stores the
       shortest paths to all the vertices from the source vertex.

       The number of vertices is adj.length
       For vertex i:
         adj[i].length is the number of edges
         adj[i][j] is an int[2] that stores the j'th edge for vertex i, where:
           the edge has endpoints i and adj[i][j][0]
           the edge weight is adj[i][j][1] and assumed to be a positive integer

       All weights will be positive.
    */
    static void ShortestPaths(int[][][] adj, int source){
        // Initialize globals to be used (ew)
        n = adj.length;
        ArrayList<ArrayList<WeightedEdge>> edgeList = makeEdgeList(adj);

        marked = new boolean[n];
        marked[source] = true;

        d = new int[n];
        for(int i = 0; i < d.length; i++) {
            d[i] = ((i == source) ? 0 : Integer.MAX_VALUE);
        }

        pi = new int[n];
        for(int i = 0; i < pi.length; i++) {
            pi[i] = ((i == source) ? source : -1);
        }

        pq = new PriorityQueue<WeightedEdge>( (e, f) -> {
            int x = d[e.from()] + e.weight();
            int y = d[f.from()] + f.weight();

            if(x == y) return 0;
            else if(x < y) return -1;
            else return 1;
        });
        for(WeightedEdge e : edgeList.get(source)) {
            pq.add(e);
        }

        // main loop
        while(!pq.isEmpty()) {
            WeightedEdge e = pq.poll();

            int u = e.from();
            int v = e.to();
            if(marked[v]) {
                continue;
            }

            if(d[v] > d[u] + e.weight())
            {
                d[v] = d[u] + e.weight();
                pi[v] = u;
            }
            marked[v] = true;
            for(WeightedEdge f : edgeList.get(v)) {
                if(!marked[f.to()]) {
                    pq.add(f);
                }
            }
        }
    }

    /*
     * Creates an adjacency list that is a little (but not much) less horrific than the one given.
     * edgeList.get(i) gives an arraylist of edges incident to vertex i.
     */
    private static ArrayList<ArrayList<WeightedEdge>> makeEdgeList(int[][][] adj) {
        ArrayList<ArrayList<WeightedEdge>> edgeList = new ArrayList<ArrayList<WeightedEdge>>();
        for(int i = 0; i < adj.length; i++) {
            ArrayList<WeightedEdge> l = new ArrayList<WeightedEdge>();
            for(int j = 0; j < adj[i].length; j++) {
                int to = adj[i][j][0]; // index will be the other vertex (not i)
                int from = i;
                int weight = adj[i][j][1];
                WeightedEdge element = new WeightedEdge(to, from, weight);
                l.add(element);
            }
            edgeList.add(i, l);
        }
        return edgeList;
    }

    static void PrintPaths(int source){
        for(int i = 0; i < n; i++) {
            System.out.print("The path from " + source + " to " + i +" is: " + getPath(source, i));
            System.out.println(" and the total distance is : " + d[i]);
        }
    }

    private static String getPath(int s, int v) {
        String path = Integer.toString(v);
        while(v != pi[v]) {
            path = pi[v] + " --> " + path;
            v = pi[v];
        }
        return path;
    }

    /* main()
       Contains code to test the ShortestPaths function. You may modify the
       testing code if needed, but nothing in this function will be considered
       during marking, and the testing process used for marking will not
       execute any of the code below.
    */
    public static void main(String[] args) throws FileNotFoundException{
        Scanner s;
        if (args.length > 0){
            //If a file argument was provided on the command line, read from the file
            try{
                s = new Scanner(new File(args[0]));
            } catch(java.io.FileNotFoundException e){
                System.out.printf("Unable to open %s\n",args[0]);
                return;
            }
            System.out.printf("Reading input values from %s.\n",args[0]);
        }
        else{
            //Otherwise, read from standard input
            s = new Scanner(System.in);
            System.out.printf("Reading input values from stdin.\n");
        }

        int graphNum = 0;
        double totalTimeSeconds = 0;

        //Read graphs until EOF is encountered (or an error occurs)
        while(true){
            graphNum++;
            if(graphNum != 1 && !s.hasNextInt())
                break;
            System.out.printf("Reading graph %d\n",graphNum);
            int n = s.nextInt();
            int[][][] adj = new int[n][][];

            int valuesRead = 0;
            for (int i = 0; i < n && s.hasNextInt(); i++){
                LinkedList<int[]> edgeList = new LinkedList<int[]>();
                for (int j = 0; j < n && s.hasNextInt(); j++){
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
            if (valuesRead < n * n){
                System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
                break;
            }

            /*
            // output the adjacency list representation of the graph
            for(int i = 0; i < n; i++) {
                System.out.print(i + ": ");
                for(int j = 0; j < adj[i].length; j++) {
                    System.out.print("(" + adj[i][j][0] + ", " + adj[i][j][1] + ") ");
                }
                System.out.print("\n");
            }

            long startTime = System.currentTimeMillis();
            */
            ShortestPaths(adj, 0);
            PrintPaths(0);
//            long endTime = System.currentTimeMillis();
//            totalTimeSeconds += (endTime-startTime)/1000.0;

            //System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
        }
        graphNum--;
        System.out.printf("Processed %d graphs.",graphNum);
    }
}