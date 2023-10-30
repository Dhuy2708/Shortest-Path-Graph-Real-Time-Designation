package Model;

import java.awt.List;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
            if(node.getKey().getName().equals(name)){
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
            if(node.getKey().getName().equals(name)){
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    public void resetGraph(){
        for(Map.Entry<Node, Map<Node, Integer>> node : this.adjacencyList.entrySet()){
            node.getKey().setDefault();
        }
    }

    public void setRandomGraph(Rectangle rec , int nodeQuanity){

        ArrayList<Node> listNodes = new ArrayList<>();

        this.adjacencyList.clear();

        for(int i = 1; i <= nodeQuanity; i++){
            Node tmp = new Node();
            tmp.setRandomNode(rec, Integer.toString(i));

            listNodes.add(tmp);
            this.addNode(tmp);
        }

        Random random = new Random();

        //duyet tung node trong graph de them ngau nhien canh
        for(Map.Entry<Node, Map<Node, Integer>> node : this.adjacencyList.entrySet()){
            int edgeAmountOfNode = random.nextInt(listNodes.size() - 1);    //so luong canh ngau nhien

            //them canh ngau nhien
            for(Node innerNode : RandomNodeSelector.GetRandomNodes(listNodes, edgeAmountOfNode)){
                int weight = (int)node.getKey().getPoint().distance(innerNode.getPoint());
                this.addEdge(node.getKey(), innerNode, weight);
            }
        }
    }

    public static void main(String[] args){
        Rectangle rec = new Rectangle(0, 0 , 800, 600);
        
        Graph graph = new Graph();
        try{
                graph.setRandomGraph(rec, 8);
        
                for(Map.Entry<Node, Map<Node, Integer>> node : graph.adjacencyList.entrySet()){
                    for(Map.Entry<Node, Integer> innerNode : graph.getNeighbors(node.getKey()).entrySet()){
                        System.out.println(node.getKey().getName() + "->" + innerNode.getKey().getName() + " (" + innerNode.getValue() + ")");
                    }
                    System.out.println("\n");
                }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}

class RandomNodeSelector {
    public static ArrayList<Node> GetRandomNodes(ArrayList<Node> list, int k) {
        ArrayList<Node> randomNodes = new ArrayList<>();
        Random random = new Random();

        int n = list.size(); // Số lượng Node trong danh sách

        if (k > n) {
            // Tránh lấy nhiều hơn số lượng Node có sẵn
            k = n;
        }

        for (int i = 0; i < k; i++) {
            // Sinh một chỉ số ngẫu nhiên trong khoảng từ 0 đến n - 1
            int randomIndex = random.nextInt(n);

            // Lấy Node tương ứng với chỉ số ngẫu nhiên và loại bỏ nó khỏi danh sách
            Node randomNode = list.remove(randomIndex);
            randomNodes.add(randomNode);

            n--; // Giảm số lượng Node còn lại
        }

        return randomNodes;
    }
}