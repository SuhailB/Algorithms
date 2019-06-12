import java.io.*;
import java.util.*;

class Vertix {
    int id;
    double d;
    Vertix p;

    public Vertix()
    {
        this.id = 0;
        this.d = 0;
        this.p = null;
    }
    public Vertix(Vertix copy)
    {
        this.id = copy.id;
        this.d = copy.d;
        this.p = copy.p;
    }
}

class Graph { 

    static final double INF = 99999.0; 
  
    private int v_count; 
    public double[][] adj; 
    public double[][] w;
    public Vertix[] V;
    
    public Graph(int vertices){ 

        v_count = vertices;
        V = new Vertix[vertices];
        adj = new double[vertices][vertices];
        w = new double[vertices][vertices];
        
        for(int i=0; i<vertices; i++)
        {
            for(int j=0; j<vertices; j++)
            {
                adj[i][j] = INF;
            }
        }
        for(int i = 0; i <vertices; i++) 
        { 
            adj[i][i] = 0;
            V[i] = new Vertix();
        } 
    } 

    public void addEdge(int u, int v, double w) 
    { 
        if(u>0 && v>0){
            
            this.adj[u-1][v-1] = w;
            // this.w[u-1][v-1] = w;
            this.V[u-1].id = u;
            // vertix.w.add(w);
        } 
        else
            System.out.println("couldn't add edge!");
    } 

   
    boolean BellFord(Vertix s)
    {
        int x = 0;
        ISS(s);
        for(int i=0; i<v_count; ++i)
        {
            x= -1;
            for(int j=0; j<v_count; j++)
            {
                for(int k=0; k<v_count; k++)
                {
                    Vertix u = V[j];
                    Vertix v = V[k];
                    if(adj[j][k]==1){
                        
                        if (u.d + w[u.id-1][v.id-1] < v.d){
                            v.d = u.d + w[u.id-1][v.id-1];
                            v.p = u;
                            x = v.id-1;
                        }
            
                    }
                }
            }
        }
        System.out.println(x);
        if(x != -1)
        {
            Stack<Integer> stack = new Stack<>();
            Vertix v = V[x];
            do
            {
                stack.push(v.id);
                v = v.p;
            }while(x != v.id-1);

            while(!stack.empty())
            {
                System.out.print(stack.pop()+" ");
            }
        }

        
        for(int j=0; j<v_count; j++)
        {
            for(int k=0; k<v_count; k++)
            {
                Vertix u = V[j];
                Vertix v = V[k];
                if(adj[j][k]==1)
                    if(v.d > u.d + w[u.id-1][v.id-1])
                        return false;
            }
        }
        return true;
    }

    // int Relax(Vertix u, Vertix v)
    // {
    //     if (v.d > u.d + w[u.id-1][v.id-1])
    //     {
    //         v.d = u.d + w[u.id-1][v.id-1];
    //         v.p = u;
    //         return v.id-1;
    //     }
    //     return -1;
    // }

    void ISS(Vertix s)
    {
        for(Vertix v: this.V)
        {
            v.d = INF;
            v.p = null;
        }
        s.d = 0;
    }


    public static void print2DArray(double[][] val)
    {
        int n=val.length-1;
        System.out.print("  ");
        for(int i=0; i<n+1;i++)
        {
            System.out.print(i+" ");
        }
        System.out.println();
        for(int i=0; i<n+1; i++)
        {
            System.out.print(i+" ");
            for(int j=0; j<n+1; j++)
            {
                System.out.print(val[j][i]+" ");
            }
            System.out.println();
        }
    }

    Vertix FloyedWashall()
    {
        double dist[][] = new double[v_count][v_count];
        int i, j, k; 
      
        
        for (i = 0; i < v_count; i++) 
            for (j = 0; j < v_count; j++) 
                dist[i][j] = adj[i][j]; 
      
       
        for (k = 0; k < v_count; k++) 
        { 
            print2DArray(dist);
            // Pick all vertices as source one by one 
            for (i = 0; i < v_count; i++) 
            { 
                // if (dist[i][i] < 0)  return V[i];
                // Pick all vertices as destination for the 
                // above picked source 
                for (j = 0; j < v_count; j++) 
                { 
                    // if (dist[j][j] < 0)  return V[j];
                    // If vertex k is on the shortest path from 
                    // i to j, then update the value of dist[i][j] 
                    if (dist[i][k] + dist[k][j] < dist[i][j]) 
                    {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        // V[i].p = V[j];
                        // if(d[i][j]<= d[i][k]+d[k][j])

                        // if (dist[k][k] < 0) return V[k];
                    }
                    else
                    {
                        // V[k].p = V[j];
                    }
                } 
            } 
        } 
      
        // If distance of any verex from itself 
        // becomes negative, then there is a negative 
        // weight cycle. 
        for (i = 0; i < v_count; i++) 
            if (dist[i][i] < 0) 
                return V[i]; 
  
        return null;  
    }
}



public class BellFord
{

