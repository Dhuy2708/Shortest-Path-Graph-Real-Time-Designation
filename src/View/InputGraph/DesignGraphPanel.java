package View.InputGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Model.*;
import javax.swing.border.BevelBorder;

import View.OutputGraph.*;

//Form để thiết kế đồ thị
public class DesignGraphPanel {

    public static JTextField text = new JTextField();
    public static JTextArea textArea = new JTextArea();

    public static void main(String[] args) {
        DesignGraphPanel panel = new DesignGraphPanel();
        panel.displayForm();
    }

    public void displayForm(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        
        JButton clrButton = new JButton("Xóa");
        setNodes graphPanel = new setNodes();
        graphPanel.setBackground(new Color(255, 255, 255));

        clrButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                graphPanel.clearAllNodes();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(clrButton);
        text.setHorizontalAlignment(SwingConstants.LEFT);
        buttonPanel.add(text);
        
        frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
        frame.getContentPane().add(graphPanel);
        graphPanel.setLayout(null);
        
        
        textArea.setBounds(592, 23, 184, 123);
        graphPanel.add(textArea);
        
        
        
        frame.setVisible(true);
    }
}

//Panel để vẽ và thiết kế độ thị
class setNodes extends JPanel {
    private int nodeRadius = 15;
    private ArrayList<NodeInfo> nodeList = new ArrayList<>();

    private int numberOfNodesSelected = 0;
    private Node startNode;
    private Node endNode = new Node();
    HashMap<Integer, Node> nodes = new HashMap<>();
    public Graph graph = new Graph();

    public Graph getGraph(){
        return graph;
    }

    public setNodes(){

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(10, 11, 89, 23);
        this.add(confirmButton);


        JButton randomButton = new JButton("Random");
        randomButton.setBounds(150, 11, 89, 23);
        this.add(randomButton);
        

        confirmButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                try{
                    OutputGraphForm form = new OutputGraphForm(graph);
                    form.displayForm();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
                
                
            }
        });

        randomButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Rectangle rec = new Rectangle(50, 100, 500, 300);
                    Random random = new Random();
                    int nodeAmount = random.nextInt(4) + 6;
                    graph.setRandomGraph(rec, nodeAmount);
                    repaint();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
               
            }
        });


        addMouseListener(new MouseAdapter(){
            
            //int numberOfNodesSelected = 0;    
            boolean isInSelection = false;
            
            @Override
            public void mouseClicked(MouseEvent e){

                //Click chuot trai, Add node
                if(e.getButton() == MouseEvent.BUTTON1) {
                    NodeInfo point = new NodeInfo(e.getPoint(), graph.getAdjacencyList().size() + 1);
                    nodeList.add(point); //add point vao pointlist

                    Node node = new Node(point.getPoint(), String.valueOf(point.getIndex()));
                    nodes.put(graph.getAdjacencyList().size() + 1, node); //add node into nodes
                    graph.addNode(node); //add node into graph
                    repaint();
                }

                //Click chuot phai, Add edge
                else if(e.getButton() == MouseEvent.BUTTON3){
                    
                    boolean nodeSelected = false;
                    for(NodeInfo node : nodeList){
                        if(node.getPoint().distance(e.getPoint()) <= nodeRadius){
                            numberOfNodesSelected++;
                            nodeSelected = true;
                            if(numberOfNodesSelected == 1){
                                startNode = nodes.get(node.getIndex());
                            }
                            else if(numberOfNodesSelected == 2){
                                endNode = nodes.get(node.getIndex());



                                

                                try{
                                    //add edge into graph
                                    graph.addEdge(startNode, endNode, (int)startNode.getPoint().distance(node.getPoint()));
                                    repaint();
                                }
                                catch(Exception ex){
                                    ex.printStackTrace();
                                }



                                numberOfNodesSelected = 0;

                            }
                            break;
                            
                        }
                        
                    }
                    DesignGraphPanel.text.setText("selected: " + nodeSelected + " start: " + startNode.getName() + " end: " + endNode.getName());
                    if(nodeSelected == false) {
                        numberOfNodesSelected = 0;
                        startNode.setName("");
                        endNode.setName("");
                    }
                    else nodeSelected = false;


                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        //Draw edge
        for(Map.Entry<Node, Map<Node, Integer>> node : graph.getAdjacencyList().entrySet()){
            for(Map.Entry<Node, Integer> innerNode : graph.getNeighbors(node.getKey()).entrySet()){
                Point startPoinnt = node.getKey().getPoint();
                Point endPoint = innerNode.getKey().getPoint();

                g.setColor(Color.RED);
                
                g.drawLine((int)startPoinnt.getX(), (int)startPoinnt.getY() - 1, (int)endPoint.getX(), (int)endPoint.getY() - 1);
                g.drawLine((int)startPoinnt.getX(), (int)startPoinnt.getY(), (int)endPoint.getX(), (int)endPoint.getY());
                g.drawLine((int)startPoinnt.getX(), (int)startPoinnt.getY() + 1, (int)endPoint.getX(), (int)endPoint.getY() + 1);
               
            }  
        }

        for(Map.Entry<Node, Map<Node, Integer>> node : graph.getAdjacencyList().entrySet()){
            g.setColor(Color.DARK_GRAY);
            int x = (int)node.getKey().getPoint().getX();
            int y = (int)node.getKey().getPoint().getY();

            //draw node
            g.fillOval(x - nodeRadius, y - nodeRadius, nodeRadius * 2, nodeRadius * 2);

            g.setColor(Color.WHITE);
            g.drawString(node.getKey().getName(), x - 5, y + 5);
        }
        




        
    }

    public void clearAllNodes(){
        graph.getAdjacencyList().clear();
        repaint();
    }
}




class NodeInfo {
    private Point point;
    private int index;

    //0: not selected, 1: start node selected, 2: end node selected
    private int orderSelected = 0;

    public NodeInfo(){

    }

    public NodeInfo(Point point, int index) {
        this.point = point;
        this.index = index;
    }

    public Point getPoint() {
        return point;
    }

    public int getIndex() {
        return index;
    }

    public void setOrder(int orderSelected){
        this.orderSelected = orderSelected;
    }

    public int getOrder(){
        return this.orderSelected;
    }

}
