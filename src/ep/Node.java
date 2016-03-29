package ep;

import java.util.ArrayList;

public class Node {
    String label, ID;
    ArrayList<Integer> features = new ArrayList<Integer>();
    ArrayList<Integer> previous = new ArrayList<Integer>();
    int from, startNext;

    public Node(String l) {
        label = l;
    }
}
