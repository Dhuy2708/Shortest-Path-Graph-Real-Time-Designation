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
        
        this.addNode(to);
        this.addNode(from);
        if(!adjacencyList.get(from).containsKey(to))
            adjacencyList.get(from).put(to, weight);
        if(!adjacencyList.get(to).containsKey(from))
            adjacencyList.get(to).put(from,weight);

    }

    public Map<Node, Integer> getNeighbors(Node node){
        return this.adjacencyList.get(node);
    }

    public Node getNodeByName(String name){
        Node nodeToReturn = new Node();
        Boolean isExist = false;
        for(Map.Entry<Node, Map<Node, Integer>> node : this.adjacencyList.entrySet()){
            if(node.getKey().getName() == name){
                nodeToReturn = node.getKey();
                isExist = true;
                break;
            }
        }
        if(isExist == true) return nodeToReturn;
        else return null;
    }

    public Boolean isNodeNameExist(String name){
        Boolean isExist = false;
        for(Map.Entry<Node, Map<Node, Integer>> node : this.adjacencyList.entrySet()){
            if(node.getKey().getName() == name){
                isExist = true;
                break;
            }
        }
        return isExist;
    }

}
