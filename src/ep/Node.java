package ep;

import java.util.ArrayList;

/**
 * Created by Barbara on 3/30/2015.
 */
public class Node {
    String label, ID;
    ArrayList<Integer> features = new ArrayList<Integer>();
    ArrayList<Integer> previous = new ArrayList<Integer>();
    int from, startNext;

    public Node(String l) {
        label = l;
    }
}
