package Shared.Model;

import java.awt.Point;

//đối tượng node tạm thời dùng để vẽ node cho panel
public class NodeInfo {
    private Point point;
    private int index;

    //0: not selected, 1: start node selected, 2: end node selected
    private int orderSelected = 0;

    public NodeInfo(){

    }

    public NodeInfo(Point point, int index) {
        this.point = point;
        this.index = index;
    }

    public Point getPoint() {
        return point;
    }

    public int getIndex() {
        return index;
    }

    public void setOrder(int orderSelected){
        this.orderSelected = orderSelected;
    }

    public int getOrder(){
        return this.orderSelected;
    }

}
