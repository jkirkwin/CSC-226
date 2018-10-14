import java.util.*;
public class GraphWrapper
{
    public int n;
    public int mstWeight, m;
    public int [] id; // for our gross UF shenanigans
    public int [][] adj;

    public GraphWrapper(int n)
    {
        this.n = n;
        this.m = 0;
        this.mstWeight = 0;
        this.adj = new int[n][n];
        this.id = new int[n];
        for(int i = 0; i < n; i++)
        {
            this.id[i] = i;
        }

        // Generate the graph
        Random r = new Random();
        int edgeWeight = 1;
        int u, v;
        while(!isGraphConnected())
        {
            v = r.nextInt(n);
            u = r.nextInt(n);
            while(v == u)
            {
                u = r.nextInt(n);
            }
            if(!connected(u, v) || adj[u][v] == 0)
            {
                System.out.println("Adding edge (" +u+ ", " +v+") to graph");
                if(!connected(u, v))
                {
                    this.mstWeight += edgeWeight;
                    System.out.println("Adding edge to MST");
                }
                union(u,v);
                adj[u][v] = edgeWeight;
                adj[v][u] = edgeWeight;
                edgeWeight++;
                m++;
            }
        }
    }

    public boolean connected(int u, int v)
    {
        return id[u] == id[v];
    }

    public void union(int u, int v)
    {
        if(connected(u,v)) return;
        int parent = id[u];
        for(int i = 0; i < id.length; i++)
        {
            if(id[i] == parent) id[i] = id[v];
        }
    }


    private boolean isGraphConnected()
    {
        int x = id[0];
        for(int i : id)
        {
            if(i != x) return false;
        }
        return true;
    }

}