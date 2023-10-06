import java.awt.EventQueue;
import javax.swing.UIManager;
import View.GraphForm;

public class Program {

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

}
