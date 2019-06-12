public class Main
{
    static class Matrix{
        int r;
        int c;
        String n;
        Matrix(int r, int c, String n)
        {
            this.r = r; this.c = c; this.n = n;
        }
    };
	public static void main(String[] args) {

        Matrix [] chain = {new Matrix(1,2,"A1"), new Matrix(2,4,"A2"), new Matrix(4,10,"A3")};//, new Matrix(12,5,"A4"), new Matrix(5,50,"A5"), new Matrix(50,6,"A6")};
        int s[][] = Matrix_Chain_Order(chain);
        // print2DArray(s);
        // printSolution(chain, s, 0, 5);
        
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

    // public Matrix MATRIX_CHAIN_MULTIPLY(Matrix [] A, int[][] s, int i, int j)
    // {
        // int k = s[i][j];
        // if(i==j) { return A[i]; }
        // System.out.print("(");
        // Matrix A = MATRIX_CHAIN_MULTIPLY(A,s,i,k);
        // Matrix B = MATRIX_CHAIN_MULTIPLY(chain,s,k+1,j);
        // return AxB;
        // System.out.print(")");
        // MATRIX_MULTIPLY(A,B);
    // }

    public static void printSolution(Matrix[] chain,int [][] s, int i, int j)
    {
        int k = s[i][j];
        if(i==j) { System.out.print(chain[i].n); return; }
        System.out.print("(");
        printSolution(chain,s,i,k);
        printSolution(chain,s,k+1,j);
        System.out.print(")");
    }

    public static int[][] Matrix_Chain_Order(Matrix [] p)
    {
        int n = p.length;
        int m[][] = new int[n][n];
        int s[][] = new int[n][n];

        for(int l=1; l<n; l++)
        {
            for(int i=0; i<n-l;i++)
            {
                int j=i+l;
                m[i][j] = 100000000;
                for(int k=i; k<j; k++)
                {
					System.out.println(i+" "+j+" "+k);
                    int q=m[i][k]+m[k+1][j]+p[i].r*p[k].c*p[j].c;
                    // System.out.println(p[i].r+" "+p[k].c+" "+p[j].c);
                    if(q<m[i][j])
                    {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
        return m;
    }
}