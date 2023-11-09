import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import Client.Client;

public class ClientView extends JFrame {
    private JTextField textField;

    private JButton button ;

    int a = 0;

    public ClientView() {
        // Gọi constructor của lớp cha JFrame và đặt tiêu đề cửa sổ
        super("Hiển thị số");

        // Tạo một JTextField (ô văn bản) để hiển thị số
        textField = new JTextField(10);
        textField.setEditable(false); // Ngăn người dùng chỉnh sửa nội dung trong textField

        // Tạo một số để hiển thị
        //int numberToDisplay = 42;

        // Đặt nội dung của textField là số cần hiển thị
        //textField.setText(String.valueOf(numberToDisplay));

        // Thêm textField vào cửa sổ
        textField.setBounds(100,100, 200,200);
        add(BorderLayout.WEST ,textField);

        button = new JButton("ok");

        add(BorderLayout.EAST ,button);

        SetText(a);

        // Cài đặt kích thước cửa sổ
        setSize(800, 600);


        // Đảm bảo chương trình kết thúc khi đóng cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void SetText(int number){
        textField.setText(String.valueOf(number));

    }

    public void AddButtonListener(ActionListener e){
        this.button.addActionListener(e);
    }

    public void display(){
        this.setVisible(true);
    }

    public static void main(String[] args) {
        ClientView frame = new ClientView();
        frame.setVisible(true);
    }
}
