
import java.io.*;
import java.util.*;
class Activity
{
    public int i;
    public int s;
    public int f;
    public int v;

    Activity()
    {
        this.i = 0;
        this.s = 0;
        this.f = 0;
        this.v = 0;
    }
    Activity(int i, int s, int f, int v)
    {
        this.i = i;
        this.s = s;
        this.f = f;
        this.v = v;
    }

    
}

public class Main
{

    static final int verbose = 1;

	public static void main(String[] args){

        int I[][], S[][], F[][], V[][];
        ArrayList<Activity> activities[] = new ArrayList[2];
        int n[] = new int[2];
        int index[] = new int[2];
        File input1 = new File("input1.txt");
        File input2 = new File("input2.txt");
        Set<Solution> optimalSolutions;
        
        
        try
        {
            Scanner fileScan[] = new Scanner[2];
            FileWriter fileWriter[] = new FileWriter[2];
            fileWriter[0] = new FileWriter("output1.txt");
            fileWriter[1] = new FileWriter("output2.txt");
            PrintWriter printWriter[] = new PrintWriter[2];
            printWriter[0] = new PrintWriter(fileWriter[0]);
            printWriter[1] = new PrintWriter(fileWriter[1]);
            
            
            fileScan[0] = new Scanner(input1);
            fileScan[1] = new Scanner(input2);
            n[0] = fileScan[0].nextInt();
            n[1] = fileScan[1].nextInt();
            index[0] = fileScan[0].nextInt();
            index[1] = fileScan[1].nextInt();
            

            for(int k=0; k<2; k++)
            {
                I = new int[2][n[k]];
                S = new int[2][n[k]];
                F = new int[2][n[k]];
                V = new int[2][n[k]];
                activities[k] = new ArrayList<Activity>();
                for(int j=0; j<n[k]; j++)
                {
                    activities[k].add(new Activity(fileScan[k].nextInt(),fileScan[k].nextInt(),fileScan[k].nextInt(),fileScan[k].nextInt()));
                }

                //sort by finish time
                SorterByFinishTime sorter = new SorterByFinishTime();
                Collections.sort(activities[k], sorter);

                for(int j=0; j<n[k]; j++)
                {
                    I[k][j] = activities[k].get(j).i;
                    S[k][j] = activities[k].get(j).s;
                    F[k][j] = activities[k].get(j).f;
                    V[k][j] = activities[k].get(j).v;
                }

                //calling the algorithm
                optimalSolutions = Algorithm(I[k],S[k],F[k],V[k]);
                Iterator<Solution> it1 = optimalSolutions.iterator();
                Solution oneSolution = it1.next();
                int maxValue = oneSolution.v;
                printWriter[k].println(maxValue);

                //writing the solutions to the file in either non-verbose mode (as instructions say)
                //or in verbose mode showing the sets of all the optimal solutions
                if(verbose==0)
                {
                    Iterator<Activity> it2 = oneSolution.A.iterator();
                    while(it2.hasNext())
                    {
                        Activity a = it2.next();
                        printWriter[k].print(a.i);
                        if(it2.hasNext())  printWriter[k].print(" ");
                    }
                    if(optimalSolutions.size()==1)
                        printWriter[k].println("\nIT HAS A UNIQUE SOLUTION");
                    else
                        printWriter[k].println("\nIT HAS MULTIPLE SOLUTIONS");
                }   
                else
                {
                    Iterator<Solution> solutionIter = optimalSolutions.iterator();
                    while(solutionIter.hasNext())
                    {
                        Solution solution = solutionIter.next();
                        Iterator<Activity> setIter = solution.A.iterator();
                        printWriter[k].print("{");
                        while(setIter.hasNext())
                        {
                            Activity a = setIter.next();
                            printWriter[k].print("a"+a.i);
                            if(setIter.hasNext())  printWriter[k].print(",");
                        }
                        printWriter[k].print("}");
                    
                    }
                    if(optimalSolutions.size()==1)
                        printWriter[k].println("\nIT HAS A UNIQUE SOLUTION");
                    else
                        printWriter[k].println("\nIT HAS MULTIPLE SOLUTIONS");
                }
                printWriter[k].close();
            }
            
           
          
        }
        catch(Exception e)
        {
            System.out.println("File not found or IO Exception");
        }

        
    }

    
    public static Set<Solution> Algorithm(int[] index, int[] s, int[] f, int[] v)
    {
        //a comparator to keep only unique solutions in the set of optimal solutions
        SolutionComparator solutionComp = new SolutionComparator();
        int n = s.length;

        //make a treeset to hold optimal solutions
        Set<Solution> optimalSolutions = new TreeSet<Solution>(solutionComp);

        //variable to hold maxValue incurred so far
        int maxValue = 0;
        //first loop to determine the width of the a subset Sij where l = j-i
        //the width of the subsets increase with each loop to get the values of set 
        //starting from set S00 (val[0][0]) to set S0n (val[0][n]), where maxVal of S0n is the optimal solution 
        for(int l=0; l<n; l++)
        {
            //this loop is to move along the activities with constant difference between i and j
            //for example, it checks val[0][1], then val[1][2] to val[n-1][n]
            for(int i=0; i<n-l; i++)
            {
                int j=i+l;
               
                //the function GetSolution uses the GREEDY-ACTIVITY-SELECTOR(s,f) algorithm to find compatible activities 
                //it returns a set of compatible activities in set Sij and the sum of their respective values
                Solution solution = GetSolution(index,s,f,v,i,j);
                //we compare the values of new activities and check if we got bigger value incurred, we clear the set and start anew set
                if(solution.v>maxValue)
                {
                    optimalSolutions.clear();
                    maxValue = solution.v;
                    optimalSolutions.add(solution);
                }
                //otherwise, we keep adding multiple optimal solutions of same value to the set of optimal solutions 
                else if(solution.v==maxValue)
                {
                    maxValue = solution.v;
                    optimalSolutions.add(solution);
                }
            }
        }

        return optimalSolutions;
    }

