import java.awt.EventQueue;

import View.InputGraph.DesignGraphPanel;


public class Program {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DesignGraphPanel form = new DesignGraphPanel();
					//form.displayForm();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
