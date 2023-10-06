package Model;


public class Node {
    private String name;
    private int distance = Integer.MAX_VALUE;
    private boolean isPermanent = false;
    private Node previousNode = null;

    public Node(String name) {
        this.name = name;
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
