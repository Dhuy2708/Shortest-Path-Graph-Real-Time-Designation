

import java.util.*;

public class TestAlgorithm {
    // Biểu diễn một đỉnh trong đồ thị
    private static class Node {
        String name;
        int distance = Integer.MAX_VALUE;
        boolean isPermanent = false;
        Node previousNode = null;

        public Node(String name) {
            this.name = name;
        }
    }

    // Biểu diễn đồ thị bằng danh sách kề
    private static class Graph {
        Map<Node, Map<Node, Integer>> adjacencyList = new HashMap<>();

        public void addNode(Node node) {
            if (!adjacencyList.containsKey(node))
                adjacencyList.put(node, new HashMap<>());
        }

        public void addEdge(Node from, Node to, int weight) {
            addNode(from);
            addNode(to);
            adjacencyList.get(from).put(to, weight);
        }
    }

    // Thuật toán định đường ngắn nhất
    public static void shortestPath(Graph graph, Node source) {
        source.distance = 0;

        while (true) {
            Node currentNode = null;
            int currentMinDistance = Integer.MAX_VALUE;

            for (Node node : graph.adjacencyList.keySet()) {
                if (!node.isPermanent && node.distance < currentMinDistance) {
                    currentNode = node;
                    currentMinDistance = node.distance;
                }
            }

            if (currentNode == null)
                break;

            currentNode.isPermanent = true;

            for (Map.Entry<Node, Integer> neighbor : graph.adjacencyList.get(currentNode).entrySet()) {
                int distance = currentNode.distance + neighbor.getValue();
                if (distance < neighbor.getKey().distance) {
                    neighbor.getKey().distance = distance;
                    neighbor.getKey().previousNode = currentNode;
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
            current = current.previousNode;
        }

        return path;
    }

    public static void main(String[] args) {
        // Tạo đồ thị
        Graph graph = new Graph();
        Node node1 = new Node("1");
        Node node2 = new Node("2");
        Node node3 = new Node("3");
        Node node4 = new Node("4");
        Node node5 = new Node("5");
        Node node6 = new Node("6");

        graph.addEdge(node1, node2, 4);
        graph.addEdge(node1, node6, 2);
        graph.addEdge(node2, node3, 3);
        graph.addEdge(node2, node5, 3);
        graph.addEdge(node3, node4, 2);
        graph.addEdge(node4, node5, 1);
        graph.addEdge(node5, node6, 3);

        // Chọn nút nguồn
        Node sourceNode = node1;

        // Thực hiện thuật toán định đường ngắn nhất
        shortestPath(graph, sourceNode);

        // Đỉnh đích
        Node destinationNode = node4;

        // Tìm đường đi ngắn nhất và in kết quả
        List<Node> shortestPath = getShortestPath(sourceNode, destinationNode);
        int shortestDistance = destinationNode.distance;

        System.out.println("Đường đi ngắn nhất từ " + sourceNode.name + " đến " + destinationNode.name + ":");
        for (Node node : shortestPath) {
            System.out.print(node.name + " -> ");
        }
        System.out.println("Khoảng cách ngắn nhất: " + shortestDistance);
    }
}
