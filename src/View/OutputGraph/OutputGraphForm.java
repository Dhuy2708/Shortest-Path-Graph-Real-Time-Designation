package View.OutputGraph;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.Graph;
import Model.Node;
import Controller.*;

import javax.swing.JInternalFrame;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.SwingConstants;
import java.awt.Label;
import java.awt.List;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.CompoundBorder;

public class OutputGraphForm extends JFrame {

	private JPanel contentPane;
	private JTextField startNodeTF;
	private JTextField endNodeTF;
	private JTextField routeTextField;
	private JTextField distanceTextField;

	private Graph graph = new Graph();
	private ArrayList<Node> shortestPath = new ArrayList<>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		OutputGraphForm form = new OutputGraphForm();
		form.displayForm();
	}
	/**
	 * Create the frame.
	 */
	public OutputGraphForm(){

	}
	public OutputGraphForm(Graph data) {
		this.graph = data;
	}

	public void displayForm(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1104, 802);
		contentPane = new JPanel();
		contentPane.setBorder(new CompoundBorder());

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		routeTextField = new JTextField();
		routeTextField.setEditable(false);
		routeTextField.setForeground(Color.RED);
		routeTextField.setHorizontalAlignment(SwingConstants.CENTER);
		routeTextField.setText("");
		routeTextField.setFont(new Font("Times New Roman", Font.BOLD, 25));
		routeTextField.setBounds(725, 532, 353, 56);
		contentPane.add(routeTextField);
		routeTextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Đường đi");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(574, 532, 141, 56);
		contentPane.add(lblNewLabel);
		
		JLabel lblKhongCch = new JLabel("Khoảng cách");
		lblKhongCch.setHorizontalAlignment(SwingConstants.CENTER);
		lblKhongCch.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblKhongCch.setBounds(584, 599, 212, 56);
		contentPane.add(lblKhongCch);
		
		distanceTextField = new JTextField();
		distanceTextField.setEditable(false);
		distanceTextField.setText("");
		distanceTextField.setHorizontalAlignment(SwingConstants.CENTER);
		distanceTextField.setForeground(Color.BLUE);
		distanceTextField.setFont(new Font("Times New Roman", Font.BOLD, 30));
		distanceTextField.setColumns(10);
		distanceTextField.setBounds(817, 600, 110, 56);
		contentPane.add(distanceTextField);
		
		JButton btnNewButton_1 = new JButton("Close");
		btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		btnNewButton_1.setBounds(725, 704, 148, 47);
		contentPane.add(btnNewButton_1);
		
		JPanel guidePanel = new JPanel();
		guidePanel.setBounds(32, 298, 384, 453);
		contentPane.add(guidePanel);
		guidePanel.setLayout(null);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setBounds(70, 33, 241, 241);
		contentPane.add(controlPanel);
		controlPanel.setLayout(null);
		
		startNodeTF = new JTextField();
		startNodeTF.setBounds(26, 74, 72, 71);
		controlPanel.add(startNodeTF);
		startNodeTF.setHorizontalAlignment(SwingConstants.CENTER);
		startNodeTF.setFont(new Font("Times New Roman", Font.PLAIN, 70));
		startNodeTF.setText("1");
		startNodeTF.setColumns(10);
		
		endNodeTF = new JTextField();
		endNodeTF.setBounds(141, 74, 72, 71);
		controlPanel.add(endNodeTF);
		endNodeTF.setText("6");
		endNodeTF.setFont(new Font("Times New Roman", Font.PLAIN, 70));
		endNodeTF.setHorizontalAlignment(SwingConstants.CENTER);
		endNodeTF.setColumns(10);
		
		JButton OKButton = new JButton("Chọn");
		OKButton.setBounds(58, 166, 133, 48);
		controlPanel.add(OKButton);
		OKButton.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		
		JLabel lblNewLabel_1 = new JLabel("Bảng điều khiển");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(26, 11, 187, 29);
		controlPanel.add(lblNewLabel_1);
		
		graphPanel graphPanel = new graphPanel(graph, shortestPath);
		graphPanel.setLayout(null);
		graphPanel.setBounds(464, 33, 614, 453);
		contentPane.add(graphPanel);

		this.setVisible(true);

		//EVENT HANDLER
		OKButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if(graph.isNodeNameExist(startNodeTF.getText()) == false || graph.isNodeNameExist(endNodeTF.getText()) == false){
					JOptionPane.showMessageDialog(null, "Không tìm thấy nút mạng", "Lỗi", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					Node startNode = new Node();
					startNode = graph.getNodeByName(startNodeTF.getText());

					Node endNode = new Node();
					endNode = graph.getNodeByName(endNodeTF.getText());

					GraphController.shortestPath(graph, startNode);
					shortestPath = GraphController.getShortestPath(startNode, endNode);

					repaint();

				}
			}
		});
	}
}

class graphPanel extends JPanel{
	private Graph graph = new Graph();
	private ArrayList<Node> shortestPath = new ArrayList<>();

	private int nodeRadius = 15;

	public graphPanel(Graph data, ArrayList<Node> shortestPath){
		this.graph = data;
		this.shortestPath = shortestPath;
	}

	@Override
	protected void paintComponent(Graphics g){
		//Draw edge
        for(Map.Entry<Node, Map<Node, Integer>> node : graph.getAdjacencyList().entrySet()){

            for(Map.Entry<Node, Integer> innerNode : graph.getNeighbors(node.getKey()).entrySet()){
                Point startPoint = node.getKey().getPoint();
                Point endPoint = innerNode.getKey().getPoint();

                g.setColor(Color.RED);
                
                g.drawLine((int)startPoint.getX(), (int)startPoint.getY(), (int)endPoint.getX(), (int)endPoint.getY());
               
            }
        }

		//Draw shortest path
		if(shortestPath.size() > 0){
			for(int i = 0; i < shortestPath.size() - 1; i++){
				Point startPoint = shortestPath.get(i).getPoint();
				Point endPoint = shortestPath.get(i + 1).getPoint();

				g.setColor(Color.MAGENTA);

				g.drawLine((int)startPoint.getX(), (int)startPoint.getY() - 1, (int)endPoint.getX(), (int)endPoint.getY() - 1);
				g.drawLine((int)startPoint.getX(), (int)startPoint.getY(), (int)endPoint.getX(), (int)endPoint.getY());
				g.drawLine((int)startPoint.getX(), (int)startPoint.getY() + 1, (int)endPoint.getX(), (int)endPoint.getY() + 1);


			}
		}

		//Draw nodes
        for(Map.Entry<Node, Map<Node, Integer>> node : graph.getAdjacencyList().entrySet()){
            g.setColor(Color.DARK_GRAY);
            int x = (int)node.getKey().getPoint().getX();
            int y = (int)node.getKey().getPoint().getY();

            
            g.fillOval(x - nodeRadius, y - nodeRadius, nodeRadius * 2, nodeRadius * 2);

            g.setColor(Color.WHITE);
            g.drawString(node.getKey().getName(), x - 5, y + 5);
        }
	}
}

class OkButtonClick implements ActionListener{
	OutputGraphForm form;

	public OkButtonClick(OutputGraphForm form){
		this.form = form;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}