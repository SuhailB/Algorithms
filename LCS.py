import numpy as np

def LCS_length(X,Y):
    
    m = len(X)+1
    n = len(Y)+1
    
    c = np.zeros((m,n))
    
    for i in range(m):
        c[i][0]=0
    for j in range(n):
        c[0][j]=0
        
    for i in range(1,m):
        for j in range(1,n):
            if X[i-1] == Y[j-1]:
                c[i][j] = c[i-1][j-1]+1
            else:
                c[i][j]=max(c[i][j-1],c[i-1][j])
    return c


X = ['B','D','C','A','B','A']
Y = ['A','B','C','B','D','A','B']


def print_LCS(c,X,Y,i,j):
    
    if(c[i,j]==0):
        return
    if(X[i-1]==Y[j-1]):
        print_LCS(c,X,Y,i-1,j-1)
        print(X[i-1])
    elif c[i-1,j]>c[i,j-1]:
        print_LCS(c,X,Y,i-1,j)
    else:
        print_LCS(c,X,Y,i,j-1)
        
print(LCS_length(Y,X))

print_LCS(LCS_length(Y,X),Y, X,len(Y),len(X))