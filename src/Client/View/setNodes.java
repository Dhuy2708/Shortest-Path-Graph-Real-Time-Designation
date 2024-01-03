package Client.View;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JOptionPane;

import Shared.Helper.GraphShortestPath;
import Shared.Model.*;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

//Panel để vẽ và thiết kế độ thị
public class setNodes extends Pane {
    // ======================================
    private Node startNode = null;
    private Node endNode = null;

    private String startNodeTF;
    private String endNodeTF;

    private String status; // ĐÂY LÀ TẤT CẢ (DỮ LIỆU MESSAGE ĐỂ GỬI QUA SERVER)

    private Graph graph = new Graph();
    private ArrayList<Node> shortestPath;

    // ==================================================

    // thuộc tính cho đồ họa
    private ArrayList<CircleWithText> circles = new ArrayList<>();
    private double scale = 1;
    private Circle selectedCircle = null;
    private double borderWidth = 2.5 * scale;
    private final int RADIUS = (int)(25 * scale);
    private final double MIN_DISTANCE = 2 * RADIUS; // Minimum distance required
    private double sizeFont = 16 * scale;
    private boolean isEditable = true;
    private Font customFont = Font.font("Arial", FontWeight.BOLD, sizeFont);

    private class CircleWithText extends Group {
        private Circle circle;
        private Text text;

        public CircleWithText(Circle circle, Text text) {
            this.circle = circle;
            this.text = text;

            getChildren().addAll(circle, text);
        }

        public Circle getCircle() {
            return circle;
        }

        public Text getText() {
            return text;
        }
    }

    public class ArrowLine extends Group {

        private Line line;
        private Polygon arrowhead;
        private Color arrowColor; // Thêm thuộc tính màu sắc
    
        private double arrowLength = 15;
        private double arrowWidth = 10;
        private double lineWidth = 3;
    
        public ArrowLine(double startX, double startY, double endX, double endY, Color arrowColor) {
            this.arrowColor = arrowColor; // Đặt màu sắc
    
            line = new Line(startX, startY, endX, endY);
            line.setStrokeWidth(lineWidth);
            line.setStroke(arrowColor);
            arrowhead = createArrowhead(endX, endY);
    
            getChildren().addAll(line, arrowhead);
        }
    
        public void setArrowColor(Color color) {
            this.arrowColor = color;
            arrowhead.setFill(color);
        }
    
        private Polygon createArrowhead(double endX, double endY) {
            arrowLength = 15;
            arrowWidth = 10;
    
            Polygon arrow = new Polygon();
            arrow.getPoints().addAll(
                    endX - arrowLength, endY - arrowWidth / 2,
                    endX, endY,
                    endX - arrowLength, endY + arrowWidth / 2);
            arrow.setFill(arrowColor); // Sử dụng màu sắc từ thuộc tính arrowColor
    
            // Tạo một phép biến đổi Rotate để xoay tam giác theo hướng của đường thẳng
            double angle = Math.atan2(endY - line.getStartY(), endX - line.getStartX());
            angle = Math.toDegrees(angle);
            arrow.getTransforms().add(new Rotate(angle, endX, endY));
    
            return arrow;
        }
    }

    public void isEditable(boolean param){
        isEditable = param;
    }

    public String getStatus() {
        return this.status;
    }

    public Graph getGraph() {
        return graph;
    }

    public void SetGraph(Graph graph) {
        this.graph = graph;
        draw();
    }

    public void setScale(double scale){
        this.scale = scale;
    }

    public String getStartNodeTF(){
        return startNodeTF;
    }

    public String getEndNodeTF(){
        return endNodeTF;
    }

