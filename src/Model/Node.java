package Model;

import java.awt.Point;

public class Node {
    private String name;
    private Point point;
    private int distance = Integer.MAX_VALUE;
    private boolean isPermanent = false;
    private Node previousNode = null;

    public Node(){

    }

    public Node(Point point , String name) {
        this.point = point;
        this.name = name;
    }

    public Node(String name) {
        this.name = name;
    }

    public Point getPoint(){
        return this.point;
    }

    public void setPoint(Point p){
        this.point = p;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getDistance(){
        return this.distance;
    }

    public void setDistance(int distance){
        this.distance = distance;
    }

    public boolean getIsPermanent(){
        return this.isPermanent;
    }

    public void setIsPermanent(boolean isPermanent){
        this.isPermanent = isPermanent;
    }

    public Node getPrevNode(){
        return this.previousNode;
    }

    public void setPrevNode(Node prevNode){
        this.previousNode = prevNode;
    }
    
}
