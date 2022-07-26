package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

//  get all loops
//  sort the loops by number of nodes (descending-ly)
//  check each loop -> is it a complete graph ?. if so -> deconstruct and generate all shit

public class graph {
    ArrayList<node> nodes;
    ArrayList<graph>loops;
    int edgesNum;

    public graph(){
        this.nodes=new ArrayList<node>();
        this.loops=new ArrayList<>();
        this.edgesNum = 0;
    }
    public graph(List<node> nodes){
        this.nodes=new ArrayList<node>();
        this.edgesNum = 0;
        for (node x:nodes) {
            node clone = new node(x.data);
            for (node child :x.neighbours) {
                if(nodes.contains(child)){
                    clone.neighbours.add(child);
                    this.edgesNum++;
                }
            }
            this.nodes.add(clone);
        }
        edgesNum=edgesNum/2;
    }
    public void getLoops(node current, ArrayList<node>path){
        for(int i = path.size()-3;i>-1;i--){
            if(path.get(i)==current){
                if(!loopAlreadyThere(path.subList(i, path.size()))) {
                    loops.add(new graph(path.subList(i, path.size())));
                }
                return;
            }
        }
        path.add(current);
        current.visited = true;
        for (node child:current.neighbours) {
            if(path.size()==1||child!=path.get(path.size()-2)){
                ArrayList<node> branch = new ArrayList<node>(path);
                getLoops(child,branch);
            }
        }
    }

    private boolean loopAlreadyThere(List<node> l) {
        for (graph loop:loops) {
            Boolean duplicate = true;
            if(l.size()!=loop.nodes.size()){
                duplicate = false;
            }
            else {
                for (node n : loop.nodes) {
                    Boolean contains = false;
                    for (node i:l){
                        if(i.data==n.data) {
                            contains = true;
                        }
                    }
                    if (!contains) {
                        duplicate = false;
                    }
                }
            }
            if(duplicate){
                return true;
            }
        }
        return false;
    }

    public boolean isComplete(){
        return 2 * edgesNum == nodes.size() * (nodes.size()-1);
    }
    //INPUT FORMAT :

    // number_of_nodes number_of_edges
    // node1
    // node2
    // node3
    // ...
    // node1 node2
    // node3 node7
    // node2 node3
    // ...

    public static void main(String[]args){
        Scanner s = new Scanner(System.in);
        String[] ne = s.nextLine().split(" ");
        int n = Integer.parseInt(ne[0]);
        int e = Integer.parseInt(ne[1]);

        graph g = new graph();
        HashMap<Integer,node> m = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int node_data = Integer.parseInt(s.nextLine());
            node node = new node(node_data);
            g.nodes.add(node);
            m.put(node_data,node);
        }
        for (int i = 0; i < e; i++) {
            String[] edge = s.nextLine().split(" ");
            int node1 = Integer.parseInt(edge[0]);
            int node2 = Integer.parseInt(edge[1]);
            m.get(node1).connectTo(m.get(node2));
            g.edgesNum++;
        }
        g.getLoops(g.nodes.get(0),new ArrayList<node>());
        for (node x: g.nodes){
            if(!x.visited){
                g.getLoops(x,new ArrayList<node>());
            }
        }
        int complete=0;
        for (graph loop:g.loops){
            if(loop.isComplete()){
                complete++;
            }
        }

        //OUTPUT FORMAT

        //number of loops
        //number of complete subgraphs

        for (graph loop:g.loops) {
            System.out.println("loop:");
            for (node y:loop.nodes) {
                System.out.print(y.data+" ");
            }
            System.out.println("");
        }
        System.out.println(g.loops.size());
        System.out.println(complete);
    }
}
