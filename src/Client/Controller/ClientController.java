package Client.Controller;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import Client.Client;
import Client.View.setNodes;
import Shared.Helper.StringExcute;
import Shared.Model.DataExchangeModel;
import Shared.Model.Graph;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



//Controller de tuong tac giua CLientView va CLientModel và gửi dữ liệu đến Client
public class ClientController implements Initializable{
    
    @FXML
    private Button deleteButton;
    
    @FXML
    private Button confirmButton;

    @FXML
    private Pane setNode;
    
    private setNodes setNodesPane;
    
    private DataExchangeModel dataExchangeModel;
    private Client client;
    private static StringExcute stringHelper;

    private static OutputGraphController outCon;

    private Scene outputScene;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         setNodesPane = new setNodes();
         dataExchangeModel = new DataExchangeModel();
         
         setNodesPane.setPrefSize(setNode.getPrefWidth(), setNode.getPrefHeight());
         
         setNode.getChildren().add(setNodesPane);

         setNodesPane.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            mousePressed();
         });

         deleteButton.setOnAction(event -> {
            System.out.println("DELETE BUTTON CLICKED");
         });

         confirmButton.setOnAction(event -> confirmButtonClicked());
         
         client = new Client("localhost", 2708, this);
         stringHelper = new StringExcute();

         client.run();
    }

    @FXML
    private void confirmButtonClicked() {
        System.out.println("CONFIRM BUTTON CLICKED");
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene/OutputGraphScene.fxml"));
    
            Parent root = loader.load();

            outCon = loader.<OutputGraphController>getController();

            outputScene = new Scene(root);

            Platform.runLater(() -> {
                Stage stage = new Stage();
                stage.setScene(outputScene);

                stage.show();
            });

            outCon.setGraph(setNodesPane.getGraph());
            

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    

    //trả dữ liệu về view
    public void SetGraphToView(Graph graph){
        Platform.runLater(() -> {

            setNodesPane.SetGraph(graph);
        });
    }

    //cập nhật dữ liệu của model 
    public void UpdateDataToClient(String message){
        //lấy ra graph
        Graph graph = this.dataExchangeModel.getGraphData();

        //xử lí graph
        stringHelper.translateString(graph, message);

        //set dữ liệu mới cho client
        this.dataExchangeModel.setGraphData(graph);

        //đổ lên view
        this.SetGraphToView(graph);
    }
    

//=====================================================
    //EVENT HANDLER

    public void mousePressed(){
        dataExchangeModel.setGraphData(setNodesPane.getGraph());
        //gửi dữ liệu đến server
        String messageToSend = setNodesPane.getStatus();

        if(!messageToSend.equals("")){
            client.SendDataToServer(setNodesPane.getStatus());
        }

    }


 
    
}
