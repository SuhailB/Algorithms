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
    public void DFS(int start, int goal)
    {
        pathLists = new ArrayList[3];
        current_list = 0;
        time = 0;
        for(Vertix u: V)
        {
            u.color = Color.WHITE;
            u.p = null;
        }
        // for(Vertix u: V)
        // {
        //     if(u.color == Color.WHITE)
                // DFS_VISIT(u.id, 0);
        // }

        DFS_VISIT(start, goal);
    }

   
    public void DFS_VISIT(int start, int goal)
    {

        // ArrayList<Integer>[] pathLists = new ArrayList[]; ;
        Vertix u = V[start];
        u.d = ++time;
        u.color = Color.GRAY;

        
        //create a new pathlist
        //add u.id to it
        tmpPath.add(u.id);

        ArrayList<Integer> list = adj[u.id-1];
        for(int i=0; i<list.size(); i++)
        {
            int v_index = list.get(i);
            Vertix v = V[v_index];
            if(v.color == Color.WHITE)
            {
                v.p = u;
                DFS_VISIT(v.id-1, goal);
                if(tmpPath.size()>0)
                    tmpPath.remove(tmpPath.size()-1);
            }
            else 
            {
                if((v.id-1) == goal)
                {

                    tmpPath.add(v.id);
                    pathLists[current_list] = (ArrayList<Integer>) tmpPath.clone();
                    
                    
                    for(Integer integer : pathLists[current_list])
                    {
                        System.out.print(integer+" ");
                    }

                    current_list++;
                    System.out.println("path ending with "+u.id+" saved");
                    tmpPath.remove(tmpPath.size()-1);
                }
                else
                {
                    tmpPath.clear();
                    System.out.println("list cleared");
                }

            }
            
        }
        
        u.color = Color.BLACK;
        u.f = ++time;
        

    }
    
}


public class ElementaryCyclesSearch {
	/** List of cycles */
	private List cycles = null;

	/** Blocked nodes, used by the algorithm of Johnson */
	private boolean[] blocked = null;

	/** B-Lists, used by the algorithm of Johnson */
	private Vector[] B = null;

	/** Stack for nodes, used by the algorithm of Johnson */
	private Vector stack = null;


	/**
	 * Returns List::List::Object with the Lists of nodes of all elementary
	 * cycles in the graph.
	 *
	 * @return List::List::Object with the Lists of the elementary cycles.
	 */
	public List getElementaryCycles() {
		this.cycles = new Vector();
		this.blocked = new boolean[this.adjList.length];
		this.B = new Vector[this.adjList.length];
		this.stack = new Vector();
		StrongConnectedComponents sccs = new StrongConnectedComponents(this.adjList);
		int s = 0;

		while (true) {
			SCCResult sccResult = sccs.getAdjacencyList(s);
			if (sccResult != null && sccResult.getAdjList() != null) {
				Vector[] scc = sccResult.getAdjList();
				s = sccResult.getLowestNodeId();
				for (int j = 0; j < scc.length; j++) {
					if ((scc[j] != null) && (scc[j].size() > 0)) {
						this.blocked[j] = false;
						this.B[j] = new Vector();
					}
				}

				this.findCycles(s, s, scc);
				s++;
			} else {
				break;
			}
		}

		return this.cycles;
	}

	/**
	 * Calculates the cycles containing a given node in a strongly connected
	 * component. The method calls itself recursivly.
	 *
	 * @param v
	 * @param s
	 * @param adjList adjacency-list with the subgraph of the strongly
	 * connected component s is part of.
	 * @return true, if cycle found; false otherwise
	 */
	private boolean findCycles(int v, int s, Vector[] adjList) {
		boolean f = false;
		this.stack.add(new Integer(v));
		this.blocked[v] = true;

		for (int i = 0; i < adjList[v].size(); i++) {
			int w = ((Integer) adjList[v].get(i)).intValue();
			// found cycle
			if (w == s) {
				Vector cycle = new Vector();
				for (int j = 0; j < this.stack.size(); j++) {
					int index = ((Integer) this.stack.get(j)).intValue();
					cycle.add(this.graphNodes[index]);
				}
				this.cycles.add(cycle);
				f = true;
			} else if (!this.blocked[w]) {
				if (this.findCycles(w, s, adjList)) {
					f = true;
				}
			}
		}

		if (f) {
			this.unblock(v);
		} else {
			for (int i = 0; i < adjList[v].size(); i++) {
				int w = ((Integer) adjList[v].get(i)).intValue();
				if (!this.B[w].contains(new Integer(v))) {
					this.B[w].add(new Integer(v));
				}
			}
		}

		this.stack.remove(new Integer(v));
		return f;
	}

	/**
	 * Unblocks recursivly all blocked nodes, starting with a given node.
	 *
	 * @param node node to unblock
	 */
	private void unblock(int node) {
		this.blocked[node] = false;
		Vector Bnode = this.B[node];
		while (Bnode.size() > 0) {
			Integer w = (Integer) Bnode.get(0);
			Bnode.remove(0);
			if (this.blocked[w.intValue()]) {
				this.unblock(w.intValue());
			}
		}
	}
}


public class Main
{

	public static void main(String[] args){

        // Create a sample graph 
        Graph g = new Graph(5); 
        g.addEdge(1,2,0.9); 
        g.addEdge(2,3,1.5); 
        g.addEdge(3,4,1.5); 
        g.addEdge(4,1,0.5); 
        g.addEdge(2,1,(49/45)); 
        g.addEdge(1,5,0.7); 
        g.addEdge(5,1,(4/3)); 
        //  g.printAdjList();
        g.DFS(0,0);


        for(ArrayList<Double> list: g.w)
        {
            System.out.println(list);
        }
        //  ArrayList<Vertix> pathlist = g.DFS_VISIT(0, 0);
        //  System.out.println(pathlist.size());
        // for(Vertix v: g.V)
        // {
        //     // System.out.print(v.id+" ");
        //     System.out.println("d " + v.d + " f "+v.f);
        // }

     
        // for(ArrayList<Integer> list: g.pathLists)
        // {
        //     for(Integer i : list)
        //     {
        //         System.out.print(i+" ");
        //     }
        //     System.out.println();
        // }
        //  for(Integer i: g.pathList)
        //  {
        //     System.out.print(i+" ");
        //  }
        //  // arbitrary source 
        //  int s = 0; 
       
        //  // arbitrary destination 
        //  int d = 0; 
       
        //  System.out.println("Following are all different paths from "+s+" to "+d); 
        //  g.printAllPaths(s, d); 
    }
}


