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
                if(!connected(u, v))
                {
                    this.mstWeight += edgeWeight;
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
        int parentID, changeID;
        if(id[u] < id[v])
        {
            parentID = id[v];
            changeID = id[u];
        }
        else
        {
            parentID = id[u];
            changeID = id[v];
        }

        for(int i = 0; i < id.length; i++)
        {
            if(id[i] == changeID) id[i] = parentID;
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