package Shared.Helper;

import java.awt.Point;
import java.util.Map;

import Client.View.InputForm;
import Shared.Model.Graph;
import Shared.Model.Node;

public class StringExcute {
    public static void main(String[] args){
        StringExcute excute = new StringExcute();

        Graph graph = new Graph();

        excute.translateString(graph, "new 1 300 300");
        excute.translateString(graph, "new 2 400 400");

        // Node startNode = new Node();
        // Node enNode = new Node();
        

        excute.translateString(graph, "connect 1 2");
        //excute.translateString(graph, "delete 1");

        InputForm frame = new InputForm();
        frame.setGraph(graph);

        frame.displayForm();

        System.out.println(excute.exportString(graph));
    }

    public void translateString(Graph graph, String message){
        String[] arr = message.split(" ");

        switch(arr[0]){
            //new 'point_name' + 'x' + 'y'
            case "new":
                Point point = new Point(Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
                Node node = new Node(point, arr[1]);

                graph.addNode(node);
                break;

            //delete 'point_name'
            case "delete":
                Node nodeToDelete = new Node();

                //duyệt qua các node để lấy ra node cần xóa
                for(Map.Entry<Node, Map<Node, Integer>> nodeInGraph0 : graph.getAdjacencyList().entrySet()){
                    if(nodeInGraph0.getKey().getName().equals(arr[1])){
                        nodeToDelete = nodeInGraph0.getKey();
                        break;
                    }
                }
            
                graph.removeNode(nodeToDelete);
                break;

            //connect 'point_name' + 'point_name'
            case "connect":
                Node startNode = new Node(), endNode = new Node();

                //duyệt qua các node để lấy ra 2 node cần tạo liên kết
                for(Map.Entry<Node, Map<Node, Integer>> nodeInGraph : graph.getAdjacencyList().entrySet()){
                    if(nodeInGraph.getKey().getName().equals(arr[1])){
                        startNode = nodeInGraph.getKey();
                    }
                    else if(nodeInGraph.getKey().getName().equals(arr[2])){
                        endNode = nodeInGraph.getKey();
                    }   
                }
                graph.addEdge(startNode, endNode, (int)startNode.getPoint().distance(endNode.getPoint()));

                break;
        }
    }

    public String exportString(Graph graph){
        StringBuilder strBuilder = new StringBuilder("");

        for(Map.Entry<Node, Map<Node, Integer>> node : graph.getAdjacencyList().entrySet()){
            Node tmp = node.getKey();
            strBuilder.append("new " + tmp.getName() + " " + (int)tmp.getPoint().getX() + " " + (int)tmp.getPoint().getY() + "\n");
        }

        for(Map.Entry<Node, Map<Node, Integer>> node : graph.getAdjacencyList().entrySet()){
            Node tmp = node.getKey();
            for(Map.Entry<Node, Integer> innerNode : node.getValue().entrySet()){
                Node innerNodeTmp = innerNode.getKey();
                strBuilder.append("connect " + tmp.getName() + " " + innerNodeTmp.getName() + "\n");
            }
        }

        return strBuilder.toString();
    }
}

