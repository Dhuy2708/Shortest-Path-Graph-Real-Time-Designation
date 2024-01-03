import java.awt.Point;

import Shared.Model.Graph;
import Shared.Model.Node;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;

public class TestListener implements Runnable{
    Controller controller;

    TestListener(Controller controller){
       
        this.controller = controller;

       
    }

    public void run(){
        
        try{

            Graph graph = new Graph();
            graph.addNode(new Node(new Point(200, 200), "a"));
            controller.updateGraph(graph);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(int x){
        controller.draw(x);
    }

    public TestListener(){
        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene/InputGraphScene.fxml"));
            loader.load();
            controller = loader.getController();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        TestListener test2 = new TestListener();
        
    }
}
