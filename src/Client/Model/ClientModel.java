package Client.Model;

import Shared.Model.Graph;


//hiện tại model chỉ có data là graph
public class ClientModel {
    private Graph graph;

    public void setGraphData(Graph graph){
        this.graph = graph;
    }

    public Graph getGraph(){
        return this.graph;
    }
}
