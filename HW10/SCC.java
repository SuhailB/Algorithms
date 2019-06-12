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
    ArrayList<Integer> adj;
    Vertix p;
}

class SorterByFinishTime implements Comparator<Vertix>
{
	public int compare(Vertix A, Vertix B)
	{
        if(A.f<B.f) 
            return 1;
        else if(A.f>B.f) 
            return -1;
        else
            return 0;
	}
} 

class Graph { 
  
    public ArrayList<Vertix> V; 
    private int v_count; 
    // public ArrayList<Integer>[] adj;  
    public ArrayList<Double>[] w;
    private int time;
  
       
    public Graph(int vertices){ 

        v_count = vertices;
        w = new ArrayList[vertices];
        V = new ArrayList<>();
        for(int i = 0; i <vertices; i++) 
        { 
            Vertix v = new Vertix();
            v.adj = new ArrayList<Integer>(); 
            w[i] = new ArrayList<Double>();
            v.id = i+1;
            V.add(v);
        } 
    } 

    public void addEdge(int u, int v, double w) 
    { 
        Vertix vertix = V.get(u);
        vertix.adj.add(v); 
        this.w[u].add(w);
        
        // if(u>0 && v>0){
        //     this.adj[u-1].add(v-1); 
        //     this.w[u-1].add(w-1);
        // } 
        // else
        //     System.out.println("couldn't add edge!");
    } 

    Graph getTranspose() 
	{ 
		Graph tg = new Graph(v_count); 
		for (int v = 0; v<v_count; v++) 
		{ 
            for(Integer i: this.adj[v])
            {
                tg.adj[i].add(v);
            }
		} 
		return tg; 
    } 
    
    public ArrayList<Graph> getSCCs()
    {
        ArrayList<ArrayList<Integer>> SCCV;
        ArrayList<Graph> SCCGs = new ArrayList<Graph>();
        DFS(false,null);
        Graph tg = this.getTranspose();

        SCCV = tg.DFS(true,this.V);

        for(ArrayList<Integer> SCC: SCCV)
        {
            Graph g = new Graph(SCC.size());
            for(int i=0; i<SCC.size(); i++)
            {
                g.adj[i] = (ArrayList<Integer>) this.adj[SCC.get()].clone();
            }
        }

        return SCCGs;
    }
    public ArrayList<ArrayList<Integer>> DFS(boolean tg, ArrayList<Vertix> gV)
    {
        ArrayList<ArrayList<Integer>> SCC = new ArrayList<ArrayList<Integer>>();
        time = 0;
        for(Vertix u: V)
        {
            u.d = 0;
            u.f = 0;
            u.color = Color.WHITE;
            u.p = null;
        }
        
        if(!tg)
        {
            for(Vertix u: V)
            {
                if(u.color == Color.WHITE)
                    DFS_VISIT(u.id-1, 0, null);
            }
        }
        else
        {
            SorterByFinishTime sorter = new SorterByFinishTime();
            Collections.sort(gV, sorter);
            int [] arr = new int[gV.size()];

            for(int i=0; i<gV.size(); i++)
            {
                Vertix v = gV.get(i);
                arr[i] = v.id-1;
                // System.out.println(v.id-1+" "+v.f);
            }
            // for(Vertix v: V)
            // {
            //     System.out.println(v.id+" "+v.f);
            // }

            for(int i=0; i<V.size(); i++)
            {
                int v_index = arr[i];
                ArrayList<Integer> tree = new ArrayList<>();
                Vertix u = V.get(v_index);
                if(u.color == Color.WHITE)
                    DFS_VISIT(u.id-1, 0, tree);
                
                if(!tree.isEmpty())
                    SCC.add(tree);
            }

        }
        return SCC;
    }

    public void DFS_VISIT(int start, int goal, ArrayList<Integer> tree)
    {
        Vertix u = V.get(start);
        u.d = ++time;
        u.color = Color.GRAY;


        ArrayList<Integer> list = adj[u.id-1];
        for(int i=0; i<list.size(); i++)
        {
            int v_index = list.get(i);
            Vertix v = V.get(v_index);
            if(v.color == Color.WHITE)
            {
                v.p = u;
                DFS_VISIT(v.id-1, goal, tree);
            }
            
        }
        
        if(tree!=null)
            tree.add(u.id);
        u.color = Color.BLACK;
        u.f = ++time;
        

    }
    

    // ArrayList<Integer> index_counter = new ArrayList<>(1);
    
    // ArrayList<Integer> lowlink = new ArrayList<>(10);
    // ArrayList<Integer> index = new ArrayList<>(10);
    // Stack<Integer> stack = new Stack<Integer>();

    // public void SCC()
    // {
    //    for(int i=0; i<v_count; i++)
    //    {
    //        if (!index.contains(i))
    //             SC(i);
    //    }
    // }
    // public void SC(int node)
    // {
        
    //     index_counter.add(0);
    //     for(int i=0; i<6; i++)
    //     {
    //         index.add(0);
    //         lowlink.add(0);
    //     }

    //     // System.out.println(lowlink.size());
    //     index.set(node,index_counter.get(0));
    //     lowlink.set(node,index_counter.get(0));
    //     index_counter.set(0,index_counter.get(0)+1);
    //     stack.push(node);
    //     ArrayList<Integer> successors = this.adj[node];
    //     // System.out.println(successors);
    //     for(Integer s : successors)
    //     {
    //         if (!index.contains(s))
    //         {
    //             SC(s);
    //             lowlink.set(node,Math.min(lowlink.get(node),lowlink.get(s)));
    //         }
    //         else if(stack.contains(s))
    //         {
    //             lowlink.set(node,Math.min(lowlink.get(node),index.get(s)));
    //         }
    //     }

    //     if (lowlink.get(node) == index.get(node))
    //     {
    //         ArrayList<Integer> CC = new ArrayList<>();

    //         while(true)
    //         {
    //             int s = stack.pop();
    //             CC.add(s);
    //             if(s==node) break;
    //         }

    //         System.out.print(CC);
    //         return;
    //     }
        
    // }
    
}


public class SCC
{

	public static void main(String[] args){

        // Create a sample graph 
        Graph g = new Graph(8); 
        // g.addEdge(1,2,0.9); 
        // g.addEdge(2,3,1.5); 
        // g.addEdge(3,4,1.5); 
        // g.addEdge(4,1,0.5); 
        // g.addEdge(2,1,(49/45)); 
        // g.addEdge(1,5,0.7); 
        // g.addEdge(5,1,(4/3)); 

        g.addEdge(0,1,0); 
        g.addEdge(1,2,0); 
        g.addEdge(1,4,0); 
        g.addEdge(1,5,0); 
        g.addEdge(2,3,0); 
        g.addEdge(2,6,0); 
        g.addEdge(3,2,0); 
        g.addEdge(3,7,0); 
        g.addEdge(4,0,0); 
        g.addEdge(4,5,0); 
        g.addEdge(5,6,0); 
        g.addEdge(6,5,0); 
        g.addEdge(6,7,0); 
        g.addEdge(7,7,0); 


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




        // ArrayList<Graph> SCCGs = g.getSCCs();

        // for( ArrayList<Integer> C: SCCs)
        // {
        //     System.out.println(C);
        // }
        


        for(ArrayList<Integer> list: g.adj)
        {
            System.out.println(list);
        }
        
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
