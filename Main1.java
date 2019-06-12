import java.io.*;
import java.util.*;
enum Color 
{ 
    WHITE, GRAY, BLACK; 
} 
class Vertix {
    int id;
    int d;
    int f;
    Color color;
    Vertix p;
}


class Graph { 
  
    public Vertix[] V; 
    private int v_count; 
    public ArrayList<Integer>[] adj;  
    public ArrayList<Double>[] w;
    private int time=0;
    public ArrayList<Integer>[] pathLists; 
    private ArrayList<Integer> tmpPath = new ArrayList<Integer>();
    private int current_list;
       
    public Graph(int vertices){ 

        v_count = vertices;
        adj = new ArrayList[vertices]; 
        w = new ArrayList[vertices];
        V = new Vertix[vertices];
        for(int i = 0; i <vertices; i++) 
        { 
            adj[i] = new ArrayList<Integer>(); 
            w[i] = new ArrayList<Double>();
            V[i] = new Vertix();
            V[i].id = i+1;
        } 
    } 
      
    public void addEdge(int u, int v, double w) 
    { 
        if(u>0 && v>0){
            this.adj[u-1].add(v-1); 
            this.w[u-1].add(w);
        } 
        else
            System.out.println("couldn't add edge!");
    } 
      
    public void printAdjList()
    {
        for (ArrayList<Integer>  l : adj)  
        { 
            // System.out.println(l);
           for(Integer i: l)
           {
               System.out.print(i+1+" ");
           }
           System.out.println();
        } 
    }
    public void DFS()
    {
        current_list = 0;
        time = 0;
        for(Vertix u: V)
        {
            u.color = Color.WHITE;
            u.p = null;
        }
        for(Vertix u: V)
        {
            if(u.color == Color.WHITE)
                DFS_VISIT(u.id-1);
        }
    }

   
    public void DFS_VISIT(int start)
    {
        Stack<Vertix> stack = new Stack<Vertix>();
        Vertix s = V[start];
        stack.push(s);
        while(stack.empty()==false)
        {
            Vertix u = stack.peek();
            
            if(u.color == Color.WHITE){
                u.color = Color.GRAY;
                u.d = ++time;
                // stack.push(u);
                ArrayList<Integer> list = adj[u.id-1];
                for(int i=0; i<list.size(); i++)
                {
                    int v_index = list.get(i);
                    Vertix v = V[v_index];
                    if(v.color == Color.WHITE)
                    {
                        v.p = u;
                        stack.push(v);
                    }
                    
                }
            }
            else if(u.color == Color.GRAY){
                u.color = Color.BLACK;
                u.f = ++time;
                stack.pop();
            }

            else {
                    System.out.println("Black");
                    stack.pop();
            }
            
        }
    }
    // public void DFS_VISIT(int start)
    // {

    //     Vertix u = V[start];
    //     u.d = ++time;
    //     u.color = Color.GRAY;

    //     ArrayList<Integer> list = adj[u.id-1];
    //     for(int i=0; i<list.size(); i++)
    //     {
    //         int v_index = list.get(i);
    //         Vertix v = V[v_index];
    //         if(v.color == Color.WHITE)
    //         {
    //             v.p = u;
    //             DFS_VISIT(v.id-1);
    //         }
            
    //     }
        
    //     u.color = Color.BLACK;
    //     u.f = ++time;

    // }
    
}



public class Main
{

	public static void main(String[] args){

        // Create a sample graph 
        Graph g = new Graph(4); 
        g.addEdge(1,2,0);
        g.addEdge(1,3,0);
        g.addEdge(3,4,0);
        g.addEdge(4,2,0);
        //  g.printAdjList();
        g.DFS();


        for(Vertix v: g.V)
        {
            System.out.println(v.id+": "+v.d+" "+v.f);
        }
    
    }
}


