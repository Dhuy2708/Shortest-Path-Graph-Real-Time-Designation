package Client.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Client.View.setNodes;
import Shared.Model.Graph;
import Shared.Model.Node;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class OutputGraphController implements Initializable{
    

    @FXML
    private TextField endNodeTextField;

    @FXML
    private Text infoTF;

    @FXML
    private Button okButton;

    @FXML
    private Pane outputGraphPane;

    @FXML
    private TextField startNodeTextField;
    
    private setNodes setNodesPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNodesPane = new setNodes();
        setNodesPane.isEditable(false);
        setNodesPane.setScale(0.82);
        setNodesPane.setPrefSize(outputGraphPane.getPrefWidth(), outputGraphPane.getPrefHeight());
        outputGraphPane.getChildren().add(setNodesPane);

        setNodesPane.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            setTextForTextField();
        });

        okButton.setOnAction(event -> okButtonClicked());
    }

    private void okButtonClicked(){
        infoTF.setText(setNodesPane.findShortestPath(startNodeTextField.getText(), endNodeTextField.getText()));
    }

    private void setTextForTextField(){

        startNodeTextField.setText(setNodesPane.getStartNodeTF());
        endNodeTextField.setText(setNodesPane.getEndNodeTF());

    }

    public void setGraph(Graph graph){
        if(setNodesPane != null){
            setNodesPane.SetGraph(graph);
        }
    }




}
