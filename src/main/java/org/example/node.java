package org.example;

import java.util.ArrayList;

public class node {
    int data;
    ArrayList<node> neighbours;
    Boolean visited ;
    public node(int data){
        this.data = data;
        this.neighbours=new ArrayList<>();
        this.visited = false;
    }
    public void connectTo(node x){
        this.neighbours.add(x);
        x.neighbours.add(this);
    }
}