    public static void printSet(Set<Activity> set, PrintWriter printWriter)
    {
        for(Iterator<Activity> it = set.iterator(); it.hasNext();)
        {
            Activity a = it.next();
            printWriter.println(a.i+" "+a.s+" "+a.f+" "+a.v);
        }
    }

    public static void printSolutions(Set<Solution> solutions, PrintWriter printWriter)
    {
        for(Iterator<Solution> it = solutions.iterator(); it.hasNext();)
        {
            Set<Activity> a = it.next().A;
            printSet(a,printWriter);
            printWriter.println();
        }
    }

    public static void print2DArray(int[][] val)
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

    public static void printActivities(int[][] val, int[][] act, int i, int j)
    {
        if(val[i][j]>0)
        {
            int k=act[i][j];
            System.out.print(k+" ");
            printActivities(val, act, i, k);
            printActivities(val, act, k, j);
        }
    }

    public static Solution GetSolution(int[] index, int[] s, int[] f, int[] v, int i, int j)
    {
       //GREEDY-ACTIVITY-SELECTOR(s,f) 
        ActivityComparator comp = new ActivityComparator();
        TreeSet<Activity> A = new TreeSet<Activity>(comp);
        int value=v[i];
        int n=j+1;
       
        A.add(new Activity(index[i], s[i], f[i], v[i]));
        int k=i; 
        for(int m=i+1; m<n; m++)
        {
            if(s[m]>= f[k])
            {
                A.add(new Activity(index[m], s[m], f[m], v[m]));
                value+=v[m];
                k=m;
            }
        }
        Solution solution = new Solution(value,A);
        return solution;
    }

    
}

class Solution
{
    public int v;
    public TreeSet<Activity> A;
    Solution(int v, TreeSet<Activity> A)
    {
        this.v = v;
        this.A = A;
    }
}

//compare by id
class ActivityComparator implements Comparator<Activity>
{
	public int compare(Activity A, Activity B)
	{
        if(A.i>B.i) 
            return 1;
        else if(A.i<B.i) 
            return -1;
        else
            if(A.s!=B.s||A.f!=B.f||A.v!=B.v) return 1;
        return 0;
	}
}  

//compare by finishtime
class SorterByFinishTime implements Comparator<Activity>
{
	public int compare(Activity A, Activity B)
	{
        if(A.f>B.f) 
            return 1;
        else if(A.f<B.f) 
            return -1;
        else
            if(A.s!=B.s||A.i!=B.i||A.v!=B.v) return 1;
        return 0;
	}
} 

//compare by start time
class SolutionComparator implements Comparator<Solution>
{
	public int compare(Solution S1, Solution S2)
	{
        int result=0;
        Iterator<Activity> ita = S1.A.iterator();
        Iterator<Activity> itb = S2.A.iterator();
        
        Activity a = ita.next();
        Activity b = itb.next();
        if(a.i>b.i)
            return 1;
        else if(a.i<b.i)
            return -1;
        else
        {
            if(S1.A.size()!=S2.A.size()) 
            {
                result = 1;
            }
            else
            {
                while(ita.hasNext()&&itb.hasNext())
                {
                    a = ita.next();
                    b = itb.next();
                    if(a.i>b.i)
                        result = 1;
                    else if(a.i<b.i)
                        result = -1;
                    else
                        if(a.s!=b.s||a.f!=b.f||a.v!=b.v) result = 1;

                }
            }
        }
      
        return result;
	}
}  

