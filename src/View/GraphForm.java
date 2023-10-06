package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JInternalFrame;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Label;
import javax.swing.JLabel;
import javax.swing.border.CompoundBorder;

public class GraphForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphForm frame = new GraphForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GraphForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 727);
		contentPane = new JPanel();
		contentPane.setBorder(new CompoundBorder());

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setForeground(Color.RED);
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setText("1 -> 6 -> 5 -> 4");
		textField_2.setFont(new Font("Times New Roman", Font.BOLD, 25));
		textField_2.setBounds(577, 365, 291, 56);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Đường đi");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(426, 365, 141, 56);
		contentPane.add(lblNewLabel);
		
		JLabel lblKhongCch = new JLabel("Khoảng cách");
		lblKhongCch.setHorizontalAlignment(SwingConstants.CENTER);
		lblKhongCch.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblKhongCch.setBounds(436, 432, 212, 56);
		contentPane.add(lblKhongCch);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setText("6");
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setForeground(Color.BLUE);
		textField_3.setFont(new Font("Times New Roman", Font.BOLD, 30));
		textField_3.setColumns(10);
		textField_3.setBounds(669, 433, 110, 56);
		contentPane.add(textField_3);
		
		JButton btnNewButton_1 = new JButton("Close");
		btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		btnNewButton_1.setBounds(577, 537, 148, 47);
		contentPane.add(btnNewButton_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(32, 298, 345, 381);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(70, 33, 241, 241);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(26, 74, 72, 71);
		panel_1.add(textField);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 70));
		textField.setText("1");
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(141, 74, 72, 71);
		panel_1.add(textField_1);
		textField_1.setText("4");
		textField_1.setFont(new Font("Times New Roman", Font.PLAIN, 70));
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("Chọn");
		btnNewButton.setBounds(58, 166, 133, 48);
		panel_1.add(btnNewButton);
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		
		JLabel lblNewLabel_1 = new JLabel("Bảng điều khiển");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(26, 11, 187, 29);
		panel_1.add(lblNewLabel_1);
		
		this.setVisible(true);
	}
}
