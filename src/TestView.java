import java.awt.Point;

import Client.View.setNodes;
import Shared.Model.Graph;
import Shared.Model.Node;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TestView extends Application {
    private setNodes pane = new setNodes();

    public void setGraph(Graph graph) {
        pane.SetGraph(graph);
    }


    @Override
    public void start(Stage primaryStage) {
        
        Text text = new Text(100, 100, "");
        pane.getChildren().add(text);
        pane.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            text.setText(pane.getStatus());
        });
        Scene scene = new Scene(pane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        TestView app = new TestView();

        Platform.runLater(() -> {
            app.start(new Stage());
        });

      

    }
}


