package Model;

import java.util.HashMap;
import java.util.Map;

public class Graph {
    Map<Node, Map<Node, Integer>> adjacencyList = new HashMap<>();

    public Map<Node, Map<Node, Integer>> getAdjacencyList(){
        return this.adjacencyList;
    }

    public void addNode(Node node) {
        if (!adjacencyList.containsKey(node))
            adjacencyList.put(node, new HashMap<>());
    }

    public void addEdge(Node from, Node to, int weight) {
        this.addNode(from);
        addNode(to);
        adjacencyList.get(from).put(to, weight);
    }
}
