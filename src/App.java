
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    private Circle selectedCircle = null;
    private int circleIndex = 0;
    private double borderWidth = 2.5;
    private final int RADIUS = 25;
    private final double MIN_DISTANCE = 2 * RADIUS; // Minimum distance required


    private Circle selectedCircle1 = null;
    private Circle selectedCircle2 = null;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);

        Text messageText = new Text(10,10,"");
        root.getChildren().add(messageText);

        // Define a custom font
        Font customFont = Font.font("Arial", FontWeight.BOLD, 16);

        scene.setOnMousePressed(event -> {
            messageText.setText("");
            if (event.getButton() == MouseButton.PRIMARY) {
                if (selectedCircle != null) {
                    selectedCircle.setStroke(Color.BLACK);
                    selectedCircle.setStrokeWidth(borderWidth);
                    selectedCircle = null;
                }

                // Check for collision with existing circles and minimum distance
                boolean canCreateCircle = true;
                for (Circle existingCircle : root.getChildren().filtered(Circle.class::isInstance).toArray(Circle[]::new)) {
                    double distance = calculateDistance(existingCircle.getCenterX(), existingCircle.getCenterY(), event.getX(), event.getY());
                    if (distance < MIN_DISTANCE) {
                        canCreateCircle = false;

                        messageText.setText("Cannot create bc the distance is to close");
                        break;
                    }
                }

                if (canCreateCircle) {
                    Circle circle = new Circle(event.getX(), event.getY(), RADIUS, Color.rgb(243, 112, 32));
                    circle.setStroke(Color.BLACK);
                    circle.setStrokeWidth(borderWidth);

                    Text text = new Text(Integer.toString(circleIndex));
                    text.setFont(customFont); // Set the custom font
                    text.setFill(Color.WHITE); // Set text color

                    // Calculate X and Y positions to center the text
                    double textX = event.getX() - text.getLayoutBounds().getWidth() / 2;
                    double textY = event.getY() + text.getLayoutBounds().getHeight() / 4;

                    text.setX(textX);
                    text.setY(textY);

                    // Make the text node transparent to mouse events
                    text.setMouseTransparent(true);

                    circleIndex++;

                    circle.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                            if (selectedCircle != null) {
                                selectedCircle.setStroke(Color.BLACK);
                                selectedCircle.setStrokeWidth(borderWidth);
                            }
                            selectedCircle = circle;
                            circle.setStroke(Color.LIGHTGREEN);
                            circle.setStrokeWidth(borderWidth * 1.5);
                        }
                    });

                    root.getChildren().addAll(circle, text);
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                if (selectedCircle != null) {
                    selectedCircle.setStroke(Color.BLACK);
                    selectedCircle.setStrokeWidth(borderWidth);
                    selectedCircle = null;
                }

                Circle clickedCircle = findClickedCircle(root, event.getX(), event.getY());

                if (clickedCircle != null) {
                    if (selectedCircle1 == null) {
                        selectedCircle1 = clickedCircle;
                    } else if (selectedCircle2 == null && selectedCircle1 != clickedCircle) {
                        selectedCircle2 = clickedCircle;

                        // Draw an arrow from selectedCircle1 to selectedCircle2
                        drawArrow(root, selectedCircle1, selectedCircle2);

                        // Reset the selected circles
                        selectedCircle1 = null;
                        selectedCircle2 = null;
                    }
                }
            }   
        });

        // scene.setOnMousePressed(event -> {
        //     if (event.getButton() == MouseButton.SECONDARY) {
        //         Circle clickedCircle = findClickedCircle(root, event.getX(), event.getY());

        //         if (clickedCircle != null) {
        //             if (selectedCircle1 == null) {
        //                 selectedCircle1 = clickedCircle;
        //             } else if (selectedCircle2 == null && selectedCircle1 != clickedCircle) {
        //                 selectedCircle2 = clickedCircle;

        //                 // Draw an arrow from selectedCircle1 to selectedCircle2
        //                 drawArrow(root, selectedCircle1, selectedCircle2);

        //                 // Reset the selected circles
        //                 selectedCircle1 = null;
        //                 selectedCircle2 = null;
        //             }
        //         }
        //     }
        // });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Circle Selection App");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Calculate distance between two points
    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private Circle findClickedCircle(Pane root, double x, double y) {
        for (javafx.scene.Node node : root.getChildren()) {
            if (node instanceof Circle) {
                Circle circle = (Circle) node;
                if (circle.contains(x, y)) {
                    return circle;
                }
            }
        }
        return null;
    }

    private void drawArrow(Pane root, Circle circle1, Circle circle2) {
        double x1 = circle1.getCenterX();
        double y1 = circle1.getCenterY();
        double x2 = circle2.getCenterX();
        double y2 = circle2.getCenterY();

        Line arrowLine = new Line(x1, y1, x2, y2);
        arrowLine.setStroke(Color.BLACK);

        double arrowLength = 10.0; // Length of the arrowhead
        double arrowAngle = Math.toRadians(45.0); // Angle of the arrowhead

        // Calculate the coordinates of the arrowhead points
        double arrowX1 = x2 + arrowLength * Math.cos(Math.atan2(y2 - y1, x2 - x1) + arrowAngle);
        double arrowY1 = y2 + arrowLength * Math.sin(Math.atan2(y2 - y1, x2 - x1) + arrowAngle);
        double arrowX2 = x2 + arrowLength * Math.cos(Math.atan2(y2 - y1, x2 - x1) - arrowAngle);
        double arrowY2 = y2 + arrowLength * Math.sin(Math.atan2(y2 - y1, x2 - x1) - arrowAngle);

        Line arrowHead1 = new Line(x2, y2, arrowX1, arrowY1);
        Line arrowHead2 = new Line(x2, y2, arrowX2, arrowY2);
        arrowHead1.setStroke(Color.BLACK);
        arrowHead2.setStroke(Color.BLACK);

        root.getChildren().addAll(arrowLine, arrowHead1, arrowHead2);
    }

    
}
