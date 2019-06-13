
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
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

	public static void main(String[] args) {

        ActivityComparator comp = new ActivityComparator();
        TreeSet<Activity> Set1 = new TreeSet<Activity>(comp);
        int I[], S[], F[], V[];
        int n1, I1;
        File input1 = new File("input2.txt");
        try
        {
            Scanner fileScan = new Scanner(input1);
            n1 = fileScan.nextInt();
            I1 = fileScan.nextInt();
            I = new int[n1];
            S = new int[n1];
            F = new int[n1];
            V = new int[n1];
            // S[0] = 0;
            // F[0] = 0;
            // V[0] = 0;
            // S[n1+1] = 1000000;
            // F[n1+1] = 2000000;
            // V[n1+1] = 0;

            for(int k=0; k<n1; k++)
            {
                int i = fileScan.nextInt();
                int s = fileScan.nextInt();
                int f = fileScan.nextInt();
                int v = fileScan.nextInt();
                I[k] = i;
                S[k] = s;
                F[k] = f;
                V[k] = v;

                Activity a = new Activity(i,s,f,v);
                Set1.add(a);
            }
            // for(int i=0; i<n1+2; i++)
            // {
            //     System.out.println(S[i]+" "+F[i]);
            // }
            // printSet(Set1);
            // int value = getVal(I,S,F,V,3,4);
            // System.out.println(value);
            Set<Activity> A = Greedy(I,S,F,V);
            System.out.println();
            // printSet(A);
            // TreeSet<Activity> set = getSet(I,S,F,V,0,4);
            // printSet(set);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Input file not found");
        }
        int n=5;
        // int val[][] = new int[n+1][n+1];
        // System.out.println(val[0][0]);

        
    }
    
    public static TreeSet<Activity> Greedy(int[] index, int[] s, int[] f, int[] v)
    {
        ActivityComparator ActComp = new ActivityComparator();
        TreeSet<Activity> A = new TreeSet<Activity>(ActComp);
        SetComparator Setcomp = new SetComparator();
        int n = s.length;
        // System.out.println(n);
        int val[][] = new int[n][n];

        // for(int i=0; i<n; i++)
        // {
        //     val[i][i] = v[i];
        // }
        Set<Set<Activity>> optimalSolutions = new TreeSet<Set<Activity>>(Setcomp);
        // print2DArray(val);
        // Set<Activity> act[][] = (Set<Activity>[][]) new Object[n][n];
        // for(int i=0; i<n; i++)
        // {
        //     for(int j=0; j<n; j++)
        //     {
        //         act[i][j] = new TreeSet<Activity>();
        //     }
        // }
        int maxValue = 0;
        for(int l=0; l<n; l++)
        {
            for(int i=0; i<n-l; i++)
            {
                int j=i+l;
                // System.out.println("Difference: "+l+" ("+i+","+j+")");
                // int value = getVal(index,s,f,v,i,j);
                Solution solution = GetSolution(index,s,f,v,i,j);
                if(solution.v>maxValue)
                {
                    optimalSolutions.clear();
                    val[i][j] = solution.v;
                    maxValue = solution.v;
                    // System.out.println();
                    // printSet(solution.A);
                    // System.out.println();
                    optimalSolutions.add(solution.A);
                }
                else if(solution.v==maxValue)
                {
                    val[i][j] = solution.v;
                    maxValue = solution.v;
                    // System.out.println();
                    // printSet(solution.A);
                    // System.out.println();
                    optimalSolutions.add(solution.A);
                }
            }
        }

        System.out.println(optimalSolutions.size());
        for(Iterator<Set<Activity>> it = optimalSolutions.iterator(); it.hasNext();)
        {
            Set<Activity> a = it.next();
            printSet(a);
            System.out.println();
        }
        // System.out.println(val[0][2]);
        // System.out.println(val[1][3]);
        // System.out.println(val[2][4]);
        // System.out.println(val[3][5]);
        // System.out.println(val[4][6]);
        // for(int l=2; l<n; l++)
        // {
        //     for(int i=0; i<n-l; i++)
        //     {
        //         int j=i+l;
        //         // val[i][j]=0;
        //         int k=j-1;
        //         System.out.println("Difference: "+l+" ("+i+","+j+")"+" k = "+k+" val[i][k] "+val[i][k]+" val[k][j] "+val[k][j]+" v[k] "+v[k]);
        //         while(f[i]<f[k])
        //         {
        //             if(f[i]<=s[k] && f[k]<=s[j] && val[i][k]+val[k][j]+v[k]>val[i][j])
        //             {
                        
        //                 val[i][j] = val[i][k]+val[k][j]+v[k];
        //                 act[i][j] = k;
        //                 // System.out.println(val[i][j]);
        //                 // A.add(new Activity(index[k],s[k],f[k],v[k]));
        //             }
        //             k--;
        //         }
        //     }
        // }
        print2DArray(val);
        System.out.println();
        // print2DArray(act);
       
        // printActivities(val, act, 0, 6);
        return (TreeSet<Activity>)A;
    }

    public static void printSet(Set<Activity> set)
    {
        for(Iterator<Activity> it = set.iterator(); it.hasNext();)
        {
            Activity a = it.next();
            System.out.println(a.i+" "+a.s+" "+a.f+" "+a.v);
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

    public static int getVal(int[] index, int[] s, int[] f, int[] v, int i, int j)
    {
        ActivityComparator comp = new ActivityComparator();
        TreeSet<Activity> A = new TreeSet<Activity>(comp);
        int value=v[i];
        int n=j+1;
        A.add(new Activity(index[i], s[i], f[i], v[i]));
        int k=i; 
        for(int m=1; m<n; m++)
        {
            if(s[m]>= f[k])
            {
                A.add(new Activity(index[m], s[m], f[m], v[m]));
                value+=v[m];
                k=m;
            }
        }
        // printSet(A);
        return value;
    }
    public static TreeSet<Activity> getSet(int[] index, int[] s, int[] f, int[] v, int i, int j)
    {
        ActivityComparator comp = new ActivityComparator();
        TreeSet<Activity> A = new TreeSet<Activity>(comp);
        int value=v[i];
        int n=j+1;
        // System.out.println(index[i]+" "+s[i]+" "+f[i]+" "+v[i]);
        A.add(new Activity(index[i], s[i], f[i], v[i]));
        int k=i; 
        for(int m=1; m<n; m++)
        {
            if(s[m]>= f[k])
            {
                A.add(new Activity(index[m], s[m], f[m], v[m]));
                // System.out.println(index[m]+" "+s[m]+" "+f[m]+" "+v[m]);
                value+=v[m];
                k=m;
            }
        }
        
        return A;
    }

    public static Solution GetSolution(int[] index, int[] s, int[] f, int[] v, int i, int j)
    {
       
        ActivityComparator comp = new ActivityComparator();
        TreeSet<Activity> A = new TreeSet<Activity>(comp);
        int value=v[i];
        int n=j+1;
        // System.out.println(index[i]+" "+s[i]+" "+f[i]+" "+v[i]);
        A.add(new Activity(index[i], s[i], f[i], v[i]));
        int k=i; 
        for(int m=1; m<n; m++)
        {
            if(s[m]>= f[k])
            {
                A.add(new Activity(index[m], s[m], f[m], v[m]));
                // System.out.println(index[m]+" "+s[m]+" "+f[m]+" "+v[m]);
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

class ActivityComparator implements Comparator<Activity>
{
	public int compare(Activity A, Activity B)
	{
        if(A.i!=B.i||A.s!=B.s||A.f!=B.f||A.v!=B.v) return 1;
        else return 0;
	}
}  

class SetComparator implements Comparator<Set>
{
	public int compare(Set A, Set B)
	{
        Iterator<Activity> ita = A.iterator();
        Iterator<Activity> itb = B.iterator();
        
        if(A.size()!=B.size()) return 1;

		while(ita.hasNext()&&itb.hasNext())
        {
            Activity a = ita.next();
            Activity b = itb.next();
            if(a.i!=b.i||a.s!=b.s||a.f!=b.f||a.v!=b.v)
                return 1;
        }
        return 0;
	}
}  

// public String getText() {

//     String FileContent = new String();
    
//     try {
//     File file = new File(filename);
//     Scanner fileScan = new Scanner(file);
    
//     while(fileScan.hasNext()) 
//     {
//     String line = fileScan.nextLine();
//     FileContent = FileContent+line+"\n";
//     }
//     fileScan.close();
//     }
//     catch (FileNotFoundException e) {
//     System.out.println("File not found!"); 
//     return "File not found!";
//     }
//     return FileContent;
    
//     }

1095
{a1,a2}
IT HAS A UNIQUE SOLUTION
