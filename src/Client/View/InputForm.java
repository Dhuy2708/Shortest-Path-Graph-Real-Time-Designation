package Client.View;


import java.awt.Point;
import java.util.concurrent.CountDownLatch;

import Shared.Model.Graph;
import Shared.Model.Node;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


//Form de client thiet ke graph

public class InputForm extends Application {
    private setNodes setNodesPane;
    public static final CountDownLatch latch = new CountDownLatch(1);
    public static InputForm inputForm = null;

    public static InputForm waitForInputForm(){
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return inputForm;
    }

    public static void setInputForm(InputForm inputForm0) {
        inputForm = inputForm0;
        latch.countDown();
    }

    public InputForm(){
        setNodesPane = new setNodes();
        setInputForm(this);
    }

    @Override
    public void start(Stage primaryStage){
        try{
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene/InputGraphScene.fxml"));
            
            Parent root = loader.load();
    
            Scene scene = new Scene(root);
    
            // Button deleteButton = (Button)scene.lookup("#deleteButton");
            // Pane setNode = (Pane)scene.lookup("#setNode");
    
            
            // if(deleteButton != null){
            //     deleteButton.setOnAction(event -> {
            //     setNodesPane.deleteAllNodes();
            //     });
            // }
          
           
            // setNodesPane.setPrefSize(setNode.getPrefWidth(), setNode.getPrefHeight());
    
            // setNode.getChildren().add(setNodesPane);
    
            primaryStage.setResizable(false);
    
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(){
        setNodesPane.getChildren().add(new Circle(200,200,100));
    }
    
    public void printSth(){
        System.out.println("aaaaaaa");
    }

    public String getStatus(){
        return setNodesPane.getStatus();
    }

    public setNodes getGraphPanel(){
        return setNodesPane;
    }

    public Graph getGraph(){
        return this.setNodesPane.getGraph();
    }

    public void setGraph(Graph graph){
        this.setNodesPane.SetGraph(graph);
    }

    public void displayForm(){
       Platform.runLater(() -> {
            start(new Stage());
        });
    }

     //CÁC HÀM LẮNG NGHE SỰ KIỆN CỦA FRAME(SỰ KIỆN SẼ ĐƯỢC XỬ LÍ BÊN CLIENTCONTROLLER)

    //listener cho sự kiện click chuột
    public void addMouseClickListener(javafx.event.EventHandler<javafx.scene.input.MouseEvent> event){
        this.setNodesPane.addMouseClickListener(event);
    }


    public static void main(String[] args){
       launch(args);

            
    }
    
    
}

