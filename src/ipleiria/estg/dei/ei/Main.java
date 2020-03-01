package ipleiria.estg.dei.ei;

import ipleiria.estg.dei.ei.model.search.Node;
import ipleiria.estg.dei.ei.model.search.State;

import java.util.PriorityQueue;

public class Main {

    public Main(){
        int[][] matrix = {
                {0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0}};

        State start = new State(0,0);
        State end = new State(0,16);

        Node a = new Node(null, null, 0);
        Node b = new Node(null, null, 2);
        Node c = new Node(null, null, 3);
        Node d = new Node(null, null, 5);
        Node e = new Node(null, null, 7);
        Node f = new Node(null, null, 11);

        PriorityQueue<Node> queue = new PriorityQueue<>();

        queue.add(a);
        queue.add(b);
        queue.add(c);
        queue.add(d);
        queue.add(e);
        queue.add(f);

        while (!queue.isEmpty()){
            System.out.println(queue.poll().getF());
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
