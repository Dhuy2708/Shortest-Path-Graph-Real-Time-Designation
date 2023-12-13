package Shared.Model;

import java.io.Serializable;

//model dùng để truyền tin giữa client và server
public class DataExchangeModel implements Serializable{
    private Graph graph;

    public DataExchangeModel(){
        graph = new Graph();
    }

    public void setGraphData(Graph graph){
        this.graph = graph;
    }

    public Graph getGraphData(){
        return this.graph;
    }
}