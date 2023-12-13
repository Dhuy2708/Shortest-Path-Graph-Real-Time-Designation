package Shared.Model;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Random;

public class Node implements Serializable {
    private String name;
    private Point point;
    private int distance = Integer.MAX_VALUE;
    private boolean isPermanent = false;
    private Node previousNode = null;

    public Node(){
        point = new Point();
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

    public void setDefault(){
        this.distance = Integer.MAX_VALUE;
        this.isPermanent = false;
        this.previousNode = null;
    }
    
    public void setRandomNode(Rectangle rec, String name){
        Random random = new Random();

        int randX = random.nextInt((int)rec.getWidth()) + rec.x;
        int randY = random.nextInt((int)rec.getHeight()) + rec.y;

        this.point.x = randX;
        this.point.y = randY;
        this.name = name;
    }
    
}
