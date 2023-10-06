package View;

import Controller.GraphDraw;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Button;

public class InputGraphForm extends JPanel {
	/**
	 * Create the panel.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputGraphForm frame = new InputGraphForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public InputGraphForm() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Thiết kế đồ thị mạng");
		lblNewLabel.setBounds(10, 11, 987, 44);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel);
		
	}
}
