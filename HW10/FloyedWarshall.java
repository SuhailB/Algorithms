import java.io.*;
import java.util.*;

//vertex object
class Vertex {
    int id;
    Vertex p;

    public Vertex()
    {
        this.id = 0;
        this.p = null;
    }
}

class Graph { 

    //represent infinity
    static final double INF = 99999; 
  
    //number of vertices
    private int v_count; 
    //adjacency matrix to hold the weights and edges of the graph
    public double[][] adj; 
    //array of vertices of the graph
    public Vertex[] V;
    
    public Graph(int vertices){ 

        v_count = vertices;
        V = new Vertex[vertices];
        adj = new double[vertices][vertices];
        
        //initialize the adjacency matrix to all infinity weights
        for(int i=0; i<vertices; i++)
        {
            for(int j=0; j<vertices; j++)
            {
                adj[i][j] = INF;
            }
        }
        //initialize the vertices with the setting the diagonal
        //(the weight of a vertex to itself) to zero
        for(int i = 0; i <vertices; i++) 
        { 
            adj[i][i] = 0;
            V[i] = new Vertex();
        } 
    } 

    //a function to add an edge to a graph
    public void setEdge(int u, int v, double w) 
    {
        if(u>0 && v>0){    
            this.adj[u-1][v-1] = w;
            this.V[u-1].id = u;
        } 
        else
            System.out.println("couldn't add edge!");
    } 

    Vertex FloyedWashall()
    {
        //initializing the distance matrix
        double D[][] = new double[v_count][v_count];
        //initializing the predecessor matrix
        int P[][] = new int[v_count][v_count];

        for (int i = 0; i < v_count; i++) 
            for (int j = 0; j < v_count; j++)
            { 
                //copy the adjacency matrix with weigts to the distance matrix
                D[i][j] = adj[i][j]; 
                //initialize the parent matrix with NIL if no edge exist
                if(i==j || D[i][j]>=INF)
                    P[i][j] = -1;
                //initialize the parent matrix with vertex i an edge exist
                //P_ij^((0))=i,where i≠j
                else if(i!=j && D[i][j]<INF)
                    P[i][j] = i+1;
            }
       
        //Floyed Warshall main loops
        //for each iteration K from 0 to v_count
        for (int k = 0; k < v_count; k++){ 
            //for each entry in the matrices
            for (int i = 0; i < v_count; i++){   
                for (int j = 0; j < v_count; j++) { 
                    //if the new distance is smaller than the older disance
                    //and none of the distances i->k or k->j are infinity, 
                    //update the Distance from i->j with the new smaller distance.
                    //Also update the P[i][j] entry of the parent matrix
                    if (D[i][j] > D[i][k] + D[k][j] && D[i][k] != INF && D[k][j] != INF){
                        P[i][j] = P[k][j];
                        D[i][j] = D[i][k] + D[k][j];
                    }
                    //change the parent pointer of vertex j from i to k
                    if(P[i][j]!=-1) V[j].p = V[P[i][j]-1];
                    //check if a negative cycle occured, then return that vertex
                    //to retreive the path
                    if(D[i][j]<0 && i==j) return V[i];
                } 
            } 
        } 
      
        //additional check which most probably won't be reached
        for (int i = 0; i < v_count; i++) 
            if (D[i][i] < 0) return V[i]; 
        
        //return null if a negative-weight cycle was not detected
        return null;  
    }
}

//line object
class Line
{
    double u;
    double v;
    double a;
    double b;
    public Line(double u, double v, double a, double b)
    {
        this.u = u;
        this.v = v;
        this.a = a;
        this.b = b;
    }
}


