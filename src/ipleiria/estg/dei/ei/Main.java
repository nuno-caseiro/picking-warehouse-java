package ipleiria.estg.dei.ei;

import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.search.AStar;
import ipleiria.estg.dei.ei.model.search.Node;
import ipleiria.estg.dei.ei.model.search.State;
import java.util.List;

public class Main {

    public Main(){
        int[][] matrix = {
                {0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};

        Node start = new Node(new State(0,0));
        Node end = new Node(new State(0,15));

        Environment.getInstance().setMatrix(matrix);

        AStar aStar = new AStar();

        List<State> solution = aStar.search(start, end);
        System.out.println(solution);
    }

    public static void main(String[] args) {
        new Main();
    }
}