    public setNodes() {
        shortestPath = new ArrayList<>();

        addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {

            if (event.getButton() == MouseButton.PRIMARY) {
                if (selectedCircle != null) {
                    selectedCircle.setStroke(Color.BLACK);
                    selectedCircle.setStrokeWidth(borderWidth);
                    selectedCircle = null;
                }
                if (startNode != null) {
                    startNode = null;
                }
                if (endNode != null) {
                    endNode = null;
                }

                // Check for collision with existing circles and minimum distance
                boolean canCreateCircle = true;
                for (CircleWithText existingCircle : this.getChildren().filtered(CircleWithText.class::isInstance)
                        .toArray(CircleWithText[]::new)) {
                    double distance = calculateDistance(existingCircle.getCircle().getCenterX(),
                            existingCircle.getCircle().getCenterY(), event.getX(), event.getY());
                    if (distance < MIN_DISTANCE) {

                        canCreateCircle = false;
                        if (distance < RADIUS) {
                            selectedCircle = existingCircle.getCircle();
                            existingCircle.circle.setStroke(Color.LIGHTGREEN);
                            existingCircle.circle.setStrokeWidth(borderWidth * 1.5);
                        }

                        // messageText.setText("Cannot create bc the distance is to close");
                        break;
                    }
                }

                if (canCreateCircle == true && isEditable == true) {
                    addNode(event.getX(), event.getY());

                    draw();

                    // =============================================

                } else if (!canCreateCircle) {
                    status = "";
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                
                    if (selectedCircle != null) {
                        selectedCircle.setStroke(Color.BLACK);
                        selectedCircle.setStrokeWidth(borderWidth);
                        selectedCircle = null;
                    }

                    Node clickedNode = findClickedNode(event.getX() / scale, event.getY() / scale);

                    if (clickedNode != null) {
                        if (startNode == null) {
                            status = "";
                            startNode = clickedNode;
                            startNodeTF = startNode.getName();
                        } else if (endNode == null && startNode != clickedNode) {
                            endNode = clickedNode;
                            endNodeTF = endNode.getName();

                            // Draw an arrow from selectedCircle1 to selectedCircle2
                            if(isEditable){

                                graph.addEdge(startNode, endNode, (int) startNode.getPoint().distance(endNode.getPoint()));
    
                                status = "connect " + startNode.getName() + " " + endNode.getName();
    
                                draw();
    
                                // Reset the selected circles
                            }
                            startNode = null;
                            endNode = null;
                        }
                    } else if (clickedNode == null) {
                        status = "";
                        startNode = null;
                        endNode = null;
                    }
                

            }
            System.out.println("start: " + startNodeTF + " , end: " + endNodeTF);
        });
    }

    public String findShortestPath(String start, String end){
        if(graph.isNodeNameExist(start) == false || graph.isNodeNameExist(end) == false){
            return "Cant find the node, please check the node name";
        }
        else{
            Node startNode = graph.getNodeByName(start);
            Node endNode = graph.getNodeByName(end);
            GraphShortestPath.shortestPath(graph, startNode);
            shortestPath = GraphShortestPath.getShortestPath(startNode, endNode);
            
            if(endNode.getDistance() < Integer.MAX_VALUE){
                draw();
    
                return "";
            }
            else{
                return "Cant find the path";
            }
        }
    }

    public void clearShortestPath(){
        shortestPath.clear();
    }

    public void draw() {
        deleteAllCirclesInPane();

        //draw edge
        for (Map.Entry<Node, Map<Node, Integer>> node : graph.getAdjacencyList().entrySet()) {
            for (Map.Entry<Node, Integer> innerNode : node.getValue().entrySet()) {
                drawArrow(node.getKey(), innerNode.getKey(), Color.BLACK);
            }

        }

        //draw guide path
        if(shortestPath.size() > 0){
            for(int i = 0; i < shortestPath.size() - 1; i++){
                drawArrow(shortestPath.get(i), shortestPath.get(i + 1), Color.LIGHTGREEN);
            }
        }

        // draw node
        for (Map.Entry<Node, Map<Node, Integer>> node : graph.getAdjacencyList().entrySet()) {
    
                drawCircle(node.getKey());

        }
    }

    public void deleteAllCirclesInPane() {
        // circles.clear();
        this.getChildren().removeIf(object -> {
            if (object instanceof Text) {
                return false;
            } else {
                return true;
            }
        });
    }

    public void deleteAllNodes() {
        graph.getAdjacencyList().clear();
        draw();

    }

    public void addNode(double x, double y) {
        // Node=======================================
        String nodeName = String.valueOf(graph.getAdjacencyList().size() + 1);

        Point point = new Point((int) x, (int) y);
        Node node = new Node(point, nodeName);
        // End node =================================

        status = "new " + node.getName() + " " + (int) node.getPoint().getX() + " " + (int) node.getPoint().getY();

        graph.addNode(node); // add node into graph
    }

