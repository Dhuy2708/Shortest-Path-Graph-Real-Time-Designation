package Client.View;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


import Shared.Model.Graph;


//Form de client thiet ke graph
public class InputForm extends JFrame {

    public static JTextField text = new JTextField();   //này để hiển thị hành động của client(node bắt đầu và node kết thúc client đã chọn)
    private JTextArea textArea = new JTextArea();
    private JButton clrButton;
    private setNodes graphPanel;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InputForm frame = new InputForm();
            frame.displayForm();
        });
    }

    public String getStatus(){
        return graphPanel.getStatus();
    }

    public setNodes getGraphPanel(){
        return this.graphPanel;
    }

    public Graph getGraph(){
        return this.graphPanel.getGraph();
    }

    public void setGraph(Graph graph){
        this.graphPanel.SetGraph(graph);
    }

    public void displayForm(){
         this.setVisible(true);
    }

    //Hàm để khởi tạo các giá trị thuộc tính của frame
    public void setFormProperties(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
    }

    // Khởi tạo cửa sổ JFrame
    public InputForm() {
        this.setFormProperties();

        clrButton = new JButton("Xóa");
        graphPanel = new setNodes();
        graphPanel.setBackground(new Color(255, 255, 255));

        clrButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                graphPanel.clearAllNodes();
            }
        });

        graphPanel.addMouseClickListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
              
                    text.setText(graphPanel.getStatus());
                
                
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(clrButton);
        text.setHorizontalAlignment(SwingConstants.LEFT);
        buttonPanel.add(text);

        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(graphPanel);
        graphPanel.setLayout(null);

        textArea.setBounds(592, 23, 184, 123);
        graphPanel.add(textArea);
    }

    
    //CÁC HÀM LẮNG NGHE SỰ KIỆN CỦA FRAME(SỰ KIỆN SẼ ĐƯỢC XỬ LÍ BÊN CLIENTCONTROLLER)

    //listener cho sự kiện click chuột
    public void addMouseClickListener(MouseAdapter adapter){
        this.graphPanel.addMouseClickListener(adapter);
    }

    //listener cho sự kiện nhấn nút confirm
    public void addConfirmButtonListener(ActionListener listener){
        this.graphPanel.addConfirmButtonListener(listener);
    }

    //listener cho sự kiện nhấn nút random
    public void addRandomButtonListener(ActionListener listener){
        this.graphPanel.addConfirmButtonListener(listener);
    }

    //listener cho sự kiện nhấn nút xóa
    public void addClearButtonListener(ActionListener listener){
        this.clrButton.addActionListener(listener);
    }

    //listener chung cho các button của form
    public void addGeneralButtonListener(ActionListener listener){
        this.graphPanel.addGeneralButtonListener(listener);
        this.clrButton.addActionListener(listener);
    }
    
}

