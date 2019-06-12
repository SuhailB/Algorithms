def heapify(arr, n, i): 
    largest = i # Initialize largest as root 
    l = 2*i+1      # left = 2*i + 1 
    r = 2*i+2     # right = 2*i + 2 
  
    # See if left child of root exists and is 
    # greater than root 
    if l < n and arr[i] < arr[l]: 
        largest = l 
  
    # See if right child of root exists and is 
    # greater than root 
    if r < n and arr[largest] < arr[r]: 
        largest = r 
  
    # Change root, if needed 
    if largest != i: 
        arr[i],arr[largest] = arr[largest],arr[i] # swap 
  
        # Heapify the root. 
        heapify(arr, n, largest) 


arr = [30,20,10,40,8,4,9,18,16,38,32,39,50]
heapify(arr,13,5)
heapify(arr,13,4)
heapify(arr,13,3)
heapify(arr,13,2)
heapify(arr,13,1)
heapify(arr,13,0) 
print(arr)
