package Client.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.Map;
import java.util.Random;


import View.OutputGraph.*;

import Shared.Model.*;

//Panel để vẽ và thiết kế độ thị
public class setNodes extends JPanel {
    private JButton confirmButton;
    private JButton randomButton;

    private int nodeRadius = 15;

    private int numberOfNodesSelected = 0;
    private Node startNode = new Node();
    private Node endNode = new Node();
    
    private String status; //ĐÂY LÀ TẤT CẢ (DỮ LIỆU MESSAGE ĐỂ GỬI QUA SERVER)

    private Graph graph = new Graph();

    public String getStatus(){
        return this.status;
    }

    public Graph getGraph(){
        return graph;
    }

    public void SetGraph(Graph graph){
        this.graph = graph;
        repaint();
    }

    public int getNodeRadius(){
        return this.nodeRadius;
    }


    public setNodes(){

        confirmButton = new JButton("Confirm"); //button xác nhận và gửi dữ liệu đến server
        confirmButton.setBounds(10, 11, 89, 23);
        this.add(confirmButton);


        randomButton = new JButton("Random");   //button sinh graph random
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


        //SỰ KIỆN CLICK CHUỘT => TẠO NODE VÀ GRAPH
        addMouseListener(new MouseAdapter(){

            
            @Override
            public void mousePressed(MouseEvent e){

                //Click chuot trai, Add node
                if(e.getButton() == MouseEvent.BUTTON1) {
                    NodeInfo point = new NodeInfo(e.getPoint(), graph.getAdjacencyList().size() + 1);

                    Node node = new Node(point.getPoint(), String.valueOf(point.getIndex()));
                    graph.addNode(node); //add node into graph

                    status = "new " + node.getName() + " " + (int)node.getPoint().getX() + " " + (int)node.getPoint().getY();

                    repaint();
                }

                //Click chuot phai, Add edge
                else if(e.getButton() == MouseEvent.BUTTON3){
                    
                    boolean nodeSelected = false;
                    for(Map.Entry<Node, Map<Node, Integer>> node : graph.getAdjacencyList().entrySet()){
                        
                        if(node.getKey().getPoint().distance(e.getPoint()) <= nodeRadius){
                            numberOfNodesSelected++;
                            nodeSelected = true;
                            if(numberOfNodesSelected == 1){
                                startNode = node.getKey();

                                status = "";
                            }
                            else if(numberOfNodesSelected == 2){
                                endNode = node.getKey();

                                try{
                                    //add edge into graph
                                    graph.addEdge(startNode, endNode, (int)startNode.getPoint().distance(endNode.getPoint()));

                                    //status
                                    status = "connect " + startNode.getName() + " " + endNode.getName();
                                    repaint();
                                }
                                catch(Exception ex){
                                    ex.printStackTrace();
                                }

                                numberOfNodesSelected = 0;


                            }
                            break;
                            
                        }
                        else{
                            status = "";
                        }
                        
                    }
                    //InputForm.text.setText("selected: " + nodeSelected + " start: " + startNode.getName() + " end: " + endNode.getName());
                    if(nodeSelected == false) {
                        numberOfNodesSelected = 0;
                        // startNode.setName("");
                        // endNode.setName("");
                    }
                    else nodeSelected = false;


                }
            }
        });
        
        //sự kiện tạo graph random
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

    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        //vẽ cạnh của đồ thị, anh có thể sửa đồ họa lại cho đẹp
        for(Map.Entry<Node, Map<Node, Integer>> node : graph.getAdjacencyList().entrySet()){
            for(Map.Entry<Node, Integer> innerNode : graph.getNeighbors(node.getKey()).entrySet()){
                Point startPoinnt = node.getKey().getPoint();
                Point endPoint = innerNode.getKey().getPoint();


                //vẽ cạnh
                g.setColor(Color.RED);
                
                g.drawLine((int)startPoinnt.getX(), (int)startPoinnt.getY() - 1, (int)endPoint.getX(), (int)endPoint.getY() - 1);
                g.drawLine((int)startPoinnt.getX(), (int)startPoinnt.getY(), (int)endPoint.getX(), (int)endPoint.getY());
                g.drawLine((int)startPoinnt.getX(), (int)startPoinnt.getY() + 1, (int)endPoint.getX(), (int)endPoint.getY() + 1);
               
            }  
        }

        //vẽ node của đồ thị
        for(Map.Entry<Node, Map<Node, Integer>> node : graph.getAdjacencyList().entrySet()){
            g.setColor(Color.DARK_GRAY);
            int x = (int)node.getKey().getPoint().getX();
            int y = (int)node.getKey().getPoint().getY();

            //vẽ node
            g.fillOval(x - nodeRadius, y - nodeRadius, nodeRadius * 2, nodeRadius * 2);

            g.setColor(Color.WHITE);
            g.drawString(node.getKey().getName(), x - 5, y + 5);
        }
    }

    public void clearAllNodes(){
        graph.getAdjacencyList().clear();
        repaint();
    }



    //=============CÁC HÀM LẮNG NGHE SỰ KIỆN CỦA PANEL =======================


    //listener cho sự kiện click chuột
    public void addMouseClickListener(MouseAdapter adapter){
        this.addMouseListener(adapter);
    }

    //listener cho sự kiện nhấn nút confirm
    public void addConfirmButtonListener(ActionListener listener){
        this.confirmButton.addActionListener(listener);
    }

    //listener cho sự kiện nhấn nút random
    public void addRandomButtonListener(ActionListener listener){
        this.randomButton.addActionListener(listener);
    }

    //listener chung cho các button của panel
    public void addGeneralButtonListener(ActionListener listener){
        this.randomButton.addActionListener(listener);
        this.confirmButton.addActionListener(listener);
    }
}






