public ArrayList<Vertix> DFS_VISIT(int start, int goal)
    {
        ArrayList<Vertix> pathList = new ArrayList<>();
        Stack<Vertix> stack = new Stack<Vertix>();

        Vertix init = V[start];
        stack.push(init);

        while(stack.empty() != true)
        {
            Vertix u = stack.peek();
            // System.out.print("\nu " + u.id);
            if(u.color == Color.WHITE)
            {
                time++;
                u.d = time;
                u.color = Color.GRAY;
                // System.out.print("\nu: "+u.id);
                pathList.add(u);
                ArrayList<Integer> list = adj[u.id-1];
                for(int i=0; i<list.size(); i++)
                {
                    int v_index = list.get(i);
                    Vertix v = V[v_index];
                    // System.out.print(" v " + v.id);
                    if(v.color == Color.WHITE)
                    {
                        v.p = u;
                        stack.push(v);
                    }
                    else 
                    {
                        if(v.id == goal+1)
                        {
                            pathList.add(v);
                            return pathList;
                        }
                        
                    }
                    
                }
            }
            else if(u.color == Color.GRAY)
            {
                
                    
                time++;
                u.f = time;
                u.color = Color.BLACK;
                stack.pop();
            }
        }
        return null;

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