package Server.Model;

import Shared.Model.Graph;

//Model của Server (gồm graph)
public class ServerModel {
    private Graph graph;

    public void setGraph(Graph graph){
        this.graph = graph;
    }

    public Graph getGraph(){
        return this.graph;
    }
}
