package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Model.Graph;
import Model.*;

public class GraphController {
    // Thuật toán định đường ngắn nhất
    public static void shortestPath(Graph graph, Node source) {
        source.setDistance(0);

        while (true) {
            Node currentNode = null;
            int currentMinDistance = Integer.MAX_VALUE;

            for (Node node : graph.getAdjacencyList().keySet()) {
                if (!node.getIsPermanent() && node.getDistance() < currentMinDistance) {
                    currentNode = node;
                    currentMinDistance = node.getDistance();
                }
            }

            if (currentNode == null)
                break;

            currentNode.setIsPermanent(true);

            for (Map.Entry<Node, Integer> neighbor : graph.getAdjacencyList().get(currentNode).entrySet()) {
                int distance = currentNode.getDistance() + neighbor.getValue();
                if (distance < neighbor.getKey().getDistance()) {
                    neighbor.getKey().setDistance(distance);
                    neighbor.getKey().setPrevNode(currentNode);
                }
            }
        }
    }

    // Trả về đường đi từ nút nguồn đến nút đích
    public static List<Node> getShortestPath(Node source, Node destination) {
        List<Node> path = new ArrayList<>();
        Node current = destination;

        while (current != null) {
            path.add(0, current);
            current = current.getPrevNode();
        }

        return path;
    }
}