public class FloyedWarshall
{
	public static void main(String[] args){

        //files
        String [] inputs = {"input1.txt","input2.txt","input3.txt","input4.txt","input5.txt","input6.txt"};
        String [] outputs = {"output1.txt","output2.txt","output3.txt","output4.txt","output5.txt","output6.txt"};
        
        for(int i=0; i<6; i++)
        {
            //store the lines read
            ArrayList<Line> lines = new ArrayList<>();

            try{
                //io operations
                File input = new File(inputs[i]);
                Scanner fileScan = new Scanner(input);
                FileWriter fileWriter = new FileWriter(outputs[i]);
                PrintWriter printWriter = new PrintWriter(fileWriter);
            
                //get v_count from files
                int v_count = fileScan.nextInt();
                
                //create a graph and add the edges with weigts to it.
                //also add the lines to the lines arraylist
                Graph g = new Graph(v_count); 
                while(fileScan.hasNext())
                {
                    double u = fileScan.nextInt();
                    double v = fileScan.nextInt();
                    double a = fileScan.nextDouble();
                    double b = fileScan.nextDouble();
                    lines.add(new Line(u,v,a,b));
                    //make a weight
                    double w = Math.log(a/b);
                    g.setEdge((int)u,(int)v,w); 
                }

                //run the FloyedWarshall algorithm
                Vertex head = g.FloyedWashall();
               
                ArrayList<Double> edges = new ArrayList<>();
                //if a negative cycle is found retreive the path using a stack and pointer,
                //and print the results to files
                if(head!=null){
                    printWriter.println("yes");
                    head = head.p;
                    head = head.p;
                    int x = head.id-1;
                    Stack<Integer> stack = new Stack<>();
                    do
                    {
                        stack.push(head.id);
                        head = head.p;
                    }while(x != head.id-1);

                    while(!stack.empty())
                    {
                        double u = (double) stack.pop();
                        edges.add(u);
                    }
                    ArrayList<Line> solution = new ArrayList<>();
                    for(int j=0; j<edges.size(); j++)
                    {
                        double u = edges.get(j);
                        double v = edges.get((j+1)%edges.size());
                        for (Line line : lines) {
                            if (line.u == u && line.v == v) {
                                solution.add(new Line(line.u,line.v,line.a,line.b));
                                printWriter.println((int)line.u+" "+(int)line.v+" "+line.a+" "+line.b);
                            }
                        }
                    }
                    double result = 1;
                    for(int j=0; j<solution.size(); j++)
                        result *= (solution.get(j).b/solution.get(j).a);
                    printWriter.print("one kg of product "+ edges.get(0).intValue()+" gets "+result+" kg of product " +edges.get(0).intValue()+ " from the above sequence.");
                }
                //if no negative weight cycles detected
                else
                    printWriter.print("no");
        
                fileScan.close();
                printWriter.close();

            } catch (Exception e){
                e.printStackTrace();
            }
            
        }
      
    }
}


//bellford implementaions
// boolean BellFord(Vertex s)
    // {
    //     int x = 0;
    //     ISS(s);
    //     for(int i=0; i<v_count; ++i)
    //     {
    //         x= -1;
    //         for(int j=0; j<v_count; j++)
    //         {
    //             for(int k=0; k<v_count; k++)
    //             {
    //                 Vertex u = V[j];
    //                 Vertex v = V[k];
    //                 if(adj[j][k]==1){
                        
    //                     if (u.d + w[u.id-1][v.id-1] < v.d){
    //                         v.d = u.d + w[u.id-1][v.id-1];
    //                         v.p = u;
    //                         x = v.id-1;
    //                     }
            
    //                 }
    //             }
    //         }
    //     }

    //     if(x != -1)
    //     {
    //         Stack<Integer> stack = new Stack<>();
    //         Vertex v = V[x];
    //         do
    //         {
    //             stack.push(v.id);
    //             v = v.p;
    //         }while(x != v.id-1);

    //         while(!stack.empty())
    //         {
    //             System.out.print(stack.pop()+" ");
    //         }
    //     }

        
    //     for(int j=0; j<v_count; j++)
    //     {
    //         for(int k=0; k<v_count; k++)
    //         {
    //             Vertex u = V[j];
    //             Vertex v = V[k];
    //             if(adj[j][k]==1)
    //                 if(v.d > u.d + w[u.id-1][v.id-1])
    //                     return false;
    //         }
    //     }
    //     return true;
    // }

    // int Relax(Vertex u, Vertex v)
    // {
    //     if (v.d > u.d + w[u.id-1][v.id-1])
    //     {
    //         v.d = u.d + w[u.id-1][v.id-1];
    //         v.p = u;
    //         return v.id-1;
    //     }
    //     return -1;
    // }

    // void ISS(Vertex s)
    // {
    //     for(Vertex v: this.V)
    //     {
    //         v.d = INF;
    //         v.p = null;
    //     }
    //     s.d = 0;
    // }