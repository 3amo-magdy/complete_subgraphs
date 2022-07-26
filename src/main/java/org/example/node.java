package org.example;

import java.util.ArrayList;

public class node {
    String node_name;
    ArrayList<node> neighbours;
    Boolean visited ;
    public node(String data){
        this.node_name = data;
        this.neighbours=new ArrayList<>();
        this.visited = false;
    }
    public void connectTo(node x){
        this.neighbours.add(x);
        x.neighbours.add(this);
    }
}