    public static void print2DArray(double[][] val)
    {
        int n=val.length-1;
        System.out.print("  ");
        for(int i=0; i<n+1;i++)
        {
            System.out.print(i+" ");
        }
        System.out.println();
        for(int i=0; i<n+1; i++)
        {
            System.out.print(i+" ");
            for(int j=0; j<n+1; j++)
            {
                System.out.print(val[j][i]+" ");
            }
            System.out.println();
        }
    }
	public static void main(String[] args){

        File input = new File("input1.txt");
        try{

            Scanner fileScan = new Scanner(input);
        
            int v_count = fileScan.nextInt();
            
            // Create a sample graph 
            Graph g = new Graph(v_count); 
            while(fileScan.hasNext())
            {
                int u = fileScan.nextInt();
                int v = fileScan.nextInt();
                double a = fileScan.nextDouble();
                double b = fileScan.nextDouble();
                // System.out.println(u+" "+v+" "+a+" "+b);
                double w = -Math.log(b/a);
                g.addEdge(u,v,w); 
            }

            // print2DArray(g.adj);
           
            Vertix v = g.FloyedWashall();
            // System.out.println(v.id+" ");
            // System.out.println(v.p.id+" ");
            // System.out.println(v.p.p.id+" ");
            // System.out.println(v.p.p.p.id+" ");
            // System.out.println(v.p.p.p.p.id+" ");
            // System.out.println(v.p.p.p.p.p.id+" ");
            // for(Vertix v: g.V)
            // {
            //     System.out.println(v.id+": "+v.p.id);
            //     // System.out.println(v.adj);
            //     // System.out.println(v.w);
            // }
            // int x = v.id-1;
            // Stack<Integer> stack = new Stack<>();
            // do
            // {
            //     stack.push(v.id);
            //     v = v.p;
            // }while(x != v.id-1);

            // while(!stack.empty())
            // {
            //     System.out.print(stack.pop()+" ");
            // }

    
        } catch (Exception e){
            e.printStackTrace();
        }
        // g.addEdge(1,2,-Math.log(0.9)); 
        // g.addEdge(2,3,-Math.log(1.5)); 
        // g.addEdge(3,4,-Math.log(1.5)); 
        // g.addEdge(4,1,-Math.log(0.5)); 
        // g.addEdge(2,1,-Math.log((49.0/45.0))); 
        // g.addEdge(1,5,-Math.log(0.7)); 
        // g.addEdge(5,1,-Math.log((4.0/3.0))); 

        // g.addEdge(0,1,0); 
        // g.addEdge(1,2,0); 
        // g.addEdge(1,4,0); 
        // g.addEdge(1,5,0); 
        // g.addEdge(2,3,0); 
        // g.addEdge(2,6,0); 
        // g.addEdge(3,2,0); 
        // g.addEdge(3,7,0); 
        // g.addEdge(4,0,0); 
        // g.addEdge(4,5,0); 
        // g.addEdge(5,6,0); 
        // g.addEdge(6,5,0); 
        // g.addEdge(6,7,0); 
        // g.addEdge(7,7,0); 


        // g.addEdge(0,4,0); 
        // g.addEdge(1,0,0); 
        // g.addEdge(2,1,0); 
        // g.addEdge(2,3,0); 
        // g.addEdge(3,2,0); 
        // g.addEdge(4,1,0); 
        // g.addEdge(5,4,0); 
        // g.addEdge(5,1,0); 
        // g.addEdge(5,6,0); 
        // g.addEdge(6,2,0); 
        // g.addEdge(6,5,0); 
        // g.addEdge(7,3,0); 
        // g.addEdge(7,6,0); 
        // g.addEdge(7,7,0); 
       

        // g.getSCCs();

        // g.ISS(g.V[0]);
        // g.Relax(g.V[0],g.V[1]);
        
        // g.getSCCs();
        // for(Vertix v: g.V)
        // {
        //     System.out.println(v.id-1+": "+v.d);
        //     // System.out.println(v.adj);
        //     // System.out.println(v.w);
        // }
       
        // g.getSCCs();
        // Graph tg = g.getTranspose();
        
        // System.out.println();

        
        // for(Vertix v: tg.V)
        // {
        //     System.out.print(v.id-1+": ");
        //     System.out.println(v.adj);
        // }
        // ArrayList<Graph> SCCGs = g.getSCCs();

        // for( ArrayList<Integer> C: SCCs)
        // {
        //     System.out.println(C);
        // }
        


        // for(ArrayList<Integer> list: g.adj)
        // {
        //     System.out.println(list);
        // }
        
        // g.DFS(false, null);
        // // for(Vertix v: g.V)
        // // {
        // //     System.out.println(v.id+" "+v.f);
        // // }
        // for(int i=0; i<8; i++)
        // {
        //     Vertix v = g.V.get(i);
        //     ArrayList<Integer> list = g.adj[i];
        //     System.out.print(v.id+" ");
        //     System.out.print(list);
        //     System.out.println(" "+ v.d +" "+v.f+" "+v.color);
        // }
        // System.out.println();
        // Graph tg = g.getTranspose();
        // tg.DFS(true, g.V);
        // // for(ArrayList<Integer> list: tg.adj)
        // // {
        // //     System.out.println(list);
        // // }
        // for(int i=0; i<8; i++)
        // {
        //     Vertix v = tg.V.get(i);
        //     ArrayList<Integer> list = tg.adj[i];
        //     System.out.print(v.id+" ");
        //     System.out.print(list);
        //     System.out.println(" "+ v.d +" "+v.f+" "+v.color);
        // }


        // 

        // // tg.DFS();
        // for(Vertix v: tg.V)
        // {
        //     System.out.println(v.id+" "+v.f);
        // }
        

        // for(ArrayList<Integer> list: tg.adj)
        // {
        //     System.out.println(list);
        // }
       
        // g.SCC();

      
    }
}
