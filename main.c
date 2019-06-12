#include<stdlib.h>
#include<stdio.h>

 #define max(a,b) \
   ({ __typeof__ (a) _a = (a); \
       __typeof__ (b) _b = (b); \
     _a > _b ? _a : _b; })

int cut_rod(int* prices, int n)
{
    if(n==0) return 0;

    int optimal_cut_price = -1000000;
    int i; 

    for(i=0; i<n; i++)
    {
        if(optimal_cut_price<(prices[i]+cut_rod(prices,n-i-1)))
            optimal_cut_price = prices[i]+cut_rod(prices,n-i-1);
    }
    return optimal_cut_price;
}

int memoized_cut_rod(int* prices, int n)
{
    int r[n];
    int i;
    for(i=0; i<n; i++)
    {
        r[i] = -1000000;
    }
    return memoized_aux(prices,n,r);
}

int memoized_aux(int* prices, int n, int* r)
{
    if(r[n-1]>=0)
        return r[n-1];
    int optimal;
    if (n==0)
        return 0;
    else
    {
        optimal = -1000000;
        int i;
        for(i=0; i<n; i++)
        {
            if(optimal<(prices[i]+memoized_aux(prices,n-i-1,r)))
                optimal = prices[i]+memoized_aux(prices,n-i-1,r);
        }
    }
    r[n-1] = optimal;
    return optimal;
        
}

int buttom_up_cut_rod(int* prices, int n)
{
    int r[n+1];
    r[0]=0;
    int optimal;
    for(int i=1; i<n+1; i++)
    {
        optimal = -1000000;
        for(int j=0; j<i; j++)
        {
            optimal = max(optimal, prices[j]+r[i-j-1]);
        }
        r[i] = optimal;
    }
    return r[n];
}
int main()
{
    int prices[10]={1,5,8,9,10,17,17,20,24,30};
    int i=0; 
    for(;i<11; i++)
    {
        int optimal_cut_price = buttom_up_cut_rod(prices,i);
        printf("r%d = %d\n",i,optimal_cut_price);
    }
    
    return 0;
}