    public void drawCircle(Node node) {
        double x = node.getPoint().getX() * scale;
        double y = node.getPoint().getY() * scale;


        Circle circle = new Circle(x, y, RADIUS, Color.rgb(243, 112, 32));
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(borderWidth);

        Text text = new Text(node.getName());
        text.setFont(customFont); // Set the custom font
        text.setFill(Color.WHITE); // Set text color

        double textX = x - text.getLayoutBounds().getWidth() * scale / 2 ; // Calculate X and Y positions to center the text
        double textY = y + text.getLayoutBounds().getHeight() * scale / 4 ;

        text.setX(textX);
        text.setY(textY);

        text.setMouseTransparent(true); // Make the text node transparent to mouse events

        circle.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                if (selectedCircle != null) {
                    selectedCircle.setStroke(Color.BLACK);
                    selectedCircle.setStrokeWidth(borderWidth);
                }
                selectedCircle = circle;
                circle.setStroke(Color.LIGHTGREEN);
                circle.setStrokeWidth(borderWidth * 1.5);
            }
        });
        CircleWithText circleWithText = new CircleWithText(circle, text);
        getChildren().addAll(circleWithText);
    }

    public void drawHighlightCircle(Node node){
        double x = node.getPoint().getX() * scale;
        double y = node.getPoint().getY() * scale;


        Circle circle = new Circle(x, y, RADIUS, Color.rgb(243, 112, 32));
        circle.setStroke(Color.MAGENTA);
        circle.setStrokeWidth(borderWidth * 1.5);

        Text text = new Text(node.getName());
        text.setFont(customFont); // Set the custom font
        text.setFill(Color.WHITE); // Set text color

        double textX = x - text.getLayoutBounds().getWidth() * scale / 2 ; // Calculate X and Y positions to center the text
        double textY = y + text.getLayoutBounds().getHeight() * scale / 4 ;

        text.setX(textX);
        text.setY(textY);

        text.setMouseTransparent(true); // Make the text node transparent to mouse events

        circle.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                if (selectedCircle != null) {
                    selectedCircle.setStroke(Color.BLACK);
                    selectedCircle.setStrokeWidth(borderWidth);
                }
                selectedCircle = circle;
                circle.setStroke(Color.LIGHTGREEN);
                circle.setStrokeWidth(borderWidth * 1.5);
            }
        });
        CircleWithText circleWithText = new CircleWithText(circle, text);
        getChildren().addAll(circleWithText);
    }

    private void drawArrow(Node startNode, Node endNode, Color color) {
        double startNodeX = startNode.getPoint().getX() * scale;
        double startNodeY = startNode.getPoint().getY() * scale;
        double endNodeX = endNode.getPoint().getX() * scale;
        double endNodeY = endNode.getPoint().getY() * scale;

        double angle = Math.atan((endNodeY - startNodeY) / (endNodeX - startNodeX));
        // if ((endNodeX - startNodeX) < 0 && (endNodeY - startNodeY) >= 0) {
        // angle += 180;
        // } else if ((endNodeX - startNodeX) < 0 && (endNodeY - startNodeY) < 0) {
        // angle -= 180;
        // } else if ((endNodeY - startNodeY) < 0) {
        // angle += 360;
        // }

        double startX = startNodeX + RADIUS * Math.cos(angle);
        double startY = startNodeY + RADIUS * Math.sin(angle);
        double endX = endNodeX - RADIUS * Math.cos(angle);
        double endY = endNodeY - RADIUS * Math.sin(angle);

        ArrowLine arrow = new ArrowLine(startX, startY, endX, endY, color);

        arrow.setMouseTransparent(true);

        getChildren().add(arrow);
    }

    // Calculate distance between two points
    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private Node findClickedNode(double x, double y) {
        Node rs = null;
        for (Map.Entry<Node, Map<Node, Integer>> node : this.graph.getAdjacencyList().entrySet()) {
            if (checkIfNodeIsSelected(node.getKey(), (int) x, (int) y)) {
                rs = node.getKey();
                break;
            }
        }

        return rs;
    }

    private boolean checkIfNodeIsSelected(Node node, int x, int y) {
        Point point = new Point(x, y);
        return point.distance(node.getPoint()) <= RADIUS;
    }

    // =============CÁC HÀM LẮNG NGHE SỰ KIỆN CỦA PANEL =======================
    public void addMouseClickListener(javafx.event.EventHandler<javafx.scene.input.MouseEvent> event) {
        this.setOnMousePressed(event);
    }

    public static void main(String[] args) {

    }

}
