import java.awt.Point;
import java.net.URL;
import java.util.ResourceBundle;

import Client.Controller.ClientController;
import Client.View.setNodes;
import Shared.Model.Graph;
import Shared.Model.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private Button deleteButton;

    @FXML
    private Pane setNode;

    private setNodes setNodesPane;

    private TestListener test2;

    @FXML
    void deleteButtonClicked(ActionEvent event) {
        
        

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        setNodesPane = new setNodes();
        
        setNodesPane.setPrefSize(setNode.getPrefWidth(), setNode.getPrefHeight());
        
        setNode.getChildren().add(setNodesPane);
        TestListener test2 = new TestListener(this);
        new Thread(test2).start();
    }

    public void mouseClicked(){
        
    }

    public void draw(int x){
        Circle circle = new Circle(x,200,50);
        setNodesPane.getChildren().add(circle);
    }

    public void updateGraph(Graph graph){
        setNodesPane.SetGraph(graph);
    }

}
